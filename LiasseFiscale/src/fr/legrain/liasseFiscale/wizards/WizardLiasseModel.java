package fr.legrain.liasseFiscale.wizards;

import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.exolab.castor.mapping.MappingException;
import org.exolab.castor.xml.MarshalException;
import org.exolab.castor.xml.Unmarshaller;
import org.exolab.castor.xml.ValidationException;

import com.borland.dx.sql.dataset.QueryDataSet;
import com.borland.dx.sql.dataset.QueryDescriptor;

import fr.legrain.gestCom.librairiesEcran.swt.LgrWorkEvent;
import fr.legrain.liasseFiscale.actions.Cle;
import fr.legrain.liasseFiscale.actions.Compte;
import fr.legrain.liasseFiscale.actions.InfoComplement;
import fr.legrain.liasseFiscale.actions.InfosCompta;
import fr.legrain.liasseFiscale.actions.Repart;
import fr.legrain.liasseFiscale.actions.Repartition;
import fr.legrain.liasseFiscale.db.ConstLiasse;
import fr.legrain.liasseFiscale.db.ibTables.IbTaRepart;
import fr.legrain.lib.data.IBQuLgr;
import fr.legrain.lib.data.LibConversion;

public class WizardLiasseModel extends WizardDocumentFiscalModel {
	
	static Logger logger = Logger.getLogger(WizardLiasseModel.class.getName());
	
	//private final String TYPE_TRAITEMENT_LIASSE_OUVERTURE = "LO";
	private String SOUS_TYPE_TRAITEMENT_LIASSE = null;
	private Integer annee = null;
	
	/**
	 * Répartition 
	 * @return
	 */
	public Repart repartitionDocument() {
		IbTaRepart ibRep = new IbTaRepart();
		if(logger.isDebugEnabled())
			logger.debug("debut repart");

		//TODO répartition :
		/*
		 * pour un type et un regime (fixe avant la repartition)
		 * 		pour chaque soustype du type traité
		 * 			on fait la répartition avec en entrée, le données d'une ou plusieurs origine
		 * on ajoute les données saisies "à la main"
		 * on fait les calculs des totaux
		 */
		
		//HashMap<String,Repartition> repFinal = getRepartition().repartitionXML(ibRep,getInfosCompta(),regime,TYPE_TRAITEMENT_LIASSE,SOUS_TYPE_TRAITEMENT_LIASSE);
		this.SOUS_TYPE_TRAITEMENT_LIASSE = "balance";
		HashMap<Cle,Repartition> repFinal = testRepartitionXML(ibRep,getInfosCompta(),getRegime().valeurSQL(),getTypeDocument().valeurSQL(),this.SOUS_TYPE_TRAITEMENT_LIASSE,this.anneeDocumentPDF);
//		this.SOUS_TYPE_TRAITEMENT_LIASSE = "immo";
//		repFinal = testRepartitionXML(ibRep,getInfosCompta(),getRegime().valeurSQL(),getTypeDocument().valeurSQL(),this.SOUS_TYPE_TRAITEMENT_LIASSE,annee);
		
		if(logger.isDebugEnabled())
			logger.debug("repart fini");
		
		return getRepartition();
	}
	
	public void repartition() {
		//origine compte
		//traitement
		//sous type traitement
		//regime
		//annee
	}
	
