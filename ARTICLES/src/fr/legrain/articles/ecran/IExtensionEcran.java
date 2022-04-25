package fr.legrain.articles.ecran;

import java.util.LinkedHashMap;
import java.util.Map;

import org.eclipse.swt.widgets.Control;

public interface IExtensionEcran {

	public void ecranSWT(PaArticleSWT vue);

	public void grilleSWT();

//	public void initialisation();
//
	public void bind();
	
	public void initMapComposantChamps(Map<Control, String> mapComposantChamps);
//
//	public void mappingDTO();
//
//	public void validation();
//
//	public void aide();
//
//	public void actions();
//
//	public void retourEcran();
//
//	public void enregistrer();
//
//	public void inserer();

}