package archivageepiceatestreport.actions;

public class attribuElement {
	
	private String widthTableCol;
	private String TEXT_ALIGN;
	private String FONT_SIZE;
	private String FONT_WIDTH;
	private String UNITE_WIDTH;
	private String TYPE_COL;
	private String UNITE_CASH;
	
	public attribuElement(String widthTableCol, String text_align,
								 String font_size, String font_width,String unite_width,
								 String type_col,String unite_cash) {
		super();
		this.widthTableCol = widthTableCol;
		
		TEXT_ALIGN = text_align;
		FONT_SIZE = font_size;
		FONT_WIDTH = font_width;
		UNITE_WIDTH = unite_width;
		TYPE_COL = type_col;
		UNITE_CASH = unite_cash;
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
}
