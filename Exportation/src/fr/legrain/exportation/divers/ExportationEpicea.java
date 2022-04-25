package fr.legrain.exportation.divers;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import org.apache.log4j.Logger;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.Platform;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.dialogs.ListSelectionDialog;

import fr.legrain.documents.dao.TaAcompte;
import fr.legrain.documents.dao.TaAcompteDAO;
import fr.legrain.documents.dao.TaApporteur;
import fr.legrain.documents.dao.TaApporteurDAO;
import fr.legrain.documents.dao.TaAvoir;
import fr.legrain.documents.dao.TaAvoirDAO;
import fr.legrain.documents.dao.TaFacture;
import fr.legrain.documents.dao.TaFactureDAO;
import fr.legrain.documents.dao.TaLApporteur;
import fr.legrain.documents.dao.TaLAvoir;
import fr.legrain.documents.dao.TaLFacture;
import fr.legrain.documents.dao.TaLRemise;
import fr.legrain.documents.dao.TaRAcompte;
import fr.legrain.documents.dao.TaRAvoir;
import fr.legrain.documents.dao.TaRReglement;
import fr.legrain.documents.dao.TaRReglementDAO;
import fr.legrain.documents.dao.TaReglement;
import fr.legrain.documents.dao.TaReglementDAO;
import fr.legrain.documents.dao.TaRemise;
import fr.legrain.documents.dao.TaRemiseDAO;
import fr.legrain.exportation.ExportationPlugin;
import fr.legrain.exportation.preferences.PreferenceConstants;
import fr.legrain.gestCom.Module_Document.IDocumentTiers;
import fr.legrain.gestCom.Module_Document.IRelationDoc;
import fr.legrain.lib.data.LibCalcul;
import fr.legrain.lib.data.LibConversion;
import fr.legrain.lib.data.LibDate;
import fr.legrain.tiers.dao.TaTTvaDoc;
import fr.legrain.tiers.dao.TaTTvaDocDAO;
import fr.legrain.tiers.dao.TaTiers;

/**
<table border="1">
	<tr>
		<td>Position</td>
		<td>Nom</td>								
		<td>Longueur</td> 
		<td>Commentaire</td>
	</tr>
	<tr>
		<td>1</td>
		<td>N° de pièce	</td>
		<td>5</td>	<td>*généré</td>
	</tr>
	<tr>
		<td>2</td>
		<td>N° de ligne dans la pièce</td>
		<td>3</td>
		<td>TA_L_FACTURE.NUM_LIGNE_L_FACTURE</td>
	</tr>
	<tr>
		<td>3</td>
		<td>Type de pièce</td>
		<td>1</td>
		<td></td>
	</tr>
	<tr>
		<td>4</td>
		<td>N° de référence de la pièce</td>
		<td>9</td>	
		<td>TA_FACTURE.CODE_FACTURE</td>
	</tr>
	<tr>
		<td>5</td>
		<td>Date de la pièce</td>
		<td>8</td>
		<td>TA_FACTURE.DATE_FACTURE</td>
	</tr>
	<tr>
		<td>6</td>
		<td>N° de compte de la ligne</td>
		<td>8</td>
		<td>TA_INFOS_FACTURE.CODE_COMPTA / TA_TVA.NUMCPT_TVA</td>
	</tr>
	<tr>
		<td>7</td>
		<td>Désignation de la ligne</td>
		<td>23</td>
		<td>TA_L_FACTURE.LIB_L_FACTURE</td>
	</tr>
	<tr>
		<td>8</td>
		<td>Montant Débit de la ligne</td>
		<td>12</td>
		<td>*vide si TVA sinon => (entete) TA_L_FACTURE.MT_TTC_L_FACTURE</td>
	</tr>
	<tr>
		<td>9</td>
		<td>Montant Crédit de la ligne</td>
		<td>12</td>
		<td>*vide si pas TVA sinon => (ligne normale) TA_L_FACTURE.MT_HT_L_FACTURE</td>
	</tr>
	<tr>
		<td>10</td>
		<td>Quantité / unité 1 de la ligne</td>
		<td>9</td>
		<td>TA_L_FACTURE.QTE_L_FACTURE</td>
	</tr>
	<tr>
		<td>11</td>
		<td>Quantité / unité 2 de la ligne</td>
		<td>9</td>
		<td>TA_L_FACTURE.QTE_L_FACTURE</td>
	</tr>
	<tr>
		<td>12</td>
		<td>Codification TVA</td>
		<td>1</td>
		<td>TA_L_FACTURE.CODE_TVA_L_FACTURE</td>
	</tr>
	<tr>
		<td>13</td>
		<td>Taux de TVA	</td>
		<td>6</td>
		<td>TA_L_FACTURE.TAUX_TVA_L_FACTURE</td>
	</tr>
	<tr>
		<td>14</td>
		<td>Montant Débit de la TVA</td>
		<td>11</td>
		<td>*ttc-ht</td>
	</tr>
	<tr>
		<td>15</td>
		<td>Montant Crédit de la TVA</td>
		<td>11</td>
		<td>*ttc-ht</td>
	</tr>
	<tr>
		<td>16</td>
		<td>Date d’échéance de la pièce</td>
		<td>8</td>
		<td>TA_FACTURE.DATE_ECH_FACTURE</td>
	</tr>
	<tr>
		<td>17</td>
		<td>Compte collectif</td>
		<td>8</td>
		<td>TA_INFOS_FACTURE.COMPTE</td>
	</tr>
	<tr>
		<td>18</td>
		<td>Nom du tiers</td>
		<td>30</td>
		<td>TA_INFOS_FACTURE.NOM_TIERS</td>
	</tr>
	<tr>
		<td>19</td>
		<td>Adresse du tiers 1ère ligne</td>
		<td>30</td>
		<td>TA_INFOS_FACTURE.ADRESSE1_ADRESSE</td>
	</tr>
	<tr>
		<td>20</td>
		<td>Adresse du tiers 2ème ligne</td>
		<td>30</td>
		<td>TA_INFOS_FACTURE.ADRESSE2_ADRESSE</td>
	</tr>
	<tr>
		<td>21</td>
		<td>Code postal / Adresse</td>
		<td>5</td>
		<td>TA_INFOS_FACTURE.CODEPOSTAL_ADRESSE</td>
	</tr>
	<tr>
		<td>22</td>
		<td>Ville / Adresse</td>
		<td>25</td>
		<td>TA_INFOS_FACTURE.VILLE_ADRESSE</td>
	</tr>
	<tr>
		<td>23</td>
		<td>Exigibilité TVA</td>
		<td>1</td>
		<td>TA_L_FACTURE.CODE_T_TVA</td>
	</tr>
	<tr>
		<td>24</td>
		<td>Date de livraison de la ligne</td>
		<td>8</td>
		<td>TA_FACTURE.DATE_LIV_FACTURE</td>
	</tr>
</table>
 */
public class ExportationEpicea {

	static Logger logger = Logger.getLogger(ExportationEpicea.class.getName());
	
	private String fichierExportation =Platform.getInstanceLocation().getURL().getFile()+"/E2-IMPOR.TXT";
	//	private String fichierExportation = "c:/E2-IMPOR.TXT";
	private String finDeLigne = "\r\n";
	private String ctpEscompteDebit = "665";
	private String ctpEscompteCredit = "765";
	public static final int RE_EXPORT = 1;
    private String messageRetour="";
    private Boolean retour=true;
    private String locationFichier="";
    
    private int reExportAcompte;
    private int reExportAvoir;
    private int reExportFacture;
    private int reExportReglement;
    private int reExportRemise;
    
    private FileWriter fw = null;
    private BufferedWriter bw = null;
    private int numDepart=0;
    private EntityManager em = new TaFactureDAO().getEntityManager();
    
    private List<IDocumentTiers> listeVerif= new LinkedList<IDocumentTiers>();
    
    private List<String> listComplete=new LinkedList<String>();
    private List<IDocumentTiers[]> listCompletePointage= new LinkedList<IDocumentTiers[]>();
    
    
    int numeroDePiece = 0;
    
	String TypeAchat="A";
	String TypeVente="V";
	String TypeReglement="T";
	String TypeOd="O";
	String typePointage="P";
	
	Integer numLignePiece =0;
	String typePiece = null;
	String codePiece = null;
	Date datePiece = new Date();
	String numCptLigne = null;
	String libelleLigne = null;
	Double mtDebitLigne = 0d;
	Double mtCreditLigne = 0d;
	Double qte1 = 0d;
	Double qte2 = 0d;
	String codeTva = null;
	Double tauxTva = 0d;
	Double mtDebitTva = 0d;
	Double mtCreditTva = 0d;
	Date dateEcheance = new Date();
	String cptCollectif = null;
	String nomTiers = null;
	String adresse1Tiers = null;
	String adresse2Tiers = null;
	String codePostalTiers = null;
	String villeTiers = null;
	String exigibiliteTva = null;
	Date dateLivraisonLigne = new Date();

	String refContrepartie = null;		
	Date dateContrepartie  = null;
	Double mtContrepartieDebit = 0d;
	Double mtContrepartieCredit= 0d;
	Double mtAffectation= 0d;

    
	public ExportationEpicea() {}

	//	public void exportObject() {
	//		int debut = 1;
	//		int fin = 3;
	//		try {      
	//			
	//			StringBuffer ligne = new StringBuffer("");
	//			
	//			FileWriter fw = new FileWriter(fichierExportation);
	//			BufferedWriter bw = new BufferedWriter(fw);
	//			char c = ' ';
	//			
	//			Facture f = new Facture(null);
	//			INFOS_FACTURE infosFacture = null;
	//			IB_TA_FACTURE ibTaFacture = new IB_TA_FACTURE(f);
	//			IB_TA_L_FACTURE ibTaLFacture = new IB_TA_L_FACTURE(f);
	//			IB_TA_INFOS_FACTURE ibTaInfosFacture = new IB_TA_INFOS_FACTURE();
	//			ARTICLE article = new ARTICLE();
	//			
	//			//CALCUL_TOTAL_TEMP
	//			CallableStatement cs;
	//			double montantTTC = 0;
	//			
	//			cs = ibTaFacture.getFIBBase().getJdbcConnection().prepareCall("{?,?,?,?,?,?,?,?,?,?,?,? = call CALCUL_TOTAL_DIRECT(?)}");
	//			cs.registerOutParameter(1,Types.DOUBLE);
	//			cs.registerOutParameter(2,Types.DOUBLE);
	//			cs.registerOutParameter(3,Types.DOUBLE);
	//			cs.registerOutParameter(4,Types.DOUBLE);
	//			cs.registerOutParameter(5,Types.DOUBLE);
	//			cs.registerOutParameter(6,Types.DOUBLE);
	//			cs.registerOutParameter(7,Types.DOUBLE);
	//			cs.registerOutParameter(8,Types.DOUBLE);
	//			cs.registerOutParameter(9,Types.DOUBLE);
	//			cs.registerOutParameter(10,Types.DOUBLE);
	//			cs.registerOutParameter(11,Types.DOUBLE);
	//			cs.registerOutParameter(12,Types.DOUBLE);
	//			
	//			for (int i = debut, numPieceExport=1; i <= fin; i++, numPieceExport++) {
	//				
	//				//RECUPERATION DE LA FACTURE
	//				//"Nettoyage" de la facture
	//				f.getEntete().videEntete();
	//				f.getLignes().clear();
	//				
	//				//Recuperation de la facture et de son entete
	//				ibTaFacture.rechercheFacture(i);
	//				ibTaFacture.getQuery_Champ_Obj();
	//				ibTaFacture.remplissageEnteteSurObjetQuery(null,null);
	//				
	//				//Récuperation des lignes de la facture
	//				ibTaLFacture.recupLignesFacture(f.getEntete().getCODE());
	//				
	//				ibTaLFacture.getFIBQuery().close();
	//				ibTaLFacture.getFIBQuery().setQuery(new QueryDescriptor(ibTaLFacture.getFIBBase(),ibTaLFacture.getRequete(),true));
	//				ibTaLFacture.getFIBQuery().open();
	//				ibTaLFacture.getParamSQL().remove(Const.C_ID_FACTURE);
	//				ibTaLFacture.getParamSQL().put(Const.C_ID_FACTURE,new String[]{"=",String.valueOf(((EnteteFacture)f.getEntete()).getID_FACTURE())});
	//				ibTaLFacture.setParamWhereSQL(ibTaLFacture.getParamSQL());
	//				ibTaLFacture.getFIBQuery().setRowId(ibTaLFacture.champIdTable, true);
	//				ibTaLFacture.getFIBQuery().setSort(null);
	//				
	//				if (!ibTaLFacture.getFIBQuery().isEmpty()) {
	//					ibTaLFacture.getFIBQuery().first();
	//					do {
	//						ibTaLFacture.getQuery_Champ_Obj();
	//						ibTaLFacture.initLigneCourantSurRow();
	//						f.addLigne(new LigneFacture(f.getLigneCourante()));
	//						ibTaLFacture.remplissageLigneSurObjetQuery(f.getLigneCourante());
	//					} while(ibTaLFacture.getFIBQuery().next());
	//				}	
	//				
	//				//Recuperation des informations complementaires de la facture
	//				infosFacture = INFOS_FACTURE.trouveINFOS_FACTURE(
	//						((EnteteFacture)f.getEntete()).getID_FACTURE().toString(),
	//						((EnteteFacture)f.getEntete()).getID_TIERS().toString(),
	//						infosFacture);
	//				
	//				//Recuperation des montants calcules de la facture
	//				cs.setInt(13,((EnteteFacture)f.getEntete()).getID_FACTURE());
	//				cs.execute();
	//				montantTTC=LibCalcul.arrondi(cs.getDouble(3),2);
	//				
	//				//ECRITURE DE LA FACTURE DANS LE FICHIER D'EXPORTATION
	//				//Entete
	//				//1
	//				ligne.append(completeChaine(String.valueOf(numPieceExport),5,c,0));
	//				//2
	//				ligne.append(completeChaine("1",3,c,0));
	//				//3
	//				ligne.append(completeChaine("V",1,c,0));
	//				//4
	//				ligne.append(completeChaine(((EnteteFacture)f.getEntete()).getCODE().toString(),9,c,0));
	//				//5
	//				ligne.append(completeChaine(LibDate.dateToStringAA(((EnteteFacture)f.getEntete()).getDATE()),8,c,0));
	//				//6
	//				ligne.append(completeChaine("+"+infosFacture.getInfosTiers().getCODE_COMPTA(),8,c,0));
	//				//7
	//				ligne.append(completeChaine(((EnteteFacture)f.getEntete()).getLIBELLE(),23,c,1));
	//				//8
	//				ligne.append(completeChaine(String.valueOf(montantTTC),12,c,0));
	//				//9
	//				ligne.append(completeChaine("0",12,c,0));
	//				//10
	//				ligne.append(completeChaine("0",9,c,0));
	//				//11
	//				ligne.append(completeChaine("0",9,c,0));
	//				//12
	//				ligne.append(completeChaine("",1,c,0));
	//				//13
	//				ligne.append(completeChaine("0",6,c,0));
	//				//14
	//				ligne.append(completeChaine("0",11,c,0));
	//				//15
	//				ligne.append(completeChaine("0",11,c,0));
	//				//16
	//				ligne.append(completeChaine(LibDate.dateToStringAA(((EnteteFacture)f.getEntete()).getDATE_ECH_FACTURE()),8,c,0));
	//				//17
	//				ligne.append(completeChaine(infosFacture.getInfosTiers().getCOMPTE(),8,c,0));
	//				//18
	//				ligne.append(completeChaine(infosFacture.getInfosTiers().getNOM_TIERS(),30,c,1));
	//				//19
	//				ligne.append(completeChaine(infosFacture.getInfosAdresse().getADRESSE1(),30,c,1));
	//				//20
	//				ligne.append(completeChaine(infosFacture.getInfosAdresse().getADRESSE2(),30,c,1));
	//				//21
	//				ligne.append(completeChaine(infosFacture.getInfosAdresse().getCODEPOSTAL(),5,c,1));
	//				//22
	//				ligne.append(completeChaine(infosFacture.getInfosAdresse().getVILLE(),25,c,1));
	//				//23
	//				ligne.append(completeChaine("",1,c,0));
	//				//24
	//				ligne.append(completeChaine(LibDate.dateToStringAA(((EnteteFacture)f.getEntete()).getDATE_LIV_FACTURE()),8,c,0));
	//				
	//				fw.write(ligne.toString());
	//				ligne.delete(0,ligne.length());
	//				fw.write(finDeLigne);
	//				
	//				//Lignes
	//				for (LigneDocument l : f.getLignes()) {
	//					article = ARTICLE.getARTICLE(((LigneFacture)l).getID_ARTICLE().toString(),article);
	//					if(article!=null) {
	//						//1
	//						ligne.append(completeChaine(((EnteteFacture)f.getEntete()).getID_FACTURE().toString(),5,c,0));
	//						//2
	//						ligne.append(completeChaine(LibConversion.integerToString(l.getNUM_LIGNE()+2),3,c,0));
	//						//3
	//						ligne.append(completeChaine("V",1,c,0));
	//						//4
	//						ligne.append(completeChaine(((EnteteFacture)f.getEntete()).getCODE().toString(),9,c,0));
	//						//5
	//						ligne.append(completeChaine(LibDate.dateToStringAA(((EnteteFacture)f.getEntete()).getDATE()),8,c,0));
	//						//6
	//						ligne.append(completeChaine(article.getNUMCPT_ARTICLE(),8,c,0));
	//						//7
	//						ligne.append(completeChaine(((EnteteFacture)f.getEntete()).getLIBELLE(),23,c,1));
	//						//8
	//						ligne.append(completeChaine("0",12,c,0));
	//						//9
	//						ligne.append(completeChaine(((LigneFacture)l).getMT_HT_L_FACTURE().toString(),12,c,0));
	//						//10
	//						ligne.append(completeChaine(((LigneFacture)l).getQTE_L_FACTURE().toString(),9,c,0));
	//						//11
	//						ligne.append(completeChaine(((LigneFacture)l).getQTE_L_FACTURE().toString(),9,c,0));
	//						//12
	//						ligne.append(completeChaine(((LigneFacture)l).getCODE_TVA_L_FACTURE(),1,c,0));
	//						//13
	//						ligne.append(completeChaine(((LigneFacture)l).getTAUX_TVA_L_FACTURE().toString(),6,c,0));
	//						//14
	//						ligne.append(completeChaine("0",11,c,0));
	//						//15
	//						ligne.append(completeChaine(String.valueOf(LibCalcul.arrondi(((LigneFacture)l).getMT_TTC_L_FACTURE()-((LigneFacture)l).getMT_HT_L_FACTURE())),11,c,0));
	//						//16
	//						ligne.append(completeChaine(LibDate.dateToStringAA(((EnteteFacture)f.getEntete()).getDATE_ECH_FACTURE()),8,c,0));
	//						//17
	//						ligne.append(completeChaine("",8,c,0));
	//						//18
	//						ligne.append(completeChaine("",30,c,1));
	//						//19
	//						ligne.append(completeChaine("",30,c,1));
	//						//20
	//						ligne.append(completeChaine("",30,c,1));
	//						//21
	//						ligne.append(completeChaine("",5,c,1));
	//						//22
	//						ligne.append(completeChaine("",25,c,1));
	//						//23
	//						ligne.append(completeChaine(((LigneFacture)l).getCODE_T_TVA_L_FACTURE(),1,c,0));
	//						//24
	//						ligne.append(completeChaine(LibDate.dateToStringAA(((EnteteFacture)f.getEntete()).getDATE_LIV_FACTURE()),8,c,0));
	//						
	//						fw.write(ligne.toString());
	//						fw.write(finDeLigne);
	//						ligne.delete(0,ligne.length());
	//					} else {
	//						logger.debug("Exporation, ligne de commentaire");
	//					}
	//				}
	//			}
	//			
	//			bw.close();
	//		} catch(IOException ioe){
	//			logger.error("",ioe);
	//		} catch (SQLException e) {
	//			logger.error("",e);
	//		} catch (Exception e) {
	//			logger.error("",e);
	//		}
	//	}

