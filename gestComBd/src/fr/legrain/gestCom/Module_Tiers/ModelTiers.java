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
import fr.legrain.tiers.dao.TaAdresse;
import fr.legrain.tiers.dao.TaEmail;
import fr.legrain.tiers.dao.TaEntreprise;
import fr.legrain.tiers.dao.TaTelephone;
import fr.legrain.tiers.dao.TaTiers;
import fr.legrain.tiers.dao.TaTiersDAO;

public class ModelTiers /*extends AbstractConcurrentModel*/ implements IModelGeneral, ILgrListModel<TaTiers>  {
	static Logger logger = Logger.getLogger(ModelTiers.class.getName());
	LinkedList<SWTTiers> listeObjet = new LinkedList<SWTTiers>();
	private Collection<TaTiers> listeEntity = null;
	private String JPQLQuery = null;
	private String JPQLQuerySansOrder = null;
	private String JPQLQueryLight = null;

	private Class<SWTTiers> typeObjet;
	private LgrDozerMapper<TaTiers,SWTTiers> mapperModelToUI = null;
	private IlgrMapper<SWTTiers, TaTiers> lgrMapper = null;
	private AbstractLgrDAO<TaTiers> dao;

	private EntityManager entityManager = null;

	long _totalMapping = 0;

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
//			try {
//				if (element instanceof SWTTiers) {
//					String result = null;
//					result = BeanUtils.getSimpleProperty(element, listeChamp[columnIndex]);
//					if(result!=null) {
//						if(result.equals("true"))
//							return LibrairiesLeGrainPlugin.ir.get(LibrairiesLeGrainPlugin.CHECK);
//						else if(result.equals("false"))
//							return LibrairiesLeGrainPlugin.ir.get(LibrairiesLeGrainPlugin.UNCHECK);
//					}
//				}
//			} catch (IllegalAccessException e) {
//				logger.error("",e);
//			} catch (InvocationTargetException e) {
//				logger.error("",e);
//			} catch (NoSuchMethodException e) {
//				logger.error("",e);
//			}
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
//				if(result!=null && (result.equals("true") || result.equals("false")))
//					return null;
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

	//public class MyContentProvider extends ObservableCollectionContentProvider implements ILazyContentProvider {
	//public class MyContentProvider extends DeferredContentProvider  {
	//public class MyContentProvider implements org.eclipse.jface.viewers.IContentProvider  {
	public class MyContentProvider implements IStructuredContentProvider  {

		//		protected MyContentProvider(IViewerUpdater explicitViewerUpdater) {
		//			super(explicitViewerUpdater);
		//		}

		public MyContentProvider(TableViewer viewer) {
			//super(null);
			this.viewer = viewer;
		}

		private TableViewer viewer;
		public WritableList elements = new WritableList(listeObjet, SWTTiers.class);

		public void dispose() {
		}

		public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
			//this.elements = (WritableList) newInput;
		}

