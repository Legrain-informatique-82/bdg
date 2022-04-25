package fr.legrain.tiers.statistiques.editors;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;

import fr.legrain.documents.dao.TaAcompte;
import fr.legrain.documents.dao.TaApporteur;
import fr.legrain.documents.dao.TaAvoir;
import fr.legrain.documents.dao.TaBoncde;
import fr.legrain.documents.dao.TaBonliv;
import fr.legrain.documents.dao.TaDevis;
import fr.legrain.documents.dao.TaFacture;
import fr.legrain.documents.dao.TaProforma;
import fr.legrain.gestCom.Appli.IlgrMapper;
import fr.legrain.lib.data.ModelObject;
import fr.legrain.tiers.dao.TaTiers;
import fr.legrain.tiers.dao.TaTiersDAO;

public class DefaultFormPageController /*extends JPABaseControllerSWTStandard*/ implements IFormPageTiersContoller {

	static Logger logger = Logger.getLogger(DefaultFormPageController.class.getName());

	private TaTiers masterEntity = null;
	private TaTiersDAO masterDAO = null;
	private DefaultFormPage vue = null;

	private IdentiteControllerMini identiteControllerMini = null;
	private CAControllerMini caControllerMini = null;
	private SoldeControllerMini soldeControllerMini = null;
	private GraphControllerMini graphControllerMini = null;
	private AutreControllerMini autreControllerMini = null;
	private ParamControllerMini paramControllerMini = null;


	public DefaultFormPageController(DefaultFormPage vue) {
		this.vue = vue;
		identiteControllerMini = new IdentiteControllerMini(this,vue, null);

		caControllerMini = new CAControllerMini(this,vue, null);
		soldeControllerMini = new SoldeControllerMini(this,vue, null);
		graphControllerMini = new GraphControllerMini(this,vue, null);
		autreControllerMini = new AutreControllerMini(this,vue, null);
		paramControllerMini = new ParamControllerMini(this,vue, null);
	}

	public void refreshAll() {
		initialisationModel(false);
	}

	private void initialisationModel() {
		initialisationModel(true);
	}

	private void initialisationModel(boolean tout) {
		try {
			if(masterDAO!=null && masterEntity!=null) {
				
				identiteControllerMini.initialiseModelIHM(masterEntity,masterDAO);
				identiteControllerMini.bind();

				if(tout) {
					paramControllerMini.initialiseModelIHM(masterEntity,masterDAO);
				}

				graphControllerMini.initialiseModelIHM(masterEntity,masterDAO);

				caControllerMini.initialiseModelIHM(masterEntity,masterDAO);
				caControllerMini.bind();

				soldeControllerMini.initialiseModelIHM(masterEntity,masterDAO);
				soldeControllerMini.bind();
				
				autreControllerMini.initialiseModelIHM(masterEntity,masterDAO);
				autreControllerMini.bind();
				
			}
		} catch(Exception e) {
			logger.error("", e);
		}	finally {
			vue.reflow();
		}
	}

	@Override
	public TaTiersDAO getMasterDAO() {
		return masterDAO;
	}

	@Override
	public TaTiers getMasterEntity() {
		return masterEntity;
	}

	@Override
	public void setMasterDAO(TaTiersDAO masterDAO) {
		this.masterDAO = masterDAO;
	}

	@Override
	public void setMasterEntity(TaTiers masterEntity) {
		this.masterEntity = masterEntity;
		//		bind();
		initialisationModel();
	}

	/* ************************************************************************************************************* */
	/* ************************************************************************************************************* */

	class IdentiteIHM extends ModelObject {
		String codeTiers = null;
		String nomTiers = null;
		String prenomTiers = null;

		public IdentiteIHM() {}

		public IdentiteIHM(String codeTiers, String nomTiers,String prenomTiers) {
			super();
			this.codeTiers = codeTiers;
			this.nomTiers = nomTiers;
			this.prenomTiers = prenomTiers;
		}

