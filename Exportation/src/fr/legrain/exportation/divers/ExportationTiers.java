package fr.legrain.exportation.divers;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import org.apache.log4j.Logger;
import org.eclipse.core.runtime.Platform;

import fr.legrain.articles.dao.TaArticle;
import fr.legrain.articles.dao.TaArticleDAO;


public class ExportationTiers {

	static Logger logger = Logger.getLogger(ExportationTiers.class.getName());
	
	private String fichierExportation =Platform.getInstanceLocation().getURL().getFile()+"/IMPORTTIERS.TXT";

	private String finDeLigne = "\r\n";

	public static final int RE_EXPORT = 1;
    private String messageRetour="";
    private Boolean retour=true;
    private String locationFichier="";

    
    private FileWriter fw = null;
    private BufferedWriter bw = null;

    
    


    
	public ExportationTiers() {}
	

	public void exportation_tiers(){
		StringBuffer ligne = new StringBuffer("");
		
		FileWriter fw = null;
		try {
			fw = new FileWriter(fichierExportation);
			BufferedWriter bw = new BufferedWriter(fw);
		} catch (IOException e) {
			// TODO Bloc catch généré automatiquement
			e.printStackTrace();
		}

		
		TaArticleDAO dao = new TaArticleDAO();
		List<TaArticle> liste= dao.selectAll();
		
		for (TaArticle taArticle : liste) {
			try {
				if(taArticle!=null){
					
				}
//				fw.write("");
			} catch (Exception e) {
				// TODO Bloc catch généré automatiquement
				e.printStackTrace();
			}
		}
	}

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

	
	
}

