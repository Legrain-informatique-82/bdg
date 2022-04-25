package Interface;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

public class AjouteElemnet {
	
	private InterFaceMains mainsFrame;
	private JMenuBar menuBar = new JMenuBar();
    private JMenu menuFile;
    private JMenuItem MenuFileItem;
	
	public AjouteElemnet(InterFaceMains mainsFrame){
		this.mainsFrame = mainsFrame;
	}
	public void addMenuFile(String nameOneMenu,String nameOneMenuItem) {
		menuFile = new JMenu();
		menuFile.setText(nameOneMenu);
		menuBar.add(menuFile);

		MenuFileItem = new JMenuItem();
		MenuFileItem.setText(nameOneMenuItem);
		menuFile.add(MenuFileItem);
		mainsFrame.setJMenuBar(menuBar);
	}


	public InterFaceMains getMainsFrame() {
		return mainsFrame;
	}


	public void setMainsFrame(InterFaceMains mainsFrame) {
		this.mainsFrame = mainsFrame;
	}


	public JMenuBar getMenuBar() {
		return menuBar;
	}


	public void setMenuBar(JMenuBar menuBar) {
		this.menuBar = menuBar;
	}
	public JMenu getMenuFile() {
		return menuFile;
	}
	public void setMenuFile(JMenu menuFile) {
		this.menuFile = menuFile;
	}
	public JMenuItem getMenuFileItem() {
		return MenuFileItem;
	}
	public void setMenuFileItem(JMenuItem menuFileItem) {
		MenuFileItem = menuFileItem;
	}
	
	

}
