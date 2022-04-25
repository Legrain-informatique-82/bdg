package fr.legrain.gestCom.Module_Tiers;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.log4j.Logger;
import org.eclipse.core.databinding.observable.IObservableCollection;
import org.eclipse.core.databinding.observable.list.WritableList;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.deferred.IConcurrentModelListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.ProgressBar;
import org.eclipse.ui.part.ViewPart;

import fr.legrain.SupportAbonLegrain.dao.TaUtilisateur;
import fr.legrain.SupportAbonLegrain.dao.TaUtilisateurDAO;
import fr.legrain.abonnement.dao.TaAbonnement;
import fr.legrain.abonnement.dao.TaAbonnementDAO;
import fr.legrain.articles.dao.TaArticle;
import fr.legrain.articles.dao.TaArticleDAO;
import fr.legrain.articles.dao.TaTSupport;
import fr.legrain.articles.dao.TaTSupportDAO;
import fr.legrain.documents.dao.TaLFacture;
import fr.legrain.documents.dao.TaLFactureDAO;
import fr.legrain.gestCom.Appli.Const;
import fr.legrain.gestCom.Appli.CustomFieldMapperIFLGR;
import fr.legrain.gestCom.Appli.ILgrListModel;
import fr.legrain.gestCom.Appli.IlgrMapper;
import fr.legrain.gestCom.Appli.LgrDozerMapper;
import fr.legrain.lib.data.AbstractLgrDAO;
import fr.legrain.lib.data.IModelGeneral;
import fr.legrain.lib.data.LibConversion;
import fr.legrain.lib.data.ModelObject;
import fr.legrain.lib.gui.grille.LgrCompositeTableViewer;
import fr.legrain.lib.gui.grille.LgrTableViewer;
import fr.legrain.licenceBdg.dao.TaLicenceBdg;
import fr.legrain.licenceBdg.dao.TaLicenceBdgDAO;
import fr.legrain.licenceEpicea.dao.TaLicenceEpicea;
import fr.legrain.tiers.dao.TaEntreprise;
import fr.legrain.tiers.dao.TaFamilleTiers;
import fr.legrain.tiers.dao.TaFamilleTiersDAO;
import fr.legrain.tiers.dao.TaTEntite;
import fr.legrain.tiers.dao.TaTiers;
import fr.legrain.tiers.dao.TaTiersDAO;

public class ModelBdg /*extends AbstractConcurrentModel*/ implements IModelGeneral, ILgrListModel<TaLicenceBdg>  {
	static Logger logger = Logger.getLogger(ModelBdg.class.getName());
	LinkedList<SWTSupportAbonLogiciel> listeObjet = new LinkedList<SWTSupportAbonLogiciel>();
	private Collection<TaLicenceBdg> listeEntity = null;
	private String JPQLQuery = null;
	private Class<SWTSupportAbonLogiciel> typeObjet;
	private LgrDozerMapper<TaLicenceBdg,SWTSupportAbonLogiciel> mapperModelToUI = null;
	private IlgrMapper<SWTSupportAbonLogiciel, TaLicenceBdg> lgrMapper = null;
	private AbstractLgrDAO<TaLicenceBdg> dao;
	private TaAbonnementDAO daoAbonnement=null;

	private EntityManager entityManager = null;


	/* ****************************************************************************
	 * Test LazyLoading et remplissage à partir d'un thread pour le TableViewer
	 * ****************************************************************************/
	public TableLabelProvider getTableLabelProvider(String[] listeChamp) {
		return new TableLabelProvider(listeChamp);
	}

	private class TableLabelProvider implements ITableLabelProvider {
		private String[] listeChamp = null;
		public TableLabelProvider(String[] listeChamp) {
			this.listeChamp = listeChamp;
		}

		public Image getColumnImage(Object element, int columnIndex) {
			return null;
		}

		public String getColumnText(Object element, int columnIndex) {
			if (element instanceof SWTTiers) {
				String result = null;
				try {
					result = BeanUtils.getSimpleProperty(element, listeChamp[columnIndex]);
				} catch (IllegalAccessException e) {
					logger.error("",e);
				} catch (InvocationTargetException e) {
					logger.error("",e);
				} catch (NoSuchMethodException e) {
					logger.error("",e);
				}
				return result;
			}
			return "?"; //$NON-NLS-1$
		}

		public void addListener(ILabelProviderListener listener) {
		}

		public void dispose() {

		}

		public boolean isLabelProperty(Object element, String property) {
			return false;
		}

		public void removeListener(ILabelProviderListener listener) {
		}

	}


	public class MyContentProvider implements IStructuredContentProvider  {

		public MyContentProvider(TableViewer viewer) {

			this.viewer = viewer;
		}

		private TableViewer viewer;
		public WritableList elements = new WritableList(listeObjet, SWTSupportAbonLogiciel.class);

		public void dispose() {
		}

		public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
		}

