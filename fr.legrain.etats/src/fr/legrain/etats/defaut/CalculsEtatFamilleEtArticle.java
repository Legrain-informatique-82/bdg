package fr.legrain.etats.defaut;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.math.MathContext;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.apache.log4j.Logger;
import org.eclipse.core.runtime.Platform;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.ui.PlatformUI;

import fr.legrain.articles.dao.TaArticle;
import fr.legrain.articles.dao.TaArticleDAO;
import fr.legrain.articles.dao.TaFamille;
import fr.legrain.articles.dao.TaFamilleDAO;
import fr.legrain.articles.dao.TaTva;
import fr.legrain.dms.dao.ITaEtatDms;
import fr.legrain.dms.dao.TaEtatMouvementDms;
import fr.legrain.documents.dao.TaLAvoirDAO;
import fr.legrain.documents.dao.TaLFactureDAO;
import fr.legrain.gestCom.Appli.AbstractApplicationDAO;
import fr.legrain.lib.data.LibChaine;
import fr.legrain.stocks.dao.TaStockDAO;


public class CalculsEtatFamilleEtArticle {
	
	static Logger logger = Logger.getLogger(CalculsEtatFamilleEtArticle.class);
	
	public static final int DETAIL = 1;
	public static final int SYNTHESE = 2;
	public static final int SYNTHESE_ET_ARTICLE = 3;
	
	private TaStockDAO dao = new TaStockDAO();
	private List<TaEtatMouvementDms> listeEtatMouvement = new ArrayList<TaEtatMouvementDms>(0);
	private List<TaEtatMouvementDms> listeEtatFinalMouvement = new ArrayList<TaEtatMouvementDms>();

	private List<TaEtatMouvementDms> listeEtatReport = new ArrayList<TaEtatMouvementDms>(0);
	private List<TaEtatMouvementDms> listeEtatFinalReport = new ArrayList<TaEtatMouvementDms>();

	private List<TaEtatMouvementDms> listeEtatFin = new ArrayList<TaEtatMouvementDms>(0);
	private List<TaEtatMouvementDms> listeEtatFinalFin = new ArrayList<TaEtatMouvementDms>();


