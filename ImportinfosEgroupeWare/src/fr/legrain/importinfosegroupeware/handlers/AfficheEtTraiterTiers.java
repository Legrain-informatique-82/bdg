package fr.legrain.importinfosegroupeware.handlers;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityTransaction;

import org.apache.commons.net.ftp.FTPFile;
import org.apache.log4j.Logger;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.PlatformUI;

import fr.legrain.ftp.FtpUtil;
import fr.legrain.gestCom.Appli.IB_APPLICATION;
import fr.legrain.gestCom.librairiesEcran.workbench.AbstractLgrMultiPageEditor;
import fr.legrain.gestCom.librairiesEcran.workbench.LgrPartListener;
import fr.legrain.importinfosegroupeware.connect.ControlConnect;
import fr.legrain.importinfosegroupeware.constant.ConstantPlugin;
import fr.legrain.lib.data.ModeObjet.EnumModeObjet;
import fr.legrain.tiers.dao.TaAdresse;
import fr.legrain.tiers.dao.TaCommentaire;
import fr.legrain.tiers.dao.TaEmail;
import fr.legrain.tiers.dao.TaEntreprise;
import fr.legrain.tiers.dao.TaLiens;
import fr.legrain.tiers.dao.TaTAdrDAO;
import fr.legrain.tiers.dao.TaTEmailDAO;
import fr.legrain.tiers.dao.TaTLiensDAO;
import fr.legrain.tiers.dao.TaTTelDAO;
import fr.legrain.tiers.dao.TaTTiers;
import fr.legrain.tiers.dao.TaTTiersDAO;
import fr.legrain.tiers.dao.TaTWebDAO;
import fr.legrain.tiers.dao.TaTelephone;
import fr.legrain.tiers.dao.TaTiers;
import fr.legrain.tiers.dao.TaTiersDAO;
import fr.legrain.tiers.dao.TaWeb;
import fr.legrain.tiers.ecran.ParamAfficheTiers;
import fr.legrain.tiers.editor.EditorInputTiers;
import fr.legrain.tiers.editor.TiersMultiPageEditor;

public class AfficheEtTraiterTiers {
	static Logger logger = Logger.getLogger(AfficheEtTraiterTiers.class.getName());
	
	private ControlConnect controlConnect = new ControlConnect();
	private Connection connectionFirebird = IB_APPLICATION.findDatabase().getJdbcConnection();

	
	private String pathFileProspect = null;
	/**pour table Tiers**/
	private String nomTiers = null;
	private String prenomTiers = null;
	private String codeTiers = null;
	private int idTiers = 0;
	
	
	/**pour table Entreprise**/
	private String codeEntreprise = null;
	private String nomEntreprise = null;
	private int idEntreprise = 0;
	
	private TaEntreprise taEntreprise = null;
	
	/** pour table Address**/
	private String adr_one_street = null;
	private String adr_one_street2 = null;
	private String adr_one_locality = null;
	private String adr_one_postalcode = null;
	private String adr_one_countryname = null;
	
	private String adr_two_street = null;
	private String adr_two_street2 = null;
	private String adr_two_locality = null;
	private String adr_two_postalcode = null;
	private String adr_two_countryname = null;
	
	/** JPA Table Adresse**/
	TaAdresse taAdresseFACT = null;
	TaAdresse taAdresseS = null;
	
	/**pour table telephone**/
	private String tel_work = null;
	private String tel_cell = null;
	private String tel_fax = null;
	private String tel_home = null;
	
	/*** JPA Table TELEPHONE ***/
	private TaTelephone taTelephoneB = null;
	private TaTelephone taTelephoneP = null;
	private TaTelephone taTelephoneD = null;
	private TaTelephone taTelephoneFAX = null;
	
	/** pour table email**/
	private String contact_email = null;
	private String contact_email_home = null;
	
	/*** JPA Table Email ***/
	private TaEmail taEmailC = null;
	private TaEmail taEmailCH = null;
	
	/** pour table url **/
	private String contact_url = null;
	private String contact_url_home = null;
	
	/*** JPA Table URL ***/
	private TaWeb taWebC = null;
	private TaWeb taWebCH = null;
	
	/*** JPA ***/
	EntityTransaction transaction = null;
	
	/** add les liens (update 02/11/2009)**/
	private String commentaire = null;
	private TaCommentaire taCommentaire = null;
	
	/** JPA Table Liens**/
	private TaLiens taLiensHttp = null;
	private TaLiens taLiensServerLinux = null;
	private TaLiens taLiensServerWin = null;
	
