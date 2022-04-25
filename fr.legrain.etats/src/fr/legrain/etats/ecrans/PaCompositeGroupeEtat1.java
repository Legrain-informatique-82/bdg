/**
 * PaSectionParam.java
 */
package fr.legrain.etats.ecrans;

import java.util.LinkedList;

import org.eclipse.jface.action.ToolBarManager;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.ui.forms.events.IHyperlinkListener;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.ImageHyperlink;

import fr.legrain.etats.Activator;
import fr.legrain.etats.controllers.MenuEtat;
import fr.legrain.etats.defaut.EtatArticle;
import fr.legrain.etats.defaut.EtatFamilleArticle;
import fr.legrain.etats.defaut.EtatFamilleTiers;
import fr.legrain.etats.defaut.EtatTiers;


public class PaCompositeGroupeEtat1 {
	// icons
	public static String iconPath = "/icons/chart_bar.png";
	public static String iconRefreshPath = "/icons/arrow_refresh_small.png";
	
	// Composite
	private Composite compo = null;
	private PaCompositeListeEtat1 compoListe = null;

	// Elements graphiques
	private Label label = null;
	private ToolBarManager sectionToolbar = null;
	private Button btnRefesh = null;
	private ImageHyperlink ihlRefresh = null;
	
	private LinkedList<MenuEtat> listeMenu = new LinkedList<MenuEtat>();

	public PaCompositeGroupeEtat1(Composite compo,FormToolkit toolkit) {
		this.compo = compo;

//		label = toolkit.createLabel(compo, "Etat"); 
//		label.setLayoutData(new GridData(SWT.FILL,SWT.NONE,true,false,4,1));
		
		MenuEtat m1 = new MenuEtat();
		m1.label = "Articles";
		m1.imagePath = "/icons/boutons/bouton_120px/bouton_articles_120px.png";
		m1.imageHover = "/icons/boutons/bouton_120px/hover_bouton_articles.png";
		m1.listeEtat.add(new EtatArticle(false));
		m1.listeEtat.add(new EtatFamilleArticle(false));
		m1.listeEtat.add(new EtatArticle(true));
		m1.listeEtat.add(new EtatFamilleArticle(true));		
//		m1.listeEtat.add(new EtatTest() {
//			@Override
//			public String getName() {
//				// TODO Auto-generated method stub
//				return "Edition 1";
//			}
//		});
		
		MenuEtat m2 = new MenuEtat();
		m2.label = "Tiers";
		m2.imagePath = "/icons/boutons/bouton_120px/bouton_clients_120px.png";
		m2.imageHover = "/icons/boutons/bouton_120px/hover_bouton_clients_120px.png";
		m2.listeEtat.add(new EtatTiers());
		m2.listeEtat.add(new EtatFamilleTiers());
//		m2.listeEtat.add(new EtatTest() {
//			@Override
//			public String getName() {
//				// TODO Auto-generated method stub
//				return "Edition 2.1";
//			}
//		});
//		m2.listeEtat.add(new EtatTest() {
//			@Override
//			public String getName() {
//				// TODO Auto-generated method stub
//				return "Edition 2.2";
//			}
//		});
		
//		MenuEtat m3 = new MenuEtat();
//		m3.imagePath = "/icons/boutons/menu3.png";
//		m3.imageOver = "/icons/boutons/menu3V.png";
//		m3.listeEtat.add(new EtatTest() {
//			@Override
//			public String getName() {
//				// TODO Auto-generated method stub
//				return "Edition 3.1";
//			}
//		});
//		m3.listeEtat.add(new EtatTest() {
//			@Override
//			public String getName() {
//				// TODO Auto-generated method stub
//				return "Edition 3.2";
//			}
//		});
//		m3.listeEtat.add(new EtatTest() {
//			@Override
//			public String getName() {
//				// TODO Auto-generated method stub
//				return "Edition 3.3";
//			}
//		});
		
		listeMenu.add(m1);
		listeMenu.add(m2);
//		listeMenu.add(m3);
		
		ImageHyperlink imageHyperlink = null;
		for (final MenuEtat menu : listeMenu) {
//			imageHyperlink = toolkit.createImageHyperlink(compo, SWT.NULL);
			imageHyperlink = toolkit.createImageHyperlink(compo, SWT.BOTTOM);
			imageHyperlink.setImage(Activator.getImageDescriptor(menu.imagePath).createImage());
			imageHyperlink.setHoverImage(Activator.getImageDescriptor(menu.imageHover).createImage());
//			//imageHyperlink.setActiveImage(Activator.getImageDescriptor(PaCompositeGroupeEtat1.iconRefreshPath).createImage());
//			imageHyperlink.setText(menu.label);
			imageHyperlink.setToolTipText(menu.label);	
			
			imageHyperlink.addHyperlinkListener(new IHyperlinkListener() {
				@Override
				public void linkExited(org.eclipse.ui.forms.events.HyperlinkEvent e) {}
				
				@Override
				public void linkEntered(org.eclipse.ui.forms.events.HyperlinkEvent e) {}
				
				@Override
				public void linkActivated(org.eclipse.ui.forms.events.HyperlinkEvent e) {
					compoListe.setEtats(menu.listeEtat);
					compoListe.createEtatButton();
					compoListe.selectionEtat(menu.listeEtat.getFirst());
				}
			});
		}

//		ImageHyperlink h1 = toolkit.createImageHyperlink(compo, SWT.NULL);
//		h1.setText("test 2");

		GridLayout layout = new GridLayout();
		layout.marginWidth = layout.marginHeight = 0;
		layout.numColumns = 1;
		compo.setLayout(layout);
		
		sectionToolbar = new ToolBarManager(SWT.FLAT);
	}
	
	

	public Composite getCompo() {
		return compo;
	}

	public Label getLabel() {
		return label;
	}
	
	public Button getBtnRefesh() {
		return btnRefesh;
	}
	
	public ImageHyperlink getIhlRefresh() {
		return ihlRefresh;
	}
	
	public ToolBarManager getSectionToolbar() {
		return sectionToolbar;
	}



	public void setCompoListe(PaCompositeListeEtat1 compoListe) {
		this.compoListe = compoListe;
	}
}