	//	public void exportProcedureStockee() {
	//		int debut = 1;
	//		int fin = 3;
	//		try {      
	//
	//			StringBuffer ligne = new StringBuffer("");
	//
	//			FileWriter fw = new FileWriter(fichierExportation);
	//			BufferedWriter bw = new BufferedWriter(fw);
	//			char c = ' ';
	//			ResultSet rs = null;
	//
	//			//IB_TA_FACTURE ibTaFacture = new IB_TA_FACTURE(new Facture(null));
	//			//créer une connection à part pour éviter d'avoir la même connection que les factures
	//			//en cours de saisie ou de modification dans perspective "Facture"
	//			Database base =new Database();
	//			base.setConnection(new com.borland.dx.sql.dataset.ConnectionDescriptor(
	//					Const.C_URL_BDD+Const.C_FICHIER_BDD,Const.C_USER,Const.C_PASS , false, Const.C_DRIVER_JDBC));
	//			base.setAutoCommit(false);
	//
	//			//CallableStatement cs;
	//			PreparedStatement ps;
	//			Integer un = null;
	//			Integer deux = null;
	//			String trois = null;
	//			String quatre = null;
	//			Date cinq = new Date();
	//			String six = null;
	//			String sept = null;
	//			Double huit = null;
	//			Double neuf = null;
	//			Double dix = null;
	//			Double onze = null;
	//			String douze = null;
	//			Double treize = null;
	//			Double quatorze = null;
	//			Double quinze = null;
	//			Date seize = new Date();
	//			String dixsept = null;
	//			String dixhuit = null;
	//			String dixneuf = null;
	//			String vingt = null;
	//			String vingtetun = null;
	//			String vingtdeux = null;
	//			String vingttrois = null;
	//			Date vingtquatre = new Date();
	//			Const.setFichierToutVenant(Const.C_FICHIER_INI_TOUTVENANT);
	//
	//
	//			//			ps = ibTaFacture.getFIBBase().getJdbcConnection().prepareStatement("select * from EXPORT_EPICEA("+Const.C_CPT_ESCOMPTE_DEBIT+","+Const.C_CPT_ESCOMPTE_CREDIT+")");
	//			ps = base.getJdbcConnection().prepareStatement("select * from EXPORT_EPICEA("+Const.C_CPT_ESCOMPTE_DEBIT+","+Const.C_CPT_ESCOMPTE_CREDIT+")");
	//			ps.execute();
	//
	//			do {
	//				rs = ps.getResultSet();
	//				while(rs.next()){
	//					//Recuperation des montants calcules de la facture
	//					un = rs.getInt(1);
	//					deux = rs.getInt(2);
	//					trois = rs.getString(3);
	//					quatre = rs.getString(4);
	//					cinq.setTime(rs.getTimestamp(5).getTime());
	//					six =rs.getString(6);
	//					sept = rs.getString(7);
	//					huit = rs.getDouble(8);
	//					neuf = rs.getDouble(9);
	//					dix = rs.getDouble(10);
	//					onze = rs.getDouble(11);
	//					douze = rs.getString(12);
	//					treize = rs.getDouble(13);
	//					quatorze = rs.getDouble(14);
	//					quinze = rs.getDouble(15);
	//					seize.setTime(rs.getTimestamp(16).getTime());
	//					dixsept = rs.getString(17);
	//					dixhuit = rs.getString(18);
	//					dixneuf = rs.getString(19);
	//					vingt = rs.getString(20);
	//					vingtetun = rs.getString(21);
	//					vingtdeux = rs.getString(22);
	//					vingttrois = rs.getString(23);
	//					vingtquatre.setTime(rs.getTimestamp(24).getTime());
	//
	//					//ECRITURE DE LA FACTURE DANS LE FICHIER D'EXPORTATION
	//					//Entete
	//					//1
	//					ligne.append(completeChaine(String.valueOf(un),5,c,0));
	//					//2
	//					ligne.append(completeChaine(String.valueOf(deux),3,c,0));
	//					//3
	//					ligne.append(completeChaine(String.valueOf(trois),1,c,0));
	//					//4
	//					ligne.append(completeChaine(String.valueOf(quatre),9,c,0));
	//					//5
	//					ligne.append(completeChaine(LibDate.dateToStringAA(cinq),8,c,0));
	//					//6
	//					ligne.append(completeChaine(String.valueOf(six),8,c,0));
	//					//7
	//					ligne.append(completeChaine(String.valueOf(sept),23,c,1));
	//					//8
	//					ligne.append(completeChaine(String.valueOf(huit),12,c,0));
	//					//9
	//					ligne.append(completeChaine(String.valueOf(neuf),12,c,0));
	//					//10
	//					ligne.append(completeChaine(String.valueOf(dix),9,c,0));
	//					//11
	//					ligne.append(completeChaine(String.valueOf(onze),9,c,0));
	//					//12
	//					ligne.append(completeChaine(String.valueOf(douze),1,c,0));
	//					//13
	//					ligne.append(completeChaine(String.valueOf(treize),6,c,0));
	//					//14
	//					ligne.append(completeChaine(String.valueOf(quatorze),11,c,0));
	//					//15
	//					ligne.append(completeChaine(String.valueOf(quinze),11,c,0));
	//					//16
	//					ligne.append(completeChaine(LibDate.dateToStringAA(seize),8,c,0));
	//					//17
	//					ligne.append(completeChaine(String.valueOf(dixsept),8,c,0));
	//					//18
	//					ligne.append(completeChaine(String.valueOf(dixhuit),30,c,1));
	//					//19
	//					ligne.append(completeChaine(String.valueOf(dixneuf),30,c,1));
	//					//20
	//					ligne.append(completeChaine(String.valueOf(vingt),30,c,1));
	//					//21
	//					ligne.append(completeChaine(String.valueOf(vingtetun),5,c,1));
	//					//22
	//					ligne.append(completeChaine(String.valueOf(vingtdeux),25,c,1));
	//					//23
	//					ligne.append(completeChaine(String.valueOf(vingttrois),1,c,0));
	//					//24
	//					ligne.append(completeChaine(LibDate.dateToStringAA(vingtquatre),8,c,0));
	//
	//					fw.write(ligne.toString());
	//					ligne.delete(0,ligne.length());
	//					fw.write(finDeLigne);
	//				}
	//				rs.close();
	//			} while(ps.getMoreResults());
	//
	//			bw.close();
	//			fw.close();
	//			base.closeConnection();
	//		} catch(IOException ioe){
	//			logger.error("",ioe);
	//		} catch (SQLException e) {
	//			logger.error("",e);
	//		} catch (Exception e) {
	//			logger.error("",e);
	//		}
	//	}

	//	public void exportProcedureStockee(String[] idFactureAExporter,IProgressMonitor monitor) {
	//		exportProcedureStockee(idFactureAExporter,monitor,0);
	//	}

	//public void exportJPA(String[] idFactureAExporter,String[] idAvoirAExporter,IProgressMonitor monitor) {
	public void  exportJPA(List<TaFacture> idFactureAExporter,List<TaAvoir> idAvoirAExporter,List<TaApporteur> idApporteurAExporter,List<TaAcompte> 
	idAcompteAExporter,List<TaReglement> idReglementAExporter,List<TaRemise> idRemiseAExporter,IProgressMonitor monitor,boolean gererPointages
	, boolean centralisation) throws Exception {
		 exportJPA(idFactureAExporter,idAvoirAExporter,idApporteurAExporter,idAcompteAExporter,idReglementAExporter,idRemiseAExporter,
				monitor,0,0,0,0,0,0,gererPointages,centralisation);
	}

	//	public void exportProcedureStockee(String[] idFactureAExporter,IProgressMonitor monitor, int reExport) {
	//		try {      
	//
	//			StringBuffer ligne = new StringBuffer("");
	//
	//			FileWriter fw = new FileWriter(fichierExportation);
	//			BufferedWriter bw = new BufferedWriter(fw);
	//			char c = ' ';
	//			ResultSet rs = null;
	//
	//			//IB_TA_FACTURE ibTaFacture = new IB_TA_FACTURE(new Facture(null));
	//
	//			//créer une connection à part pour éviter d'avoir la même connection que les factures
	//			//en cours de saisie ou de modification dans perspective "Facture"
	//			Database base =new Database();
	//			base.setConnection(new com.borland.dx.sql.dataset.ConnectionDescriptor(
	//					Const.C_URL_BDD+Const.C_FICHIER_BDD,Const.C_USER,Const.C_PASS , false, Const.C_DRIVER_JDBC));
	//			base.setAutoCommit(false);
	//
	//			//CallableStatement cs;
	//			PreparedStatement ps;
	//			Integer un = null;
	//			Integer deux = null;
	//			String trois = null;
	//			String quatre = null;
	//			Date cinq = new Date();
	//			String six = null;
	//			String sept = null;
	//			Double huit = null;
	//			Double neuf = null;
	//			Double dix = null;
	//			Double onze = null;
	//			String douze = null;
	//			Double treize = null;
	//			Double quatorze = null;
	//			Double quinze = null;
	//			Date seize = new Date();
	//			String dixsept = null;
	//			String dixhuit = null;
	//			String dixneuf = null;
	//			String vingt = null;
	//			String vingtetun = null;
	//			String vingtdeux = null;
	//			String vingttrois = null;
	//			Date vingtquatre = new Date();
	//
	//			boolean nouvellePiece = false;
	//			int numeroDePiece = 0;
	//			for(int i=0; i<idFactureAExporter.length; i++) {
	//				nouvellePiece = true;
	//				//	ps = ibTaLFacture.getFIBBase().getJdbcConnection().prepareStatement("select * from EXPORT_EPICEA_2("+idFactureAExporter[i]+","+reExport+")");
	//
	//				//  ps = ibTaLFacture.getFIBBase().getJdbcConnection().prepareStatement("select * from EXPORT_EPICEA("+Const.C_CPT_ESCOMPTE_DEBIT+","+Const.C_CPT_ESCOMPTE_CREDIT+")");
	//				//	ps = ibTaFacture.getFIBBase().getJdbcConnection().prepareStatement("select * from EXPORT_EPICEA(665,765,"+idFactureAExporter[i]+","+reExport+")");
	//				ps = base.getJdbcConnection().prepareStatement("select * from EXPORT_EPICEA(665,765,"+idFactureAExporter[i]+","+reExport+")");
	//				ps.execute();
	//
	//				do {
	//					rs = ps.getResultSet();
	//					while(rs.next()){
	//						if(nouvellePiece) {
	//							numeroDePiece++;
	//							nouvellePiece = false;
	//						}
	//						un = numeroDePiece;//rs.getInt(1);
	//						deux = rs.getInt(2);
	//						trois = rs.getString(3);
	//						quatre = rs.getString(4);
	//						cinq.setTime(rs.getTimestamp(5).getTime());
	//						six =rs.getString(6);
	//						sept = rs.getString(7);
	//						huit = rs.getDouble(8);
	//						neuf = rs.getDouble(9);
	//						dix = rs.getDouble(10);
	//						onze = rs.getDouble(11);
	//						douze = rs.getString(12);
	//						treize = rs.getDouble(13);
	//						quatorze = rs.getDouble(14);
	//						quinze = rs.getDouble(15);
	//						seize.setTime(rs.getTimestamp(16).getTime());
	//						dixsept = rs.getString(17);
	//						dixhuit = rs.getString(18);
	//						dixneuf = rs.getString(19);
	//						vingt = rs.getString(20);
	//						vingtetun = rs.getString(21);
	//						vingtdeux = rs.getString(22);
	//						vingttrois = rs.getString(23);
	//						vingtquatre.setTime(rs.getTimestamp(24).getTime());
	//
	//
	//						//ECRITURE DE LA FACTURE DANS LE FICHIER D'EXPORTATION
	//						//Entete
	//						//1
	//						ligne.append(completeChaine(String.valueOf(un),5,c,0));
	//						//2
	//						ligne.append(completeChaine(String.valueOf(deux),3,c,0));
	//						//3
	//						ligne.append(completeChaine(String.valueOf(trois),1,c,0));
	//						//4
	//						ligne.append(completeChaine(String.valueOf(quatre),9,c,0));
	//						//5
	//						ligne.append(completeChaine(LibDate.dateToStringAA(cinq),8,c,0));
	//						//6
	//						ligne.append(completeChaine(String.valueOf(six),8,c,0));
	//						//7
	//						ligne.append(completeChaine(String.valueOf(sept),23,c,1));
	//						//8
	//						ligne.append(completeChaine(String.valueOf(huit),12,c,0));
	//						//9
	//						ligne.append(completeChaine(String.valueOf(neuf),12,c,0));
	//						//10
	//						ligne.append(completeChaine(String.valueOf(dix),9,c,0));
	//						//11
	//						ligne.append(completeChaine(String.valueOf(onze),9,c,0));
	//						//12
	//						ligne.append(completeChaine(String.valueOf(douze),1,c,0));
	//						//13
	//						ligne.append(completeChaine(String.valueOf(treize),6,c,0));
	//						//14
	//						ligne.append(completeChaine(String.valueOf(quatorze),11,c,0));
	//						//15
	//						ligne.append(completeChaine(String.valueOf(quinze),11,c,0));
	//						//16
	//						ligne.append(completeChaine(LibDate.dateToStringAA(seize),8,c,0));
	//						//17
	//						ligne.append(completeChaine(String.valueOf(dixsept),8,c,0));
	//						//18
	//						ligne.append(completeChaine(String.valueOf(dixhuit),30,c,1));
	//						//19
	//						ligne.append(completeChaine(String.valueOf(dixneuf),30,c,1));
	//						//20
	//						ligne.append(completeChaine(String.valueOf(vingt),30,c,1));
	//						//21
	//						ligne.append(completeChaine(String.valueOf(vingtetun),5,c,1));
	//						//22
	//						ligne.append(completeChaine(String.valueOf(vingtdeux),25,c,1));
	//						//23
	//						ligne.append(completeChaine(String.valueOf(vingttrois),1,c,0));
	//						//24
	//						ligne.append(completeChaine(LibDate.dateToStringAA(vingtquatre),8,c,0));
	//
	//						fw.write(ligne.toString());
	//						ligne.delete(0,ligne.length());
	//						fw.write(finDeLigne);
	//					}
	//					rs.close();
	//				} while(ps.getMoreResults());
	//				if(monitor!=null) monitor.worked(1);
	//			}
	//
	//			bw.close();
	//			fw.close();
	//			base.closeConnection();
	//		} catch(IOException ioe){
	//			logger.error("",ioe);
	//		} catch (SQLException e) {
	//			logger.error("",e);
	//		} catch (Exception e) {
	//			logger.error("",e);
	//		}
	//	}

	//public void exportJPA(String[] idFactureAExporter,String[] idAvoirAExporter,IProgressMonitor monitor, int reExport) {
	public void exportJPA(List<TaFacture> idFactureAExporter,List<TaAvoir> idAvoirAExporter,List<TaApporteur> idApporteurAExporter,List<TaAcompte> idAcompteAExporter,
			List<TaReglement> idReglementAExporter,List<TaRemise> idRemiseAExporter,IProgressMonitor monitor, 
			int reExportFacture,int reExportAvoir,int reExportApporteur,int reExportAcompte,int reExportReglement,int reExportRemise,boolean gererPointages, boolean centralisation) throws Exception {
		int nb=0;
		if(idFactureAExporter!=null)nb=idFactureAExporter.size();
		if(idAvoirAExporter!=null)nb=idAvoirAExporter.size();
		if(idApporteurAExporter!=null)nb=idApporteurAExporter.size();
		if(idAcompteAExporter!=null)nb=idAcompteAExporter.size();
		if(idReglementAExporter!=null)nb=idReglementAExporter.size();
		if(idRemiseAExporter!=null)nb=idRemiseAExporter.size();

		if(nb==0)this.setMessageRetour("Il n'y a aucun document à exporter !!!");
		if(ExportationPlugin.getDefault().getPreferenceStoreProject().getString(PreferenceConstants.REPERTOIRE_EXPORTATION)!=null)
			fichierExportation =ExportationPlugin.getDefault().getPreferenceStoreProject().getString(PreferenceConstants.REPERTOIRE_EXPORTATION)+"/E2-IMPOR.TXT";
		
		final boolean annuler[] = new boolean[]{false};
		if(new File(fichierExportation).exists()) {
			//Le fichier existe déjà
			PlatformUI.getWorkbench().getDisplay().syncExec( new Runnable() {

					@Override
					public void run() {
						annuler[0] = ! MessageDialog.openQuestion(
									PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(),
									"Bureau de gestion",
//									"Attention, des documents sont en attente d'importation en comptabilité.\n" +
//									"Si vous continuez l'exportation, la liste des documents déjà en attente d'importation sera réinitialisée.\n\n" +
//									"Annuler l'exportation en cours ?");
									"Attention, des documents sont en attente d'importation en comptabilité.\n" +
									"Si vous continuez l'exportation en cours, cette liste de documents en attente\n" +
									"d'importation sera supprimée.\n" +
									"\n" +
									"Continuer l'exportation en cours ?");
						
					}
					} );
			
		}
		
		if(!annuler[0]) {
			
			fw = null;
			bw = null;
			TaFactureDAO dao =new TaFactureDAO();
			em = dao.getEntityManager();
			EntityTransaction transaction = dao.getEntityManager().getTransaction();
	
			try {      
				String ctpEscompteDebit = "665";
				String ctpEscompteCredit = "765";
				setReExportAcompte(reExportAcompte);
				setReExportAvoir(reExportAvoir);
				setReExportFacture(reExportFacture);
				setReExportReglement(reExportReglement);
				setReExportRemise(reExportRemise);
	
				fw = new FileWriter(fichierExportation);
				File reportFile = new File(fichierExportation);
				setLocationFichier(reportFile.getAbsolutePath());
				bw = new BufferedWriter(fw);
				numDepart=0;
				dao.begin(transaction);
				if(idFactureAExporter!=null) {
					if(centralisation){
						numDepart=exportFactureCentraliseeJPA(idFactureAExporter,listComplete, fw, monitor, reExportFacture,numDepart,gererPointages,null,em,
								ExportationPlugin.getDefault().getPreferenceStoreProject().getBoolean(PreferenceConstants.REGLEMENTS_LIES));
					}else{
						numDepart=exportFactureJPA(idFactureAExporter,listComplete, fw, monitor, reExportFacture,numDepart,gererPointages,null,em,
								ExportationPlugin.getDefault().getPreferenceStoreProject().getBoolean(PreferenceConstants.REGLEMENTS_LIES));
					}
				}
				
				if(idAvoirAExporter!=null) {
					if(centralisation){
						numDepart=exportAvoirCentraliseeJPA(idAvoirAExporter,listComplete, fw, monitor, reExportAvoir,numDepart,gererPointages,null,em,
								ExportationPlugin.getDefault().getPreferenceStoreProject().getBoolean(PreferenceConstants.DOCUMENTS_LIES));					
					}else{
					numDepart=exportAvoirJPA(idAvoirAExporter,listComplete, fw, monitor, reExportAvoir,numDepart,gererPointages,null,em,
							ExportationPlugin.getDefault().getPreferenceStoreProject().getBoolean(PreferenceConstants.DOCUMENTS_LIES));
					}
				}
				if(idApporteurAExporter!=null) {
					if(centralisation){
						numDepart=exportApporteurCentraliseeJPA(idApporteurAExporter,listComplete, fw, monitor, reExportApporteur,numDepart,gererPointages,null,em,
								ExportationPlugin.getDefault().getPreferenceStoreProject().getBoolean(PreferenceConstants.DOCUMENTS_LIES));					
					}else{
					numDepart=exportApporteurJPA(idApporteurAExporter,listComplete, fw, monitor, reExportApporteur,numDepart,gererPointages,null,em,
							ExportationPlugin.getDefault().getPreferenceStoreProject().getBoolean(PreferenceConstants.DOCUMENTS_LIES));
					}
				}	
				if(idAcompteAExporter!=null) {
					numDepart=exportAcompteJPA(idAcompteAExporter,listComplete, fw, monitor, reExportAcompte,numDepart,gererPointages,null,em,
							ExportationPlugin.getDefault().getPreferenceStoreProject().getBoolean(PreferenceConstants.DOCUMENTS_LIES));
				}
				
				if(idReglementAExporter!=null) {
					numDepart=exportReglementJPA(idReglementAExporter,listComplete, fw, monitor, reExportReglement,numDepart,gererPointages,null,em,
							ExportationPlugin.getDefault().getPreferenceStoreProject().getBoolean(PreferenceConstants.DOCUMENTS_LIES));
				}
				
				if(idRemiseAExporter!=null) {
					numDepart=exportRemiseJPA(idRemiseAExporter,listComplete, fw, monitor, reExportRemise,numDepart,gererPointages,null,em,
							ExportationPlugin.getDefault().getPreferenceStoreProject().getBoolean(PreferenceConstants.DOCUMENTS_LIES));
				}
				final List<IDocumentTiers> listeDocumentsManquants =verifCoherenceExportation();
				final List elementsToSelect = new LinkedList<String>();
				for (IDocumentTiers o : listeDocumentsManquants) {
					elementsToSelect.add(o.getCodeDocument());
					o.setAccepte(false);
				}
				if(elementsToSelect.size()>0){
	            Display.getDefault().syncExec(new Runnable() {
	                @Override
	                public void run() {               	
	                	
	                	 ListSelectionDialog dlg =
	                		   new ListSelectionDialog(
	                				   PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(),
	                				   elementsToSelect,
	                				   new ArrayContentProvider(), 
	                			        new LabelProvider(),
	                		                 "Les documents suivants sont manquant, sélectionnez ceux que vous voulez rajouter à l'exportation ?");
	                	 dlg.setTitle("Documents manquant dans l'exportation"); //$NON-NLS-1$
	                	 dlg.setInitialElementSelections(elementsToSelect);
	                	 dlg.open();
	                	 for (Object b : dlg.getResult()) {
	                		 for (IDocumentTiers doc : listeDocumentsManquants) {
								if(doc.getCodeDocument().equals(b))doc.setAccepte(true);
							}
						} 
	
	                }});
				}
	            for (IDocumentTiers doc : listeDocumentsManquants) {
					if(doc.getAccepte()){
						if(doc.getTypeDocument()==TaFacture.TYPE_DOC){
							idFactureAExporter = new  LinkedList<TaFacture>();
							idFactureAExporter.add((TaFacture)doc);
							numDepart=exportFactureJPA(idFactureAExporter,listComplete, fw, monitor, reExportFacture,numDepart,false,null,em,false);
						}
						if(doc.getTypeDocument()==TaAvoir.TYPE_DOC){
							idAvoirAExporter=new LinkedList<TaAvoir>();
							idAvoirAExporter.add((TaAvoir)doc);
							numDepart=exportAvoirJPA(idAvoirAExporter,listComplete, fw, monitor, reExportAvoir,numDepart,false,null,em,false);
						}
						if(doc.getTypeDocument()==TaApporteur.TYPE_DOC){
							idApporteurAExporter=new LinkedList<TaApporteur>();
							idApporteurAExporter.add((TaApporteur)doc);
							numDepart=exportApporteurJPA(idApporteurAExporter,listComplete, fw, monitor, reExportApporteur,numDepart,false,null,em,false);
						}						
						if(doc.getTypeDocument()==TaAcompte.TYPE_DOC){
							idAcompteAExporter = new LinkedList<TaAcompte>();
							idAcompteAExporter.add((TaAcompte)doc);
							numDepart=exportAcompteJPA(idAcompteAExporter,listComplete, fw, monitor, reExportAcompte,numDepart,false,null,em,false);
						}
						if(doc.getTypeDocument()==TaReglement.TYPE_DOC){
							idReglementAExporter = new  LinkedList<TaReglement>();
							idReglementAExporter.add((TaReglement)doc);
							numDepart=exportReglementJPA(idReglementAExporter,listComplete, fw, monitor, reExportReglement,numDepart,false,null,em,false);
						}
						if(doc.getTypeDocument()==TaRemise.TYPE_DOC){
							idRemiseAExporter = new LinkedList<TaRemise>();
							idRemiseAExporter.add((TaRemise)doc);
							numDepart=exportRemiseJPA(idRemiseAExporter,listComplete, fw, monitor, reExportRemise,numDepart,false,null,em,false);
						}
					}
				}
	            if(retour)dao.commit(transaction);
	            else throw new Exception();
				if(bw!=null) bw.close();
				if(fw!=null) fw.close();
				
	
	
			} catch(IOException ioe){
				transaction.rollback();
				logger.error("",ioe);
			} 
			catch (Exception e) {
				transaction.rollback();
				if(bw!=null) bw.close();
				if(fw!=null) fw.close();
				logger.error("",e);
				effaceFichierTexte(fichierExportation);
				throw e;
			}
		} else {
			setMessageRetour("Exportation annulée.");
		}
	}
	