	public List<TaEtatMouvementDms> calculEtatFamille(TaEtatMouvementDms criteres,int synthese){
		listeEtatFinalMouvement.clear();
		listeEtatMouvement.clear();
		
		List<TaEtatMouvementDms>  listeEtatTemp = new ArrayList<TaEtatMouvementDms>();
		List<TaEtatMouvementDms>  listeEtatFinalMouvementLocal= new ArrayList<TaEtatMouvementDms>();

		listeEtatTemp.clear();

		AbstractApplicationDAO taLDocumentDAO = null;
		taLDocumentDAO = new TaLFactureDAO();
		
		listeEtatTemp.clear();
		if(synthese == SYNTHESE
				|| synthese == SYNTHESE_ET_ARTICLE)
			listeEtatTemp =((TaLFactureDAO)taLDocumentDAO).selectSumSyntheseMouvEtatFamille(criteres);
		else
			listeEtatTemp =((TaLFactureDAO)taLDocumentDAO).selectSumMouvementEtatFamille(criteres);
		if(listeEtatTemp!=null)
			for (TaEtatMouvementDms taEtat : listeEtatTemp) {
				listeEtatFinalMouvementLocal.add(taEtat);
			}
		taLDocumentDAO = new TaLAvoirDAO();
		listeEtatTemp.clear();
		if(synthese == SYNTHESE
				|| synthese == SYNTHESE_ET_ARTICLE)
			listeEtatTemp =((TaLAvoirDAO)taLDocumentDAO).selectSumSyntheseMouvEtatFamille(criteres);
		else 
			listeEtatTemp =((TaLAvoirDAO)taLDocumentDAO).selectSumMouvementEtatFamille(criteres);
		if(listeEtatTemp!=null)
			for (TaEtatMouvementDms taEtat : listeEtatTemp) {
				listeEtatFinalMouvementLocal.add(taEtat);
			}

		if(synthese == SYNTHESE
				|| synthese == SYNTHESE_ET_ARTICLE) {

			for (ITaEtatDms taEtat : listeEtatFinalMouvementLocal) {
				int rang = 0;
				if(synthese == SYNTHESE)
					rang = ((TaEtatMouvementDms)taEtat).existeFamilleUnite(taEtat,listeEtatMouvement);
				else if(synthese == SYNTHESE_ET_ARTICLE) {
					rang = ((TaEtatMouvementDms)taEtat).existeFamilleUniteArticle(taEtat,listeEtatMouvement);
				}
				if(rang==-1){
					listeEtatMouvement.add((TaEtatMouvementDms)taEtat);
				}
				else{
					TaEtatMouvementDms tmp = listeEtatMouvement.get(rang);
					if(((TaEtatMouvementDms)taEtat).getQte1()!=null && tmp.getQte1()!=null)
						tmp.setQte1(tmp.getQte1().add(((TaEtatMouvementDms)taEtat).getQte1()));
					if(((TaEtatMouvementDms)taEtat).getQte2()!=null && tmp.getQte2()!=null)
						tmp.setQte2(tmp.getQte2().add(((TaEtatMouvementDms)taEtat).getQte2()));

					if(((TaEtatMouvementDms)taEtat).getHt()!=null && tmp.getHt()!=null)
						tmp.setHt(tmp.getHt().add(((TaEtatMouvementDms)taEtat).getHt()));
					if(((TaEtatMouvementDms)taEtat).getTva()!=null && tmp.getTva()!=null)
						tmp.setTva(tmp.getTva().add(((TaEtatMouvementDms)taEtat).getTva()));
					if(((TaEtatMouvementDms)taEtat).getTtc()!=null && tmp.getTtc()!=null)
						tmp.setTtc(tmp.getTtc().add(((TaEtatMouvementDms)taEtat).getTtc()));
				}
			}

			listeEtatFinalMouvement.addAll(listeEtatMouvement);

			for (TaEtatMouvementDms taEtat : listeEtatFinalMouvement) {
				if(taEtat.getQte1()==null)taEtat.setQte1(new BigDecimal(0));
				if(taEtat.getQte1Report()==null)taEtat.setQte1Report(new BigDecimal(0));
				taEtat.setQte1Fin(taEtat.getQte1Report().add(taEtat.getQte1()));
				if(taEtat.getQte2()==null)taEtat.setQte2(new BigDecimal(0));
				if(taEtat.getQte2Report()==null)taEtat.setQte2Report(new BigDecimal(0));
				taEtat.setQte2Fin(taEtat.getQte2Report().add(taEtat.getQte2()));
			}
		}
		else listeEtatFinalMouvement.addAll(listeEtatFinalMouvementLocal);
		return listeEtatFinalMouvement;
	}


