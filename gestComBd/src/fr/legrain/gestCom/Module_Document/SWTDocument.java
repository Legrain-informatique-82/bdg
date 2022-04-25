package fr.legrain.gestCom.Module_Document;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.TooManyListenersException;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.SequenceGenerator;
import javax.persistence.Transient;
import javax.persistence.Version;
import javax.swing.event.EventListenerList;

import fr.legrain.documents.dao.TaFacture;
import fr.legrain.documents.events.SWTModificationDocumentEvent;
import fr.legrain.documents.events.SWTModificationDocumentListener;
import fr.legrain.dossier.dao.TaInfoEntreprise;
import fr.legrain.dossier.dao.TaInfoEntrepriseDAO;
import fr.legrain.dossier.dao.TaVersion;
import fr.legrain.dossier.dao.TaVersionDAO;
import fr.legrain.gestCom.Appli.LgrHibernateValidated;
import fr.legrain.lib.data.ExceptLgr;
import fr.legrain.lib.data.LibDate;
import fr.legrain.lib.data.ModeObjet.EnumModeObjet;

@Entity
@SequenceGenerator(name = "GEN_FACTURE", sequenceName = "NUM_ID_DOCUMENT", allocationSize = 1)
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class SWTDocument implements PropertyChangeListener,IDocumentTiers{
	
	/*
	 * 
	 */
	
	protected int idDocument;
	protected Boolean relationDocument;
	protected Integer versionObj;
	
	@Transient
	private Boolean accepte=true;
	
	@Version
	@Column(name = "VERSION_OBJ")
	public Integer getVersionObj() {
		return this.versionObj;
	}
	
	public void setVersionObj(Integer versionObj) {
		this.versionObj = versionObj;
	}
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "GEN_FACTURE")
	@Column(name = "ID_DOCUMENT", unique = true, nullable = false)
	public int getIdDocument() {
		return this.idDocument;
	}
	
	public void setIdDocument(int idDocument) {
		this.idDocument = idDocument;
	}
	/*
	 * 
	 */
	protected EnumModeObjet modeDocument; //insertion,modification,suppression
	protected EnteteDocument entete = null;
	protected List/*<? extends SWTLigneDocument>*/ lignes = null; //ensemble des lignes du document
