package fr.legrain.etats.handlers;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

import javax.persistence.EntityTransaction;

import org.apache.log4j.Logger;
import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.dialogs.ProgressMonitorDialog;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.handlers.HandlerUtil;

import fr.legrain.documents.dao.TaAcompte;
import fr.legrain.documents.dao.TaAcompteDAO;
import fr.legrain.documents.dao.TaApporteur;
import fr.legrain.documents.dao.TaApporteurDAO;
import fr.legrain.documents.dao.TaAvoir;
import fr.legrain.documents.dao.TaAvoirDAO;
import fr.legrain.documents.dao.TaBoncde;
import fr.legrain.documents.dao.TaBoncdeDAO;
import fr.legrain.documents.dao.TaBonliv;
import fr.legrain.documents.dao.TaBonlivDAO;
import fr.legrain.documents.dao.TaDevis;
import fr.legrain.documents.dao.TaDevisDAO;
import fr.legrain.documents.dao.TaFacture;
import fr.legrain.documents.dao.TaFactureDAO;
import fr.legrain.documents.dao.TaPrelevement;
import fr.legrain.documents.dao.TaPrelevementDAO;
import fr.legrain.documents.dao.TaProforma;
import fr.legrain.documents.dao.TaProformaDAO;

/**
 * Our sample handler extends AbstractHandler, an IHandler base class.
 * @see org.eclipse.core.commands.IHandler
 * @see org.eclipse.core.commands.AbstractHandler
 */
public class MiseAJourLocalisationTVADocumentHandler extends AbstractHandler {
	
	static Logger logger = Logger.getLogger(MiseAJourLocalisationTVADocumentHandler.class.getName());
	
	/**
	 * The constructor.
	 */
	public MiseAJourLocalisationTVADocumentHandler() {
	}

	/**
	 * the command has been executed, so extract extract the needed information
	 * from the application context.
	 */
	public Object execute(ExecutionEvent event) throws ExecutionException {
		IWorkbenchWindow window = HandlerUtil.getActiveWorkbenchWindowChecked(event);
		
		if(MessageDialog.openConfirm(
				window.getShell(),
				"Etats",
				"Etes vous sur de vouloir éffectuer la mise à jour des informations de localisation TVA dans les documents à partir des informations du tiers?" +
				"\n Cette action est irréversible.")) {
			maj();
		}
		return null;
	}

	public void maj() {
		try {
			new ProgressMonitorDialog(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell()).run(true, true, new MajDocWithProgress(false));
		} catch (InvocationTargetException e) {
			logger.error("",e);
		} catch (InterruptedException e) {
			logger.error("",e);
		}
	}
	
	public void verification() {
//		TaFactureDAO dao = new TaFactureDAO();
//		dao.
	}

	class MajDocWithProgress implements IRunnableWithProgress {
		private int TOTAL = initTotal();

		private boolean indeterminate;

		public MajDocWithProgress(boolean indeterminate) {
			this.indeterminate = indeterminate;
		}
		
		public int initTotal() {
			int i = new TaFactureDAO().selectCount();
			i += new TaDevisDAO().selectCount();
			i += new TaBoncdeDAO().selectCount();
			i += new TaAvoirDAO().selectCount();
			i += new TaApporteurDAO().selectCount();
			i += new TaAcompteDAO().selectCount();
			i += new TaProformaDAO().selectCount();
			i += new TaPrelevementDAO().selectCount();
			i += new TaBonlivDAO().selectCount();
			return i;
		}

		public void run(IProgressMonitor monitor) throws InvocationTargetException, InterruptedException {
			monitor.beginTask("Mise à jour des documents", indeterminate ? IProgressMonitor.UNKNOWN : TOTAL);
			//for (int total = 0; total < TOTAL_TIME && !monitor.isCanceled(); total++) {}
			majFacture(monitor);
			majDevis(monitor);
			majBonCommande(monitor);
			majAvoir(monitor);
			majApporteur(monitor);
			majAcompte(monitor);
			majProforma(monitor);
			majPrelevement(monitor);
			majBonLivraison(monitor);
			
			monitor.done();
			if (monitor.isCanceled()) {
				throw new InterruptedException("The long running operation was cancelled");
			} 
//			else {
//				MessageDialog.openInformation(
//						PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(),
//						"Etats",
//						"La mise à jour c'est déroulée correctement.");
//			}
		}
	} 