	//public Object execute(ExecutionEvent event) throws ExecutionException {
	public void execute(String codeTiers){
		  try {
			  //fr.legrain.editor.facture.swt.multi
			  //if(vue.getTfListeDoc().getSelection()!=null&&vue.getTfListeDoc().getSelection().length>0){										
					//String idEditor = "fr.legrain.tiers.editor.EditorTiers";
					//IEditorPart e = PlatformUI.
					
					//if(idEditor!=null) {
						//IEditorPart editor = AbstractLgrMultiPageEditor.chercheEditeurDocumentOuvert(idEditor);
						//if(editor==null) {
							LgrPartListener.getLgrPartListener().setLgrActivePart(null);
							IEditorPart e = PlatformUI.getWorkbench().getActiveWorkbenchWindow().
							 getActivePage().openEditor(new EditorInputTiers(), TiersMultiPageEditor.ID_EDITOR);
							//getActivePage().openEditor(new EditorInputTiers(), EditorTiers.ID);
//							LgrPartListener.getLgrPartListener().getLgrNavigation().add(e);
//							LgrPartListener.getLgrPartListener().setLgrActivePart(e);

//							ParamAffiche paramAffiche = new ParamAffiche();
//							paramAffiche.setCodeDocument(vue.getTfListeDoc().getSelection()[0]);
//							paramAffiche.setModeEcran(EnumModeObjet.C_MO_CONSULTATION);
//							((AbstractLgrMultiPageEditor)e).findMasterController().configPanel(paramAffiche);
							ParamAfficheTiers paramAfficheTiers = new ParamAfficheTiers();
							//paramAfficheTiers.setIdTiers(idTiers);
							
							paramAfficheTiers.setCodeDocument(codeTiers);
							paramAfficheTiers.setModeEcran(EnumModeObjet.C_MO_CONSULTATION);
							//((JPALgrEditorPart)e).getController().configPanel(paramAfficheTiers);
							((AbstractLgrMultiPageEditor)e).findMasterController().configPanel(paramAfficheTiers);
						//}

					//}				  
			  //}
		 } catch (Exception e1) {
			  logger.error("Erreur : actionPerformed", e1);
		  }
		  //return event;
	  }
	