	public List<TaEtatMouvementDms> calculEtatArticle(TaEtatMouvementDms criteres,boolean synthese){
		listeEtatFinalMouvement.clear();
		listeEtatMouvement.clear();
		
		List<TaEtatMouvementDms>  listeEtatTemp = new ArrayList<TaEtatMouvementDms>();
		List<TaEtatMouvementDms>  listeEtatFinalMouvementLocal= new ArrayList<TaEtatMouvementDms>();
		TaArticleDAO articleDao = new TaArticleDAO();
		TaArticle article = null;
		listeEtatTemp.clear();

		AbstractApplicationDAO taLDocumentDAO = null;
		taLDocumentDAO = new TaLFactureDAO();

		listeEtatTemp.clear();
		if(synthese)
			listeEtatTemp =((TaLFactureDAO)taLDocumentDAO).selectSumSyntheseMouvEtatArticle(criteres);
		else
			listeEtatTemp =((TaLFactureDAO)taLDocumentDAO).selectSumMouvementEtatArticle(criteres);
//		debugList(listeEtatTemp);
		if(listeEtatTemp!=null)
			for (TaEtatMouvementDms taEtat : listeEtatTemp) {
				listeEtatFinalMouvementLocal.add(taEtat);
			}
		taLDocumentDAO = new TaLAvoirDAO();
		listeEtatTemp.clear();
		if(synthese)
			listeEtatTemp =((TaLAvoirDAO)taLDocumentDAO).selectSumSyntheseMouvEtatArticle(criteres);
		else 
			listeEtatTemp =((TaLAvoirDAO)taLDocumentDAO).selectSumMouvementEtatArticle(criteres);
		if(listeEtatTemp!=null)
			for (TaEtatMouvementDms taEtat : listeEtatTemp) {
				listeEtatFinalMouvementLocal.add(taEtat);

			}
		for (TaEtatMouvementDms taEtatMouvementDms : listeEtatFinalMouvementLocal) {
			if(taEtatMouvementDms.getCodeFamille()==null && article!=null && article.getTaFamille()!=null) {
				article=articleDao.findByCode(taEtatMouvementDms.getCodeArticle());
				taEtatMouvementDms.setCodeFamille(article.getTaFamille().getCodeFamille());	
			}

			if(taEtatMouvementDms.getLargeur()!=null && taEtatMouvementDms.getHauteur()!=null && taEtatMouvementDms.getPoids()!=null){
			taEtatMouvementDms.setDroitsAccises(taEtatMouvementDms.getHauteur().multiply((taEtatMouvementDms.getLargeur().
					divide(BigDecimal.valueOf(100),MathContext.DECIMAL128))).
					multiply(taEtatMouvementDms.getPoids()).multiply(taEtatMouvementDms.getQte1()).setScale(2,BigDecimal.ROUND_HALF_UP));
			}
		}
		if(synthese){
			//Boolean retour=false;
			for (ITaEtatDms taEtat : listeEtatFinalMouvementLocal) {
				//			if(taEtat.getCodeArticle().equalsIgnoreCase("DIVERS"))
				//				retour=true;
				int rang =((TaEtatMouvementDms)taEtat).existeArticleUnite(taEtat,listeEtatMouvement);
				if(rang==-1){
					listeEtatMouvement.add((TaEtatMouvementDms)taEtat);
				}
				else{
					TaEtatMouvementDms tmp = listeEtatMouvement.get(rang);
					if(((TaEtatMouvementDms)taEtat).getQte1()!=null && tmp.getQte1()!=null)
						tmp.setQte1(tmp.getQte1().add(((TaEtatMouvementDms)taEtat).getQte1()));
					if(((TaEtatMouvementDms)taEtat).getQte2()!=null && tmp.getQte2()!=null)
						tmp.setQte2(tmp.getQte2().add(((TaEtatMouvementDms)taEtat).getQte2()));

					if(((TaEtatMouvementDms)taEtat).getHt()!=null && tmp.getHt()!=null)
						tmp.setHt(tmp.getHt().add(((TaEtatMouvementDms)taEtat).getHt()));
					if(((TaEtatMouvementDms)taEtat).getTva()!=null && tmp.getTva()!=null)
						tmp.setTva(tmp.getTva().add(((TaEtatMouvementDms)taEtat).getTva()));
					if(((TaEtatMouvementDms)taEtat).getTtc()!=null && tmp.getTtc()!=null)
						tmp.setTtc(tmp.getTtc().add(((TaEtatMouvementDms)taEtat).getTtc()));
				}
			}

			listeEtatFinalMouvement.addAll(listeEtatMouvement);

			for (TaEtatMouvementDms taEtat : listeEtatFinalMouvement) {
				if(taEtat.getQte1()==null)taEtat.setQte1(new BigDecimal(0));
				if(taEtat.getQte1Report()==null)taEtat.setQte1Report(new BigDecimal(0));
				taEtat.setQte1Fin(taEtat.getQte1Report().add(taEtat.getQte1()));
				if(taEtat.getQte2()==null)taEtat.setQte2(new BigDecimal(0));
				if(taEtat.getQte2Report()==null)taEtat.setQte2Report(new BigDecimal(0));
				taEtat.setQte2Fin(taEtat.getQte2Report().add(taEtat.getQte2()));
			}
		}
		else listeEtatFinalMouvement.addAll(listeEtatFinalMouvementLocal);
		return listeEtatFinalMouvement;
	}
	
