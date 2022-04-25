package fr.legrain.article.export.catalogue;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.xml.bind.JAXBException;

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

import fr.legrain.articles.dao.TaTvaDAO;
import fr.legrain.boutique.dao.TaCorrespondanceIDBoutiqueDAO;
import fr.legrain.gestCom.Appli.ILgrListModel;
import fr.legrain.gestCom.Appli.IlgrMapper;
import fr.legrain.gestCom.Appli.LgrDozerMapper;
import fr.legrain.lib.data.AbstractLgrDAO;
import fr.legrain.lib.data.IModelGeneral;
import fr.legrain.lib.data.ModelObject;
import fr.legrain.lib.gui.grille.LgrCompositeTableViewer;
import fr.legrain.lib.gui.grille.LgrTableViewer;
import fr.legrain.prestashop.ws.WSPrestashop;
import fr.legrain.prestashop.ws.entities.Taxes;

public class ModelTaxPrestashop implements IModelGeneral, ILgrListModel<Taxes>  {
	static Logger logger = Logger.getLogger(ModelTaxPrestashop.class.getName());
	LinkedList<SWTTaxeBoutique> listeObjet = new LinkedList<SWTTaxeBoutique>();
	private Collection<Taxes> listeEntity = null;
	private String JPQLQuery = null;
	private String JPQLQuerySansOrder = null;


	private Class<SWTTaxeBoutique> typeObjet;
	private LgrDozerMapper<Taxes,SWTTaxeBoutique> mapperModelToUI = null;
	private IlgrMapper<SWTTaxeBoutique, Taxes> lgrMapper = null;
	private AbstractLgrDAO<Taxes> dao;

	private EntityManager entityManager = null;
			
	private WSPrestashop ws = null;


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
//				if (element instanceof SWTTaxeBoutique) {
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
			if (element instanceof SWTTaxeBoutique) {
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
		public WritableList elements = new WritableList(listeObjet, SWTTaxeBoutique.class);

		public void dispose() {
		}

		public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
			//this.elements = (WritableList) newInput;
		}

