package fr.legrain.saisiecaisse.divers;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import javax.persistence.EntityTransaction;

import org.apache.log4j.Logger;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.Platform;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;

import fr.legrain.documents.dao.TaAvoir;
import fr.legrain.documents.dao.TaAvoirDAO;
import fr.legrain.documents.dao.TaFacture;
import fr.legrain.documents.dao.TaFactureDAO;
import fr.legrain.documents.dao.TaLAvoir;
import fr.legrain.documents.dao.TaLFacture;
import fr.legrain.documents.dao.TaTPaiement;
import fr.legrain.gestCom.librairiesEcran.swt.LgrShellUtil;
import fr.legrain.lib.data.LibCalcul;
import fr.legrain.lib.data.LibConversion;
import fr.legrain.lib.data.LibDate;
import fr.legrain.saisieCaisse.dao.TaOperation;
import fr.legrain.saisieCaisse.dao.TaOperationDAO;
import fr.legrain.saisiecaisse.saisieCaissePlugin;
import fr.legrain.saisiecaisse.preferences.PreferenceConstants;
import fr.legrain.tiers.dao.TaTiers;
import fr.legrain.tiers.dao.TaTiersDAO;

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
public class ExportationEpiceaSaisieCaisse {

	static Logger logger = Logger.getLogger(ExportationEpiceaSaisieCaisse.class.getName());
	private String fichierExportation = Platform.getInstanceLocation().getURL().getFile()+"/E2-IMPOR.TXT";
	private String finDeLigne = "\r\n";
	public static final int RE_EXPORT = 1;

	public ExportationEpiceaSaisieCaisse() {
		 
	}



	public void exportJPA(List<TaOperation> idFactureAExporter,IProgressMonitor monitor) {
		exportJPA(idFactureAExporter,monitor,0);
	}

	
	public void exportJPA(List<TaOperation> idOperationAExporter,IProgressMonitor monitor, int reExport) {
		try {      

			FileWriter fw = new FileWriter(fichierExportation);
			BufferedWriter bw = new BufferedWriter(fw);
			
			if(idOperationAExporter!=null) {
				exportOperationJPA(idOperationAExporter, fw, monitor, reExport);
			}
			

			bw.close();
			fw.close();
		} catch(IOException ioe){
			logger.error("",ioe);
		} catch (Exception e) {
			logger.error("",e);
		}
	}
	