	public List<TaEtatMouvementDms> calculEtatTiers(TaEtatMouvementDms criteres,boolean synthese, boolean groupByTiers){
		listeEtatFinalMouvement.clear();
		listeEtatMouvement.clear();
		
		List<TaEtatMouvementDms>  listeEtatTemp = new ArrayList<TaEtatMouvementDms>();
		List<TaEtatMouvementDms>  listeEtatFinalMouvementLocal= new ArrayList<TaEtatMouvementDms>();
		TaArticleDAO articleDao = new TaArticleDAO();
		TaArticle article = null;
		listeEtatTemp.clear();

		AbstractApplicationDAO taLDocumentDAO = null;
		taLDocumentDAO = new TaLFactureDAO();

		listeEtatTemp.clear();
		if(synthese)
			listeEtatTemp =((TaLFactureDAO)taLDocumentDAO).selectSumSyntheseMouvEtatTiers(criteres);
		else
			listeEtatTemp =((TaLFactureDAO)taLDocumentDAO).selectSumMouvementEtatTiers(criteres);
		if(listeEtatTemp!=null)
			for (TaEtatMouvementDms taEtat : listeEtatTemp) {
				listeEtatFinalMouvementLocal.add(taEtat);
			}
		taLDocumentDAO = new TaLAvoirDAO();
		listeEtatTemp.clear();
		if(synthese)
			listeEtatTemp =((TaLAvoirDAO)taLDocumentDAO).selectSumSyntheseMouvEtatTiers(criteres);
		else 
			listeEtatTemp =((TaLAvoirDAO)taLDocumentDAO).selectSumMouvementEtatTiers(criteres);
		if(listeEtatTemp!=null)
			for (TaEtatMouvementDms taEtat : listeEtatTemp) {
				listeEtatFinalMouvementLocal.add(taEtat);

			}
		for (TaEtatMouvementDms taEtatMouvementDms : listeEtatFinalMouvementLocal) {
			article=articleDao.findByCode(taEtatMouvementDms.getCodeArticle());
			if(article!=null && article.getTaFamille()!=null)
				taEtatMouvementDms.setCodeFamille(article.getTaFamille().getCodeFamille());	
		}
		if(synthese){
			//Boolean retour=false;
			for (ITaEtatDms taEtat : listeEtatFinalMouvementLocal) {
				//			if(taEtat.getCodeArticle().equalsIgnoreCase("DIVERS"))
				//				retour=true;
				int rang = 0;
				if(groupByTiers) {
					rang = ((TaEtatMouvementDms)taEtat).existeTiers(taEtat,listeEtatMouvement);
				} else {
					rang =((TaEtatMouvementDms)taEtat).existeTiersEtArticleUnite(taEtat,listeEtatMouvement);
				}
				if(rang==-1){
					listeEtatMouvement.add((TaEtatMouvementDms)taEtat);
				}
				else{
					TaEtatMouvementDms tmp = listeEtatMouvement.get(rang);
					if(((TaEtatMouvementDms)taEtat).getQte1()!=null && tmp.getQte1()!=null)
						tmp.setQte1(tmp.getQte1().add(((TaEtatMouvementDms)taEtat).getQte1()));
					if(((TaEtatMouvementDms)taEtat).getQte2()!=null && tmp.getQte2()!=null)
						tmp.setQte2(tmp.getQte2().add(((TaEtatMouvementDms)taEtat).getQte2()));

					if(((TaEtatMouvementDms)taEtat).getHt()!=null && tmp.getHt()!=null)
						tmp.setHt(tmp.getHt().add(((TaEtatMouvementDms)taEtat).getHt()));
					if(((TaEtatMouvementDms)taEtat).getTva()!=null && tmp.getTva()!=null)
						tmp.setTva(tmp.getTva().add(((TaEtatMouvementDms)taEtat).getTva()));
					if(((TaEtatMouvementDms)taEtat).getTtc()!=null && tmp.getTtc()!=null)
						tmp.setTtc(tmp.getTtc().add(((TaEtatMouvementDms)taEtat).getTtc()));
				}
			}

			listeEtatFinalMouvement.addAll(listeEtatMouvement);

			for (TaEtatMouvementDms taEtat : listeEtatFinalMouvement) {
				if(taEtat.getQte1()==null)taEtat.setQte1(new BigDecimal(0));
				if(taEtat.getQte1Report()==null)taEtat.setQte1Report(new BigDecimal(0));
				taEtat.setQte1Fin(taEtat.getQte1Report().add(taEtat.getQte1()));
				if(taEtat.getQte2()==null)taEtat.setQte2(new BigDecimal(0));
				if(taEtat.getQte2Report()==null)taEtat.setQte2Report(new BigDecimal(0));
				taEtat.setQte2Fin(taEtat.getQte2Report().add(taEtat.getQte2()));
			}
		}
		else listeEtatFinalMouvement.addAll(listeEtatFinalMouvementLocal);
		return listeEtatFinalMouvement;
	}
	
