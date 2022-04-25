package fr.legrain.liasseFiscale.db;

/**
 * <p>Title: Gestion Commerciale</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2005</p>
 * <p>Company: </p>
 * @author Le Grain A.A
 * @version 1.0
 */

import java.io.File;
import java.io.FileInputStream;
import java.util.Properties;

import javax.swing.JOptionPane;

import org.apache.log4j.Logger;

import fr.legrain.lib.data.CtrlChamp;
import fr.legrain.lib.data.ExceptLgr;
import fr.legrain.lib.data.LgrConstantes;
import fr.legrain.lib.data.LibConversion;
import fr.legrain.lib.data.MessCtrlLgr;

/**
 * <p>Title: </p>
 * <p>Description: Controles généreaux de l'application de test.</p>
 * <p>Copyright: Copyright (c) 2005</p>
 * <p>Company: Le Grain SA</p>
 * @author Le Grain SA
 * @version 1.0
 */
public class CtrlLiasse extends CtrlChamp{
	static Logger logger = Logger.getLogger(CtrlLiasse.class.getName());
  protected /*private*/ Properties FListeCtrl; //Liste créée à partir du fichier ini //TStringList
  private String FFichierIni; //chemin du fichier ini contenant la liste des controle pour chacun des champs

  public void setFFichierIni(String value) { //procedure EcritFichierIni(value : string);
    //TmpCursor : TCursor;
    //TmpCursor :=Screen.Cursor;
    //Screen.Cursor := crSQLWait;
    try {
      if (!new File(value).exists()) {
        JOptionPane.showMessageDialog(LgrConstantes.getFocusedLgr(),"Le fichier .ini " + value + " est inexistant","ERREUR",JOptionPane.ERROR_MESSAGE); //mtError
        //abort;
      }
      else {
        FFichierIni = value;
        FListeCtrl.load(new FileInputStream(FFichierIni)); //FListeAssoCtrl.LoadFromFile(FFichierIni);
      }
    }
    catch (Exception e) {
      FFichierIni = "";
	  logger.error("setFFichierIni : "+"ERREUR");
	  
      //abort;
    }
    finally {
      //Screen.Cursor := TmpCursor;
    }
  }

  public String getFFichierIni() { //function LitFichierIni: string;
    return FFichierIni;
  }

  public CtrlLiasse() {
    FListeCtrl = new Properties();
    //FListeAssoCtrl.Values[C_CODE_TA_DONATEUR] := '$100';
  }