	public List<IDocumentTiers> verifCoherenceExportation(){
		List<IDocumentTiers> listeRetour = new LinkedList<IDocumentTiers>();
		for (IDocumentTiers[] tab : listCompletePointage) {
			IDocumentTiers docDebit =tab[0];
			IDocumentTiers docCredit=tab[1];

			if(listeVerif.indexOf(docDebit)!=0 && (docDebit.getExport()==0 ||docDebit.getExport()==null)){
				if(listeRetour.indexOf(docDebit)==-1)
					listeRetour.add(docDebit);
			}
			if(listeVerif.indexOf(docCredit)!=0 && (docCredit.getExport()==0 ||docCredit.getExport()==null)){
				if(listeRetour.indexOf(docCredit)== -1)
					listeRetour.add(docCredit);
			}
		}
		return listeRetour;
	}
	
	public void effaceFichierTexte(String chemin) {
		File f = new File(chemin);
		f.delete();
	}
	
	//public void exportFactureJPA(String[] idFactureAExporter,FileWriter fw,IProgressMonitor monitor, int reExport) {
	public int exportFactureJPA(List<TaFacture> idFactureAExporter,List<String> listComplete,FileWriter fw,
			IProgressMonitor monitor, int reExport,int numDepart,boolean gererPointages,Integer idAppelant,EntityManager em,boolean gererLesReglements) throws Exception {
		int numFin=numDepart;
		TaFactureDAO taFactureDAO = new TaFactureDAO(em);
//		EntityTransaction transaction = taFactureDAO.getEntityManager().getTransaction();
		try { 


			TaFacture taFacture = null;
			TaRemiseDAO taRemiseDao = new TaRemiseDAO(em);


			numeroDePiece = numFin;


			boolean nouvellePiece = false;


//			taFactureDAO.begin(transaction);

			//for(int i=0; i<idFactureAExporter.length; i++) {
			for(int i=0; i<idFactureAExporter.size(); i++) {
				//taFacture = taFactureDAO.findById(LibConversion.stringToInteger(idFactureAExporter[i]));
				taFacture = idFactureAExporter.get(i);
				if(listComplete.indexOf(taFacture.getCodeDocument())==-1){

					/**/   // pour compenser les problèmes d'arrondis mis sur des lignes à 0
					taFacture.calculTvaTotal();
					/**/  //isa le 03 février 2011 en accord avec Nicolas


					if(taFacture.getExport()!=1 || reExport==1) { /* facture pas deja exportee ou reexportation */
						nouvellePiece = true;
						//taFacture.calculeTvaEtTotaux();

						if(nouvellePiece) {
							numeroDePiece++;
							nouvellePiece = false;
							numFin++;
						}

						//recuperation des informations propre a la facture
						codePiece = taFacture.getCodeDocument();
						datePiece = taFacture.getDateDocument();
						dateEcheance = taFacture.getDateEchDocument();
						dateLivraisonLigne = taFacture.getDateDocument();//changer pour erreur 18000 dans epicéa;
//						dateLivraisonLigne = taFacture.getDateLivDocument();
						libelleLigne = taFacture.getTaInfosDocument().getNomTiers();
						numCptLigne = taFacture.getTaInfosDocument().getCodeCompta();
						cptCollectif = taFacture.getTaInfosDocument().getCompte();
						nomTiers = taFacture.getTaInfosDocument().getNomTiers();
						adresse1Tiers = taFacture.getTaInfosDocument().getAdresse1();
						adresse2Tiers = taFacture.getTaInfosDocument().getAdresse2();
						codePostalTiers = taFacture.getTaInfosDocument().getCodepostal();
						villeTiers = taFacture.getTaInfosDocument().getVille();
						//					ttc = taFacture.getTtc();
						//					txRemHtFacture = taFacture.getRemHtFacture();
						//					exportComtpa = taFacture.getExport();

						mtDebitLigne = LibConversion.BigDecimalToDouble(taFacture.getNetTtcCalc());  

						numCptLigne = "+"+taFacture.getTaInfosDocument().getCodeCompta();    
						numLignePiece = 1;
						typePiece = TypeVente;
						mtCreditLigne = 0d;
						qte1 = 0d;
						qte2 = 0d;
						codeTva ="";
						tauxTva = 0d;
						mtDebitTva = 0d;
						mtCreditTva = 0d;
						exigibiliteTva = "";

						refContrepartie = "";
						dateContrepartie = null;
						mtContrepartieDebit = 0d;
						mtContrepartieCredit = 0d;
						mtAffectation = 0d;

						//ecriture de la ligne d'entete dans le fichier d'export
						fw.write(creationLigne(numeroDePiece,
								numLignePiece ,
								typePiece ,
								codePiece ,
								datePiece ,
								numCptLigne ,
								libelleLigne ,
								mtDebitLigne ,
								mtCreditLigne ,
								qte1 ,
								qte2 ,
								codeTva ,
								tauxTva ,
								mtDebitTva ,
								mtCreditTva ,
								dateEcheance ,
								cptCollectif ,
								nomTiers ,
								adresse1Tiers ,
								adresse2Tiers ,
								codePostalTiers ,
								villeTiers ,
								exigibiliteTva ,
								dateLivraisonLigne,
								refContrepartie,
								dateContrepartie,
								mtContrepartieDebit,
								mtContrepartieCredit,
								mtAffectation));
						fw.write(finDeLigne);

						//réinitialisation des variables pour le prochain document
						cptCollectif ="";
						nomTiers ="";
						adresse1Tiers ="";
						adresse2Tiers ="";
						codePostalTiers ="";
						villeTiers="";

						BigDecimal escompte = taFacture.getRemTtcDocument();
						if (escompte.signum() > 0) { 
							//création d'une lignes d'escompte
							numCptLigne = ctpEscompteDebit;
							numLignePiece = numLignePiece + 1;
							typePiece = "V";
							libelleLigne = "Escompte "+taFacture.getLibelleDocument();
							mtDebitLigne = LibConversion.BigDecimalToDouble(escompte);
							mtCreditLigne = 0d;
							qte1 = 0d;
							qte2 = 0d;
							codeTva = "";
							tauxTva = 0d;
							mtDebitTva = 0d;
							mtCreditTva = 0d;
							exigibiliteTva = "";
							//ecriture de la ligne d'escompte dans le fichier d'export

							refContrepartie = "";
							dateContrepartie = null;
							mtContrepartieDebit = 0d;
							mtContrepartieCredit = 0d;
							mtAffectation = 0d;

							fw.write(creationLigne(numeroDePiece,
									numLignePiece ,
									typePiece ,
									codePiece ,
									datePiece ,
									numCptLigne ,
									libelleLigne ,
									mtDebitLigne ,
									mtCreditLigne ,
									qte1 ,
									qte2 ,
									codeTva ,
									tauxTva ,
									mtDebitTva ,
									mtCreditTva ,
									dateEcheance ,
									cptCollectif ,
									nomTiers ,
									adresse1Tiers ,
									adresse2Tiers ,
									codePostalTiers ,
									villeTiers ,
									exigibiliteTva ,
									dateLivraisonLigne,
									refContrepartie,
									dateContrepartie,
									mtContrepartieDebit,
									mtContrepartieCredit,
									mtAffectation));
							fw.write(finDeLigne);
						}

						//traitement des lignes
						for (TaLFacture lf : taFacture.getLignes()) {
							//récupération des valeurs propres à la ligne                 
							if(lf.getTaArticle()!=null   && lf.getMtHtLApresRemiseGlobaleDocument()!=null && lf.getMtHtLApresRemiseGlobaleDocument().signum()!=0){
								libelleLigne = lf.getLibLDocument();
								qte1 = LibConversion.BigDecimalToDouble(lf.getQteLDocument());
								qte2 = LibConversion.BigDecimalToDouble(lf.getQte2LDocument());
								numCptLigne = lf.getCompteLDocument();
								if(numCptLigne==null || numCptLigne.equals("")){
									//if(!transaction.isActive()){
									//transaction.rollback();
									//}
									setRetour(false);
									setMessageRetour("\nErreur N° 1\n"
											+"Article (code article : "+lf.getTaArticle().getCodeArticle()+") sans compte dans le document "+taFacture.getCodeDocument());
									throw new Exception("\nErreur N° 1\n"
											+"Article (code article : "+lf.getTaArticle().getCodeArticle()+") sans compte dans le document "+taFacture.getCodeDocument());
								}
								
								//traitement du type de tva du tiers (F ou ue ou horsUE ou Franchise)
//								if(taFacture.getTaTiers()!=null && taFacture.getTaTiers().getTaTTvaDoc()!=null){
									TaTTvaDocDAO daoTvaDoc = new TaTTvaDocDAO(em);
									String codeDoc="";
									if(taFacture.getTaInfosDocument().getCodeTTvaDoc()==null || taFacture.getTaInfosDocument().getCodeTTvaDoc().equals(""))
										codeDoc="F";
									else codeDoc=taFacture.getTaInfosDocument().getCodeTTvaDoc();
									TaTTvaDoc tvaDoc =daoTvaDoc.findByCode(codeDoc);
									String journalTva="";
									if(tvaDoc!=null)journalTva=tvaDoc.getJournalTTvaDoc();
									//si Franchise pas de code tva sur ligne sans code
									// si autre, L ou E sur compte 7 sauf compte 609
									if(codeDoc.equalsIgnoreCase("F"))
										codeTva = correspondanceCodeTva(codeDoc,lf.getCodeTvaLDocument(),lf.getCodeTvaLDocument(),numCptLigne,lf.getMtTtcLApresRemiseGlobaleDocument().subtract(lf.getMtHtLApresRemiseGlobaleDocument()));
									else
										codeTva = correspondanceCodeTva(codeDoc,lf.getCodeTvaLDocument(),journalTva,numCptLigne,lf.getMtTtcLApresRemiseGlobaleDocument().subtract(lf.getMtHtLApresRemiseGlobaleDocument()));	
//								}else
//									codeTva = lf.getCodeTvaLDocument();
									
								if(codeTva==null)codeTva="";
								if(lf.getTaArticle().getTaTTva()!=null)  
									exigibiliteTva = lf.getTaArticle().getTaTTva().getCodeTTva();
								if (exigibiliteTva.equals(""))exigibiliteTva="D";
								tauxTva = LibConversion.BigDecimalToDouble(lf.getTauxTvaLDocument());
								mtCreditTva = LibConversion.BigDecimalToDouble(lf.getMtTtcLApresRemiseGlobaleDocument().subtract(lf.getMtHtLApresRemiseGlobaleDocument()));
								mtCreditLigne = LibConversion.BigDecimalToDouble(lf.getMtHtLApresRemiseGlobaleDocument());

								numLignePiece = numLignePiece + 1;
								mtDebitLigne = 0d;
								mtDebitTva = 0d;
								cptCollectif = "";
								nomTiers = "";
								adresse1Tiers = "";
								adresse2Tiers = "";
								codePostalTiers = "";
								villeTiers = "";
								//ecriture de la ligne dans le fichier d'export

								refContrepartie = "";
								dateContrepartie = null;
								mtContrepartieDebit = 0d;
								mtContrepartieCredit = 0d;
								mtAffectation = 0d;

								fw.write(creationLigne(numeroDePiece,
										numLignePiece ,
										typePiece ,
										codePiece ,
										datePiece ,
										numCptLigne ,
										libelleLigne ,
										mtDebitLigne ,
										mtCreditLigne ,
										qte1 ,
										qte2 ,
										codeTva ,
										tauxTva ,
										mtDebitTva ,
										mtCreditTva ,
										dateEcheance ,
										cptCollectif ,
										nomTiers ,
										adresse1Tiers ,
										adresse2Tiers ,
										codePostalTiers ,
										villeTiers ,
										exigibiliteTva ,
										dateLivraisonLigne,
										refContrepartie,
										dateContrepartie,
										mtContrepartieDebit,
										mtContrepartieCredit,
										mtAffectation));
								fw.write(finDeLigne);
							}
						}

						if(gererLesReglements){

							List<TaRemise> listeRemise = new LinkedList<TaRemise>();
							//récupérer tous les documents directement liés à cette facture
							if(ExportationPlugin.getDefault().getPreferenceStoreProject().getBoolean(PreferenceConstants.ACOMPTES)){
								for (TaRAcompte rAcompte : taFacture.getTaRAcomptes()) {
									List<TaAcompte> liste=new LinkedList<TaAcompte>();
									List<TaRemise> listeRemiseAcompte =new LinkedList<TaRemise>();
									listeRemiseAcompte=taRemiseDao.findSiAcompteDansRemise(rAcompte.getTaAcompte().getCodeDocument());
									if(listeRemiseAcompte.size()==0 && ExportationPlugin.getDefault().getPreferenceStoreProject().getBoolean(PreferenceConstants.REGLEMENT_SIMPLE)){
										liste.add(rAcompte.getTaAcompte());
										numFin=exportAcompteJPA(liste, listComplete, fw, monitor, getReExportAcompte(), numFin, gererPointages,taFacture.getIdDocument(),em,false);
									}else{
										for (TaRemise taRemise : listeRemiseAcompte) {
											if(listeRemise.indexOf(taRemise)==-1 )
												listeRemise.add(taRemise);
										}
									}
								}	
							}
							for (TaRReglement rReglement : taFacture.getTaRReglements()) {
								List<TaReglement> liste=new LinkedList<TaReglement>();
								List<TaRemise> listeRemiseReglement =new LinkedList<TaRemise>();
								listeRemiseReglement=taRemiseDao.findSiReglementDansRemise(rReglement.getTaReglement().getCodeDocument());


								if(listeRemiseReglement.size()==0 && ExportationPlugin.getDefault().getPreferenceStoreProject().getBoolean(PreferenceConstants.REGLEMENT_SIMPLE)){
									liste.add(rReglement.getTaReglement());
									numFin=exportReglementJPA(liste, listComplete, fw, monitor, getReExportReglement(), numFin, gererPointages,
											taFacture.getIdDocument(),em,false);
								}else{
									for (TaRemise taRemise : listeRemiseReglement) {
										if(listeRemise.indexOf(taRemise)==-1 )
											listeRemise.add(taRemise);
									}
								}

							}
							if(ExportationPlugin.getDefault().getPreferenceStoreProject().getBoolean(PreferenceConstants.REMISE)){
								if(listeRemise.size()>0){
									numFin=exportRemiseJPA(listeRemise, listComplete, fw, monitor, getReExportRemise(), numFin, gererPointages, 
											taFacture.getIdDocument(),em,false);
								}
							}
						}else if(gererPointages){
							for (TaRAcompte r : taFacture.getTaRAcomptes()) {
								numFin=initPointage(taFacture, r.getTaAcompte(), r, false, idAppelant, monitor,numFin);
							}
							for (TaRAvoir r : taFacture.getTaRAvoirs()) {
								numFin=initPointage(taFacture, r.getTaAvoir(), r, false, idAppelant, monitor,numFin);
							}
							for (TaRReglement r : taFacture.getTaRReglements()) {
								numFin=initPointage(taFacture, r.getTaReglement(), r, false, idAppelant, monitor,numFin);
							}
						}
							
						if(taFacture.getExport()!=1)taFacture.setExport(1);
						taFactureDAO.enregistrerMerge(taFacture);
						listComplete.add(taFacture.getCodeDocument());
						listeVerif.add(taFacture);
					}
				}
				if(monitor!=null) monitor.worked(1);
			}
			//numFin=numeroDePiece;
			//taFactureDAO.commit(transaction);

		} catch(IOException ioe){
			logger.error("",ioe);
			//transaction.rollback();
			em.clear();
			setRetour(false);
		} 
		//		}
		return numFin;
	}