		public void updateElement(int index) {
			System.err.println("updateElement " + index);
			try {
				if(dao==null) 
					dao = new TaLicenceBdgDAO();
				if(daoAbonnement==null)
					daoAbonnement= new TaAbonnementDAO();
				
				SWTSupportAbonLogiciel t = remplirListeLazy(dao.getEntityManager(),index);
				if(t==null) {
					t = new SWTSupportAbonLogiciel();
					t.setCodeTiers("Pas de tiers avec l'id "+index);
				}
				viewer.replace(t, index);
			} catch(Exception e) {
				logger.error("",e);
			}
		}

		//@Override
		protected void checkInput(Object input) {
			// TODO Auto-generated method stub
		}

		//@Override
		protected void addCollectionChangeListener(IObservableCollection collection) {
			// TODO Auto-generated method stub
		}

		//@Override
		protected void removeCollectionChangeListener(IObservableCollection collection) {
			// TODO Auto-generated method stub
		}

		@Override
		public Object[] getElements(Object inputElement) {
			// TODO Auto-generated method stub
			return elements.toArray();
		}
	}

	public MyContentProvider cp = null;
	public MyContentProvider initCP(LgrTableViewer viewer) {
		//cp = new MyContentProvider(viewer);
		cp = new MyContentProvider(viewer);
		initCPThread(viewer, null,null,null,null);
		return cp;
	}
	
	public MyContentProvider initCP(LgrTableViewer viewer, ViewPart v) {
		//cp = new MyContentProvider(viewer);
		cp = new MyContentProvider(viewer);
		initCPThread(viewer,v,null,null,null);
		return cp;
	}
	
	public MyContentProvider initCP(TableViewer viewer, ViewPart v, ProgressBar bar, Composite barContainer, LgrCompositeTableViewer lgrviewer) {
		//cp = new MyContentProvider(viewer);
		cp = new MyContentProvider(viewer);
		initCPThread(viewer,v,bar, barContainer,lgrviewer);
		return cp;
	}
	

	
	public SWTSupportAbonLogiciel mapping(TaLicenceBdg licence, SWTSupportAbonLogiciel swtEpicea) {
//		if(daoAbonnement==null)
			daoAbonnement= new TaAbonnementDAO();
		if(swtEpicea==null)
			swtEpicea = new SWTSupportAbonLogiciel();
		if(licence.getTaLDocument()!=null){
			swtEpicea.setCodeDocument(licence.getTaLDocument().getTaDocument().getCodeDocument());
			swtEpicea.setIdLDocument(licence.getTaLDocument().getIdLDocument());
		}
		if(licence==null)licence=new TaLicenceBdg();
		if(licence.getTaArticle()!=null){
			swtEpicea.setCodeArticle(licence.getTaArticle().getCodeArticle());
			swtEpicea.setIdArticle(licence.getTaArticle().getIdArticle());
		}
		if(licence.getTaTSupport()!=null){
			swtEpicea.setCodeTSupport(licence.getTaTSupport().getCodeTSupport());
			swtEpicea.setIdTSupport(licence.getTaTSupport().getIdTSupport());
		}
		swtEpicea.setCodeSupportAbon(licence.getCodeSupportAbon());
		swtEpicea.setIdSupportAbon(licence.getIdSupportAbon());
		swtEpicea.setLibelle(licence.getLibelle());
		swtEpicea.setCommentaire(licence.getCommentaire());
		if(licence.getCommercial()!=null){
			swtEpicea.setCommercial(licence.getCommercial().getCodeTiers());
			swtEpicea.setIdCommercial(licence.getCommercial().getIdTiers());
		}
		swtEpicea.setDateAcquisition(licence.getDateAcquisition());
		if(licence.getGroupe()!=null){
			swtEpicea.setCodeFamille(licence.getGroupe().getCodeFamille());
			swtEpicea.setIdFamille(licence.getGroupe().getIdFamille());
		}
		if(licence.getTaUtilisateur()!=null){
			swtEpicea.setIdUtilisateur(licence.getTaUtilisateur().getIdUtilisateur());
			swtEpicea.setEmail(licence.getTaUtilisateur().getEmail());
			swtEpicea.setTel(licence.getTaUtilisateur().getTel());
			swtEpicea.setNom(licence.getTaUtilisateur().getNom());
			swtEpicea.setPrenom(licence.getTaUtilisateur().getPrenom());
		}
		if(licence.getTaTiers()!=null){
			swtEpicea.setIdTiers(licence.getTaTiers().getIdTiers());
			swtEpicea.setCodeTiers(licence.getTaTiers().getCodeTiers());
			if(licence.getTaTiers().getTaCommercial()!=null){
				swtEpicea.setCommercial(licence.getTaTiers().getTaCommercial().getCodeTiers());
			}
			if(licence.getTaTiers().getTaEntreprise()!=null){
				swtEpicea.setEntreprise(licence.getTaTiers().getTaEntreprise().getNomEntreprise());
			}
			if(licence.getTaTiers().getTaTEntite()!=null){
				swtEpicea.setCodeEntite(licence.getTaTiers().getTaTEntite().getCodeTEntite());
			}			
		}
		swtEpicea.setDateDebAbon(null);
		swtEpicea.setDateFinAbon(null);
		if(licence.getTaAbonnements().size()>0){
			TaAbonnement abon= daoAbonnement.selectDernierAbonnement(licence.getIdSupportAbon());
			if(abon!=null){
				swtEpicea.setDateDebAbon(abon.getDateDebut());
				swtEpicea.setDateFinAbon(abon.getDateFin());
			}
		}
		swtEpicea.setTelechargement(LibConversion.intToBoolean(licence.getTelechargement()));
		swtEpicea.setInactif(LibConversion.intToBoolean(licence.getInactif()));
		return swtEpicea;
	}
	
	public TaLicenceBdg mapping( SWTSupportAbonLogiciel swtEpicea,TaLicenceBdg licence) {
		if(licence==null)
			licence = new TaLicenceBdg();
		TaTiersDAO daoTiers=new TaTiersDAO();
		TaFamilleTiersDAO daoFamille = new TaFamilleTiersDAO();
		TaUtilisateurDAO daoUtilisateur = new TaUtilisateurDAO();
		TaArticleDAO daoArticle = new TaArticleDAO();
		TaLFactureDAO daolFacture = new TaLFactureDAO();
		TaTSupportDAO daoTSupport = new TaTSupportDAO();
		TaTiers tiers=null;
		TaTiers commercial=null;
		TaFamilleTiers groupe=null;
		TaUtilisateur utilisateur=null;
		TaArticle article =null;
		TaLFacture lFacture = null;
		TaTSupport tSupport = null;
		TaTEntite tentite = null;
		TaEntreprise entreprise = null;
		try {
			if(swtEpicea.getIdLDocument()!=null)
				lFacture=daolFacture.findById(swtEpicea.getIdLDocument());
		} catch (Exception e) {}
		try {
			if(swtEpicea.getCommercial()!=null)
				commercial=daoTiers.findByCode(swtEpicea.getCommercial());
		} catch (Exception e) {}
		try {
			if(swtEpicea.getCodeFamille()!=null)
				groupe=daoFamille.findByCode(swtEpicea.getCodeFamille());
		} catch (Exception e) {}
		try {
			if(swtEpicea.getCodeTSupport()!=null)
				tSupport=daoTSupport.findByCode(swtEpicea.getCodeTSupport());
		} catch (Exception e) {}		
		try {
			if(swtEpicea.getIdUtilisateur()!=null && swtEpicea.getIdUtilisateur()!=0){
				utilisateur=daoUtilisateur.findById(swtEpicea.getIdUtilisateur());
				utilisateur.setNom(swtEpicea.getNom());
				utilisateur.setPrenom(swtEpicea.getPrenom());
				utilisateur.setEmail(swtEpicea.getEmail());
				utilisateur.setTel(swtEpicea.getTel());
				licence.setTaUtilisateur(utilisateur);
			}
			else if (swtEpicea.getNom()!=null || swtEpicea.getPrenom()!=null ||
					swtEpicea.getEmail()!=null || swtEpicea.getTel()!=null){
				utilisateur=new TaUtilisateur();
				utilisateur.setIdUtilisateur(0);
				utilisateur.setNom(swtEpicea.getNom());
				utilisateur.setPrenom(swtEpicea.getPrenom());
				utilisateur.setEmail(swtEpicea.getEmail());
				utilisateur.setTel(swtEpicea.getTel());
				licence.setTaUtilisateur(utilisateur);
			}
		} catch (Exception e) {}
		
		try {
			if(swtEpicea.getCodeTiers()!=null)
				tiers=daoTiers.findByCode(swtEpicea.getCodeTiers());
		} catch (Exception e) {}
		try {
			if(swtEpicea.getCodeArticle()!=null)
				article=daoArticle.findByCode(swtEpicea.getCodeArticle());
		} catch (Exception e) {}
		licence.setCodeSupportAbon(swtEpicea.getCodeSupportAbon());
		licence.setCommentaire(swtEpicea.getCommentaire());
		licence.setCommercial(commercial);
		licence.setDateAcquisition(swtEpicea.getDateAcquisition());
		licence.setGroupe(groupe);
		licence.setIdSupportAbon(swtEpicea.getIdSupportAbon());
		licence.setLibelle(swtEpicea.getLibelle());
		licence.setTaArticle(article);
		licence.setTaTiers(tiers);
		licence.setTaLDocument(lFacture);
		licence.setTaTSupport(tSupport);
		licence.setInactif(LibConversion.booleanToInt(swtEpicea.getInactif()));
		licence.setTelechargement(LibConversion.booleanToInt(swtEpicea.getTelechargement()));
		if(licence.getTaUtilisateur()==null && tiers!=null){
			utilisateur= new TaUtilisateur();
			utilisateur.setNom(tiers.getNomTiers());
			utilisateur.setPrenom(tiers.getPrenomTiers());
			if(tiers.getTaEmail()!=null)utilisateur.setEmail(tiers.getTaEmail().getAdresseEmail());
			if(tiers.getTaTelephone()!=null)utilisateur.setTel(tiers.getTaTelephone().getNumeroTelephone());
			licence.setTaUtilisateur(utilisateur);
		}
		if(licence.getIdSupportAbon()==null)licence.setIdSupportAbon(0);
		return licence;
	}
	
	public void addToModel(final TableViewer viewer, TaLicenceBdg licence) {
		SWTSupportAbonLogiciel swtEpicea = mapping(licence,null);
		listeEntity.add(licence);
		
		System.err.println("ID licenceEpicea ajouter : "+swtEpicea.getIdSupportAbon());
		
		viewer.add(swtEpicea);
		cp.elements.add(swtEpicea);
		
		viewer.setSelection(new StructuredSelection(swtEpicea),true);
	}
	
	public void refreshModel(final TableViewer viewer, TaLicenceBdg licence) {
		SWTSupportAbonLogiciel swtEpicea = recherche(Const.C_ID_SUPPORT_ABON, licence.getIdSupportAbon());
		
		swtEpicea = mapping(licence,swtEpicea);
		viewer.update(swtEpicea, null);

		viewer.setSelection(new StructuredSelection(swtEpicea),true);
	}
	
	public void removeFromModel(final TableViewer viewer, TaLicenceBdg licence) {
		SWTSupportAbonLogiciel swtEpicea = recherche(Const.C_ID_SUPPORT_ABON, licence.getIdSupportAbon());
		//listeObjet.remove(swttiers);
		listeEntity.remove(licence);

		Object suivant=viewer.getElementAt(viewer.getTable().getSelectionIndex()+1);
		Object precedent=viewer.getElementAt(viewer.getTable().getSelectionIndex()-1);
		
		viewer.remove(swtEpicea);
		
		cp.elements.remove(swtEpicea);
		if(suivant!=null)
			viewer.setSelection(new StructuredSelection(suivant),true);
		else if(precedent!=null)
			viewer.setSelection(new StructuredSelection(precedent),true);
		else
			viewer.setSelection(new StructuredSelection(cp.elements.get(0)),true);
	}

	public void initCPThread(final TableViewer viewer, final ViewPart v,final ProgressBar bar, 
			final Composite barContainer, final LgrCompositeTableViewer lgrviewer) {
		
		Thread t = new Thread() {
			public void run() {
				TaLicenceBdgDAO dao =new TaLicenceBdgDAO();
				Collection<TaLicenceBdg> l = null;
				if(listeEntity==null) {	//recuperation des entites dans la base de donnees		
					if(JPQLQuery!=null) {
						Query ejbQuery = dao.getEntityManager().createQuery(JPQLQuery);
						l = ejbQuery.getResultList();
					} else if (dao != null) {
						JPQLQuery = dao.getJPQLQuery();
						l = dao.selectAll();
					}
					listeEntity = l;
				} else {
					l = listeEntity;
				}
				if(l != null) {
					final Collection<TaLicenceBdg> lfinal = l;
					final int[] cpt = new int[1];
					cpt[0] = 0;
					viewer.getTable().getDisplay().asyncExec(new Runnable() {

						public void run() {
							bar.setMaximum(lfinal.size());
						}
					});
					
					
					for(TaLicenceBdg ti : l){
						final TaLicenceBdg licence = ti;
						if (viewer.getTable().isDisposed()) {
							return;
						}

						viewer.getTable().getDisplay().asyncExec(new Runnable() {

							public void run() {
								SWTSupportAbonLogiciel swtEpicea = mapping(licence,null);

								viewer.add(swtEpicea);
								cp.elements.add(swtEpicea);
								
								cpt[0] = cpt[0]+1;
								bar.setSelection(cpt[0]);
							
								if(cpt[0]==lfinal.size()) {
									((GridData) barContainer.getLayoutData()).exclude = true;
									Composite c = barContainer.getParent();
									barContainer.dispose();
									c.layout(true);
									
									if(lgrviewer!=null) {
									}
								}
							}
						});
					}
					
					
					
				}
			
			}
			
		};
		t.start();
	}

	public SWTSupportAbonLogiciel remplirListeLazy(EntityManager em, int id) {
		System.err.println("remplirListeLazy " + id);
		TaLicenceBdgDAO dao =new TaLicenceBdgDAO(em);
		SWTSupportAbonLogiciel swtEpicea = null;
		Date dateDeb = new Date();
		Collection<TaLicenceBdg> l = new ArrayList<TaLicenceBdg>();
		if(listeEntity==null) {
			listeEntity = new LinkedList<TaLicenceBdg>();
		}
		TaLicenceBdg t = dao.findById(id);
		if(t!=null) {
			listeEntity.add(t);

			TaLicenceBdg tiers = t;
			swtEpicea = new SWTSupportAbonLogiciel();
//			swtEpicea.setIdTiers(tiers.getIdTiers());
//			swtEpicea.setCodeTiers(tiers.getCodeTiers());
//			swtEpicea.setActifTiers(LibConversion.intToBoolean(tiers.getActifTiers()));
//			swtEpicea.setCodeCompta(tiers.getCodeCompta());
//			swtEpicea.setCompte(tiers.getCompte());
//			swtEpicea.setNomTiers(tiers.getNomTiers());
//			swtEpicea.setPrenomTiers(tiers.getPrenomTiers());
//			if(tiers.getTaTCivilite()!=null)swtEpicea.setCodeTCivilite(tiers.getTaTCivilite().getCodeTCivilite());
//			if(tiers.getTaTEntite()!=null)swtEpicea.setCodeTEntite(tiers.getTaTEntite().getCodeTEntite());
//			if(tiers.getTaTTiers()!=null)swtEpicea.setCodeTTiers(tiers.getTaTTiers().getCodeTTiers());
//			if(tiers.getTaEntreprise()!=null)swtEpicea.setNomEntreprise(tiers.getTaEntreprise().getNomEntreprise());
//			if(tiers.getTaCompl()!=null)swtEpicea.setTvaIComCompl(tiers.getTaCompl().getTvaIComCompl());
//			if(tiers.getTaCompl()!=null)swtEpicea.setSiretCompl(tiers.getTaCompl().getSiretCompl());
//			if(tiers.getTaCompl()!=null)swtEpicea.setAccise(tiers.getTaCompl().getAccise());
//			if(tiers.getTaAdresse()!=null)swtEpicea.setAdresse1Adresse(tiers.getTaAdresse().getAdresse1Adresse());
//			if(tiers.getTaAdresse()!=null)swtEpicea.setAdresse2Adresse(tiers.getTaAdresse().getAdresse2Adresse());
//			if(tiers.getTaAdresse()!=null)swtEpicea.setAdresse3Adresse(tiers.getTaAdresse().getAdresse3Adresse());
//			if(tiers.getTaAdresse()!=null)swtEpicea.setVilleAdresse(tiers.getTaAdresse().getVilleAdresse());
//			if(tiers.getTaAdresse()!=null)swtEpicea.setCodepostalAdresse(tiers.getTaAdresse().getCodepostalAdresse());
//			if(tiers.getTaAdresse()!=null)swtEpicea.setPaysAdresse(tiers.getTaAdresse().getPaysAdresse());
//			if(tiers.getTaEmail()!=null)swtEpicea.setAdresseEmail(tiers.getTaEmail().getAdresseEmail());
//			if(tiers.getTaWeb()!=null)swtEpicea.setAdresseWeb(tiers.getTaWeb().getAdresseWeb());
//			if(tiers.getTaTelephone()!=null)swtEpicea.setNumeroTelephone(tiers.getTaTelephone().getNumeroTelephone());
//			if(tiers.getTaTTvaDoc()!=null)swtEpicea.setCodeTTvaDoc(tiers.getTaTTvaDoc().getCodeTTvaDoc());
//			swtEpicea.setTtcTiers(LibConversion.intToBoolean(tiers.getTtcTiers()));
//			if(tiers.getTaCPaiement()!=null)swtEpicea.setCodeCPaiement(tiers.getTaCPaiement().getCodeCPaiement());
//			if(tiers.getTaTPaiement()!=null)swtEpicea.setCodeTPaiement(tiers.getTaTPaiement().getCodeTPaiement());
//			if(tiers.getTaTTarif()!=null)swtEpicea.setCodeTTarif(tiers.getTaTTarif().getCodeTTarif());
//			if(tiers.getTaCommentaire()!=null)swtEpicea.setCommentaire(tiers.getTaCommentaire().getCommentaire());
//			if(tiers.getAccepte()!=null)swtEpicea.setAccepte(tiers.getAccepte());else swtEpicea.setAccepte(false);
			listeObjet.add(swtEpicea);
			//	}
			//}
		}

		Date dateFin = new Date();
		logger.info("temp remplirListeLazy "+new Date(dateFin.getTime()-dateDeb.getTime()));
		return swtEpicea;
	}

	//@Override
	//remettre le extends AbstractConcurrentModel dans la déclaration de la classe
	public void requestUpdate(final IConcurrentModelListener listener) {
		Thread t = new Thread() {

			@Override
			public void run() {

				TaLicenceBdgDAO dao =new TaLicenceBdgDAO();
				SWTSupportAbonLogiciel swtEpicea = null;
				Date dateDeb = new Date();
				Collection<TaLicenceBdg> l = null;
				if(listeEntity==null) {	//recuperation des entites dans la base de donnees		
					if(JPQLQuery!=null) {
						Query ejbQuery = dao.getEntityManager().createQuery(JPQLQuery);
						l = ejbQuery.getResultList();
					} else if (dao != null) {
						l = dao.selectAll();
					}
					listeEntity = l;
				} else {
					l = listeEntity;
				}
				listeObjet.clear();

				if(l != null) {
					for(TaLicenceBdg licence : l){
						swtEpicea = new SWTSupportAbonLogiciel();
//						swtEpicea.setIdTiers(licence.getIdTiers());
//						swtEpicea.setCodeTiers(licence.getCodeTiers());
//						swtEpicea.setActifTiers(LibConversion.intToBoolean(licence.getActifTiers()));
//						swtEpicea.setCodeCompta(licence.getCodeCompta());
//						swtEpicea.setCompte(licence.getCompte());
//						swtEpicea.setNomTiers(licence.getNomTiers());
//						swtEpicea.setPrenomTiers(licence.getPrenomTiers());
//						if(licence.getTaTCivilite()!=null)swtEpicea.setCodeTCivilite(licence.getTaTCivilite().getCodeTCivilite());
//						if(licence.getTaTEntite()!=null)swtEpicea.setCodeTEntite(licence.getTaTEntite().getCodeTEntite());
//						if(licence.getTaTTiers()!=null)swtEpicea.setCodeTTiers(licence.getTaTTiers().getCodeTTiers());
//						if(licence.getTaEntreprise()!=null)swtEpicea.setNomEntreprise(licence.getTaEntreprise().getNomEntreprise());
//						if(licence.getTaCompl()!=null)swtEpicea.setTvaIComCompl(licence.getTaCompl().getTvaIComCompl());
//						if(licence.getTaCompl()!=null)swtEpicea.setSiretCompl(licence.getTaCompl().getSiretCompl());
//						if(licence.getTaCompl()!=null)swtEpicea.setAccise(licence.getTaCompl().getAccise());
//						if(licence.getTaAdresse()!=null)swtEpicea.setAdresse1Adresse(licence.getTaAdresse().getAdresse1Adresse());
//						if(licence.getTaAdresse()!=null)swtEpicea.setAdresse2Adresse(licence.getTaAdresse().getAdresse2Adresse());
//						if(licence.getTaAdresse()!=null)swtEpicea.setAdresse3Adresse(licence.getTaAdresse().getAdresse3Adresse());
//						if(licence.getTaAdresse()!=null)swtEpicea.setVilleAdresse(licence.getTaAdresse().getVilleAdresse());
//						if(licence.getTaAdresse()!=null)swtEpicea.setCodepostalAdresse(licence.getTaAdresse().getCodepostalAdresse());
//						if(licence.getTaAdresse()!=null)swtEpicea.setPaysAdresse(licence.getTaAdresse().getPaysAdresse());
//						if(licence.getTaEmail()!=null)swtEpicea.setAdresseEmail(licence.getTaEmail().getAdresseEmail());
//						if(licence.getTaWeb()!=null)swtEpicea.setAdresseWeb(licence.getTaWeb().getAdresseWeb());
//						if(licence.getTaTelephone()!=null)swtEpicea.setNumeroTelephone(licence.getTaTelephone().getNumeroTelephone());
//						if(licence.getTaTTvaDoc()!=null)swtEpicea.setCodeTTvaDoc(licence.getTaTTvaDoc().getCodeTTvaDoc());
//						swtEpicea.setTtcTiers(LibConversion.intToBoolean(licence.getTtcTiers()));
//						if(licence.getTaCPaiement()!=null)swtEpicea.setCodeCPaiement(licence.getTaCPaiement().getCodeCPaiement());
//						if(licence.getTaTPaiement()!=null)swtEpicea.setCodeTPaiement(licence.getTaTPaiement().getCodeTPaiement());
//						if(licence.getTaTTarif()!=null)swtEpicea.setCodeTTarif(licence.getTaTTarif().getCodeTTarif());
//						if(licence.getTaCommentaire()!=null)swtEpicea.setCommentaire(licence.getTaCommentaire().getCommentaire());
//						if(licence.getAccepte()!=null)swtEpicea.setAccepte(licence.getAccepte());else swtEpicea.setAccepte(false);
						listeObjet.add(swtEpicea);
						listener.add(new Object[]{swtEpicea});
					}
				}
			}

		};
		t.start();

	}

	/* ****************************************************************************
	 * Fin code de Test
	 * ****************************************************************************/
	@SuppressWarnings("unchecked")
	public LinkedList<SWTSupportAbonLogiciel> remplirListe(EntityManager em) {
		TaLicenceBdgDAO dao =new TaLicenceBdgDAO(em);
		SWTSupportAbonLogiciel swtEpicea = null;
		Date dateDeb = new Date();
		Collection<TaLicenceBdg> l = null;
		if(listeEntity==null) {	//recuperation des entites dans la base de donnees		
			if(JPQLQuery!=null) {
				Query ejbQuery = em.createQuery(JPQLQuery);
				l = ejbQuery.getResultList();
			} else if (dao != null) {
				l = dao.selectAll();
			}
			listeEntity = l;
		} else {
			l = listeEntity;
		}
		listeObjet.clear();

		if(l != null) {
			for(TaLicenceBdg licence : l){
				swtEpicea = new SWTSupportAbonLogiciel();
				listeObjet.add(swtEpicea);
			}
		}

		Date dateFin = new Date();
		logger.info("temp remplirListe "+new Date(dateFin.getTime()-dateDeb.getTime()));
		return listeObjet;
	}



	public SWTSupportAbonLogiciel recherche(String propertyName, Object value, boolean startWith) {
		boolean trouve = false;
		int i = 0;
		while(!trouve && i<listeObjet.size()){
			try {
				PropertyUtils.getProperty(listeObjet.get(i), propertyName);
				if(startWith) {
					trouve = PropertyUtils.getProperty(listeObjet.get(i), propertyName).toString().startsWith(value.toString());
				} else {
					trouve = PropertyUtils.getProperty(listeObjet.get(i), propertyName).equals(value);
				}
				
				if(!trouve) {
					i++;
				}
			} catch (IllegalAccessException e) {

			} catch (InvocationTargetException e) {

			} catch (NoSuchMethodException e) {

			}
		}

		if(trouve)
			return listeObjet.get(i);
		else 
			return null;

		//		return trouve ? listeObjet.get(i) : null;
	}
	
	public SWTSupportAbonLogiciel recherche(String propertyName, Object value) {
		return recherche(propertyName,value,false);
	}
	
	public LinkedList<SWTSupportAbonLogiciel> getListeObjet() {
		return listeObjet;
	}

	public Collection<TaLicenceBdg> getListeEntity() {
		return listeEntity;
	}

	public void setListeEntity(Collection<TaLicenceBdg> listeEntity) {
		this.listeEntity = listeEntity;
	}

	public void setListeObjet(LinkedList<SWTSupportAbonLogiciel> listeObjet) {
		this.listeObjet = listeObjet;
	}

	public String getJPQLQuery() {
		return JPQLQuery;
	}

	public void setJPQLQuery(String jPQLQuery) {
		JPQLQuery = jPQLQuery;
	}

	//	@Override
	//	public ModelObject rechercheDansListe(LinkedList<ModelObject> listeObjet,
	//			String propertyName, Object value) {
	//		// TODO Auto-generated method stub
	//		return null;
	//	}

	//	@Override
	//	public List remplirListe() {
	//		// TODO Auto-generated method stub
	//		return remplirListe(new TaTiersDAO().getEntityManager());
	//	}

	//	@Override
	//	public List remplirListeElement(Object entite,String propertyName, Object value) {
	//		try {
	//			logger.debug("ModelGeneralObjet.remplirListeElement()");
	//
	//			Collection<TaTiers> l = null;
	//
	//			//			if(listeEntity==null) {	//recuperation des entites dans la base de donnees		
	//			if(JPQLQuery!=null) {
	//				CustomFieldMapperIFLGR customFieldMapperIFLGR = new CustomFieldMapperIFLGR(propertyName);
	//				if(lgrMapper!=null)
	//					lgrMapper.entityToDto((TaTiers)entite);
	//				else {
	//					
	//					mapperModelToUI.getMapper().setCustomFieldMapper(customFieldMapperIFLGR);
	//					mapperModelToUI.map((TaTiers)entite, SWTTiers.class);
	//				}
	//
	//				Query ejbQuery = null;	
	//
	//				String predicat=" where ";
	//					if(JPQLQuery.toUpperCase().contains(" WHERE "))predicat=" and ";
	//				//traiter suivant type champ
	//				if (customFieldMapperIFLGR.getFieldMapRetour().getSrcFieldType(entite.getClass())
	//						==java.util.Date.class ||
	//						customFieldMapperIFLGR.getFieldMapRetour().getSrcFieldType(entite.getClass())==Timestamp.class) {
	//					ejbQuery = entityManager.createQuery(JPQLQuery
	//							+predicat+" EXTRACT(DAY FROM "+customFieldMapperIFLGR.getFieldMapRetour().getSrcFieldName()+") = ?"
	//							+" and EXTRACT(MONTH FROM "+customFieldMapperIFLGR.getFieldMapRetour().getSrcFieldName()+") = ?"
	//							+" and EXTRACT(YEAR FROM "+customFieldMapperIFLGR.getFieldMapRetour().getSrcFieldName()+") = ?"
	//					);						
	//					java.util.Date date =LibDate.stringToDate(value.toString());
	//					ejbQuery.setParameter(1,LibConversion.stringToInteger(LibDate.getJour(date)));
	//					ejbQuery.setParameter(2,LibConversion.stringToInteger(LibDate.getMois(date)));
	//					ejbQuery.setParameter(3,LibConversion.stringToInteger(LibDate.getAnnee(date)));							
	//				}else if (customFieldMapperIFLGR.getFieldMapRetour().getSrcFieldType(entite.getClass())
	//							==String.class) {
	//					if(!value.toString().equals("")) {
	//					String aliasEntiteDansRequete ="";
	//					if(JPQLQuery.toLowerCase().contains("alias"))
	//						aliasEntiteDansRequete="alias";
	//					else
	//						aliasEntiteDansRequete=JPQLQuery.substring("select ".length(),"select ".length()+1);
	//					ejbQuery = entityManager.createQuery(JPQLQuery
	//							+predicat+" UPPER("+aliasEntiteDansRequete+"."+customFieldMapperIFLGR.getFieldMapRetour().getSrcFieldName()+") "
	//							+" like '%"+value.toString().toUpperCase()+"%'"
	//							//+" where "+customFieldMapperIFLGR.getFieldMapRetour().getSrcFieldName()+" like ?"
	//							//+" where "+aliasEntiteDansRequete+"."+customFieldMapperIFLGR.getFieldMapRetour().getSrcFieldName()+" like ?"
	//					);
	//					//ejbQuery.setParameter(1, ""+value.toString().toUpperCase()+"");
	//					}
	//				}else{
	//					String aliasEntiteDansRequete ="";
	//					if(JPQLQuery.toLowerCase().contains("alias"))
	//						aliasEntiteDansRequete="alias";
	//					else
	//						aliasEntiteDansRequete=JPQLQuery.substring("select ".length(),"select ".length()+1);
	//					ejbQuery = entityManager.createQuery(JPQLQuery
	//							+predicat+" UPPER("+aliasEntiteDansRequete+"."+customFieldMapperIFLGR.getFieldMapRetour().getSrcFieldName()+") = ?"
	//							//+" where "+aliasEntiteDansRequete+"."+customFieldMapperIFLGR.getFieldMapRetour().getSrcFieldName()+" = ?"
	//					);
	//
	//					if (customFieldMapperIFLGR.getFieldMapRetour().getSrcFieldType(entite.getClass())
	//							==Integer.class) {
	//						if(customFieldMapperIFLGR.getFieldMapRetour().getDestFieldType(typeObjet)==Boolean.class)
	//							ejbQuery.setParameter(1,LibConversion.booleanToInt(LibConversion.StringToBoolean(value.toString())) );
	//						else
	//						ejbQuery.setParameter(1,LibConversion.stringToInteger(value.toString()) );
	//					}
	//					if (customFieldMapperIFLGR.getFieldMapRetour().getSrcFieldType(entite.getClass())
	//							==Double.class) {
	//						ejbQuery.setParameter(1, LibConversion.stringToDouble(value.toString()));
	//					}
	//					if (customFieldMapperIFLGR.getFieldMapRetour().getSrcFieldType(entite.getClass())
	//							==Float.class) {
	//						ejbQuery.setParameter(1, LibConversion.stringToFloat(value.toString()));
	//					}
	//					if (customFieldMapperIFLGR.getFieldMapRetour().getSrcFieldType(entite.getClass())
	//							==Short.class) {
	//						ejbQuery.setParameter(1, LibConversion.stringToShort(value.toString()));
	//					}
	//					if (customFieldMapperIFLGR.getFieldMapRetour().getSrcFieldType(entite.getClass())
	//							==BigDecimal.class) {
	//						ejbQuery.setParameter(1, LibConversion.stringToBigDecimal(value.toString()));
	//					}
	//					if (customFieldMapperIFLGR.getFieldMapRetour().getSrcFieldType(entite.getClass())
	//							==Long.class) {
	//						ejbQuery.setParameter(1, LibConversion.stringToLong(value.toString()));
	//					}
	//					
	//				}
	//				if(ejbQuery!=null)
	//					l = ejbQuery.getResultList();
	//
	//
	//			} else if (dao != null) {
	//				l = dao.selectAll();
	//			}
	//			listeEntity = l;
	//	
	//			listeObjet.clear();
	//
	//			if(l!=null) {
	//				for (TaTiers o : l) {
	//					SWTTiers t = null;
	//					if(lgrMapper!=null)
	//						t = lgrMapper.entityToDto(o);
	//					else
	//						t = (SWTTiers) mapperModelToUI.map(o, typeObjet);
	//					listeObjet.add(t);
	//				}
	//			}
	//			return listeObjet;
	//		} catch(Exception e) {
	//			logger.error("",e);
	//		}
	//
	//		return listeObjet;
	//	}

	//	@Override
	//	public void razListEntity() {
	//		// TODO Auto-generated method stub
	//		
	//	}

	public void customMapper(CustomFieldMapperIFLGR custom){
		if(mapperModelToUI==null)mapperModelToUI=new LgrDozerMapper<TaLicenceBdg, SWTSupportAbonLogiciel>();
		mapperModelToUI.getMapper().setCustomFieldMapper(custom);
	}

	public LgrDozerMapper<TaLicenceBdg, SWTSupportAbonLogiciel> getMapperModelToUI() {
		return mapperModelToUI;
	}

	public void setMapperModelToUI(LgrDozerMapper<TaLicenceBdg, SWTSupportAbonLogiciel> mapperModelToUI) {
		this.mapperModelToUI = mapperModelToUI;
	}

	@Override
	public void razListEntity() {
		// TODO Auto-generated method stub
		listeEntity.clear();
	}

	@Override
	public ModelObject rechercheDansListe(LinkedList<ModelObject> listeObjet,
			String propertyName, Object value) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List remplirListe() {
		// TODO Auto-generated method stub
		dao = new TaLicenceBdgDAO();
		return remplirListe(dao.getEntityManager());
	}

	@Override
	public List remplirListeElement(Object e, String propertyName, Object value) {
		// TODO Auto-generated method stub
		return null;
	}



}