	public void majDevis(IProgressMonitor monitor) {
		try {
			TaDevisDAO dao = new TaDevisDAO();
			List<TaDevis> l = dao.selectAll();
			String codeTTvaDocTiers = null;
			
			if(monitor!=null) monitor.subTask("Devis");
			for (TaDevis taDevis : l) {
				codeTTvaDocTiers = taDevis.getTaTiers().getTaTTvaDoc().getCodeTTvaDoc();

				EntityTransaction et = dao.getEntityManager().getTransaction();
				et.begin();
				dao.modifier(taDevis);
				taDevis.getTaInfosDocument().setCodeTTvaDoc(codeTTvaDocTiers);
				dao.enregistrerMerge(taDevis);
				dao.commit(et);
				
				if(monitor!=null) monitor.worked(1);
			}
		} catch (Exception e) {
			logger.error("",e);
		}
	}
	
	public void majAvoir(IProgressMonitor monitor) {
		try {
			TaAvoirDAO dao = new TaAvoirDAO();
			List<TaAvoir> l = dao.selectAll();
			String codeTTvaDocTiers = null;
			
			if(monitor!=null) monitor.subTask("Avoir");
			for (TaAvoir taAvoir : l) {
				codeTTvaDocTiers = taAvoir.getTaTiers().getTaTTvaDoc().getCodeTTvaDoc();

				EntityTransaction et = dao.getEntityManager().getTransaction();
				et.begin();
				dao.modifier(taAvoir);
				taAvoir.getTaInfosDocument().setCodeTTvaDoc(codeTTvaDocTiers);
				dao.enregistrerMerge(taAvoir);
				dao.commit(et);
				
				if(monitor!=null) monitor.worked(1);
			}
		} catch (Exception e) {
			logger.error("",e);
		}
	}
	
	public void majApporteur(IProgressMonitor monitor) {
		try {
			TaApporteurDAO dao = new TaApporteurDAO();
			List<TaApporteur> l = dao.selectAll();
			String codeTTvaDocTiers = null;
			
			if(monitor!=null) monitor.subTask("Facture apporteur");
			for (TaApporteur taApporteur : l) {
				codeTTvaDocTiers = taApporteur.getTaTiers().getTaTTvaDoc().getCodeTTvaDoc();

				EntityTransaction et = dao.getEntityManager().getTransaction();
				et.begin();
				dao.modifier(taApporteur);
				taApporteur.getTaInfosDocument().setCodeTTvaDoc(codeTTvaDocTiers);
				dao.enregistrerMerge(taApporteur);
				dao.commit(et);
				
				if(monitor!=null) monitor.worked(1);
			}
		} catch (Exception e) {
			logger.error("",e);
		}
	}
	
	public void majAcompte(IProgressMonitor monitor) {
		try {
			TaAcompteDAO dao = new TaAcompteDAO();
			List<TaAcompte> l = dao.selectAll();
			String codeTTvaDocTiers = null;
			
			if(monitor!=null) monitor.subTask("Acompte");
			for (TaAcompte taAcompte : l) {
				codeTTvaDocTiers = taAcompte.getTaTiers().getTaTTvaDoc().getCodeTTvaDoc();

				EntityTransaction et = dao.getEntityManager().getTransaction();
				et.begin();
				dao.modifier(taAcompte);
				taAcompte.getTaInfosDocument().setCodeTTvaDoc(codeTTvaDocTiers);
				dao.enregistrerMerge(taAcompte);
				dao.commit(et);
				
				if(monitor!=null) monitor.worked(1);
			}
		} catch (Exception e) {
			logger.error("",e);
		}
	}
	
