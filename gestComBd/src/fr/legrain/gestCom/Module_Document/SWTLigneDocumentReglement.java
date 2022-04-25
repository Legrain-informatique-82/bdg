package fr.legrain.gestCom.Module_Document;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.math.BigDecimal;
import java.util.TooManyListenersException;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.SequenceGenerator;
import javax.persistence.Transient;
import javax.persistence.Version;
import javax.swing.event.EventListenerList;

import fr.legrain.articles.dao.TaArticle;
import fr.legrain.lib.data.LibCalcul;
import fr.legrain.lib.data.LibConversion;
import fr.legrain.lib.data.ModeObjet.EnumModeObjet;

@Entity
@SequenceGenerator(name = "GEN_L_FACTURE", sequenceName = "NUM_ID_L_DOCUMENT", allocationSize = 1)
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class SWTLigneDocumentReglement  /*implements  ILigneDocumentTiers*/ {
	protected EnumModeObjet modeLigne;
	protected EnumModeObjet modeDocument;
	protected EventListenerList listenerList = new EventListenerList();
	//protected Update updateLigne = new Update();
	//protected TaArticle taArticle = null;
	protected Integer rowGrille = -1;
	
	//TODO arriver à supprimer ces 2 propriétés et n'utiliser que numLigneDocument et idDocument => demande pas mal de tests/verifications
	protected Integer ID_DOCUMENT = 0;
	//protected Integer NUM_LIGNE = 0;
	
	protected int idLDocument;
	protected Integer versionObj;
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "GEN_L_FACTURE")
	@Column(name = "ID_L_DOCUMENT", unique = true, nullable = false)
	public int getIdLDocument() {
		return this.idLDocument;
	}

	public void setIdLDocument(int idLFacture) {
		this.idLDocument = idLFacture;
	}
	
	@Version
	@Column(name = "VERSION_OBJ")
	public Integer getVersionObj() {
		return this.versionObj;
	}
	
	public void setVersionObj(Integer versionObj) {
		this.versionObj = versionObj;
	}
	
	public abstract boolean ligneEstVide();
	
	public void addChangeModeListener(ChangeModeListener l) throws TooManyListenersException {
		listenerList.add(ChangeModeListener.class, l);
//		if(listenerList.getListenerCount()>1) {
//			throw new TooManyListenersException("Un seul listener par ligne");
//		}
	}
	
	public void removeChangeModeListener(ChangeModeListener l) {
		listenerList.remove(ChangeModeListener.class, l);
	}
	
	protected void fireChangeMode(ChangeModeEvent e) {
		Object[] listeners = listenerList.getListenerList();
		for (int i = listeners.length - 2; i >= 0; i -= 2) {
			if (listeners[i] == ChangeModeListener.class) {
				if (e == null)
					e = new ChangeModeEvent(this,null);
				( (ChangeModeListener) listeners[i + 1]).changementMode(e);
			}
		}
	}
	
	public void addPropertyChangeListener(PropertyChangeListener l) {
		listenerList.add(PropertyChangeListener.class, l);
	}
	
	public void removePropertyChangeListener(PropertyChangeListener l) {
		listenerList.remove(PropertyChangeListener.class, l);
	}
	
	protected void firePropertyChange(PropertyChangeEvent e) {
		Object[] listeners = listenerList.getListenerList();
		for (int i = listeners.length - 2; i >= 0; i -= 2) {
			if (listeners[i] == PropertyChangeListener.class) {
				if (e == null)
					e = new PropertyChangeEvent(this,null,null,null);
				( (PropertyChangeListener) listeners[i + 1]).propertyChange(e);
			}
		}
	}
	
//	public void enregistrer() throws ExceptLgr {
//		beforeEnregistrerEntete();
//		//TODO enregistrement du document dans les bonnes tables
//		
//		//update.createQuery();
//		afterEnregistrerEntete();
//	}
//	
//	public void modifier() throws ExceptLgr {
//		beforeModifierEntete();
//		//TODO modification du document dans les bonnes tables
//		afterModifierEntete();
//	}
//	
//	public void supprimer() throws ExceptLgr {
//		beforeSupprimerEntete();
//		//TODO suppression du document
//		afterSupprimerEntete();
//	}

	@Transient
	public EnumModeObjet getModeLigne() {
		return modeLigne;
	}

	public void setModeLigne(EnumModeObjet mode) {
		this.modeLigne = mode;
		fireChangeMode(new ChangeModeEvent(this,mode));
		firePropertyChange(new PropertyChangeEvent(this,"modeLigne",mode,mode));
	}

	@Transient
	public Integer getID_DOCUMENT() {
		return ID_DOCUMENT;
	}

	public void setID_DOCUMENT(Integer id_document) {
		ID_DOCUMENT = id_document;
	}

	@Transient
	public EnumModeObjet getModeDocument() {
		return modeDocument;
	}

	public void setModeDocument(EnumModeObjet modeDocument) {
		this.modeDocument = modeDocument;
	}

//	@Transient
//	public TaArticle getTaArticle() {
//		return taArticle;
//	}
//
//	public void setTaArticle(TaArticle article) {
//		this.taArticle = article;
//	}

	@Transient
	public Integer getRowGrille() {
		return rowGrille;
	}

	public void setRowGrille(Integer rowGrille) {
		this.rowGrille = rowGrille;
	}

//	@Transient
//	public Integer getNUM_LIGNE() {
//		return NUM_LIGNE;
//	}
//
//	public void setNUM_LIGNE(Integer num_ligne) {
//		NUM_LIGNE = num_ligne;
//		setNumLigneLDocument(num_ligne);
//	}
	
//	@Transient
//	public void initQte2(TaArticle article){
//		if(article.getTaRapportUnite()!=null && article.getTaRapportUnite().getTaUnite2()!=null
//				&& article.getTaRapportUnite().getTaUnite1().getCodeUnite().equals(getU1LDocument())){
//			int nbDecimal =article.getTaRapportUnite().getNbDecimal(); 
//			setU2LDocument(article.getTaRapportUnite().getTaUnite2().getCodeUnite());
//			BigDecimal Qte2 = LibCalcul.arrondi(getQteLDocument().multiply(article.getTaRapportUnite().getRapport()),nbDecimal);
//			setQte2LDocument(Qte2);
//		}else{
////			setU2LDocument("");
////			setQte2LDocument(BigDecimal.ZERO);
//		}
//	}
}
