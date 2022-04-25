package fr.legrain.stocks.divers;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import fr.legrain.documents.dao.TaLApporteurDAO;
import fr.legrain.documents.dao.TaLAvoirDAO;
import fr.legrain.documents.dao.TaLBonlivDAO;
import fr.legrain.documents.dao.TaLFactureDAO;
import fr.legrain.gestCom.Appli.AbstractApplicationDAO;
import fr.legrain.stocks.dao.TaEtatStock;
import fr.legrain.stocks.dao.TaReportStockDAO;
import fr.legrain.stocks.dao.TaStockDAO;


public class EtatStocks {
    private TaStockDAO dao = new TaStockDAO();
	private List<TaEtatStock>  listeEtatStock = new ArrayList<TaEtatStock>(0);
	List<TaEtatStock>  listeEtatStockFinal = new ArrayList<TaEtatStock>();
	
	public List<TaEtatStock> calculEtatStocks(TaEtatStock criteres,Date dateDeb){
		//LinkedList<SWTStocks> listObject = new LinkedList<SWTStocks>();
		List<TaEtatStock>  listeEtatStockTemp = new ArrayList<TaEtatStock>();
		
		listeEtatStockTemp =dao.selectSumMouvEntrees(criteres,dateDeb);
		if(listeEtatStockTemp!=null)
			for (TaEtatStock taStock : listeEtatStockTemp) {
				listeEtatStock.add(taStock);
			}
		listeEtatStockTemp.clear();
		listeEtatStockTemp =	dao.selectSumMouvSorties(criteres,dateDeb);
		if(listeEtatStockTemp!=null)
			for (TaEtatStock taStock : listeEtatStockTemp) {
				listeEtatStock.add(taStock);
			}


		AbstractApplicationDAO taLDocumentDAO = null;
		taLDocumentDAO = new TaLFactureDAO();
		//TaSumMouvDocument
		listeEtatStockTemp.clear();
		listeEtatStockTemp =((TaLFactureDAO)taLDocumentDAO).selectSumMouvDocumentStocksEntree(criteres,dateDeb);
		if(listeEtatStockTemp!=null)
			for (TaEtatStock taStock : listeEtatStockTemp) {
				listeEtatStock.add(taStock);
			}
		listeEtatStockTemp.clear();
		listeEtatStockTemp =((TaLFactureDAO)taLDocumentDAO).selectSumMouvDocumentStocksSorties(criteres,dateDeb);
		if(listeEtatStockTemp!=null)
			for (TaEtatStock taStock : listeEtatStockTemp) {
				listeEtatStock.add(taStock);
			}
		
		taLDocumentDAO = new TaLBonlivDAO();
		listeEtatStockTemp.clear();
		listeEtatStockTemp =((TaLBonlivDAO)taLDocumentDAO).selectSumMouvDocumentEntrees(criteres,dateDeb);
		if(listeEtatStockTemp!=null)
			for (TaEtatStock taStock : listeEtatStockTemp) {
				listeEtatStock.add(taStock);
			}
		listeEtatStockTemp.clear();
		listeEtatStockTemp =((TaLBonlivDAO)taLDocumentDAO).selectSumMouvDocumentSorties(criteres,dateDeb);
		if(listeEtatStockTemp!=null)
			for (TaEtatStock taStock : listeEtatStockTemp) {
				listeEtatStock.add(taStock);
			}
		
		taLDocumentDAO = new TaLAvoirDAO();
		listeEtatStockTemp.clear();
		listeEtatStockTemp =((TaLAvoirDAO)taLDocumentDAO).selectSumMouvDocumentStocksEntrees(criteres,dateDeb);
		if(listeEtatStockTemp!=null)
			for (TaEtatStock taStock : listeEtatStockTemp) {
				listeEtatStock.add(taStock);
			}
		listeEtatStockTemp.clear();
		listeEtatStockTemp =((TaLAvoirDAO)taLDocumentDAO).selectSumMouvDocumentStocksSorties(criteres,dateDeb);
		if(listeEtatStockTemp!=null)
			for (TaEtatStock taStock : listeEtatStockTemp) {
				listeEtatStock.add(taStock);
			}
		
		taLDocumentDAO = new TaLApporteurDAO();
		listeEtatStockTemp.clear();
		listeEtatStockTemp =((TaLApporteurDAO)taLDocumentDAO).selectSumMouvDocumentStocksEntrees(criteres,dateDeb);
		if(listeEtatStockTemp!=null)
			for (TaEtatStock taStock : listeEtatStockTemp) {
				listeEtatStock.add(taStock);
			}
		listeEtatStockTemp.clear();
		listeEtatStockTemp =((TaLApporteurDAO)taLDocumentDAO).selectSumMouvDocumentStocksSorties(criteres,dateDeb);
		if(listeEtatStockTemp!=null)
			for (TaEtatStock taStock : listeEtatStockTemp) {
				listeEtatStock.add(taStock);
			}
		
		
		taLDocumentDAO = new TaReportStockDAO();
		listeEtatStockTemp.clear();
		listeEtatStockTemp =((TaReportStockDAO)taLDocumentDAO).selectReportStocks(criteres);
		if(listeEtatStockTemp!=null)
			for (TaEtatStock taStock : listeEtatStockTemp) {
				listeEtatStock.add(taStock);
			}

		
		for (TaEtatStock taEtatStock : listeEtatStock) {
			int rang =existeArticleUnite(taEtatStock);
			if(rang==-1)
				listeEtatStockFinal.add(taEtatStock);
			else {
				TaEtatStock tmp = listeEtatStockFinal.get(rang);
				if(tmp.getQte1Stock()==null && taEtatStock.getQte1Stock()!=null)tmp.setQte1Stock(taEtatStock.getQte1Stock());
				else
				if(taEtatStock.getQte1Stock()!=null)
					tmp.setQte1Stock(tmp.getQte1Stock().add(taEtatStock.getQte1Stock()));
				if(tmp.getQte2Stock()==null && taEtatStock.getQte2Stock()!=null)tmp.setQte2Stock(taEtatStock.getQte2Stock());
				else
				if(taEtatStock.getQte2Stock()!=null && tmp.getQte2Stock()!=null)
					tmp.setQte2Stock(tmp.getQte2Stock().add(taEtatStock.getQte2Stock()));
			}
		}
		
//		for (TaEtatStock taStock : listeEtatStockFinal) {
//			SWTStocks S =new SWTStocks();
//			mapperModelToUIEtatStock.map(((TaEtatStock)taStock),S);
//			listObject.add(S);
//		}
		return listeEtatStockFinal;
	}
	
	
	public List<TaEtatStock> calculEtatStocksMouvements(TaEtatStock criteres,Date dateDeb){
		//LinkedList<SWTStocks> listObject = new LinkedList<SWTStocks>();
		List<TaEtatStock>  listeEtatStockTemp = new ArrayList<TaEtatStock>();
		
		listeEtatStockTemp =dao.selectSumMouvEntrees(criteres,dateDeb);
		if(listeEtatStockTemp!=null)
			for (TaEtatStock taStock : listeEtatStockTemp) {
				listeEtatStock.add(taStock);
			}
		listeEtatStockTemp.clear();
		listeEtatStockTemp =	dao.selectSumMouvSorties(criteres,dateDeb);
		if(listeEtatStockTemp!=null)
			for (TaEtatStock taStock : listeEtatStockTemp) {
				listeEtatStock.add(taStock);
			}


		AbstractApplicationDAO taLDocumentDAO = null;
		taLDocumentDAO = new TaLFactureDAO();
		//TaSumMouvDocument
		listeEtatStockTemp.clear();
		listeEtatStockTemp =((TaLFactureDAO)taLDocumentDAO).selectSumMouvDocumentStocksEntree(criteres,dateDeb);
		if(listeEtatStockTemp!=null)
			for (TaEtatStock taStock : listeEtatStockTemp) {
				listeEtatStock.add(taStock);
			}
		listeEtatStockTemp.clear();
		listeEtatStockTemp =((TaLFactureDAO)taLDocumentDAO).selectSumMouvDocumentStocksSorties(criteres,dateDeb);
		if(listeEtatStockTemp!=null)
			for (TaEtatStock taStock : listeEtatStockTemp) {
				listeEtatStock.add(taStock);
			}
		
		taLDocumentDAO = new TaLBonlivDAO();
		listeEtatStockTemp.clear();
		listeEtatStockTemp =((TaLBonlivDAO)taLDocumentDAO).selectSumMouvDocumentEntrees(criteres,dateDeb);
		if(listeEtatStockTemp!=null)
			for (TaEtatStock taStock : listeEtatStockTemp) {
				listeEtatStock.add(taStock);
			}
		listeEtatStockTemp.clear();
		listeEtatStockTemp =((TaLBonlivDAO)taLDocumentDAO).selectSumMouvDocumentSorties(criteres,dateDeb);
		if(listeEtatStockTemp!=null)
			for (TaEtatStock taStock : listeEtatStockTemp) {
				listeEtatStock.add(taStock);
			}
		
		
		taLDocumentDAO = new TaLAvoirDAO();
		listeEtatStockTemp.clear();
		listeEtatStockTemp =((TaLAvoirDAO)taLDocumentDAO).selectSumMouvDocumentStocksEntrees(criteres,dateDeb);
		if(listeEtatStockTemp!=null)
			for (TaEtatStock taStock : listeEtatStockTemp) {
				listeEtatStock.add(taStock);
			}
		listeEtatStockTemp.clear();
		listeEtatStockTemp =((TaLAvoirDAO)taLDocumentDAO).selectSumMouvDocumentStocksSorties(criteres,dateDeb);
		if(listeEtatStockTemp!=null)
			for (TaEtatStock taStock : listeEtatStockTemp) {
				listeEtatStock.add(taStock);
			}
		
		taLDocumentDAO = new TaLApporteurDAO();
		listeEtatStockTemp.clear();
		listeEtatStockTemp =((TaLApporteurDAO)taLDocumentDAO).selectSumMouvDocumentStocksEntrees(criteres,dateDeb);
		if(listeEtatStockTemp!=null)
			for (TaEtatStock taStock : listeEtatStockTemp) {
				listeEtatStock.add(taStock);
			}
		listeEtatStockTemp.clear();
		listeEtatStockTemp =((TaLApporteurDAO)taLDocumentDAO).selectSumMouvDocumentStocksSorties(criteres,dateDeb);
		if(listeEtatStockTemp!=null)
			for (TaEtatStock taStock : listeEtatStockTemp) {
				listeEtatStock.add(taStock);
			}
				
		
		taLDocumentDAO = new TaReportStockDAO();
		listeEtatStockTemp.clear();
		listeEtatStockTemp =((TaReportStockDAO)taLDocumentDAO).selectReportStocks(criteres);
		if(listeEtatStockTemp!=null)
			for (TaEtatStock taStock : listeEtatStockTemp) {
				listeEtatStock.add(taStock);
			}

		
		for (TaEtatStock taEtatStock : listeEtatStock) {
			int rang =-1;
			if(rang==-1)
				listeEtatStockFinal.add(taEtatStock);
			else {
				TaEtatStock tmp = listeEtatStockFinal.get(rang);
				if(tmp.getQte1Stock()==null && taEtatStock.getQte1Stock()!=null)tmp.setQte1Stock(taEtatStock.getQte1Stock());
				else
				if(taEtatStock.getQte1Stock()!=null)
					tmp.setQte1Stock(tmp.getQte1Stock().add(taEtatStock.getQte1Stock()));
				if(tmp.getQte2Stock()==null && taEtatStock.getQte2Stock()!=null)tmp.setQte2Stock(taEtatStock.getQte2Stock());
				else
				if(taEtatStock.getQte2Stock()!=null && tmp.getQte2Stock()!=null)
					tmp.setQte2Stock(tmp.getQte2Stock().add(taEtatStock.getQte2Stock()));
			}
		}
		
//		for (TaEtatStock taStock : listeEtatStockFinal) {
//			SWTStocks S =new SWTStocks();
//			mapperModelToUIEtatStock.map(((TaEtatStock)taStock),S);
//			listObject.add(S);
//		}
		return listeEtatStockFinal;
	}
	