  /**
   * CtrlSaisie
   * @param Message TMessCtrlLGR
   */
  public void ctrlSaisie(MessCtrlLgr message) throws ExceptLgr {
  // super.ctrlSaisie(message); //controle généreaux à l'application
//   /*
   int codeCtrl=0;
   String codeControleStr;
   /** @todo Remettre le try {...} ? */
  // try {
     //i = FListeAssoCtrl.IndexOfName(Message.getNomTable() + "." +Message.getNomChamp());
     if(message==null)throw new ExceptLgr("Le contrôleur est null",0,true,0);
     if (FListeCtrl.containsKey(message.getNomTable() + "." +message.getNomChamp())) {
       codeControleStr = (String) FListeCtrl.get(message.getNomTable() + "." +message.getNomChamp()); //CodeControleStr = FListeAssoCtrl.ValueFromIndex[i];
        for(int j=0; j<codeControleStr.split(";").length; j++) {//for (j = 0 to Str_Count_Str(';', CodeControleStr)) {
          codeCtrl = LibConversion.stringToInteger(codeControleStr.split(";")[j]); //StrToInt(str_getstringelement(CodeControleStr, j + 1, ';'));
          switch(codeCtrl) {
            case 100: // Laisser numéro de controle pour pouvoir passer outre les controles !!
              switch(message.getModeObjet().getMode()) {
                case C_MO_CONSULTATION:  break;
                case C_MO_EDITION:  break;
                case C_MO_INSERTION:  break;
                case C_MO_IMPORTATION:break;
                default: break;
              }
              break;
            case 101: // controle d'unicité
              switch(message.getModeObjet().getMode()) {
                case C_MO_CONSULTATION: break;
                case C_MO_EDITION:
                  ctrl_ExistePasDansTableEx(message);
                  break;
                case C_MO_INSERTION:
                  ctrl_ExistePasDansTable(message);
                  break;
                case C_MO_IMPORTATION: break;
                default: break;
              }
              //MessageDlg('TAssoCtrl : code ['+IntToStr(CodeCtrl)+']', mtWarning, [mbOK], 0);
              break;
            case 102:
              //message.setMsgID("C_90001");
              ctrl_EstNonVide(message);
              //MessageDlg('TAssoCtrl : code ['+IntToStr(CodeCtrl)+']', mtWarning, [mbOK], 0);
              break;
            case 103: // controle si valeur existe dans table, utilisé lors de la recherche d'un id pour une valeur de clé étrangère
              switch(message.getModeObjet().getMode()) {
                case C_MO_CONSULTATION: break;
                case C_MO_EDITION:
                  ctrl_ExisteDansTable(message);
                  break;
                case C_MO_INSERTION:
                  ctrl_ExisteDansTable(message);
                  break;
                case C_MO_IMPORTATION: break;
                default: break;
             }
             //JOptionPane.showMessageDialog(null,"TTestSpecifiqueCtrl : code NON FINI["+String.valueOf(codeCtrl) + "]","ERREUR",JOptionPane.WARNING_MESSAGE); //mtWarning
             break;
           case 104: // controle un code postal
              switch(message.getModeObjet().getMode()) {
               case C_MO_CONSULTATION: break;
               case C_MO_EDITION:
                 ctrl_CodePostal(message);
                 break;
               case C_MO_INSERTION:
                 ctrl_CodePostal(message);
                 break;
               case C_MO_IMPORTATION: break;
               default: break;
             }
             //MessageDlg('TAssoCtrl : code NON FINI ['+IntToStr(CodeCtrl)+']', mtWarning, [mbOK], 0);
             break;
           case 105: // controle si une date à un format valide
              switch(message.getModeObjet().getMode()) {
               case C_MO_CONSULTATION: break;
               case C_MO_EDITION:
                 ctrl_Date(message);
                 break;
               case C_MO_INSERTION:
                 ctrl_Date(message);
                 break;
               case C_MO_IMPORTATION: break;
               default: break;
             }
             //MessageDlg('TAssoCtrl : code NON FINI ['+IntToStr(CodeCtrl)+']', mtWarning, [mbOK], 0);
             break;
           case 106: // controle si un montant qui doit être supérieur à 0 est supérieur à 0 !
              switch(message.getModeObjet().getMode()) {
               case C_MO_CONSULTATION: break;
               case C_MO_EDITION:
                 ctrl_MontantPositif(message);
                 break;
               case C_MO_INSERTION:
                 ctrl_MontantPositif(message);
                 break;
               case C_MO_IMPORTATION: break;
               default: break;
             }
             //MessageDlg('TAssoCtrl : code NON FINI ['+IntToStr(CodeCtrl)+']', mtWarning, [mbOK], 0);
             break;
           case 107: //controle si la nouvelle valeur n'est pas vide
             switch (message.getModeObjet().getMode()) {
               case C_MO_CONSULTATION:
                 break;
               case C_MO_EDITION:
                 ctrl_EstNonVide(message);
                 break;
               case C_MO_INSERTION:
                 ctrl_EstNonVide(message);
                 break;
               case C_MO_IMPORTATION:
                 break;
               default:
                 break;
             }
             break;//
           case 108: //controle si la nouvelle valeur est du type boolean
               switch (message.getModeObjet().getMode()) {
                 case C_MO_CONSULTATION:
                   break;
                 case C_MO_EDITION:
                	 ctrl_Boolean(message);
                   break;
                 case C_MO_INSERTION:
                	 ctrl_Boolean(message);
                   break;
                 case C_MO_IMPORTATION:
                   break;
                 default:
                   break;
               }
               break;//

           case 109: //controle si la nouvelle valeur ne contient que des Chiffres
               switch (message.getModeObjet().getMode()) {
                 case C_MO_CONSULTATION:
                   break;
                 case C_MO_EDITION:
                	 if (!ctrl_ValeurQueDesChiffres(message,false))throw new ExceptLgr(message,message.getMessageAffiche(),message.getCodeErreur(),(message.isAfficheMessage() && !message.isDejaAffiche()),JOptionPane.ERROR_MESSAGE);
                   break;
                 case C_MO_INSERTION:
                	 if (!ctrl_ValeurQueDesChiffres(message,false))throw new ExceptLgr(message,message.getMessageAffiche(),message.getCodeErreur(),(message.isAfficheMessage() && !message.isDejaAffiche()),JOptionPane.ERROR_MESSAGE);
                   break;
                 case C_MO_IMPORTATION:
                   break;
                 default:
                   break;
               }
               break;//
           case 110: //controle si la nouvelle valeur ne contient pas des caractères non autorisés
               switch (message.getModeObjet().getMode()) {
                 case C_MO_CONSULTATION:
                   break;
                 case C_MO_EDITION:
                	 if (!ctrl_ValeursKeyAutorisees(message))throw new ExceptLgr(message,message.getMessageAffiche(),message.getCodeErreur(),(message.isAfficheMessage() && !message.isDejaAffiche()),JOptionPane.ERROR_MESSAGE);
                   break;
                 case C_MO_INSERTION:
                	 if (!ctrl_ValeursKeyAutorisees(message))throw new ExceptLgr(message,message.getMessageAffiche(),message.getCodeErreur(),(message.isAfficheMessage() && !message.isDejaAffiche()),JOptionPane.ERROR_MESSAGE);
                   break;
                 case C_MO_IMPORTATION:
                   break;
                 default:
                   break;
               }
               break;// 
           case 111: //controle si la nouvelle valeur n'est pas vide sauf si elle est null
        	   //dans le cas de valeur facultative suivant condition
               switch (message.getModeObjet().getMode()) {
                 case C_MO_CONSULTATION:
                   break;
                 case C_MO_EDITION:
                   if (message.getValeur()!=null)
                	 ctrl_EstNonVide(message);
                   break;
                 case C_MO_INSERTION:
                     if (message.getValeur()!=null)
                    	 ctrl_EstNonVide(message);
                   break;
                 case C_MO_IMPORTATION:
                   break;
                 default:
                   break;
               }
               break;//
           case 112: // Laisser numéro de controle pour pouvoir passer outre les controles !!
               switch(message.getModeObjet().getMode()) {
                 case C_MO_CONSULTATION:  break;
                 case C_MO_EDITION: ctrl_Longueur(message); break;
                 case C_MO_INSERTION: ctrl_Longueur(message); break;
                 case C_MO_IMPORTATION: ctrl_Longueur(message); break;
                 default: break;
               }
               break;
           case 113: // Laisser numéro de controle pour pouvoir passer outre les controles !!
               switch(message.getModeObjet().getMode()) {
                 case C_MO_CONSULTATION:  break;
                 case C_MO_EDITION: ctrl_Majuscule(message); break;
                 case C_MO_INSERTION: ctrl_Majuscule(message); break;
                 case C_MO_IMPORTATION: ctrl_Majuscule(message); break;
                 default: break;
               }
               break;
              
               //ctrl_Majuscule
               default:
             JOptionPane.showMessageDialog(null,"TTestSpecifiqueCtrl : Code de controle inconnu :["+String.valueOf(codeCtrl) + "]","ERREUR",JOptionPane.WARNING_MESSAGE); //mtWarning
             break;
         }
       }

       } else {
         JOptionPane.showMessageDialog(null,"TTestSpecifiqueCtrl : Champ ou Table inconnu : ["+message.getNomTable() + "].["+message.getNomChamp() +"]","ERREUR",JOptionPane.WARNING_MESSAGE); //mtWarning
         throw new ExceptLgr();
       }
     //} catch(Exception e){
     //  e.printStackTrace();
           //abort;
     //}
// */
 }

  /**
   * Called by the garbage collector on an object when garbage collection
   * determines that there are no more references to the object.
   *
   * @throws Throwable the <code>Exception</code> raised by this method
   */
  protected void finalize() throws Throwable {
  }

}