//	protected String tableLignes = null;
	protected EventListenerList listenerList = new EventListenerList();
	protected int ligneCourante = 0;
	public static final String C_OLD_CODE_INITIAL = "-1";
	protected String OldCODE = C_OLD_CODE_INITIAL;
	protected Date oldDate;
	
	public SWTDocument(){
	}
	public SWTDocument(String oldCode){		
		this.setOldCODE(oldCode);
	}
	
	@Transient
	public boolean isRemplie(){
		if(lignes!=null && lignes.size()>0){
			for (int i = 0; i < lignes.size(); i++) {
				if(!getLigne(i).ligneEstVide())return true;
			};
		}
		return false;		
	}
	
	
	public void addModificationDocumentListener(SWTModificationDocumentListener l) {
		listenerList.add(SWTModificationDocumentListener.class, l);
	}
	
	public void removeModificationDocumentListener(SWTModificationDocumentListener l) {
		listenerList.remove(SWTModificationDocumentListener.class, l);
	}
	
	protected void fireModificationDocument(SWTModificationDocumentEvent e) throws Exception {
		Object[] listeners = listenerList.getListenerList();
		for (int i = listeners.length - 2; i >= 0; i -= 2) {
			if (listeners[i] == SWTModificationDocumentListener.class) {
				if (e == null)
					e = new SWTModificationDocumentEvent(this);
				( (SWTModificationDocumentListener) listeners[i + 1]).modificationDocument(e);
			}
		}
	}
	
	public void declencheModificationDocument(SWTModificationDocumentEvent e) throws Exception {
		fireModificationDocument(e);
	}
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
	
	/**
	 * Ajout d'une ligne en fin de liste
	 * avec controle avant ajout et traitement après ajout
	 * @param ligne
	 * @throws ExceptLgr
	 */
	public void addLigne(SWTLigneDocument ligne) throws Exception {
		if(beforeAjoutLigne(ligne)) {
			ligne.setNumLigneLDocument(lignes.size());
			lignes.add(ligne);
			ligne.addPropertyChangeListener(this);
			ligneCourante = lignes.size()-1;
			afterAjoutLigne(ligne);
			fireModificationDocument(new SWTModificationDocumentEvent(this,EnumModeObjet.C_MO_INSERTION,ligne));
		} else {
			throw new ExceptLgr();
		}
	}
	
	/**
	 * Ajout d'une ligne en fin de liste
	 * avec controle avant ajout et traitement après ajout
	 * @param ligne
	 * @throws ExceptLgr
	 */
	public void enregistreLigne(SWTLigneDocument ligne) throws Exception {
		beforeAjoutLigne(ligne);
		
		afterAjoutLigne(ligne);
		fireModificationDocument(new SWTModificationDocumentEvent(this,EnumModeObjet.C_MO_CONSULTATION,ligne));
	}	
	
	/**
	 * Ajout d'une ligne a un rang donné
	 * avec controle avant ajout et traitement après ajout
	 * @param ligne
	 * @param rang - position
	 * @throws ExceptLgr
	 */
	public void insertLigne(SWTLigneDocument ligne, Integer rang) throws Exception {
		if(beforeAjoutLigne(ligne)) {
			ligne.setNumLigneLDocument(rang);
			reinitialiseNumLignes(rang,1);
			lignes.add(rang,ligne);
			ligne.addPropertyChangeListener(this);
			ligneCourante = rang;
			afterAjoutLigne(ligne);
			fireModificationDocument(new SWTModificationDocumentEvent(this,EnumModeObjet.C_MO_INSERTION));
		} else {
			throw new ExceptLgr();
		}
	}
	public void deplaceLigne(SWTLigneDocument ligne,Integer nouveauRang){
		int rangActuel=lignes.indexOf(ligne);
		//SWTLigneDocument l=(SWTLigneDocument)lignes.get(rangActuel);
		lignes.remove(ligne);
		reinitialiseNumLignes(0,0);
		ligne.setNumLigneLDocument(nouveauRang);
		reinitialiseNumLignes(nouveauRang,1);
		lignes.add(nouveauRang,ligne);
		reinitialiseNumLignes(0,0);
	}
	
	public void reinitialiseNumLignes(Integer aPartirDe,int increment){
		int i=aPartirDe+increment;
		//int i=aPartirDe;
		for (Object ligne : lignes) {
//			if(((SWTLigneDocument)ligne).getNUM_LIGNE()>=aPartirDe)
			if(((SWTLigneDocument)ligne).getNumLigneLDocument()>=aPartirDe)
			{
				((SWTLigneDocument)ligne).setNumLigneLDocument(i);
				i++;
			}
		}
		reinitialiseNumLignes();
	}
	
	
	protected abstract boolean beforeAjoutLigne(SWTLigneDocument ligne);
	
	protected abstract void afterAjoutLigne(SWTLigneDocument ligne) throws ExceptLgr;
	
	protected abstract boolean beforeRemoveLigne(SWTLigneDocument ligne);
	
	protected abstract void afterRemoveLigne(SWTLigneDocument ligne) throws ExceptLgr;
	
	protected abstract boolean beforeEnregistrerEntete();
	
	protected abstract void afterEnregistrerEntete() throws ExceptLgr;
	
	protected abstract boolean beforeModifierEntete();
	
	protected abstract void afterModifierEntete() throws ExceptLgr;
	
	protected abstract boolean beforeSupprimerEntete();
	
	protected abstract void afterSupprimerEntete() throws ExceptLgr;
	
	protected abstract void reinitialiseNumLignes();
	/**
	 * Suppression d'un ligne donnée
	 * @param ligne
	 * @throws ExceptLgr
	 */
	public void removeLigne(SWTLigneDocument ligne) throws Exception {
		if(beforeRemoveLigne(ligne)) {
			if(lignes.size()-1>=0 ){
				fireModificationDocument(new SWTModificationDocumentEvent(this,EnumModeObjet.C_MO_SUPPRESSION,getLigne(ligneCourante)));
				int rang = lignes.indexOf(ligne);
				lignes.remove(ligne);
				reinitialiseNumLignes(0, 0);
				afterRemoveLigne(ligne);
				fireModificationDocument(new SWTModificationDocumentEvent(this,EnumModeObjet.C_MO_SUPPRESSION,getLigne(ligneCourante)));
				ligne.removePropertyChangeListener(this);
			}
		} else {
			throw new ExceptLgr();
		}
	}
	
	public SWTLigneDocument getLigne(Integer numLigne) {
		if (lignes.size()>numLigne && numLigne>=0)
		return (SWTLigneDocument)lignes.get(numLigne);
		else
			return null;
	}
	
		
	
