package fr.legrain.importinfosegroupeware.handlers;

import java.io.IOException;
import java.sql.Connection;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Shell;

import fr.legrain.ftp.FtpUtil;
import fr.legrain.importinfosegroupeware.connect.ControlConnect;
import fr.legrain.importinfosegroupeware.constant.ConstantPlugin;
import fr.legrain.importinfosegroupeware.swt.DialogInfosEgw;

public class ActionsDialogInfosEgw {

	public void actionButtonAnnuler(Button button, final Shell shell) {
		button.addSelectionListener(new SelectionListener() {

		
			public void widgetDefaultSelected(SelectionEvent e) {
				// TODO Auto-generated method stub
				widgetSelected(e);

			}


			public void widgetSelected(SelectionEvent e) {
				// TODO Auto-generated method stub
				shell.close();

			}
		});
	}

	public void actionButtonValider(final DialogInfosEgw dialogInfosEgw,final Shell shell) {
		
		dialogInfosEgw.getButtonValider().addSelectionListener(new SelectionListener() {


			public void widgetDefaultSelected(SelectionEvent e) {
						// TODO Auto-generated method stub
					widgetSelected(e);
			}

			public void widgetSelected(SelectionEvent e) {
						// TODO Auto-generated method stub
				/*** JPA ***/
				ControlConnect controlConnect = new ControlConnect();
				Connection connectionMysql = controlConnect.makeConnect(ConstantPlugin.MYSQL_DB,
											ConstantPlugin.DB_EGROUPWARE);
				
				AfficheEtTraiterTiers affichageOngletTiers = new AfficheEtTraiterTiers();
			    String idContactSaisir = dialogInfosEgw.getTextNumeroCpntact().getText();
			    boolean flagIsExistIdContact = affichageOngletTiers.isExisterIdContact(connectionMysql, idContactSaisir);
				boolean flagImport = affichageOngletTiers.idContactIsClient(connectionMysql,idContactSaisir);
				
				if(!flagIsExistIdContact){
					
					dialogInfosEgw.getLabelMessage().setText(ConstantPlugin.MESSAGE_EXISTER);
				}
				else if(flagImport) {
					
					dialogInfosEgw.getLabelMessage().setText(ConstantPlugin.MESSAGE_IMPORT);
				} 
				else {
					affichageOngletTiers.traiterEgw(idContactSaisir,connectionMysql);
//					affichageOngletTiers.ajouterInfosEntreprise();
//					int idEntreprise = affichageOngletTiers.getIdEntreprise(affichageOngletTiers.getCodeEntreprise());
					String nomTiers = affichageOngletTiers.getNomTiers();
					if(nomTiers==null || nomTiers.trim().length()==0){
						
						dialogInfosEgw.getLabelMessage().setText(ConstantPlugin.MESSAGE_IMPORT_ERROR_NOM_TIERS);
					}
					else{
						
//						int idEntreprise = affichageOngletTiers.getIdEntreprise(affichageOngletTiers.getCodeEntreprise());
//						affichageOngletTiers.setIdEntreprise(idEntreprise);
						String codeTier = affichageOngletTiers.ajouteInfosTiers();
						
						/**
						 * pathClientFtp ==> http://lgrser.lgr/lgr/tiers/client_test/2401-2600/2461
						 */
						String folderTier = affichageOngletTiers.getPathClient(codeTier);
						String pathClientFtp = ConstantPlugin.PATH_FTP+ConstantPlugin.CLIENT+folderTier;
						
						String linkServerLinux = String.format(ConstantPlugin.PATH_FOLDER_CLIENT,ConstantPlugin.HEAD_SERVER_LINUX)+folderTier;
						String linkServerWin = String.format(ConstantPlugin.PATH_FOLDER_CLIENT,ConstantPlugin.HEAD_SERVER_WIN)+folderTier;
						affichageOngletTiers.addLiensToTier(codeTier,pathClientFtp,linkServerLinux,linkServerWin);
						
						
						if(!affichageOngletTiers.idContactExisteFile(connectionMysql,idContactSaisir)){
							
							affichageOngletTiers.insertProspectToClient(connectionMysql, idContactSaisir, 
																		pathClientFtp, codeTier);
						}
						else{
							
							String sourceFile = ConstantPlugin.PATH_TIERS+affichageOngletTiers.getPathFileProspect();
							String destFile =  ConstantPlugin.PATH_TIERS+ConstantPlugin.CLIENT+
												    affichageOngletTiers.getPathClient(codeTier);
//							System.out.println(sourceFile+"---"+destFile);
							affichageOngletTiers.moveFileEtFolder(sourceFile, destFile);
							affichageOngletTiers.updateProspectToClient(connectionMysql, idContactSaisir, 
									pathClientFtp,codeTier);
						}
//						if(affichageOngletTiers.isExistFile(connectionMysql, idContactSaisir)){
//							String pathClient = affichageOngletTiers.getPathClient(affichageOngletTiers.getCodeTiers());
//							String sourceFile = ConstantPlugin.PATH_TIERS+affichageOngletTiers.getPathFileProspect();
//							String destFile = ConstantPlugin.PATH_TIERS+ConstantPlugin.CLIENT+
//												affichageOngletTiers.getPathClient(codeTier);
//							//System.out.println(destFile);
//							affichageOngletTiers.moveFileEtFolder(sourceFile, destFile);
//							affichageOngletTiers.updateProspectToClient(connectionMysql, idContactSaisir, 
//									ConstantPlugin.PATH_FTP+ConstantPlugin.CLIENT+affichageOngletTiers.getPathClient(codeTier), 
//															codeTier);
//							
//						}
						affichageOngletTiers.execute(codeTier);
						
						shell.close();
						}
					}
			}
		});
	}

}
