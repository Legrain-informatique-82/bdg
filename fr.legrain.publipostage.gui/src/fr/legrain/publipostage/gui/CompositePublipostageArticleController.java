package fr.legrain.publipostage.gui;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.apache.log4j.Logger;
import org.eclipse.core.runtime.Platform;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.ui.PlatformUI;

import fr.legrain.articles.dao.TaArticle;
import fr.legrain.documents.dao.TaFacture;
import fr.legrain.documents.dao.TaFactureDAO;
import fr.legrain.documents.dao.TaParamPublipostage;
import fr.legrain.documents.dao.TaParamPublipostageDAO;
import fr.legrain.gestCom.Appli.Const;
import fr.legrain.lib.data.IFichierDonnees;
import fr.legrain.lib.data.ISelectionLgr;
import fr.legrain.lib.data.LibChaine;
import fr.legrain.lib.data.LibDate;
import fr.legrain.lib.windows.WinRegistry;
import fr.legrain.publipostage.divers.ParamPublipostage;
import fr.legrain.publipostage.divers.TypeVersionPublipostage;
import fr.legrain.publipostage.msword.PublipostageMsWord;
import fr.legrain.publipostage.openoffice.AbstractPublipostageOOo;
import fr.legrain.publipostage.openoffice.PublipostageOOoFactory;

public class CompositePublipostageArticleController {
	
	static Logger logger = Logger.getLogger(CompositePublipostageArticleController.class.getName());
	
	private CompositePublipostage vue = null;
	private List<TaArticle> listeFinale = null;
	
	private TaParamPublipostage paramPubli;
	private TypeVersionPublipostage typeVersion;
	private String versionPubli;
	private final int type = TYPE_ARTICLES;
	
	private IPreferenceStore store;
	private String clePreferenceDerniereValeurWord;
	private String clePreferenceDerniereValeurOOo;
	private IFichierDonnees<TaArticle> donneesArticle;
	private ISelectionLgr<TaArticle> selection;
	
	private String valeurDefautWord;
	private String valeurDefautOOo;
	
	private static final int TYPE_TIERS = 1;
	private static final int TYPE_ARTICLES = 2;
	private static final int TYPE_DOCUMENTS = 3;
	
	public CompositePublipostageArticleController(CompositePublipostage vue, List<TaArticle> listeFinale,  ISelectionLgr<TaArticle> selection,
			IPreferenceStore store, String clePreferenceDerniereValeurWord,
			String clePreferenceDerniereValeurOOo, IFichierDonnees<TaArticle> donneesArticle,
			String valeurDefautWord, String valeurDefautOOo) {
		this.vue = vue;
		this.listeFinale = listeFinale;
		
		this.donneesArticle = donneesArticle;
		
		this.store = store; //Activator.getDefault().getPreferenceStore()
		this.clePreferenceDerniereValeurWord = clePreferenceDerniereValeurWord;
		this.clePreferenceDerniereValeurOOo = clePreferenceDerniereValeurOOo;
		
		this.valeurDefautWord = valeurDefautWord;;
		this.valeurDefautOOo = valeurDefautOOo;
		
		this.selection = selection;
		
		remplissagePubliArticle();
	}
	
	/**
	 * Remplissage de l'encadré pour le publipostage, dans le cadre d'un
	 * résultat typé tiers
	 */
	private void remplissagePubliArticle() {

		typeVersion = TypeVersionPublipostage.getInstance();
		versionPubli = "";
		int rangVersion = 0;
		paramPubli = new TaParamPublipostageDAO().findInstance();
		if (paramPubli != null && paramPubli.getLogicielPublipostage() != null)
			versionPubli = paramPubli.getLogicielPublipostage();
		String[] liste = new String[typeVersion.getTypeVersionExistantes()
				.size()];
		int i = 0;
		for (String libelle : typeVersion.getTypeVersionExistantes().values()) {
			liste[i] = libelle;
			if (versionPubli.equals(libelle))
				rangVersion = i;
			i++;
		}
		vue.getCbListeVersion().setItems(liste);
		vue.getCbListeVersion().select(rangVersion);
		if (typeVersion.getType().get(
				vue.getCbListeVersion().getItem(vue.getCbListeVersion().getSelectionIndex()))
				.equals(TypeVersionPublipostage.TYPE_MSWORD))
			vue.getTfLETTRE().setText(valeurDefautWord);
		if (typeVersion.getType().get(
				vue.getCbListeVersion().getItem(vue.getCbListeVersion().getSelectionIndex()))
				.equals(TypeVersionPublipostage.TYPE_OPENOFFICE))
			vue.getTfLETTRE()
					.setText(valeurDefautOOo);

		// Initialisation des listeners
		initListenersPubli();

	}
	
