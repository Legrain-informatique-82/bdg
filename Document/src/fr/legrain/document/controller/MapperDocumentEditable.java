package fr.legrain.document.controller;

import java.util.ArrayList;
import java.util.HashSet;

import javax.persistence.EntityManager;

import fr.legrain.documents.dao.TaDocumentEditable;
import fr.legrain.documents.dao.TaTDoc;
import fr.legrain.documents.dao.TaTDocDAO;
import fr.legrain.gestCom.Appli.IlgrMapper;
import fr.legrain.gestCom.Appli.LgrDozerMapper;
import fr.legrain.gestCom.Module_Document.SWTDocumentEditable;
import fr.legrain.gestCom.Module_Document.SWTTDoc;

public class MapperDocumentEditable implements IlgrMapper<SWTDocumentEditable, TaDocumentEditable> {
	
	private LgrDozerMapper<SWTDocumentEditable,TaDocumentEditable> mapperUIToModel  = new LgrDozerMapper<SWTDocumentEditable,TaDocumentEditable>();
	private LgrDozerMapper<TaDocumentEditable,SWTDocumentEditable> mapperModelToUI  = new LgrDozerMapper<TaDocumentEditable,SWTDocumentEditable>();
	
	private LgrDozerMapper<SWTTDoc,TaTDoc> mapperUIToModelTdoc  = new LgrDozerMapper<SWTTDoc,TaTDoc>();
	private LgrDozerMapper<TaTDoc,SWTTDoc> mapperModelToUITdoc  = new LgrDozerMapper<TaTDoc,SWTTDoc>();
	
	private EntityManager em = null;

	@Override
	public TaDocumentEditable dtoToEntity(SWTDocumentEditable e) {
		TaDocumentEditable doc = new TaDocumentEditable();
		mapperUIToModel.map(e, doc);
		
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
	
	public TaDocumentEditable dtoToEntity(SWTDocumentEditable e, TaDocumentEditable doc) {
		//TaDocumentEditable doc = new TaDocumentEditable();
		mapperUIToModel.map(e, doc);
		
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
//		else { //TODO A SUPPRIMER, AFFECTATION D'UN TYPE PAR DEFAUT
//			taTDocDAO = new TaTDocDAO(em);
//			taTDoc = taTDocDAO.findById(1);
//			doc.getTaTDoc().add(taTDoc);
//		}
		return doc;
	}

	@Override
	public SWTDocumentEditable entityToDto(TaDocumentEditable e) {
		SWTDocumentEditable doc = new SWTDocumentEditable();
		mapperModelToUI.map(e, doc);
		
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