//	public void enregistrer() throws ExceptLgr {
//		//TODO enregistrement du document dans les bonnes tables
//		//updateDocument.setMode(new ModeObjet(mode));
//		entete.setMode(mode);
//		entete.enregistrer();
//		for (SWTLigneDocument ligne : lignes) {
//			ligne.setMode(mode);
//			ligne.enregistrer();
//		}
//	}
//	
//	public void modifier() throws ExceptLgr {
//		//TODO modification du document dans les bonnes tables
//	}
//	
//	public void supprimer() throws ExceptLgr {
//		//TODO suppression du document
//		//updateDocument.setMode(new ModeObjet(mode));
//	}
	
	
	@Transient
	public EnteteDocument getEntete() {
		return entete;
	}

	public void setEntete(EnteteDocument entete) {
		this.entete = entete;
	}

//	public ArrayList<SWTLigneDocument> getLignes() {
//		return lignes;
//	}
//
//	public void setLignes(ArrayList<SWTLigneDocument> lignes) {
//		this.lignes = lignes;
//	}
	
	@Transient
	public EnumModeObjet getModeDocument() {
		return modeDocument;
	}

	public void setModeDocument(EnumModeObjet mode) {
		this.modeDocument = mode;
		fireChangeMode(new ChangeModeEvent(this,mode));
	}
	