		public int exportFactureCentraliseeJPA(List<TaFacture> idFactureAExporter,List<String> listComplete,FileWriter fw,
				IProgressMonitor monitor, int reExport,int numDepart,boolean gererPointages,Integer idAppelant,EntityManager em,boolean gererLesReglements) throws Exception {
			int numFin=numDepart;
			TaFactureDAO taFactureDAO = new TaFactureDAO(em);
			try { 


				TaFacture taFacture = null;
				TaRemiseDAO taRemiseDao = new TaRemiseDAO(em);


				numeroDePiece = numFin;


				boolean nouvellePiece = false;

				

				for(int i=0; i<idFactureAExporter.size(); i++) {
					taFacture = idFactureAExporter.get(i);
					if(listComplete.indexOf(taFacture.getCodeDocument())==-1){

						/**/   // pour compenser les problèmes d'arrondis mis sur des lignes à 0
						taFacture.calculeTvaEtTotaux();
						/**/  //isa le 03 février 2011 en accord avec Nicolas


						if(taFacture.getExport()!=1 || reExport==1) { /* facture pas deja exportee ou reexportation */
							nouvellePiece = true;

							if(nouvellePiece) {
								numeroDePiece++;
								nouvellePiece = false;
								numFin++;
							}
							//on crée un nouvel objet facture pour créer la centralisation
							TaFacture newFacture =new TaFacture(false);
							newFacture=(TaFacture) taFacture.cloneCentralisation();
							newFacture.getLignes().clear();
							
							TaLFacture newTaLFacture;
							for (TaLFacture obj : taFacture.getLignes()) {
								newTaLFacture=newFacture.contientMemeParametreCompte(obj);
								if(newTaLFacture!=null){
									newTaLFacture.setQteLDocument(BigDecimal.ONE);
									if(taFacture.getTtc()==1) {
										newTaLFacture.setPrixULDocument(newTaLFacture.getPrixULDocument().add(obj.getMtTtcLApresRemiseGlobaleDocument()));
									}else{
										newTaLFacture.setPrixULDocument(newTaLFacture.getPrixULDocument().add(obj.getMtHtLApresRemiseGlobaleDocument()));
									}
								}else{
									if(obj.getTaArticle()!=null){
									newTaLFacture=new TaLFacture(false);
									newTaLFacture=obj.clone();
									newTaLFacture.setLibLDocument(newFacture.getLibelleDocument());
									newTaLFacture.setQteLDocument(BigDecimal.ONE);
									newTaLFacture.setRemTxLDocument(BigDecimal.ZERO);
									if(taFacture.getTtc()==1) {
										newTaLFacture.setPrixULDocument(obj.getMtTtcLApresRemiseGlobaleDocument());
									}else{
										newTaLFacture.setPrixULDocument(obj.getMtHtLApresRemiseGlobaleDocument());
									}
									newTaLFacture.setTaDocument(newFacture);
									newFacture.addLigne(newTaLFacture);
									}
								}
							}
							newFacture.setTxRemHtDocument(BigDecimal.ZERO);
							newFacture.calculeTvaEtTotaux();
							
							//recuperation des informations propre a la facture
							codePiece = taFacture.getCodeDocument();
							datePiece = taFacture.getDateDocument();
							dateEcheance = taFacture.getDateEchDocument();
							dateLivraisonLigne = taFacture.getDateDocument();//changer pour erreur 18000 dans epicéa;
							libelleLigne = taFacture.getTaInfosDocument().getNomTiers();
							numCptLigne = taFacture.getTaInfosDocument().getCodeCompta();
							cptCollectif = taFacture.getTaInfosDocument().getCompte();
							nomTiers = taFacture.getTaInfosDocument().getNomTiers();
							adresse1Tiers = taFacture.getTaInfosDocument().getAdresse1();
							adresse2Tiers = taFacture.getTaInfosDocument().getAdresse2();
							codePostalTiers = taFacture.getTaInfosDocument().getCodepostal();
							villeTiers = taFacture.getTaInfosDocument().getVille();

							mtDebitLigne = LibConversion.BigDecimalToDouble(taFacture.getNetTtcCalc());  

							numCptLigne = "+"+taFacture.getTaInfosDocument().getCodeCompta();    
							numLignePiece = 1;
							typePiece = TypeVente;
							mtCreditLigne = 0d;
							qte1 = 0d;
							qte2 = 0d;
							codeTva ="";
							tauxTva = 0d;
							mtDebitTva = 0d;
							mtCreditTva = 0d;
							exigibiliteTva = "";

							refContrepartie = "";
							dateContrepartie = null;
							mtContrepartieDebit = 0d;
							mtContrepartieCredit = 0d;
							mtAffectation = 0d;

							//ecriture de la ligne d'entete dans le fichier d'export
							fw.write(creationLigne(numeroDePiece,
									numLignePiece ,
									typePiece ,
									codePiece ,
									datePiece ,
									numCptLigne ,
									libelleLigne ,
									mtDebitLigne ,
									mtCreditLigne ,
									qte1 ,
									qte2 ,
									codeTva ,
									tauxTva ,
									mtDebitTva ,
									mtCreditTva ,
									dateEcheance ,
									cptCollectif ,
									nomTiers ,
									adresse1Tiers ,
									adresse2Tiers ,
									codePostalTiers ,
									villeTiers ,
									exigibiliteTva ,
									dateLivraisonLigne,
									refContrepartie,
									dateContrepartie,
									mtContrepartieDebit,
									mtContrepartieCredit,
									mtAffectation));
							fw.write(finDeLigne);

							//réinitialisation des variables pour le prochain document
							cptCollectif ="";
							nomTiers ="";
							adresse1Tiers ="";
							adresse2Tiers ="";
							codePostalTiers ="";
							villeTiers="";

							BigDecimal escompte = taFacture.getRemTtcDocument();
							if (escompte.signum() > 0) { 
								//création d'une lignes d'escompte
								numCptLigne = ctpEscompteDebit;
								numLignePiece = numLignePiece + 1;
								typePiece = "V";
								libelleLigne = "Escompte "+taFacture.getLibelleDocument();
								mtDebitLigne = LibConversion.BigDecimalToDouble(escompte);
								mtCreditLigne = 0d;
								qte1 = 0d;
								qte2 = 0d;
								codeTva = "";
								tauxTva = 0d;
								mtDebitTva = 0d;
								mtCreditTva = 0d;
								exigibiliteTva = "";
								//ecriture de la ligne d'escompte dans le fichier d'export

								refContrepartie = "";
								dateContrepartie = null;
								mtContrepartieDebit = 0d;
								mtContrepartieCredit = 0d;
								mtAffectation = 0d;

								fw.write(creationLigne(numeroDePiece,
										numLignePiece ,
										typePiece ,
										codePiece ,
										datePiece ,
										numCptLigne ,
										libelleLigne ,
										mtDebitLigne ,
										mtCreditLigne ,
										qte1 ,
										qte2 ,
										codeTva ,
										tauxTva ,
										mtDebitTva ,
										mtCreditTva ,
										dateEcheance ,
										cptCollectif ,
										nomTiers ,
										adresse1Tiers ,
										adresse2Tiers ,
										codePostalTiers ,
										villeTiers ,
										exigibiliteTva ,
										dateLivraisonLigne,
										refContrepartie,
										dateContrepartie,
										mtContrepartieDebit,
										mtContrepartieCredit,
										mtAffectation));
								fw.write(finDeLigne);
							}

							//traitement des lignes
							for (TaLFacture lf : newFacture.getLignes()) {
								//récupération des valeurs propres à la ligne                 
								if(lf.getTaArticle()!=null   && lf.getMtHtLApresRemiseGlobaleDocument()!=null && lf.getMtHtLApresRemiseGlobaleDocument().signum()!=0){
									libelleLigne = lf.getLibLDocument();
									qte1 = LibConversion.BigDecimalToDouble(lf.getQteLDocument());
									qte2 = LibConversion.BigDecimalToDouble(lf.getQte2LDocument());
									numCptLigne = lf.getCompteLDocument();
									if(numCptLigne==null || numCptLigne.equals("")){
										setRetour(false);
										setMessageRetour("\nErreur N° 1\n"
												+"Article (code article : "+lf.getTaArticle().getCodeArticle()+") sans compte dans le document "+taFacture.getCodeDocument());
										throw new Exception("\nErreur N° 1\n"
												+"Article (code article : "+lf.getTaArticle().getCodeArticle()+") sans compte dans le document "+taFacture.getCodeDocument());
									}
									
									//traitement du type de tva du tiers (F ou ue ou horsUE ou Franchise)
										TaTTvaDocDAO daoTvaDoc = new TaTTvaDocDAO(em);
										String codeDoc="";
										if(taFacture.getTaInfosDocument().getCodeTTvaDoc()==null || taFacture.getTaInfosDocument().getCodeTTvaDoc().equals(""))
											codeDoc="F";
										else codeDoc=taFacture.getTaInfosDocument().getCodeTTvaDoc();
										TaTTvaDoc tvaDoc =daoTvaDoc.findByCode(codeDoc);
										String journalTva="";
										if(tvaDoc!=null)journalTva=tvaDoc.getJournalTTvaDoc();
										//si Franchise pas de code tva sur ligne sans code
										// si autre, L ou E sur compte 7 sauf compte 609
										if(codeDoc.equalsIgnoreCase("F"))
											codeTva = correspondanceCodeTva(codeDoc,lf.getCodeTvaLDocument(),lf.getCodeTvaLDocument(),numCptLigne,lf.getMtTtcLApresRemiseGlobaleDocument().subtract(lf.getMtHtLApresRemiseGlobaleDocument()));
										else
											codeTva = correspondanceCodeTva(codeDoc,lf.getCodeTvaLDocument(),journalTva,numCptLigne,lf.getMtTtcLApresRemiseGlobaleDocument().subtract(lf.getMtHtLApresRemiseGlobaleDocument()));	
										
									if(codeTva==null)codeTva="";
									if(lf.getTaArticle().getTaTTva()!=null)  
										exigibiliteTva = lf.getTaArticle().getTaTTva().getCodeTTva();
									if (exigibiliteTva.equals(""))exigibiliteTva="D";
									tauxTva = LibConversion.BigDecimalToDouble(lf.getTauxTvaLDocument());
									mtCreditTva = LibConversion.BigDecimalToDouble(lf.getMtTtcLApresRemiseGlobaleDocument().subtract(lf.getMtHtLApresRemiseGlobaleDocument()));
									mtCreditLigne = LibConversion.BigDecimalToDouble(lf.getMtHtLApresRemiseGlobaleDocument());

									numLignePiece = numLignePiece + 1;
									mtDebitLigne = 0d;
									mtDebitTva = 0d;
									cptCollectif = "";
									nomTiers = "";
									adresse1Tiers = "";
									adresse2Tiers = "";
									codePostalTiers = "";
									villeTiers = "";
									//ecriture de la ligne dans le fichier d'export

									refContrepartie = "";
									dateContrepartie = null;
									mtContrepartieDebit = 0d;
									mtContrepartieCredit = 0d;
									mtAffectation = 0d;

									fw.write(creationLigne(numeroDePiece,
											numLignePiece ,
											typePiece ,
											codePiece ,
											datePiece ,
											numCptLigne ,
											libelleLigne ,
											mtDebitLigne ,
											mtCreditLigne ,
											qte1 ,
											qte2 ,
											codeTva ,
											tauxTva ,
											mtDebitTva ,
											mtCreditTva ,
											dateEcheance ,
											cptCollectif ,
											nomTiers ,
											adresse1Tiers ,
											adresse2Tiers ,
											codePostalTiers ,
											villeTiers ,
											exigibiliteTva ,
											dateLivraisonLigne,
											refContrepartie,
											dateContrepartie,
											mtContrepartieDebit,
											mtContrepartieCredit,
											mtAffectation));
									fw.write(finDeLigne);
								}
							}

							if(gererLesReglements){

								List<TaRemise> listeRemise = new LinkedList<TaRemise>();
								//récupérer tous les documents directement liés à cette facture
								if(ExportationPlugin.getDefault().getPreferenceStoreProject().getBoolean(PreferenceConstants.ACOMPTES)){
									for (TaRAcompte rAcompte : taFacture.getTaRAcomptes()) {
										List<TaAcompte> liste=new LinkedList<TaAcompte>();
										List<TaRemise> listeRemiseAcompte =new LinkedList<TaRemise>();
										listeRemiseAcompte=taRemiseDao.findSiAcompteDansRemise(rAcompte.getTaAcompte().getCodeDocument());
										if(listeRemiseAcompte.size()==0 && ExportationPlugin.getDefault().getPreferenceStoreProject().getBoolean(PreferenceConstants.REGLEMENT_SIMPLE)){
											liste.add(rAcompte.getTaAcompte());
											numFin=exportAcompteJPA(liste, listComplete, fw, monitor, getReExportAcompte(), numFin, gererPointages,taFacture.getIdDocument(),em,false);
										}else{
											for (TaRemise taRemise : listeRemiseAcompte) {
												if(listeRemise.indexOf(taRemise)==-1 )
													listeRemise.add(taRemise);
											}
										}
									}	
								}
								for (TaRReglement rReglement : taFacture.getTaRReglements()) {
									List<TaReglement> liste=new LinkedList<TaReglement>();
									List<TaRemise> listeRemiseReglement =new LinkedList<TaRemise>();
									listeRemiseReglement=taRemiseDao.findSiReglementDansRemise(rReglement.getTaReglement().getCodeDocument());


									if(listeRemiseReglement.size()==0 && ExportationPlugin.getDefault().getPreferenceStoreProject().getBoolean(PreferenceConstants.REGLEMENT_SIMPLE)){
										liste.add(rReglement.getTaReglement());
										numFin=exportReglementJPA(liste, listComplete, fw, monitor, getReExportReglement(), numFin, gererPointages,
												taFacture.getIdDocument(),em,false);
									}else{
										for (TaRemise taRemise : listeRemiseReglement) {
											if(listeRemise.indexOf(taRemise)==-1 )
												listeRemise.add(taRemise);
										}
									}

								}
								if(ExportationPlugin.getDefault().getPreferenceStoreProject().getBoolean(PreferenceConstants.REMISE)){
									if(listeRemise.size()>0){
										numFin=exportRemiseJPA(listeRemise, listComplete, fw, monitor, getReExportRemise(), numFin, gererPointages, 
												taFacture.getIdDocument(),em,false);
									}
								}
							}else if(gererPointages){
								for (TaRAcompte r : taFacture.getTaRAcomptes()) {
									numFin=initPointage(taFacture, r.getTaAcompte(), r, false, idAppelant, monitor,numFin);
								}
								for (TaRAvoir r : taFacture.getTaRAvoirs()) {
									numFin=initPointage(taFacture, r.getTaAvoir(), r, false, idAppelant, monitor,numFin);
								}
								for (TaRReglement r : taFacture.getTaRReglements()) {
									numFin=initPointage(taFacture, r.getTaReglement(), r, false, idAppelant, monitor,numFin);
								}
							}
								
							if(taFacture.getExport()!=1)taFacture.setExport(1);
							taFactureDAO.enregistrerMerge(taFacture);
							listComplete.add(taFacture.getCodeDocument());
							listeVerif.add(taFacture);
						}
					}
					if(monitor!=null) monitor.worked(1);
				}

			} catch(IOException ioe){
				logger.error("",ioe);
				em.clear();
				setRetour(false);
			} 
			return numFin;
		}

		public int exportApporteurJPA(List<TaApporteur> idFactureAExporter,List<String> listComplete,FileWriter fw,IProgressMonitor monitor,
				int reExport,int numDepart,boolean gererPointages,Integer idAppelant,EntityManager em,boolean gererLesDocumenstLies) throws Exception {
			int numFin=numDepart;
			try {      


				TaApporteurDAO TaApporteurDAO = new TaApporteurDAO(em);
				TaApporteur TaApporteur = null;


				numeroDePiece = numFin;



				boolean nouvellePiece = false;


				for(int i=0; i<idFactureAExporter.size(); i++) {
					TaApporteur = idFactureAExporter.get(i);
					if(listComplete.indexOf(TaApporteur.getCodeDocument())==-1){

						/**/   // pour compenser les problèmes d'arrondis mis sur des lignes à 0
						TaApporteur.calculTvaTotal();
						/**/  //isa le 03 février 2011 en accord avec Nicolas

						if(TaApporteur.getExport()!=1 || reExport==1) { /* facture pas deja exportee ou reexportation */
							nouvellePiece = true;
							//					TaApporteur.calculeTvaEtTotaux();

							if(nouvellePiece) {
								numeroDePiece++;
								nouvellePiece = false;
								numFin++;
							}

							//recuperation des informations propre a la facture
							codePiece = TaApporteur.getCodeDocument();
							datePiece = TaApporteur.getDateDocument();
							dateEcheance = TaApporteur.getDateEchDocument();
							dateLivraisonLigne = TaApporteur.getDateDocument();//changer pour erreur 18000 dans epicéa;
//							dateLivraisonLigne = TaApporteur.getDateLivDocument();
							libelleLigne = TaApporteur.getTaInfosDocument().getNomTiers();
							numCptLigne = TaApporteur.getTaInfosDocument().getCodeCompta();
							cptCollectif = TaApporteur.getTaInfosDocument().getCompte();
							nomTiers = TaApporteur.getTaInfosDocument().getNomTiers();
							adresse1Tiers = TaApporteur.getTaInfosDocument().getAdresse1();
							adresse2Tiers = TaApporteur.getTaInfosDocument().getAdresse2();
							codePostalTiers = TaApporteur.getTaInfosDocument().getCodepostal();
							villeTiers = TaApporteur.getTaInfosDocument().getVille();
							//					ttc = taFacture.getTtc();
							//					txRemHtFacture = taFacture.getRemHtFacture();
							//					exportComtpa = taFacture.getExport();

							mtCreditLigne = LibConversion.BigDecimalToDouble(TaApporteur.getNetTtcCalc());  

							numCptLigne = "+"+TaApporteur.getTaInfosDocument().getCodeCompta();    
							numLignePiece = 1;
							typePiece = TypeAchat;
							mtDebitLigne = 0d;
							qte1 = 0d;
							qte2 = 0d;
							codeTva ="";
							tauxTva = 0d;
							mtDebitTva = 0d;
							mtCreditTva = 0d;
							exigibiliteTva = "";

							refContrepartie = "";
							dateContrepartie = null;
							mtContrepartieDebit = 0d;
							mtContrepartieCredit = 0d;
							mtAffectation = 0d;

							//ecriture de la ligne d'entete dans le fichier d'export
							fw.write(creationLigne(numeroDePiece,
									numLignePiece ,
									typePiece ,
									codePiece ,
									datePiece ,
									numCptLigne ,
									libelleLigne ,
									mtDebitLigne ,
									mtCreditLigne ,
									qte1 ,
									qte2 ,
									codeTva ,
									tauxTva ,
									mtCreditTva ,
									mtDebitTva ,
									dateEcheance ,
									cptCollectif ,
									nomTiers ,
									adresse1Tiers ,
									adresse2Tiers ,
									codePostalTiers ,
									villeTiers ,
									exigibiliteTva ,
									dateLivraisonLigne,
									refContrepartie,
									dateContrepartie,
									mtContrepartieDebit,
									mtContrepartieCredit,
									mtAffectation));
							fw.write(finDeLigne);

							//réinitialisation des variables pour le prochain document
							cptCollectif ="";
							nomTiers ="";
							adresse1Tiers ="";
							adresse2Tiers ="";
							codePostalTiers ="";
							villeTiers="";


							//traitement des lignes
							for (TaLApporteur lf : TaApporteur.getLignes()) {
								//récupération des valeurs propres à la ligne
								//						if(lf.getTaArticle()!=null){
								typePiece = TypeAchat;
								if(lf.getTaArticle()!=null && lf.getMtHtLApresRemiseGlobaleDocument().signum()!=0){
									libelleLigne = lf.getLibLDocument();
									qte1 = LibConversion.BigDecimalToDouble(lf.getQteLDocument());
									qte2 = LibConversion.BigDecimalToDouble(lf.getQte2LDocument());
									numCptLigne = lf.getCompteLDocument();
									if(numCptLigne==null || numCptLigne.equals("")){
										//transaction.rollback();
										setRetour(false);
										setMessageRetour("\nErreur N° 1\n"
												+"Article (code article : "+lf.getTaArticle().getCodeArticle()+") sans compte dans le document "+TaApporteur.getCodeDocument());
										throw new Exception("\nErreur N° 1\n"
												+"Article (code article : "+lf.getTaArticle().getCodeArticle()+") sans compte dans le document "+TaApporteur.getCodeDocument());
									}
									//traitement du type de tva du tiers (F ou ue ou horsUE ou Franchise)
									TaTTvaDocDAO daoTvaDoc = new TaTTvaDocDAO(em);
									String codeDoc="";
									if(TaApporteur.getTaInfosDocument().getCodeTTvaDoc()==null || TaApporteur.getTaInfosDocument().getCodeTTvaDoc().equals(""))
										codeDoc="F";
									else codeDoc=TaApporteur.getTaInfosDocument().getCodeTTvaDoc();
									TaTTvaDoc tvaDoc =daoTvaDoc.findByCode(codeDoc);
									String journalTva="";
									if(tvaDoc!=null)journalTva=tvaDoc.getJournalTTvaDoc();
									
//										if(TaApporteur.getTaTiers()!=null && TaApporteur.getTaTiers().getTaTTvaDoc()!=null){
									if(codeDoc.equalsIgnoreCase("F"))
										codeTva = correspondanceCodeTva(codeDoc,lf.getCodeTvaLDocument(),lf.getCodeTvaLDocument(),numCptLigne,lf.getMtTtcLApresRemiseGlobaleDocument().subtract(lf.getMtHtLApresRemiseGlobaleDocument()));
									else
										codeTva = correspondanceCodeTva(codeDoc,lf.getCodeTvaLDocument(),journalTva,numCptLigne,lf.getMtTtcLApresRemiseGlobaleDocument().subtract(lf.getMtHtLApresRemiseGlobaleDocument()));	
//										}else
//											codeTva = lf.getCodeTvaLDocument();


//									exigibiliteTva = lf.getTaArticle().getTaTTva().getCodeTTva();
//									if(exigibiliteTva == null)exigibiliteTva="D";
									if(codeTva==null)codeTva=""; /* corrigé par isa le 11/09/2014*/
									if(lf.getTaArticle().getTaTTva()!=null)  
										exigibiliteTva = lf.getTaArticle().getTaTTva().getCodeTTva();
									if (exigibiliteTva.equals(""))exigibiliteTva="D";
									
									tauxTva = LibConversion.BigDecimalToDouble(lf.getTauxTvaLDocument());
									mtDebitTva = LibConversion.BigDecimalToDouble(lf.getMtTtcLApresRemiseGlobaleDocument().subtract(lf.getMtHtLApresRemiseGlobaleDocument()));
									mtDebitLigne = LibConversion.BigDecimalToDouble(lf.getMtHtLApresRemiseGlobaleDocument());

									numLignePiece = numLignePiece + 1;
									mtCreditLigne = 0d;
									mtCreditTva = 0d;
									cptCollectif = "";
									nomTiers = "";
									adresse1Tiers = "";
									adresse2Tiers = "";
									codePostalTiers = "";
									villeTiers = "";
									//ecriture de la ligne dans le fichier d'export

									refContrepartie = "";
									dateContrepartie = null;
									mtContrepartieDebit = 0d;
									mtContrepartieCredit = 0d;
									mtAffectation = 0d;

									fw.write(creationLigne(numeroDePiece,
											numLignePiece ,
											typePiece ,
											codePiece ,
											datePiece ,
											numCptLigne ,
											libelleLigne ,
											mtDebitLigne,
											mtCreditLigne ,
											qte1 ,
											qte2 ,
											codeTva ,
											tauxTva ,
											mtDebitTva,
											mtCreditTva ,
											dateEcheance ,
											cptCollectif ,
											nomTiers ,
											adresse1Tiers ,
											adresse2Tiers ,
											codePostalTiers ,
											villeTiers ,
											exigibiliteTva ,
											dateLivraisonLigne,
											refContrepartie,
											dateContrepartie,
											mtContrepartieDebit,
											mtContrepartieCredit,
											mtAffectation));
									fw.write(finDeLigne);
								}
							} 


							if(TaApporteur.getExport()!=1)TaApporteur.setExport(1);
							TaApporteurDAO.enregistrerMerge(TaApporteur);
							listComplete.add(TaApporteur.getCodeDocument());
							listeVerif.add(TaApporteur);
						}
					}
					if(monitor!=null) monitor.worked(1);
				}
				//numFin=numeroDePiece;
//				TaApporteurDAO.commit(transaction);

				//bw.close();
				//fw.close();
			} catch(IOException ioe){
				logger.error("",ioe);
				setRetour(false);
			} 
			//		catch (Exception e) {
			//			logger.error("",e);
			//		}
			return numFin;
		}