	public List<TaEtatMouvementDms> calculEtatFamilleTiers(TaEtatMouvementDms criteres,boolean synthese, boolean groupByTiers){
		listeEtatFinalMouvement.clear();
		listeEtatMouvement.clear();
		
		List<TaEtatMouvementDms>  listeEtatTemp = new ArrayList<TaEtatMouvementDms>();
		List<TaEtatMouvementDms>  listeEtatFinalMouvementLocal= new ArrayList<TaEtatMouvementDms>();
		TaArticleDAO articleDao = new TaArticleDAO();
		TaArticle article = null;
		listeEtatTemp.clear();

		AbstractApplicationDAO taLDocumentDAO = null;
		taLDocumentDAO = new TaLFactureDAO();

		listeEtatTemp.clear();
		if(synthese)
			listeEtatTemp =((TaLFactureDAO)taLDocumentDAO).selectSumSyntheseMouvEtatFamilleTiers(criteres);
		else
			listeEtatTemp =((TaLFactureDAO)taLDocumentDAO).selectSumMouvementEtatFamilleTiers(criteres);
		if(listeEtatTemp!=null)
			for (TaEtatMouvementDms taEtat : listeEtatTemp) {
				listeEtatFinalMouvementLocal.add(taEtat);
			}
		taLDocumentDAO = new TaLAvoirDAO();
		listeEtatTemp.clear();
		if(synthese)
			listeEtatTemp =((TaLAvoirDAO)taLDocumentDAO).selectSumSyntheseMouvEtatFamilleTiers(criteres);
		else 
			listeEtatTemp =((TaLAvoirDAO)taLDocumentDAO).selectSumMouvementEtatFamilleTiers(criteres);
		if(listeEtatTemp!=null)
			for (TaEtatMouvementDms taEtat : listeEtatTemp) {
				listeEtatFinalMouvementLocal.add(taEtat);

			}
		for (TaEtatMouvementDms taEtatMouvementDms : listeEtatFinalMouvementLocal) {
			article=articleDao.findByCode(taEtatMouvementDms.getCodeArticle());
			if(article!=null && article.getTaFamille()!=null)
				taEtatMouvementDms.setCodeFamille(article.getTaFamille().getCodeFamille());	
		}
		if(synthese){
			//Boolean retour=false;
			for (ITaEtatDms taEtat : listeEtatFinalMouvementLocal) {
				//			if(taEtat.getCodeArticle().equalsIgnoreCase("DIVERS"))
				//				retour=true;
				int rang = 0;
				if(groupByTiers) {
					rang = ((TaEtatMouvementDms)taEtat).existeTiers(taEtat,listeEtatMouvement);
				} else {
					rang =((TaEtatMouvementDms)taEtat).existeFamilleTiers(taEtat,listeEtatMouvement);
				}
				if(rang==-1){
					listeEtatMouvement.add((TaEtatMouvementDms)taEtat);
					//System.err.println(((TaEtatMouvementDms)taEtat).getCodeFamilleTiers()+" * "+((TaEtatMouvementDms)taEtat).getCodeTiers() +" --N-- "+((TaEtatMouvementDms)taEtat).getQte1());
				}
				else{
					TaEtatMouvementDms tmp = listeEtatMouvement.get(rang);
					//System.err.println(((TaEtatMouvementDms)taEtat).getCodeFamilleTiers()+" * "+((TaEtatMouvementDms)taEtat).getCodeTiers() +" ---- "+tmp.getQte1()+" add "+((TaEtatMouvementDms)taEtat).getQte1()+" ==>"+tmp.getQte1().add(((TaEtatMouvementDms)taEtat).getQte1()) );
					if(((TaEtatMouvementDms)taEtat).getQte1()!=null && tmp.getQte1()!=null)
						tmp.setQte1(tmp.getQte1().add(((TaEtatMouvementDms)taEtat).getQte1()));
					if(((TaEtatMouvementDms)taEtat).getQte2()!=null && tmp.getQte2()!=null)
						tmp.setQte2(tmp.getQte2().add(((TaEtatMouvementDms)taEtat).getQte2()));

					if(((TaEtatMouvementDms)taEtat).getHt()!=null && tmp.getHt()!=null)
						tmp.setHt(tmp.getHt().add(((TaEtatMouvementDms)taEtat).getHt()));
					if(((TaEtatMouvementDms)taEtat).getTva()!=null && tmp.getTva()!=null)
						tmp.setTva(tmp.getTva().add(((TaEtatMouvementDms)taEtat).getTva()));
					if(((TaEtatMouvementDms)taEtat).getTtc()!=null && tmp.getTtc()!=null)
						tmp.setTtc(tmp.getTtc().add(((TaEtatMouvementDms)taEtat).getTtc()));
				}
			}

			listeEtatFinalMouvement.addAll(listeEtatMouvement);

			for (TaEtatMouvementDms taEtat : listeEtatFinalMouvement) {
				if(taEtat.getQte1()==null)taEtat.setQte1(new BigDecimal(0));
				if(taEtat.getQte1Report()==null)taEtat.setQte1Report(new BigDecimal(0));
				taEtat.setQte1Fin(taEtat.getQte1Report().add(taEtat.getQte1()));
				if(taEtat.getQte2()==null)taEtat.setQte2(new BigDecimal(0));
				if(taEtat.getQte2Report()==null)taEtat.setQte2Report(new BigDecimal(0));
				taEtat.setQte2Fin(taEtat.getQte2Report().add(taEtat.getQte2()));
			}
		}
		else listeEtatFinalMouvement.addAll(listeEtatFinalMouvementLocal);
		
		//debugList(listeEtatFinalMouvement);
		
		return listeEtatFinalMouvement;
	}