		public void updateElement(int index) {
			System.err.println("updateElement " + index);
//			try {
//				if(dao==null) 
//					//dao = new TaCorrespondanceIDBoutiqueDAO();
//				SWTTaxeBoutique t = remplirListeLazy(dao.getEntityManager(),index);
//				//elements.add(t);
//				//				Object o = viewer.getElementAt(index);
//				//				if(o==null) {
//				//					System.err.println("Pas encore d'élément : " + index);
//				//				} else {
//				//					if(o instanceof SWTTaxeBoutique) {
//				//						System.err.println("Tiers : "+((SWTTaxeBoutique)o).getCodeTiers()+ " : " + index);
//				//					}
//				//				}
//				if(t==null) {
//					t = new SWTTaxeBoutique();
//					//t.setCodeTiers("Pas de tiers avec l'id "+index);
//				}
//				viewer.replace(t, index);
//			} catch(Exception e) {
//				logger.error("",e);
//			}
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
	
	public SWTTaxeBoutique mapping(Taxes tiers, SWTTaxeBoutique SWTTaxeBoutique) {
		return mapping(tiers, SWTTaxeBoutique, false);
	}
	
	public SWTTaxeBoutique mapping(Taxes tiers, SWTTaxeBoutique swtTaxeBoutique, boolean complet) {
		if(swtTaxeBoutique==null)
			swtTaxeBoutique = new SWTTaxeBoutique();
		
		swtTaxeBoutique.setId(tiers.getId());
		swtTaxeBoutique.setName(tiers.getName().get(0).getValue());
		swtTaxeBoutique.setRate(tiers.getRate());
		swtTaxeBoutique.setDeleted(tiers.getDeleted());
		swtTaxeBoutique.setActive(tiers.getActive());
		
		return swtTaxeBoutique;
	}
	
	public void addToModel(final TableViewer viewer, Taxes tiers) {
		SWTTaxeBoutique SWTTaxeBoutique = mapping(tiers,null);
		//listeObjet.add(SWTTaxeBoutique);
		listeEntity.add(tiers);
		
		System.err.println("ID correspondace ajouter : "+SWTTaxeBoutique.getId());

		//ViewerComparator vc = viewer.getComparator();
		//viewer.setComparator(null);
		
		viewer.add(SWTTaxeBoutique);
		cp.elements.add(SWTTaxeBoutique);
		
		//viewer.setComparator(vc);
		viewer.setSelection(new StructuredSelection(SWTTaxeBoutique),true);
	}
	
	public void refreshModel(final TableViewer viewer, Taxes tiers) {
		SWTTaxeBoutique SWTTaxeBoutique = recherche("id"/*Const.C_ID_TIERS*/, tiers.getId());
		
		SWTTaxeBoutique = mapping(tiers,SWTTaxeBoutique);
		viewer.update(SWTTaxeBoutique, null);

		viewer.setSelection(new StructuredSelection(SWTTaxeBoutique),true);
	}
	
	public void removeFromModel(final TableViewer viewer, Taxes tiers) {
		SWTTaxeBoutique SWTTaxeBoutique = recherche("id"/*Const.C_ID_TIERS*/, tiers.getId());
		//listeObjet.remove(SWTTaxeBoutique);
		listeEntity.remove(tiers);

		Object suivant=viewer.getElementAt(viewer.getTable().getSelectionIndex()+1);
		Object precedent=viewer.getElementAt(viewer.getTable().getSelectionIndex()-1);
		
		viewer.remove(SWTTaxeBoutique);
		
		cp.elements.remove(SWTTaxeBoutique);
		if(suivant!=null)
			viewer.setSelection(new StructuredSelection(suivant),true);
		else if(precedent!=null)
			viewer.setSelection(new StructuredSelection(precedent),true);
		else
			viewer.setSelection(new StructuredSelection(cp.elements.get(0)),true);
	}

	public void initCPThread(final TableViewer viewer, final ViewPart v,final ProgressBar bar, final Composite barContainer, final LgrCompositeTableViewer lgrviewer) {
		
//		if(lgrviewer!=null)
//			lgrviewer.tri(SWTTaxeBoutique.class,"SWTPaTiersController", Const.C_FICHIER_LISTE_CHAMP_GRILLE);
		
		Thread t = new Thread() {
			public void run() {
		
//				viewer.getTable().getDisplay().asyncExec(new Runnable() {
//					public void run() {
//					if(v!=null) v.showBusy(true);
//					}
//				});
				
				TaCorrespondanceIDBoutiqueDAO dao =new TaCorrespondanceIDBoutiqueDAO();
				//JPQLQuery = dao.getDefaultJPQLQueryOrderByCodeTiers(); //pas de tri possible avec la table virtuelle donc tri en SQL
				//JPQLQuerySansOrder = dao.getDefaultJPQLQuery(); //pas de tri possible avec la table virtuelle donc tri en SQL
				Collection<Taxes> l = null;
				if(listeEntity==null) {	//recuperation des entites dans la base de donnees		
					if(JPQLQuery!=null) {
//						if(typeCorrespondance.equals(TaCorrespondanceIDBoutiqueDAO.TYPE_TVA)) {
//							l = dao.findByTypeTable(TaCorrespondanceIDBoutiqueDAO.TYPE_TVA);
//						}
						try {
							l = ws.findAllTaxes();
						} catch (JAXBException e) {
							logger.error("",e);
						}
						
						//Query ejbQuery = dao.getEntityManager().createQuery(JPQLQuery);
						//l = ejbQuery.getResultList();
					} else if (dao != null) {
						JPQLQuery = dao.getJPQLQuery();
						//l = dao.selectTiersSurTypeTiers();
						
						//l = dao.selectAll();
						
						try {
							l = ws.findAllTaxes();
						} catch (JAXBException e) {
							logger.error("",e);
						}
					}
					listeEntity = l;
				} else {
					//l = new LinkedList<Entity>();
					l = listeEntity;
				}
				//listeObjet.clear();
				//cp.elements.clear();

				//		listeEntity=dao.selectAll();
				if(l != null) {
					final Collection<Taxes> lfinal = l;
					final int[] cpt = new int[1];
					cpt[0] = 0;
					viewer.getTable().getDisplay().asyncExec(new Runnable() {

						public void run() {
							bar.setMaximum(lfinal.size());
						}
					});
					
					
					for(Taxes ti : l){
						final Taxes tiers = ti;
						if (viewer.getTable().isDisposed()) {
							return;
						}

						viewer.getTable().getDisplay().asyncExec(new Runnable() {

							public void run() {
								SWTTaxeBoutique SWTTaxeBoutique = mapping(tiers,null);
								//listeObjet.add(SWTTaxeBoutique);

								viewer.add(SWTTaxeBoutique);
								cp.elements.add(SWTTaxeBoutique);
								
								cpt[0] = cpt[0]+1;
								bar.setSelection(cpt[0]);
							
								if(cpt[0]==lfinal.size()) {
									((GridData) barContainer.getLayoutData()).exclude = true;
									Composite c = barContainer.getParent();
									barContainer.dispose();
									c.layout(true);
									
									if(lgrviewer!=null) {
										//activation du tri sur toute les colonnes
										//lgrviewer.tri(SWTTaxeBoutique.class,"SWTPaTiersController", Const.C_FICHIER_LISTE_CHAMP_GRILLE);
										
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

	public SWTTaxeBoutique remplirListeLazy(EntityManager em, int id) {
		System.err.println("remplirListeLazy " + id);
		TaCorrespondanceIDBoutiqueDAO dao =new TaCorrespondanceIDBoutiqueDAO(em);
		SWTTaxeBoutique swtCorrespondanceIDBoutique = null;
		Date dateDeb = new Date();
		Collection<Taxes> l = new ArrayList<Taxes>();
		if(listeEntity==null) {
			listeEntity = new LinkedList<Taxes>();
		}
		//if(listeEntity==null) {	//recuperation des entites dans la base de donnees	
		Taxes t = null;
		try {
			t = ws.findTax(id);
		} catch (JAXBException e) {
			logger.error("",e);
		}
		if(t!=null) {
			//l.add(t);
			//l = new ArrayList<Taxes>();
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
			//for(Taxes tiers : l){
			Taxes tiers = t;
			swtCorrespondanceIDBoutique = new SWTTaxeBoutique();
			swtCorrespondanceIDBoutique = mapping(tiers,swtCorrespondanceIDBoutique);
			
			listeObjet.add(swtCorrespondanceIDBoutique);
			//	}
			//}
		}

		Date dateFin = new Date();
		logger.info("temp remplirListeLazy "+new Date(dateFin.getTime()-dateDeb.getTime()));
		return swtCorrespondanceIDBoutique;
	}

	//@Override
	//remettre le extends AbstractConcurrentModel dans la déclaration de la classe
	public void requestUpdate(final IConcurrentModelListener listener) {
		Thread t = new Thread() {

			@Override
			public void run() {

				TaCorrespondanceIDBoutiqueDAO dao =new TaCorrespondanceIDBoutiqueDAO();
				SWTTaxeBoutique swtCorrespondanceIDBoutique = null;
				Date dateDeb = new Date();
				Collection<Taxes> l = null;
				if(listeEntity==null) {	//recuperation des entites dans la base de donnees		
					if(JPQLQuery!=null) {
						Query ejbQuery = dao.getEntityManager().createQuery(JPQLQuery);
						l = ejbQuery.getResultList();
					} else if (dao != null) {
						//l = dao.selectAll();
						
						try {
							l = ws.findAllTaxes();
						} catch (JAXBException e) {
							logger.error("",e);
						}
					}
					listeEntity = l;
				} else {
					//l = new LinkedList<Entity>();
					l = listeEntity;
				}
				listeObjet.clear();

				if(l != null) {
					for(Taxes tiers : l){
						swtCorrespondanceIDBoutique = new SWTTaxeBoutique();
						swtCorrespondanceIDBoutique = mapping(tiers,swtCorrespondanceIDBoutique);
		
						listeObjet.add(swtCorrespondanceIDBoutique);
						listener.add(new Object[]{swtCorrespondanceIDBoutique});
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
	public LinkedList<SWTTaxeBoutique> remplirListe(EntityManager em) {
		TaCorrespondanceIDBoutiqueDAO dao =new TaCorrespondanceIDBoutiqueDAO(em);
		SWTTaxeBoutique swtCorrespondanceIDBoutique = null;
		Date dateDeb = new Date();
		Collection<Taxes> l = null;
		if(listeEntity==null) {	//recuperation des entites dans la base de donnees		
			//if(JPQLQuery!=null) {
				//Query ejbQuery = em.createQuery(JPQLQuery);
				//l = ejbQuery.getResultList();
			
				//if(typeCorrespondance.equals(TaCorrespondanceIDBoutiqueDAO.TYPE_TVA)) {
				//	l = dao.findByTypeTable(TaCorrespondanceIDBoutiqueDAO.TYPE_TVA);
				//}
				try {
					l = ws.findAllTaxes();
				} catch (JAXBException e) {
					logger.error("",e);
				}
			
			//} else if (dao != null) {
			//	l = dao.selectAll();
			//}
			listeEntity = l;
		} else {
			//l = new LinkedList<Entity>();
			l = listeEntity;
		}
		listeObjet.clear();

		//		listeEntity=dao.selectAll();
		if(l != null) {
			for(Taxes tiers : l){
				swtCorrespondanceIDBoutique = new SWTTaxeBoutique();
				swtCorrespondanceIDBoutique = mapping(tiers,swtCorrespondanceIDBoutique);
				
				listeObjet.add(swtCorrespondanceIDBoutique);
			}
		}

		Date dateFin = new Date();
		logger.info("temp remplirListe "+new Date(dateFin.getTime()-dateDeb.getTime()));
		return listeObjet;
	}



	public SWTTaxeBoutique recherche(String propertyName, Object value, boolean startWith) {
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
	}
	
	public SWTTaxeBoutique recherche(String propertyName, Object value) {
		return recherche(propertyName,value,false);
	}
	
	public LinkedList<SWTTaxeBoutique> getListeObjet() {
		return listeObjet;
	}

	public Collection<Taxes> getListeEntity() {
		return listeEntity;
	}

	public void setListeEntity(Collection<Taxes> listeEntity) {
		this.listeEntity = listeEntity;
	}

	public void setListeObjet(LinkedList<SWTTaxeBoutique> listeObjet) {
		this.listeObjet = listeObjet;
	}

	public String getJPQLQuery() {
		return JPQLQuery;
	}

	public void setJPQLQuery(String jPQLQuery) {
		JPQLQuery = jPQLQuery;
	}

	public LgrDozerMapper<Taxes, SWTTaxeBoutique> getMapperModelToUI() {
		return mapperModelToUI;
	}

	public void setMapperModelToUI(LgrDozerMapper<Taxes, SWTTaxeBoutique> mapperModelToUI) {
		this.mapperModelToUI = mapperModelToUI;
	}

	@Override
	public void razListEntity() {
		listeEntity.clear();
	}

	@Override
	public ModelObject rechercheDansListe(LinkedList<ModelObject> listeObjet,
			String propertyName, Object value) {
		return null;
	}

	@Override
	public List remplirListe() {
		//dao = new TaCorrespondanceIDBoutiqueDAO();
		//return remplirListe(dao.getEntityManager());
		return remplirListe(null);
	}

	@Override
	public List remplirListeElement(Object e, String propertyName, Object value) {
		return null;
	}

	public String getJPQLQuerySansOrder() {
		return JPQLQuerySansOrder;
	}

	public void setJPQLQuerySansOrder(String jPQLQuerySansOrder) {
		JPQLQuerySansOrder = jPQLQuerySansOrder;
	}

	public WSPrestashop getWs() {
		return ws;
	}

	public void setWs(WSPrestashop ws) {
		this.ws = ws;
	}
}
