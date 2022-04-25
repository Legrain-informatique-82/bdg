package fr.legrain.lib.validation;

import javax.swing.event.EventListenerList;

import org.apache.log4j.Logger;
import org.eclipse.core.databinding.validation.IValidator;
import org.eclipse.core.databinding.validation.ValidationStatus;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.MultiStatus;

import fr.legrain.lib.gui.JPABaseController;

public class JPACtrlInterface implements IValidator {

	static Logger logger = Logger.getLogger(JPACtrlInterface.class.getName());
	public static final String C_CONTROLE_INTERFACE = "Interface";

	protected EventListenerList listenerList = new EventListenerList();

	private JPABaseController controller;
	private String nomChamp;

	public JPACtrlInterface(JPABaseController ibTable,String nomChamp) {
		this.controller = ibTable;
		this.nomChamp = nomChamp;
	}

	public IStatus validate(Object value) {
		IStatus s = ValidationStatus.ok();
		MultiStatus m = new MultiStatus("1",0,new IStatus[]{s},C_CONTROLE_INTERFACE,null);
		s = controller.validateUIField(nomChamp,value);
		if(s==null || s.getSeverity()==IStatus.ERROR) {
			logger.error("Erreur de validation sur le champ "+nomChamp+" avec la valeur "+value);
			s = ValidationStatus.error(s.getMessage(),s.getException());
		}
		m =new MultiStatus("1",0,new IStatus[]{s},C_CONTROLE_INTERFACE,null);
		//TODO #JPA
		return m;
//		if(!ibTable.dataSetEnModif())return m;
//			System.out.println("CtrlInterface.validate() "+nomChamp+" : "+value);
//			//if(!VerrouInterface.isVerrouille()){ //interface verrouillée donc pas de vérif
//			try {
//				//MessageDialog.openError(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(),"titre", "message");
//				ibTable.verifChamp(nomChamp, value, null, null);
//				s = ValidationStatus.ok();
//				m =new MultiStatus("1",0,new IStatus[]{s},"Interface",null);
//				return m;
//			} catch (ExceptLgr e) {
//				s = ValidationStatus.error(e.getMessage(),e);
//				m =new MultiStatus("1",0,new IStatus[]{s},"Interface",null);
//				logger.error(e.getMessage(),e);
//				return m;
//			} catch (ParseException e) {
//				s = ValidationStatus.error(e.getMessage(),e);
//				m =new MultiStatus("1",0,new IStatus[]{s},"Interface",null);
//				logger.error(e.getMessage(),e);
//				return m;
//			}

//		} else {
//		return null;
//		}
	}

//	public AbstractLgrDAO getIbTable() {
//		return ibTable;
//	}
//
//	public void setIbTable(AbstractLgrDAO ibTable) {
//		this.ibTable = ibTable;
//	}

}

