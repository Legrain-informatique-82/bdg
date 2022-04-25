package fr.legrain.gestCom.Module_Articles;

import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
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

import fr.legrain.articles.dao.TaArticle;
import fr.legrain.articles.dao.TaArticleDAO;
import fr.legrain.articles.dao.TaFamille;
import fr.legrain.articles.dao.TaPrix;
import fr.legrain.articles.dao.TaRTaTitreTransport;
import fr.legrain.articles.dao.TaRapportUnite;
import fr.legrain.articles.dao.TaTTva;
import fr.legrain.articles.dao.TaTva;
import fr.legrain.articles.dao.TaUnite;
import fr.legrain.gestCom.Appli.Const;
import fr.legrain.gestCom.Appli.CustomFieldMapperIFLGR;
import fr.legrain.gestCom.Appli.ILgrListModel;
import fr.legrain.gestCom.Appli.IlgrMapper;
import fr.legrain.gestCom.Appli.LgrDozerMapper;
import fr.legrain.gestioncapsules.dao.TaTitreTransport;
import fr.legrain.lib.data.AbstractLgrDAO;
import fr.legrain.lib.data.IModelGeneral;
import fr.legrain.lib.data.LibConversion;
import fr.legrain.lib.data.ModelObject;
import fr.legrain.lib.gui.grille.LgrCompositeTableViewer;
import fr.legrain.lib.gui.grille.LgrTableViewer;

public class ModelArticles /*extends AbstractConcurrentModel*/ implements IModelGeneral, ILgrListModel<TaArticle>  {
	static Logger logger = Logger.getLogger(ModelArticles.class.getName());
	LinkedList<SWTArticle> listeObjet = new LinkedList<SWTArticle>();
	private Collection<TaArticle> listeEntity = null;
	private String JPQLQuery = null;
	private String JPQLQuerySansOrder = null;
	private String JPQLQueryLight = null;

	private Class<SWTArticle> typeObjet;
	private LgrDozerMapper<TaArticle,SWTArticle> mapperModelToUI = null;
	private IlgrMapper<SWTArticle, TaArticle> lgrMapper = null;
	private AbstractLgrDAO<TaArticle> dao;

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
//				if (element instanceof SWTArticle) {
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
			if (element instanceof SWTArticle) {
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
		public WritableList elements = new WritableList(listeObjet, SWTArticle.class);

		public void dispose() {
		}

		public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
			//this.elements = (WritableList) newInput;
		}

