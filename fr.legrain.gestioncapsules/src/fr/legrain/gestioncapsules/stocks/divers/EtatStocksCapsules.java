package fr.legrain.gestioncapsules.stocks.divers;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import fr.legrain.documents.dao.TaLAvoirDAO;
import fr.legrain.documents.dao.TaLBonlivDAO;
import fr.legrain.documents.dao.TaLFactureDAO;
import fr.legrain.gestCom.Appli.AbstractApplicationDAO;
import fr.legrain.gestioncapsules.dao.TaEtatStockCapsules;
import fr.legrain.gestioncapsules.dao.TaReportStockCapsulesDAO;
import fr.legrain.gestioncapsules.dao.TaStockCapsulesDAO;

public class EtatStocksCapsules {
    private TaStockCapsulesDAO dao = new TaStockCapsulesDAO();
	private List<TaEtatStockCapsules>  listeEtatStock = new ArrayList<TaEtatStockCapsules>(0);
	List<TaEtatStockCapsules>  listeEtatStockFinal = new ArrayList<TaEtatStockCapsules>();
	
	public List<TaEtatStockCapsules> calculEtatStocks(TaEtatStockCapsules criteres,Date dateDeb, boolean strict){
		return calculEtatStocks(criteres, dateDeb, strict, false);
	}
	
