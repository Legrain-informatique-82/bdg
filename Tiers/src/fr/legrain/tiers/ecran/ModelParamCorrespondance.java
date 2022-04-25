package fr.legrain.tiers.ecran;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import javax.persistence.EntityManager;

import fr.legrain.documents.dao.TaFactureDAO;
import fr.legrain.gestCom.Appli.ModelGeneralObjet;
import fr.legrain.gestCom.Module_Tiers.SWTTiers;
import fr.legrain.lib.data.AbstractLgrDAO;
import fr.legrain.lib.data.LibConversion;
import fr.legrain.tiers.dao.TaAdresse;
import fr.legrain.tiers.dao.TaEmail;
import fr.legrain.tiers.dao.TaTelephone;
import fr.legrain.tiers.dao.TaTiers;
import fr.legrain.tiers.dao.TaTiersDAO;

public class ModelParamCorrespondance<DTO> extends ModelGeneralObjet{
	
	AbstractLgrDAO dao=null;
	//private LinkedList<IHMEnteteDocument> listeObjet = new LinkedList<IHMEnteteDocument>();
	public ModelParamCorrespondance(TaTiersDAO dao, Class<TaTiers> typeObjet ) {
		super(dao , typeObjet);
		this.dao=dao;
		setListeEntity(new LinkedList<TaTiers>());
	}

	EntityManager em =new TaFactureDAO().getEntityManager();

	private String selectedTypeSelection=null;
	
	public LinkedList<SWTParamCorrespondance> remplirListe(TaTiers tiers) {
		getListeEntity().clear();
		getListeObjet().clear();
		List<SWTParamCorrespondance> listeFinal =	new ArrayList<SWTParamCorrespondance>(0);

		List<SWTParamCorrespondance> listeAdresse =	new ArrayList<SWTParamCorrespondance>(0);
		List<SWTParamCorrespondance> listeEmail =	new ArrayList<SWTParamCorrespondance>(0);
		List<SWTParamCorrespondance> listeTelephone =	new ArrayList<SWTParamCorrespondance>(0);

		SWTParamCorrespondance ihm = null;
		if(tiers!=null){
			for(TaAdresse adr : tiers.getTaAdresses()) {
				String adresse = "";
				if(adr.getAdresse1Adresse()!=null) adresse+= adr.getAdresse1Adresse()+" ";
				if(adr.getAdresse2Adresse()!=null) adresse+= adr.getAdresse2Adresse()+" ";
				if(adr.getAdresse3Adresse()!=null) adresse+= adr.getAdresse3Adresse()+" ";
				if(adr.getCodepostalAdresse()!=null) adresse+= adr.getCodepostalAdresse()+" ";
				if(adr.getVilleAdresse()!=null) adresse+= adr.getVilleAdresse()+" ";
				if(adr.getPaysAdresse()!=null) adresse+= adr.getPaysAdresse()+" ";
				ihm = new SWTParamCorrespondance();
				ihm.setType(SWTParamCorrespondance.TYPE_ADRESSE);
				ihm.setId(adr.getIdAdresse());
				ihm.setLibelle(adresse);
				ihm.setAdministratif(LibConversion.intToBoolean(adr.getCommAdministratifAdresse()));
				ihm.setCommercial(LibConversion.intToBoolean(adr.getCommCommercialAdresse()));
				listeAdresse.add(ihm);
				ihm=null;
			}
		}
		ihm = new SWTParamCorrespondance();
		ihm.setLibelle("Adresse");
		ihm.setType(SWTParamCorrespondance.TYPE_ADRESSE);
		ihm.setList(listeAdresse);
		listeFinal.add(ihm);
		if(tiers!=null){		
			for(TaEmail adr : tiers.getTaEmails()) {
				ihm = new SWTParamCorrespondance();
				ihm.setType(SWTParamCorrespondance.TYPE_EMAIL);
				ihm.setId(adr.getIdEmail());
				ihm.setLibelle(adr.getAdresseEmail());
				ihm.setAdministratif(LibConversion.intToBoolean(adr.getCommAdministratifEmail()));
				ihm.setCommercial(LibConversion.intToBoolean(adr.getCommCommercialEmail()));
				listeEmail.add(ihm);
				ihm=null;
			}
		}
		ihm = new SWTParamCorrespondance();
		ihm.setLibelle("EMail");
		ihm.setType(SWTParamCorrespondance.TYPE_EMAIL);
		ihm.setList(listeEmail);
		listeFinal.add(ihm);
		if(tiers!=null){
			for(TaTelephone adr : tiers.getTaTelephones()) {
				ihm = new SWTParamCorrespondance();
				ihm.setType(SWTParamCorrespondance.TYPE_TELEPHONE);
				ihm.setId(adr.getIdTelephone());
				ihm.setLibelle(adr.getNumeroTelephone());
				ihm.setAdministratif(LibConversion.intToBoolean(adr.getCommAdministratifTelephone()));
				ihm.setCommercial(LibConversion.intToBoolean(adr.getCommCommercialTelephone()));
				listeTelephone.add(ihm);
				ihm=null;
			}
		}
		ihm = new SWTParamCorrespondance();
		ihm.setLibelle("Telephone");
		ihm.setType(SWTParamCorrespondance.TYPE_TELEPHONE);
		ihm.setList(listeTelephone);
		listeFinal.add(ihm);

		setListeEntity(listeFinal);

		if(getListeEntity()!=null && getListeEntity().size()>0)
			getListeObjet().addAll(this.remplirListe());

		return getListeObjet();
	}
	

	public LinkedList<SWTParamCorrespondance> remplirListe() {
		SWTTiers ihm=null;
		LinkedList<SWTParamCorrespondance> listeObjet=new LinkedList<SWTParamCorrespondance>();
		
		listeObjet.clear();
		for (Object t : getListeEntity()) {
			listeObjet.add((SWTParamCorrespondance)t);
			ihm=null;
		}
		return listeObjet;
	}

}