		public void updateElement(int index) {
			System.err.println("updateElement " + index);
			try {
				if(dao==null) 
					dao = new TaArticleDAO();
				SWTArticle t = remplirListeLazy(dao.getEntityManager(),index);
				//elements.add(t);
				//				Object o = viewer.getElementAt(index);
				//				if(o==null) {
				//					System.err.println("Pas encore d'élément : " + index);
				//				} else {
				//					if(o instanceof SWTArticle) {
				//						System.err.println("Tiers : "+((SWTArticle)o).getCodeTiers()+ " : " + index);
				//					}
				//				}
				if(t==null) {
					t = new SWTArticle();
					t.setCodeArticle("Pas d'article avec l'id "+index);
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
	
	public SWTArticle mapping(TaArticle tiers, SWTArticle SWTArticle) {
		return mapping(tiers, SWTArticle, false);
	}
	
	public SWTArticle mapping(TaArticle article, SWTArticle SWTArticle, boolean complet) {
		long intoMapping = System.currentTimeMillis();

		if(SWTArticle==null)
			SWTArticle = new SWTArticle();
		SWTArticle.setIdArticle(article.getIdArticle());
		SWTArticle.setCodeArticle(article.getCodeArticle());
		SWTArticle.setLibellelArticle(article.getLibellelArticle());
		SWTArticle.setLibellecArticle(article.getLibellecArticle());
		SWTArticle.setNumcptArticle(article.getNumcptArticle());
		SWTArticle.setDiversArticle(article.getNumcptArticle());
		SWTArticle.setCommentaireArticle(article.getCommentaireArticle());
		SWTArticle.setStockMinArticle(article.getStockMinArticle());
		SWTArticle.setLongueur(article.getLongueur());
		SWTArticle.setLargeur(article.getLargeur());
		SWTArticle.setHauteur(article.getHauteur());
		SWTArticle.setPoids(article.getPoids());
		SWTArticle.setActif(LibConversion.intToBoolean(article.getActif()));
		
		TaFamille taFamille = article.getTaFamille();
		if(taFamille!=null){
			SWTArticle.setIdFamille(taFamille.getIdFamille());
			SWTArticle.setCodeFamille(taFamille.getCodeFamille());
			SWTArticle.setLibcFamille(taFamille.getLibcFamille());
		}
		TaPrix taPrix = article.getTaPrix();
		if(taPrix!=null){
			SWTArticle.setPrixPrix(taPrix.getPrixPrix());	
			SWTArticle.setPrixttcPrix(taPrix.getPrixttcPrix());	
			TaUnite taUnite = taPrix.getTaUnite();
			if(taUnite!=null){
				SWTArticle.setCodeUnite(taUnite.getCodeUnite());	
			}
		}
		TaTva taTva = article.getTaTva();
		if(taTva!=null){
			SWTArticle.setIdTva(taTva.getIdTva());
			SWTArticle.setTauxTva(taTva.getTauxTva());
			SWTArticle.setCodeTva(taTva.getCodeTva());
			SWTArticle.setNumcptTva(taTva.getNumcptTva());
		}
		TaTTva taTTva = article.getTaTTva();
		if(taTTva!=null){
			SWTArticle.setIdTTva(taTTva.getIdTTva());
			SWTArticle.setCodeTTva(taTTva.getCodeTTva());
			SWTArticle.setLibTTva(taTTva.getLibTTva());
		}
		TaRapportUnite taRapportUnite = article.getTaRapportUnite();
		if(taRapportUnite!=null){
			SWTArticle.setRapport(taRapportUnite.getRapport());
			SWTArticle.setSens(LibConversion.intToBoolean(taRapportUnite.getSens()));
			SWTArticle.setNbDecimal(taRapportUnite.getNbDecimal());
			TaUnite taUnite2 = taRapportUnite.getTaUnite2();
			if(taUnite2!=null){
				SWTArticle.setCodeUnite2(taUnite2.getCodeUnite());
			}
		}

		TaRTaTitreTransport taRTaTitreTransport = article.getTaRTaTitreTransport();
		if(taRTaTitreTransport!=null){
			SWTArticle.setQteTitreTransport(taRTaTitreTransport.getQteTitreTransport());
			TaTitreTransport taTitreTransport = taRTaTitreTransport.getTaTitreTransport();
			if(taTitreTransport!=null){
				SWTArticle.setCodeTitreTransport(taTitreTransport.getCodeTitreTransport());
			}
		}
		
		
		long outMapping = System.currentTimeMillis();
		long delta = outMapping - intoMapping;
		_totalMapping += delta;
		return SWTArticle;
	}
	
	public void addToModel(final TableViewer viewer, TaArticle tiers) {
		SWTArticle SWTArticle = mapping(tiers,null);
		//listeObjet.add(SWTArticle);
		listeEntity.add(tiers);
		
		System.err.println("ID article ajouter : "+SWTArticle.getIdArticle());

		//ViewerComparator vc = viewer.getComparator();
		//viewer.setComparator(null);
		
		viewer.add(SWTArticle);
		cp.elements.add(SWTArticle);
		
		//viewer.setComparator(vc);
		viewer.setSelection(new StructuredSelection(SWTArticle),true);
	}
	
	public void refreshModel(final TableViewer viewer, TaArticle article) {
		SWTArticle SWTArticle = recherche(Const.C_ID_ARTICLE, article.getIdArticle());
		
		SWTArticle = mapping(article,SWTArticle);
		viewer.update(SWTArticle, null);

		viewer.setSelection(new StructuredSelection(SWTArticle),true);
	}
	
	public void removeFromModel(final TableViewer viewer, TaArticle article) {
		SWTArticle SWTArticle = recherche(Const.C_ID_ARTICLE, article.getIdArticle());
		//listeObjet.remove(SWTArticle);
		listeEntity.remove(article);

		Object suivant=viewer.getElementAt(viewer.getTable().getSelectionIndex()+1);
		Object precedent=viewer.getElementAt(viewer.getTable().getSelectionIndex()-1);
		
		viewer.remove(SWTArticle);
		
		cp.elements.remove(SWTArticle);
		if(suivant!=null)
			viewer.setSelection(new StructuredSelection(suivant),true);
		else if(precedent!=null)
			viewer.setSelection(new StructuredSelection(precedent),true);
		else
			viewer.setSelection(new StructuredSelection(cp.elements.get(0)),true);
	}

	public void initCPThread(final TableViewer viewer, final ViewPart v,final ProgressBar bar, final Composite barContainer, final LgrCompositeTableViewer lgrviewer) {
		
//		if(lgrviewer!=null)
//			lgrviewer.tri(SWTArticle.class,"SWTPaTiersController", Const.C_FICHIER_LISTE_CHAMP_GRILLE);
		
		Thread t = new Thread() {
			public void run() {
		
//				viewer.getTable().getDisplay().asyncExec(new Runnable() {
//					public void run() {
//					if(v!=null) v.showBusy(true);
//					}
//				});
				
				TaArticleDAO dao =new TaArticleDAO();
				JPQLQuery = dao.getDefaultJPQLQuery(); //pas de tri possible avec la table virtuelle donc tri en SQL
				
				/*TODO Faire une requete HQL dediee de type getHibernateTemplate().find(....) en ne récupérant que les colonnes voulues 
				et en jointant sur les tables concernées (adresse, email, telephone, entreprise) 
				Travailler ainsi avec un objet LightTaArticle composé uniquement des attributs minimums qui sont affichés dans la liste
				*/
				JPQLQueryLight = dao.getDefaultLightJPQLQueryEcran();
				
				Collection<TaArticle> l = null;
				Collection<Object[]> lObjs = null;
				
				if(listeEntity==null) {	//recuperation des entites dans la base de donnees		
					if(JPQLQueryLight!=null) {
						Query ejbQuery = dao.getEntityManager().createQuery(JPQLQueryLight);
						lObjs = ejbQuery.getResultList();
					} else if (dao != null) {
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
								SWTArticle SWTArticle = getSWTArticleLight(finalObj);
								//listeObjet.add(SWTArticle);
								viewer.add(SWTArticle);
								cp.elements.add(SWTArticle);
								
								cpt[0] = cpt[0]+1;
								bar.setSelection(cpt[0]);
							
								if(cpt[0]==lObjFinal.size()) {
									((GridData) barContainer.getLayoutData()).exclude = true;
									Composite c = barContainer.getParent();
									barContainer.dispose();
									c.layout(true);
									
									if(lgrviewer!=null) {
										//activation du tri sur toute les colonnes
										//lgrviewer.tri(SWTArticle.class,"SWTPaTiersController", Const.C_FICHIER_LISTE_CHAMP_GRILLE);
										
										//Pour corriger le problème si on active le tri sur une colonne à la création du viewer
										//viewer.refresh();
									}
								}
							}
						});
					}					
				}	
				else if(l != null) {
					final Collection<TaArticle> lfinal = l;
					final int[] cpt = new int[1];
					cpt[0] = 0;
					viewer.getTable().getDisplay().asyncExec(new Runnable() {

						public void run() {
							bar.setMaximum(lfinal.size());
						}
					});
					
					for(TaArticle ti : l){
						final TaArticle tiers = ti;
						if (viewer.getTable().isDisposed()) {
							return;
						}

						viewer.getTable().getDisplay().asyncExec(new Runnable() {

							public void run() {
								SWTArticle SWTArticle = mapping(tiers,null);
								//listeObjet.add(SWTArticle);
								logger.info("TOTAL mapping time = "+_totalMapping);
								viewer.add(SWTArticle);
								cp.elements.add(SWTArticle);
								
								cpt[0] = cpt[0]+1;
								bar.setSelection(cpt[0]);
							
								if(cpt[0]==lfinal.size()) {
									((GridData) barContainer.getLayoutData()).exclude = true;
									Composite c = barContainer.getParent();
									barContainer.dispose();
									c.layout(true);
									
									if(lgrviewer!=null) {
										//activation du tri sur toute les colonnes
										//lgrviewer.tri(SWTArticle.class,"SWTPaTiersController", Const.C_FICHIER_LISTE_CHAMP_GRILLE);
										
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

	public SWTArticle remplirListeLazy(EntityManager em, int id) {
		System.err.println("remplirListeLazy " + id);
		TaArticleDAO dao =new TaArticleDAO(em);
		SWTArticle SWTArticle = null;
		Date dateDeb = new Date();
		Collection<TaArticle> l = new ArrayList<TaArticle>();
		if(listeEntity==null) {
			listeEntity = new LinkedList<TaArticle>();
		}
		//if(listeEntity==null) {	//recuperation des entites dans la base de donnees	
		TaArticle t = dao.findById(id);
		if(t!=null) {
			//l.add(t);
			//l = new ArrayList<TaArticle>();
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
			//for(TaArticle tiers : l){
			TaArticle article = t;
			SWTArticle = new SWTArticle();
			SWTArticle.setIdArticle(article.getIdArticle());
			SWTArticle.setCodeArticle(article.getCodeArticle());
			SWTArticle.setLibellelArticle(article.getLibellelArticle());
			SWTArticle.setLibellecArticle(article.getLibellecArticle());
			SWTArticle.setNumcptArticle(article.getNumcptArticle());
			SWTArticle.setDiversArticle(article.getNumcptArticle());
			SWTArticle.setCommentaireArticle(article.getCommentaireArticle());
			SWTArticle.setStockMinArticle(article.getStockMinArticle());
			SWTArticle.setLongueur(article.getLongueur());
			SWTArticle.setLargeur(article.getLargeur());
			SWTArticle.setHauteur(article.getHauteur());
			SWTArticle.setPoids(article.getPoids());
			SWTArticle.setActif(LibConversion.intToBoolean(article.getActif()));
			
			TaFamille taFamille = article.getTaFamille();
			if(taFamille!=null){
				SWTArticle.setIdFamille(taFamille.getIdFamille());
				SWTArticle.setCodeFamille(taFamille.getCodeFamille());
				SWTArticle.setLibcFamille(taFamille.getLibcFamille());
			}
			TaPrix taPrix = article.getTaPrix();
			if(taPrix!=null){
				SWTArticle.setPrixPrix(taPrix.getPrixPrix());	
				SWTArticle.setPrixttcPrix(taPrix.getPrixttcPrix());	
				TaUnite taUnite = taPrix.getTaUnite();
				if(taUnite!=null){
					SWTArticle.setCodeUnite(taUnite.getCodeUnite());	
				}
			}
			TaTva taTva = article.getTaTva();
			if(taTva!=null){
				SWTArticle.setIdTva(taTva.getIdTva());
				SWTArticle.setTauxTva(taTva.getTauxTva());
				SWTArticle.setCodeTva(taTva.getCodeTva());
				SWTArticle.setNumcptTva(taTva.getNumcptTva());
			}
			TaTTva taTTva = article.getTaTTva();
			if(taTTva!=null){
				SWTArticle.setIdTTva(taTTva.getIdTTva());
				SWTArticle.setCodeTTva(taTTva.getCodeTTva());
				SWTArticle.setLibTTva(taTTva.getLibTTva());
			}
			TaRapportUnite taRapportUnite = article.getTaRapportUnite();
			if(taRapportUnite!=null){
				SWTArticle.setRapport(taRapportUnite.getRapport());
				SWTArticle.setSens(LibConversion.intToBoolean(taRapportUnite.getSens()));
				SWTArticle.setNbDecimal(taRapportUnite.getNbDecimal());
				TaUnite taUnite2 = taRapportUnite.getTaUnite2();
				if(taUnite2!=null){
					SWTArticle.setCodeUnite2(taUnite2.getCodeUnite());
				}
			}

			TaRTaTitreTransport taRTaTitreTransport = article.getTaRTaTitreTransport();
			if(taRTaTitreTransport!=null){
				SWTArticle.setQteTitreTransport(taRTaTitreTransport.getQteTitreTransport());
				TaTitreTransport taTitreTransport = taRTaTitreTransport.getTaTitreTransport();
				if(taTitreTransport!=null){
					SWTArticle.setCodeTitreTransport(taTitreTransport.getCodeTitreTransport());
				}
			}

			listeObjet.add(SWTArticle);
			//	}
			//}
		}

		Date dateFin = new Date();
		logger.info("temp remplirListeLazy "+new Date(dateFin.getTime()-dateDeb.getTime()));
		return SWTArticle;
	}

	//@Override
	//remettre le extends AbstractConcurrentModel dans la déclaration de la classe
	public void requestUpdate(final IConcurrentModelListener listener) {
		Thread t = new Thread() {

			@Override
			public void run() {

				TaArticleDAO dao =new TaArticleDAO();
				SWTArticle SWTArticle = null;
				Date dateDeb = new Date();
				Collection<TaArticle> l = null;
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
					for(TaArticle article : l){
						SWTArticle = new SWTArticle();
						SWTArticle.setIdArticle(article.getIdArticle());
						SWTArticle.setCodeArticle(article.getCodeArticle());
						SWTArticle.setLibellelArticle(article.getLibellelArticle());
						SWTArticle.setLibellecArticle(article.getLibellecArticle());
						SWTArticle.setNumcptArticle(article.getNumcptArticle());
						SWTArticle.setDiversArticle(article.getNumcptArticle());
						SWTArticle.setCommentaireArticle(article.getCommentaireArticle());
						SWTArticle.setStockMinArticle(article.getStockMinArticle());
						SWTArticle.setLongueur(article.getLongueur());
						SWTArticle.setLargeur(article.getLargeur());
						SWTArticle.setHauteur(article.getHauteur());
						SWTArticle.setPoids(article.getPoids());
						SWTArticle.setActif(LibConversion.intToBoolean(article.getActif()));
						
						TaFamille taFamille = article.getTaFamille();
						if(taFamille!=null){
							SWTArticle.setIdFamille(taFamille.getIdFamille());
							SWTArticle.setCodeFamille(taFamille.getCodeFamille());
							SWTArticle.setLibcFamille(taFamille.getLibcFamille());
						}
						TaPrix taPrix = article.getTaPrix();
						if(taPrix!=null){
							SWTArticle.setPrixPrix(taPrix.getPrixPrix());	
							SWTArticle.setPrixttcPrix(taPrix.getPrixttcPrix());	
							TaUnite taUnite = taPrix.getTaUnite();
							if(taUnite!=null){
								SWTArticle.setCodeUnite(taUnite.getCodeUnite());	
							}
						}
						TaTva taTva = article.getTaTva();
						if(taTva!=null){
							SWTArticle.setIdTva(taTva.getIdTva());
							SWTArticle.setTauxTva(taTva.getTauxTva());
							SWTArticle.setCodeTva(taTva.getCodeTva());
							SWTArticle.setNumcptTva(taTva.getNumcptTva());
						}
						TaTTva taTTva = article.getTaTTva();
						if(taTTva!=null){
							SWTArticle.setIdTTva(taTTva.getIdTTva());
							SWTArticle.setCodeTTva(taTTva.getCodeTTva());
							SWTArticle.setLibTTva(taTTva.getLibTTva());
						}
						TaRapportUnite taRapportUnite = article.getTaRapportUnite();
						if(taRapportUnite!=null){
							SWTArticle.setRapport(taRapportUnite.getRapport());
							SWTArticle.setSens(LibConversion.intToBoolean(taRapportUnite.getSens()));
							SWTArticle.setNbDecimal(taRapportUnite.getNbDecimal());
							TaUnite taUnite2 = taRapportUnite.getTaUnite2();
							if(taUnite2!=null){
								SWTArticle.setCodeUnite2(taUnite2.getCodeUnite());
							}
						}

						TaRTaTitreTransport taRTaTitreTransport = article.getTaRTaTitreTransport();
						if(taRTaTitreTransport!=null){
							SWTArticle.setQteTitreTransport(taRTaTitreTransport.getQteTitreTransport());
							TaTitreTransport taTitreTransport = taRTaTitreTransport.getTaTitreTransport();
							if(taTitreTransport!=null){
								SWTArticle.setCodeTitreTransport(taTitreTransport.getCodeTitreTransport());
							}
						}

						listeObjet.add(SWTArticle);
						listener.add(new Object[]{SWTArticle});
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
	public LinkedList<SWTArticle> remplirListe(EntityManager em) {
		TaArticleDAO dao =new TaArticleDAO(em);
		SWTArticle SWTArticle = null;
		Date dateDeb = new Date();
		Collection<TaArticle> l = null;
		Collection<Object[]> lObjs = null;
		
		//JPQLQueryLight = dao.getDefaultLightJPQLQueryEcran();
		
		if(listeEntity==null) {	//recuperation des entites dans la base de donnees		
			if(JPQLQueryLight!=null) {
				Query ejbQuery = em.createNamedQuery(JPQLQueryLight);
				lObjs = ejbQuery.getResultList();
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
		if(lObjs != null) {
			for(Object[] article : lObjs){
//				SWTArticle = new SWTArticle();
				SWTArticle = getSWTArticleLight(article);
				listeObjet.add(SWTArticle);
			}
		}else
		if(l != null) {
			for(TaArticle article : l){
				SWTArticle = new SWTArticle();
				SWTArticle.setIdArticle(article.getIdArticle());
				SWTArticle.setCodeArticle(article.getCodeArticle());
				SWTArticle.setLibellelArticle(article.getLibellelArticle());
				SWTArticle.setLibellecArticle(article.getLibellecArticle());
				SWTArticle.setNumcptArticle(article.getNumcptArticle());
				SWTArticle.setDiversArticle(article.getNumcptArticle());
				SWTArticle.setCommentaireArticle(article.getCommentaireArticle());
				SWTArticle.setStockMinArticle(article.getStockMinArticle());
				SWTArticle.setLongueur(article.getLongueur());
				SWTArticle.setLargeur(article.getLargeur());
				SWTArticle.setHauteur(article.getHauteur());
				SWTArticle.setPoids(article.getPoids());
				SWTArticle.setActif(LibConversion.intToBoolean(article.getActif()));
				
				TaFamille taFamille = article.getTaFamille();
				if(taFamille!=null){
					SWTArticle.setIdFamille(taFamille.getIdFamille());
					SWTArticle.setCodeFamille(taFamille.getCodeFamille());
					SWTArticle.setLibcFamille(taFamille.getLibcFamille());
				}
				TaPrix taPrix = article.getTaPrix();
				if(taPrix!=null){
					SWTArticle.setPrixPrix(taPrix.getPrixPrix());	
					SWTArticle.setPrixttcPrix(taPrix.getPrixttcPrix());	
					TaUnite taUnite = taPrix.getTaUnite();
					if(taUnite!=null){
						SWTArticle.setCodeUnite(taUnite.getCodeUnite());	
					}
				}
				TaTva taTva = article.getTaTva();
				if(taTva!=null){
					SWTArticle.setIdTva(taTva.getIdTva());
					SWTArticle.setTauxTva(taTva.getTauxTva());
					SWTArticle.setCodeTva(taTva.getCodeTva());
					SWTArticle.setNumcptTva(taTva.getNumcptTva());
				}
				TaTTva taTTva = article.getTaTTva();
				if(taTTva!=null){
					SWTArticle.setIdTTva(taTTva.getIdTTva());
					SWTArticle.setCodeTTva(taTTva.getCodeTTva());
					SWTArticle.setLibTTva(taTTva.getLibTTva());
				}
				TaRapportUnite taRapportUnite = article.getTaRapportUnite();
				if(taRapportUnite!=null){
					SWTArticle.setRapport(taRapportUnite.getRapport());
					SWTArticle.setSens(LibConversion.intToBoolean(taRapportUnite.getSens()));
					SWTArticle.setNbDecimal(taRapportUnite.getNbDecimal());
					TaUnite taUnite2 = taRapportUnite.getTaUnite2();
					if(taUnite2!=null){
						SWTArticle.setCodeUnite2(taUnite2.getCodeUnite());
					}
				}

				TaRTaTitreTransport taRTaTitreTransport = article.getTaRTaTitreTransport();
				if(taRTaTitreTransport!=null){
					SWTArticle.setQteTitreTransport(taRTaTitreTransport.getQteTitreTransport());
					TaTitreTransport taTitreTransport = taRTaTitreTransport.getTaTitreTransport();
					if(taTitreTransport!=null){
						SWTArticle.setCodeTitreTransport(taTitreTransport.getCodeTitreTransport());
					}
				}

				listeObjet.add(SWTArticle);
			}
		}

		Date dateFin = new Date();
		logger.info("temp remplirListe "+new Date(dateFin.getTime()-dateDeb.getTime()));
		return listeObjet;
	}



	public SWTArticle recherche(String propertyName, Object value, boolean startWith) {
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
	
	public SWTArticle recherche(String propertyName, Object value) {
		return recherche(propertyName,value,false);
	}
	
	public LinkedList<SWTArticle> getListeObjet() {
		return listeObjet;
	}

	public Collection<TaArticle> getListeEntity() {
		return listeEntity;
	}

	public void setListeEntity(Collection<TaArticle> listeEntity) {
		this.listeEntity = listeEntity;
	}

	public void setListeObjet(LinkedList<SWTArticle> listeObjet) {
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
	//		return remplirListe(new TaArticleDAO().getEntityManager());
	//	}

	//	@Override
	//	public List remplirListeElement(Object entite,String propertyName, Object value) {
	//		try {
	//			logger.debug("ModelGeneralObjet.remplirListeElement()");
	//
	//			Collection<TaArticle> l = null;
	//
	//			//			if(listeEntity==null) {	//recuperation des entites dans la base de donnees		
	//			if(JPQLQuery!=null) {
	//				CustomFieldMapperIFLGR customFieldMapperIFLGR = new CustomFieldMapperIFLGR(propertyName);
	//				if(lgrMapper!=null)
	//					lgrMapper.entityToDto((TaArticle)entite);
	//				else {
	//					
	//					mapperModelToUI.getMapper().setCustomFieldMapper(customFieldMapperIFLGR);
	//					mapperModelToUI.map((TaArticle)entite, SWTArticle.class);
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
	//				for (TaArticle o : l) {
	//					SWTArticle t = null;
	//					if(lgrMapper!=null)
	//						t = lgrMapper.entityToDto(o);
	//					else
	//						t = (SWTArticle) mapperModelToUI.map(o, typeObjet);
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
		if(mapperModelToUI==null)mapperModelToUI=new LgrDozerMapper<TaArticle, SWTArticle>();
		mapperModelToUI.getMapper().setCustomFieldMapper(custom);
	}

	public LgrDozerMapper<TaArticle, SWTArticle> getMapperModelToUI() {
		return mapperModelToUI;
	}

	public void setMapperModelToUI(LgrDozerMapper<TaArticle, SWTArticle> mapperModelToUI) {
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
		dao = new TaArticleDAO();
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
	 * getSWTArticleLightList
	 * 
	 * Recupere une liste d'objets SWTArticle avec les attributs minimums
	 * 
	 * @param TaArticleCollection : collection de tableau de resultats de requete HQL
	 * @return SWTArticle
	 */
	public List<SWTArticle> getSWTArticleLightList(
			Collection<Object[]> TaArticleCollection) {

		List<SWTArticle> SWTArticleList = new ArrayList<SWTArticle>();

		for (Object[] objects : TaArticleCollection) {
			SWTArticleList.add(getSWTArticleLight(objects));
		}

		return SWTArticleList;
	}
	
	/**
	 * getSWTArticleLight
	 * 
	 * Recupere un objet SWTArticle en fonction d'un tableau de resultats HQL
	 * 
	 * @param objs : tableau de resultats correspondant aux attributs minimums
	 * @return SWTArticle
	 */
	public SWTArticle getSWTArticleLight(Object[] objs){
		SWTArticle SWTArticle = new SWTArticle();
		
		SWTArticle.setCodeArticle(objs[0]!=null?(String)objs[0]:null);
		
		String libelle =objs[1]!=null?(String)objs[1]:null;
		String numcpt =objs[2]!=null?(String)objs[2]:null;
		BigDecimal prixHT =objs[3]!=null?(BigDecimal)objs[3]:null;
		
		String codeUnite =(objs[4]!=null?(String)objs[4]:null);
		String codeFamille =(objs[5]!=null?(String)objs[5]:null);
		String codeTva =(objs[6]!=null?(String)objs[6]:null);
		BigDecimal tauxTva =(objs[7]!=null?(BigDecimal)objs[7]:null);
		String codeTTva =(objs[8]!=null?(String)objs[8]:null);
		
		if(libelle!=null)SWTArticle.setLibellecArticle(libelle);
		else SWTArticle.setLibellecArticle("");
		
		if(numcpt!=null)SWTArticle.setNumcptArticle(numcpt);
		else SWTArticle.setNumcptArticle("");
		
		if(prixHT!=null)SWTArticle.setPrixPrix(prixHT);
		else SWTArticle.setPrixPrix(BigDecimal.ZERO);
		
		if(codeUnite!=null)SWTArticle.setCodeUnite(codeUnite);
		else SWTArticle.setCodeUnite("");
		
		if(codeFamille!=null)SWTArticle.setCodeFamille(codeFamille);
		else SWTArticle.setCodeFamille("");
		
		if(codeTva!=null)SWTArticle.setCodeTva(codeTva);
		else SWTArticle.setCodeTva("");
		
		if(codeTTva!=null)SWTArticle.setCodeTTva(codeTTva);
		else SWTArticle.setCodeTTva("");

		
		return SWTArticle;
	}
	
}
