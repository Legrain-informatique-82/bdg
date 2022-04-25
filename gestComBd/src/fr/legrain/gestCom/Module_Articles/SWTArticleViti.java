//package fr.legrain.gestCom.Module_Articles;
//
//
///**
// * SWTArticleViti
// */
//public class SWTArticleViti extends SWTArticle {
//
//	private String titreTransport;
//
//	public SWTArticleViti() {}
//
//	public String getCodeTitreTransport() {
//		return this.titreTransport;
//	}
//
//	public void setCodeTitreTransport(String titreTransport) {
//		firePropertyChange("titreTransport", this.titreTransport, this.titreTransport = titreTransport);
//	}
//
//	public static SWTArticleViti copy(SWTArticleViti swtArticle){
//		
//		SWTArticleViti swtArticleLoc = (SWTArticleViti)SWTArticle.copy((SWTArticle)swtArticle, (SWTArticle)(new SWTArticleViti()));
//		swtArticleLoc.setTitreTransport(swtArticle.titreTransport);
//
//		return swtArticleLoc;
//	}
//}
