package fr.legrain.appli.tiers.formlayout;

import com.jeta.forms.components.separator.TitledSeparator;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;
import fr.legrain.lib.gui.grille.LgrJdbTable;
import fr.legrain.lib.gui.grille.LgrTable;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;


public class PaTiers2 extends JPanel
{
   JTabbedPane tabbedPane = new JTabbedPane();
   JLabel laAdresse = new JLabel();
   JLabel laCodePostal = new JLabel();
   JTextField tfAdresse1 = new JTextField();
   JTextField tfAdresse2 = new JTextField();
   JTextField tfCodePostal = new JTextField();
   JLabel laVille = new JLabel();
   JTextField tfVille = new JTextField();
   JLabel laPays = new JLabel();
   JTextField tfPays = new JTextField();
   JCheckBox cbAdressePrincipale = new JCheckBox();
   JCheckBox cbAdresseLivraison = new JCheckBox();
   JButton btnAjouterAdresse = new JButton();
   JButton btnSupprimerAdresse = new JButton();
   JButton btnEnregistrerAdresse = new JButton();
   JTextField tfAdresse3 = new JTextField();
   LgrJdbTable grilleAdresses = new LgrJdbTable();
   JLabel laNom = new JLabel();
   JComboBox tfNomContact = new JComboBox();
   JLabel laType = new JLabel();
   JComboBox cbTypeContact = new JComboBox();
   JLabel laValeur = new JLabel();
   JTextField tfValeurContact = new JTextField();
   JLabel laCommentaire = new JLabel();
   JTextField tfCommentaireContact = new JTextField();
   LgrTable grilleContacts = new LgrTable();
   JButton btnEnregistrerContact = new JButton();
   JButton btnAjouterContact = new JButton();
   JButton btnSupprimerContact = new JButton();
   JLabel laCode = new JLabel();
   JTextField tfCodeTiers = new JTextField();
   JLabel laActif = new JLabel();
   JLabel laTypeTiers = new JLabel();
   JLabel laRemarque = new JLabel();
   JCheckBox cbActif = new JCheckBox();
   JTextField tfTypeTiers = new JTextField();
   JEditorPane epRemarque = new JEditorPane();
   JButton btnImprimerTiers = new JButton();
   JButton btnEnregistrerTiers = new JButton();
   JButton btnAnnulerTiers = new JButton();
   JButton btnTiersPrec = new JButton();
   JButton btnTiersSuiv = new JButton();

   /**
    * Default constructor
    */
   public PaTiers2()
   {
      initializePanel();
   }

   /**
    * Adds fill components to empty cells in the first row and first column of the grid.
    * This ensures that the grid spacing will be the same as shown in the designer.
    * @param cols an array of column indices in the first row where fill components should be added.
    * @param rows an array of row indices in the first column where fill components should be added.
    */
   void addFillComponents( Container panel, int[] cols, int[] rows )
   {
      Dimension filler = new Dimension(10,10);

      boolean filled_cell_11 = false;
      CellConstraints cc = new CellConstraints();
      if ( cols.length > 0 && rows.length > 0 )
      {
         if ( cols[0] == 1 && rows[0] == 1 )
         {
            /** add a rigid area  */
            panel.add( Box.createRigidArea( filler ), cc.xy(1,1) );
            filled_cell_11 = true;
         }
      }

      for( int index = 0; index < cols.length; index++ )
      {
         if ( cols[index] == 1 && filled_cell_11 )
         {
            continue;
         }
         panel.add( Box.createRigidArea( filler ), cc.xy(cols[index],1) );
      }

      for( int index = 0; index < rows.length; index++ )
      {
         if ( rows[index] == 1 && filled_cell_11 )
         {
            continue;
         }
         panel.add( Box.createRigidArea( filler ), cc.xy(1,rows[index]) );
      }

   }

