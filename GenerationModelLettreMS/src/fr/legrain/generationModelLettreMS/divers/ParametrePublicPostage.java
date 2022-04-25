package fr.legrain.generationModelLettreMS.divers;

import java.io.Serializable;

public class ParametrePublicPostage  implements Serializable{

	private String nameParamPublicPostage;
	private String pathParamPublicPostage;
	private int choixIndexCcombo;
	private String pathFileExtraction;
	
	private boolean isChoixMotCle = false;
	private String pathFileMotCle;
	
	private boolean isChoixSavePublicPostage = false;
	private String pathFileSavePublicPostage;
	private String pathDefautFileSavePublipostage;
	
	private String valueSeparateur;
	private boolean isChoixSeparateur = false;
	
	private String namePublicpostage;
	private boolean isSavePublicpostage = false;
	private boolean isSavePublicpostagePagePlugin = false;
	

	private String pathFileModelLettre;
	private boolean isChoixModelLettre = false;

	private int indexChoixCcomboSeparateur;
	private String namePlugin;

	public ParametrePublicPostage() {
	}

	public String getNameParamPublicPostage() {
		return nameParamPublicPostage;
	}

	public void setNameParamPublicPostage(String nameParamPublicPostage) {
		this.nameParamPublicPostage = nameParamPublicPostage;
	}

	public String getPathParamPublicPostage() {
		return pathParamPublicPostage;
	}

	public void setPathParamPublicPostage(String pathParamPublicPostage) {
		this.pathParamPublicPostage = pathParamPublicPostage;
	}

	public int getChoixIndexCcombo() {
		return choixIndexCcombo;
	}

	public void setChoixIndexCcombo(int choixIndexCcombo) {
		this.choixIndexCcombo = choixIndexCcombo;
	}

	public String getPathFileExtraction() {
		return pathFileExtraction;
	}

	public void setPathFileExtraction(String pathFileExtraction) {
		this.pathFileExtraction = pathFileExtraction;
	}

	public String getPathFileMotCle() {
		return pathFileMotCle;
	}

	public void setPathFileMotCle(String pathFileMotCle) {
		this.pathFileMotCle = pathFileMotCle;
	}

	public boolean isChoixMotCle() {
		return isChoixMotCle;
	}

	public void setChoixMotCle(boolean isChoixMotCle) {
		this.isChoixMotCle = isChoixMotCle;
	}

	public boolean isChoixSavePublicPostage() {
		return isChoixSavePublicPostage;
	}

	public void setChoixSavePublicPostage(boolean isChoixSavePublicPostage) {
		this.isChoixSavePublicPostage = isChoixSavePublicPostage;
	}

	public String getPathFileSavePublicPostage() {
		return pathFileSavePublicPostage;
	}

	public void setPathFileSavePublicPostage(String pathFileSavePublicPostage) {
		this.pathFileSavePublicPostage = pathFileSavePublicPostage;
	}

	public String getValueSeparateur() {
		return valueSeparateur;
	}

	public void setValueSeparateur(String valueSeparateur) {
		this.valueSeparateur = valueSeparateur;
	}

	public boolean isChoixSeparateur() {
		return isChoixSeparateur;
	}

	public void setChoixSeparateur(boolean isChoixSeparateur) {
		this.isChoixSeparateur = isChoixSeparateur;
	}

	public String getNamePublicpostage() {
		return namePublicpostage;
	}

	public void setNamePublicpostage(String namePublicpostage) {
		this.namePublicpostage = namePublicpostage;
	}

	public boolean isSavePublicpostage() {
		return isSavePublicpostage;
	}

	public void setSavePublicpostage(boolean isSavePublicpostage) {
		this.isSavePublicpostage = isSavePublicpostage;
	}

	public String getPathFileModelLettre() {
		return pathFileModelLettre;
	}

	public void setPathFileModelLettre(String pathFileModelLettre) {
		this.pathFileModelLettre = pathFileModelLettre;
	}

	public String getNamePlugin() {
		return namePlugin;
	}

	public void setNamePlugin(String namePlugin) {
		this.namePlugin = namePlugin;
	}

	public boolean isChoixModelLettre() {
		return isChoixModelLettre;
	}

	public void setChoixModelLettre(boolean isChoixModelLettre) {
		this.isChoixModelLettre = isChoixModelLettre;
	}

	public boolean isSavePublicpostagePagePlugin() {
		return isSavePublicpostagePagePlugin;
	}

	public void setSavePublicpostagePagePlugin(boolean isSavePublicpostagePagePlugin) {
		this.isSavePublicpostagePagePlugin = isSavePublicpostagePagePlugin;
	}

	public String getPathDefautFileSavePublipostage() {
		return pathDefautFileSavePublipostage;
	}

	public void setPathDefautFileSavePublipostage(
			String pathDefautFileSavePublipostage) {
		this.pathDefautFileSavePublipostage = pathDefautFileSavePublipostage;
	}

	public int getIndexChoixCcomboSeparateur() {
		return indexChoixCcomboSeparateur;
	}

	public void setIndexChoixCcomboSeparateur(int indexChoixCcomboSeparateur) {
		this.indexChoixCcomboSeparateur = indexChoixCcomboSeparateur;
	}


}
