package fr.legrain.facture.actions;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;

import org.eclipse.jface.action.IAction;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.IWorkbenchWindowActionDelegate;
import org.eclipse.ui.PlatformUI;

import fr.legrain.documents.dao.TaBonliv;
import fr.legrain.documents.dao.TaBonlivDAO;
import fr.legrain.documents.dao.TaDevis;
import fr.legrain.documents.dao.TaDevisDAO;
import fr.legrain.documents.dao.TaFacture;
import fr.legrain.documents.dao.TaFactureDAO;
import fr.legrain.documents.dao.TaLFacture;
import fr.legrain.gestCom.Appli.EntityManagerUtil;

public class MajDocument implements IWorkbenchWindowActionDelegate {

	public void dispose() {
		// TODO Auto-generated method stub

	}

	public void init(IWorkbenchWindow window) {
		// TODO Auto-generated method stub

	}

	public void run(IAction action) {
		try {
			if(MessageDialog.openConfirm(PlatformUI.getWorkbench()
						.getActiveWorkbenchWindow().getShell(),
						"ATTENTION", "Voulez-vous vraiment mettre à jour les totaux des documents ?"
						+"\r\n"+"Cette procédure peut prendre du temps.")){
			//gestion des bonliv
			TaBonlivDAO daoBonliv = new TaBonlivDAO();
			daoBonliv.begin(daoBonliv.getEntityManager().getTransaction());
			List<TaBonliv> lBonliv = daoBonliv.selectAll();
			for (TaBonliv taDocument : lBonliv) {
				taDocument.calculeTvaEtTotaux();
				daoBonliv.merge(taDocument);
			}
			daoBonliv.commit(daoBonliv.getEntityManager().getTransaction());
				
			//gestion des devis
			TaDevisDAO daoDevis = new TaDevisDAO();
			daoDevis.begin(daoDevis.getEntityManager().getTransaction());
			List<TaDevis> lDevis = daoDevis.selectAll();
//			List<TaDevis> lDevis = daoDevis.rechercheDocument(new Date("01/01/1900"),new Date("01/01/2009"));
			for (TaDevis taDocument : lDevis) {
				taDocument.calculeTvaEtTotaux();
				daoDevis.merge(taDocument);
			}
			daoDevis.commit(daoDevis.getEntityManager().getTransaction());
			
			//gestion des factures
			TaFactureDAO daoFacture = new TaFactureDAO();
			daoFacture.begin(daoFacture.getEntityManager().getTransaction());
			List<TaFacture> lFacture = daoFacture.selectAll();
//			List<TaFacture> lFacture = daoFacture.rechercheDocument(new Date("01/01/1900"),new Date("01/01/2009"));
			for (TaFacture taDocument : lFacture) {
				taDocument.calculeTvaEtTotaux();
				daoFacture.merge(taDocument);
			}
			daoFacture.commit(daoFacture.getEntityManager().getTransaction());
			}
		} catch(Exception e) {
			e.printStackTrace();
		}

	}

	public void selectionChanged(IAction action, ISelection selection) {
		// TODO Auto-generated method stub

	}

}
