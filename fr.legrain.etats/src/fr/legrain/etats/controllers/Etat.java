package fr.legrain.etats.controllers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.DateTime;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.forms.widgets.FormText;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.Section;

import fr.legrain.gestCom.librairiesEcran.LibrairiesEcranPlugin;
import fr.legrain.gestCom.librairiesEcran.swt.LibDateTime;
import fr.legrain.lib.data.LibConversion;

public abstract class Etat {
	
	public static int TYPE_PARAM_DATE = 1;
	public static int TYPE_PARAM_TEXT = 2;
	public static int TYPE_PARAM_NUM  = 3;
	public static int TYPE_PARAM_BOOLEAN  = 4;
	public static int TYPE_PARAM_LIST  = 5;
	
	protected List<Param> param = null;
	protected List<Control> listeControlParam = null;
	
	protected ParamController paramController = null;
	
	private FormToolkit toolkit = null;
	
	/*
	 * Liste param final, map string clé valeur
	 * 
	 * méthode pour créer l'ihm des param, soit auto + manuel, soit manuel
	 */
	
	//public abstract LinkedList<Param> description();
	public abstract LinkedHashMap<String,LinkedList<Param>> description();
	
	public void initIHM(Composite c) {
		
	}
	
	public void initIHM(Composite c, FormToolkit toolkit) {
		this.toolkit = toolkit;
		
		Control[] controls = c.getChildren();
		for (int i = 0; i < controls.length; i++) {
			controls[i].dispose();
		}
		
		//LinkedList<Param> l = description();
		LinkedHashMap<String,LinkedList<Param>> l = description();
		List<Param> param = initCompositeParam(c, l);
		addIHMAction();
		c.layout();
	}
	
	public abstract void addIHMAction();
	
	/**
	 * methode (point d'entrée) pour différents calculs pour obtenir les données
	 * @param reportParam
	 * @return
	 */
	public abstract List<Object> calcul(/*HashMap<String,String> reportParam*/);
	
	/**
	 * méthode pour appeler l'édition BIRT
	 * @param valeur
	 * @param reportParam
	 */
	public abstract void edit(List<Object> valeur, HashMap<String,String> reportParam, Boolean extraction);
	
