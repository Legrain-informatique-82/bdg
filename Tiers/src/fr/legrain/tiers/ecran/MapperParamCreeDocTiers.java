package fr.legrain.tiers.ecran;

import java.util.ArrayList;
import java.util.HashSet;

import javax.persistence.EntityManager;

import fr.legrain.documents.dao.TaTDoc;
import fr.legrain.documents.dao.TaTDocDAO;
import fr.legrain.gestCom.Appli.IlgrMapper;
import fr.legrain.gestCom.Appli.LgrDozerMapper;
import fr.legrain.gestCom.Module_Document.SWTTDoc;
import fr.legrain.gestCom.Module_Tiers.SWTParamCreeDocTiers;
import fr.legrain.lib.data.LibConversion;
import fr.legrain.tiers.dao.TaParamCreeDocTiers;

public class MapperParamCreeDocTiers implements IlgrMapper<SWTParamCreeDocTiers, TaParamCreeDocTiers> {
	
	private LgrDozerMapper<SWTParamCreeDocTiers,TaParamCreeDocTiers> mapperUIToModel = 
			new LgrDozerMapper<SWTParamCreeDocTiers,TaParamCreeDocTiers>();
	private LgrDozerMapper<TaParamCreeDocTiers,SWTParamCreeDocTiers> mapperModelToUI  = 
			new LgrDozerMapper<TaParamCreeDocTiers,SWTParamCreeDocTiers>();
	
	private LgrDozerMapper<SWTTDoc,TaTDoc> mapperUIToModelTdoc  = new LgrDozerMapper<SWTTDoc,TaTDoc>();
	private LgrDozerMapper<TaTDoc,SWTTDoc> mapperModelToUITdoc  = new LgrDozerMapper<TaTDoc,SWTTDoc>();
	
	private EntityManager em = null;

	@Override
	public TaParamCreeDocTiers dtoToEntity(SWTParamCreeDocTiers e) {
		TaParamCreeDocTiers doc = new TaParamCreeDocTiers();
		mapperUIToModel(doc, e);
		doc.setTaTDoc(new HashSet<TaTDoc>());
		TaTDocDAO taTDocDAO = null;
		TaTDoc taTDoc = null;
		if(em!=null) {
			taTDocDAO = new TaTDocDAO(em);
			for (SWTTDoc tdoc : e.getTaTDoc()) {
				taTDoc = taTDocDAO.findById(tdoc.getIdTDoc());
				doc.getTaTDoc().add(taTDoc);
			}
		}
		return doc;
	}
	
	public TaParamCreeDocTiers dtoToEntity(SWTParamCreeDocTiers e, TaParamCreeDocTiers doc) {
		mapperUIToModel(doc, e);
		
		doc.setTaTDoc(new HashSet<TaTDoc>());
		TaTDocDAO taTDocDAO = null;
		TaTDoc taTDoc = null;
		if(em!=null && e.getTaTDoc()!=null) {
			taTDocDAO = new TaTDocDAO(em);
			for (SWTTDoc tdoc : e.getTaTDoc()) {
				taTDoc = taTDocDAO.findById(tdoc.getIdTDoc());
				doc.getTaTDoc().add(taTDoc);
			}
		} 
		return doc;
	}
	private void mapperUIToModel(TaParamCreeDocTiers e,SWTParamCreeDocTiers doc){
		if(e.getTiers()!=null)doc.setTiers(LibConversion.intToBoolean(e.getTiers()));
		if(e.getDocument()!=null)doc.setDocument(LibConversion.intToBoolean(e.getDocument()));
		if(e.getSemaine()!=null)doc.setSemaine(LibConversion.intToBoolean(e.getSemaine()));
		if(e.getDeuxSemaines()!=null)doc.setDeuxSemaines(LibConversion.intToBoolean(e.getDeuxSemaines()));
		if(e.getDecade()!=null)doc.setDecade(LibConversion.intToBoolean(e.getDecade()));
		if(e.getMois()!=null)doc.setMois(LibConversion.intToBoolean(e.getMois()));
		if(e.getxJours()!=null)doc.setxJours(LibConversion.intToBoolean(e.getxJours()));
		doc.setIdParamCreeDocTiers(e.getIdParamCreeDocTiers());
		if(e.getNbJours()!=null)doc.setNbJours(e.getNbJours());
		if(e.getTaTiers()!=null)doc.setIdTiers(e.getTaTiers().getIdTiers());
		if(e.getCodeParam()!=null)doc.setCodeParam(e.getCodeParam());
	}
	private void mapperModelToUI(TaParamCreeDocTiers e,SWTParamCreeDocTiers doc){
		if(e.getTiers()!=null)e.setTiers(LibConversion.booleanToInt(doc.getTiers()));
		if(e.getDocument()!=null)e.setDocument(LibConversion.booleanToInt(doc.getDocument()));
		if(e.getSemaine()!=null)e.setSemaine(LibConversion.booleanToInt(doc.getSemaine()));
		if(e.getDeuxSemaines()!=null)e.setDeuxSemaines(LibConversion.booleanToInt(doc.getDeuxSemaines()));
		if(e.getDecade()!=null)e.setDecade(LibConversion.booleanToInt(doc.getDecade()));
		if(e.getMois()!=null)e.setMois(LibConversion.booleanToInt(doc.getMois()));
		if(e.getxJours()!=null)e.setxJours(LibConversion.booleanToInt(doc.getxJours()));
		if(e.getCodeParam()!=null)e.setCodeParam(doc.getCodeParam());
		e.setIdParamCreeDocTiers(doc.getIdParamCreeDocTiers());
		e.setNbJours(doc.getNbJours());
//		e.setTaTiers(e.getTaTiers().getIdTiers());
	}
	@Override
	public SWTParamCreeDocTiers entityToDto(TaParamCreeDocTiers e) {
		SWTParamCreeDocTiers doc = new SWTParamCreeDocTiers();

		mapperUIToModel(e, doc);
		
		doc.setTaTDoc(new ArrayList<SWTTDoc>());
		SWTTDoc swtTDoc = null;
		for (TaTDoc tdoc : e.getTaTDoc()) {
			swtTDoc = new SWTTDoc();
			mapperModelToUITdoc.map(tdoc, swtTDoc);
			doc.getTaTDoc().add(swtTDoc);
		}
		return doc;
	}

	public void setEm(EntityManager em) {
		this.em = em;
	}

}
