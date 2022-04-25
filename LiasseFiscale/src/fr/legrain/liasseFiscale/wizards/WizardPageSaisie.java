package fr.legrain.liasseFiscale.wizards;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;

import org.apache.log4j.Logger;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.events.FocusAdapter;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Text;

import fr.legrain.liasseFiscale.actions.Cle;
import fr.legrain.liasseFiscale.actions.InfoComplement;

public class WizardPageSaisie extends WizardPage implements ILgrWizardPage {
	
	static Logger logger = Logger.getLogger(WizardPageSaisie.class.getName());
	
	static final public String PAGE_NAME = "WizardPageSaisie";
	static final private String PAGE_TITLE = "";
	private Control control;
//	private Listener listenerValidate = new Listener() {
//		public void handleEvent(Event e) {
//			setPageComplete(validatePage());
//		}
//	};
	private String fichierPDF;
	private InputStream streamPDF;
	private InputStream fichierImage;
	private int pagePDF;
	private int hauteurPage;
	private int largeurPage;
	private HashMap<String,Text> listeChamp = new HashMap<String,Text>();
	private WizardPageSaisie instance;
	
	public WizardPageSaisie(String pageName) {
		super(pageName);
		setTitle(PAGE_TITLE);
		instance = this;
	}
	
	public WizardPageSaisie(String pageName, String fichierPDF, String fichierImage, int pagePDF, int hauteurPage, int largeurPage) throws FileNotFoundException {
		this(pageName,new FileInputStream(fichierPDF),fichierPDF,new FileInputStream(fichierImage),pagePDF,hauteurPage,largeurPage);
	
	}
	
	public WizardPageSaisie(String pageName, InputStream streamPDF, String fichierPDF, InputStream fichierImage, int pagePDF, int hauteurPage, int largeurPage) {
		super(pageName);
		setTitle(PAGE_TITLE);
		this.fichierImage = fichierImage;
		this.streamPDF = streamPDF;
		this.fichierPDF = fichierPDF;
		this.pagePDF = pagePDF;
		this.hauteurPage = hauteurPage;
		this.largeurPage = largeurPage;		
	}
	
	public void createControl(Composite parent) {
		try {
			logger.debug("page : "+pagePDF+" WizardPageSaisie.createControl()");
			UtilImage img = new UtilImage();
			double scaleText = 1.39d;
			//Canvas control = img.imageBackground(parent,parent.getShell().getDisplay(),fichierImage,hauteurPage,largeurPage);
			ScrolledComposite control = img.imageBackgroundSC(parent,parent.getShell().getDisplay(),fichierImage,hauteurPage,largeurPage);

			control.layout();
			
			HashMap<String,ArrayList> listeInfosChampPDF = img.listeChamps(streamPDF,fichierPDF,pagePDF);
			ArrayList coord;
			Double llx;
			Double lly;
			Double urx;
			Double ury;
			Text t;
			
			if(listeInfosChampPDF!=null && !listeInfosChampPDF.isEmpty()) {
				for (final String nom : listeInfosChampPDF.keySet()) {
					coord = listeInfosChampPDF.get(nom);
					//Text t = new Text(control,SWT.CENTER);
					t = new Text((Composite)control.getContent(),SWT.CENTER 
						//	| SWT.NO_BACKGROUND
							);
					llx = new Double(Double.parseDouble(coord.toArray()[0].toString()));
					lly = new Double(Double.parseDouble(coord.toArray()[1].toString()));
					urx = new Double(Double.parseDouble(coord.toArray()[2].toString()));
					ury = new Double(Double.parseDouble(coord.toArray()[3].toString()));
					t.setBounds(//x,y,w,h
							(int)Math.round(llx*scaleText),
							(int)Math.round((hauteurPage-ury*scaleText)),
							(int)Math.round((urx-llx)*scaleText),
							(int)Math.round((ury-lly)*scaleText-scaleText)-1
					);
					//t.setText(nom);
					listeChamp.put(nom,t);
//					t.addFocusListener(new FocusAdapter() {
//						@Override
//						public void focusLost(FocusEvent e) {
//							super.focusLost(e);
//							enregistreSaisie();
//							((WizardLiasseFiscale)getWizard()).calculTotauxAvecSaisieManuelle(instance);
//							//enregistreSaisie();
//						}
//					});
				}
			}
			control.layout();
			
			this.control = control;
			setControl(control);
			setPageComplete(validatePage());
		} catch(Exception e) {
			logger.error("",e);
		}
	}
	
	public Control getControl() {
		return control;
	}
	
	public boolean validatePage() {
		//saveToModel();
		setErrorMessage(null);
		setMessage(null);
		return true;
	}
	
	public void finishPage() {
		if (isPageComplete()) {
			saveToModel();
		}
	}

	public void saveToModel() {
		try {
			enregistreSaisie();

			((WizardLiasseFiscale)getWizard()).calculTotauxAvecSaisieManuelle(this);
			enregistreSaisie();
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void setVisible(boolean visible) {
		if(((WizardLiasseFiscale)getWizard()).getModel().estVerrouille()) {
			for (String nomChamp : listeChamp.keySet()) {
				listeChamp.get(nomChamp).setEditable(false);
			}
		}
		super.setVisible(visible);
	}

	protected void enregistreSaisie() {
		for (String nomChamp : listeChamp.keySet()) {
//			if(listeChamp.get(nomChamp).isEnabled() ) {
			if(listeChamp.get(nomChamp).getEditable() ) {
		//	if(listeChamp.get(nomChamp).getEditable()) {		
		//	if(	((WizardLiasseFiscale)getWizard()).getModel().getRepartition().getListeRepartition().keySet().contains(new Cle(nomChamp)) ) {		
				if(!listeChamp.get(nomChamp).getText().equals("")) { 
					//le champ n'etait pas prerempli et une valeur a ete saisie
					if(((WizardLiasseFiscale)getWizard()).getModel().getInfosCompta()!=null) {
						//on ajoute la valeur saisie comme complement dans l'infoscompta
						((WizardLiasseFiscale)getWizard()).getModel().getInfosCompta().
//						getListeSaisieComplement().add(
//						new InfoComplement(nomChamp,listeChamp.get(nomChamp).getText(),listeChamp.get(nomChamp).getText()));
						getListeSaisieComplement().put(new Cle(nomChamp),
								new InfoComplement(nomChamp,listeChamp.get(nomChamp).getText(),listeChamp.get(nomChamp).getText()));
					}
//					((WizardLiasseFiscale)getWizard()).updateChampSaisie(nomChamp, listeChamp.get(nomChamp).getText());
				} else {
					if(((WizardLiasseFiscale)getWizard()).getModel().getInfosCompta()!=null) {
						//on ajoute la valeur saisie comme complement dans l'infoscompta
						((WizardLiasseFiscale)getWizard()).getModel().getInfosCompta().
						getListeSaisieComplement().remove(nomChamp);
								
					}
				}
				((WizardLiasseFiscale)getWizard()).updateChampSaisie(nomChamp, listeChamp.get(nomChamp).getText());
			}
		}
	}


	public HashMap<String, Text> getListeChamp() {
		return listeChamp;
	}
	
}
