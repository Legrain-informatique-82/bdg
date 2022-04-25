package fr.legrain.documents.dao;

//Generated Apr 9, 2009 12:40:07 PM by Hibernate Tools 3.2.0.CR1

import java.beans.PropertyChangeEvent;
import java.math.BigDecimal;
import java.math.MathContext;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;
import javax.persistence.Version;

import org.apache.log4j.Logger;

import fr.legrain.articles.dao.TaArticle;
import fr.legrain.articles.dao.TaTvaDAO;
import fr.legrain.documents.events.SWTModificationDocumentEvent;
import fr.legrain.gestCom.Appli.Const;
import fr.legrain.gestCom.Appli.LgrHibernateValidated;
import fr.legrain.gestCom.Module_Document.ChangeModeEvent;
import fr.legrain.gestCom.Module_Document.ChangeModeListener;
import fr.legrain.gestCom.Module_Document.IDocumentTiers;
import fr.legrain.gestCom.Module_Document.SWTDocument;
import fr.legrain.gestCom.Module_Document.SWTLigneDocument;
import fr.legrain.lib.data.ExceptLgr;
import fr.legrain.lib.data.LibCalcul;
import fr.legrain.lib.data.ModeObjet.EnumModeObjet;
import fr.legrain.tiers.dao.TaAdresse;
import fr.legrain.tiers.dao.TaCPaiement;
import fr.legrain.tiers.dao.TaTiers;

public class TaSumMouvDocument implements java.io.Serializable {


//	private TaTPaiement taTPaiement;
//	private TaTiers taTiers;
//	private String codeDocument;
	private Date dateDocument;
	private TaArticle taArticle;
	private BigDecimal qteLDocument;
	private String u1LDocument;
	private BigDecimal qte2LDocument;
	private String u2LDocument;	
//	private Date dateEchDocument;
//	private Date dateLivDocument;
//	private String libelleDocument;
//	private BigDecimal regleDocument = new BigDecimal(0);
//	private BigDecimal remHtDocument = new BigDecimal(0);
//	private BigDecimal txRemHtDocument = new BigDecimal(0);
//	private BigDecimal remTtcDocument = new BigDecimal(0);
//	private BigDecimal txRemTtcDocument = new BigDecimal(0);
//	private Integer nbEDocument = 0;
//	private Integer ttc = 0;
//	private Integer export = 0;
//	private String commentaire;
	
	
	public TaSumMouvDocument(Date dateDocument, TaArticle taArticle,
			BigDecimal qteLDocument, String u1LDocument, BigDecimal qte2LDocument,
			String u2LDocument) {
		this.dateDocument = dateDocument;
		this.taArticle = taArticle;
		this.qteLDocument = qteLDocument;
		this.u1LDocument = u1LDocument;
		this.qte2LDocument = qte2LDocument;
		this.u2LDocument = u2LDocument;
	}


	public Date getDateDocument() {
		return dateDocument;
	}


	public void setDateDocument(Date dateDocument) {
		this.dateDocument = dateDocument;
	}


	public TaArticle getTaArticle() {
		return taArticle;
	}


	public void setTaArticle(TaArticle taArticle) {
		this.taArticle = taArticle;
	}


	public BigDecimal getQteLDocument() {
		return qteLDocument;
	}


	public void setQteLDocument(BigDecimal qteLDocument) {
		this.qteLDocument = qteLDocument;
	}


	public String getU1LDocument() {
		return u1LDocument;
	}


	public void setU1LDocument(String document) {
		u1LDocument = document;
	}


	public BigDecimal getQte2LDocument() {
		return qte2LDocument;
	}


	public void setQte2LDocument(BigDecimal qte2LDocument) {
		this.qte2LDocument = qte2LDocument;
	}


	public String getU2LDocument() {
		return u2LDocument;
	}


	public void setU2LDocument(String document) {
		u2LDocument = document;
	}
	
	



}
