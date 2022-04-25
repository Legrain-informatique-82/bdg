package fr.legrain.document.etat.devis.edition;

import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Collection;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;

import org.apache.log4j.Logger;
import org.eclipse.birt.report.viewer.ViewerPlugin;
import org.eclipse.birt.report.viewer.utilities.WebViewer;
import org.eclipse.core.runtime.Path;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.browser.IWorkbenchBrowserSupport;

import fr.legrain.dossier.dao.TaInfoEntreprise;
import fr.legrain.dossier.dao.TaInfoEntrepriseDAO;
import fr.legrain.edition.ImprimeObjet;
import fr.legrain.edition.actions.AttributElementResport;
import fr.legrain.edition.actions.ConstEdition;
import fr.legrain.edition.actions.MakeDynamiqueReport;
import fr.legrain.edition.dynamique.EditionAppContext;
import fr.legrain.edition.dynamique.FonctionGetInfosXmlAndProperties;
import fr.legrain.gestCom.Appli.Const;
import fr.legrain.gestCom.librairiesEcran.workbench.LgrPartListener;
import fr.legrain.lib.gui.grille.LgrTableViewer;
import fr.legrain.libLgrBirt.WebViewerUtil;

public class EditionTable {
	
	static Logger logger = Logger.getLogger(EditionTable.class.getName()); 
	private InfosPresentation[] infos = null;
	private EntityManager em = null;
	private LgrTableViewer tableViewer = null;
	private String nomClassObjIHM = null;
	private LinkedList<Resultat> listeRes = null;
	private String titre = null;
	private String sousTitre = null;
	private String titreOngletEdition = null;
	
	public EditionTable(InfosPresentation[] infos, EntityManager em,
			LgrTableViewer tableViewer, String nomClassObjIHM,
			LinkedList<Resultat> listeRes, String titre, String sousTitre, String titreOngletEdition) {
		
		this.infos = infos;
		this.em = em;
		this.tableViewer = tableViewer;
		this.nomClassObjIHM = nomClassObjIHM;
		this.listeRes = listeRes;
		this.titre = titre;
		this.sousTitre = sousTitre;
		this.titreOngletEdition = titreOngletEdition;
	}
	