	public void exportOperationJPA(List<TaOperation> idOperationAExporter,FileWriter fw,IProgressMonitor monitor, int reExport) {
		String tiers = saisieCaissePlugin.getDefault().getPreferenceStore().getString(PreferenceConstants.TIERS);
		TaTiersDAO daoTiers = new TaTiersDAO();			
		TaTiers taTiers=daoTiers.findByCode(tiers);
		try {      
			String ctpAchatDefaut =saisieCaissePlugin.getDefault().getPreferenceStore().getString(PreferenceConstants.FIXE_OP_ACHAT);

			TaOperationDAO taOperationDAO = new TaOperationDAO();
			TaOperation taOperation = null;
			
			String compte = null;

			Integer numLignePiece = null;
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


			boolean pieceAchat = false;
			boolean nouvellePiece = true;

			EntityTransaction transaction = new TaOperationDAO().getEntityManager().getTransaction();
			taOperationDAO.begin(transaction);
			
			Double totalOperation = new Double(0);
			Date dateEnCours =null;
			int numLigne=1;
			TaTPaiement taTPaiementEnCours = null;
			cptCollectif = taTiers.getCompte();
			nomTiers = taTiers.getNomTiers();
			adresse1Tiers = taTiers.getTaAdresse().getAdresse1Adresse();
			adresse2Tiers =  taTiers.getTaAdresse().getAdresse2Adresse();
			codePostalTiers = taTiers.getTaAdresse().getCodepostalAdresse();
			villeTiers =  taTiers.getTaAdresse().getVilleAdresse();
			typePiece = "T";
			codePiece = "B";
			int numeroDePiece = 1;
			List<TaOperation> listeOperations = new LinkedList<TaOperation>();
			
			for(int i=0; i<idOperationAExporter.size(); i++) {
				taOperation = idOperationAExporter.get(i);
				
				if(taOperation.getExport()!=1 || reExport==1) { /* facture pas deja exportee ou reexportation */
					
					if((dateEnCours!=null && dateEnCours.before(taOperation.getDateOperation()))||
							(taTPaiementEnCours!=null && taTPaiementEnCours.getIdTPaiement()!=
								taOperation.getTaTPaiement().getIdTPaiement())){
					//création de l'entête de la pièce					
						datePiece = taOperation.getDateOperation();
						dateEcheance = taOperation.getDateOperation();
						dateLivraisonLigne = taOperation.getDateOperation();
						compte =  taOperation.getTaTPaiement().getCompte();
						pieceAchat=ctpAchatDefaut.equals(taOperation.getTaTOperation().getCodeTOperation());
					libelleLigne = "Libelle de la piece";					
					numCptLigne = compte;
					mtCreditTva = 0d;
					mtDebitTva = 0d;
					if(!pieceAchat){
						mtCreditLigne = totalOperation;
						mtDebitLigne = 0d;
					}else
					{
						mtCreditLigne = 0d;
						mtDebitLigne = totalOperation;
					}
					
					numLignePiece = numLigne;
					qte1 = 0d;
					qte2 = 0d;
					codeTva ="";
					tauxTva = 0d;
					exigibiliteTva = "";

					//ecriture de l'entête dans le fichier d'export
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
							"" ,
							tauxTva ,
							mtDebitTva ,
							mtCreditTva ,
							dateEcheance ,
							"" ,
							"" ,
							"" ,
							"" ,
							"" ,
							"" ,
							exigibiliteTva ,
							dateLivraisonLigne));
					fw.write(finDeLigne);
					numLigne++;
					///Traitement pour créer les lignes d'écritures récoltées
					for (TaOperation taOperationTemp : listeOperations) {
						
					
//					datePiece = taOperationTemp.getDateOperation();
//					dateEcheance = taOperationTemp.getDateOperation();
//					dateLivraisonLigne = taOperationTemp.getDateOperation();
//					compte =  taOperationTemp.getTaTPaiement().getCompte();
//					pieceAchat=ctpAchatDefaut.equals(taOperationTemp.getTaTOperation().getCodeTOperation());

					numCptLigne = "+"+taTiers.getCodeCompta();

					libelleLigne = taOperationTemp.getLiblOperation();
					if(libelleLigne.equals(""))libelleLigne="ligne "+numLigne;
						qte1 = 0d;
						qte2 = 0d;
						codeTva = "";
						exigibiliteTva = "";
						//tauxTva = "mettre le taux de tva récupéré";
						if(!pieceAchat){
							mtDebitTva = LibConversion.BigDecimalToDouble(taOperationTemp.getTva());  
							mtDebitLigne = LibConversion.BigDecimalToDouble(taOperationTemp.getMontantOperation());
							mtCreditTva = 0d;
							mtCreditLigne = 0d;
						}else
						{
							mtDebitTva = 0d;  
							mtDebitLigne =0d; 
							mtCreditTva = LibConversion.BigDecimalToDouble(taOperationTemp.getTva()); 
							mtCreditLigne = LibConversion.BigDecimalToDouble(taOperationTemp.getMontantOperation());  
						}


						numLignePiece = numLigne;

						//ecriture de la ligne dans le fichier d'export
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
								"" ,
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
								dateLivraisonLigne));
						fw.write(finDeLigne);
						numLigne++;
						taOperationTemp.setExport(1);
						taOperationDAO.enregistrerMerge(taOperationTemp);

					}
					totalOperation=new Double(0);
					numLigne=1;
					numeroDePiece++;
					listeOperations.clear();

					}
					listeOperations.add(taOperation);

						dateEnCours=taOperation.getDateOperation();
						taTPaiementEnCours=taOperation.getTaTPaiement();
						totalOperation+=LibCalcul.arrondi(LibConversion.BigDecimalToDouble(taOperation.getMontantOperation()));
					} 

				}
				if(monitor!=null) monitor.worked(1);
			taOperationDAO.commit(transaction);

		} catch(IOException ioe){
			logger.error("",ioe);
		} catch (Exception e) {
			logger.error("",e);
		}
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
			Date dateLivraisonLigne) {
		StringBuffer ligne = new StringBuffer("");
		char c = ' ';
		//ECRITURE DE L'OPERATION DANS LE FICHIER D'EXPORTATION
		//Entete
		//1
		ligne.append(completeChaine(String.valueOf(numPiece),5,c,0));
		//2
		ligne.append(completeChaine(String.valueOf(numLignePiece),3,c,0));
		//3
		ligne.append(completeChaine(String.valueOf(typePiece),1,c,0));
		//4
		ligne.append(completeChaine(String.valueOf(codePiece),9,c,0));
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
}

