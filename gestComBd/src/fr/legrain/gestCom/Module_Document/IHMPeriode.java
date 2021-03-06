package fr.legrain.gestCom.Module_Document;

// Generated Mar 1, 2007 11:26:10 AM by Hibernate Tools 3.2.0.b9



import java.util.Date;

import fr.legrain.lib.data.ModelObject;

/**
 * SWTLFacture generated by hbm2java
 */
public class IHMPeriode extends ModelObject {

	private Date periodeDebut;
	private Date periodeFin;


	public IHMPeriode() {
	}


	public Date getPeriodeDebut() {
		return periodeDebut;
	}

	
	
	public void setPeriodeDebut(Date periodeDebut) {
		firePropertyChange("periodeDebut", this.periodeDebut, this.periodeDebut = periodeDebut);
	}




	public Date getPeriodeFin() {
		return periodeFin;
	}




	public void setPeriodeFin(Date periodeFin) {
		firePropertyChange("periodeFin", this.periodeFin, this.periodeFin = periodeFin);
	}


	public IHMPeriode(Date periodeDebut, Date periodeFin) {
		super();
		this.periodeDebut = periodeDebut;
		this.periodeFin = periodeFin;
	}




	

	
	
}