	private int existeArticleUnite(TaEtatStock taEtatStock){
		int i =0;
		for (TaEtatStock e : listeEtatStockFinal) {
			if(e.equalsPartiel(taEtatStock))
				return i;
			i++;
		}
		return -1;
	}
	
	private Date recupDerniereDateCalcul(){
		return new TaReportStockDAO().recupDerniereDateReportStock();	
	}
	
	public List<TaEtatStock> calculEtatStocksReport(TaEtatStock criteres){
		List<TaEtatStock>  listeEtatStockTemp = new ArrayList<TaEtatStock>();
		AbstractApplicationDAO taLDocumentDAO = null;
		taLDocumentDAO = new TaReportStockDAO();
		listeEtatStockTemp.clear();
		listeEtatStockTemp =((TaReportStockDAO)taLDocumentDAO).selectReportStocks(criteres);
		if(listeEtatStockTemp!=null)
			for (TaEtatStock taStock : listeEtatStockTemp) {
				listeEtatStock.add(taStock);
			}

		
		for (TaEtatStock taEtatStock : listeEtatStock) {
			int rang =existeArticleUnite(taEtatStock);
			if(rang==-1)
				listeEtatStockFinal.add(taEtatStock);
			else {
				TaEtatStock tmp = listeEtatStockFinal.get(rang);
				if(tmp.getQte1Stock()==null && taEtatStock.getQte1Stock()!=null)tmp.setQte1Stock(taEtatStock.getQte1Stock());
				else
				if(taEtatStock.getQte1Stock()!=null)
					tmp.setQte1Stock(tmp.getQte1Stock().add(taEtatStock.getQte1Stock()));
				if(tmp.getQte2Stock()==null && taEtatStock.getQte2Stock()!=null)tmp.setQte2Stock(taEtatStock.getQte2Stock());
				else
				if(taEtatStock.getQte2Stock()!=null && tmp.getQte2Stock()!=null)
					tmp.setQte2Stock(tmp.getQte2Stock().add(taEtatStock.getQte2Stock()));
			}
		}
		
		return listeEtatStockFinal;
	}

}
