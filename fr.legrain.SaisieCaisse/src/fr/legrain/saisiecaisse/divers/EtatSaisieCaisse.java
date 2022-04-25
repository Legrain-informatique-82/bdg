package fr.legrain.saisiecaisse.divers;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


import fr.legrain.gestCom.Appli.AbstractApplicationDAO;
import fr.legrain.saisieCaisse.dao.TaAchatTTC;
import fr.legrain.saisieCaisse.dao.TaCriteresSaisieCaisse;
import fr.legrain.saisieCaisse.dao.TaDepot;
import fr.legrain.saisieCaisse.dao.TaDepotDAO;
import fr.legrain.saisieCaisse.dao.TaEtablissement;
import fr.legrain.saisieCaisse.dao.TaOperation;
import fr.legrain.saisieCaisse.dao.TaOperationDAO;
import fr.legrain.saisieCaisse.dao.TaReportTPaiementDAO;
import fr.legrain.saisieCaisse.dao.TaSumDepot;
import fr.legrain.saisieCaisse.dao.TaSumOperation;
import fr.legrain.saisiecaisse.saisieCaissePlugin;
import fr.legrain.saisiecaisse.preferences.PreferenceConstants;




public class EtatSaisieCaisse {
    private TaOperationDAO operationDAO = new TaOperationDAO();
    private TaDepotDAO depotDAO = new TaDepotDAO();
	private List<TaOperation>  listeEtatSaisieCaisse = new ArrayList<TaOperation>(0);
//	List<TaOperation>  listeEtatStockFinal = new ArrayList<TaOperation>();
	
	public List<TaOperation> calculEtatSaisieCaisse(TaCriteresSaisieCaisse criteres){
		String fixeAchat=saisieCaissePlugin.getDefault().getPreferenceStore().getString(PreferenceConstants.FIXE_OP_ACHAT);
		return operationDAO.selectOperationDate(criteres,fixeAchat);
	}
	
	public List<TaSumOperation> calculEtatSaisieCaisseSynthese(TaCriteresSaisieCaisse criteres){
		String fixeAchat=saisieCaissePlugin.getDefault().getPreferenceStore().getString(PreferenceConstants.FIXE_OP_ACHAT);
		return operationDAO.selectSumOperationDate(criteres,fixeAchat);
	}
	
	public List<TaSumDepot> calculReportJournaux(Date dateDeb,Date datefin,String journalCaisse,TaEtablissement taEtablissement){		
		String fixeAchat=saisieCaissePlugin.getDefault().getPreferenceStore().getString(PreferenceConstants.FIXE_OP_ACHAT);
		return operationDAO.selectReportJournaux(dateDeb,datefin,journalCaisse,taEtablissement,fixeAchat);
	}

	
	public List<TaDepot> calculDepot(Date dateDeb,Date dateFin,String journalCaisse,TaEtablissement taEtablissement){
		return depotDAO.selectDepot(dateDeb,dateFin, journalCaisse, taEtablissement);		
	}
	
	public List<TaAchatTTC> calculAchatTTC(Date dateDeb,Date dateFin,TaEtablissement taEtablissement){
		String fixeAchat=saisieCaissePlugin.getDefault().getPreferenceStore().getString(PreferenceConstants.FIXE_OP_ACHAT);
		return operationDAO.selectAchat(dateDeb,dateFin,fixeAchat,taEtablissement);		
	}

}