   /**
    * Helper method to load an image file from the CLASSPATH
    * @param imageName the package and name of the file to load relative to the CLASSPATH
    * @return an ImageIcon instance with the specified image file
    * @throws IllegalArgumentException if the image resource cannot be loaded.
    */
   public ImageIcon loadImage( String imageName )
   {
      try
      {
         ClassLoader classloader = getClass().getClassLoader();
         java.net.URL url = classloader.getResource( imageName );
         if ( url != null )
         {
            ImageIcon icon = new ImageIcon( url );
            return icon;
         }
      }
      catch( Exception e )
      {
         e.printStackTrace();
      }
      throw new IllegalArgumentException( "Unable to load image: " + imageName );
   }

   public JPanel createPanel()
   {
      JPanel jpanel1 = new JPanel();
      FormLayout formlayout1 = new FormLayout("FILL:DEFAULT:NONE,FILL:74PX:NONE,FILL:90PX:NONE,FILL:DEFAULT:NONE,FILL:DEFAULT:NONE,FILL:DEFAULT:NONE,FILL:DEFAULT:NONE,FILL:DEFAULT:NONE,FILL:DEFAULT:NONE,FILL:DEFAULT:NONE,FILL:DEFAULT:NONE,FILL:DEFAULT:NONE,FILL:DEFAULT:NONE,FILL:DEFAULT:NONE,FILL:DEFAULT:NONE,FILL:DEFAULT:NONE,FILL:DEFAULT:NONE,FILL:DEFAULT:NONE,FILL:DEFAULT:NONE,FILL:DEFAULT:NONE,FILL:DEFAULT:NONE,FILL:DEFAULT:NONE","CENTER:DEFAULT:NONE,CENTER:25PX:NONE,CENTER:25PX:NONE,CENTER:25PX:NONE,CENTER:25PX:NONE,CENTER:DEFAULT:NONE,CENTER:DEFAULT:NONE,CENTER:DEFAULT:NONE,CENTER:DEFAULT:NONE,CENTER:436PX:NONE,CENTER:25PX:NONE,CENTER:DEFAULT:NONE,CENTER:DEFAULT:NONE");
      CellConstraints cc = new CellConstraints();
      jpanel1.setLayout(formlayout1);

      tabbedPane.setName("tabbedPane");
      tabbedPane.setToolTipText("");
      TitledBorder titledborder1 = new TitledBorder(null,"Coordonnées",TitledBorder.DEFAULT_JUSTIFICATION,TitledBorder.DEFAULT_POSITION,null,new Color(49,106,196));
      tabbedPane.setBorder(titledborder1);
      tabbedPane.addTab("Adresses",loadImage("pen_blue.png"),createPanel1());
      tabbedPane.addTab("Contacts",loadImage("id_card2.png"),createPanel2());
      jpanel1.add(tabbedPane,cc.xywh(2,10,18,1));

      laCode.setName("laCode");
      laCode.setText("Code");
      jpanel1.add(laCode,cc.xy(2,2));

      tfCodeTiers.setName("tfCodeTiers");
      jpanel1.add(tfCodeTiers,cc.xy(3,2));

      laActif.setName("laActif");
      laActif.setText("Actif");
      jpanel1.add(laActif,cc.xy(2,3));

      laTypeTiers.setName("laTypeTiers");
      laTypeTiers.setText("Type de tiers");
      jpanel1.add(laTypeTiers,cc.xy(2,4));

      laRemarque.setName("laRemarque");
      laRemarque.setText("Remarque");
      jpanel1.add(laRemarque,cc.xy(2,5));

      cbActif.setName("cbActif");
      jpanel1.add(cbActif,cc.xy(3,3));

      tfTypeTiers.setName("tfTypeTiers");
      jpanel1.add(tfTypeTiers,cc.xy(3,4));

      epRemarque.setName("epRemarque");
      JScrollPane jscrollpane1 = new JScrollPane();
      jscrollpane1.setViewportView(epRemarque);
      jscrollpane1.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
      jscrollpane1.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
      jpanel1.add(jscrollpane1,cc.xywh(3,5,7,5));

      TitledSeparator titledseparator1 = new TitledSeparator();
      titledseparator1.setText("Tiers");
      jpanel1.add(titledseparator1,cc.xywh(2,1,18,1));

      btnImprimerTiers.setActionCommand("Imprimer");
      btnImprimerTiers.setIcon(loadImage("print.gif"));
      btnImprimerTiers.setName("btnImprimerTiers");
      btnImprimerTiers.setText("Imprimer");
      jpanel1.add(btnImprimerTiers,cc.xy(6,11));

      btnEnregistrerTiers.setActionCommand("Enregistrer");
      btnEnregistrerTiers.setIcon(loadImage("disk_yellow.png"));
      btnEnregistrerTiers.setName("btnEnregistrerTiers");
      btnEnregistrerTiers.setText("Enregistrer");
      btnEnregistrerTiers.setToolTipText("Enregistrer le tiers");
      jpanel1.add(btnEnregistrerTiers,cc.xy(5,11));

      btnAnnulerTiers.setActionCommand("Annuler");
      btnAnnulerTiers.setIcon(loadImage("refresh.gif"));
      btnAnnulerTiers.setName("btnAnnulerTiers");
      btnAnnulerTiers.setText("Annuler");
      jpanel1.add(btnAnnulerTiers,cc.xy(7,11));

      btnTiersPrec.setActionCommand("<");
      btnTiersPrec.setName("btnTiersPrec");
      btnTiersPrec.setText("<");
      jpanel1.add(btnTiersPrec,cc.xy(10,3));

      btnTiersSuiv.setActionCommand(">");
      btnTiersSuiv.setName("btnTiersSuiv");
      btnTiersSuiv.setText(">");
      btnTiersSuiv.setToolTipText("");
      jpanel1.add(btnTiersSuiv,cc.xy(11,3));

      addFillComponents(jpanel1,new int[]{ 1,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22 },new int[]{ 1,2,3,4,5,6,7,8,9,10,11,12,13 });
      return jpanel1;
   }