	public int exportAvoirJPA(List<TaAvoir> idFactureAExporter,List<String> listComplete,FileWriter fw,IProgressMonitor monitor,
			int reExport,int numDepart,boolean gererPointages,Integer idAppelant,EntityManager em,boolean gererLesDocumenstLies) throws Exception {
		int numFin=numDepart;
		try {      


			Boolean gererLesPointages = gererPointages;

			TaAvoirDAO taAvoirDAO = new TaAvoirDAO(em);
			TaAvoir taAvoir = null;


			numeroDePiece = numFin;



			boolean nouvellePiece = false;

//			EntityTransaction transaction = taAvoirDAO.getEntityManager().getTransaction();
//			taAvoirDAO.begin(transaction);

			//for(int i=0; i<idFactureAExporter.length; i++) {
			for(int i=0; i<idFactureAExporter.size(); i++) {
				//taAvoir = taAvoirDAO.findById(LibConversion.stringToInteger(idFactureAExporter[i]));
				taAvoir = idFactureAExporter.get(i);
				if(listComplete.indexOf(taAvoir.getCodeDocument())==-1){

					/**/   // pour compenser les problèmes d'arrondis mis sur des lignes à 0
					taAvoir.calculTvaTotal();
					/**/  //isa le 03 février 2011 en accord avec Nicolas

					if(taAvoir.getExport()!=1 || reExport==1) { /* facture pas deja exportee ou reexportation */
						nouvellePiece = true;
						//					taAvoir.calculeTvaEtTotaux();

						if(nouvellePiece) {
							numeroDePiece++;
							nouvellePiece = false;
							numFin++;
						}

						//recuperation des informations propre a la facture
						codePiece = taAvoir.getCodeDocument();
						datePiece = taAvoir.getDateDocument();
						dateEcheance = taAvoir.getDateEchDocument();
						dateLivraisonLigne = taAvoir.getDateDocument();//changer pour erreur 18000 dans epicéa;
//						dateLivraisonLigne = taAvoir.getDateLivDocument();
						libelleLigne = taAvoir.getTaInfosDocument().getNomTiers();
						numCptLigne = taAvoir.getTaInfosDocument().getCodeCompta();
						cptCollectif = taAvoir.getTaInfosDocument().getCompte();
						nomTiers = taAvoir.getTaInfosDocument().getNomTiers();
						adresse1Tiers = taAvoir.getTaInfosDocument().getAdresse1();
						adresse2Tiers = taAvoir.getTaInfosDocument().getAdresse2();
						codePostalTiers = taAvoir.getTaInfosDocument().getCodepostal();
						villeTiers = taAvoir.getTaInfosDocument().getVille();
						//					ttc = taFacture.getTtc();
						//					txRemHtFacture = taFacture.getRemHtFacture();
						//					exportComtpa = taFacture.getExport();

						mtCreditLigne = LibConversion.BigDecimalToDouble(taAvoir.getNetTtcCalc());  

						numCptLigne = "+"+taAvoir.getTaInfosDocument().getCodeCompta();    
						numLignePiece = 1;
						typePiece = TypeAchat;
						mtDebitLigne = 0d;
						qte1 = 0d;
						qte2 = 0d;
						codeTva ="";
						tauxTva = 0d;
						mtDebitTva = 0d;
						mtCreditTva = 0d;
						exigibiliteTva = "";

						refContrepartie = "";
						dateContrepartie = null;
						mtContrepartieDebit = 0d;
						mtContrepartieCredit = 0d;
						mtAffectation = 0d;

						//ecriture de la ligne d'entete dans le fichier d'export
						fw.write(creationLigne(numeroDePiece,
								numLignePiece ,
								typePiece ,
								codePiece ,
								datePiece ,
								numCptLigne ,
								libelleLigne ,
								mtDebitLigne ,
								mtCreditLigne ,
								qte1 ,
								qte2 ,
								codeTva ,
								tauxTva ,
								mtCreditTva ,
								mtDebitTva ,
								dateEcheance ,
								cptCollectif ,
								nomTiers ,
								adresse1Tiers ,
								adresse2Tiers ,
								codePostalTiers ,
								villeTiers ,
								exigibiliteTva ,
								dateLivraisonLigne,
								refContrepartie,
								dateContrepartie,
								mtContrepartieDebit,
								mtContrepartieCredit,
								mtAffectation));
						fw.write(finDeLigne);

						//réinitialisation des variables pour le prochain document
						cptCollectif ="";
						nomTiers ="";
						adresse1Tiers ="";
						adresse2Tiers ="";
						codePostalTiers ="";
						villeTiers="";


						//traitement des lignes
						for (TaLAvoir lf : taAvoir.getLignes()) {
							//récupération des valeurs propres à la ligne
							//						if(lf.getTaArticle()!=null){
							typePiece = TypeAchat;
							if(lf.getTaArticle()!=null && lf.getMtHtLApresRemiseGlobaleDocument().signum()!=0){
								libelleLigne = lf.getLibLDocument();
								qte1 = LibConversion.BigDecimalToDouble(lf.getQteLDocument());
								qte2 = LibConversion.BigDecimalToDouble(lf.getQte2LDocument());
								numCptLigne = lf.getCompteLDocument();
								if(numCptLigne==null || numCptLigne.equals("")){
									//transaction.rollback();
									setRetour(false);
									setMessageRetour("\nErreur N° 1\n"
											+"Article (code article : "+lf.getTaArticle().getCodeArticle()+") sans compte dans le document "+taAvoir.getCodeDocument());
									throw new Exception("\nErreur N° 1\n"
											+"Article (code article : "+lf.getTaArticle().getCodeArticle()+") sans compte dans le document "+taAvoir.getCodeDocument());
								}
								//traitement du type de tva du tiers (F ou ue ou horsUE ou Franchise)
								TaTTvaDocDAO daoTvaDoc = new TaTTvaDocDAO(em);
								String codeDoc="";
								if(taAvoir.getTaInfosDocument().getCodeTTvaDoc()==null || taAvoir.getTaInfosDocument().getCodeTTvaDoc().equals(""))
									codeDoc="F";
								else codeDoc=taAvoir.getTaInfosDocument().getCodeTTvaDoc();
								TaTTvaDoc tvaDoc =daoTvaDoc.findByCode(codeDoc);
								String journalTva="";
								if(tvaDoc!=null)journalTva=tvaDoc.getJournalTTvaDoc();
								
//									if(taAvoir.getTaTiers()!=null && taAvoir.getTaTiers().getTaTTvaDoc()!=null){
								if(codeDoc.equalsIgnoreCase("F"))
									codeTva = correspondanceCodeTva(codeDoc,lf.getCodeTvaLDocument(),lf.getCodeTvaLDocument(),numCptLigne,lf.getMtTtcLApresRemiseGlobaleDocument().subtract(lf.getMtHtLApresRemiseGlobaleDocument()));
								else
									codeTva = correspondanceCodeTva(codeDoc,lf.getCodeTvaLDocument(),journalTva,numCptLigne,lf.getMtTtcLApresRemiseGlobaleDocument().subtract(lf.getMtHtLApresRemiseGlobaleDocument()));	
//									}else
//										codeTva = lf.getCodeTvaLDocument();


//								exigibiliteTva = lf.getTaArticle().getTaTTva().getCodeTTva();
//								if(exigibiliteTva == null)exigibiliteTva="D";
								if(codeTva==null)codeTva=""; /* corrigé par isa le 11/09/2014*/
								if(lf.getTaArticle().getTaTTva()!=null)  
									exigibiliteTva = lf.getTaArticle().getTaTTva().getCodeTTva();
								if (exigibiliteTva.equals(""))exigibiliteTva="D";
								
								tauxTva = LibConversion.BigDecimalToDouble(lf.getTauxTvaLDocument());
								mtDebitTva = LibConversion.BigDecimalToDouble(lf.getMtTtcLApresRemiseGlobaleDocument().subtract(lf.getMtHtLApresRemiseGlobaleDocument()));
								mtDebitLigne = LibConversion.BigDecimalToDouble(lf.getMtHtLApresRemiseGlobaleDocument());

								numLignePiece = numLignePiece + 1;
								mtCreditLigne = 0d;
								mtCreditTva = 0d;
								cptCollectif = "";
								nomTiers = "";
								adresse1Tiers = "";
								adresse2Tiers = "";
								codePostalTiers = "";
								villeTiers = "";
								//ecriture de la ligne dans le fichier d'export

								refContrepartie = "";
								dateContrepartie = null;
								mtContrepartieDebit = 0d;
								mtContrepartieCredit = 0d;
								mtAffectation = 0d;

								fw.write(creationLigne(numeroDePiece,
										numLignePiece ,
										typePiece ,
										codePiece ,
										datePiece ,
										numCptLigne ,
										libelleLigne ,
										mtDebitLigne,
										mtCreditLigne ,
										qte1 ,
										qte2 ,
										codeTva ,
										tauxTva ,
										mtDebitTva,
										mtCreditTva ,
										dateEcheance ,
										cptCollectif ,
										nomTiers ,
										adresse1Tiers ,
										adresse2Tiers ,
										codePostalTiers ,
										villeTiers ,
										exigibiliteTva ,
										dateLivraisonLigne,
										refContrepartie,
										dateContrepartie,
										mtContrepartieDebit,
										mtContrepartieCredit,
										mtAffectation));
								fw.write(finDeLigne);
							}
						} 
						if(gererLesPointages){
							//traitement des lignes pour les pointages
							typePiece = typePointage;
							for (TaRAvoir lf : taAvoir.getTaRAvoirs()) {
								//récupération des valeurs propres à la ligne
								TaFacture fac =lf.getTaFacture();
								numFin=initPointage(fac, taAvoir, lf, gererLesDocumenstLies, idAppelant, monitor,numFin);
//								if(fac!=null){
//									if(gererLesDocumenstLies
//											&& idAppelant==null){
//										List<TaFacture> liste = new LinkedList<TaFacture>();
//										liste.add(fac);
//										exportFactureJPA(liste, listComplete, fw, monitor, reExportFacture, numDepart, false, taAvoir.getIdDocument(),em,
//												false);
//									}
//									if(idAppelant==null || idAppelant==fac.getIdDocument()){
//										codePiece=taAvoir.getCodeDocument();
//										datePiece=taAvoir.getDateDocument();
//										libelleLigne = taAvoir.getLibelleDocument();//libelle de la ligne de facture
//										qte1 = 0d;
//										qte2 = 0d;
//										numCptLigne = "+"+taAvoir.getTaInfosDocument().getCodeCompta();
//										cptCollectif = taAvoir.getTaInfosDocument().getCompte();;
//										nomTiers = "";
//										adresse1Tiers = "";
//										adresse2Tiers = "";
//										codePostalTiers = "";
//										villeTiers = "";
//
//										codeTva = "";
//										exigibiliteTva = "";
//										mtDebitTva = 0d;
//										mtDebitLigne = 0d;
//
//										numLignePiece = numLignePiece + 1;
//										mtCreditLigne = LibConversion.BigDecimalToDouble(taAvoir.getNetTtcCalc());
//										mtCreditTva = 0d;	
//
//										refContrepartie = fac.getCodeDocument();
//										dateContrepartie = fac.getDateDocument();
//										mtContrepartieDebit = LibConversion.BigDecimalToDouble(fac.getNetTtcCalc());
//										mtContrepartieCredit = 0d;
//										mtAffectation = LibConversion.BigDecimalToDouble(lf.getAffectation());
//
//										fw.write(creationLigne(numeroDePiece,
//												numLignePiece ,
//												typePiece ,
//												codePiece ,
//												datePiece ,
//												numCptLigne ,
//												libelleLigne ,
//												mtDebitLigne,
//												mtCreditLigne ,
//												qte1 ,
//												qte2 ,
//												codeTva ,
//												tauxTva ,
//												mtDebitTva,
//												mtCreditTva ,
//												dateEcheance ,
//												cptCollectif ,
//												nomTiers ,
//												adresse1Tiers ,
//												adresse2Tiers ,
//												codePostalTiers ,
//												villeTiers ,
//												exigibiliteTva ,
//												dateLivraisonLigne,
//												refContrepartie,
//												dateContrepartie,
//												mtContrepartieDebit,
//												mtContrepartieCredit,
//												mtAffectation));
//										fw.write(finDeLigne);
//										if(lf.getExport()!=1)lf.setExport(1);
//										listCompletePointage.add(new IDocumentTiers[]{taAvoir,fac});
//										listeVerif.add(fac);
//									}
//								}
							} 
						}

						if(taAvoir.getExport()!=1)taAvoir.setExport(1);
						taAvoirDAO.enregistrerMerge(taAvoir);
						listComplete.add(taAvoir.getCodeDocument());
						listeVerif.add(taAvoir);
					}
				}
				if(monitor!=null) monitor.worked(1);
			}
			//numFin=numeroDePiece;
//			taAvoirDAO.commit(transaction);

			//bw.close();
			//fw.close();
		} catch(IOException ioe){
			logger.error("",ioe);
			setRetour(false);
		} 
		//		catch (Exception e) {
		//			logger.error("",e);
		//		}
		return numFin;
	}

	
	public int exportApporteurCentraliseeJPA(List<TaApporteur> idFactureAExporter,List<String> listComplete,FileWriter fw,IProgressMonitor monitor,
			int reExport,int numDepart,boolean gererPointages,Integer idAppelant,EntityManager em,boolean gererLesDocumenstLies) throws Exception {
		int numFin=numDepart;
		try {      


			Boolean gererLesPointages = gererPointages;

			TaApporteurDAO TaApporteurDAO = new TaApporteurDAO(em);
			TaApporteur TaApporteur = null;


			numeroDePiece = numFin;



			boolean nouvellePiece = false;


			for(int i=0; i<idFactureAExporter.size(); i++) {
				TaApporteur = idFactureAExporter.get(i);
				if(listComplete.indexOf(TaApporteur.getCodeDocument())==-1){

					/**/   // pour compenser les problèmes d'arrondis mis sur des lignes à 0
					TaApporteur.calculeTvaEtTotaux();
					/**/  //isa le 03 février 2011 en accord avec Nicolas

					if(TaApporteur.getExport()!=1 || reExport==1) { /* facture pas deja exportee ou reexportation */
						nouvellePiece = true;

						if(nouvellePiece) {
							numeroDePiece++;
							nouvellePiece = false;
							numFin++;
						}

						//on crée un nouvel objet facture pour créer la centralisation
						TaApporteur newDocument =new TaApporteur(false);
						newDocument=(TaApporteur) TaApporteur.cloneCentralisation();
						newDocument.getLignes().clear();
						
						TaLApporteur newTaLDocument;
						for (TaLApporteur obj : TaApporteur.getLignes()) {
							newTaLDocument=newDocument.contientMemeParametreCompte(obj);
							if(newTaLDocument!=null){
								newTaLDocument.setQteLDocument(BigDecimal.ONE);
								if(TaApporteur.getTtc()==1) {
									newTaLDocument.setPrixULDocument(newTaLDocument.getPrixULDocument().add(obj.getMtTtcLApresRemiseGlobaleDocument()));
								}else{
									newTaLDocument.setPrixULDocument(newTaLDocument.getPrixULDocument().add(obj.getMtHtLApresRemiseGlobaleDocument()));
								}
							}else{
								if(obj.getTaArticle()!=null){
								newTaLDocument=new TaLApporteur(false);
								newTaLDocument=obj.clone();
								newTaLDocument.setLibLDocument(newDocument.getLibelleDocument());
								newTaLDocument.setQteLDocument(BigDecimal.ONE);
								newTaLDocument.setRemTxLDocument(BigDecimal.ZERO);
								if(TaApporteur.getTtc()==1) {
									newTaLDocument.setPrixULDocument(obj.getMtTtcLApresRemiseGlobaleDocument());
								}else{
									newTaLDocument.setPrixULDocument(obj.getMtHtLApresRemiseGlobaleDocument());
								}
								newTaLDocument.setTaDocument(newDocument);
								newDocument.addLigne(newTaLDocument);
								}
							}
						}
						newDocument.setTxRemHtDocument(BigDecimal.ZERO);
						newDocument.calculeTvaEtTotaux();
						
						//recuperation des informations propre a la facture
						codePiece = TaApporteur.getCodeDocument();
						datePiece = TaApporteur.getDateDocument();
						dateEcheance = TaApporteur.getDateEchDocument();
						dateLivraisonLigne = TaApporteur.getDateDocument();//changer pour erreur 18000 dans epicéa;
//						dateLivraisonLigne = TaApporteur.getDateLivDocument();
						libelleLigne = TaApporteur.getTaInfosDocument().getNomTiers();
						numCptLigne = TaApporteur.getTaInfosDocument().getCodeCompta();
						cptCollectif = TaApporteur.getTaInfosDocument().getCompte();
						nomTiers = TaApporteur.getTaInfosDocument().getNomTiers();
						adresse1Tiers = TaApporteur.getTaInfosDocument().getAdresse1();
						adresse2Tiers = TaApporteur.getTaInfosDocument().getAdresse2();
						codePostalTiers = TaApporteur.getTaInfosDocument().getCodepostal();
						villeTiers = TaApporteur.getTaInfosDocument().getVille();
						//					ttc = taFacture.getTtc();
						//					txRemHtFacture = taFacture.getRemHtFacture();
						//					exportComtpa = taFacture.getExport();

						mtCreditLigne = LibConversion.BigDecimalToDouble(TaApporteur.getNetTtcCalc());  

						numCptLigne = "+"+TaApporteur.getTaInfosDocument().getCodeCompta();    
						numLignePiece = 1;
						typePiece = TypeAchat;
						mtDebitLigne = 0d;
						qte1 = 0d;
						qte2 = 0d;
						codeTva ="";
						tauxTva = 0d;
						mtDebitTva = 0d;
						mtCreditTva = 0d;
						exigibiliteTva = "";

						refContrepartie = "";
						dateContrepartie = null;
						mtContrepartieDebit = 0d;
						mtContrepartieCredit = 0d;
						mtAffectation = 0d;

						//ecriture de la ligne d'entete dans le fichier d'export
						fw.write(creationLigne(numeroDePiece,
								numLignePiece ,
								typePiece ,
								codePiece ,
								datePiece ,
								numCptLigne ,
								libelleLigne ,
								mtDebitLigne ,
								mtCreditLigne ,
								qte1 ,
								qte2 ,
								codeTva ,
								tauxTva ,
								mtCreditTva ,
								mtDebitTva ,
								dateEcheance ,
								cptCollectif ,
								nomTiers ,
								adresse1Tiers ,
								adresse2Tiers ,
								codePostalTiers ,
								villeTiers ,
								exigibiliteTva ,
								dateLivraisonLigne,
								refContrepartie,
								dateContrepartie,
								mtContrepartieDebit,
								mtContrepartieCredit,
								mtAffectation));
						fw.write(finDeLigne);

						//réinitialisation des variables pour le prochain document
						cptCollectif ="";
						nomTiers ="";
						adresse1Tiers ="";
						adresse2Tiers ="";
						codePostalTiers ="";
						villeTiers="";


						//traitement des lignes
						for (TaLApporteur lf : newDocument.getLignes()) {
							//récupération des valeurs propres à la ligne
							//						if(lf.getTaArticle()!=null){
							typePiece = TypeAchat;
							if(lf.getTaArticle()!=null && lf.getMtHtLApresRemiseGlobaleDocument().signum()!=0){
								libelleLigne = lf.getLibLDocument();
								qte1 = LibConversion.BigDecimalToDouble(lf.getQteLDocument());
								qte2 = LibConversion.BigDecimalToDouble(lf.getQte2LDocument());
								numCptLigne = lf.getCompteLDocument();
								if(numCptLigne==null || numCptLigne.equals("")){
									//transaction.rollback();
									setRetour(false);
									setMessageRetour("\nErreur N° 1\n"
											+"Article (code article : "+lf.getTaArticle().getCodeArticle()+") sans compte dans le document "+TaApporteur.getCodeDocument());
									throw new Exception("\nErreur N° 1\n"
											+"Article (code article : "+lf.getTaArticle().getCodeArticle()+") sans compte dans le document "+TaApporteur.getCodeDocument());
								}
								//traitement du type de tva du tiers (F ou ue ou horsUE ou Franchise)
								TaTTvaDocDAO daoTvaDoc = new TaTTvaDocDAO(em);
								String codeDoc="";
								if(TaApporteur.getTaInfosDocument().getCodeTTvaDoc()==null || TaApporteur.getTaInfosDocument().getCodeTTvaDoc().equals(""))
									codeDoc="F";
								else codeDoc=TaApporteur.getTaInfosDocument().getCodeTTvaDoc();
								TaTTvaDoc tvaDoc =daoTvaDoc.findByCode(codeDoc);
								String journalTva="";
								if(tvaDoc!=null)journalTva=tvaDoc.getJournalTTvaDoc();
								
//									if(TaApporteur.getTaTiers()!=null && TaApporteur.getTaTiers().getTaTTvaDoc()!=null){
								if(codeDoc.equalsIgnoreCase("F"))
									codeTva = correspondanceCodeTva(codeDoc,lf.getCodeTvaLDocument(),lf.getCodeTvaLDocument(),numCptLigne,lf.getMtTtcLApresRemiseGlobaleDocument().subtract(lf.getMtHtLApresRemiseGlobaleDocument()));
								else
									codeTva = correspondanceCodeTva(codeDoc,lf.getCodeTvaLDocument(),journalTva,numCptLigne,lf.getMtTtcLApresRemiseGlobaleDocument().subtract(lf.getMtHtLApresRemiseGlobaleDocument()));	
//									}else
//										codeTva = lf.getCodeTvaLDocument();


//								exigibiliteTva = lf.getTaArticle().getTaTTva().getCodeTTva();
//								if(exigibiliteTva == null)exigibiliteTva="D";
								if(codeTva==null)codeTva=""; /* corrigé par isa le 11/09/2014*/
								if(lf.getTaArticle().getTaTTva()!=null)  
									exigibiliteTva = lf.getTaArticle().getTaTTva().getCodeTTva();
								if (exigibiliteTva.equals(""))exigibiliteTva="D";
								
								tauxTva = LibConversion.BigDecimalToDouble(lf.getTauxTvaLDocument());
								mtDebitTva = LibConversion.BigDecimalToDouble(lf.getMtTtcLApresRemiseGlobaleDocument().subtract(lf.getMtHtLApresRemiseGlobaleDocument()));
								mtDebitLigne = LibConversion.BigDecimalToDouble(lf.getMtHtLApresRemiseGlobaleDocument());

								numLignePiece = numLignePiece + 1;
								mtCreditLigne = 0d;
								mtCreditTva = 0d;
								cptCollectif = "";
								nomTiers = "";
								adresse1Tiers = "";
								adresse2Tiers = "";
								codePostalTiers = "";
								villeTiers = "";
								//ecriture de la ligne dans le fichier d'export

								refContrepartie = "";
								dateContrepartie = null;
								mtContrepartieDebit = 0d;
								mtContrepartieCredit = 0d;
								mtAffectation = 0d;

								fw.write(creationLigne(numeroDePiece,
										numLignePiece ,
										typePiece ,
										codePiece ,
										datePiece ,
										numCptLigne ,
										libelleLigne ,
										mtDebitLigne,
										mtCreditLigne ,
										qte1 ,
										qte2 ,
										codeTva ,
										tauxTva ,
										mtDebitTva,
										mtCreditTva ,
										dateEcheance ,
										cptCollectif ,
										nomTiers ,
										adresse1Tiers ,
										adresse2Tiers ,
										codePostalTiers ,
										villeTiers ,
										exigibiliteTva ,
										dateLivraisonLigne,
										refContrepartie,
										dateContrepartie,
										mtContrepartieDebit,
										mtContrepartieCredit,
										mtAffectation));
								fw.write(finDeLigne);
							}
						} 

						if(TaApporteur.getExport()!=1)TaApporteur.setExport(1);
						TaApporteurDAO.enregistrerMerge(TaApporteur);
						listComplete.add(TaApporteur.getCodeDocument());
						listeVerif.add(TaApporteur);
					}
				}
				if(monitor!=null) monitor.worked(1);
			}
			//numFin=numeroDePiece;
//			TaApporteurDAO.commit(transaction);

			//bw.close();
			//fw.close();
		} catch(IOException ioe){
			logger.error("",ioe);
			setRetour(false);
		} 
		//		catch (Exception e) {
		//			logger.error("",e);
		//		}
		return numFin;
	}

	
	public int exportAvoirCentraliseeJPA(List<TaAvoir> idFactureAExporter,List<String> listComplete,FileWriter fw,IProgressMonitor monitor,
			int reExport,int numDepart,boolean gererPointages,Integer idAppelant,EntityManager em,boolean gererLesDocumenstLies) throws Exception {
		int numFin=numDepart;
		try {      


			Boolean gererLesPointages = gererPointages;

			TaAvoirDAO taAvoirDAO = new TaAvoirDAO(em);
			TaAvoir taAvoir = null;


			numeroDePiece = numFin;



			boolean nouvellePiece = false;


			for(int i=0; i<idFactureAExporter.size(); i++) {
				taAvoir = idFactureAExporter.get(i);
				if(listComplete.indexOf(taAvoir.getCodeDocument())==-1){

					/**/   // pour compenser les problèmes d'arrondis mis sur des lignes à 0
					taAvoir.calculeTvaEtTotaux();
					/**/  //isa le 03 février 2011 en accord avec Nicolas

					if(taAvoir.getExport()!=1 || reExport==1) { /* facture pas deja exportee ou reexportation */
						nouvellePiece = true;

						if(nouvellePiece) {
							numeroDePiece++;
							nouvellePiece = false;
							numFin++;
						}

						//on crée un nouvel objet facture pour créer la centralisation
						TaAvoir newDocument =new TaAvoir(false);
						newDocument=(TaAvoir) taAvoir.cloneCentralisation();
						newDocument.getLignes().clear();
						
						TaLAvoir newTaLDocument;
						for (TaLAvoir obj : taAvoir.getLignes()) {
							newTaLDocument=newDocument.contientMemeParametreCompte(obj);
							if(newTaLDocument!=null){
								newTaLDocument.setQteLDocument(BigDecimal.ONE);
								if(taAvoir.getTtc()==1) {
									newTaLDocument.setPrixULDocument(newTaLDocument.getPrixULDocument().add(obj.getMtTtcLApresRemiseGlobaleDocument()));
								}else{
									newTaLDocument.setPrixULDocument(newTaLDocument.getPrixULDocument().add(obj.getMtHtLApresRemiseGlobaleDocument()));
								}
							}else{
								if(obj.getTaArticle()!=null){
								newTaLDocument=new TaLAvoir(false);
								newTaLDocument=obj.clone();
								newTaLDocument.setLibLDocument(newDocument.getLibelleDocument());
								newTaLDocument.setQteLDocument(BigDecimal.ONE);
								newTaLDocument.setRemTxLDocument(BigDecimal.ZERO);
								if(taAvoir.getTtc()==1) {
									newTaLDocument.setPrixULDocument(obj.getMtTtcLApresRemiseGlobaleDocument());
								}else{
									newTaLDocument.setPrixULDocument(obj.getMtHtLApresRemiseGlobaleDocument());
								}
								newTaLDocument.setTaDocument(newDocument);
								newDocument.addLigne(newTaLDocument);
								}
							}
						}
						newDocument.setTxRemHtDocument(BigDecimal.ZERO);
						newDocument.calculeTvaEtTotaux();
						
						//recuperation des informations propre a la facture
						codePiece = taAvoir.getCodeDocument();
						datePiece = taAvoir.getDateDocument();
						dateEcheance = taAvoir.getDateEchDocument();
						dateLivraisonLigne = taAvoir.getDateDocument();//changer pour erreur 18000 dans epicéa;
//						dateLivraisonLigne = taAvoir.getDateLivDocument();
						libelleLigne = taAvoir.getTaInfosDocument().getNomTiers();
						numCptLigne = taAvoir.getTaInfosDocument().getCodeCompta();
						cptCollectif = taAvoir.getTaInfosDocument().getCompte();
						nomTiers = taAvoir.getTaInfosDocument().getNomTiers();
						adresse1Tiers = taAvoir.getTaInfosDocument().getAdresse1();
						adresse2Tiers = taAvoir.getTaInfosDocument().getAdresse2();
						codePostalTiers = taAvoir.getTaInfosDocument().getCodepostal();
						villeTiers = taAvoir.getTaInfosDocument().getVille();
						//					ttc = taFacture.getTtc();
						//					txRemHtFacture = taFacture.getRemHtFacture();
						//					exportComtpa = taFacture.getExport();

						mtCreditLigne = LibConversion.BigDecimalToDouble(taAvoir.getNetTtcCalc());  

						numCptLigne = "+"+taAvoir.getTaInfosDocument().getCodeCompta();    
						numLignePiece = 1;
						typePiece = TypeAchat;
						mtDebitLigne = 0d;
						qte1 = 0d;
						qte2 = 0d;
						codeTva ="";
						tauxTva = 0d;
						mtDebitTva = 0d;
						mtCreditTva = 0d;
						exigibiliteTva = "";

						refContrepartie = "";
						dateContrepartie = null;
						mtContrepartieDebit = 0d;
						mtContrepartieCredit = 0d;
						mtAffectation = 0d;

						//ecriture de la ligne d'entete dans le fichier d'export
						fw.write(creationLigne(numeroDePiece,
								numLignePiece ,
								typePiece ,
								codePiece ,
								datePiece ,
								numCptLigne ,
								libelleLigne ,
								mtDebitLigne ,
								mtCreditLigne ,
								qte1 ,
								qte2 ,
								codeTva ,
								tauxTva ,
								mtCreditTva ,
								mtDebitTva ,
								dateEcheance ,
								cptCollectif ,
								nomTiers ,
								adresse1Tiers ,
								adresse2Tiers ,
								codePostalTiers ,
								villeTiers ,
								exigibiliteTva ,
								dateLivraisonLigne,
								refContrepartie,
								dateContrepartie,
								mtContrepartieDebit,
								mtContrepartieCredit,
								mtAffectation));
						fw.write(finDeLigne);

						//réinitialisation des variables pour le prochain document
						cptCollectif ="";
						nomTiers ="";
						adresse1Tiers ="";
						adresse2Tiers ="";
						codePostalTiers ="";
						villeTiers="";


						//traitement des lignes
						for (TaLAvoir lf : newDocument.getLignes()) {
							//récupération des valeurs propres à la ligne
							//						if(lf.getTaArticle()!=null){
							typePiece = TypeAchat;
							if(lf.getTaArticle()!=null && lf.getMtHtLApresRemiseGlobaleDocument().signum()!=0){
								libelleLigne = lf.getLibLDocument();
								qte1 = LibConversion.BigDecimalToDouble(lf.getQteLDocument());
								qte2 = LibConversion.BigDecimalToDouble(lf.getQte2LDocument());
								numCptLigne = lf.getCompteLDocument();
								if(numCptLigne==null || numCptLigne.equals("")){
									//transaction.rollback();
									setRetour(false);
									setMessageRetour("\nErreur N° 1\n"
											+"Article (code article : "+lf.getTaArticle().getCodeArticle()+") sans compte dans le document "+taAvoir.getCodeDocument());
									throw new Exception("\nErreur N° 1\n"
											+"Article (code article : "+lf.getTaArticle().getCodeArticle()+") sans compte dans le document "+taAvoir.getCodeDocument());
								}
								//traitement du type de tva du tiers (F ou ue ou horsUE ou Franchise)
								TaTTvaDocDAO daoTvaDoc = new TaTTvaDocDAO(em);
								String codeDoc="";
								if(taAvoir.getTaInfosDocument().getCodeTTvaDoc()==null || taAvoir.getTaInfosDocument().getCodeTTvaDoc().equals(""))
									codeDoc="F";
								else codeDoc=taAvoir.getTaInfosDocument().getCodeTTvaDoc();
								TaTTvaDoc tvaDoc =daoTvaDoc.findByCode(codeDoc);
								String journalTva="";
								if(tvaDoc!=null)journalTva=tvaDoc.getJournalTTvaDoc();
								
//									if(taAvoir.getTaTiers()!=null && taAvoir.getTaTiers().getTaTTvaDoc()!=null){
								if(codeDoc.equalsIgnoreCase("F"))
									codeTva = correspondanceCodeTva(codeDoc,lf.getCodeTvaLDocument(),lf.getCodeTvaLDocument(),numCptLigne,lf.getMtTtcLApresRemiseGlobaleDocument().subtract(lf.getMtHtLApresRemiseGlobaleDocument()));
								else
									codeTva = correspondanceCodeTva(codeDoc,lf.getCodeTvaLDocument(),journalTva,numCptLigne,lf.getMtTtcLApresRemiseGlobaleDocument().subtract(lf.getMtHtLApresRemiseGlobaleDocument()));	
//									}else
//										codeTva = lf.getCodeTvaLDocument();


//								exigibiliteTva = lf.getTaArticle().getTaTTva().getCodeTTva();
//								if(exigibiliteTva == null)exigibiliteTva="D";
								if(codeTva==null)codeTva=""; /* corrigé par isa le 11/09/2014*/
								if(lf.getTaArticle().getTaTTva()!=null)  
									exigibiliteTva = lf.getTaArticle().getTaTTva().getCodeTTva();
								if (exigibiliteTva.equals(""))exigibiliteTva="D";
								
								tauxTva = LibConversion.BigDecimalToDouble(lf.getTauxTvaLDocument());
								mtDebitTva = LibConversion.BigDecimalToDouble(lf.getMtTtcLApresRemiseGlobaleDocument().subtract(lf.getMtHtLApresRemiseGlobaleDocument()));
								mtDebitLigne = LibConversion.BigDecimalToDouble(lf.getMtHtLApresRemiseGlobaleDocument());

								numLignePiece = numLignePiece + 1;
								mtCreditLigne = 0d;
								mtCreditTva = 0d;
								cptCollectif = "";
								nomTiers = "";
								adresse1Tiers = "";
								adresse2Tiers = "";
								codePostalTiers = "";
								villeTiers = "";
								//ecriture de la ligne dans le fichier d'export

								refContrepartie = "";
								dateContrepartie = null;
								mtContrepartieDebit = 0d;
								mtContrepartieCredit = 0d;
								mtAffectation = 0d;

								fw.write(creationLigne(numeroDePiece,
										numLignePiece ,
										typePiece ,
										codePiece ,
										datePiece ,
										numCptLigne ,
										libelleLigne ,
										mtDebitLigne,
										mtCreditLigne ,
										qte1 ,
										qte2 ,
										codeTva ,
										tauxTva ,
										mtDebitTva,
										mtCreditTva ,
										dateEcheance ,
										cptCollectif ,
										nomTiers ,
										adresse1Tiers ,
										adresse2Tiers ,
										codePostalTiers ,
										villeTiers ,
										exigibiliteTva ,
										dateLivraisonLigne,
										refContrepartie,
										dateContrepartie,
										mtContrepartieDebit,
										mtContrepartieCredit,
										mtAffectation));
								fw.write(finDeLigne);
							}
						} 
						if(gererLesPointages){
							//traitement des lignes pour les pointages
							typePiece = typePointage;
							for (TaRAvoir lf : taAvoir.getTaRAvoirs()) {
								//récupération des valeurs propres à la ligne
								TaFacture fac =lf.getTaFacture();
								numFin=initPointage(fac, taAvoir, lf, gererLesDocumenstLies, idAppelant, monitor,numFin);
//								if(fac!=null){
//									if(gererLesDocumenstLies
//											&& idAppelant==null){
//										List<TaFacture> liste = new LinkedList<TaFacture>();
//										liste.add(fac);
//										exportFactureJPA(liste, listComplete, fw, monitor, reExportFacture, numDepart, false, taAvoir.getIdDocument(),em,
//												false);
//									}
//									if(idAppelant==null || idAppelant==fac.getIdDocument()){
//										codePiece=taAvoir.getCodeDocument();
//										datePiece=taAvoir.getDateDocument();
//										libelleLigne = taAvoir.getLibelleDocument();//libelle de la ligne de facture
//										qte1 = 0d;
//										qte2 = 0d;
//										numCptLigne = "+"+taAvoir.getTaInfosDocument().getCodeCompta();
//										cptCollectif = taAvoir.getTaInfosDocument().getCompte();;
//										nomTiers = "";
//										adresse1Tiers = "";
//										adresse2Tiers = "";
//										codePostalTiers = "";
//										villeTiers = "";
//
//										codeTva = "";
//										exigibiliteTva = "";
//										mtDebitTva = 0d;
//										mtDebitLigne = 0d;
//
//										numLignePiece = numLignePiece + 1;
//										mtCreditLigne = LibConversion.BigDecimalToDouble(taAvoir.getNetTtcCalc());
//										mtCreditTva = 0d;	
//
//										refContrepartie = fac.getCodeDocument();
//										dateContrepartie = fac.getDateDocument();
//										mtContrepartieDebit = LibConversion.BigDecimalToDouble(fac.getNetTtcCalc());
//										mtContrepartieCredit = 0d;
//										mtAffectation = LibConversion.BigDecimalToDouble(lf.getAffectation());
//
//										fw.write(creationLigne(numeroDePiece,
//												numLignePiece ,
//												typePiece ,
//												codePiece ,
//												datePiece ,
//												numCptLigne ,
//												libelleLigne ,
//												mtDebitLigne,
//												mtCreditLigne ,
//												qte1 ,
//												qte2 ,
//												codeTva ,
//												tauxTva ,
//												mtDebitTva,
//												mtCreditTva ,
//												dateEcheance ,
//												cptCollectif ,
//												nomTiers ,
//												adresse1Tiers ,
//												adresse2Tiers ,
//												codePostalTiers ,
//												villeTiers ,
//												exigibiliteTva ,
//												dateLivraisonLigne,
//												refContrepartie,
//												dateContrepartie,
//												mtContrepartieDebit,
//												mtContrepartieCredit,
//												mtAffectation));
//										fw.write(finDeLigne);
//										if(lf.getExport()!=1)lf.setExport(1);
//										listCompletePointage.add(new IDocumentTiers[]{taAvoir,fac});
//										listeVerif.add(fac);
//									}
//								}
							} 
						}

						if(taAvoir.getExport()!=1)taAvoir.setExport(1);
						taAvoirDAO.enregistrerMerge(taAvoir);
						listComplete.add(taAvoir.getCodeDocument());
						listeVerif.add(taAvoir);
					}
				}
				if(monitor!=null) monitor.worked(1);
			}
			//numFin=numeroDePiece;
//			taAvoirDAO.commit(transaction);

			//bw.close();
			//fw.close();
		} catch(IOException ioe){
			logger.error("",ioe);
			setRetour(false);
		} 
		//		catch (Exception e) {
		//			logger.error("",e);
		//		}
		return numFin;
	}

