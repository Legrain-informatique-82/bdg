package fr.legrain.openmail.mail.ui;


public class ParamAfficheBrowser {

	public String url = null;
	public String postData = null;
	public String[] httpHeader = null;
	public String titre = null;
	
	public ParamAfficheBrowser() {}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getTitre() {
		return titre;
	}

	public void setTitre(String titre) {
		this.titre = titre;
	}

	public String getPostData() {
		return postData;
	}

	public void setPostData(String postData) {
		this.postData = postData;
	}

	public String[] getHttpHeader() {
		return httpHeader;
	}

	public void setHttpHeader(String[] httpHeader) {
		this.httpHeader = httpHeader;
	} 
}
