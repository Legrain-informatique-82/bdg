package fr.legrain.gestCom.Module_Tiers;

import fr.legrain.lib.data.ModelObject;

public class SWTTBanque extends ModelObject {
	private Integer idTBanque;
	private String codeTBanque;
	private String liblTBanque;
	
	public SWTTBanque() {
		
	}
	
	public Integer getIdTBanque() {
		return idTBanque;
	}
	public void setIdTBanque(Integer idTBanque) {
		firePropertyChange("idTBanque", this.idTBanque, this.idTBanque = idTBanque);
	}
	public String getCodeTBanque() {
		return codeTBanque;
	}
	public void setCodeTBanque(String codeTBanque) {
		firePropertyChange("codeTBanque", this.codeTBanque, this.codeTBanque = codeTBanque);
	}
	public String getLiblTBanque() {
		return liblTBanque;
	}
	public void setLiblTBanque(String liblTBanque) {
		firePropertyChange("liblTBanque", this.liblTBanque, this.liblTBanque = liblTBanque);
	}

	public static SWTTBanque copy(SWTTBanque swtTypeBanque){
		SWTTBanque swtTypeBanqueLoc = new SWTTBanque();
		swtTypeBanqueLoc.setIdTBanque(swtTypeBanque.getIdTBanque());                //1
		swtTypeBanqueLoc.setCodeTBanque(swtTypeBanque.getCodeTBanque());        //2
		swtTypeBanqueLoc.setLiblTBanque(swtTypeBanque.getLiblTBanque());            //3
		return swtTypeBanqueLoc;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((codeTBanque == null) ? 0 : codeTBanque.hashCode());
		result = prime * result
				+ ((idTBanque == null) ? 0 : idTBanque.hashCode());
		result = prime * result
				+ ((liblTBanque == null) ? 0 : liblTBanque.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SWTTBanque other = (SWTTBanque) obj;
		if (codeTBanque == null) {
			if (other.codeTBanque != null)
				return false;
		} else if (!codeTBanque.equals(other.codeTBanque))
			return false;
		if (idTBanque == null) {
			if (other.idTBanque != null)
				return false;
		} else if (!idTBanque.equals(other.idTBanque))
			return false;
		if (liblTBanque == null) {
			if (other.liblTBanque != null)
				return false;
		} else if (!liblTBanque.equals(other.liblTBanque))
			return false;
		return true;
	}
	
}