	public List<TaEtatStockCapsules> calculEtatStocks(TaEtatStockCapsules criteres,Date dateDeb, boolean strict, boolean synthese){
		//LinkedList<SWTStocks> listObject = new LinkedList<SWTStocks>();
		List<TaEtatStockCapsules>  listeEtatStockTemp = new ArrayList<TaEtatStockCapsules>();
		
		listeEtatStockTemp =dao.selectSumMouvEntrees(criteres,dateDeb,strict);
		if(listeEtatStockTemp!=null)
			for (TaEtatStockCapsules taStock : listeEtatStockTemp) {
				listeEtatStock.add(taStock);
			}
		listeEtatStockTemp.clear();
		listeEtatStockTemp =	dao.selectSumMouvSorties(criteres,dateDeb,strict);
		if(listeEtatStockTemp!=null)
			for (TaEtatStockCapsules taStock : listeEtatStockTemp) {
				listeEtatStock.add(taStock);
			}


		AbstractApplicationDAO taLDocumentDAO = null;
		taLDocumentDAO = new TaLFactureDAO();
		//TaSumMouvDocument
		listeEtatStockTemp.clear();
		listeEtatStockTemp =((TaLFactureDAO)taLDocumentDAO).selectSumMouvDocumentStocksCapsulesEntree(criteres,dateDeb,strict);
		if(listeEtatStockTemp!=null)
			for (TaEtatStockCapsules taStock : listeEtatStockTemp) {
				listeEtatStock.add(taStock);
			}
		listeEtatStockTemp.clear();
		listeEtatStockTemp =((TaLFactureDAO)taLDocumentDAO).selectSumMouvDocumentStocksCapsulesSorties(criteres,dateDeb,strict);
		if(listeEtatStockTemp!=null)
			for (TaEtatStockCapsules taStock : listeEtatStockTemp) {
				listeEtatStock.add(taStock);
			}
		
//		taLDocumentDAO = new TaLBonlivDAO();
//		listeEtatStockTemp.clear();
//		listeEtatStockTemp =((TaLBonlivDAO)taLDocumentDAO).selectSumMouvDocumentCapsulesEntrees(criteres,dateDeb);
//		if(listeEtatStockTemp!=null)
//			for (TaEtatStockCapsules taStock : listeEtatStockTemp) {
//				listeEtatStock.add(taStock);
//			}
//		listeEtatStockTemp.clear();
//		listeEtatStockTemp =((TaLBonlivDAO)taLDocumentDAO).selectSumMouvDocumentCapsulesSorties(criteres,dateDeb);
//		if(listeEtatStockTemp!=null)
//			for (TaEtatStockCapsules taStock : listeEtatStockTemp) {
//				listeEtatStock.add(taStock);
//			}
		
		taLDocumentDAO = new TaLAvoirDAO();
		listeEtatStockTemp.clear();
		listeEtatStockTemp =((TaLAvoirDAO)taLDocumentDAO).selectSumMouvDocumentStocksCapsulesEntrees(criteres,dateDeb,strict);
		if(listeEtatStockTemp!=null)
			for (TaEtatStockCapsules taStock : listeEtatStockTemp) {
				listeEtatStock.add(taStock);
			}
		listeEtatStockTemp.clear();
		listeEtatStockTemp =((TaLAvoirDAO)taLDocumentDAO).selectSumMouvDocumentStocksCapsulesSorties(criteres,dateDeb,strict);
		if(listeEtatStockTemp!=null)
			for (TaEtatStockCapsules taStock : listeEtatStockTemp) {
				listeEtatStock.add(taStock);
			}
		
		
		taLDocumentDAO = new TaReportStockCapsulesDAO();
		listeEtatStockTemp.clear();
		listeEtatStockTemp =((TaReportStockCapsulesDAO)taLDocumentDAO).selectReportStocks(criteres);
		if(listeEtatStockTemp!=null)
			for (TaEtatStockCapsules taStock : listeEtatStockTemp) {
				listeEtatStock.add(taStock);
			}

		
		for (TaEtatStockCapsules taEtatStock : listeEtatStock) {
			int rang = -1;
			if(synthese) {
				rang =existeArticle(taEtatStock);
			}
			//int rang =existeArticleUnite(taEtatStock);
			if(rang==-1)
				listeEtatStockFinal.add(taEtatStock);
			else {
				TaEtatStockCapsules tmp = listeEtatStockFinal.get(rang);
				if(tmp.getQteCapsuleStock()==null && taEtatStock.getQteCapsuleStock()!=null)tmp.setQteCapsuleStock(taEtatStock.getQteCapsuleStock());
				else
				if(taEtatStock.getQteCapsuleStock()!=null)
					tmp.setQteCapsuleStock(tmp.getQteCapsuleStock().add(taEtatStock.getQteCapsuleStock()));
			}
		}
		
//		for (TaEtatStockCapsules taStock : listeEtatStockFinal) {
//			SWTStocks S =new SWTStocks();
//			mapperModelToUIEtatStock.map(((TaEtatStockCapsules)taStock),S);
//			listObject.add(S);
//		}
		return listeEtatStockFinal;
	}
	
	
	public List<TaEtatStockCapsules> calculEtatStocksMouvements(TaEtatStockCapsules criteres,Date dateDeb, boolean strict){
		//LinkedList<SWTStocks> listObject = new LinkedList<SWTStocks>();
		List<TaEtatStockCapsules>  listeEtatStockTemp = new ArrayList<TaEtatStockCapsules>();
		
		listeEtatStockTemp =dao.selectSumMouvEntrees(criteres,dateDeb,strict);
		if(listeEtatStockTemp!=null)
			for (TaEtatStockCapsules taStock : listeEtatStockTemp) {
				listeEtatStock.add(taStock);
			}
		listeEtatStockTemp.clear();
		listeEtatStockTemp =	dao.selectSumMouvSorties(criteres,dateDeb,strict);
		if(listeEtatStockTemp!=null)
			for (TaEtatStockCapsules taStock : listeEtatStockTemp) {
				listeEtatStock.add(taStock);
			}


		AbstractApplicationDAO taLDocumentDAO = null;
		taLDocumentDAO = new TaLFactureDAO();
		//TaSumMouvDocument
		listeEtatStockTemp.clear();
		listeEtatStockTemp =((TaLFactureDAO)taLDocumentDAO).selectSumMouvDocumentStocksCapsulesEntree(criteres,dateDeb,strict);
		if(listeEtatStockTemp!=null)
			for (TaEtatStockCapsules taStock : listeEtatStockTemp) {
				listeEtatStock.add(taStock);
			}
		listeEtatStockTemp.clear();
		listeEtatStockTemp =((TaLFactureDAO)taLDocumentDAO).selectSumMouvDocumentStocksCapsulesSorties(criteres,dateDeb,strict);
		if(listeEtatStockTemp!=null)
			for (TaEtatStockCapsules taStock : listeEtatStockTemp) {
				listeEtatStock.add(taStock);
			}
		
//		taLDocumentDAO = new TaLBonlivDAO();
//		listeEtatStockTemp.clear();
//		listeEtatStockTemp =((TaLBonlivDAO)taLDocumentDAO).selectSumMouvDocumentCapsulesEntrees(criteres,dateDeb);
//		if(listeEtatStockTemp!=null)
//			for (TaEtatStockCapsules taStock : listeEtatStockTemp) {
//				listeEtatStock.add(taStock);
//			}
//		listeEtatStockTemp.clear();
//		listeEtatStockTemp =((TaLBonlivDAO)taLDocumentDAO).selectSumMouvDocumentCapsulesSorties(criteres,dateDeb);
//		if(listeEtatStockTemp!=null)
//			for (TaEtatStockCapsules taStock : listeEtatStockTemp) {
//				listeEtatStock.add(taStock);
//			}
		
		
		taLDocumentDAO = new TaLAvoirDAO();
		listeEtatStockTemp.clear();
		listeEtatStockTemp =((TaLAvoirDAO)taLDocumentDAO).selectSumMouvDocumentStocksCapsulesEntrees(criteres,dateDeb,strict);
		if(listeEtatStockTemp!=null)
			for (TaEtatStockCapsules taStock : listeEtatStockTemp) {
				listeEtatStock.add(taStock);
			}
		listeEtatStockTemp.clear();
		listeEtatStockTemp =((TaLAvoirDAO)taLDocumentDAO).selectSumMouvDocumentStocksCapsulesSorties(criteres,dateDeb,strict);
		if(listeEtatStockTemp!=null)
			for (TaEtatStockCapsules taStock : listeEtatStockTemp) {
				listeEtatStock.add(taStock);
			}
		
		
		taLDocumentDAO = new TaReportStockCapsulesDAO();
		listeEtatStockTemp.clear();
		listeEtatStockTemp =((TaReportStockCapsulesDAO)taLDocumentDAO).selectReportStocks(criteres);
		if(listeEtatStockTemp!=null)
			for (TaEtatStockCapsules taStock : listeEtatStockTemp) {
				listeEtatStock.add(taStock);
			}

		
		for (TaEtatStockCapsules taEtatStock : listeEtatStock) {
			int rang =-1;
			if(rang==-1)
				listeEtatStockFinal.add(taEtatStock);
			else {
//				TaEtatStockCapsules tmp = listeEtatStockFinal.get(rang);
//				if(tmp.getQte1Stock()==null && taEtatStock.getQte1Stock()!=null)tmp.setQte1Stock(taEtatStock.getQte1Stock());
//				else
//				if(taEtatStock.getQte1Stock()!=null)
//					tmp.setQte1Stock(tmp.getQte1Stock().add(taEtatStock.getQte1Stock()));
//				if(tmp.getQte2Stock()==null && taEtatStock.getQte2Stock()!=null)tmp.setQte2Stock(taEtatStock.getQte2Stock());
//				else
//				if(taEtatStock.getQte2Stock()!=null && tmp.getQte2Stock()!=null)
//					tmp.setQte2Stock(tmp.getQte2Stock().add(taEtatStock.getQte2Stock()));
			}
		}
		
//		for (TaEtatStockCapsules taStock : listeEtatStockFinal) {
//			SWTStocks S =new SWTStocks();
//			mapperModelToUIEtatStock.map(((TaEtatStockCapsules)taStock),S);
//			listObject.add(S);
//		}
		return listeEtatStockFinal;
	}
	