   public JPanel createPanel1()
   {
      JPanel jpanel1 = new JPanel();
      FormLayout formlayout1 = new FormLayout("FILL:DEFAULT:NONE,FILL:66PX:NONE,FILL:57PX:NONE,FILL:30PX:NONE,FILL:DEFAULT:NONE,FILL:DEFAULT:NONE,FILL:DEFAULT:NONE,FILL:DEFAULT:NONE,FILL:DEFAULT:NONE,FILL:5PX:NONE,FILL:79PX:NONE,FILL:DEFAULT:NONE,FILL:44PX:NONE,FILL:DEFAULT:NONE,FILL:DEFAULT:NONE,FILL:DEFAULT:NONE,FILL:DEFAULT:NONE","CENTER:DEFAULT:NONE,CENTER:25PX:NONE,CENTER:25PX:NONE,CENTER:DEFAULT:NONE,CENTER:25PX:NONE,CENTER:25PX:NONE,CENTER:25PX:NONE,CENTER:DEFAULT:NONE,CENTER:DEFAULT:NONE,CENTER:DEFAULT:NONE,CENTER:DEFAULT:NONE,CENTER:DEFAULT:NONE,CENTER:DEFAULT:NONE,CENTER:DEFAULT:NONE,CENTER:DEFAULT:NONE,CENTER:DEFAULT:NONE,CENTER:34PX:NONE,CENTER:DEFAULT:NONE,CENTER:DEFAULT:NONE");
      CellConstraints cc = new CellConstraints();
      jpanel1.setLayout(formlayout1);

      laAdresse.setName("laAdresse");
      laAdresse.setText("Adresse");
      jpanel1.add(laAdresse,cc.xy(2,2));

      laCodePostal.setName("laCodePostal");
      laCodePostal.setText("Code postal");
      jpanel1.add(laCodePostal,cc.xy(2,5));

      tfAdresse1.setName("tfAdresse1");
      jpanel1.add(tfAdresse1,cc.xywh(3,2,8,1));

      tfAdresse2.setName("tfAdresse2");
      jpanel1.add(tfAdresse2,cc.xywh(3,3,8,1));

      tfCodePostal.setName("tfCodePostal");
      jpanel1.add(tfCodePostal,cc.xy(3,5));

      laVille.setName("laVille");
      laVille.setText("Ville");
      laVille.setHorizontalAlignment(JLabel.CENTER);
      jpanel1.add(laVille,cc.xy(4,5));

      tfVille.setName("tfVille");
      jpanel1.add(tfVille,cc.xywh(5,5,6,1));

      laPays.setName("laPays");
      laPays.setText("Pays");
      jpanel1.add(laPays,cc.xy(2,6));

      tfPays.setName("tfPays");
      jpanel1.add(tfPays,cc.xywh(3,6,8,1));

      cbAdressePrincipale.setActionCommand("Adresse principale");
      cbAdressePrincipale.setName("cbAdressePrincipale");
      cbAdressePrincipale.setText("Adresse principale");
      jpanel1.add(cbAdressePrincipale,cc.xywh(2,7,2,1));

      cbAdresseLivraison.setActionCommand("Livraison");
      cbAdresseLivraison.setName("cbAdresseLivraison");
      cbAdresseLivraison.setText("Livraison");
      cbAdresseLivraison.setToolTipText("");
      jpanel1.add(cbAdresseLivraison,cc.xywh(4,7,6,1));

      btnAjouterAdresse.setActionCommand("Nouvelle Adresse");
      btnAjouterAdresse.setName("btnAjouterAdresse");
      btnAjouterAdresse.setText("+");
      btnAjouterAdresse.setToolTipText("Ajouter une nouvelle adresse");
      jpanel1.add(btnAjouterAdresse,cc.xy(13,14));

      btnSupprimerAdresse.setActionCommand("Supprimer");
      btnSupprimerAdresse.setName("btnSupprimerAdresse");
      btnSupprimerAdresse.setText("-");
      btnSupprimerAdresse.setToolTipText("Effacer l'adresse sélectionnée");
      jpanel1.add(btnSupprimerAdresse,cc.xy(13,15));

      btnEnregistrerAdresse.setActionCommand("Enregistrer");
      btnEnregistrerAdresse.setIcon(loadImage("disk_yellow.png"));
      btnEnregistrerAdresse.setName("btnEnregistrerAdresse");
      btnEnregistrerAdresse.setText("Enregistrer");
      btnEnregistrerAdresse.setToolTipText("Enregistrer l'adresse");
      jpanel1.add(btnEnregistrerAdresse,cc.xy(7,9));

      TitledSeparator titledseparator1 = new TitledSeparator();
      titledseparator1.setText("Liste des adresses");
      jpanel1.add(titledseparator1,cc.xywh(2,11,13,1));

      tfAdresse3.setName("tfAdresse3");
      jpanel1.add(tfAdresse3,cc.xywh(3,4,8,1));

      grilleAdresses.setCellSelectionEnabled(false);
      grilleAdresses.setColumnSelectionAllowed(false);
      grilleAdresses.setName("grilleAdresses");
      grilleAdresses.setRowSelectionAllowed(false);
      JScrollPane jscrollpane1 = new JScrollPane();
      jscrollpane1.setViewportView(grilleAdresses);
      jscrollpane1.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
      jscrollpane1.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
      jpanel1.add(jscrollpane1,cc.xywh(2,12,10,6));

      addFillComponents(jpanel1,new int[]{ 1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17 },new int[]{ 1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19 });
      return jpanel1;
   }

