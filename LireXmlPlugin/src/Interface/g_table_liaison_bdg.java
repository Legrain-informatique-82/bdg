package Interface;

import java.awt.EventQueue;
import java.io.File;
import java.util.Properties;

import ControleAction.AllActionInterFace;

public class g_table_liaison_bdg {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		if(args.length==3){
			try {
				System.out.println("*****************************************************");
				System.out.println("Repertoire Eclipse (/features) : "+args[0]);
				System.out.println("Repertoire Sources : "+args[1]);
				System.out.println("Qualifier : "+args[2]);
				System.out.println("*****************************************************");
				AllActionInterFace objetAction = new AllActionInterFace();
				objetAction.setQualifier(args[2]);
				objetAction.addAllMapFeature(new File(args[0]),ConstProjet.TYPE_FEATURE_ECLIPSE);
				objetAction.addAllMapFeature(new File(args[1]),ConstProjet.TYPE_FEATURE_BG);
				objetAction.remplirDB();
				System.out.println("Base de donnees "+ConstProjet.NAME_DB+" mise a jour. (serveur : "+ConstProjet.LOCALHOST+")");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}else{
			EventQueue.invokeLater(new Runnable() {
				public void run() {
					try {
						
						Properties property = System.getProperties();
						String currentFolder = property.getProperty(ConstProjet.USER_DIR);
						
						InterFaceMains frame = new InterFaceMains();
						AjouteElemnet objetAjouteElemnet = new AjouteElemnet(frame);
						objetAjouteElemnet.addMenuFile("File", "Open");
						AllActionInterFace objetAction = new AllActionInterFace(objetAjouteElemnet,
								frame);
						objetAction.setQualifier("test");
						objetAction.allNewActionInterface(frame);
						objetAction.actionValiderEtAnnuler(frame);
						
						frame.setVisible(true);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			});

		}

		
	}

}