		public String getCodeTiers() {
			return codeTiers;
		}
		public void setCodeTiers(String codeTiers) {
			firePropertyChange("codeTiers", this.codeTiers, this.codeTiers = codeTiers);
		}
		public String getNomTiers() {
			return nomTiers;
		}
		public void setNomTiers(String nomTiers) {
			firePropertyChange("nomTiers", this.nomTiers, this.nomTiers = nomTiers);
		}
		public String getPrenomTiers() {
			return prenomTiers;
		}
		public void setPrenomTiers(String prenomTiers) {
			firePropertyChange("prenomTiers", this.prenomTiers, this.prenomTiers = prenomTiers);
		}

	}

	class MapperIdentite implements IlgrMapper<IdentiteIHM, TaTiers> {

		@Override
		public TaTiers dtoToEntity(IdentiteIHM e) {
			return null;
		}

		@Override
		public IdentiteIHM entityToDto(TaTiers e) {
			return new IdentiteIHM(e.getCodeTiers(),e.getNomTiers(),e.getPrenomTiers());
		}

	}

	/* ************************************************************************************************************* */
	/* ************************************************************************************************************* */

	class ParamIHM extends ModelObject {
		Date dateDeb = null;
		Date dateFin = null;

		public ParamIHM() {}

		public ParamIHM(Date dateDeb, Date dateFin) {
			super();
			this.dateDeb = dateDeb;
			this.dateFin = dateFin;
		}

		public Date getDateDeb() {
			return dateDeb;
		}
		public void setDateDeb(Date dateDeb) {
			firePropertyChange("dateDeb", this.dateDeb, this.dateDeb = dateDeb);
		}
		public Date getDateFin() {
			return dateFin;
		}
		public void setDateFin(Date dateFin) {
			firePropertyChange("dateFin", this.dateFin, this.dateFin = dateFin);
		}

	}

	/* ************************************************************************************************************* */
	/* ************************************************************************************************************* */

	class CAIHM extends ModelObject {
		BigDecimal chiffreAffaire = null;

		public CAIHM() {}

		public CAIHM(BigDecimal chiffreAffaire) {
			super();
			this.chiffreAffaire = chiffreAffaire;
		}

		public BigDecimal getChiffreAffaire() {
			return chiffreAffaire;
		}
		public void setChiffreAffaire(BigDecimal chiffreAffaire) {
			firePropertyChange("chiffreAffaire", this.chiffreAffaire, this.chiffreAffaire = chiffreAffaire);
		}

	}

	/* ************************************************************************************************************* */
	/* ************************************************************************************************************* */

	class SoldeIHM extends ModelObject {
		BigDecimal soldeTiers = null;

		public SoldeIHM() {}

		public SoldeIHM(BigDecimal soldeTiers) {
			super();
			this.soldeTiers = soldeTiers;
		}

		public BigDecimal getSoldeTiers() {
			return soldeTiers;
		}
		public void setSoldeTiers(BigDecimal soldeTiers) {
			firePropertyChange("soldeTiers", this.soldeTiers, this.soldeTiers = soldeTiers);
		}

	}
	/* ************************************************************************************************************* */
	/* ************************************************************************************************************* */

	class DocumentIHM extends ModelObject {
		String codeDocument = null;
		String libelleDocument = null;
		Date dateDocument = null;
		BigDecimal montantDocument = null;
		String typeDocument = null;

		public DocumentIHM() {}

		public DocumentIHM(String codeDocument, String libelleDocument, Date dateDocument, BigDecimal montantDocument, String typeDocument) {
			super();
			this.codeDocument = codeDocument;
			this.libelleDocument = libelleDocument;
			this.dateDocument = dateDocument;
			this.montantDocument = montantDocument;
			this.typeDocument = typeDocument;
		}

		public String getCodeDocument() {
			return codeDocument;
		}