//	@Transient
//	public String getTableLignes() {
//		return tableLignes;
//	}
//
//	public void setTableLignes(String tableLignes) {
//		this.tableLignes = tableLignes;
//	}
	
	@Transient
	public int getLigneCourante() {
		return ligneCourante;
	}

	public void setLigneCourante(int ligneCourante) {
		this.ligneCourante = ligneCourante;
		if (this.ligneCourante== -1)this.ligneCourante=0;
	}
	
	@Transient
	public String getOldCODE() {
		return OldCODE;
	}

	public void setOldCODE(String oldCODE) {
		OldCODE = oldCODE;
	}
	

	@Transient
	public boolean getRelationDocument() {
		// TODO Auto-generated method stub
		return relationDocument;
	}


	@Transient
	public void setRelationDocument(Boolean relation) {
		this.relationDocument=relation;
	}
	
	public void propertyChange(PropertyChangeEvent evt) {
		if(evt.getPropertyName().equals("modeLigne")){
			this.setModeDocument((EnumModeObjet)evt.getNewValue());
		}
	}

	@Transient
	public Date getOldDate() {
		return oldDate;
	}

	public void setOldDate(Date oldDate) {
		this.oldDate = oldDate;
	}
	

	public Date dateDansExercice(Date valeur) throws Exception {
		
		TaInfoEntrepriseDAO taInfoEntrepriseDAO = new TaInfoEntrepriseDAO(new TaInfoEntrepriseDAO().getEntityManager());
		TaInfoEntreprise taInfoEntreprise = taInfoEntrepriseDAO.findInstance();
		// si date inférieur à dateDeb dossier
		if (LibDate.compareTo(valeur, taInfoEntreprise.getDatedebInfoEntreprise()) == -1) {
			return taInfoEntreprise.getDatedebInfoEntreprise();
		} else
			// si date supérieur à dateFin dossier
			if (LibDate.compareTo(valeur, taInfoEntreprise.getDatefinInfoEntreprise()) == 1) {
				return taInfoEntreprise.getDatefinInfoEntreprise();
			}
		//return new Date();
		return valeur;
	}
	
	public Boolean changementDateDocument(Date newDate){
		if(newDate==null)return false;
		
		return oldDate==null || newDate == null ||
		LibDate.dateToString(oldDate).compareTo(LibDate.dateToString(newDate))!=0;
		//(oldDate.compareTo(newDate)!=0);
		//newDate.getDate();
	}

	
	@Transient
	public Boolean getAccepte() {
		return accepte;
	}
	@Transient
	public void setAccepte(Boolean accepte) {
		this.accepte = accepte;
	}


	@Transient
	public List<ILigneDocumentTiers> calculNbReelLigneImpression(int coupure,ArrayList<ILigneDocumentTiers>ligneInitiales){
		int nbLigne=0;
		int rangImpression=0;
		LinkedList<ILigneDocumentTiers> listeFinale=new LinkedList<ILigneDocumentTiers>();
		LinkedList<ILigneDocumentTiers> liste2=new LinkedList<ILigneDocumentTiers>();
		for (Object ligne : ligneInitiales) {
			liste2.clear();
			liste2=((SWTLigneDocument)ligne).creeLigneImpression_indirect(coupure,rangImpression);
			for (ILigneDocumentTiers ligneFinale : liste2) {
				listeFinale.add(ligneFinale);
			}
			rangImpression++;
			
			//nbLigne=nbLigne+((TaLFacture)ligne).calculNbLigneLibelle(coupure);
		}
	return listeFinale;
	}
	
	@Transient
	public int calculNbLigne(LinkedList listeFinale,int nombreLineBreakTotaux,int nombreLineBreakMaxi, int nombreFois){
		int sizeTotal=0;
		int reste = 0;
		if(listeFinale.size() <= nombreLineBreakTotaux ){
			sizeTotal = nombreLineBreakTotaux;
		}else{
			if(listeFinale.size() <= nombreLineBreakMaxi ){
				sizeTotal = nombreLineBreakMaxi+nombreLineBreakTotaux;
			}else{
				reste=listeFinale.size()-(nombreFois*nombreLineBreakMaxi);
				if(reste<=nombreLineBreakTotaux){
					sizeTotal=  (nombreFois*nombreLineBreakMaxi)+(nombreLineBreakTotaux);
				}else{
					sizeTotal =  (nombreFois*nombreLineBreakMaxi)+(nombreLineBreakMaxi)+nombreLineBreakTotaux;
				}
			}
		}
		return sizeTotal;
	}
	
	@Transient
	public boolean retourneValeurs(LinkedList listeFinale,int count, int sizeTotal){
		if(count<= sizeTotal ) {
			//a = count%nombreLineBreakMaxi;
//			if(a == 0 && count != 0 && count != sizeTotal){
//				row["flagVisible"] = false;
//			}
//			else{
				//row["flagVisible"] = true;
//			}
			
			if(count <= listeFinale.size()){
				
			}
			count++;
			return true;	
		}
		return false;
	}
	
/*
	@PrePersist
	public void beforePost()throws Exception{
//		   New.CODE_DOCUMENT = Upper(New.CODE_DOCUMENT);
//		   New.QUI_CREE_DOCUMENT  = USER;
//		   New.QUAND_CREE_DOCUMENT = 'NOW';
//		   New.QUI_MODIF_DOCUMENT = USER;
//		   New.QUAND_MODIF_DOCUMENT = 'NOW';
//		   new.IP_ACCES = current_connection;
//		   select num_version from ta_version into new."VERSION";
		TaVersionDAO daoVersion =new TaVersionDAO();
		TaVersion version= daoVersion.findInstance();
		this.setCodeDocument(getCodeDocument().toUpperCase());
		this.setQuiCreeDocument("");
		this.setQuandCreeDocument(new Date());
		this.setQuiModifDocument("");
		this.setQuandModifDocument(new Date());
		this.setIpAcces("");
		this.setVersion(version.getNumVersion());
	}
	
//	@PreUpdate
	public void beforeUpdate()throws Exception{
		TaVersionDAO daoVersion =new TaVersionDAO();
		TaVersion version= daoVersion.findInstance();
		this.setCodeDocument(getCodeDocument().toUpperCase());
		this.setQuiModifDocument("");
		this.setQuandModifDocument(new Date());
		this.setIpAcces("");
		this.setVersion(version.getNumVersion());
	}
*/
}