	private void initListeValeur() {
		if(listeFinale==null && selection!=null && selection.getSelection()!=null) {
			listeFinale = new ArrayList<TaArticle>();
			listeFinale.addAll(selection.getSelection());
		} else if (selection!=null && selection.getSelection()!=null) {
			listeFinale.clear();
			listeFinale.addAll(selection.getSelection());
		}
	}
	
	
	private void initListenersPubli() {

		vue.getBtnChemin_Model().addMouseListener(new MouseAdapter() {

			@Override
			public void mouseDown(MouseEvent e) {
				super.mouseDown(e);

				FileDialog dd = new FileDialog(PlatformUI.getWorkbench()
						.getActiveWorkbenchWindow().getShell());
				dd
						.setFilterExtensions(new String[] { paramPubli
								.getExtension() });
				dd.setFilterNames(new String[] { "Modèle de lettre" });
				dd.setFileName(vue.getTfLETTRE().getText());
				String repDestination = dd.getFileName();
				if (repDestination.equals(""))
					repDestination = Platform.getInstanceLocation().getURL()
							.getFile();
				dd.setFilterPath(LibChaine.pathCorrect(repDestination));
				String choix = dd.open();
				System.out.println(choix);
				if (choix != null) {
					vue.getTfLETTRE().setText(choix);
				}
				vue.getTfLETTRE().forceFocus();
			}

		});

		vue.getBtnOuvrir_Model().addMouseListener(new MouseAdapter() {

			@Override
			public void mouseDown(MouseEvent e) {
				super.mouseDown(e);
				if (typeVersion.getType().get(
						paramPubli.getLogicielPublipostage()).equals(
						TypeVersionPublipostage.TYPE_OPENOFFICE)) {

					// PublipostageOOoWin32 pub = new
					// PublipostageOOoWin32(listeParamPubli);
					AbstractPublipostageOOo pub = PublipostageOOoFactory
							.createPublipostageOOo(null);
					String pathOpenOffice = "";
					try {
						if (Platform.getOS().equals(Platform.OS_WIN32)) {
							pathOpenOffice = WinRegistry.readString(
									WinRegistry.HKEY_LOCAL_MACHINE,
									WinRegistry.KEY_REGISTR_WIN_OPENOFFICE, "");
						} else if (Platform.getOS().equals(Platform.OS_LINUX)) {
							pathOpenOffice = "/usr/bin/soffice";
						} else if(Platform.getOS().equals(Platform.OS_MACOSX)) {}
					} catch (Exception e3) {
						logger.error("", e3);
					}

					pub.setCheminOpenOffice(new File(pathOpenOffice).getPath());
					pub.setPortOpenOffice("8100");
					pub.setNomFichierFinalFusionne(new File(vue.getTfLETTRE().getText())
							.getPath());
					pub.demarrerServeur();
					pub.OuvreDocument("8100", new File(vue.getTfLETTRE().getText())
							.getPath());

				} else if (typeVersion.getType().get(
						paramPubli.getLogicielPublipostage()).equals(
						TypeVersionPublipostage.TYPE_MSWORD)) {
					PublipostageMsWord pub = new PublipostageMsWord(null);
					// pub.setNomFichierFinalFusionne(new
					// File(vue.getvue.getTfLETTRE()().getText()).getPath());
					pub.OuvreDocument(new File(vue.getTfLETTRE().getText()).getPath());
				}

			}

		});

		vue.getCbListeVersion().addSelectionListener(new SelectionAdapter() {

			@Override
			public void widgetSelected(SelectionEvent e) {
				// // TODO Auto-generated method stub
				// super.widgetSelected(e);
				String versionNew = vue.getCbListeVersion().getItem(vue.getCbListeVersion()
						.getSelectionIndex());
				if (!versionNew.equals(versionPubli)) {
					// if(MessageDialog.openQuestion(vue.getShell(),"Changement de version",
					// "Attention, si vous changez de version de logiciel, tous les chemins de vos types "
					// +
					// "de relances seront réinitialisés. Vous devrez ensuite les renseigner en fonction de la version "
					// +
					// "du logiciel de publipostage choisie !!!"))
					// {
					// Supprimer tous les chemins de tous les types relances
					// avec un message pour prévenir
					TaParamPublipostageDAO daoParam = new TaParamPublipostageDAO();
					if (paramPubli == null)
						paramPubli = daoParam.findInstance();
					paramPubli.setLogicielPublipostage(versionNew);
					paramPubli.setExtension(typeVersion.getExtension().get(
							versionNew));
					paramPubli.setId(1);

					try {
						daoParam.begin(daoParam.getEntityManager()
								.getTransaction());
						paramPubli = daoParam.merge(paramPubli);
						daoParam.commit(daoParam.getEntityManager()
								.getTransaction());
						versionPubli = paramPubli.getLogicielPublipostage();
						// vue.getTfETIQUETTE().setText("");
						vue.getTfLETTRE().setText("");
						if (typeVersion.getType().get(
								vue.getCbListeVersion().getItem(vue.getCbListeVersion()
										.getSelectionIndex())).equals(
								TypeVersionPublipostage.TYPE_MSWORD)) {
							if(type == TYPE_TIERS) {
//								vue.getTfLETTRE()
//									.setText(store.getDefaultString(
//													PreferenceConstants.P_DERNIER_MODELE_UTILISE_WORD));
							} else {
								vue.getTfLETTRE()
								.setText(store.getDefaultString(clePreferenceDerniereValeurWord));
							}
						}
						if (typeVersion.getType().get(
								vue.getCbListeVersion().getItem(vue.getCbListeVersion()
										.getSelectionIndex())).equals(
								TypeVersionPublipostage.TYPE_OPENOFFICE)) {
							if(type == TYPE_TIERS) {
//								vue.getTfLETTRE()
//								.setText(store.getDefaultString(
//											PreferenceConstants.P_DERNIER_MODELE_UTILISE_WORD));
							} else {
								vue.getTfLETTRE()
									.setText(store.getDefaultString(clePreferenceDerniereValeurOOo));
							}
						}

					} catch (Exception e1) {
						logger.error("", e1);
					}
					// }else{
					// int rang =vue.getCbListeVersion().indexOf(versionPubli);
					// if(rang==-1)rang=0;
					// vue.getCbListeVersion().select(rang);
					// }

				}
			}

		});

		vue.getBtnPubli().addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				super.mouseDown(e);
				try {
					initListeValeur();
					final String cheminModele = new File(vue.getTfLETTRE().getText()).getPath();
					Thread t = new Thread() {
						@Override
						public void run() {
							TaParamPublipostage param = new TaParamPublipostageDAO().findInstance();
							//List<TaTiers> listeFinale = new ArrayList<TaTiers>();
							//listeFinale = listeTiers;
							
							//List listeFinale = null;
							
							if(type == TYPE_TIERS) {
//								listeFinale = listeTiersOnglet.get(item);
//							} else {
//								listeFinale = listeArticleOnglet.get(item);
							}

							if(listeFinale!=null) {

								String repert = new File(Platform
										.getInstanceLocation().getURL().getFile()).getPath();
								String extensionFinale = TypeVersionPublipostage
								.getInstance().getExtensionFinale().get(
										param.getLogicielPublipostage());
								
								List<ParamPublipostage> listeParamPubli = null;
//								listeParamPubli = creationFichierPublipostages(
//										listeFinale, repert, extensionFinale,
//										cheminModele);
								
								if(type == TYPE_TIERS) {
//									listeParamPubli = creationFichierPublipostages(
//											listeFinale, repert, extensionFinale,
//											cheminModele);
								} else {
									listeParamPubli = creationFichierPublipostagesArticle(
											listeFinale, repert, extensionFinale,
											cheminModele);
								}

								TypeVersionPublipostage typeVersion = TypeVersionPublipostage
								.getInstance();
								String nonFichierFinal = "publiFusion-"
									+ LibDate.dateToString(new Date(), "")
									+ extensionFinale;

								if (typeVersion.getType().get(
										param.getLogicielPublipostage()).equals(
												TypeVersionPublipostage.TYPE_OPENOFFICE)) {
									
									if(type == TYPE_TIERS) {
//											store.setValue(
//													PreferenceConstants.P_DERNIER_MODELE_UTILISE_OPENOFFICE,
//													cheminModele);
									} else {
											store.setValue(clePreferenceDerniereValeurOOo,
														cheminModele);
									}

									// PublipostageOOoWin32 pub = new
									// PublipostageOOoWin32(listeParamPubli);
									AbstractPublipostageOOo pub = PublipostageOOoFactory
									.createPublipostageOOo(listeParamPubli);
									String pathOpenOffice = "";
									try {
										if (Platform.getOS().equals(
												Platform.OS_WIN32)) {
											pathOpenOffice = WinRegistry
											.readString(
													WinRegistry.HKEY_LOCAL_MACHINE,
													WinRegistry.KEY_REGISTR_WIN_OPENOFFICE,
											"");
										} else if (Platform.getOS().equals(
												Platform.OS_LINUX)) {
											pathOpenOffice = "/usr/bin/soffice";
										} else if(Platform.getOS().equals(Platform.OS_MACOSX)) {}
									} catch (Exception e3) {
										logger.error("", e3);
									}

									pub
									.setCheminOpenOffice(new File(
											pathOpenOffice).getPath());
									pub.setPortOpenOffice("8100");
									pub.setNomFichierFinalFusionne(new File(
											nonFichierFinal).getPath());
									pub.demarrerServeur();
									pub.publipostages();

								} else if (typeVersion.getType().get(
										param.getLogicielPublipostage()).equals(
												TypeVersionPublipostage.TYPE_MSWORD)) {
									
									if(type == TYPE_TIERS) {
//											store.setValue(
//													PreferenceConstants.P_DERNIER_MODELE_UTILISE_WORD,
//													cheminModele);
									} else {
												store.setValue(clePreferenceDerniereValeurWord,
														cheminModele);
									}
									
									PublipostageMsWord pub = new PublipostageMsWord(
											listeParamPubli);
									pub.setNomFichierFinalFusionne(new File(
											nonFichierFinal).getPath());
									pub.publipostages();
								}
							}

						}// fin run
					}; // fin thread
					t.start();

				} catch (Exception e2) {
					logger.error("", e2);
				}
			}
		});
	}
	
	public List<ParamPublipostage> creationFichierPublipostagesArticle(
			List<TaArticle> liste, String repertoireModele,
			String extensionFichierFinal, String fileModele) {
		TaFactureDAO daoFacture = new TaFactureDAO();
		TaFacture facture = null;
		List<ParamPublipostage> listePubli = new LinkedList<ParamPublipostage>();
		if (liste != null && liste.size() > 0) {
			// FileWriter fw = null;
			// BufferedWriter bw = null;
			String codeRelance = "";
			String cheminFichier = "";
			String cheminFichierFinal = "";
			// try {
			// if(bw!=null) bw.close();
			// if(fw!=null) fw.close();
			cheminFichier = new File(repertoireModele + "/Publipostage" + "-"
					+ LibDate.dateToString(new Date(), "") + ".txt").getPath();
			cheminFichierFinal = new File(repertoireModele + "/Publipostage"
					+ "-" + LibDate.dateToString(new Date(), "")
					+ extensionFichierFinal).getPath();
			// fw = new FileWriter(cheminFichier);
			ParamPublipostage param = new ParamPublipostage();
			param.setCheminFichierDonnees(cheminFichier);

			param.setCheminFichierModelLettre(fileModele);

//			param.setCheminFichierMotCle(new File(Const
//					.pathRepertoireSpecifiques("fr.legrain.publipostageTiers",
//							"Modeles")
//					+ "/motcle.properties").getPath());
			param.setCheminFichierMotCle(new File(Const.pathRepertoireSpecifiques("fr.legrain.publipostage.gui","Modeles")
					+ "/motcle_articles.properties").getPath());
//			param.setCheminFichierMotCle(LgrFileBundleLocator.bundleToFile(
//							generationlabeletiquette.Activator.getDefault().getBundle(),
//							"/modelEtiquette/motcle_articles.properties")
//					.getPath());
			// param.setCheminFichierMotCle(new
			// File("/Modeles/motcle.properties").getPath());

			param.setCheminFichierFinal(cheminFichierFinal);
			param.setCheminRepertoireFinal(repertoireModele);
			listePubli.add(param);

			donneesArticle.creationFichierDonnees(liste,
					repertoireModele, cheminFichier,false);
			
//			FichierDonneesAdresseTiers donneesTiers = new FichierDonneesAdresseTiers();
//			donneesTiers.creationFichierDonneesAdresse(liste, repertoireModele,
//					cheminFichier);

		}
		return listePubli;

	}

}
