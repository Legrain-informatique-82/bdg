package fr.legrain.liasseFiscale.db.dao;

// Generated Dec 7, 2006 10:37:19 AM by Hibernate Tools 3.1.0.beta5

/**
 * InitialCompl generated by hbm2java
 */
public class InitialCompl implements java.io.Serializable {

	// Fields    

	private Integer idInitialCompl;

	private Integer idCompl;

	private String valeur;

	private String emplacement;

	// Constructors

	/** default constructor */
	public InitialCompl() {
	}

	/** minimal constructor */
	public InitialCompl(Integer idInitialCompl) {
		this.idInitialCompl = idInitialCompl;
	}

	/** full constructor */
	public InitialCompl(Integer idInitialCompl, Integer idCompl, String valeur,
			String emplacement) {
		this.idInitialCompl = idInitialCompl;
		this.idCompl = idCompl;
		this.valeur = valeur;
		this.emplacement = emplacement;
	}

	// Property accessors
	public Integer getIdInitialCompl() {
		return this.idInitialCompl;
	}

	public void setIdInitialCompl(Integer idInitialCompl) {
		this.idInitialCompl = idInitialCompl;
	}

	public Integer getIdCompl() {
		return this.idCompl;
	}

	public void setIdCompl(Integer idCompl) {
		this.idCompl = idCompl;
	}

	public String getValeur() {
		return this.valeur;
	}

	public void setValeur(String valeur) {
		this.valeur = valeur;
	}

	public String getEmplacement() {
		return this.emplacement;
	}

	public void setEmplacement(String emplacement) {
		this.emplacement = emplacement;
	}

}