	public void traiterEgw(String idcontact,Connection connection){
		
			
		
		int id_contact = Integer.parseInt(idcontact);
		//ControlConnect controlConnect = new ControlConnect();
//		Connection connection = controlConnect.makeConnect(ConstantPlugin.MYSQL_DB, ConstantPlugin.DB_EGROUPWARE);
		try {
			PreparedStatement preparedStatement = connection.prepareStatement(ConstantPlugin.SELECT_EGW_ADDRESSBOOK);
			preparedStatement.setInt(1,id_contact);
			ResultSet resultSet =  preparedStatement.executeQuery();
			//SWT_IB_TA_TIERS ibTaTable = new SWT_IB_TA_TIERS();
			if(resultSet.next()){
				nomTiers = resultSet.getString("n_family");
				prenomTiers = resultSet.getString("n_given");
				
				taEntreprise = new TaEntreprise();
				String nomEntreprise = null;
				
				String orgName = resultSet.getString("org_name");
				if(orgName==null ||	orgName.trim().length()==0){
					/** JPA **/
//					taEntreprise = new TaEntreprise();
//					taEntreprise.setNomEntreprise("INCONNU");
					nomEntreprise = ConstantPlugin.VALUE_INCONNU;
					
				}else{
//					taEntreprise = new TaEntreprise();
//					taEntreprise.setNomEntreprise(resultSet.getString("org_name"));
					nomEntreprise = resultSet.getString("org_name");
				}
				taEntreprise.setNomEntreprise(nomEntreprise);
				
				
				adr_one_street = resultSet.getString("adr_one_street");
				adr_one_street2 = resultSet.getString("adr_one_street2");
				adr_one_locality = resultSet.getString("adr_one_locality");
				adr_one_postalcode = resultSet.getString("adr_one_postalcode"); 
				adr_one_countryname = resultSet.getString("adr_one_countryname");
				//TaTAdrDAO taTAdrDAOFACT = new TaTAdrDAO();
				TaTAdrDAO taTAdrDAO = new TaTAdrDAO();
				if(adr_one_street!=null ||adr_one_street2!=null || adr_one_locality != null || 
						adr_one_postalcode != null || adr_one_countryname != null){
					taAdresseFACT = new TaAdresse();
//					taAdresseFACT.setTaTAdr(taTAdrDAOFACT.findByCode("FACT"));
					taAdresseFACT.setTaTAdr(taTAdrDAO.findByCode("FACT"));
//					taAdresseFACT.setTaTAdr(taTAdrDAO.findById(4));
					taAdresseFACT.setAdresse1Adresse(adr_one_street);
					taAdresseFACT.setAdresse2Adresse(adr_one_street2);
					taAdresseFACT.setCodepostalAdresse(adr_one_postalcode);
					String villeAdresseFACT = null;
					String paysAdresseFACT = null;
					if(adr_one_locality==null || adr_one_locality.trim().length() == 0){
//						taAdresseFACT.setVilleAdresse("INCONNU");
						villeAdresseFACT = ConstantPlugin.VALUE_INCONNU;
					}else{
//						taAdresseFACT.setVilleAdresse(adr_one_locality);
						villeAdresseFACT = adr_one_locality;
					}
					taAdresseFACT.setVilleAdresse(villeAdresseFACT);
					
					if(adr_one_countryname==null || adr_one_countryname.trim().length() == 0){
//						taAdresseFACT.setPaysAdresse("");
						paysAdresseFACT = "";
					}else{
//						taAdresseFACT.setPaysAdresse(adr_one_countryname);
						paysAdresseFACT = adr_one_countryname;
					}
					taAdresseFACT.setPaysAdresse(paysAdresseFACT);
				}
				
				adr_two_street = resultSet.getString("adr_two_street");
				adr_two_street2 = resultSet.getString("adr_two_street2");
				adr_two_locality = resultSet.getString("adr_two_locality");
				adr_two_postalcode = resultSet.getString("adr_two_postalcode"); 
				adr_two_countryname = resultSet.getString("adr_two_countryname");
//				TaTAdrDAO taTAdrDAOS = new TaTAdrDAO();
				if(adr_two_street!=null ||adr_two_street2!=null || adr_two_locality != null || 
						adr_two_postalcode != null || adr_two_countryname != null){
					
					
					taAdresseS = new TaAdresse();
					taAdresseS.setTaTAdr(taTAdrDAO.findByCode("S"));
//					taAdresseS.setTaTAdr(taTAdrDAO.findById(3));
					taAdresseS.setAdresse1Adresse(adr_two_street);
					taAdresseS.setAdresse2Adresse(adr_two_street2); 
					taAdresseS.setCodepostalAdresse(adr_two_postalcode);
					String villeAdresseS = null;
					String paysAdresseS = null;
					if(adr_two_locality==null || adr_two_locality.trim().length() == 0){
//						taAdresseS.setVilleAdresse("INCONNU");
						villeAdresseS = ConstantPlugin.VALUE_INCONNU;
					}else{
//						taAdresseS.setVilleAdresse(adr_two_locality);
						villeAdresseS = adr_two_locality;
					}
					taAdresseS.setVilleAdresse(adr_two_locality);
					if(adr_two_countryname==null || adr_two_countryname.trim().length() == 0){
//						taAdresseS.setPaysAdresse("");
						paysAdresseS = ConstantPlugin.VALUE_INCONNU;
					}else{
//						taAdresseS.setPaysAdresse(adr_two_countryname);
						paysAdresseS = adr_two_countryname;
					}
					taAdresseS.setPaysAdresse(paysAdresseS);
				}
			
				tel_work = resultSet.getString("tel_work");
				TaTTelDAO taTTelDAO = new TaTTelDAO();
				if(tel_work!=null && tel_work.trim().length()!=0){
//					TaTTelDAO taTTelDAOB = new TaTTelDAO();
					taTelephoneB = new TaTelephone();
					taTelephoneB.setNumeroTelephone(tel_work);
					taTelephoneB.setTaTTel(taTTelDAO.findByCode("B"));
//					taTelephoneB.setTaTTel(taTTelDAO.findById(1));
					
				}
				//System.out.println("tel_work is-- "+tel_work);
				tel_cell = resultSet.getString("tel_cell");
				if(tel_cell!=null && tel_cell.trim().length()!=0){
//					TaTTelDAO taTTelDAOP = new TaTTelDAO();
					taTelephoneP = new TaTelephone();
					taTelephoneP.setNumeroTelephone(tel_cell);
					taTelephoneP.setTaTTel(taTTelDAO.findByCode("P"));
//					taTelephoneP.setTaTTel(taTTelDAO.findById(2));
					
				}
				tel_fax = resultSet.getString("tel_fax");
				if(tel_fax!=null && tel_fax.trim().length()!=0){
//					TaTTelDAO taTTelDAOFax = new TaTTelDAO();
					taTelephoneFAX = new TaTelephone();
					taTelephoneFAX.setNumeroTelephone(tel_fax);
					taTelephoneFAX.setTaTTel(taTTelDAO.findByCode("FAX"));
//					taTelephoneFAX.setTaTTel(taTTelDAO.findById(4));
					
				}
				tel_home = resultSet.getString("tel_home");
				if(tel_home!=null && tel_home.trim().length()!=0){
//					TaTTelDAO taTTelDAOD = new TaTTelDAO();
					taTelephoneD = new TaTelephone();
					taTelephoneD.setNumeroTelephone(tel_home);
					taTelephoneD.setTaTTel(taTTelDAO.findByCode("D"));
					//taTelephoneD.setTaTTel(taTTelDAO.findById(3));
					
				}
				
				contact_email = resultSet.getString("contact_email");
				TaTEmailDAO taTEmailDAO = new TaTEmailDAO();
				if(contact_email!=null && contact_email.trim().length()!=0){
//					TaTEmailDAO taTEmailDAOC = new TaTEmailDAO();
					taEmailC = new TaEmail();
					taEmailC.setAdresseEmail(contact_email);
					taEmailC.setTaTEmail(taTEmailDAO.findByCode("B"));
//					taEmailC.setTaTEmail(taTEmailDAO.findById(1));
				}
				contact_email_home = resultSet.getString("contact_email_home");
				if(contact_email_home!=null && contact_email_home.trim().length()!=0){
					//TaTEmailDAO taTEmailDAOCH = new TaTEmailDAO();
					taEmailCH = new TaEmail();
					taEmailCH.setAdresseEmail(contact_email_home);
					taEmailCH.setTaTEmail(taTEmailDAO.findByCode("P"));
//					taEmailCH.setTaTEmail(taTEmailDAO.findById(2));
				}
			
				contact_url = resultSet.getString("contact_url");
				TaTWebDAO taTWebDAO = new TaTWebDAO();
				if(contact_url!=null && contact_url.trim().length()!=0){
					//TaTWebDAO taTWebDAOC = new TaTWebDAO();
					taWebC = new TaWeb();
					taWebC.setAdresseWeb(contact_url);
					taWebC.setTaTWeb(taTWebDAO.findByCode("B"));
//					taWebC.setTaTWeb(taTWebDAO.findById(2));
				}
				contact_url_home = resultSet.getString("contact_url_home");
				if(contact_url_home!=null && contact_url_home.trim().length()!=0){
//					TaTWebDAO taTWebDAOC = new TaTWebDAO();
					taWebCH = new TaWeb();
					taWebCH.setAdresseWeb(contact_url_home);
					taWebCH.setTaTWeb(taTWebDAO.findByCode("P"));
//					taWebCH.setTaTWeb(taTWebDAO.findById(1));
				}
				
				/*** new ***/
				commentaire = resultSet.getString("contact_note");
				if(commentaire!=null && commentaire.trim().length()!=0){
					taCommentaire = new TaCommentaire();
					taCommentaire.setCommentaire(commentaire);
				}
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
//			e.printStackTrace();
			logger.error("", e);
		} 	
		
	}
	public void updateEtInsertDBEgw(String idContact){
		Connection connection = controlConnect.makeConnect(ConstantPlugin.MYSQL_DB, 
														   ConstantPlugin.DB_EGROUPWARE);
		
	}
	public String ajouteInfosTiers(){
		/******** JPA ********/ 
		TaTiersDAO dao = new TaTiersDAO();
		TaTiers taTiers = new TaTiers();
		TaTTiersDAO taTTiersDAO = new TaTTiersDAO();
		TaTTiers taTTiers = taTTiersDAO.findByCode("P");
//		TaTTiers taTTiers = taTTiersDAO.findById(5);
		
		
		/*** JPA ADRESSE ***/
		if(taAdresseFACT!=null){
			taAdresseFACT.setTaTiers(taTiers);
			taAdresseFACT.getTaTierses().add(taTiers);
		}
		if(taAdresseS!=null){
			taAdresseS.setTaTiers(taTiers);
			taAdresseS.getTaTierses().add(taTiers);
		}
		
		/*** JPA TELEPHONE ***/
		if(taTelephoneB != null){
			taTelephoneB.setTaTiers(taTiers);
			taTelephoneB.getTaTierses().add(taTiers);
		}
		if(taTelephoneD != null){
			taTelephoneD.setTaTiers(taTiers);
			taTelephoneD.getTaTierses().add(taTiers);
		}
		if(taTelephoneFAX != null){
			taTelephoneFAX.setTaTiers(taTiers);
			taTelephoneFAX.getTaTierses().add(taTiers);
		}
		if(taTelephoneP != null){
			taTelephoneP.setTaTiers(taTiers);
			taTelephoneP.getTaTierses().add(taTiers);
		}
		
		/*** JPA EMAIL ***/
		if(taEmailC != null){
			taEmailC.setTaTiers(taTiers);
			taEmailC.getTaTierses().add(taTiers);
		}
		
		if(taEmailCH != null){
			taEmailCH.setTaTiers(taTiers);
			taEmailCH.getTaTierses().add(taTiers);
		}
		
		/*** JPA WEB ***/
		if(taWebC != null){
			taWebC.setTaTiers(taTiers);
			taWebC.getTaTierses().add(taTiers);
		}
		
		if(taWebCH != null){
			taWebCH.setTaTiers(taTiers);
			taWebCH.getTaTierses().add(taTiers);
		}
		try {
			//EntityTransaction transaction = dao.getEntityManager().getTransaction();
			transaction = dao.getEntityManager().getTransaction();
			
			dao.begin(transaction);
			codeTiers = dao.genereCode();
			taTiers.setCodeTiers(codeTiers);
			taTiers.setCodeCompta(codeTiers);
			taTiers.setCompte("411");
			taTiers.setNomTiers(nomTiers);
			taTiers.setPrenomTiers(prenomTiers);
			taTiers.setActifTiers(new Integer(1));
			taTiers.setTtcTiers(new Integer(0));
			taTiers.setTaEntreprise(taEntreprise);
			
			/*************** new ****************/
			taTiers.setTaCommentaire(taCommentaire);
			
			/************************************/
			if(taAdresseFACT!=null){
				taTiers.setTaAdresse(taAdresseFACT);
				taTiers.getTaAdresses().add(taAdresseFACT);
			}
			
			taTiers.setTaTTiers(taTTiers);
			//dao.persist(taTiers);
			if(taAdresseS != null){
				taTiers.getTaAdresses().add(taAdresseS);
			}
			/*** TELEPHONE ***/
			if(taTelephoneB != null){
				taTiers.setTaTelephone(taTelephoneB);
				taTiers.getTaTelephones().add(taTelephoneB);
			}
			if(taTelephoneD != null){
				taTiers.getTaTelephones().add(taTelephoneD);
			}
			if(taTelephoneFAX != null){
				taTiers.getTaTelephones().add(taTelephoneFAX);
			}
			if(taTelephoneP != null){
				taTiers.getTaTelephones().add(taTelephoneP);
			}
			
			/*** EMAIL ***/
			if(taEmailC != null){
				taTiers.setTaEmail(taEmailC);
				taTiers.getTaEmails().add(taEmailC);
			}
			if(taEmailCH != null){
				taTiers.getTaEmails().add(taEmailCH);
			}
			
			/*** WEB ***/
			if(taWebC != null){
				taTiers.setTaWeb(taWebC);
				taTiers.getTaWebs().add(taWebC);
			}
			if(taWebCH != null){
				taTiers.getTaWebs().add(taWebCH);
			}
			dao.merge(taTiers);
			dao.commit(transaction);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
//			e.printStackTrace();
			logger.error("", e);
		}
		return codeTiers;
		
	}

	public void addLiensToTier(String codeTiers,String linkHttp,String linkServerLinux,String linkServerWin){
		

		TaTLiensDAO taTLiensDAO = new TaTLiensDAO();
		taLiensHttp = new TaLiens();
		taLiensHttp.setAdresseLiens(linkHttp);
		taLiensHttp.setTaTLiens(taTLiensDAO.findById(5));
		
		taLiensServerLinux = new TaLiens();
		taLiensServerLinux.setAdresseLiens(linkServerLinux);
		taLiensServerLinux.setTaTLiens(taTLiensDAO.findById(3));
		
		taLiensServerWin = new TaLiens();
		taLiensServerWin.setAdresseLiens(linkServerLinux);
		taLiensServerWin.setTaTLiens(taTLiensDAO.findById(4));
	
		TaTiersDAO dao = new TaTiersDAO();
		transaction = dao.getEntityManager().getTransaction();
		try {
			dao.begin(transaction);
			TaTiers taTiers = dao.findByCode(codeTiers);
			
			if(taLiensHttp != null){
				taTiers.getTaLienses().add(taLiensHttp);
			}
			if(taLiensServerLinux != null){
				taTiers.getTaLienses().add(taLiensServerLinux);
			}
			if(taLiensServerWin != null){
				taTiers.getTaLienses().add(taLiensServerWin);
			}
			dao.enregistrerMerge(taTiers);
			dao.commit(transaction);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
//			e.printStackTrace();
			logger.error("", e);
		}
		
	}
	public TaAdresse getDefautAdresse(){
		TaAdresse taAdresse = null;
		
		return taAdresse;
	}

	public String genereCodeEntreprise(String nomEntreprise){
		String code= null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		String partNomEntreprise = getSubString(nomEntreprise);
		
		List<String> listPartiCode = new ArrayList<String>();
		
		try {
			preparedStatement = connectionFirebird.prepareStatement(ConstantPlugin.SELECT_TA_ENTREPRISE);
			resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				String codeEntreprise = resultSet.getString("CODE_ENTREPRISE");
				String partCodeEntreprise = getSubString(codeEntreprise);
				if(partNomEntreprise.equalsIgnoreCase(partCodeEntreprise)){
					listPartiCode.add(codeEntreprise);
				}
			}	
		} catch (SQLException e) {
			// TODO Auto-generated catch block
//			e.printStackTrace();
			logger.error("", e);
		}finally{
			closeResulsetEtPreparedStatement(resultSet, preparedStatement,null);
		}
		code = partNomEntreprise+(listPartiCode.size()+1);
		return code;
	}
	