	private String creationLigne(Integer numPiece,
			Integer numLignePiece ,
			String typePiece ,
			String codePiece ,
			Date datePiece ,
			String numCptLigne ,
			String libelleLigne ,
			Double mtDebitLigne ,
			Double mtCreditLigne ,
			Double qte1 ,
			Double qte2 ,
			String codeTva ,
			Double tauxTva ,
			Double mtDebitTva ,
			Double mtCreditTva ,
			Date dateEcheance ,
			String cptCollectif ,
			String nomTiers ,
			String adresse1Tiers ,
			String adresse2Tiers ,
			String codePostalTiers ,
			String villeTiers ,
			String exigibiliteTva ,
			Date dateLivraisonLigne,
			String refContrepartie,			
			Date dateContrepartie ,
			Double mtContrepartieDebit ,
			Double mtContrepartieCredit,
			Double mtAffectation) {
		StringBuffer ligne = new StringBuffer("");
		char c = ' ';
		//ECRITURE DE LA FACTURE DANS LE FICHIER D'EXPORTATION
		//Entete
		//1
		ligne.append(completeChaine(String.valueOf(numPiece),5,c,0));
		//2
		ligne.append(completeChaine(String.valueOf(numLignePiece),3,c,0));
		//3
		ligne.append(completeChaine(String.valueOf(typePiece),1,c,0));
		//4
		ligne.append(completeChaine(String.valueOf(codePiece),9,c,1));
		//5
		ligne.append(completeChaine(LibDate.dateToStringAA(datePiece),8,c,0));
		//6
		ligne.append(completeChaine(String.valueOf(numCptLigne),8,c,0));
		//7
		ligne.append(completeChaine(String.valueOf(libelleLigne),23,c,1));
		//8
		ligne.append(completeChaine(String.valueOf(mtDebitLigne),12,c,0));
		//9
		ligne.append(completeChaine(String.valueOf(mtCreditLigne),12,c,0));
		//10
		ligne.append(completeChaine(String.valueOf(qte1),9,c,0));
		//11
		ligne.append(completeChaine(String.valueOf(qte2),9,c,0));
		//12
		ligne.append(completeChaine(String.valueOf(codeTva),1,c,0));
		//13
		ligne.append(completeChaine(String.valueOf(tauxTva),6,c,0));
		//14
		ligne.append(completeChaine(String.valueOf(mtDebitTva),11,c,0));
		//15
		ligne.append(completeChaine(String.valueOf(mtCreditTva),11,c,0));
		//16
		ligne.append(completeChaine(LibDate.dateToStringAA(dateEcheance),8,c,0));
		//17
		ligne.append(completeChaine(String.valueOf(cptCollectif),8,c,0));
		//18
		ligne.append(completeChaine(String.valueOf(nomTiers),30,c,1));
		//19
		ligne.append(completeChaine(String.valueOf(adresse1Tiers),30,c,1));
		//20
		ligne.append(completeChaine(String.valueOf(adresse2Tiers),30,c,1));
		//21
		ligne.append(completeChaine(String.valueOf(codePostalTiers),5,c,1));
		//22
		ligne.append(completeChaine(String.valueOf(villeTiers),25,c,1));
		//23
		ligne.append(completeChaine(String.valueOf(exigibiliteTva),1,c,0));
		//24
		ligne.append(completeChaine(LibDate.dateToStringAA(dateLivraisonLigne),8,c,0));
		//25
		ligne.append(completeChaine(String.valueOf(refContrepartie),9,c,1));
		//26
		ligne.append(completeChaine(LibDate.dateToStringAA(dateContrepartie),8,c,0));
		//27
		ligne.append(completeChaine(String.valueOf(mtContrepartieDebit),11,c,0));
		//28
		ligne.append(completeChaine(String.valueOf(mtContrepartieCredit),11,c,0));
		//29
		ligne.append(completeChaine(String.valueOf(mtAffectation), 11, c, 0));

		return ligne.toString();
	}


