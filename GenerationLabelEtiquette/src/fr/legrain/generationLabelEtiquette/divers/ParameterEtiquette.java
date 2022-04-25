package fr.legrain.generationLabelEtiquette.divers;

import java.io.Serializable;

public class ParameterEtiquette implements Serializable {
//public class ParameterEtiqutte {

	private String nameParameterEtiqutte;
	private String pathFileParamEtiquette;
	private Float leftMargin;
	private Float rightMargin;
	private Float topMargin;
	private Float bottomMargin;
	
	private Float leftPadding;
	private Float rightPadding;
	private Float topPadding;
	private Float bottomPadding;
	
	private Float largeurPapier;
	private Float hauteurPapier;
	
	private Float largeurEspace;
	private Float hauteurEspace;
	private String typePaper;
//	private boolean flagChoixPreDefinionModelLables;
	
	
	private boolean cellBorder = false;
	
	private Integer nombreColumns;
	private Integer nombreRows;
	
	private String sizeEtiquette;
//	private String styleGras = "";
//	private String styleItalic = "";
	private boolean isGras = false;
	private boolean isItalic = false;
	
	private String pathFileExtraction; 

	private String textModelEtiquette;
	
	private String valueSeparateur;
	private boolean isChoixSeparateur = false;
	
	private boolean isChoixMotCle = false;
	private String pathFileMotCle;
	
	private Integer decalage;
	private Integer quantite;
	
	
	public ParameterEtiquette() {
		
	}
	public ParameterEtiquette(Float leftMargin,Float rightMargin,Float topMargin,Float bottomMargin,Float leftPadding,Float rightPadding,
							 Float topPadding,Float bottomPadding,Float largeurPapier,Float hauteurPapier,Float largeurEspace,
							 Float hauteurEspace,String typePaper,Integer nombreColumns,Integer nombreRows,String valueSeparateur,
							 String pathFileMotCle){
		
		
		this.leftMargin = leftMargin;
		this.rightMargin = rightMargin;
		this.topMargin = topMargin;
		this.bottomMargin = bottomMargin;
		
		this.leftPadding = leftPadding;
		this.rightPadding = rightPadding;
		this.topPadding = topPadding;
		this.bottomPadding = bottomPadding;
		
		this.largeurPapier = largeurPapier;
		this.hauteurPapier = hauteurPapier;
		
		this.largeurEspace = largeurEspace;
		this.hauteurEspace = hauteurEspace;
		
		this.typePaper = typePaper;
		
		this.nombreRows = nombreRows;
		this.nombreColumns = nombreColumns;
		
		this.valueSeparateur = valueSeparateur;
		
		this.pathFileMotCle = pathFileMotCle;
	}

	public String getNameParameterEtiqutte() {
		return nameParameterEtiqutte;
	}

	public void setNameParameterEtiqutte(String nameParameterEtiquttes) {
		nameParameterEtiqutte = nameParameterEtiquttes;
	}

	public Float getLeftMargin() {
		return leftMargin;
	}

	public void setLeftMargin(Float leftMargin) {
		this.leftMargin = leftMargin;
	}

	public Float getRightMargin() {
		return rightMargin;
	}

	public void setRightMargin(Float rightMargin) {
		this.rightMargin = rightMargin;
	}

	public Float getTopMargin() {
		return topMargin;
	}

	public void setTopMargin(Float topMargin) {
		this.topMargin = topMargin;
	}

	public Float getBottomMargin() {
		return bottomMargin;
	}

	public void setBottomMargin(Float bottomMargin) {
		this.bottomMargin = bottomMargin;
	}

	public Float getLeftPadding() {
		return leftPadding;
	}

	public void setLeftPadding(Float leftPadding) {
		this.leftPadding = leftPadding;
	}

	public Float getRightPadding() {
		return rightPadding;
	}

	public void setRightPadding(Float rightPadding) {
		this.rightPadding = rightPadding;
	}

