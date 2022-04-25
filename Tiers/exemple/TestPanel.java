package fr.legrain.tiers.test.ecran;

import com.borland.dbswing.JdbNavToolBar;
import com.borland.dbswing.JdbTable;
import com.borland.dx.dataset.MetaDataUpdate;

import fr.legrain.Gest_Com.Appli.IB_APPLICATION;
import fr.legrain.Gest_Com.Module_Tiers.T_CIVILITE;
import fr.legrain.Gest_Com.Module_Tiers.IB_Tables.IB_TA_T_CIVILITE;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JPanel;

public class TestPanel extends javax.swing.JPanel {
	
	    JPanel contentPane;
	    BorderLayout borderLayout1 = new BorderLayout();
	    JdbTable jdbTable = new JdbTable();
	    JPanel jPanel1 = new JPanel();
	    JButton jButton1 = new JButton();
	    JButton jButton2 = new JButton();
	    JButton jButton3 = new JButton();
	    JdbNavToolBar jdbNavToolBar1 = new JdbNavToolBar();
	    IB_TA_T_CIVILITE ibTaTCivilite = new IB_TA_T_CIVILITE();

	    public TestPanel() {
	        try {
	            //setDefaultCloseOperation(EXIT_ON_CLOSE);
	            jbInit();
	        } catch (Exception exception) {
	            exception.printStackTrace();
	        }
	    }

	    /**
	     * Component initialization.
	     * @throws java.lang.Exception
	     */
	    private void jbInit() throws Exception {
	        contentPane = this;
	        contentPane.setLayout(borderLayout1);
	        setSize(new Dimension(400, 300));
	        jButton1.setText("jButton1");
	        jButton1.addActionListener(new Frame1_jButton1_actionAdapter(this));
	        jButton2.setText("jButton2");
	        jButton3.setText("jButton3");
	        contentPane.add(jdbTable, java.awt.BorderLayout.WEST);
	        contentPane.add(jPanel1, java.awt.BorderLayout.SOUTH);
	        jPanel1.add(jButton1);
	        jPanel1.add(jButton2);
	        jPanel1.add(jButton3);
	        contentPane.add(jdbNavToolBar1, java.awt.BorderLayout.NORTH);

	        IB_APPLICATION ibApplication = new IB_APPLICATION();

	        jdbTable.setDataSet(ibTaTCivilite.getFIBQuery());

	        ibTaTCivilite.getFIBQuery().close();
	        ibTaTCivilite.getFIBQuery().setMetaDataUpdate(MetaDataUpdate.NONE);
	        ibTaTCivilite.getFIBQuery().setRowId("ID_T_CIVILITE",true);
	        ibTaTCivilite.getFIBQuery().open();
	    }

	    public void jButton1_actionPerformed(ActionEvent e) {
	        try {
	            T_CIVILITE tCivilite = new  T_CIVILITE();
	            tCivilite.setCODE_T_CIVILITE("soeur2");
	            ibTaTCivilite.modfication(tCivilite);
	            //ibTaTCivilite.verifChamp("CODE_T_CIVILITE", "soeur");
	           // ibTaTCivilite.setChamp_Obj_Query();
	            //ibTaTCivilite.inserer();
	            //ibTaTCivilite.enregistrer();
	        }
	        catch(Exception ex){
	            ex.printStackTrace();
	        }
	    }
	}


	class Frame1_jButton1_actionAdapter implements ActionListener {
	    private TestPanel adaptee;
	    Frame1_jButton1_actionAdapter(TestPanel adaptee) {
	        this.adaptee = adaptee;
	    }

	    public void actionPerformed(ActionEvent e) {
	        adaptee.jButton1_actionPerformed(e);
	    }
	}