		public void updateElement(int index) {
			System.err.println("updateElement " + index);
			try {
				if(dao==null) 
					dao = new TaTiersDAO();
				SWTTiers t = remplirListeLazy(dao.getEntityManager(),index);
				//elements.add(t);
				//				Object o = viewer.getElementAt(index);
				//				if(o==null) {
				//					System.err.println("Pas encore d'élément : " + index);
				//				} else {
				//					if(o instanceof SWTTiers) {
				//						System.err.println("Tiers : "+((SWTTiers)o).getCodeTiers()+ " : " + index);
				//					}
				//				}
				if(t==null) {
					t = new SWTTiers();
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
	
	public SWTTiers mapping(TaTiers tiers, SWTTiers swttiers) {
		return mapping(tiers, swttiers, false);
	}
	
	public SWTTiers mapping(TaTiers tiers, SWTTiers swttiers, boolean complet) {
		long intoMapping = System.currentTimeMillis();
		
		
		if(swttiers==null)
			swttiers = new SWTTiers();
		swttiers.setIdTiers(tiers.getIdTiers());
		swttiers.setCodeTiers(tiers.getCodeTiers());
		swttiers.setNomTiers(tiers.getNomTiers());
		swttiers.setPrenomTiers(tiers.getPrenomTiers());
		
		if(tiers.getDateAnniv()!=null)swttiers.setDateAnniv(tiers.getDateAnniv());else
			swttiers.setDateAnniv(new Date());
		
		TaAdresse tiersAdresse = tiers.getTaAdresse();
		if (tiersAdresse != null) {
			swttiers.setVilleAdresse(tiersAdresse.getVilleAdresse());
			swttiers.setCodepostalAdresse(tiersAdresse.getCodepostalAdresse());
		}
		else {
			swttiers.setVilleAdresse("");
			swttiers.setCodepostalAdresse("");
		}
		
		TaEmail tiersEmail = tiers.getTaEmail();
		if (tiersEmail != null) {
			swttiers.setAdresseEmail(tiersEmail.getAdresseEmail());
		}
		else {
			swttiers.setAdresseEmail("");
		}
		
		TaTelephone tiersTel = tiers.getTaTelephone();
		if (tiersTel != null) {
			swttiers.setNumeroTelephone(tiersTel.getNumeroTelephone());
		}
		else {
			swttiers.setNumeroTelephone("");
		}
		
		TaEntreprise tiersEntreprise = tiers.getTaEntreprise();
		if (tiersEntreprise != null) {
			swttiers.setNomEntreprise(tiersEntreprise.getNomEntreprise());
		}
		else {
			swttiers.setNomEntreprise("");
		}
		
		swttiers.setActifTiers(LibConversion.intToBoolean(tiers.getActifTiers()));
		
		if(complet) {
			swttiers.setCodeCompta(tiers.getCodeCompta());
			swttiers.setCompte(tiers.getCompte());
			swttiers.setTtcTiers(LibConversion.intToBoolean(tiers.getTtcTiers()));
			swttiers.setDateAnniv(tiers.getDateAnniv());
			if(tiers.getTaTCivilite()!=null)swttiers.setCodeTCivilite(tiers.getTaTCivilite().getCodeTCivilite());
			if(tiers.getTaTEntite()!=null)swttiers.setCodeTEntite(tiers.getTaTEntite().getCodeTEntite());
			if(tiers.getTaTTiers()!=null)swttiers.setCodeTTiers(tiers.getTaTTiers().getCodeTTiers());
			if(tiers.getTaCompl()!=null)swttiers.setTvaIComCompl(tiers.getTaCompl().getTvaIComCompl());
			if(tiers.getTaCompl()!=null)swttiers.setSiretCompl(tiers.getTaCompl().getSiretCompl());
			if(tiers.getTaCompl()!=null)swttiers.setAccise(tiers.getTaCompl().getAccise());
			if(tiers.getTaCompl()!=null)swttiers.setIcs(tiers.getTaCompl().getIcs());
			if(tiers.getTaAdresse()!=null){
				swttiers.setAdresse1Adresse(tiers.getTaAdresse().getAdresse1Adresse());
				swttiers.setAdresse2Adresse(tiers.getTaAdresse().getAdresse2Adresse());
				swttiers.setAdresse3Adresse(tiers.getTaAdresse().getAdresse3Adresse());
				swttiers.setVilleAdresse(tiers.getTaAdresse().getVilleAdresse());
				swttiers.setCodepostalAdresse(tiers.getTaAdresse().getCodepostalAdresse());
				swttiers.setPaysAdresse(tiers.getTaAdresse().getPaysAdresse());
			}else{
				swttiers.setAdresse1Adresse("");
				swttiers.setAdresse2Adresse("");
				swttiers.setAdresse3Adresse("");
				swttiers.setVilleAdresse("");
				swttiers.setCodepostalAdresse("");
				swttiers.setPaysAdresse("");
			}
			if(tiers.getTaEmail()!=null)swttiers.setAdresseEmail(tiers.getTaEmail().getAdresseEmail());else swttiers.setAdresseEmail("");
			if(tiers.getTaWeb()!=null)swttiers.setAdresseWeb(tiers.getTaWeb().getAdresseWeb());else swttiers.setAdresseWeb("");
			if(tiers.getTaTelephone()!=null)swttiers.setNumeroTelephone(tiers.getTaTelephone().getNumeroTelephone());else swttiers.setNumeroTelephone("");
			if(tiers.getTaTTvaDoc()!=null)swttiers.setCodeTTvaDoc(tiers.getTaTTvaDoc().getCodeTTvaDoc());else swttiers.setCodeTTvaDoc("");
			if(tiers.getTaCPaiement()!=null)swttiers.setCodeCPaiement(tiers.getTaCPaiement().getCodeCPaiement());else swttiers.setCodeCPaiement("");
			if(tiers.getTaTPaiement()!=null)swttiers.setCodeTPaiement(tiers.getTaTPaiement().getCodeTPaiement());else swttiers.setCodeTPaiement("");
			if(tiers.getTaTTarif()!=null)swttiers.setCodeTTarif(tiers.getTaTTarif().getCodeTTarif());else swttiers.setCodeTTarif("");
			if(tiers.getTaCommentaire()!=null)swttiers.setCommentaire(tiers.getTaCommentaire().getCommentaire());else swttiers.setCommentaire("");
			if(tiers.getAccepte()!=null)swttiers.setAccepte(tiers.getAccepte());else swttiers.setAccepte(false);
		}
		long outMapping = System.currentTimeMillis();
		long delta = outMapping - intoMapping;
		_totalMapping += delta;
		return swttiers;
	}
	
	public void addToModel(final TableViewer viewer, TaTiers tiers) {
		SWTTiers swttiers = mapping(tiers,null);
		//listeObjet.add(swttiers);
		//listeEntity.add(tiers);
		
		System.err.println("ID tiers ajouter : "+swttiers.getIdTiers());

		//ViewerComparator vc = viewer.getComparator();
		//viewer.setComparator(null);
		
		viewer.add(swttiers);
		cp.elements.add(swttiers);
		
		//viewer.setComparator(vc);
		viewer.setSelection(new StructuredSelection(swttiers),true);
	}
	
	public void refreshModel(final TableViewer viewer, TaTiers tiers) {
		SWTTiers swttiers = recherche(Const.C_ID_TIERS, tiers.getIdTiers());
		
		swttiers = mapping(tiers,swttiers);
		viewer.update(swttiers, null);

		viewer.setSelection(new StructuredSelection(swttiers),true);
	}
	
	public void removeFromModel(final TableViewer viewer, TaTiers tiers) {
		SWTTiers swttiers = recherche(Const.C_ID_TIERS, tiers.getIdTiers());
		//listeObjet.remove(swttiers);
		//listeEntity.remove(tiers);

		Object suivant=viewer.getElementAt(viewer.getTable().getSelectionIndex()+1);
		Object precedent=viewer.getElementAt(viewer.getTable().getSelectionIndex()-1);
		
		viewer.remove(swttiers);
		
		cp.elements.remove(swttiers);
		if(suivant!=null)
			viewer.setSelection(new StructuredSelection(suivant),true);
		else if(precedent!=null)
			viewer.setSelection(new StructuredSelection(precedent),true);
		else
			viewer.setSelection(new StructuredSelection(cp.elements.get(0)),true);
	}

	public void initCPThread(final TableViewer viewer, final ViewPart v,final ProgressBar bar, final Composite barContainer, final LgrCompositeTableViewer lgrviewer) {
		
//		if(lgrviewer!=null)
//			lgrviewer.tri(SWTTiers.class,"SWTPaTiersController", Const.C_FICHIER_LISTE_CHAMP_GRILLE);
		
		Thread t = new Thread() {
			public void run() {
		
//				viewer.getTable().getDisplay().asyncExec(new Runnable() {
//					public void run() {
//					if(v!=null) v.showBusy(true);
//					}
//				});
				
				TaTiersDAO dao =new TaTiersDAO();
				JPQLQuery = dao.getDefaultJPQLQueryOrderByCodeTiers(); //pas de tri possible avec la table virtuelle donc tri en SQL
				JPQLQuerySansOrder = dao.getDefaultJPQLQuery(); //pas de tri possible avec la table virtuelle donc tri en SQL
				
				/*TODO Faire une requete HQL dediee de type getHibernateTemplate().find(....) en ne récupérant que les colonnes voulues 
				et en jointant sur les tables concernées (adresse, email, telephone, entreprise) 
				Travailler ainsi avec un objet LightTaTiers composé uniquement des attributs minimums qui sont affichés dans la liste
				*/
				JPQLQueryLight = dao.getDefaultLightJPQLQueryOrderByCodeTiers();
				
				Collection<TaTiers> l = null;
				Collection<Object[]> lObjs = null;
				
				if(listeEntity==null) {	//recuperation des entites dans la base de donnees		
					if(JPQLQueryLight!=null) {
						Query ejbQuery = dao.getEntityManager().createQuery(JPQLQueryLight);
						lObjs = ejbQuery.getResultList();
					} else if (dao != null) {
						JPQLQueryLight = dao.getDefaultLightJPQLQueryOrderByCodeTiers();
						//l = dao.selectTiersSurTypeTiers();
						l = dao.selectAll();
					}
					listeEntity = l;
				} else {
					//l = new LinkedList<Entity>();
					l = listeEntity;
				}
				//listeObjet.clear();
				//cp.elements.clear();

				//		listeEntity=dao.selectAll();
				if (lObjs != null) {

					final Collection<Object[]> lObjFinal = lObjs;
					final int[] cpt = new int[1];
					cpt[0] = 0;
					viewer.getTable().getDisplay().asyncExec(new Runnable() {

						public void run() {
							bar.setMaximum(lObjFinal.size());
						}
					});
					
					for(Object[] obj : lObjs){
						final Object[] finalObj = obj;
						if (viewer.getTable().isDisposed()) {
							return;
						}

						viewer.getTable().getDisplay().asyncExec(new Runnable() {

							public void run() {
								SWTTiers swttiers = getSWTTiersLight(finalObj);
								//listeObjet.add(swttiers);
								viewer.add(swttiers);
								cp.elements.add(swttiers);
								
								cpt[0] = cpt[0]+1;
								bar.setSelection(cpt[0]);
							
								if(cpt[0]==lObjFinal.size()) {
									((GridData) barContainer.getLayoutData()).exclude = true;
									Composite c = barContainer.getParent();
									barContainer.dispose();
									c.layout(true);
									
									if(lgrviewer!=null) {
										//activation du tri sur toute les colonnes
										//lgrviewer.tri(SWTTiers.class,"SWTPaTiersController", Const.C_FICHIER_LISTE_CHAMP_GRILLE);
										
										//Pour corriger le problème si on active le tri sur une colonne à la création du viewer
										//viewer.refresh();
									}
								}
							}
						});
					}					
				}	
				else if(l != null) {
					final Collection<TaTiers> lfinal = l;
					final int[] cpt = new int[1];
					cpt[0] = 0;
					viewer.getTable().getDisplay().asyncExec(new Runnable() {

						public void run() {
							bar.setMaximum(lfinal.size());
						}
					});
					
					for(TaTiers ti : l){
						final TaTiers tiers = ti;
						if (viewer.getTable().isDisposed()) {
							return;
						}

						viewer.getTable().getDisplay().asyncExec(new Runnable() {

							public void run() {
								SWTTiers swttiers = mapping(tiers,null);
								//listeObjet.add(swttiers);
								logger.info("TOTAL mapping time = "+_totalMapping);
								viewer.add(swttiers);
								cp.elements.add(swttiers);
								
								cpt[0] = cpt[0]+1;
								bar.setSelection(cpt[0]);
							
								if(cpt[0]==lfinal.size()) {
									((GridData) barContainer.getLayoutData()).exclude = true;
									Composite c = barContainer.getParent();
									barContainer.dispose();
									c.layout(true);
									
									if(lgrviewer!=null) {
										//activation du tri sur toute les colonnes
										//lgrviewer.tri(SWTTiers.class,"SWTPaTiersController", Const.C_FICHIER_LISTE_CHAMP_GRILLE);
										
										//Pour corriger le problème si on active le tri sur une colonne à la création du viewer
										//viewer.refresh();
									}
								}
							}
						});
					}					
				}
//				viewer.getTable().getDisplay().asyncExec(new Runnable() {
//					public void run() {
//				if(v!=null) v.showBusy(false);
//					}});
			
			}
			
		};
		t.start();
	}

	public SWTTiers remplirListeLazy(EntityManager em, int id) {
		System.err.println("remplirListeLazy " + id);
		TaTiersDAO dao =new TaTiersDAO(em);
		SWTTiers swttiers = null;
		Date dateDeb = new Date();
		Collection<TaTiers> l = new ArrayList<TaTiers>();
		if(listeEntity==null) {
			listeEntity = new LinkedList<TaTiers>();
		}
		//if(listeEntity==null) {	//recuperation des entites dans la base de donnees	
		TaTiers t = dao.findById(id);
		if(t!=null) {
			//l.add(t);
			//l = new ArrayList<TaTiers>();
			//			if(JPQLQuery!=null) {
			//				Query ejbQuery = em.createQuery(JPQLQuery);
			//				l = ejbQuery.getResultList();
			//			} else if (dao != null) {
			//				l = dao.selectAll();
			//			}
			listeEntity.add(t);
			//	listeEntity = l;
			//} else {
			//l = new LinkedList<Entity>();
			//	l = listeEntity;
			//}

			//listeObjet.clear();

			//		listeEntity=dao.selectAll();
			//if(l != null) {
			//for(TaTiers tiers : l){
			TaTiers tiers = t;
			swttiers = new SWTTiers();
			swttiers.setIdTiers(tiers.getIdTiers());
			swttiers.setCodeTiers(tiers.getCodeTiers());
			swttiers.setActifTiers(LibConversion.intToBoolean(tiers.getActifTiers()));
			swttiers.setCodeCompta(tiers.getCodeCompta());
			swttiers.setCompte(tiers.getCompte());
			swttiers.setNomTiers(tiers.getNomTiers());
			swttiers.setPrenomTiers(tiers.getPrenomTiers());
			swttiers.setDateAnniv(tiers.getDateAnniv());
			if(tiers.getTaTCivilite()!=null)swttiers.setCodeTCivilite(tiers.getTaTCivilite().getCodeTCivilite());
			if(tiers.getTaTEntite()!=null)swttiers.setCodeTEntite(tiers.getTaTEntite().getCodeTEntite());
			if(tiers.getTaTTiers()!=null)swttiers.setCodeTTiers(tiers.getTaTTiers().getCodeTTiers());
			if(tiers.getTaEntreprise()!=null)swttiers.setNomEntreprise(tiers.getTaEntreprise().getNomEntreprise());
			if(tiers.getTaCompl()!=null)swttiers.setTvaIComCompl(tiers.getTaCompl().getTvaIComCompl());
			if(tiers.getTaCompl()!=null)swttiers.setSiretCompl(tiers.getTaCompl().getSiretCompl());
			if(tiers.getTaCompl()!=null)swttiers.setAccise(tiers.getTaCompl().getAccise());
			if(tiers.getTaCompl()!=null)swttiers.setIcs(tiers.getTaCompl().getIcs());
			if(tiers.getTaAdresse()!=null)swttiers.setAdresse1Adresse(tiers.getTaAdresse().getAdresse1Adresse());
			if(tiers.getTaAdresse()!=null)swttiers.setAdresse2Adresse(tiers.getTaAdresse().getAdresse2Adresse());
			if(tiers.getTaAdresse()!=null)swttiers.setAdresse3Adresse(tiers.getTaAdresse().getAdresse3Adresse());
			if(tiers.getTaAdresse()!=null)swttiers.setVilleAdresse(tiers.getTaAdresse().getVilleAdresse());
			if(tiers.getTaAdresse()!=null)swttiers.setCodepostalAdresse(tiers.getTaAdresse().getCodepostalAdresse());
			if(tiers.getTaAdresse()!=null)swttiers.setPaysAdresse(tiers.getTaAdresse().getPaysAdresse());
			if(tiers.getTaEmail()!=null)swttiers.setAdresseEmail(tiers.getTaEmail().getAdresseEmail());
			if(tiers.getTaWeb()!=null)swttiers.setAdresseWeb(tiers.getTaWeb().getAdresseWeb());
			if(tiers.getTaTelephone()!=null)swttiers.setNumeroTelephone(tiers.getTaTelephone().getNumeroTelephone());
			if(tiers.getTaTTvaDoc()!=null)swttiers.setCodeTTvaDoc(tiers.getTaTTvaDoc().getCodeTTvaDoc());
			swttiers.setTtcTiers(LibConversion.intToBoolean(tiers.getTtcTiers()));
			if(tiers.getTaCPaiement()!=null)swttiers.setCodeCPaiement(tiers.getTaCPaiement().getCodeCPaiement());
			if(tiers.getTaTPaiement()!=null)swttiers.setCodeTPaiement(tiers.getTaTPaiement().getCodeTPaiement());
			if(tiers.getTaTTarif()!=null)swttiers.setCodeTTarif(tiers.getTaTTarif().getCodeTTarif());
			if(tiers.getTaCommentaire()!=null)swttiers.setCommentaire(tiers.getTaCommentaire().getCommentaire());
			if(tiers.getAccepte()!=null)swttiers.setAccepte(tiers.getAccepte());else swttiers.setAccepte(false);
			listeObjet.add(swttiers);
			//	}
			//}
		}

		Date dateFin = new Date();
		logger.info("temp remplirListeLazy "+new Date(dateFin.getTime()-dateDeb.getTime()));
		return swttiers;
	}

	//@Override
	//remettre le extends AbstractConcurrentModel dans la déclaration de la classe
	public void requestUpdate(final IConcurrentModelListener listener) {
		Thread t = new Thread() {

			@Override
			public void run() {

				TaTiersDAO dao =new TaTiersDAO();
				SWTTiers swttiers = null;
				Date dateDeb = new Date();
				Collection<TaTiers> l = null;
				if(listeEntity==null) {	//recuperation des entites dans la base de donnees		
					if(JPQLQuery!=null) {
						Query ejbQuery = dao.getEntityManager().createQuery(JPQLQuery);
						l = ejbQuery.getResultList();
					} else if (dao != null) {
						l = dao.selectAll();
					}
					listeEntity = l;
				} else {
					//l = new LinkedList<Entity>();
					l = listeEntity;
				}
				listeObjet.clear();

				if(l != null) {
					for(TaTiers tiers : l){
						swttiers = new SWTTiers();
						swttiers.setIdTiers(tiers.getIdTiers());
						swttiers.setCodeTiers(tiers.getCodeTiers());
						swttiers.setActifTiers(LibConversion.intToBoolean(tiers.getActifTiers()));
						swttiers.setCodeCompta(tiers.getCodeCompta());
						swttiers.setCompte(tiers.getCompte());
						swttiers.setNomTiers(tiers.getNomTiers());
						swttiers.setPrenomTiers(tiers.getPrenomTiers());
						swttiers.setDateAnniv(tiers.getDateAnniv());
						if(tiers.getTaTCivilite()!=null)swttiers.setCodeTCivilite(tiers.getTaTCivilite().getCodeTCivilite());
						if(tiers.getTaTEntite()!=null)swttiers.setCodeTEntite(tiers.getTaTEntite().getCodeTEntite());
						if(tiers.getTaTTiers()!=null)swttiers.setCodeTTiers(tiers.getTaTTiers().getCodeTTiers());
						if(tiers.getTaEntreprise()!=null)swttiers.setNomEntreprise(tiers.getTaEntreprise().getNomEntreprise());
						if(tiers.getTaCompl()!=null)swttiers.setTvaIComCompl(tiers.getTaCompl().getTvaIComCompl());
						if(tiers.getTaCompl()!=null)swttiers.setSiretCompl(tiers.getTaCompl().getSiretCompl());
						if(tiers.getTaCompl()!=null)swttiers.setAccise(tiers.getTaCompl().getAccise());
						if(tiers.getTaCompl()!=null)swttiers.setIcs(tiers.getTaCompl().getIcs());
						if(tiers.getTaAdresse()!=null)swttiers.setAdresse1Adresse(tiers.getTaAdresse().getAdresse1Adresse());
						if(tiers.getTaAdresse()!=null)swttiers.setAdresse2Adresse(tiers.getTaAdresse().getAdresse2Adresse());
						if(tiers.getTaAdresse()!=null)swttiers.setAdresse3Adresse(tiers.getTaAdresse().getAdresse3Adresse());
						if(tiers.getTaAdresse()!=null)swttiers.setVilleAdresse(tiers.getTaAdresse().getVilleAdresse());
						if(tiers.getTaAdresse()!=null)swttiers.setCodepostalAdresse(tiers.getTaAdresse().getCodepostalAdresse());
						if(tiers.getTaAdresse()!=null)swttiers.setPaysAdresse(tiers.getTaAdresse().getPaysAdresse());
						if(tiers.getTaEmail()!=null)swttiers.setAdresseEmail(tiers.getTaEmail().getAdresseEmail());
						if(tiers.getTaWeb()!=null)swttiers.setAdresseWeb(tiers.getTaWeb().getAdresseWeb());
						if(tiers.getTaTelephone()!=null)swttiers.setNumeroTelephone(tiers.getTaTelephone().getNumeroTelephone());
						if(tiers.getTaTTvaDoc()!=null)swttiers.setCodeTTvaDoc(tiers.getTaTTvaDoc().getCodeTTvaDoc());
						swttiers.setTtcTiers(LibConversion.intToBoolean(tiers.getTtcTiers()));
						if(tiers.getTaCPaiement()!=null)swttiers.setCodeCPaiement(tiers.getTaCPaiement().getCodeCPaiement());
						if(tiers.getTaTPaiement()!=null)swttiers.setCodeTPaiement(tiers.getTaTPaiement().getCodeTPaiement());
						if(tiers.getTaTTarif()!=null)swttiers.setCodeTTarif(tiers.getTaTTarif().getCodeTTarif());
						if(tiers.getTaCommentaire()!=null)swttiers.setCommentaire(tiers.getTaCommentaire().getCommentaire());
						if(tiers.getAccepte()!=null)swttiers.setAccepte(tiers.getAccepte());else swttiers.setAccepte(false);
						listeObjet.add(swttiers);
						listener.add(new Object[]{swttiers});
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
	public LinkedList<SWTTiers> remplirListe(EntityManager em) {
		TaTiersDAO dao =new TaTiersDAO(em);
		SWTTiers swttiers = null;
		Date dateDeb = new Date();
		Collection<TaTiers> l = null;
		if(listeEntity==null) {	//recuperation des entites dans la base de donnees		
			if(JPQLQuery!=null) {
				Query ejbQuery = em.createQuery(JPQLQuery);
				l = ejbQuery.getResultList();
			} else if (dao != null) {
				l = dao.selectAll();
			}
			listeEntity = l;
		} else {
			//l = new LinkedList<Entity>();
			l = listeEntity;
		}
		listeObjet.clear();

		//		listeEntity=dao.selectAll();
		if(l != null) {
			for(TaTiers tiers : l){
				swttiers = new SWTTiers();
				swttiers.setIdTiers(tiers.getIdTiers());
				swttiers.setCodeTiers(tiers.getCodeTiers());
				swttiers.setActifTiers(LibConversion.intToBoolean(tiers.getActifTiers()));
				swttiers.setCodeCompta(tiers.getCodeCompta());
				swttiers.setCompte(tiers.getCompte());
				swttiers.setNomTiers(tiers.getNomTiers());
				swttiers.setPrenomTiers(tiers.getPrenomTiers());
				swttiers.setDateAnniv(tiers.getDateAnniv());
				if(tiers.getTaTCivilite()!=null)swttiers.setCodeTCivilite(tiers.getTaTCivilite().getCodeTCivilite());
				if(tiers.getTaTEntite()!=null)swttiers.setCodeTEntite(tiers.getTaTEntite().getCodeTEntite());
				if(tiers.getTaTTiers()!=null)swttiers.setCodeTTiers(tiers.getTaTTiers().getCodeTTiers());
				if(tiers.getTaEntreprise()!=null)swttiers.setNomEntreprise(tiers.getTaEntreprise().getNomEntreprise());
				if(tiers.getTaCompl()!=null)swttiers.setTvaIComCompl(tiers.getTaCompl().getTvaIComCompl());
				if(tiers.getTaCompl()!=null)swttiers.setSiretCompl(tiers.getTaCompl().getSiretCompl());
				if(tiers.getTaCompl()!=null)swttiers.setAccise(tiers.getTaCompl().getAccise());
				if(tiers.getTaCompl()!=null)swttiers.setIcs(tiers.getTaCompl().getIcs());
				if(tiers.getTaAdresse()!=null)swttiers.setAdresse1Adresse(tiers.getTaAdresse().getAdresse1Adresse());
				if(tiers.getTaAdresse()!=null)swttiers.setAdresse2Adresse(tiers.getTaAdresse().getAdresse2Adresse());
				if(tiers.getTaAdresse()!=null)swttiers.setAdresse3Adresse(tiers.getTaAdresse().getAdresse3Adresse());
				if(tiers.getTaAdresse()!=null)swttiers.setVilleAdresse(tiers.getTaAdresse().getVilleAdresse());
				if(tiers.getTaAdresse()!=null)swttiers.setCodepostalAdresse(tiers.getTaAdresse().getCodepostalAdresse());
				if(tiers.getTaAdresse()!=null)swttiers.setPaysAdresse(tiers.getTaAdresse().getPaysAdresse());
				if(tiers.getTaEmail()!=null)swttiers.setAdresseEmail(tiers.getTaEmail().getAdresseEmail());
				if(tiers.getTaWeb()!=null)swttiers.setAdresseWeb(tiers.getTaWeb().getAdresseWeb());
				if(tiers.getTaTelephone()!=null)swttiers.setNumeroTelephone(tiers.getTaTelephone().getNumeroTelephone());
				if(tiers.getTaTTvaDoc()!=null)swttiers.setCodeTTvaDoc(tiers.getTaTTvaDoc().getCodeTTvaDoc());
				swttiers.setTtcTiers(LibConversion.intToBoolean(tiers.getTtcTiers()));
				if(tiers.getTaCPaiement()!=null)swttiers.setCodeCPaiement(tiers.getTaCPaiement().getCodeCPaiement());
				if(tiers.getTaTPaiement()!=null)swttiers.setCodeTPaiement(tiers.getTaTPaiement().getCodeTPaiement());
				if(tiers.getTaTTarif()!=null)swttiers.setCodeTTarif(tiers.getTaTTarif().getCodeTTarif());
				if(tiers.getTaCommentaire()!=null)swttiers.setCommentaire(tiers.getTaCommentaire().getCommentaire());
				if(tiers.getAccepte()!=null)swttiers.setAccepte(tiers.getAccepte());else swttiers.setAccepte(false);
				listeObjet.add(swttiers);
			}
		}

		Date dateFin = new Date();
		logger.info("temp remplirListe "+new Date(dateFin.getTime()-dateDeb.getTime()));
		return listeObjet;
	}



	public SWTTiers recherche(String propertyName, Object value, boolean startWith) {
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
				
//				if(PropertyUtils.getProperty(listeObjet.get(i), propertyName).equals(value)) {
//					trouve = true;
//				} else {
//					i++;
//				}
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
	
	public SWTTiers recherche(String propertyName, Object value) {
		return recherche(propertyName,value,false);
	}
	
	public LinkedList<SWTTiers> getListeObjet() {
		return listeObjet;
	}

	public Collection<TaTiers> getListeEntity() {
		return listeEntity;
	}

	public void setListeEntity(Collection<TaTiers> listeEntity) {
		this.listeEntity = listeEntity;
	}

	public void setListeObjet(LinkedList<SWTTiers> listeObjet) {
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
		if(mapperModelToUI==null)mapperModelToUI=new LgrDozerMapper<TaTiers, SWTTiers>();
		mapperModelToUI.getMapper().setCustomFieldMapper(custom);
	}

	public LgrDozerMapper<TaTiers, SWTTiers> getMapperModelToUI() {
		return mapperModelToUI;
	}

	public void setMapperModelToUI(LgrDozerMapper<TaTiers, SWTTiers> mapperModelToUI) {
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
		dao = new TaTiersDAO();
		return remplirListe(dao.getEntityManager());
	}

	@Override
	public List remplirListeElement(Object e, String propertyName, Object value) {
		// TODO Auto-generated method stub
		return null;
	}

	public String getJPQLQuerySansOrder() {
		return JPQLQuerySansOrder;
	}

	public void setJPQLQuerySansOrder(String jPQLQuerySansOrder) {
		JPQLQuerySansOrder = jPQLQuerySansOrder;
	}
	
	/**
	 * getSWTTiersLightList
	 * 
	 * Recupere une liste d'objets SWTTiers avec les attributs minimums
	 * 
	 * @param taTiersCollection : collection de tableau de resultats de requete HQL
	 * @return SWTTiers
	 */
	public List<SWTTiers> getSWTTiersLightList(
			Collection<Object[]> taTiersCollection) {

		List<SWTTiers> swtTiersList = new ArrayList<SWTTiers>();

		for (Object[] objects : taTiersCollection) {
			swtTiersList.add(getSWTTiersLight(objects));
		}

		return swtTiersList;
	}
	
	/**
	 * getSWTTiersLight
	 * 
	 * Recupere un objet SWTTiers en fonction d'un tableau de resultats HQL
	 * 
	 * @param objs : tableau de resultats correspondant aux attributs minimums
	 * @return SWTTiers
	 */
	public SWTTiers getSWTTiersLight(Object[] objs){
		SWTTiers swttiers = new SWTTiers();;
		
		swttiers.setIdTiers((Integer)objs[0]);
		swttiers.setCodeTiers((String)objs[1]);
		swttiers.setNomTiers((String)objs[2]);
		swttiers.setPrenomTiers(objs[5]!=null?(String)objs[3]:null);
		
		Date anniv = objs[4]!=null?(Date)objs[4]:null;
		String ville = objs[5]!=null?(String)objs[5]:null;
		String cp = objs[6]!=null?(String)objs[6]:null;
		String email = objs[7]!=null?(String)objs[7]:null;
		String tel = objs[8]!=null?(String)objs[8]:null;
		String ent = objs[9]!=null?(String)objs[9]:null;
		
		if (anniv != null) {
			swttiers.setDateAnniv(anniv);
		} else {
			swttiers.setDateAnniv(new Date());
		}
		
		if (ville != null) {
			swttiers.setVilleAdresse(ville);
		} else {
			swttiers.setVilleAdresse("");
		}
		
		if (cp != null) {
			swttiers.setCodepostalAdresse(cp);
		} else {
			swttiers.setCodepostalAdresse("");
		}
		
		if (email != null) {
			swttiers.setAdresseEmail(email);
		}
		else {
			swttiers.setAdresseEmail("");
		}
		
		if (tel != null) {
			swttiers.setNumeroTelephone(tel);
		}
		else {
			swttiers.setNumeroTelephone("");
		}
		
		if (ent != null) {
			swttiers.setNomEntreprise(ent);
		}
		else {
			swttiers.setNomEntreprise("");
		}
		
		swttiers.setActifTiers(LibConversion.intToBoolean((Integer)objs[10]));
		
		return swttiers;
	}
	
}