	public String getSubString(String allString) {
		String partString = null;

		String remplaceAllString = allString.replaceAll("\\s", "_");
		if (remplaceAllString.length() >= 10) {
			partString = remplaceAllString.substring(0, 10) + "_";
		} else {
			partString = remplaceAllString.substring(0) + "_";
		}

//		System.out.println("partString is "+partString);
		return partString;
	}
	
	public int getIdEntreprise(String codeEntreprise){
		
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		try {
			preparedStatement = connectionFirebird.prepareStatement(ConstantPlugin.SELECT_ID_ENTREPRISE_TA_ENTREPRISE);
			preparedStatement.setString(1,codeEntreprise);
			resultSet =  preparedStatement.executeQuery();
			while (resultSet.next()) {
				idEntreprise = resultSet.getInt("ID_ENTREPRISE");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			logger.error("", e);
		}finally{
//			try {
//				preparedStatement.close();
//				resultSet.close();
//			} catch (SQLException e) {
//				// TODO Auto-generated catch block
//				//e.printStackTrace();
//				logger.error("", e);
//			}
			closeResulsetEtPreparedStatement(resultSet,preparedStatement,null);
		}
		return idEntreprise;
	}
	
	public void closeResulsetEtPreparedStatement(ResultSet resultSet,PreparedStatement preparedStatement1,PreparedStatement preparedStatement2){
		
		if(resultSet!=null){
			try {
				resultSet.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				//e.printStackTrace();
				logger.error("", e);
			}
		}
		if(preparedStatement1!=null){
			try {
				preparedStatement1.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				//e.printStackTrace();
				logger.error("", e);
			}
		}
		if(preparedStatement2!=null){
			try {
				preparedStatement2.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				//e.printStackTrace();
				logger.error("", e);
			}
		}
	}
	
	/** pour vérifier idContact est importe, ou pas**/ 
	public boolean idContactIsClient(Connection connection,String idcontact){
		boolean flag = false;
		int id_contact = Integer.parseInt(idcontact);
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		try {
			preparedStatement = connection.prepareStatement(ConstantPlugin.SELECT_EGW_ADDRESSBOOK_EXTRA);
			preparedStatement.setInt(1, id_contact);
			resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				String contactName = resultSet.getString("contact_name");
				if(contactName.equalsIgnoreCase(ConstantPlugin.CODE_CLI)){
					flag= true;
					break;
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			logger.error("", e);
		}finally{
			closeResulsetEtPreparedStatement(resultSet,preparedStatement,null);
		}
		return flag;
	}
	/** pour verifier idContact existe,ou pas?**/
	public boolean isExisterIdContact(Connection connection,String idContact){
		boolean flag = false;
		int counter = 0;
		int id_contact = Integer.valueOf(idContact);
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		
		try {
			preparedStatement = connection.prepareStatement(ConstantPlugin.SELECT_EGW_ADDRESSBOOK);
			preparedStatement.setInt(1, id_contact);
			resultSet = preparedStatement.executeQuery();
			if(resultSet.next()) {
				flag = true;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			logger.error("", e);
		}finally{
			closeResulsetEtPreparedStatement(resultSet,preparedStatement,null);
		}
		return flag;
	}

	/** pour verifier idcontact exist des files **/
	public boolean isExistFile(Connection connection,String idContact){
		boolean flag = false;
		int id_contact = Integer.parseInt(idContact);
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		try {
			preparedStatement = connection.prepareStatement(ConstantPlugin.SELECT_EGW_ADDRESSBOOK_EXTRA);
			preparedStatement.setInt(1, id_contact);
			resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				String contactValue = resultSet.getString("contact_value");
				if(contactValue!=null){
					pathFileProspect = getPathFolderProspect(contactValue);
					flag= true;
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			logger.error("", e);
		}finally{
			closeResulsetEtPreparedStatement(resultSet, preparedStatement,null);
		}
		return flag;
	}
	public boolean idContactExisteFile(Connection connection,String idContact){
		boolean flag = false;
		int id_contact = Integer.parseInt(idContact);
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		try {
			preparedStatement = connection.prepareStatement(ConstantPlugin.SELECT_EGW_ADDRESSBOOK_EXTRA_NEW);
			preparedStatement.setInt(1, id_contact);
			preparedStatement.setString(2, ConstantPlugin.LIEN_LGRSER);
			resultSet = preparedStatement.executeQuery();
			if(resultSet.next()) {
				String contactValue = resultSet.getString("contact_value");
				pathFileProspect = getPathFolderProspect(contactValue);
				//System.out.println("****** "+pathFileProspect);
				flag= true;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			logger.error("", e);
		}finally{
			closeResulsetEtPreparedStatement(resultSet, preparedStatement,null);
		}
		return flag;
	}
	/** pour obtenir path "prospect" 
	 * 
	 * ex ==>  http://lgrser.lgr/lgr/tiers/prospect_test/a/albet/
	 * 			prospect_test/a/albet/
	 **/
	public String getPathFolderProspect(String contactValue){
		String pathString = null;
		pathString = contactValue.substring(28);
		return pathString;
	}
	
	public String getPathClient(String codeTiers){
		String pathClient = null;
		String upperLimit = null;
		String lowerLimit = null;
		Float floatCodeClient = Float.valueOf(codeTiers);
		Float interval = Float.valueOf("200");
		int nombreFois = Math.round(floatCodeClient/interval);
		int bord = nombreFois*200;
		if(bord<floatCodeClient){
			lowerLimit = String.valueOf(bord+1);
			upperLimit = String.valueOf(bord+200);
		}
		if(bord>=floatCodeClient){
			upperLimit = String.valueOf(bord);
			lowerLimit = String.valueOf(bord-199);			
		}
		pathClient = lowerLimit+"-"+upperLimit+"/"+Integer.valueOf(codeTiers)+"/";
		return pathClient;
	}
	
	/** deplacer les files et supprimer la folder qui contient ces files**/
	public void moveFileEtFolder(String source,String dest) {
		FtpUtil ftpUtil = new FtpUtil();
		ftpUtil.connectServer(ConstantPlugin.SERVER_FTP, ConstantPlugin.PORT_FTP, 
							  ConstantPlugin.USER_FTP,ConstantPlugin.PASSWORD_FTP, source);
		
		try {
			FTPFile[] ftpFiles = ftpUtil.getFtpClient().listFiles();
			for (FTPFile file : ftpFiles) {
				String nameFile = file.getName();
				ftpUtil.getFtpClient().rename(nameFile, dest+nameFile);
			}
			ftpUtil.getFtpClient().removeDirectory(source);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			logger.error("", e);
		}
		
		ftpUtil.closeServer();
	}
	
	public void updateProspectToClient(Connection connection,String idContact,String contactValue,String codeTiers){
		PreparedStatement preparedStatementUpdate = null;
		PreparedStatement preparedStatementInsert = null;
		//ResultSet resultSet = null;
		try {
			preparedStatementUpdate = connection.prepareStatement(ConstantPlugin.UPDATE_EGW_ADDRESSBOOK_EXTRA);
			preparedStatementUpdate.setString(1,contactValue);
			preparedStatementUpdate.setInt(2,Integer.valueOf(idContact));
			preparedStatementUpdate.execute();
			
			preparedStatementInsert = connection.prepareStatement(ConstantPlugin.INSERT_EGW_ADDRESSBOOK_EXTRA);
			preparedStatementInsert.setInt(1,Integer.valueOf(idContact));
			preparedStatementInsert.setInt(2,8);
			preparedStatementInsert.setString(3, ConstantPlugin.CODE_CLI);
			preparedStatementInsert.setString(4, codeTiers);
			preparedStatementInsert.execute();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			logger.error("", e);
		}finally{
//			try {
//				preparedStatementUpdate.close();
//				preparedStatementInsert.close();
//			} catch (SQLException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
			closeResulsetEtPreparedStatement(null, preparedStatementUpdate, preparedStatementInsert);
		}
	}
	
	public void insertProspectToClient(Connection connection,String idContact,String contactValue,String codeTiers){
		PreparedStatement preparedStatementInsert1 = null;
		PreparedStatement preparedStatementInsert2 = null;
		try {
			preparedStatementInsert1 = connection.prepareStatement(ConstantPlugin.INSERT_EGW_ADDRESSBOOK_EXTRA);
			preparedStatementInsert1.setInt(1,Integer.valueOf(idContact));
			preparedStatementInsert1.setInt(2,8);
			preparedStatementInsert1.setString(3, ConstantPlugin.LIEN_LGRSER);
			preparedStatementInsert1.setString(4, contactValue);
			preparedStatementInsert1.execute();
			
			preparedStatementInsert2 = connection.prepareStatement(ConstantPlugin.INSERT_EGW_ADDRESSBOOK_EXTRA);
			preparedStatementInsert2.setInt(1,Integer.valueOf(idContact));
			preparedStatementInsert2.setInt(2,8);
			preparedStatementInsert2.setString(3, ConstantPlugin.CODE_CLI);
			preparedStatementInsert2.setString(4, codeTiers);
			preparedStatementInsert2.execute();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			logger.error("", e);
		}finally{
//			try {
//				preparedStatementInsert2.close();
//				preparedStatementInsert1.close();
//			} catch (SQLException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
			closeResulsetEtPreparedStatement(null, preparedStatementInsert1, preparedStatementInsert2);
		}
		
	}
	
	public String getNomTiers() {
		return nomTiers;
	}

	public void setNomTiers(String nomTiers) {
		this.nomTiers = nomTiers;
	}

	public String getPrenomTiers() {
		return prenomTiers;
	}

	public void setPrenomTiers(String prenomTiers) {
		this.prenomTiers = prenomTiers;
	}

	public String getCodeTiers() {
		return codeTiers;
	}

	public void setCodeTiers(String codeTiers) {
		this.codeTiers = codeTiers;
	}

	public String getCodeEntreprise() {
		return codeEntreprise;
	}

	public void setCodeEntreprise(String codeEntreprise) {
		this.codeEntreprise = codeEntreprise;
	}

	public Connection getConnectionFirebird() {
		return connectionFirebird;
	}

	public void setConnectionFirebird(Connection connectionFirebird) {
		this.connectionFirebird = connectionFirebird;
	}

	public String getNomEntreprise() {
		return nomEntreprise;
	}

	public void setNomEntreprise(String nomEntreprise) {
		this.nomEntreprise = nomEntreprise;
	}

	public int getIdEntreprise() {
		return idEntreprise;
	}

	public void setIdEntreprise(int idEntreprise) {
		this.idEntreprise = idEntreprise;
	}

	public int getIdTiers() {
		return idTiers;
	}

	public void setIdTiers(int idTiers) {
		this.idTiers = idTiers;
	}

	public String getAdr_one_street() {
		return adr_one_street;
	}

	public void setAdr_one_street(String adr_one_street) {
		this.adr_one_street = adr_one_street;
	}

	public String getAdr_one_street2() {
		return adr_one_street2;
	}

	public void setAdr_one_street2(String adr_one_street2) {
		this.adr_one_street2 = adr_one_street2;
	}

	public String getAdr_one_locality() {
		return adr_one_locality;
	}

	public void setAdr_one_locality(String adr_one_locality) {
		this.adr_one_locality = adr_one_locality;
	}

	public String getAdr_one_postalcode() {
		return adr_one_postalcode;
	}

	public void setAdr_one_postalcode(String adr_one_postalcode) {
		this.adr_one_postalcode = adr_one_postalcode;
	}

	public String getAdr_one_countryname() {
		return adr_one_countryname;
	}

	public void setAdr_one_countryname(String adr_one_countryname) {
		this.adr_one_countryname = adr_one_countryname;
	}

	public String getAdr_two_street() {
		return adr_two_street;
	}

	public void setAdr_two_street(String adr_two_street) {
		this.adr_two_street = adr_two_street;
	}

	public String getAdr_two_street2() {
		return adr_two_street2;
	}

	public void setAdr_two_street2(String adr_two_street2) {
		this.adr_two_street2 = adr_two_street2;
	}

	public String getAdr_two_locality() {
		return adr_two_locality;
	}

	public void setAdr_two_locality(String adr_two_locality) {
		this.adr_two_locality = adr_two_locality;
	}

	public String getAdr_two_postalcode() {
		return adr_two_postalcode;
	}

	public void setAdr_two_postalcode(String adr_two_postalcode) {
		this.adr_two_postalcode = adr_two_postalcode;
	}

	public String getAdr_two_countryname() {
		return adr_two_countryname;
	}

	public void setAdr_two_countryname(String adr_two_countryname) {
		this.adr_two_countryname = adr_two_countryname;
	}

	public String getTel_work() {
		return tel_work;
	}

	public void setTel_work(String tel_work) {
		this.tel_work = tel_work;
	}

	public String getTel_cell() {
		return tel_cell;
	}

	public void setTel_cell(String tel_cell) {
		this.tel_cell = tel_cell;
	}

	public String getTel_fax() {
		return tel_fax;
	}

	public void setTel_fax(String tel_fax) {
		this.tel_fax = tel_fax;
	}

	public String getTel_home() {
		return tel_home;
	}

	public void setTel_home(String tel_home) {
		this.tel_home = tel_home;
	}

	public String getContact_email() {
		return contact_email;
	}

	public void setContact_email(String contact_email) {
		this.contact_email = contact_email;
	}

	public String getContact_email_home() {
		return contact_email_home;
	}

	public void setContact_email_home(String contact_email_home) {
		this.contact_email_home = contact_email_home;
	}

	public ControlConnect getControlConnect() {
		return controlConnect;
	}

	public void setControlConnect(ControlConnect controlConnect) {
		this.controlConnect = controlConnect;
	}

	public String getPathFileProspect() {
		return pathFileProspect;
	}

	public void setPathFileProspect(String pathFileProspect) {
		this.pathFileProspect = pathFileProspect;
	}

	public String getContact_url() {
		return contact_url;
	}

	public void setContact_url(String contact_url) {
		this.contact_url = contact_url;
	}

	public String getContact_url_home() {
		return contact_url_home;
	}

	public void setContact_url_home(String contact_url_home) {
		this.contact_url_home = contact_url_home;
	}

	public String getCommentaire() {
		return commentaire;
	}

	public void setCommentaire(String commentaire) {
		this.commentaire = commentaire;
	}

	public TaCommentaire getTaCommentaire() {
		return taCommentaire;
	}

	public void setTaCommentaire(TaCommentaire taCommentaire) {
		this.taCommentaire = taCommentaire;
	}

	public TaLiens getTaLiensServerLinux() {
		return taLiensServerLinux;
	}

	public void setTaLiensServerLinux(TaLiens taLiensServerLinux) {
		this.taLiensServerLinux = taLiensServerLinux;
	}

	public TaLiens getTaLiensHttp() {
		return taLiensHttp;
	}

	public void setTaLiensHttp(TaLiens taLiensHttp) {
		this.taLiensHttp = taLiensHttp;
	}

	public TaLiens getTaLiensServerWin() {
		return taLiensServerWin;
	}

	public void setTaLiensServerWin(TaLiens taLiensServerWin) {
		this.taLiensServerWin = taLiensServerWin;
	}
	
	
}