	/**
	 * Répartition spécifique à la liasse fiscale
	 * @param repartition
	 * @param aRepartir
	 * @param regime
	 * @param typeTraitement
	 * @param sousTypeTraitement
	 * @return
	 */
	public HashMap<Cle,Repartition> testRepartitionXML(IBQuLgr repartition,InfosCompta aRepartir, String regime, String typeTraitement, String sousTypeTraitement, Integer annee) {
		String cptARepartir,            // Compte traité
		nomPBilanDebit,             	// Nom de la colonne débit traité dans PBilan
		nomPBilanCredit;             	// Nom de la colonne crédit traité dans PBilan
		Double debit;                 	// Débit dans la balance
		Double credit;               	// Crédit dans la balance
		
		QueryDescriptor qd = null;
		
		getRepartition().initListeRepartition();
		
//		final String sousTypeBalance = "balance";
//		final String sousTypeImmo = "immo";
//		final String sousTypeAmmort = "ammort";
//		final String sousType8 = "8";
//		final String sousType11 = "11";
//		
//		final String origineBalance = "Bal";
		
		/////////////////////////////////////////////////////////////////////////
		//TODO traitement des comptes en fonction de leurs origines et du type de traitement en cours
		QueryDataSet traitements = new QueryDataSet();
		qd = new QueryDescriptor(repartition.getFIBBase(),"select * from "+ConstLiasse.C_NOM_TA_TRAITEMENTS+" where "+ConstLiasse.C_REPART_REGIME+"='"+regime+"' and "+ConstLiasse.C_REPART_TYPE_TRAITEMENT+"='"+typeTraitement+"' and "+ConstLiasse.C_REPART_ANNEE+"='"+annee+"'");
		traitements.close();
		traitements.setQuery(qd);
		traitements.open();
		
		Map<String,String> mapSousTypeTraitement = new LinkedHashMap<String,String>();
		if(!traitements.isEmpty()) {
			do {
				mapSousTypeTraitement.put(traitements.getString(ConstLiasse.C_TRAITEMENTS_SOUS_TYPE_TRAITEMENT), traitements.getString(ConstLiasse.C_TRAITEMENTS_LIBELLE_SOUS_TYPE_TRAITEMENT));
			} while(traitements.next());
		}
		int nbSousTypeTraitement = mapSousTypeTraitement.size();
		
		Map<String,String[]> originePourSousType = new LinkedHashMap<String,String[]>();
		traitements.first();
		if(!traitements.isEmpty()) {
			do {
				originePourSousType.put(traitements.getString(ConstLiasse.C_TRAITEMENTS_SOUS_TYPE_TRAITEMENT), traitements.getString(ConstLiasse.C_TRAITEMENTS_ORIGINES_DONNEES).split(";"));
			} while(traitements.next());
		}

		fireBeginWork(new LgrWorkEvent(this,aRepartir.getListeCompte().size()*nbSousTypeTraitement+aRepartir.getListeSaisieComplement().size()));
		
		//Traitement de la balance
//		sousTypeTraitement = "balance"; //1,2,3,4 => balance et compte de resultat
//		sousTypeTraitement = "immo"; //5, (5 bis ?)
////		sousTypeTraitement = "ammort"; //6
////		//7 a faire, infos de la balance?
////		sousTypeTraitement = "dette_creance"; //8
////		sousTypeTraitement = "resultat_fiscal"; //9
////		//10 manuel
////		sousTypeTraitement = "renseignement_divers"; //11 
//		sousTypeTraitement = "+/- values"; //12 a voir, pb si plus de 8 immo
////		//13 manuel
////		//14,15 et debut manuel + reprise annee precedente + reprise infos dossiers 
//		sousTypeTraitement = "emprunts";
//		String origineCompte = "Bal";

		for (String key : mapSousTypeTraitement.keySet()) {
			
			fireSubTask(new LgrWorkEvent(this,aRepartir.getListeCompte().size(),mapSousTypeTraitement.get(key)));

			qd = new QueryDescriptor(repartition.getFIBBase(),"select * from "+ConstLiasse.C_NOM_TA_REPART+" where "+ConstLiasse.C_REPART_REGIME+"='"+regime+"' and "+ConstLiasse.C_REPART_TYPE_TRAITEMENT+"='"+typeTraitement+"' and "+ConstLiasse.C_REPART_SOUS_TYPE_TRAITEMENT+"='"+key+"' and "+ConstLiasse.C_REPART_ANNEE+"='"+annee+"'");
			//si suppression de cette requete pense a remettre tous les champs dans le filtre
			repartition.getFIBQuery().close();
			repartition.getFIBQuery().setQuery(qd);
			repartition.getFIBQuery().open();
			
			//remplissage d'une liste pour plus de facilite par la suite
			List<String> listeDesOriginesATraiter = new LinkedList<String>();
			for (int i = 0; i < originePourSousType.get(key).length; i++) {
				listeDesOriginesATraiter.add(originePourSousType.get(key)[i]);
			}

			for (Compte c : aRepartir.getListeCompte()) {
				cptARepartir = c.getNumero();
				
				if(listeDesOriginesATraiter.contains(c.getOrigine())) {
					debit = c.getMtDebit();
					credit = c.getMtCredit();

					//Traitement des soldes Report Debut
					nomPBilanDebit = ConstLiasse.C_REPART_MT_DEBIT;
					nomPBilanCredit = ConstLiasse.C_REPART_MT_CREDIT;

					getRepartition().addRepartition(nomPBilanDebit,nomPBilanCredit,cptARepartir,c.getOrigine(),regime,typeTraitement,key,annee,debit,credit,repartition);
					//	getRepartition().addRepartition(nomPBilanDebit,nomPBilanCredit,cptARepartir,regime,typeTraitement,"11",annee,debit,credit,repartition);
					
//					debit = c.getMtDebit2();
//					credit = c.getMtCredit2();
//					nomPBilanDebit = ConstLiasse.C_REPART_MT_DEBIT_2;
//					nomPBilanCredit = ConstLiasse.C_REPART_MT_CREDIT_2;
//					getRepartition().addRepartition(nomPBilanDebit,nomPBilanCredit,cptARepartir,regime,typeTraitement,key,annee,debit,credit,repartition);
					/////////////////////////////////////////////////////////////////////////
					//Traitement des soldes Fin
					nomPBilanDebit = ConstLiasse.C_REPART_MT_DEBIT_3;
					nomPBilanCredit = ConstLiasse.C_REPART_MT_CREDIT_3;
					debit = c.getMtDebit3();
					credit = c.getMtCredit3();
					//ATTENTION POSITION N et N-1 dans le fichier texte			
					getRepartition().addRepartition(nomPBilanDebit,nomPBilanCredit,cptARepartir,c.getOrigine(),regime,typeTraitement,key,annee,debit,credit,repartition);
					//	getRepartition().addRepartition(nomPBilanDebit,nomPBilanCredit,cptARepartir,regime,typeTraitement,"immo",annee,debit,credit,repartition);
					//	getRepartition().addRepartition(nomPBilanDebit,nomPBilanCredit,cptARepartir,regime,typeTraitement,"ammort",annee,debit,credit,repartition);
					//	getRepartition().addRepartition(nomPBilanDebit,nomPBilanCredit,cptARepartir,regime,typeTraitement,"8",annee,debit,credit,repartition);
					//	getRepartition().addRepartition(nomPBilanDebit,nomPBilanCredit,cptARepartir,regime,typeTraitement,"11",annee,debit,credit,repartition);
				}
				fireWork(null);

			}
		}

		/////////////////////////////////////////////////////////////////////////
		fireSubTask(new LgrWorkEvent(this,aRepartir.getListeSaisieComplement().size(),"Prise en compte de la saisie manuelle"));
		//TODO ajout des "compléments"
		Repartition compl;
		//for (InfoComplement complement : aRepartir.getListeSaisieComplement()) {
		
		InfoComplement complement;
		for (Cle cle : aRepartir.getListeSaisieComplement().keySet()) {
			complement = aRepartir.getListeSaisieComplement().get(cle);
			compl = new Repartition();
			compl.setValeur(complement.getValeur1());
			//pour le montant on prend la 1ere valeur (valeur1) et on la converti en double
			if(LibConversion.queDesChiffres(complement.getValeur1(), true))
				compl.setMontant(LibConversion.stringToDouble(complement.getValeur1()));
			else {
				compl.setMontant(0d);
			}
			getRepartition().getListeRepartition().put(new Cle(complement.getCle(),sousTypeTraitement),compl);
			fireWork(null);
		}
		
		fireSubTask(new LgrWorkEvent(this,aRepartir.getListeSaisieComplement().size(),"Totaux"));
		//TODO calcul totaux
		getRepartition().calculTotaux(null,regime,typeTraitement,sousTypeTraitement);
		
		fireEndWork(null);
		traitements.close();
		return getRepartition().getListeRepartition();
	}
	
	/**
	 * 
	 * @param xmlFile - fichier source
	 * @return Document
	 */
	public WizardLiasseModel lectureXML(String xmlFile) {
		//binding de this avec Castor XML
		try {
			initMapping();
			FileReader reader = new FileReader(xmlFile);
			//Create a new Unmarshaller
			Unmarshaller unmarshaller = new Unmarshaller(WizardLiasseModel.class);
			unmarshaller.setMapping(mapping);
			//Unmarshal the object
			WizardLiasseModel model = (WizardLiasseModel)unmarshaller.unmarshal(reader);
			reader.close();
			return model;
		} catch (IOException e) {
			logger.error("",e);
		} catch (MappingException e) {
			logger.error("",e);
		} catch (MarshalException e) {
			logger.error("",e);
		} catch (ValidationException e) {
			logger.error("",e);
		}
		return null;
	}
	
}