	public List<TaEtatMouvementDms> calculEtatReportFamille(TaEtatMouvementDms criteres){
		listeEtatFinalMouvement.clear();
		listeEtatMouvement.clear();
		
		List<TaEtatMouvementDms>  listeEtatTemp = new ArrayList<TaEtatMouvementDms>();
		listeEtatTemp.clear();

		AbstractApplicationDAO taLDocumentDAO = null;
		taLDocumentDAO = new TaLFactureDAO();
		
		listeEtatTemp.clear();
		listeEtatTemp =((TaLFactureDAO)taLDocumentDAO).selectSumReportDms(criteres);
		if(listeEtatTemp!=null)
			for (TaEtatMouvementDms taEtat : listeEtatTemp) {
				listeEtatReport.add(taEtat);
			}
		taLDocumentDAO = new TaLAvoirDAO();
		listeEtatTemp.clear();
		listeEtatTemp =((TaLAvoirDAO)taLDocumentDAO).selectSumReportDms(criteres);
		if(listeEtatTemp!=null)
			for (TaEtatMouvementDms taEtat : listeEtatTemp) {
				listeEtatReport.add(taEtat);
			}


		for (TaEtatMouvementDms taEtat : listeEtatReport) {
			int rang =taEtat.existeArticleUnite(taEtat,listeEtatFinalReport);
			if(rang==-1)
				listeEtatFinalReport.add(taEtat);
			else {
				TaEtatMouvementDms tmp = listeEtatFinalReport.get(rang);
				if(taEtat.getQte1()!=null)
					tmp.setQte1(tmp.getQte1().add(taEtat.getQte1()));
				if(taEtat.getQte2()!=null && tmp.getQte2()!=null)
					tmp.setQte2(tmp.getQte2().add(taEtat.getQte2()));
			}
		}

		return listeEtatFinalReport;
	}
	
