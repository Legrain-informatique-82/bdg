package fr.legrain.articles.importation.controllers;

import org.apache.log4j.Logger;
import org.eclipse.jface.viewers.ColumnViewer;
import org.eclipse.jface.viewers.ColumnViewerToolTipSupport;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;

import fr.legrain.gestCom.librairiesEcran.LibrairiesEcranPlugin;
import fr.legrain.gestCom.librairiesEcran.workbench.AbstractLgrMultiPageEditor;
import fr.legrain.tiers.dao.TaTiers;
import fr.legrain.tiers.dao.TaTiersDAO;
import fr.legrain.tiers.editor.TiersMultiPageEditor;

public final class FancyToolTipSupportTiers extends ColumnViewerToolTipSupport {
	
	static Logger logger = Logger.getLogger(FancyToolTipSupportTiers.class.getName());	

	private TaTiersDAO dao = null;

	protected FancyToolTipSupportTiers(ColumnViewer viewer, int style,
			boolean manualActivation) {
		super(viewer, style, manualActivation);
	}

	protected Composite createToolTipContentArea(Event event,
			Composite parent) {

		if(dao==null)
			dao = new TaTiersDAO();

		final TaTiers tiers = dao.findByCode(getText(event));
		Composite comp = new Composite(parent,SWT.NONE);

		if(tiers!=null) {
			comp.setSize(500, 500);
			GridLayout l = new GridLayout(1,false);
			l.horizontalSpacing=0;
			l.marginWidth=0;
			l.marginHeight=0;
			l.verticalSpacing=0;

			comp.setLayout(l);
			Label label = null;
			String texte = null;
			GridData gd = null;

			Button b = new Button(comp,SWT.PUSH);
			//b.setText("Ouvrir fiche tiers");
			b.setToolTipText("Ouvrir fiche tiers");
			b.addSelectionListener(new SelectionListener() {

				@Override
				public void widgetSelected(SelectionEvent e) {
					try{
						if(tiers.getIdTiers()!=0){
							AbstractLgrMultiPageEditor.ouvreFiche(String.valueOf(tiers.getIdTiers()),null, TiersMultiPageEditor.ID_EDITOR,null,false);
						}
					}catch (Exception ex) {
						logger.error("",ex);
					}			
				}

				@Override
				public void widgetDefaultSelected(SelectionEvent e) {
					widgetSelected(e);	
				}
			});
			b.setImage(LibrairiesEcranPlugin.ir.get(LibrairiesEcranPlugin.IMAGE_IDENTITE_TIERS));
			gd = new GridData();
			gd.horizontalAlignment = SWT.END;
			b.setLayoutData(gd);

			texte = "";
			if(tiers.getTaTEntite()!=null && tiers.getTaTEntite().getCodeTEntite()!=null) {
				texte+=tiers.getTaTEntite().getCodeTEntite()+" ";
			}
			if(tiers.getTaEntreprise()!=null && tiers.getTaEntreprise().getNomEntreprise()!=null) {
				texte+=tiers.getTaEntreprise().getNomEntreprise();
			}
			label = new Label(comp,SWT.NONE); label.setText(texte);


			texte = "";
			if(tiers.getTaTCivilite()!=null && tiers.getTaTCivilite().getCodeTCivilite()!=null) {
				texte+=tiers.getTaTCivilite().getCodeTCivilite()+" ";

			}
			if(tiers.getNomTiers()!=null) {
				texte+=tiers.getNomTiers()+" ";
			}
			if(tiers.getPrenomTiers()!=null) {
				texte+=tiers.getPrenomTiers();

			}
			label = new Label(comp,SWT.NONE); label.setText(texte);
			
			new Label(comp, SWT.SEPARATOR | SWT.HORIZONTAL);
			if(tiers.getTaAdresse()!=null && tiers.getTaAdresse().getAdresse1Adresse()!=null && !tiers.getTaAdresse().getAdresse1Adresse().equals("")) {
				label = new Label(comp,SWT.NONE); label.setText(tiers.getTaAdresse().getAdresse1Adresse());
			}
			if(tiers.getTaAdresse()!=null && tiers.getTaAdresse().getAdresse2Adresse()!=null && !tiers.getTaAdresse().getAdresse2Adresse().equals("")) {
				label = new Label(comp,SWT.NONE); label.setText(tiers.getTaAdresse().getAdresse2Adresse());
			}
			if(tiers.getTaAdresse()!=null && tiers.getTaAdresse().getAdresse3Adresse()!=null && !tiers.getTaAdresse().getAdresse3Adresse().equals("")) {
				label = new Label(comp,SWT.NONE); label.setText(tiers.getTaAdresse().getAdresse3Adresse());
			}
			texte = "";
			if(tiers.getTaAdresse()!=null && tiers.getTaAdresse().getCodepostalAdresse()!=null) {
				texte += tiers.getTaAdresse().getCodepostalAdresse()+" " ;
			}
			if(tiers.getTaAdresse()!=null && tiers.getTaAdresse().getVilleAdresse()!=null) {
				texte += tiers.getTaAdresse().getVilleAdresse() ;
			}
			label = new Label(comp,SWT.NONE); label.setText(texte);
			if(tiers.getTaAdresse()!=null && tiers.getTaAdresse().getPaysAdresse()!=null && !tiers.getTaAdresse().getPaysAdresse().equals("")) {
				label = new Label(comp,SWT.NONE); label.setText(tiers.getTaAdresse().getPaysAdresse());
			}
			new Label(comp, SWT.SEPARATOR | SWT.HORIZONTAL);

			if(tiers.getTaTelephone()!=null && tiers.getTaTelephone().getNumeroTelephone()!=null) {
				label = new Label(comp,SWT.NONE); label.setText(tiers.getTaTelephone().getNumeroTelephone());
			}
			if(tiers.getTaEmail()!=null && tiers.getTaEmail().getAdresseEmail()!=null) {
				label = new Label(comp,SWT.NONE); label.setText(tiers.getTaEmail().getAdresseEmail());
			}
			if(tiers.getTaWeb()!=null && tiers.getTaWeb().getAdresseWeb()!=null) {
				label = new Label(comp,SWT.NONE); label.setText(tiers.getTaWeb().getAdresseWeb());
			}

			if(tiers.getTaCompl()!=null && tiers.getTaCompl().getTvaIComCompl()!=null) {
				label = new Label(comp,SWT.NONE); label.setText(tiers.getTaCompl().getTvaIComCompl());
			}

			if(tiers.getTaCompl()!=null && tiers.getTaCompl().getSiretCompl()!=null) {
				label = new Label(comp,SWT.NONE); label.setText(tiers.getTaCompl().getSiretCompl());
			}

			//Browser browser = new Browser(comp,SWT.BORDER);
			//browser.setText(tiers.getCodeTiers()+"<br>"+
			//			tiers.getNomTiers()+"<br>"+
			//			tiers.getPrenomTiers()+"<br>"
			//		);
			//browser.setLayoutData(new GridData(200,150));
		}

		return comp;
	}

	public boolean isHideOnMouseDown() {
		return false;
	}

	public static final void enableFor(ColumnViewer viewer, int style) {
		new FancyToolTipSupportTiers(viewer,style,false);
	}
}
