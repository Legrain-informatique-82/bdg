package fr.legrain.lib.gui.aide;
import com.borland.dbswing.JdbTable;
import com.borland.dbswing.JdbTextField;

import fr.legrain.lib.gui.grille.LgrJdbTable;

import java.awt.BorderLayout;

import java.awt.Dimension;
import javax.swing.BorderFactory;

import javax.swing.WindowConstants;
import javax.swing.border.BevelBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import org.apache.log4j.Logger;

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
public class PaAideRecherche extends javax.swing.JPanel {
	private JPanel paRecherche;
	private LgrJdbTable jdbTable;
	private JPopupMenu jPopupMenuAide;
	private JScrollPane jScrollPane;
	//private JTextField tfChoix;  // changer aussi dans le new
	private JdbTextField tfChoix;
	private JLabel laRecherche;
	static Logger logger = Logger.getLogger(PaAideRecherche.class.getName());

	/**
	* Auto-generated main method to display this 
	* JPanel inside a new JFrame.
	*/
	public static void main(String[] args) {
		JFrame frame = new JFrame();
		frame.getContentPane().add(new PaAideRecherche());
		frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		frame.pack();
		frame.setVisible(true);
	}
	
	public PaAideRecherche() {
		super();
		initGUI();
	}
	
	private void initGUI() {
		try {
			BorderLayout thisLayout = new BorderLayout();
			this.setLayout(thisLayout);
			setPreferredSize(new Dimension(400, 300));
			{
				jScrollPane = new JScrollPane();
				this.add(jScrollPane, BorderLayout.CENTER);
				jScrollPane.setBounds(114, 110, 60, 30);
				{
					TableModel jdbTableModel = new DefaultTableModel(
						new String[][] { { "One", "Two" }, { "Three", "Four" } },
						new String[] { "Column 1", "Column 2" });
					jdbTable = new LgrJdbTable();
					jScrollPane.setViewportView(jdbTable);
					jdbTable.setModel(jdbTableModel);
					jdbTable.setBorder(BorderFactory
						.createEtchedBorder(BevelBorder.LOWERED));
					{
						jPopupMenuAide = new JPopupMenu();
						jScrollPane.setComponentPopupMenu(jPopupMenuAide);
						jdbTable.setComponentPopupMenu(jPopupMenuAide);
						setComponentPopupMenu(jdbTable, jPopupMenuAide);
					}
				}
			}
			{
				paRecherche = new JPanel();
				this.add(paRecherche, BorderLayout.NORTH);
				paRecherche.setComponentPopupMenu(jPopupMenuAide);
				{
					laRecherche = new JLabel();
					paRecherche.add(laRecherche);
					laRecherche.setText("Recherche");
				}
				{
//					tfChoix = new JTextField();
					tfChoix = new JdbTextField();
					tfChoix.setName("tfChoix");
					this.setFocusable(true);
					this.setNextFocusableComponent(tfChoix);
					paRecherche.add(tfChoix);
					tfChoix.setPreferredSize(new java.awt.Dimension(107, 20));
					tfChoix.setComponentPopupMenu(jPopupMenuAide);
				}
			}
		} catch (Exception e) {
			logger.error("Erreur : initGUI", e);
		}
	}

	public LgrJdbTable getJdbTable() {
		return jdbTable;
	}

	public void setJdbTable(LgrJdbTable jdbTable) {
		this.jdbTable = jdbTable;
	}

	public JLabel getLaRecherche() {
		return laRecherche;
	}

	public void setLaRecherche(JLabel laRecherche) {
		this.laRecherche = laRecherche;
	}

	public JdbTextField getTfChoix() {
		return tfChoix;
	}

	public void setTfChoix(JdbTextField tfChoix) {
		this.tfChoix = tfChoix;
	}
	
	/**
	* Auto-generated method for setting the popup menu for a component
	*/
	private void setComponentPopupMenu(final java.awt.Component parent, final javax.swing.JPopupMenu menu) {
parent.addMouseListener(new java.awt.event.MouseAdapter() {
public void mousePressed(java.awt.event.MouseEvent e) {
if(e.isPopupTrigger())
menu.show(parent, e.getX(), e.getY());
}
public void mouseReleased(java.awt.event.MouseEvent e) {
if(e.isPopupTrigger())
menu.show(parent, e.getX(), e.getY());
}
});
	}
	public JPopupMenu getPopupMenuAide() {
		return jPopupMenuAide;
	}
}