	private int existeArticleUnite(TaEtatStockCapsules taEtatStock){
		int i =0;
		for (TaEtatStockCapsules e : listeEtatStockFinal) {
			if(e.equalsPartiel(taEtatStock))
				return i;
			i++;
		}
		return -1;
	}
	
	private int existeArticle(TaEtatStockCapsules taEtatStock){
		int i =0;
		for (TaEtatStockCapsules e : listeEtatStockFinal) {
			if(e.equalsPartielCodeArticle(taEtatStock))
				return i;
			i++;
		}
		return -1;
	}
	
	private Date recupDerniereDateCalcul(){
		return new TaReportStockCapsulesDAO().recupDerniereDateReportStock();	
	}
	
	public List<TaEtatStockCapsules> calculEtatStocksReport(TaEtatStockCapsules criteres){
		List<TaEtatStockCapsules>  listeEtatStockTemp = new ArrayList<TaEtatStockCapsules>();
		AbstractApplicationDAO taLDocumentDAO = null;
		taLDocumentDAO = new TaReportStockCapsulesDAO();
		listeEtatStockTemp.clear();
		listeEtatStockTemp =((TaReportStockCapsulesDAO)taLDocumentDAO).selectReportStocks(criteres);
		if(listeEtatStockTemp!=null)
			for (TaEtatStockCapsules taStock : listeEtatStockTemp) {
				listeEtatStock.add(taStock);
			}

		
		for (TaEtatStockCapsules taEtatStock : listeEtatStock) {
			int rang =existeArticleUnite(taEtatStock);
			if(rang==-1)
				listeEtatStockFinal.add(taEtatStock);
			else {
//				TaEtatStockCapsules tmp = listeEtatStockFinal.get(rang);
//				if(tmp.getQte1Stock()==null && taEtatStock.getQte1Stock()!=null)tmp.setQte1Stock(taEtatStock.getQte1Stock());
//				else
//				if(taEtatStock.getQte1Stock()!=null)
//					tmp.setQte1Stock(tmp.getQte1Stock().add(taEtatStock.getQte1Stock()));
//				if(tmp.getQte2Stock()==null && taEtatStock.getQte2Stock()!=null)tmp.setQte2Stock(taEtatStock.getQte2Stock());
//				else
//				if(taEtatStock.getQte2Stock()!=null && tmp.getQte2Stock()!=null)
//					tmp.setQte2Stock(tmp.getQte2Stock().add(taEtatStock.getQte2Stock()));
			}
		}
		
		return listeEtatStockFinal;
	}

}