	//public List<Param> initCompositeParam(final Composite c, LinkedList<Param> l) {
	public List<Param> initCompositeParam(final Composite c, LinkedHashMap<String,LinkedList<Param>> l) {
		param = (ArrayList)initCompositeParamAuto(c, l);
		param.addAll(initCompositeParam(c));
	
		Button b = new Button(c, SWT.PUSH);
		b.setText("Imprimer");
		b.setImage(LibrairiesEcranPlugin.ir.get(LibrairiesEcranPlugin.IMAGE_IMPRIMER));
		
		b.addSelectionListener(new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent e) {
				
				HashMap<String,String> reportParam = getParamValue();
				
				//System.out.println("Edition : "+getName());
				//MessageDialog.openConfirm(c.getShell(), "Edition", "Edition : "+getName());
				List<Object> listeEdition =  calcul();				
				edit(listeEdition, reportParam,false);
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				widgetSelected(e);
			}
		});
		
		listeControlParam.add(b);
		
		Button E = new Button(c, SWT.PUSH);
		E.setText("Extraction");
		E.setImage(LibrairiesEcranPlugin.ir.get(LibrairiesEcranPlugin.IMAGE_EXPORT));
		
		E.addSelectionListener(new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent e) {
				
				HashMap<String,String> reportParam = getParamValue();
				
				//System.out.println("Edition : "+getName());
				//MessageDialog.openConfirm(c.getShell(), "Edition", "Edition : "+getName());
				List<Object> listeEdition =  calcul();				
				edit(listeEdition, reportParam,true);
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				widgetSelected(e);
			}
		});
		
		listeControlParam.add(E);		
		return param;
	}
	
	public HashMap<String,String> getParamValue() {
		HashMap<String,String> reportParam = new HashMap<String,String>();
		for (Param p : param) {
			if(p.type==Etat.TYPE_PARAM_TEXT) {
				p.value = ((Text)p.control.get(0)).getText();
				System.out.println(p.name+" : "+p.value);
			} else if(p.type==Etat.TYPE_PARAM_NUM) {
				p.value = ((Text)p.control.get(0)).getText();
				System.out.println(p.name+" : "+p.value);
			} else if(p.type==Etat.TYPE_PARAM_DATE) {
				p.value = LibDateTime.getDateText((DateTime)p.control.get(0));
				System.out.println(p.name+" : "+p.value);
			} else if(p.type==Etat.TYPE_PARAM_BOOLEAN) {
				p.value = LibConversion.booleanToString(((Button)p.control.get(0)).getSelection());
				System.out.println(p.name+" : "+p.value);
			} else if(p.type==Etat.TYPE_PARAM_LIST) {
				System.out.println(p.name+" : "+((Button)p.control.get(0)).getSelection());
				for(Control c : p.control){
					System.out.println(p.name+" - "+((Button)c).getText()+" "+((Button)c).getSelection());
					if(((Button)c).getSelection()) {
						p.value = p.findValueList(((Button)c).getText());
						//System.out.println(p.name+" : "+p.liste);
						System.out.println(p.name+" : "+p.value);
					}
				}
			}
			
			reportParam.put(p.name, p.value);
		}
		return reportParam;
	}
	
	public List<Param> initCompositeParam(Composite c) {
		return new ArrayList<Param>();
	}
	
	public abstract String getName();
	
	public abstract String getDescription();
	
	public abstract Image getIcon();
	
	public abstract String getEditionFilePath();
	
	public abstract String getNomOngletEdition();
	
	public abstract Map<String,String> getParamEditonURL();
	
	public abstract String getDescriptionParam();
	
	/**
	 * Liste param interface (possibilié de générer une partie de l'ihm)
	 * @param c
	 * @param l
	 * @return
	 */
	//public Map<Param,Control> initCompositeParamAuto(Composite c, LinkedList<Param> l) {
	//	public List<Param> initCompositeParamAuto(Composite c, LinkedList<Param> l) {
	public List<Param> initCompositeParamAuto(Composite parent, LinkedHashMap<String,LinkedList<Param>> l) {
		//HashMap<Param,Control> result = new HashMap<Param,Control>();*
		List<Param> result = new ArrayList<Param>();
		listeControlParam = new ArrayList<Control>();

		int nbCol = 4;

		GridLayout layoutParent = new GridLayout();
		layoutParent.makeColumnsEqualWidth = false;
		layoutParent.numColumns = 1;
		parent.setLayout(layoutParent);

		Label label = null;
		GridData gd = null;
		gd = new GridData();
		gd.horizontalSpan = nbCol;
		gd.grabExcessHorizontalSpace = true;
		gd.horizontalAlignment = SWT.CENTER;

		label = new Label(parent,SWT.NULL);
		label.setLayoutData(gd);
		label.setText(getName());
		
		FontData[] fD = label.getFont().getFontData();
		fD[0].setHeight(14);
		fD[0].setStyle(SWT.BOLD);
		label.setFont( new Font(label.getDisplay(),fD[0]));
		//label.setFont(new Font(label.getDisplay(),new FontData("", 18, SWT.BOLD)));
		
//		FormText description = new FormText(parent, SWT.NO_FOCUS);
//		//http://www.eclipse.org/articles/Article-Forms/article.html
//		description.setLayoutData(gd);
//		description.setText(getDescription(),false,false);

		if(l!=null) {

			for (String s : l.keySet()) {
				Group c = new Group(parent, SWT.NONE);
				c.setText(s);
				Color color = new Color(c.getShell().getDisplay(), 218,229,235);
				c.setBackground(color);
				
				GridLayout layout = new GridLayout();
				layout.makeColumnsEqualWidth = false;
				layout.numColumns = nbCol;
				c.setLayout(layout);
				
				GridData gdGroup = new GridData();
				//gdGroup.horizontalSpan = nbCol;
				gdGroup.grabExcessHorizontalSpace = true;
				gdGroup.horizontalAlignment = SWT.FILL;
				c.setLayoutData(gdGroup);

				//for (Param param : l) {
				for (Param param : l.get(s)) {
					if(param.type == TYPE_PARAM_TEXT) {
						label = new Label(c,SWT.NULL);
						label.setText(param.label);
						Text text = new Text(c,SWT.BORDER);
						text.setText(param.defaultValue);
						if(param.control==null) param.control = new LinkedList<Control>();
						param.control.add(text);
						//result.put(param, new Control[] {text});

					} else if(param.type == TYPE_PARAM_NUM) {
						label = new Label(c,SWT.NULL);
						label.setText(param.label);
						Text number = new Text(c,SWT.BORDER);
						number.setText(param.defaultValue);
						//result.put(param, new Control[] {number});
						if(param.control==null) param.control = new LinkedList<Control>();
						param.control.add(number);
					} else if(param.type == TYPE_PARAM_DATE) {
						label = new Label(c,SWT.NULL);
						label.setText(param.label);
						DateTime date = new DateTime(c,SWT.BORDER | SWT.DROP_DOWN);
						if(param.defaultDate!=null)
							LibDateTime.setDate(date,param.defaultDate);
						if(param.control==null) param.control = new LinkedList<Control>();
						//result.put(param, new Control[] {date});
						param.control.add(date);
					} else if(param.type == TYPE_PARAM_BOOLEAN) {
						label = new Label(c,SWT.NULL);
						label.setText(param.label);
						Button booleen = new Button(c,SWT.CHECK);
						if(param.control==null) param.control = new LinkedList<Control>();
						booleen.setSelection(LibConversion.StringToBoolean(param.defaultValue, true));
						//result.put(param, new Control[] {booleen});
						param.control.add(booleen);
					} else if(param.type == TYPE_PARAM_LIST) {
						//label = new Label(c,SWT.NULL);
						//label.setText(param.label);
						//Composite comp = toolkit.createComposite(c,SWT.BORDER);
						//Section comp = toolkit.createSection(c,SWT.NONE);
						//Group comp = new Group(c,SWT.BORDER);
						//comp.setText("test");
						Composite comp = new Composite(c,SWT.NONE);
						
						//compo.setBackground(new Color(compo.getShell().getDisplay(), 218,229,235));
						//comp.setBackgroundMode(SWT.INHERIT_FORCE);
						//comp.setBackground(color);
						comp.setLayout(new GridLayout(1, true));
						GridData gdComp = new GridData();
						gdComp.horizontalSpan = nbCol;
						gdComp.grabExcessHorizontalSpace = true;
						gdComp.horizontalAlignment = SWT.FILL;
						comp.setLayoutData(gdComp);
						Button radio = null;
						Control[] lc = new Control[param.liste.length];
						if(param.control==null) param.control = new LinkedList<Control>();
						for (int i = 0; i < param.liste.length; i++) {
							radio = new Button(comp,SWT.RADIO);
							radio.setText(param.liste[i][0]);
							//radio.setBackground(color);
							param.control.add(radio);
							if(i==0) {
								radio.setSelection(true);
							}
						}

						//result.put(param, lc);
					}
					listeControlParam.addAll(param.control);
					result.add(param);

					if(param.singleLine) {
						gd = new GridData();
						gd.horizontalSpan = 3;
						param.control.get(0).setLayoutData(gd);
					}
				}

				c.layout();
			}
		}
		return result;
	}
}