	private String creationLignePointage(Integer numReglement,
			Integer numLignePiece ,
			String typePiece ,
			String refReglement,
			Date dateReglement ,
			String tiersReglement ,
			Double mtAffectationReglementDebit ,
			Double mtAffectationReglementCredit ,
			Integer numContrepartie,
			String refContrepartie,			
			Date dateContrepartie ,
			String tiersContrepartie ,
			Double mtAffectationContrepartieDebit ,
			Double mtAffectationContrepartieCredit) {
		StringBuffer ligne = new StringBuffer("");
		char c = ' ';
		//ECRITURE DU POINTAGE DANS LE FICHIER D'EXPORTATION
		//Entete
		//1
		ligne.append(completeChaine(String.valueOf(numReglement),5,c,0));
		//2
		ligne.append(completeChaine(String.valueOf(numLignePiece),3,c,0));
		//3
		ligne.append(completeChaine(String.valueOf(typePiece),1,c,0));
		//4
		ligne.append(completeChaine(String.valueOf(refReglement),9,c,1));
		//5
		ligne.append(completeChaine(LibDate.dateToStringAA(dateReglement),8,c,0));
		//6
		ligne.append(completeChaine(String.valueOf(tiersReglement),8,c,0));
		//7
		ligne.append(completeChaine(String.valueOf(mtAffectationReglementDebit),12,c,0));
		//8
		ligne.append(completeChaine(String.valueOf(mtAffectationReglementDebit),12,c,0));
		//9
		ligne.append(completeChaine(String.valueOf(numContrepartie),5,c,0));
		//10
		ligne.append(completeChaine(String.valueOf(refContrepartie),9,c,1));
		//11
		ligne.append(completeChaine(LibDate.dateToStringAA(dateContrepartie),8,c,0));
		//12
		ligne.append(completeChaine(String.valueOf(tiersContrepartie),8,c,0));
		//13
		ligne.append(completeChaine(String.valueOf(mtAffectationContrepartieDebit),12,c,0));
		//14
		ligne.append(completeChaine(String.valueOf(mtAffectationContrepartieCredit),12,c,0));

		return ligne.toString();
	}


	/**
	 * Si la <code>chaine</code> de caractère est plus courte que <code>longueur</code>, ajoute <code>c</code>
	 *  autant de fois que necessaire pour atteindre la longueur demandée. Si la chaine est plus longue, elle est coupée.
	 * @param chaine - chaine initiale
	 * @param longueur - longeur souhaitée
	 * @param c - caractère à répéter pour compléter la chaine
	 * @param position - 0 la chaine est complétée par l'avant, 1 par l'arrière
	 * @return 
	 */
	public String completeChaine(String chaine, int longueur, char c, int position) {
		if(chaine==null) chaine="";
		if(chaine.length()<longueur) { //plus court
			int nbCaractere = longueur - chaine.length();
			StringBuffer complement = new StringBuffer("");
			for(int i=0; i<nbCaractere; i++) {
				complement.append(c);
			}
			if(position==0) {
				chaine=complement.toString()+chaine;
			} else if(position==1) {
				chaine=chaine+complement.toString();
			}
		} else { //plus long
			chaine = chaine.substring(0,longueur);
		}
		return chaine;
	}

	public String getMessageRetour() {
		return messageRetour;
	}

	public void setMessageRetour(String messageRetour) {
		this.messageRetour = messageRetour;
	}

	public String getLocationFichier() {
		return locationFichier;
	}

	public void setLocationFichier(String locationFichier) {
		this.locationFichier = locationFichier;
	}

	public Boolean getRetour() {
		return retour;
	}

	public void setRetour(Boolean retour) {
		this.retour = retour;
	}
	
	
	public int exportAcompteJPA(List<TaAcompte> idAcompteAExporter,List<String> listComplete,FileWriter fw,IProgressMonitor monitor,
			int reExport,int numDepart,boolean gererPointages,Integer idAppelant,EntityManager em,boolean gererLesDocumenstLies) throws Exception {
		int numFin=numDepart;
		try {   



			Boolean gererLesPointages = gererPointages;

			TaAcompteDAO taDocDAO = new TaAcompteDAO(em);
			TaAcompte taDoc = null;


			numeroDePiece = numFin;

			

			boolean nouvellePiece = false;

//			EntityTransaction transaction = taDocDAO.getEntityManager().getTransaction();
//			taDocDAO.begin(transaction);
			//taRReglementDAO.begin(transaction);

			for(int i=0; i<idAcompteAExporter.size(); i++) {
				taDoc = idAcompteAExporter.get(i);
				if(listComplete.indexOf(taDoc.getCodeDocument())==-1){


					if(taDoc.getExport()!=1 || reExport==1) { /* facture pas deja exportee ou reexportation */
						nouvellePiece = true;

						if(nouvellePiece) {
							numeroDePiece++;
							nouvellePiece = false;
							numFin++;
						}

						//recuperation des informations propre a la facture
						codePiece = taDoc.getCodeDocument();
						datePiece = taDoc.getDateDocument();
						dateEcheance = taDoc.getDateEchDocument();
						dateLivraisonLigne = taDoc.getDateLivDocument();
						libelleLigne = taDoc.getTaInfosDocument().getNomTiers();
						if(taDoc.getTaTPaiement()!=null)
							numCptLigne = taDoc.getTaTPaiement().getCompte();//taDoc.getTaCompteBanque().getCptcomptable();
						
						cptCollectif = taDoc.getTaInfosDocument().getCompte();
						nomTiers = taDoc.getTaInfosDocument().getNomTiers();
						adresse1Tiers = taDoc.getTaInfosDocument().getAdresse1();
						adresse2Tiers = taDoc.getTaInfosDocument().getAdresse2();
						codePostalTiers = taDoc.getTaInfosDocument().getCodepostal();
						villeTiers = taDoc.getTaInfosDocument().getVille();

						mtCreditLigne = 0d;  

						numLignePiece = 1;
						typePiece = TypeReglement;
						mtDebitLigne = LibConversion.BigDecimalToDouble(taDoc.getNetTtcCalc());
						qte1 = 0d;
						qte2 = 0d;
						codeTva ="";
						tauxTva = 0d;
						mtDebitTva = 0d;
						mtCreditTva = 0d;
						exigibiliteTva = "";

						refContrepartie = "";
						dateContrepartie = null;
						mtContrepartieDebit = 0d;
						mtContrepartieCredit = 0d;
						mtAffectation = 0d;

						//ecriture de la ligne d'entete dans le fichier d'export
						fw.write(creationLigne(numeroDePiece,
								numLignePiece ,
								typePiece ,
								codePiece ,
								datePiece ,
								numCptLigne ,
								libelleLigne ,
								mtDebitLigne ,
								mtCreditLigne ,
								qte1 ,
								qte2 ,
								codeTva ,
								tauxTva ,
								mtCreditTva ,
								mtDebitTva ,
								dateEcheance ,
								cptCollectif ,
								nomTiers ,
								adresse1Tiers ,
								adresse2Tiers ,
								codePostalTiers ,
								villeTiers ,
								exigibiliteTva ,
								dateLivraisonLigne,
								refContrepartie,
								dateContrepartie,
								mtContrepartieDebit,
								mtContrepartieCredit,
								mtAffectation));
						fw.write(finDeLigne);

						//réinitialisation des variables pour le prochain document
						cptCollectif ="";
						nomTiers ="";
						adresse1Tiers ="";
						adresse2Tiers ="";
						codePostalTiers ="";
						villeTiers="";

						//traitement de la ligne de contrepartie (tiers)
						libelleLigne = taDoc.getLibelleDocument();//libelle de la ligne de facture
						qte1 = 0d;
						qte2 = 0d;
						numCptLigne = "+"+taDoc.getTaInfosDocument().getCodeCompta();
						cptCollectif = taDoc.getTaInfosDocument().getCompte();
						nomTiers = taDoc.getTaInfosDocument().getNomTiers();
						adresse1Tiers = taDoc.getTaInfosDocument().getAdresse1();
						adresse2Tiers = taDoc.getTaInfosDocument().getAdresse2();
						codePostalTiers = taDoc.getTaInfosDocument().getCodepostal();
						villeTiers = taDoc.getTaInfosDocument().getVille();

						codeTva = "";
						exigibiliteTva = "";
						mtDebitTva = 0d;
						mtDebitLigne = 0d;

						numLignePiece = numLignePiece + 1;
						mtCreditLigne = LibConversion.BigDecimalToDouble(taDoc.getNetTtcCalc());
						mtCreditTva = 0d;

						refContrepartie = "";
						dateContrepartie = null;
						mtContrepartieDebit = 0d;
						mtContrepartieCredit = 0d;
						mtAffectation = 0d;

						fw.write(creationLigne(numeroDePiece,
								numLignePiece ,
								typePiece ,
								codePiece ,
								datePiece ,
								numCptLigne ,
								libelleLigne ,
								mtDebitLigne,
								mtCreditLigne ,
								qte1 ,
								qte2 ,
								codeTva ,
								tauxTva ,
								mtDebitTva,
								mtCreditTva ,
								dateEcheance ,
								cptCollectif ,
								nomTiers ,
								adresse1Tiers ,
								adresse2Tiers ,
								codePostalTiers ,
								villeTiers ,
								exigibiliteTva ,
								dateLivraisonLigne,
								refContrepartie,
								dateContrepartie,
								mtContrepartieDebit,
								mtContrepartieCredit,
								mtAffectation));
						fw.write(finDeLigne);

						if(gererLesPointages){				

							//traitement des lignes pour les pointages
							typePiece = typePointage;	
							for (TaRAcompte lf : taDoc.getTaRAcomptes()) {
								//récupération des valeurs propres à la ligne
								TaFacture fac =lf.getTaFacture();
								numFin=initPointage(fac, taDoc, lf, gererLesDocumenstLies, idAppelant, monitor,numFin);
							} 
						}
						if(taDoc.getExport()!=1)taDoc.setExport(1);
						taDocDAO.enregistrerMerge(taDoc);
						listComplete.add(taDoc.getCodeDocument());
						listeVerif.add(taDoc);
					}
				}
				if(monitor!=null) monitor.worked(1);
			}
			//numFin=numeroDePiece;
//			if(idAppelant==null)
//				taDocDAO.commit(transaction);

		} catch(IOException ioe){
			logger.error("",ioe);
			setRetour(false);
		} 

		return numFin;
	}

	public int exportReglementJPA(List<TaReglement> idReglementAExporter,List<String> listComplete,FileWriter fw,IProgressMonitor monitor,
			int reExport,int numDepart,boolean gererPointages,Integer idAppelant,EntityManager em,boolean gererLesDocumenstLies) throws Exception {
		int numFin=numDepart;
		try {     


			Boolean gererLesPointages = gererPointages;

			TaReglementDAO taDocDAO = new TaReglementDAO(em);
			TaReglement taDoc = null;
			TaRReglementDAO taRReglementDAO = new TaRReglementDAO(em);

			numeroDePiece = numFin;

		

			boolean nouvellePiece = false;

//			EntityTransaction transaction = taDocDAO.getEntityManager().getTransaction();
//			taDocDAO.begin(transaction);
			//taRReglementDAO.begin(transaction);

			for(int i=0; i<idReglementAExporter.size(); i++) {
				taDoc = idReglementAExporter.get(i);
				if(listComplete.indexOf(taDoc.getCodeDocument())==-1){
					TaRemiseDAO taRemiseDao = new TaRemiseDAO(em);

					if(taDoc.getExport()!=1 || reExport==1) { /* facture pas deja exportee ou reexportation */
//						if(idAppelant==null && taRemiseDao.findSiReglementDansRemise(taDoc.getCodeDocument()).size()==0){
							if( taRemiseDao.findSiReglementDansRemise(taDoc.getCodeDocument()).size()==0){
							nouvellePiece = true;

							if(nouvellePiece) {
								numeroDePiece++;
								nouvellePiece = false;
								numFin++;
							}

							//recuperation des informations propre a la facture
							codePiece = taDoc.getCodeDocument();
							datePiece = taDoc.getDateDocument();
							dateEcheance = taDoc.getDateEchDocument();
							dateLivraisonLigne = taDoc.getDateLivDocument();
							if(taDoc.getLibelleDocument()!=null)
								libelleLigne = taDoc.getLibelleDocument().replace("\r","").replace("\n","");
							else
								libelleLigne = "";
							if(taDoc.getTaTPaiement()!=null)
								numCptLigne = taDoc.getTaTPaiement().getCompte();//taDoc.getTaCompteBanque().getCptcomptable();
							
							//trouver le tiers lié au règlement
							TaTiers tiers=taDoc.getTaTiers();

							cptCollectif = "";
							nomTiers = "";
							adresse1Tiers = "";
							adresse2Tiers ="";
							codePostalTiers = "";
							villeTiers = "";
							
					

							mtCreditLigne = 0d;  

							numLignePiece = 1;
							if(numCptLigne.startsWith("5"))
							typePiece = TypeReglement;
							else typePiece = TypeOd;
							mtDebitLigne = LibConversion.BigDecimalToDouble(taDoc.getNetTtcCalc());
							qte1 = 0d;
							qte2 = 0d;
							codeTva ="";
							tauxTva = 0d;
							mtDebitTva = 0d;
							mtCreditTva = 0d;
							exigibiliteTva = "";

							refContrepartie = "";
							dateContrepartie = null;
							mtContrepartieDebit = 0d;
							mtContrepartieCredit = 0d;
							mtAffectation = 0d;

							//ecriture de la ligne d'entete dans le fichier d'export
							fw.write(creationLigne(numeroDePiece,
									numLignePiece ,
									typePiece ,
									codePiece ,
									datePiece ,
									numCptLigne ,
									libelleLigne ,
									mtDebitLigne ,
									mtCreditLigne ,
									qte1 ,
									qte2 ,
									codeTva ,
									tauxTva ,
									mtCreditTva ,
									mtDebitTva ,
									dateEcheance ,
									cptCollectif ,
									nomTiers ,
									adresse1Tiers ,
									adresse2Tiers ,
									codePostalTiers ,
									villeTiers ,
									exigibiliteTva ,
									dateLivraisonLigne,
									refContrepartie,
									dateContrepartie,
									mtContrepartieDebit,
									mtContrepartieCredit,
									mtAffectation));
							fw.write(finDeLigne);

							//réinitialisation des variables pour le prochain document
							cptCollectif ="";
							nomTiers ="";
							adresse1Tiers ="";
							adresse2Tiers ="";
							codePostalTiers ="";
							villeTiers="";

							//traitement de la ligne de contrepartie (tiers)
							if(taDoc.getLibelleDocument()!=null)
								libelleLigne = taDoc.getLibelleDocument().replace("\r","").replace("\n","");//libelle de la ligne de facture
							else
								libelleLigne = "";
							qte1 = 0d;
							qte2 = 0d;
							numCptLigne = "+"+taDoc.getTaTiers().getCodeCompta();
							cptCollectif = taDoc.getTaTiers().getCompte();
							nomTiers = taDoc.getTaTiers().getNomTiers();
							adresse1Tiers = taDoc.getTaTiers().getTaAdresse().getAdresse1Adresse();
							adresse2Tiers = taDoc.getTaTiers().getTaAdresse().getAdresse2Adresse();
							codePostalTiers = taDoc.getTaTiers().getTaAdresse().getCodepostalAdresse();
							villeTiers = taDoc.getTaTiers().getTaAdresse().getVilleAdresse();

							codeTva = "";
							exigibiliteTva = "";
							mtDebitTva = 0d;
							mtDebitLigne = 0d;

							numLignePiece = numLignePiece + 1;
							mtCreditLigne = LibConversion.BigDecimalToDouble(taDoc.getNetTtcCalc());
							mtCreditTva = 0d;

							refContrepartie = "";
							dateContrepartie = null;
							mtContrepartieDebit = 0d;
							mtContrepartieCredit = 0d;
							mtAffectation = 0d;

							fw.write(creationLigne(numeroDePiece,
									numLignePiece ,
									typePiece ,
									codePiece ,
									datePiece ,
									numCptLigne ,
									libelleLigne ,
									mtDebitLigne,
									mtCreditLigne ,
									qte1 ,
									qte2 ,
									codeTva ,
									tauxTva ,
									mtDebitTva,
									mtCreditTva ,
									dateEcheance ,
									cptCollectif ,
									nomTiers ,
									adresse1Tiers ,
									adresse2Tiers ,
									codePostalTiers ,
									villeTiers ,
									exigibiliteTva ,
									dateLivraisonLigne,
									refContrepartie,
									dateContrepartie,
									mtContrepartieDebit,
									mtContrepartieCredit,
									mtAffectation));
							fw.write(finDeLigne);

							if(gererLesPointages){
								typePiece = typePointage;	
								//traitement des lignes pour les pointages
								for (TaRReglement lf : taDoc.getTaRReglements()) {
									//récupération des valeurs propres à la ligne
									TaFacture fac =lf.getTaFacture();
									numFin=initPointage(fac, taDoc, lf, gererLesDocumenstLies, idAppelant, monitor,numFin);
//									if(fac!=null ){
//										if(gererLesDocumenstLies
//												&& idAppelant==null){
//											List<TaFacture> liste = new LinkedList<TaFacture>();
//											liste.add(fac);
//											exportFactureJPA(liste, listComplete, fw, monitor, reExportFacture, numDepart, false, taDoc.getIdDocument(),em,false);
//										}
//										if(idAppelant==null || idAppelant==fac.getIdDocument()){
//											codePiece=taDoc.getCodeDocument();
//											datePiece=taDoc.getDateDocument();
//											libelleLigne = lf.getTaReglement().getLibelleDocument();//libelle de la ligne de facture
//											qte1 = 0d;
//											qte2 = 0d;
//											numCptLigne = "+"+lf.getTaReglement().getTaTiers().getCodeTiers();
//											cptCollectif = lf.getTaReglement().getTaTiers().getCompte();
//											nomTiers = "";
//											adresse1Tiers = "";
//											adresse2Tiers = "";
//											codePostalTiers = "";
//											villeTiers = "";
//
//											codeTva = "";
//											exigibiliteTva = "";
//											mtDebitTva = 0d;
//											mtDebitLigne = 0d;
//
//											numLignePiece = numLignePiece + 1;
//											mtCreditLigne = LibConversion.BigDecimalToDouble(lf.getTaReglement().getNetTtcCalc());
//											mtCreditTva = 0d;	
//
//											refContrepartie = fac.getCodeDocument();
//											dateContrepartie = fac.getDateDocument();
//											mtContrepartieDebit = LibConversion.BigDecimalToDouble(fac.getNetTtcCalc());
//											mtContrepartieCredit = 0d;
//											mtAffectation = LibConversion.BigDecimalToDouble(lf.getAffectation());
//
//											fw.write(creationLigne(numeroDePiece,
//													numLignePiece ,
//													typePiece ,
//													codePiece ,
//													datePiece ,
//													numCptLigne ,
//													libelleLigne ,
//													mtDebitLigne,
//													mtCreditLigne ,
//													qte1 ,
//													qte2 ,
//													codeTva ,
//													tauxTva ,
//													mtDebitTva,
//													mtCreditTva ,
//													dateEcheance ,
//													cptCollectif ,
//													nomTiers ,
//													adresse1Tiers ,
//													adresse2Tiers ,
//													codePostalTiers ,
//													villeTiers ,
//													exigibiliteTva ,
//													dateLivraisonLigne,
//													refContrepartie,
//													dateContrepartie,
//													mtContrepartieDebit,
//													mtContrepartieCredit,
//													mtAffectation));
//											fw.write(finDeLigne);
//											if(lf.getExport()!=1)lf.setExport(1);
////											listCompletePointage.add(codePiece+separateurListePointage+refContrepartie);
//											listCompletePointage.add(new IDocumentTiers[]{taDoc,fac});
//											listeVerif.add(fac);
//										}
//									}
								} 
							}
							if(taDoc.getExport()!=1)taDoc.setExport(1);
							taDoc=taDocDAO.enregistrerMerge(taDoc);
							listComplete.add(taDoc.getCodeDocument());
							listeVerif.add(taDoc);
						}
					}
				}
				if(monitor!=null) monitor.worked(1);
			}
			//numFin=numeroDePiece;
//			if(idAppelant==null)
//				taDocDAO.commit(transaction);

		} catch(IOException ioe){
			logger.error("",ioe);
			setRetour(false);
		} 

		return numFin;
	}