	private void debugList(List<TaEtatMouvementDms> l) {
		System.out.println(l.get(0).toStringEnteteSQL());
		for (TaEtatMouvementDms taEtatMouvementDms : l) {
			System.out.println(taEtatMouvementDms.toStringSQL());
		}
	}
	
	public void exportCSVObject(List<Object> l) {
		LinkedList<TaEtatMouvementDms> lm = new LinkedList<TaEtatMouvementDms>();
		for (Object o : l) {
			lm.add((TaEtatMouvementDms)o);
		}
		exportCSV(lm);
	}
	
	public void exportCSV(List<TaEtatMouvementDms> l) {
		try {
			FileDialog dd = new FileDialog(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(), SWT.SAVE);
			// Configuration de la fenêtre
			dd.setText("Exporter en fichier csv");
			dd.setFileName("*.csv");
			dd.setFilterExtensions(new String[] { "*.csv" });
			dd.setOverwrite(true);

			// Récupération des informations
			String repDestination = "";
//			String repDestination = GestionDossierPlugin.getDefault().getPreferenceStore().getString("repertoire_w");
			if (repDestination.equals("")) {
				repDestination = Platform.getInstanceLocation().getURL().getFile();
			}
			dd.setFilterPath(LibChaine.pathCorrect(repDestination));
			String choix = dd.open();
			System.err.println(choix);

			// Si la fenetre de dialogue n'a pas été fermée volontairement
			String filecontent = "";
			if (choix != null) {
				PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell().setCursor(Display.getCurrent().getSystemCursor(SWT.CURSOR_WAIT));
				
				// Première ligne contenant les id des colonnes
//				InfosPresentation[] infos = ((CTabItemResultatRechercheJPA)vue.getCTabFolderResultat().getSelection()).getController().getInfos();
//				for (int i = 0; i < infos.length-1; i++) {
//					filecontent += infos[i].getTitre() + ";";
//				}
//				filecontent += infos[infos.length - 1].getTitre();
				filecontent += "dateDeb;";
				filecontent += "dateFin;";
				filecontent += "codeTiers;";
				filecontent += "codeTiersFin;";
				filecontent += "nomTiers;";
				filecontent += "codeDocument;";
				filecontent += "dateDocument;";
				filecontent += "codeFamilleTiers;";
				filecontent += "codeFamilleTiersFin;";
				filecontent += "libcFamilleTiers;";
				filecontent += "codeArticle;";
				filecontent += "codeArticleFin;";
				filecontent += "libellecArticle;";
				filecontent += "codeFamille;";
				filecontent += "codeFamilleFin;";
				filecontent += "libcFamille;";
				filecontent += "un1;";
				filecontent += "un2;";
				filecontent += "codeTauxTVA;";
//				filecontent += "localisationTVAFr;";
//				filecontent += "localisationTVAUE;";
//				filecontent += "localisationTVAHUE;";
//				filecontent += "localisationTVAFranchise;";
				filecontent += "mouvement;";
				filecontent += "mois;";
				filecontent += "annee;";
				filecontent += "qte1;";
				filecontent += "qte1Report;";
				filecontent += "qte1Fin;";
				filecontent += "qte2;";
				filecontent += "qte2Report;";
				filecontent += "qte2Fin;";
				filecontent += "ht;";
				filecontent += "tva;";
				filecontent += "ttc;";
				filecontent += "hauteur;";
				filecontent += "largeur;";
				filecontent += "poids;";
				filecontent += "droitsAccise";
				// Contenu du tableau
//				Collection<Resultat> collectionResultat = ((CTabItemResultatRechercheJPA)vue.getCTabFolderResultat().getSelection()).getController().getModelImpression().remplirListe();
//				for (Resultat resultat : collectionResultat) {
//					filecontent += "\n";
//					for (int i = 0; i < infos.length-1; i++) {
//						filecontent += resultat.findValue(i+1) != null ? resultat.findValue(i+1)+ ";" : ";";
//					}
//					filecontent += resultat.findValue(infos.length) != null ? resultat.findValue(infos.length):"";
//				}
				for (TaEtatMouvementDms m : l) {
					filecontent += "\n";
					filecontent += m.getDateDeb()!= null ? m.getDateDeb()+";" : ";";
					filecontent += m.getDateFin()!= null ? m.getDateFin()+";" : ";";
					filecontent += m.getCodeTiers()!= null ? m.getCodeTiers()+";" : ";";
					filecontent += m.getCodeTiersFin()!= null ? m.getCodeTiersFin()+";" : ";";
					filecontent += m.getNomTiers()!= null ? m.getNomTiers()+";" : ";";
					filecontent += m.getCodeDocument()!= null ? m.getCodeDocument()+";" : ";";
					filecontent += m.getDateDocument()!= null ? m.getDateDocument()+";" : ";";
					filecontent += m.getCodeFamilleTiers()!= null ? m.getCodeFamilleTiers()+";" : ";";
					filecontent += m.getCodeFamilleTiersFin()!= null ? m.getCodeFamilleTiersFin()+";" : ";";
					filecontent += m.getLibcFamilleTiers()!= null ? m.getLibcFamilleTiers()+";" : ";";
					filecontent += m.getCodeArticle()!= null ? m.getCodeArticle()+";" : ";";
					filecontent += m.getCodeArticleFin()!= null ? m.getCodeArticleFin()+";" : ";";
					filecontent += m.getLibellecArticle()!= null ? m.getLibellecArticle()+";" : ";";
					filecontent += m.getCodeFamille()!= null ? m.getCodeFamille()+";" : ";";
					filecontent += m.getCodeFamilleFin()!= null ? m.getCodeFamilleFin()+";" : ";";
					filecontent += m.getLibcFamille()!= null ? m.getLibcFamille()+";" : ";";
					filecontent += m.getUn1()!= null ? m.getUn1()+";" : ";";
					filecontent += m.getUn2()!= null ? m.getUn2()+";" : ";";
					filecontent += m.getCodeTauxTVA()!= null ? m.getCodeTauxTVA()+";" : ";";
//					filecontent += m.getlocalisationTVAFr;";
//					filecontent += m.getlocalisationTVAUE;";
//					filecontent += m.getlocalisationTVAHUE;";
//					filecontent += m.getlocalisationTVAFranchise;";
					filecontent += m.getMouvement()!= null ? m.getMouvement()+";" : ";";
					filecontent += m.getMois()!= null ? m.getMois()+";" : ";";
					filecontent += m.getAnnee()!= null ? m.getAnnee()+";" : ";";
					filecontent += m.getQte1()!= null ? m.getQte1()+";" : ";";
					filecontent += m.getQte1Report()!= null ? m.getQte1Report()+";" : ";";
					filecontent += m.getQte1Fin()!= null ? m.getQte1Fin()+";" : ";";
					filecontent += m.getQte2()!= null ? m.getQte2()+";" : ";";
					filecontent += m.getQte2Report()!= null ? m.getQte2Report()+";" : ";";
					filecontent += m.getQte2Fin()!= null ? m.getQte2Fin()+";" : ";";
					filecontent += m.getHt()!= null ? m.getHt()+";" : ";";
					filecontent += m.getTva()!= null ? m.getTva()+";" : ";";
					filecontent += m.getTtc()!= null ? m.getTtc()+";" : ";";
					
					filecontent += m.getHauteur()!= null ? m.getHauteur()+";" : ";";
					filecontent += m.getLargeur()!= null ? m.getLargeur()+";" : ";";
					filecontent += m.getPoids()!= null ? m.getPoids()+";" : ";";
					filecontent += m.getDroitsAccises()!= null ? m.getDroitsAccises()+";" : ";";
				}
			

				PrintStream out;
				out = new PrintStream(new FileOutputStream(choix), true, "UTF-8");
				out.print(filecontent);
				out.flush();
				out.close();
				out = null;
				PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell().setCursor(Display.getCurrent().getSystemCursor(SWT.CURSOR_ARROW));
				MessageDialog.openInformation(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(), "Exportation",
						"Exportation terminée.");
			}
		} catch (UnsupportedEncodingException e1) {
			logger.error("",e1);
		} catch (FileNotFoundException e1) {
			logger.error("",e1);
		} finally {
			PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell().setCursor(Display.getCurrent().getSystemCursor(SWT.CURSOR_ARROW));
		}


	}
	
}
