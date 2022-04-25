package fr.legrain.tiers.poubelle;
import com.borland.dbswing.JdbTable;
import java.awt.BorderLayout;

import java.awt.Dimension;
import java.awt.FlowLayout;
import javax.swing.BorderFactory;

import javax.swing.WindowConstants;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

/**
* This code was generated using CloudGarden's Jigloo
* SWT/Swing GUI Builder, which is free for non-commercial
* use. If Jigloo is being used commercially (ie, by a corporation,
* company or business for any purpose whatever) then you
* should purchase a license for each developer using Jigloo.
* Please visit www.cloudgarden.com for details.
* Use of Jigloo implies acceptance of these licensing terms.
* *************************************
* A COMMERCIAL LICENSE HAS NOT BEEN PURCHASED
* for this machine, so Jigloo or this code cannot be used legally
* for any corporate or commercial purpose.
* *************************************
*/
public class NewJPanel extends javax.swing.JPanel {
	public JdbTable jdbTable1;
	private JPanel jPanel1;
	private JLabel jLabel1;
	private JScrollPane jScrollPane1;

	/**
	* Auto-generated main method to display this 
	* JPanel inside a new JFrame.
	*/
	public static void main(String[] args) {
		JFrame frame = new JFrame();
		frame.getContentPane().add(new NewJPanel());
		frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		frame.pack();
		frame.setVisible(true);
	}
	
	public NewJPanel() {
		super();
		initGUI();
	}
	
	private void initGUI() {
		try {
			setPreferredSize(new Dimension(400, 300));
			{
				jScrollPane1 = new JScrollPane();
				this.add(jScrollPane1, BorderLayout.CENTER);
				jScrollPane1.setBorder(new LineBorder(new java.awt.Color(0,0,0), 1, false));
				{
					TableModel jdbTable1Model = new DefaultTableModel(
						new String[][] { { "One", "Two" }, { "Three", "Four" } },
						new String[] { "Column 1", "Column 2" });
					BorderLayout thisLayout = new BorderLayout();
					this.setLayout(thisLayout);
					jdbTable1 = new JdbTable();
					jScrollPane1.setViewportView(jdbTable1);
					jdbTable1.setModel(jdbTable1Model);
				}
			}
			{
				jPanel1 = new JPanel();
				this.add(jPanel1, BorderLayout.NORTH);
				jPanel1.setBorder(new LineBorder(new java.awt.Color(0,0,0), 1, false));
				{
					jLabel1 = new JLabel();
					jPanel1.add(jLabel1);
					jLabel1.setText("jLabel1");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
