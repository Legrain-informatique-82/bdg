package fr.legrain.gestCom.global;


public class VERSION {
	private String NUM_VERSION;
	private String OLD_VERSION; 
	private String DATE_VERSION;

	public static VERSION getVERSION(String idVersion,VERSION version) {
		if (idVersion!=null && !idVersion.equals("0")){
			IB_TA_VERSION ibTaVersion= new IB_TA_VERSION();
			return ibTaVersion.infosVersion(idVersion,version);
		}return null;
	}

	public String getNUM_VERSION() {
		return NUM_VERSION;
	}

	public void setNUM_VERSION(String num_version) {
		NUM_VERSION = num_version;
	}

	public String getOLD_VERSION() {
		return OLD_VERSION;
	}

	public void setOLD_VERSION(String old_version) {
		OLD_VERSION = old_version;
	}

	public String getDATE_VERSION() {
		return DATE_VERSION;
	}

	public void setDATE_VERSION(String date_version) {
		DATE_VERSION = date_version;
	} 
}