	public void majProforma(IProgressMonitor monitor) {
		try {
			TaProformaDAO dao = new TaProformaDAO();
			List<TaProforma> l = dao.selectAll();
			String codeTTvaDocTiers = null;
			
			if(monitor!=null) monitor.subTask("Proforma");
			for (TaProforma taProforma : l) {
				codeTTvaDocTiers = taProforma.getTaTiers().getTaTTvaDoc().getCodeTTvaDoc();

				EntityTransaction et = dao.getEntityManager().getTransaction();
				et.begin();
				dao.modifier(taProforma);
				taProforma.getTaInfosDocument().setCodeTTvaDoc(codeTTvaDocTiers);
				dao.enregistrerMerge(taProforma);
				dao.commit(et);
				
				if(monitor!=null) monitor.worked(1);
			}
		} catch (Exception e) {
			logger.error("",e);
		}
	}
	
	public void majPrelevement(IProgressMonitor monitor) {
		try {
			TaPrelevementDAO dao = new TaPrelevementDAO();
			List<TaPrelevement> l = dao.selectAll();
			String codeTTvaDocTiers = null;
			
			if(monitor!=null) monitor.subTask("Prélèvement");
			for (TaPrelevement taPrelevement : l) {
				codeTTvaDocTiers = taPrelevement.getTaTiers().getTaTTvaDoc().getCodeTTvaDoc();

				EntityTransaction et = dao.getEntityManager().getTransaction();
				et.begin();
				dao.modifier(taPrelevement);
				taPrelevement.getTaInfosDocument().setCodeTTvaDoc(codeTTvaDocTiers);
				dao.enregistrerMerge(taPrelevement);
				dao.commit(et);
				
				if(monitor!=null) monitor.worked(1);
			}
		} catch (Exception e) {
			logger.error("",e);
		}
	}
	
	public void majFacture(IProgressMonitor monitor) {
		try {
			TaFactureDAO dao = new TaFactureDAO();
			List<TaFacture> l = dao.selectAll();
			String codeTTvaDocTiers = null;
			
			if(monitor!=null) monitor.subTask("Facture");
			for (TaFacture taFacture : l) {
				codeTTvaDocTiers = taFacture.getTaTiers().getTaTTvaDoc().getCodeTTvaDoc();

				EntityTransaction et = dao.getEntityManager().getTransaction();
				et.begin();
				dao.modifier(taFacture);
				taFacture.getTaInfosDocument().setCodeTTvaDoc(codeTTvaDocTiers);
				dao.enregistrerMerge(taFacture);
				dao.commit(et);
				
				if(monitor!=null) monitor.worked(1);
			}
		} catch (Exception e) {
			logger.error("",e);
		}
	}
	
	public void majBonCommande(IProgressMonitor monitor) {
		try {
			TaBoncdeDAO dao = new TaBoncdeDAO();
			List<TaBoncde> l = dao.selectAll();
			String codeTTvaDocTiers = null;
			
			if(monitor!=null) monitor.subTask("Bon de commande");
			for (TaBoncde taBoncde : l) {
				codeTTvaDocTiers = taBoncde.getTaTiers().getTaTTvaDoc().getCodeTTvaDoc();

				EntityTransaction et = dao.getEntityManager().getTransaction();
				et.begin();
				dao.modifier(taBoncde);
				taBoncde.getTaInfosDocument().setCodeTTvaDoc(codeTTvaDocTiers);
				dao.enregistrerMerge(taBoncde);
				dao.commit(et);
				
				if(monitor!=null) monitor.worked(1);
			}
		} catch (Exception e) {
			logger.error("",e);
		}
	}
	
	public void majBonLivraison(IProgressMonitor monitor) {
		try {
			TaBonlivDAO dao = new TaBonlivDAO();
			List<TaBonliv> l = dao.selectAll();
			String codeTTvaDocTiers = null;
			
			if(monitor!=null) monitor.subTask("Bon de livraison");
			for (TaBonliv taBonliv : l) {
				codeTTvaDocTiers = taBonliv.getTaTiers().getTaTTvaDoc().getCodeTTvaDoc();

				EntityTransaction et = dao.getEntityManager().getTransaction();
				et.begin();
				dao.modifier(taBonliv);
				taBonliv.getTaInfosDocument().setCodeTTvaDoc(codeTTvaDocTiers);
				dao.enregistrerMerge(taBonliv);
				dao.commit(et);
				
				if(monitor!=null) monitor.worked(1);
			}
		} catch (Exception e) {
			logger.error("",e);
		}
	}
}