		public void setCodeDocument(String codeDocument) {
			firePropertyChange("codeDocument", this.codeDocument, this.codeDocument = codeDocument);
		}

		public String getLibelleDocument() {
			return libelleDocument;
		}

		public void setLibelleDocument(String libelleDocument) {
			firePropertyChange("libelleDocument", this.libelleDocument, this.libelleDocument = libelleDocument);
		}

		public Date getDateDocument() {
			return dateDocument;
		}

		public void setDateDocument(Date dateDocument) {
			firePropertyChange("dateDocument", this.dateDocument, this.dateDocument = dateDocument);
		}

		public BigDecimal getMontantDocument() {
			return montantDocument;
		}

		public void setMontantDocument(BigDecimal montantDocument) {
			firePropertyChange("montantDocument", this.montantDocument, this.montantDocument = montantDocument);
		}

		public String getTypeDocument() {
			return typeDocument;
		}

		public void setTypeDocument(String typeDocument) {
			firePropertyChange("typeDocument", this.typeDocument, this.typeDocument = typeDocument);
		}

	}

	class MapperDocumentIHMTaFacture implements IlgrMapper<DocumentIHM, TaFacture> {

		public static final String TYPE_DOCUMENT = "Facture";

		@Override
		public TaFacture dtoToEntity(DocumentIHM e) {
			return null;
		}

		@Override
		public DocumentIHM entityToDto(TaFacture e) {
			return new DocumentIHM(e.getCodeDocument(),e.getLibelleDocument(),e.getDateDocument(),e.getMtHtCalc(),TYPE_DOCUMENT);
		}

		public List<DocumentIHM> listeEntityToDto(List<TaFacture> l) {
			List<DocumentIHM> res = new ArrayList<DocumentIHM>();
			for (TaFacture taFacture : l) {
				res.add(entityToDto(taFacture));
			}
			return res;
		}

	}

	class MapperDocumentIHMTaDevis implements IlgrMapper<DocumentIHM, TaDevis> {

		public static final String TYPE_DOCUMENT = "Devis";

		@Override
		public TaDevis dtoToEntity(DocumentIHM e) {
			return null;
		}

		@Override
		public DocumentIHM entityToDto(TaDevis e) {
			return new DocumentIHM(e.getCodeDocument(),e.getLibelleDocument(),e.getDateDocument(),e.getMtHtCalc(),TYPE_DOCUMENT);
		}

		public List<DocumentIHM> listeEntityToDto(List<TaDevis> l) {
			List<DocumentIHM> res = new ArrayList<DocumentIHM>();
			for (TaDevis taDevis : l) {
				res.add(entityToDto(taDevis));
			}
			return res;
		}

	}

	class MapperDocumentIHMTaApporteur implements IlgrMapper<DocumentIHM, TaApporteur> {

		public static final String TYPE_DOCUMENT = "Apporteur";

		@Override
		public TaApporteur dtoToEntity(DocumentIHM e) {
			return null;
		}

		@Override
		public DocumentIHM entityToDto(TaApporteur e) {
			return new DocumentIHM(e.getCodeDocument(),e.getLibelleDocument(),e.getDateDocument(),e.getMtHtCalc(),TYPE_DOCUMENT);
		}

		public List<DocumentIHM> listeEntityToDto(List<TaApporteur> l) {
			List<DocumentIHM> res = new ArrayList<DocumentIHM>();
			for (TaApporteur taApporteur : l) {
				res.add(entityToDto(taApporteur));
			}
			return res;
		}

	}

	class MapperDocumentIHMTaProforma implements IlgrMapper<DocumentIHM, TaProforma> {

		public static final String TYPE_DOCUMENT = "Proforma";

		@Override
		public TaProforma dtoToEntity(DocumentIHM e) {
			return null;
		}