	/**
	 * Méthode d'impression de la liste
	 */
	public void imprJPA() {
		
		String[] totaux = new String[infos.length];
		
		// Préparation des entités pour la génération du document
		TaInfoEntrepriseDAO taInfoEntrepriseDAO = new TaInfoEntrepriseDAO(em);
		TaInfoEntreprise infoEntreprise = taInfoEntrepriseDAO.findInstance();

		FonctionGetInfosXmlAndProperties fonctionGetInfosXmlAndProperties = new FonctionGetInfosXmlAndProperties();
		fonctionGetInfosXmlAndProperties.cleanValueMapAttributeTable();

		String valueMap = null;
		String[] valueAttributes = null;
		LinkedList<String> listeAttribut = new LinkedList<String>();
		String typeBirt = null;
		String alignement = null;

		String birtDataTypeFloat = "float";
		String birtDataTypeInteger = "integer";
		String birtDataTypeString = "string";
		String birtDataTypeDecimal = "decimal";
		String birtAlignRight = "right";
		String birtAlignLeft = "left";

		/*
		 * Generation des informations decrivant la structure/presentation de la
		 * table. Les lignes generees doivent etre au meme format que celle du
		 * fichier "AttributeTableEdition.properties"
		 */
		for (int i = 0; i < infos.length; i++) {
			try {
				// titre
				valueMap = infos[i].getTitre() + ";" + infos[i].getTaille()
						+ ";center;medium;bold;%;string;vide;vide";
				valueAttributes = valueMap.split(";");
				fonctionGetInfosXmlAndProperties.getMapAttributeTablHead().put(
						Resultat.debutNomChamp + (i + 1),
						new AttributElementResport(valueAttributes));

				// contenu
				if (infos[i].getTypeString() != null) {
					if (infos[i].getTypeString().equals(ConstVisualisation.typeFloat)) {
						typeBirt = birtDataTypeFloat;
						alignement = birtAlignRight;
					} else if (infos[i].getTypeString().equals(ConstVisualisation.typeBigDecimal)
							|| infos[i].getTypeString().equals(ConstVisualisation.typeDouble)) {
						typeBirt = birtDataTypeDecimal;
						alignement = birtAlignRight;
					} else if (infos[i].getTypeString().equals(ConstVisualisation.typeInteger)) {
						typeBirt = birtDataTypeInteger;
						alignement = birtAlignRight;
					} else {
						typeBirt = birtDataTypeString;
						alignement = birtAlignLeft;
					}
				}

				valueMap = Resultat.debutNomChamp + (i + 1) + ";"
						+ infos[i].getTaille() + ";" + alignement
						+ ";medium;bold;%;" + typeBirt + ";vide;vide";
				valueAttributes = valueMap.split(";");
				fonctionGetInfosXmlAndProperties.getMapAttributeTablDetail()
						.put(Resultat.debutNomChamp + (i + 1),
								new AttributElementResport(valueAttributes));

				listeAttribut.add(Resultat.debutNomChamp + (i + 1));
			} catch (Exception e) {
				logger.error("",e);
			}
		}
		LinkedHashMap<String, AttributElementResport> mapAttributeTablHead = fonctionGetInfosXmlAndProperties
				.getMapAttributeTablHead();
		LinkedHashMap<String, AttributElementResport> mapAttributeTablDetail = fonctionGetInfosXmlAndProperties
				.getMapAttributeTablDetail();

		// Passage de contexte au Birt Viewer
		ViewerPlugin.getDefault().getPluginPreferences().setValue(
				WebViewer.APPCONTEXT_EXTENSION_KEY,
				EditionAppContext.APP_CONTEXT_NAME);

		// Recuperation des objets a imprimer
		Collection<Resultat> collectionResultat = remplirListe();

		// Initialisation des colonnes numérique sans valeur avec des zéros,
		// sinon plante lors de l'extraction
		for (Resultat resultat : collectionResultat) {
			for (int i = 0; i < infos.length; i++) {
				if ((infos[i].getTypeString().equals(ConstVisualisation.typeBigDecimal)
						|| infos[i].getTypeString().equals(ConstVisualisation.typeFloat)
						|| infos[i].getTypeString().equals(ConstVisualisation.typeDouble) 
						|| infos[i].getTypeString().equals(ConstVisualisation.typeInteger))
						&& (resultat.findValue(i + 1) == null 
						|| resultat.findValue(i + 1).equals(""))) {
					resultat.changeValue(i + 1, "0");
				}
			}
		}

		ImprimeObjet.clearListAndMap();
		ImprimeObjet.l.addAll(collectionResultat);

		/************** pour préparer plusieurs objects construisirent l'edition ***************/
		ImprimeObjet.m.put(Resultat.class.getSimpleName(), ImprimeObjet.l);

		/**************************************************************************************/

		/*
		 * La liste des attibuts de la classe qui sont utilise pour generer le
		 * script dans l'edition est en general retrouve a partir du mapping
		 * dozer et des champ affiches dans l'interface. Ici il n'y a pas de
		 * mapping dozer donc, il faut donner cette liste explicitement.
		 */
		fonctionGetInfosXmlAndProperties.getInfosObjetJPA(new Resultat(),listeAttribut);

		ConstEdition constEdition = new ConstEdition();
		String nomDossier = null;

		/**
		 * nombreLine ==> le nombre de ligne dans interface
		 */
		int nombreLine = collectionResultat.size();

		if (nombreLine == 0) {
			MessageDialog.openWarning(PlatformUI.getWorkbench()
					.getActiveWorkbenchWindow().getShell(),
					ConstEdition.TITRE_MESSAGE_EDITION_VIDE,
					ConstEdition.EDITION_VIDE);
		} else {
			if (infoEntreprise.getIdInfoEntreprise() == 0) {
				nomDossier = ConstEdition.INFOS_VIDE;
			} else {
				nomDossier = infoEntreprise.getNomInfoEntreprise();
			}
	
			constEdition.addValueList(tableViewer, nomClassObjIHM);
	

			/**
			 * pathFileReport ==> le path de ficher de edition dynamique
			 */
			String folderEditionDynamique = Const.C_RCP_INSTANCE_LOCATION + "/" + Const.C_NOM_PROJET_TMP + "/" + Resultat.class.getSimpleName();
			constEdition.makeFolderEditions(folderEditionDynamique);
			String nomVue = "visualisation";
			Path pathFileReport = new Path(folderEditionDynamique + "/" + nomVue + ".rptdesign");
			final String pathFileReportDynamic = pathFileReport.toPortableString();

			MakeDynamiqueReport dynamiqueReport = new MakeDynamiqueReport(
					constEdition.getNameTableEcran(), constEdition
							.getNameTableBDD(), pathFileReportDynamic, nomVue,
					ConstEdition.PAGE_ORIENTATION_LANDSCAPE, nomDossier,
					infos.length);

			/**************************************************************/
			dynamiqueReport
					.setFonctionGetInfosXml(fonctionGetInfosXmlAndProperties);
			dynamiqueReport.setNomObjet(Resultat.class.getSimpleName());
			dynamiqueReport.setSimpleNameEntity(Resultat.class.getSimpleName());
			/**************************************************************/
			Map<String, AttributElementResport> attribuGridHeader = new LinkedHashMap<String, AttributElementResport>();

			if(titre!=null)
				attribuGridHeader.put(titre,
					new AttributElementResport("",
							ConstEdition.TEXT_ALIGN_CENTER,
							ConstEdition.FONT_SIZE_XX_LARGE,
							ConstEdition.FONT_WEIGHT_BOLD, "",
							ConstEdition.COLUMN_DATA_TYPE_STRING,
							ConstEdition.UNITS_VIDE, ""));

			if(sousTitre!=null)
				attribuGridHeader.put(
					sousTitre, new AttributElementResport("",
							ConstEdition.TEXT_ALIGN_CENTER,
							ConstEdition.FONT_SIZE_X_LARGE,
							ConstEdition.FONT_WEIGHT_BOLD, "",
							ConstEdition.COLUMN_DATA_TYPE_STRING,
							ConstEdition.UNITS_VIDE, ConstEdition.COLOR_GRAY));

			// ConstEdition.CONTENT_COMMENTS =
			// ConstEditionArticle.COMMENTAIRE_EDITION_DEFAUT;
			ConstEdition.CONTENT_COMMENTS = "COMMENTAIRE";
			dynamiqueReport.initializeBuildDesignReportConfig();
			dynamiqueReport.makePageMater("1", "1", "1", "1", "100");
			dynamiqueReport.makeReportHeaderGrid(3, 5, 100,
					ConstEdition.UNITS_PERCENTAGE, attribuGridHeader);

			dynamiqueReport.biuldTableReport("100",
					ConstEdition.UNITS_PERCENTAGE, nomVue, 1, 1, 2, "5000",
					mapAttributeTablHead, mapAttributeTablDetail);
			dynamiqueReport.savsAsDesignHandle();

			String url = WebViewerUtil.debutURL();

			url += "run?__report=";

			url += pathFileReportDynamic;

			url += "&__document=doc" + new Date().getTime();
			url += "&__format=pdf";
			logger.debug("URL edition: " + url);

			final String finalURL = url;
			final String titreOnglet = titreOngletEdition!=null?titreOngletEdition:"Edition";
			PlatformUI.getWorkbench().getDisplay().asyncExec(new Runnable() {
				public void run() {
					try {
						LgrPartListener.getLgrPartListener().setLgrActivePart(
								null);
						PlatformUI.getWorkbench().getBrowserSupport()
								.createBrowser(
										IWorkbenchBrowserSupport.AS_EDITOR,
										"myId", titreOnglet, "").openURL(
										new URL(finalURL));
					} catch (PartInitException e) {
						logger.error("", e);
					} catch (MalformedURLException e) {
						logger.error("", e);
					}
				}
			});
		}
	}
	
	public LinkedList<Resultat> remplirListe() {
		return listeRes;
	}
}
