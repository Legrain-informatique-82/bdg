package fr.legrain.articles.statistiques.editors;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;

import fr.legrain.articles.dao.TaArticle;
import fr.legrain.articles.dao.TaArticleDAO;
import fr.legrain.gestCom.Appli.IlgrMapper;
import fr.legrain.lib.data.ModelObject;
import fr.legrain.tiers.dao.TaTiers;


public class DefaultFormPageController /*extends JPABaseControllerSWTStandard*/ implements IFormPageArticlesContoller {

	static Logger logger = Logger.getLogger(DefaultFormPageController.class.getName());

	private TaArticle masterEntity = null;
	private TaArticleDAO masterDAO = null;
	private DefaultFormPage vue = null;

	private IdentiteControllerMini identiteControllerMini = null;
	private CAControllerMini caControllerMini = null;
	private GraphControllerMini graphControllerMini = null;
	private GraphQuantiteControllerMini graphQuantiteControllerMini = null;
	private GraphQuantite2ControllerMini graphQuantite2ControllerMini = null;
	private AutreControllerMini autreControllerMini = null;
	private ParamControllerMini paramControllerMini = null;


	public DefaultFormPageController(DefaultFormPage vue) {
		this.vue = vue;
		identiteControllerMini = new IdentiteControllerMini(this,vue, null);

		caControllerMini = new CAControllerMini(this,vue, null);
		graphControllerMini = new GraphControllerMini(this,vue, null);
		autreControllerMini = new AutreControllerMini(this,vue, null);
		paramControllerMini = new ParamControllerMini(this,vue, null);
		graphQuantiteControllerMini = new GraphQuantiteControllerMini(this,vue, null);
		graphQuantite2ControllerMini = new GraphQuantite2ControllerMini(this,vue, null);
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
				
				caControllerMini.initialiseModelIHM(masterEntity,masterDAO);
				caControllerMini.bind();

				autreControllerMini.initialiseModelIHM(masterEntity,masterDAO);
				autreControllerMini.bind();
				
				graphControllerMini.initialiseModelIHM(masterEntity,masterDAO);
				
				graphQuantiteControllerMini.initialiseModelIHM(masterEntity,masterDAO);
				graphQuantite2ControllerMini.initialiseModelIHM(masterEntity,masterDAO);
				
			}
		} catch(Exception e) {
			logger.error("", e);
		} finally {
			vue.reflow();
		}
	}

	@Override
	public TaArticleDAO getMasterDAO() {
		return masterDAO;
	}

	@Override
	public TaArticle getMasterEntity() {
		return masterEntity;
	}

	@Override
	public void setMasterDAO(TaArticleDAO masterDAO) {
		this.masterDAO = masterDAO;
	}

	@Override
	public void setMasterEntity(TaArticle masterEntity) {
		this.masterEntity = masterEntity;
		//		bind();
		initialisationModel();
	}

	/* ************************************************************************************************************* */
	/* ************************************************************************************************************* */

	class IdentiteIHM extends ModelObject {
		String codeArticle = null;
		String libelleC = null;
		String libelleL = null;

		public IdentiteIHM() {}

		public IdentiteIHM(String codeTiers, String nomTiers,String prenomTiers) {
			super();
			this.codeArticle = codeTiers;
			this.libelleC = nomTiers;
			this.libelleL = prenomTiers;
		}

		public String getCodeArticle() {
			return codeArticle;
		}
		public void setCodeArticle(String codeTiers) {
			firePropertyChange("codeArticle", this.codeArticle, this.codeArticle = codeTiers);
		}
		public String getLibelleC() {
			return libelleC;
		}
		public void setLibelleC(String nomTiers) {
			firePropertyChange("libelleC", this.libelleC, this.libelleC = nomTiers);
		}
		public String getLibelleL() {
			return libelleL;
		}
		public void setLibelleL(String prenomTiers) {
			firePropertyChange("libelleL", this.libelleL, this.libelleL = prenomTiers);
		}

	}

	class MapperIdentite implements IlgrMapper<IdentiteIHM, TaArticle> {

		@Override
		public TaArticle dtoToEntity(IdentiteIHM e) {
			return null;
		}

		@Override
		public IdentiteIHM entityToDto(TaArticle e) {
			return new IdentiteIHM(e.getCodeArticle(),e.getLibellecArticle(),e.getLibellelArticle());
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

	class TiersIHM extends ModelObject {
		String codeTiers = null;
		String nom = null;
		String prenom = null;
		String entreprise = null;
		Double chiffreAffaire = null;

		public TiersIHM(String codeTiers, String nom, String prenom, String entreprise, Double chiffreAffaire) {
			super();
			this.codeTiers = codeTiers;
			this.nom = nom;
			this.prenom = prenom;
			this.entreprise = entreprise;
			this.chiffreAffaire = chiffreAffaire;
		}

		public String getCodeTiers() {
			return codeTiers;
		}

		public void setCodeTiers(String codeTiers) {
			firePropertyChange("codeTiers", this.codeTiers, this.codeTiers = codeTiers);
		}

		public String getNom() {
			return nom;
		}

		public void setNom(String nom) {
			firePropertyChange("nom", this.nom, this.nom = nom);
		}

		public String getPrenom() {
			return prenom;
		}

		public void setPrenom(String prenom) {
			firePropertyChange("prenom", this.prenom, this.prenom = prenom);
		}

		public String getEntreprise() {
			return entreprise;
		}

		public void setEntreprise(String entreprise) {
			firePropertyChange("entreprise", this.entreprise, this.entreprise = entreprise);
		}

		public Double getChiffreAffaire() {
			return chiffreAffaire;
		}

		public void setChiffreAffaire(Double chiffreAffaire) {
			firePropertyChange("chiffreAffaire", this.chiffreAffaire, this.chiffreAffaire = chiffreAffaire);
		}
		
	}

	class MapperTiersIHMTaTiers implements IlgrMapper<TiersIHM, TaTiers> {

		@Override
		public TaTiers dtoToEntity(TiersIHM e) {
			return null;
		}

		@Override
		public TiersIHM entityToDto(TaTiers e) {
			String entreprise = "";
			if(e.getTaEntreprise()!=null) {
				entreprise = e.getTaEntreprise().getNomEntreprise();
			}
			return new TiersIHM(e.getCodeTiers(),e.getNomTiers(),e.getPrenomTiers(),entreprise,0d);
		}

		public List<TiersIHM> listeEntityToDto(List<TaTiers> l) {
			List<TiersIHM> res = new ArrayList<TiersIHM>();
			for (TaTiers taTiers : l) {
				res.add(entityToDto(taTiers));
			}
			return res;
		}

	}

	/* ************************************************************************************************************* */
	/* ************************************************************************************************************* */

}
