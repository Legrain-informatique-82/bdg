package fr.legrain.appli.tiers.formlayout;


import java.util.*;

import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;

import org.apache.log4j.*;
import com.borland.dx.dataset.*;
import fr.legrain.appli.tiers.*;
import fr.legrain.lib.data.*;
import fr.legrain.lib.gui.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2005</p>
 * <p>Company: Le Grain SA</p>
 * @author Le Grain SA
 * @version 1.0
 */

public class PaTiersController extends BaseController implements NavigationListener,DataChangeListener {

  static Logger logger = Logger.getLogger(PaTiersController.class.getName());

  private PaTiers2 vue = null; //vue
  //modèle
  private IBTestTa_Adresse ibTestTa_Adresse = new IBTestTa_Adresse(); //table adresse
  private IBTestTa_Tiers ibTestTa_Tiers     = new IBTestTa_Tiers(); //table tiers

  //correspondance composant graphique/champs bdd
  static private HashMap mapAdresseComposantChamps = null;
  static private HashMap mapTiersComposantChamps   = null;

  public PaTiersController(PaTiers2 vue) {
    this.vue = vue;
    initComposantsVue();
    initMapComposantChamps();

    vue.grilleAdresses.setDataSet(ibTestTa_Adresse.getFIBQuery());
    ibTestTa_Adresse.getFIBQuery().addNavigationListener(this);
    ibTestTa_Tiers.getFIBQuery().addNavigationListener(this);
    actualiserFormTiers();
    actualiserFormAdresse();

    vue.btnTiersPrec.addActionListener(new PrecAction());
    vue.btnTiersSuiv.addActionListener(new SuivAction());

    //Enregistrement des controles de sortie de champs des formulaires
    Iterator iteTiers = mapTiersComposantChamps.keySet().iterator();
    while(iteTiers.hasNext()) {
      ((JComponent)iteTiers.next()).setInputVerifier(new CtrlSortieChampsTiers());
    }
    Iterator iteAdresse = mapAdresseComposantChamps.keySet().iterator();
    while(iteAdresse.hasNext()) {
      ((JComponent)iteAdresse.next()).setInputVerifier(new CtrlSortieChampsAdresse());
    }

  }

  /**
   * Initialisation des composants graphiques.
   */
  private void initComposantsVue() {
    vue.grilleAdresses.setCellSelectionEnabled(false);
    vue.grilleAdresses.setColumnSelectionAllowed(false);
    vue.grilleAdresses.setRowSelectionAllowed(true);
    vue.grilleAdresses.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
  }

  /**
   * Initialisation des correspondance entre les champs de formulaire et les champs de bdd
   */
  private void initMapComposantChamps() {
    //formulaire des adresses
    if(mapAdresseComposantChamps==null) {
      mapAdresseComposantChamps = new HashMap();
      mapAdresseComposantChamps.put(vue.tfAdresse1,ibTestTa_Adresse.C_ADRESSE_1_TA_ADRESSE);
      mapAdresseComposantChamps.put(vue.tfAdresse2,ibTestTa_Adresse.C_ADRESSE_2_TA_ADRESSE);
      mapAdresseComposantChamps.put(vue.tfAdresse3, null);
      mapAdresseComposantChamps.put(vue.tfCodePostal,ibTestTa_Adresse.C_CP_TA_ADRESSE);
      mapAdresseComposantChamps.put(vue.tfVille,ibTestTa_Adresse.C_VILLE_TA_ADRESSE);
      mapAdresseComposantChamps.put(vue.tfPays,ibTestTa_Adresse.C_PAYS_TA_ADRESSE);
    }

    //formulaire des tiers
    if(mapTiersComposantChamps==null) {
      mapTiersComposantChamps = new HashMap();
      mapTiersComposantChamps.put(vue.tfCodeTiers,ibTestTa_Tiers.C_CODE_TA_TIERS);
      mapTiersComposantChamps.put(vue.cbActif, ibTestTa_Tiers.C_ACTIF_TA_TIERS);
///////////////////////////////////////////////
      class ChangeState implements ChangeListener {
        public void stateChanged(ChangeEvent e) {
         // System.out.println("stateChanged");
        }
      }
      vue.cbActif.addChangeListener(new ChangeState());
///////////////////////////////////////////////
      mapTiersComposantChamps.put(vue.tfTypeTiers, null);
      mapTiersComposantChamps.put(vue.epRemarque,ibTestTa_Tiers.C_REMARQUE_TA_TIERS);
    }
  }

  /**
   * MAJ du formulaire des adresses.
   * @todo A Finir
   */
  public void actualiserFormAdresse() {
    remplirFormulaire(mapAdresseComposantChamps, ibTestTa_Adresse);
  }

  /**
   * MAJ du formulaire des tiers.
   * @todo A Finir
   */
  public void actualiserFormTiers() {
    remplirFormulaire(mapTiersComposantChamps, ibTestTa_Tiers);
  }

  /**
   * Vide le formulaire des adresses
   */
  public void resetFormAdresse() {
    vue.tfAdresse1.setText(null);
    vue.tfAdresse2.setText(null);
    vue.tfAdresse3.setText(null);
    vue.tfCodePostal.setText(null);
    vue.tfVille.setText(null);
    vue.tfPays.setText(null);
  }

  /**
   * postRow
   * @param event DataChangeEvent
   */
  public void postRow(DataChangeEvent event) {
    actualiserFormAdresse();
  }

  /**
   * dataChanged
   * @param event DataChangeEvent
   */
  public void dataChanged(DataChangeEvent event) {
    actualiserFormAdresse();
  }

  public void navigated(NavigationEvent event) {
    actualiserFormAdresse();
    actualiserFormTiers();
  }

  class PrecAction implements ActionListener {
    public void actionPerformed(ActionEvent e) {
      ibTestTa_Tiers.previous();
    }
  }

  class SuivAction implements ActionListener {
    public void actionPerformed(ActionEvent e) {
      ibTestTa_Tiers.next();
    }
  }


  class CtrlSortieChampsTiers extends InputVerifier {
    public boolean verify(JComponent input) {
      boolean res = true;
      try {
        logger.debug(input.getName());
        /////////////////////////////////***********************/////////////////
        /** @todo controle */
        String nomChamp = null;
        String nouvelleValeur = null;

        ibTestTa_Tiers.getFModeObjet().setMode(ModeObjet.C_MO_EDITION); /** @todo gestion du mode de l'objet */

        if(mapTiersComposantChamps.get(input)!=null) { //le champs est relié à la bdd, il faut faire un controle
          nomChamp = mapTiersComposantChamps.get(input).toString();
          nouvelleValeur = stringValue(input);
          input.setInputVerifier(null); //Work Around pour le bug #4532517 JDK 1.4 pour la perte de focus à l'intérieur des méthodes d'un InputVerifier (Résolu avec le JDK 1.5)
          res = ibTestTa_Tiers.verifChamp(nomChamp, nouvelleValeur);
          input.setInputVerifier(this); //Work Around pour le bug #4532517 JDK 1.4
        }
        return res;
        ///////////////////////////////////*******************///////////////////
      } catch(Exception e) {
        logger.error("ERREUR",e);
        return false;
      }
    }
  }

  class CtrlSortieChampsAdresse extends InputVerifier {
    public boolean verify(JComponent input) {
      //System.out.println(input.getName());
      ///////////////////////////////////*******************///////////////////

      ///////////////////////////////////*******************///////////////////
      return true;
    }
  }

} //fin PaTiersConroller