	public int exportRemiseJPA(List<TaRemise> idRemiseAExporter,List<String> listComplete,FileWriter fw,IProgressMonitor monitor,
			int reExport,int numDepart,boolean gererPointages,Integer idAppelant,EntityManager em,boolean gererLesDocumenstLies) throws Exception {
		int numFin=numDepart;
		try {      

			Boolean gererLesPointages = gererPointages;

			TaRemiseDAO taDocDAO = new TaRemiseDAO(em);
			TaRemise taDoc = null;


			numeroDePiece = numFin;

			

			boolean nouvellePiece = false;

//			EntityTransaction transaction = taDocDAO.getEntityManager().getTransaction();
//			taDocDAO.begin(transaction);

			for(int i=0; i<idRemiseAExporter.size(); i++) {
				taDoc = idRemiseAExporter.get(i);

				if(listComplete.indexOf(taDoc.getCodeDocument())==-1){

					if(taDoc.getExport()!=1 || reExport==1) { /* remise pas deja exportee ou reexportation */
						nouvellePiece = true;

						if(nouvellePiece) {
							numeroDePiece++;
							nouvellePiece = false;
							numFin++;
						}

						//recuperation des informations propre a la REMISE
						codePiece = taDoc.getCodeDocument();
						datePiece = taDoc.getDateDocument();
						dateEcheance = taDoc.getDateEchDocument();
						dateLivraisonLigne = taDoc.getDateLivDocument();
//						libelleLigne = taDoc.getLibelleDocument();
						if(taDoc.getLibelleDocument()!=null)
							libelleLigne = taDoc.getLibelleDocument().replace("\r","").replace("\n","");
						else
							libelleLigne = "";
						if(taDoc.getTaTPaiement()!=null)
							numCptLigne = taDoc.getTaTPaiement().getCompte();//taDoc.getTaCompteBanque().getCptcomptable();
						cptCollectif = "";
						nomTiers = "";
						adresse1Tiers = "";
						adresse2Tiers ="";
						codePostalTiers = "";
						villeTiers = "";

						mtCreditLigne = 0d;  

						numLignePiece = 1;
						typePiece = TypeReglement;
						mtDebitLigne = LibConversion.BigDecimalToDouble(taDoc.getNetTtcCalc());
						qte1 = 0d;
						qte2 = 0d;
						codeTva ="";
						tauxTva = 0d;
						mtDebitTva = 0d;
						mtCreditTva = 0d;
						exigibiliteTva = "";

						refContrepartie = "";
						dateContrepartie = null;
						mtContrepartieDebit = 0d;
						mtContrepartieCredit = 0d;
						mtAffectation = 0d;

						//ecriture de la ligne d'entete dans le fichier d'export
						fw.write(creationLigne(numeroDePiece,
								numLignePiece ,
								typePiece ,
								codePiece ,
								datePiece ,
								numCptLigne ,
								libelleLigne ,
								mtDebitLigne ,
								mtCreditLigne ,
								qte1 ,
								qte2 ,
								codeTva ,
								tauxTva ,
								mtCreditTva ,
								mtDebitTva ,
								dateEcheance ,
								cptCollectif ,
								nomTiers ,
								adresse1Tiers ,
								adresse2Tiers ,
								codePostalTiers ,
								villeTiers ,
								exigibiliteTva ,
								dateLivraisonLigne,
								refContrepartie,
								dateContrepartie,
								mtContrepartieDebit,
								mtContrepartieCredit,
								mtAffectation));

						fw.write(finDeLigne);

						//réinitialisation des variables pour le prochain document
						cptCollectif ="";
						nomTiers ="";
						adresse1Tiers ="";
						adresse2Tiers ="";
						codePostalTiers ="";
						villeTiers="";



						//traitement pour exporter les relations avec les factures					
						//traitement des lignes
						for (TaLRemise lf : taDoc.getTaLRemises()) {
							//récupération des valeurs propres à la ligne
							if(lf.getTaReglement()!=null && listComplete.indexOf(lf.getTaReglement().getCodeDocument())==-1){
								//						if(lf.getTaReglement()!=null){//on traite le réglement
								//on traite la ligne de contrepartie (tiers)
								typePiece = TypeReglement;
								codePiece=taDoc.getCodeDocument();
								datePiece=taDoc.getDateDocument();
//								libelleLigne = lf.getTaReglement().getLibelleDocument();//libelle de la ligne de facture
								if(lf.getTaReglement().getLibelleDocument()!=null)
									libelleLigne = lf.getTaReglement().getLibelleDocument().replace("\r","").replace("\n","");
								else
									libelleLigne = "";
								qte1 = 0d;
								qte2 = 0d;
								numCptLigne = "+"+lf.getTaReglement().getTaTiers().getCodeTiers();
								cptCollectif = lf.getTaReglement().getTaTiers().getCompte();
								nomTiers = "";
								adresse1Tiers = "";
								adresse2Tiers = "";
								codePostalTiers = "";
								villeTiers = "";

								codeTva = "";
								exigibiliteTva = "";
								mtDebitTva = 0d;
								mtDebitLigne = 0d;

								numLignePiece = numLignePiece + 1;
								mtCreditLigne = LibConversion.BigDecimalToDouble(lf.getTaReglement().getNetTtcCalc());
								mtCreditTva = 0d;

								refContrepartie = "";
								dateContrepartie = null;
								mtContrepartieDebit = 0d;
								mtContrepartieCredit = 0d;
								mtAffectation = 0d;

								fw.write(creationLigne(numeroDePiece,
										numLignePiece ,
										typePiece ,
										codePiece ,
										datePiece ,
										numCptLigne ,
										libelleLigne ,
										mtDebitLigne,
										mtCreditLigne ,
										qte1 ,
										qte2 ,
										codeTva ,
										tauxTva ,
										mtDebitTva,
										mtCreditTva ,
										dateEcheance ,
										cptCollectif ,
										nomTiers ,
										adresse1Tiers ,
										adresse2Tiers ,
										codePostalTiers ,
										villeTiers ,
										exigibiliteTva ,
										dateLivraisonLigne,
										refContrepartie,
										dateContrepartie,
										mtContrepartieDebit,
										mtContrepartieCredit,
										mtAffectation));
								fw.write(finDeLigne);

								//traitment des pointages si on les gere
								if(gererLesPointages){
									typePiece = typePointage;	
									for (TaRReglement Relation : lf.getTaReglement().getTaRReglements()) {
										TaFacture fac =Relation.getTaFacture();
										idAppelant=fac.getIdDocument();
										numFin=initPointage(fac, lf.getTaReglement(), Relation, gererLesDocumenstLies, idAppelant, monitor,numFin);
//										if(fac!=null ){
//											if(gererLesDocumenstLies
//													&& idAppelant==null){
//												List<TaFacture> liste = new LinkedList<TaFacture>();
//												liste.add(fac);
//												exportFactureJPA(liste, listComplete, fw, monitor, reExportFacture, numDepart, false, taDoc.getIdDocument(),em,false);
//											}
////											if(idAppelant==null || idAppelant==fac.getIdDocument())
//											{
//											codePiece=taDoc.getCodeDocument();
//											datePiece=taDoc.getDateDocument();
//											libelleLigne = lf.getTaReglement().getLibelleDocument();//libelle de la ligne de facture
//											qte1 = 0d;
//											qte2 = 0d;
//											numCptLigne = "+"+lf.getTaReglement().getTaTiers().getCodeTiers();
//											cptCollectif = lf.getTaReglement().getTaTiers().getCompte();
//											nomTiers = "";
//											adresse1Tiers = "";
//											adresse2Tiers = "";
//											codePostalTiers = "";
//											villeTiers = "";
//
//											codeTva = "";
//											exigibiliteTva = "";
//											mtDebitTva = 0d;
//											mtDebitLigne = 0d;
//
//											numLignePiece = numLignePiece + 1;
//											mtCreditLigne = LibConversion.BigDecimalToDouble(lf.getTaReglement().getNetTtcCalc());
//											mtCreditTva = 0d;	
//
//											refContrepartie = fac.getCodeDocument();
//											dateContrepartie = fac.getDateDocument();
//											mtContrepartieDebit = LibConversion.BigDecimalToDouble(fac.getNetTtcCalc());
//											mtContrepartieCredit = 0d;
//											mtAffectation = LibConversion.BigDecimalToDouble(Relation.getAffectation());
//
//											fw.write(creationLigne(numeroDePiece,
//													numLignePiece ,
//													typePiece ,
//													codePiece ,
//													datePiece ,
//													numCptLigne ,
//													libelleLigne ,
//													mtDebitLigne,
//													mtCreditLigne ,
//													qte1 ,
//													qte2 ,
//													codeTva ,
//													tauxTva ,
//													mtDebitTva,
//													mtCreditTva ,
//													dateEcheance ,
//													cptCollectif ,
//													nomTiers ,
//													adresse1Tiers ,
//													adresse2Tiers ,
//													codePostalTiers ,
//													villeTiers ,
//													exigibiliteTva ,
//													dateLivraisonLigne,
//													refContrepartie,
//													dateContrepartie,
//													mtContrepartieDebit,
//													mtContrepartieCredit,
//													mtAffectation));
//											fw.write(finDeLigne);
//											if(Relation.getExport()!=1)Relation.setExport(1);
//											listCompletePointage.add(new IDocumentTiers[]{lf.getTaReglement(),fac});
////											listCompletePointage.add(codePiece+separateurListePointage+refContrepartie);
//											listeVerif.add(fac);
//										}
//										}
									}
								}//fin si on gere les pointages
								if(lf.getTaReglement().getExport()!=1)lf.getTaReglement().setExport(1);
								listComplete.add(lf.getTaReglement().getCodeDocument());
								listeVerif.add(lf.getTaReglement());
							}//fin on traite le reglement

							if(lf.getTaAcompte()!=null && listComplete.indexOf(lf.getTaAcompte().getCodeDocument())==-1){
								//						if(lf.getTaAcompte()!=null){//on traite l'acompte
								//on traite la ligne de contrepartie (tiers)
								typePiece = TypeReglement;
								codePiece=taDoc.getCodeDocument();
								datePiece=taDoc.getDateDocument();
								libelleLigne = lf.getTaAcompte().getLibelleDocument();//libelle de la ligne de facture
								qte1 = 0d;
								qte2 = 0d;
								numCptLigne = "+"+lf.getTaAcompte().getTaTiers().getCodeTiers();
								cptCollectif = lf.getTaAcompte().getTaTiers().getCompte();
								nomTiers = "";
								adresse1Tiers = "";
								adresse2Tiers = "";
								codePostalTiers = "";
								villeTiers = "";

								codeTva = "";
								exigibiliteTva = "";
								mtDebitTva = 0d;
								mtDebitLigne = 0d;

								numLignePiece = numLignePiece + 1;
								mtCreditLigne = LibConversion.BigDecimalToDouble(lf.getTaAcompte().getNetTtcCalc());
								mtCreditTva = 0d;

								refContrepartie = "";
								dateContrepartie = null;
								mtContrepartieDebit = 0d;
								mtContrepartieCredit = 0d;
								mtAffectation = 0d;

								fw.write(creationLigne(numeroDePiece,
										numLignePiece ,
										typePiece ,
										codePiece ,
										datePiece ,
										numCptLigne ,
										libelleLigne ,
										mtDebitLigne,
										mtCreditLigne ,
										qte1 ,
										qte2 ,
										codeTva ,
										tauxTva ,
										mtDebitTva,
										mtCreditTva ,
										dateEcheance ,
										cptCollectif ,
										nomTiers ,
										adresse1Tiers ,
										adresse2Tiers ,
										codePostalTiers ,
										villeTiers ,
										exigibiliteTva ,
										dateLivraisonLigne,
										refContrepartie,
										dateContrepartie,
										mtContrepartieDebit,
										mtContrepartieCredit,
										mtAffectation));
								fw.write(finDeLigne);

								//traitment des pointages si on les gere
								if(gererLesPointages){
									typePiece = typePointage;
									for (TaRAcompte Relation : lf.getTaAcompte().getTaRAcomptes()) {
										TaFacture fac =Relation.getTaFacture();
										numFin=initPointage(fac, lf.getTaAcompte(), Relation, gererLesDocumenstLies, idAppelant, monitor,numFin);
//										if(fac!=null ){
//											if(gererLesDocumenstLies
//													&& idAppelant==null){
//												List<TaFacture> liste = new LinkedList<TaFacture>();
//												liste.add(fac);
//												exportFactureJPA(liste, listComplete, fw, monitor, reExportFacture, numDepart, false, taDoc.getIdDocument(),em,false);
//											}
////											if(idAppelant==null || idAppelant==fac.getIdDocument())
//											{
//												codePiece=taDoc.getCodeDocument();
//												datePiece=taDoc.getDateDocument();
//												libelleLigne = lf.getTaAcompte().getLibelleDocument();//libelle de la ligne de facture
//												qte1 = 0d;
//												qte2 = 0d;
//												numCptLigne = "+"+lf.getTaAcompte().getTaTiers().getCodeTiers();
//												cptCollectif = lf.getTaAcompte().getTaTiers().getCompte();
//												nomTiers = "";
//												adresse1Tiers = "";
//												adresse2Tiers = "";
//												codePostalTiers = "";
//												villeTiers = "";
//
//												codeTva = "";
//												exigibiliteTva = "";
//												mtDebitTva = 0d;
//												mtDebitLigne = 0d;
//
//												numLignePiece = numLignePiece + 1;
//												mtCreditLigne = LibConversion.BigDecimalToDouble(lf.getTaAcompte().getNetTtcCalc());
//												mtCreditTva = 0d;	
//
//												refContrepartie = fac.getCodeDocument();
//												dateContrepartie = fac.getDateDocument();
//												mtContrepartieDebit = LibConversion.BigDecimalToDouble(fac.getNetTtcCalc());
//												mtContrepartieCredit = 0d;
//												mtAffectation = LibConversion.BigDecimalToDouble(Relation.getAffectation());
//
//												fw.write(creationLigne(numeroDePiece,
//														numLignePiece ,
//														typePiece ,
//														codePiece ,
//														datePiece ,
//														numCptLigne ,
//														libelleLigne ,
//														mtDebitLigne,
//														mtCreditLigne ,
//														qte1 ,
//														qte2 ,
//														codeTva ,
//														tauxTva ,
//														mtDebitTva,
//														mtCreditTva ,
//														dateEcheance ,
//														cptCollectif ,
//														nomTiers ,
//														adresse1Tiers ,
//														adresse2Tiers ,
//														codePostalTiers ,
//														villeTiers ,
//														exigibiliteTva ,
//														dateLivraisonLigne,
//														refContrepartie,
//														dateContrepartie,
//														mtContrepartieDebit,
//														mtContrepartieCredit,
//														mtAffectation));
//												fw.write(finDeLigne);
//												if(Relation.getExport()!=1)Relation.setExport(1);
//												listCompletePointage.add(new IDocumentTiers[]{lf.getTaAcompte(),fac});
////												listCompletePointage.add(codePiece+separateurListePointage+refContrepartie);
//												listeVerif.add(fac);
//											}	
//										}
									}		
								}//fin traitement des pointages
								if(lf.getTaAcompte().getExport()!=1)lf.getTaAcompte().setExport(1);
								listComplete.add(lf.getTaAcompte().getCodeDocument());
								listeVerif.add(lf.getTaAcompte());
							}//fin on traite l'acompte						
							if(lf.getExport()!=1)lf.setExport(1);
						} 
						//				}//fin traitement des pointages
//						taDocDAO.begin(transaction);
						if(taDoc.getExport()!=1)
							taDoc.setExport(1);
//						if(idAppelant==null)
						taDocDAO.enregistrerMerge(taDoc);
						listComplete.add(taDoc.getCodeDocument());
						listeVerif.add(taDoc);
					}
				}
				if(monitor!=null) monitor.worked(1);
			}
			//numFin=numeroDePiece;
//			if(idAppelant==null)
//				taDocDAO.commit(transaction);

		} catch(IOException ioe){
			logger.error("",ioe);
			setRetour(false);
		} 

		return numFin;

	}

	public int getReExportAcompte() {
		return reExportAcompte;
	}

	public void setReExportAcompte(int reExportAcompte) {
		this.reExportAcompte = reExportAcompte;
	}

	public int getReExportAvoir() {
		return reExportAvoir;
	}

	public void setReExportAvoir(int reExportAvoir) {
		this.reExportAvoir = reExportAvoir;
	}

	public int getReExportFacture() {
		return reExportFacture;
	}

	public void setReExportFacture(int reExportFacture) {
		this.reExportFacture = reExportFacture;
	}

	public int getReExportReglement() {
		return reExportReglement;
	}

	public void setReExportReglement(int reExportReglement) {
		this.reExportReglement = reExportReglement;
	}

	public int getReExportRemise() {
		return reExportRemise;
	}

	public void setReExportRemise(int reExportRemise) {
		this.reExportRemise = reExportRemise;
	}

	
	private int initPointage(IDocumentTiers fac , IDocumentTiers doc2,IRelationDoc relation, boolean gererLesDocumenstLies, 
			Integer idAppelant ,IProgressMonitor monitor,int numDepart) throws Exception{
		int numFin=numDepart++;
		try { 
		if(fac!=null ){
			TaRemise remise=null;
			if(gererLesDocumenstLies
					&& idAppelant==null){
				List<TaFacture> liste = new LinkedList<TaFacture>();
				liste.add((TaFacture)fac);
				exportFactureJPA(liste, listComplete, fw, monitor, reExportFacture, numFin, false, doc2.getIdDocument(),em,false);
				listeVerif.add(fac);
			}
			if(doc2.getTypeDocument()==TaReglement.TYPE_DOC){
				TaRemiseDAO dao=new TaRemiseDAO(em);
				List<TaRemise> liste =dao.findSiReglementDansRemise(doc2.getCodeDocument());
				if(liste.size()>0)remise=liste.get(0);
			}else
				if(doc2.getTypeDocument()==TaAcompte.TYPE_DOC){
					TaRemiseDAO dao=new TaRemiseDAO(em);
					List<TaRemise> liste =dao.findSiAcompteDansRemise(doc2.getCodeDocument());
					if(liste.size()>0)remise=liste.get(0);
				}
			if(idAppelant==null || idAppelant==fac.getIdDocument()){
				typePiece=typePointage;
				if(remise!=null){
					codePiece=remise.getCodeDocument();
					datePiece=remise.getDateDocument();
					if(remise.getLibelleDocument()!=null)
						libelleLigne = remise.getLibelleDocument().replace("\r","").replace("\n","");//libelle de la ligne de facture
				}
				else {
					codePiece=doc2.getCodeDocument();
					datePiece=doc2.getDateDocument();
					if(doc2.getLibelleDocument()!=null)
						libelleLigne = doc2.getLibelleDocument().replace("\r","").replace("\n","");//libelle de la ligne de facture
				}
				qte1 = 0d;
				qte2 = 0d;
				numCptLigne = "+"+doc2.getTaTiers().getCodeCompta();
				cptCollectif = doc2.getTaTiers().getCompte();
				nomTiers = "";
				adresse1Tiers = "";
				adresse2Tiers = "";
				codePostalTiers = "";
				villeTiers = "";

				codeTva = "";
				exigibiliteTva = "";
				mtDebitTva = 0d;
				mtDebitLigne = 0d;

				numLignePiece = numLignePiece + 1;
				mtCreditLigne = LibConversion.BigDecimalToDouble(doc2.getNetTtcCalc());
				mtCreditTva = 0d;	

				refContrepartie = fac.getCodeDocument();
				dateContrepartie = fac.getDateDocument();
				mtContrepartieDebit = LibConversion.BigDecimalToDouble(fac.getNetTtcCalc());
				mtContrepartieCredit = 0d;
				mtAffectation = LibConversion.BigDecimalToDouble(relation.getAffectation());

				fw.write(creationLigne(numeroDePiece,
						numLignePiece ,
						typePiece ,
						codePiece ,
						datePiece ,
						numCptLigne ,
						libelleLigne ,
						mtDebitLigne,
						mtCreditLigne ,
						qte1 ,
						qte2 ,
						codeTva ,
						tauxTva ,
						mtDebitTva,
						mtCreditTva ,
						dateEcheance ,
						cptCollectif ,
						nomTiers ,
						adresse1Tiers ,
						adresse2Tiers ,
						codePostalTiers ,
						villeTiers ,
						exigibiliteTva ,
						dateLivraisonLigne,
						refContrepartie,
						dateContrepartie,
						mtContrepartieDebit,
						mtContrepartieCredit,
						mtAffectation));
				fw.write(finDeLigne);
				//je ne l'enregistre pas car crée des conflits si facture avec plusieurs réglements
				//if(relation.getExport()!=1)relation.setExport(1);
				if(remise!=null)doc2=remise;
				listCompletePointage.add(new IDocumentTiers[]{doc2,fac});				
			}
		}
		return numFin;
		} catch(IOException ioe){
			logger.error("",ioe);
			setRetour(false);
			return numFin;
		} 
	}


	public String correspondanceCodeTvaHUE(String codeTtvaTiers, String codeInitial,String journalTvaDoc,String compte,BigDecimal montant){
		//voir dans Epicea TDMPlan.TvaCodePourCompte
		if(codeInitial==null)codeInitial="";
		if(codeTtvaTiers!=null && codeTtvaTiers.equals("N")){//si franchise de tva
			//si code vide, on renvoie vide
			if(codeInitial!=null && codeInitial.equals(""))return "";
			else if(codeInitial!=null && !codeInitial.equals("")){//si code rempli (ne doit pas arriver)

			}
		}else
			//if(codeTtvaTiers!=null && codeTtvaTiers.equals("FF")){//si franchise de tva
			if(codeTtvaTiers!=null && codeTtvaTiers.equals("FRANCHISE")){//si franchise de tva
				//si code vide, on renvoie vide
				if(codeInitial!=null && codeInitial.equals(""))return "F";
				else if(codeInitial!=null && !codeInitial.equals("")){//si code rempli (ne doit pas arriver)

				}
			}			
		else {
			if((codeTtvaTiers.equals("HUE")||codeTtvaTiers.equals("UE"))
					&& (codeInitial!=null && codeInitial.equals(""))){
				if(compte.startsWith("6")){
					if (!(compte.startsWith("609")||compte.startsWith("665"))){
						return "";
					}
					return journalTvaDoc;
				}
				return journalTvaDoc;
			} else if(codeInitial!=null && !codeInitial.equals("")){//si codeTtvaTiers E ou L puisque N pris avant et F dans autre procedure
				if(codeInitial.startsWith("A")) //si codeInitial A on transforme en B = équivalence Epicéa des code sur Achat
				  return "B";
			} else
				return codeInitial;	//sinon on laisse le code initiale puisque qu'il a était mis volontairement			
		}
		return codeInitial; // si rentre dans aucuns cas
	}
	
	public String correspondanceCodeTva(String codeTtvaTiers, String codeInitial,String codeRemplacement,String compte,BigDecimal montant){
		//voir dans Epicea TDMPlan.TvaCodePourCompte
		if(codeTtvaTiers.equalsIgnoreCase("F")){
			if(codeRemplacement.equals("E")){
				if (compte.startsWith("6")) {
					if ((compte.startsWith("609")||compte.startsWith("665"))){
						return "V";
					}else
						return "B";
				}			
			}
			if(codeRemplacement.startsWith("A")){
				return "B";
			}
		}else{
			return correspondanceCodeTvaHUE(codeTtvaTiers,codeInitial,codeRemplacement,compte,montant);
		}
		return codeRemplacement;
	}
}