	public Float getTopPadding() {
		return topPadding;
	}

	public void setTopPadding(Float topPadding) {
		this.topPadding = topPadding;
	}

	public Float getBottomPadding() {
		return bottomPadding;
	}

	public void setBottomPadding(Float bottomPadding) {
		this.bottomPadding = bottomPadding;
	}

	public Float getLargeurPapier() {
		return largeurPapier;
	}

	public void setLargeurPapier(Float largeurPapier) {
		this.largeurPapier = largeurPapier;
	}

	public Float getHauteurPapier() {
		return hauteurPapier;
	}

	public void setHauteurPapier(Float hauteurPapier) {
		this.hauteurPapier = hauteurPapier;
	}

	public Float getLargeurEspace() {
		return largeurEspace;
	}

	public void setLargeurEspace(Float largeurEspace) {
		this.largeurEspace = largeurEspace;
	}

	public Float getHauteurEspace() {
		return hauteurEspace;
	}

	public void setHauteurEspace(Float hauteurEspace) {
		this.hauteurEspace = hauteurEspace;
	}

	public boolean isCellBorder() {
		return cellBorder;
	}

	public void setCellBorder(boolean cellBorder) {
		this.cellBorder = cellBorder;
	}

	public Integer getNombreColumns() {
		return nombreColumns;
	}

	public void setNombreColumns(Integer nombreColumns) {
		this.nombreColumns = nombreColumns;
	}

	public Integer getNombreRows() {
		return nombreRows;
	}

	public void setNombreRows(Integer nombreRows) {
		this.nombreRows = nombreRows;
	}

	public String getSizeEtiquette() {
		return sizeEtiquette;
	}

	public void setSizeEtiquette(String sizeEtiquette) {
		this.sizeEtiquette = sizeEtiquette;
	}

	public String getPathFileExtraction() {
		return pathFileExtraction;
	}

	public void setPathFileExtraction(String pathFileExtraction) {
		this.pathFileExtraction = pathFileExtraction;
	}
	public String getTextModelEtiquette() {
		return textModelEtiquette;
	}

	public void setTextModelEtiquette(String textModelEtiquette) {
		this.textModelEtiquette = textModelEtiquette;
	}

	public String getTypePaper() {
		return typePaper;
	}

	public void setTypePaper(String typePaper) {
		this.typePaper = typePaper;
	}

	public boolean isGras() {
		return isGras;
	}

	public void setGras(boolean isGras) {
		this.isGras = isGras;
	}

	public boolean isItalic() {
		return isItalic;
	}

	public void setItalic(boolean isItalic) {
		this.isItalic = isItalic;
	}

	public String getPathFileParamEtiquette() {
		return pathFileParamEtiquette;
	}

	public void setPathFileParamEtiquette(String pathFileParamEtiquette) {
		this.pathFileParamEtiquette = pathFileParamEtiquette;
	}

	public String getPathFileMotCle() {
		return pathFileMotCle;
	}

	public void setPathFileMotCle(String pathFileMotCle) {
		this.pathFileMotCle = pathFileMotCle;
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

	public boolean isChoixMotCle() {
		return isChoixMotCle;
	}

	public void setChoixMotCle(boolean isChoixMotCle) {
		this.isChoixMotCle = isChoixMotCle;
	}
	public Integer getDecalage() {
		return decalage;
	}
	public void setDecalage(Integer decalage) {
		this.decalage = decalage;
	}
	public Integer getQuantite() {
		return quantite;
	}
	public void setQuantite(Integer quantite) {
		this.quantite = quantite;
	}

//	public boolean isFlagChoixPreDefinionModelLables() {
//		return flagChoixPreDefinionModelLables;
//	}
//
//	public void setFlagChoixPreDefinionModelLables(
//			boolean flagChoixPreDefinionModelLables) {
//		this.flagChoixPreDefinionModelLables = flagChoixPreDefinionModelLables;
//	}
	
	
}
