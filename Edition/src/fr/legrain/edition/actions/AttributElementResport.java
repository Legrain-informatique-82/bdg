package fr.legrain.edition.actions;

/**
 * Attribut d'un colonne dans une table Birt
 * Utilisé pour la génération dynamique de fichier d'édition.
 */
public class AttributElementResport {
	
	private String name;
	private String widthTableCol;
	private String TEXT_ALIGN;
	private String FONT_SIZE;
	private String FONT_WIDTH;
	private String UNITE_WIDTH;
	private String TYPE_COL;
	private String UNITE_CASH;
	private String COLCOR;
	private String TYPE_BOOLEAN;
	
	public AttributElementResport(String widthTableCol, String text_align,
								 String font_size, String font_width,String unite_width,
								 String type_col,String unite_cash,String colcor/*,String typeBoolean*/) {
		super();
		this.widthTableCol = widthTableCol;
		
		TEXT_ALIGN = text_align;
		FONT_SIZE = font_size;
		FONT_WIDTH = font_width;
		UNITE_WIDTH = unite_width;
		TYPE_COL = type_col;
		UNITE_CASH = unite_cash;
		COLCOR = colcor;
		//TYPE_BOOLEAN = typeBoolean;
	}

	public AttributElementResport() {
		super();
		// TODO Auto-generated constructor stub
	}
	public AttributElementResport(String[] attributes) {
		this.name = attributes[0];
		this.widthTableCol = attributes[1];
		this.TEXT_ALIGN = attributes[2];
		this.FONT_SIZE = attributes[3];
		this.FONT_WIDTH = attributes[4];
		this.UNITE_WIDTH = attributes[5];
		this.TYPE_COL = attributes[6];
		this.UNITE_CASH = attributes[7];
		this.COLCOR = attributes[8];
//		Code article;12;center;medium;bold;%;string;vide;vide
		// TODO Auto-generated constructor stub
	}

	public String getWidthTableCol() {
		return widthTableCol;
	}

	public void setWidthTableCol(String widthTableCol) {
		this.widthTableCol = widthTableCol;
	}

	public String getTEXT_ALIGN() {
		return TEXT_ALIGN;
	}

	public void setTEXT_ALIGN(String text_align) {
		TEXT_ALIGN = text_align;
	}

	public String getFONT_SIZE() {
		return FONT_SIZE;
	}

	public void setFONT_SIZE(String font_size) {
		FONT_SIZE = font_size;
	}

	public String getFONT_WIDTH() {
		return FONT_WIDTH;
	}

	public void setFONT_WIDTH(String font_width) {
		FONT_WIDTH = font_width;
	}

	public String getUNITE_WIDTH() {
		return UNITE_WIDTH;
	}

	public void setUNITE_WIDTH(String unite_width) {
		UNITE_WIDTH = unite_width;
	}

	public String getTYPE_COL() {
		return TYPE_COL;
	}

	public void setTYPE_COL(String type_col) {
		TYPE_COL = type_col;
	}

	public String getUNITE_CASH() {
		return UNITE_CASH;
	}

	public void setUNITE_CASH(String unite_cash) {
		UNITE_CASH = unite_cash;
	}

	public String getCOLCOR() {
		return COLCOR;
	}

	public void setCOLCOR(String colcor) {
		COLCOR = colcor;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