   public JPanel createPanel2()
   {
      JPanel jpanel1 = new JPanel();
      FormLayout formlayout1 = new FormLayout("FILL:DEFAULT:NONE,FILL:74PX:NONE,FILL:124PX:NONE,FILL:DEFAULT:NONE,FILL:DEFAULT:NONE,FILL:DEFAULT:NONE,FILL:DEFAULT:NONE,FILL:DEFAULT:NONE,FILL:DEFAULT:NONE,FILL:DEFAULT:NONE,FILL:DEFAULT:NONE,FILL:DEFAULT:NONE,FILL:DEFAULT:NONE","CENTER:25PX:NONE,CENTER:25PX:NONE,CENTER:25PX:NONE,CENTER:25PX:NONE,CENTER:DEFAULT:NONE,CENTER:DEFAULT:NONE,CENTER:DEFAULT:NONE,CENTER:DEFAULT:NONE,CENTER:25PX:NONE,CENTER:DEFAULT:NONE,CENTER:DEFAULT:NONE,CENTER:DEFAULT:NONE,CENTER:31PX:NONE");
      CellConstraints cc = new CellConstraints();
      jpanel1.setLayout(formlayout1);

      laNom.setName("laNom");
      laNom.setText("Nom");
      jpanel1.add(laNom,cc.xy(2,1));

      tfNomContact.setEditable(true);
      tfNomContact.setName("tfNomContact");
      tfNomContact.setRequestFocusEnabled(false);
      jpanel1.add(tfNomContact,cc.xy(3,1));

      laType.setName("laType");
      laType.setText("Type");
      jpanel1.add(laType,cc.xy(2,2));

      cbTypeContact.setName("cbTypeContact");
      cbTypeContact.addItem("Téléphone fixe");
      cbTypeContact.addItem("Téléphone portable");
      cbTypeContact.addItem("Fax");
      cbTypeContact.addItem("E-Mail");
      cbTypeContact.addItem("Site Internet");
      jpanel1.add(cbTypeContact,cc.xy(3,2));

      laValeur.setName("laValeur");
      laValeur.setText("Valeur");
      jpanel1.add(laValeur,cc.xy(2,3));

      tfValeurContact.setName("tfValeurContact");
      jpanel1.add(tfValeurContact,cc.xy(3,3));

      laCommentaire.setName("laCommentaire");
      laCommentaire.setText("Commentaire");
      jpanel1.add(laCommentaire,cc.xy(2,4));

      tfCommentaireContact.setName("tfCommentaireContact");
      jpanel1.add(tfCommentaireContact,cc.xywh(3,4,4,1));

      grilleContacts.setName("grilleContacts");
      JScrollPane jscrollpane1 = new JScrollPane();
      jscrollpane1.setViewportView(grilleContacts);
      jscrollpane1.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
      jscrollpane1.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
      jpanel1.add(jscrollpane1,cc.xywh(2,9,5,5));

      btnEnregistrerContact.setActionCommand("Enregistrer");
      btnEnregistrerContact.setIcon(loadImage("disk_yellow.png"));
      btnEnregistrerContact.setName("btnEnregistrerContact");
      btnEnregistrerContact.setText("Enregistrer");
      btnEnregistrerContact.setToolTipText("Enregistrer le contact");
      jpanel1.add(btnEnregistrerContact,cc.xy(5,6));

      TitledSeparator titledseparator1 = new TitledSeparator();
      titledseparator1.setText("Liste des contacts");
      jpanel1.add(titledseparator1,cc.xywh(2,8,8,1));

      btnAjouterContact.setActionCommand("Nouvelle Adresse");
      btnAjouterContact.setName("btnAjouterContact");
      btnAjouterContact.setText("+");
      btnAjouterContact.setToolTipText("Ajouter un nouveau contact");
      jpanel1.add(btnAjouterContact,cc.xy(8,11));

      btnSupprimerContact.setActionCommand("Nouvelle Adresse");
      btnSupprimerContact.setName("btnSupprimerContact");
      btnSupprimerContact.setText("-");
      btnSupprimerContact.setToolTipText("Effacer le contact sélectionné");
      jpanel1.add(btnSupprimerContact,cc.xy(8,12));

      addFillComponents(jpanel1,new int[]{ 1,4,5,6,7,8,9,10,11,12,13 },new int[]{ 1,2,3,4,5,6,7,8,9,10,11,12,13 });
      return jpanel1;
   }

   /**
    * Initializer
    */
   protected void initializePanel()
   {
      setLayout(new BorderLayout());
      add(createPanel(), BorderLayout.CENTER);
   }


}
