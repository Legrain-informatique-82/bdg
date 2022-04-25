package fr.legrain.lib.validation;

import java.text.ParseException;

import javax.swing.event.EventListenerList;

import org.apache.log4j.Logger;
import org.eclipse.core.databinding.validation.IValidator;
import org.eclipse.core.databinding.validation.ValidationStatus;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.MultiStatus;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.ui.PlatformUI;

import fr.legrain.lib.data.ExceptLgr;
import fr.legrain.lib.data.IBQuLgr;
import fr.legrain.lib.data.AbstractLgrDAO;
import fr.legrain.lib.data.VerrouInterface;

public class CtrlInterface implements IValidator {

	static Logger logger = Logger.getLogger(CtrlInterface.class.getName());

	protected EventListenerList listenerList = new EventListenerList();

	private IBQuLgr ibTable;
	private String nomChamp;

	public CtrlInterface(IBQuLgr ibTable,String nomChamp) {
		this.ibTable = ibTable;
		this.nomChamp = nomChamp;
	}

	public IStatus validate(Object value) {
		IStatus s = ValidationStatus.ok();
		MultiStatus m = new MultiStatus("1",0,new IStatus[]{s},"Interface",null); ;
		if(!ibTable.dataSetEnModif())return m;
			System.out.println("CtrlInterface.validate() "+nomChamp+" : "+value);
			//if(!VerrouInterface.isVerrouille()){ //interface verrouillée donc pas de vérif
			try {
				//MessageDialog.openError(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(),"titre", "message");
				ibTable.verifChamp(nomChamp, value, null, null);
				s = ValidationStatus.ok();
				m =new MultiStatus("1",0,new IStatus[]{s},"Interface",null);
				return m;
			} catch (ExceptLgr e) {
				s = ValidationStatus.error(e.getMessage(),e);
				m =new MultiStatus("1",0,new IStatus[]{s},"Interface",null);
				logger.error(e.getMessage(),e);
				return m;
			} catch (ParseException e) {
				s = ValidationStatus.error(e.getMessage(),e);
				m =new MultiStatus("1",0,new IStatus[]{s},"Interface",null);
				logger.error(e.getMessage(),e);
				return m;
			}

//		} else {
//		return null;
//		}
	}

	public IBQuLgr getIbTable() {
		return ibTable;
	}

	public void setIbTable(IBQuLgr ibTable) {
		this.ibTable = ibTable;
	}

}