		@Override
		public DocumentIHM entityToDto(TaProforma e) {
			return new DocumentIHM(e.getCodeDocument(),e.getLibelleDocument(),e.getDateDocument(),e.getMtHtCalc(),TYPE_DOCUMENT);
		}

		public List<DocumentIHM> listeEntityToDto(List<TaProforma> l) {
			List<DocumentIHM> res = new ArrayList<DocumentIHM>();
			for (TaProforma taDevis : l) {
				res.add(entityToDto(taDevis));
			}
			return res;
		}

	}

	class MapperDocumentIHMTaAvoir implements IlgrMapper<DocumentIHM, TaAvoir> {

		public static final String TYPE_DOCUMENT = "Avoir";

		@Override
		public TaAvoir dtoToEntity(DocumentIHM e) {
			return null;
		}

		@Override
		public DocumentIHM entityToDto(TaAvoir e) {
			return new DocumentIHM(e.getCodeDocument(),e.getLibelleDocument(),e.getDateDocument(),e.getMtHtCalc(),TYPE_DOCUMENT);
		}

		public List<DocumentIHM> listeEntityToDto(List<TaAvoir> l) {
			List<DocumentIHM> res = new ArrayList<DocumentIHM>();
			for (TaAvoir taDevis : l) {
				res.add(entityToDto(taDevis));
			}
			return res;
		}

	}

	class MapperDocumentIHMTaBoncde implements IlgrMapper<DocumentIHM, TaBoncde> {

		public static final String TYPE_DOCUMENT = "Boncde";

		@Override
		public TaBoncde dtoToEntity(DocumentIHM e) {
			return null;
		}

		@Override
		public DocumentIHM entityToDto(TaBoncde e) {
			return new DocumentIHM(e.getCodeDocument(),e.getLibelleDocument(),e.getDateDocument(),e.getMtHtCalc(),TYPE_DOCUMENT);
		}

		public List<DocumentIHM> listeEntityToDto(List<TaBoncde> l) {
			List<DocumentIHM> res = new ArrayList<DocumentIHM>();
			for (TaBoncde taDevis : l) {
				res.add(entityToDto(taDevis));
			}
			return res;
		}

	}

	class MapperDocumentIHMTaBonliv implements IlgrMapper<DocumentIHM, TaBonliv> {

		public static final String TYPE_DOCUMENT = "Bonliv";

		@Override
		public TaBonliv dtoToEntity(DocumentIHM e) {
			return null;
		}

		@Override
		public DocumentIHM entityToDto(TaBonliv e) {
			return new DocumentIHM(e.getCodeDocument(),e.getLibelleDocument(),e.getDateDocument(),e.getMtHtCalc(),TYPE_DOCUMENT);
		}

		public List<DocumentIHM> listeEntityToDto(List<TaBonliv> l) {
			List<DocumentIHM> res = new ArrayList<DocumentIHM>();
			for (TaBonliv taDevis : l) {
				res.add(entityToDto(taDevis));
			}
			return res;
		}

	}
	
	class MapperDocumentIHMTaAcompte implements IlgrMapper<DocumentIHM, TaAcompte> {

		public static final String TYPE_DOCUMENT = TaAcompte.TYPE_DOC;

		@Override
		public TaAcompte dtoToEntity(DocumentIHM e) {
			return null;
		}

		@Override
		public DocumentIHM entityToDto(TaAcompte e) {
			return new DocumentIHM(e.getCodeDocument(),e.getLibelleDocument(),e.getDateDocument(),e.getMtHtCalc(),TYPE_DOCUMENT);
		}

		public List<DocumentIHM> listeEntityToDto(List<TaAcompte> l) {
			List<DocumentIHM> res = new ArrayList<DocumentIHM>();
			for (TaAcompte taAcompte : l) {
				res.add(entityToDto(taAcompte));
			}
			return res;
		}

	}

	/* ************************************************************************************************************* */
	/* ************************************************************************************************************* */


}
