package fr.legrain.gestCom.Module_Tiers;

import java.util.Date;

import fr.legrain.lib.data.ModelObject;

// Generated Mar 1, 2007 11:26:10 AM by Hibernate Tools 3.2.0.b9

/**
 * SWTEmail generated by hbm2java
 */
public class SWTSupportAbonHerbergement extends SWTSupportAbonLogiciel {
	
	private String serveur;


	public SWTSupportAbonHerbergement() {
	}




	public String getServeur() {
		return serveur;
	}



	public void setServeur(String serveur) {
		firePropertyChange("serveur", this.serveur, this.serveur = serveur);
	}




	public SWTSupportAbonHerbergement(String serveur) {
		super();
		this.serveur = serveur;
	}






	public void setSWTEpicea(SWTSupportAbonHerbergement swtEpicea) {
		this.setIdSupportAbon(swtEpicea.getIdSupportAbon());		
		this.setCodeSupportAbon( swtEpicea.getCodeSupportAbon());
		this.setCodeArticle( swtEpicea.getCodeArticle());
		this.setIdArticle( swtEpicea.getIdArticle());
		this.setCodeTiers( swtEpicea.getCodeTiers()) ;
		this.setIdTiers( swtEpicea.getIdTiers()) ;
		this.setCommentaire( swtEpicea.getCommentaire()) ;
		this.setIdCommercial( swtEpicea.getIdCommercial()) ;
		this.setCommercial( swtEpicea.getCommercial()) ;
		this.setDateAcquisition(swtEpicea.getDateAcquisition()) ;
		this.setIdLDocument(swtEpicea.getIdLDocument()) ;
		this.setLibelle( swtEpicea.getLibelle()) ;

		this.serveur = swtEpicea.serveur ;

	}

	
	public static SWTSupportAbonHerbergement copy(SWTSupportAbonHerbergement swtEpicea) {
		SWTSupportAbonHerbergement swtEpiceaLoc = new SWTSupportAbonHerbergement();
		swtEpiceaLoc.setServeur(swtEpicea.getServeur());
		
		swtEpiceaLoc.setIdSupportAbon(swtEpicea.getIdSupportAbon());
		swtEpiceaLoc.setCodeSupportAbon(swtEpicea.getCodeSupportAbon());
		swtEpiceaLoc.setCodeTiers(swtEpicea.getCodeTiers());
		swtEpiceaLoc.setIdTiers(swtEpicea.getIdTiers());		
		swtEpiceaLoc.setCodeArticle(swtEpicea.getCodeArticle());
		swtEpiceaLoc.setCommentaire(swtEpicea.getCommentaire());
		swtEpiceaLoc.setCommercial(swtEpicea.getCommercial());
		swtEpiceaLoc.setDateAcquisition(swtEpicea.getDateAcquisition());
		swtEpiceaLoc.setIdArticle(swtEpicea.getIdArticle());
		swtEpiceaLoc.setIdCommercial(swtEpicea.getIdCommercial());
		swtEpiceaLoc.setIdLDocument(swtEpicea.getIdLDocument());
		swtEpiceaLoc.setLibelle(swtEpicea.getLibelle());
		return swtEpiceaLoc;
	}




	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((serveur == null) ? 0 : serveur.hashCode());
		return result;
	}




	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		SWTSupportAbonHerbergement other = (SWTSupportAbonHerbergement) obj;
		if (serveur == null) {
			if (other.serveur != null)
				return false;
		} else if (!serveur.equals(other.serveur))
			return false;
		return true;
	}
	

	
	
}