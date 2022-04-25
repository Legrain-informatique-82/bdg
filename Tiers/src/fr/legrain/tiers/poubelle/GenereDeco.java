package fr.legrain.tiers.poubelle;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import org.eclipse.swt.widgets.Text;

import fr.legrain.lib.gui.DefaultFrameFormulaireSWT;

public class GenereDeco {
	private Class source;
	private DefaultFrameFormulaireSWT ecran;
	private File fgen;
	private File fsource;
	private BufferedWriter bw;
	private BufferedReader br;

	/**
	 * 1 - Nom de la classe
	 * 2 - Fichier source de la classe
	 * @param args
	 */
	public static void main(String[] args) {
		GenereDeco genereDeco = new GenereDeco();
		try {
			genereDeco.source =  Class.forName(args[0]);
			genereDeco.ecran = (DefaultFrameFormulaireSWT)genereDeco.source.newInstance();
			genereDeco.fgen = new File("C:/Projet/Java/Eclipse/GestionCommerciale/Tiers/src/fr/legrain/tiers/ecran/"+args[0]+"Deco.java");
			genereDeco.fsource = new File(args[1]);
			genereDeco.bw = new BufferedWriter(new FileWriter(genereDeco.fgen));
			genereDeco.br = new BufferedReader(new FileReader(genereDeco.fsource));
			String tmpBr;
			do {
				tmpBr = genereDeco.br.readLine();
				genereDeco.bw.write(tmpBr);
			} while (!tmpBr.equals("//DECO"));
				
			
			
			Text t = null;
//			for (int i = 0; i < genereDeco.ecran.getPaFomulaire().getChildren().length; i++) {
//				if(genereDeco.ecran.getPaFomulaire().getChildren()[i] instanceof Text) {
//					t = (Text)genereDeco.ecran.getPaFomulaire().getChildren()[i];
//					genereDeco.bw.write("");
//					{
//						GridData tfCODE_TIERSLData = new GridData();
//						tfCODE_TIERSLData.horizontalAlignment = GridData.FILL;
//						t.
//						
//						
//						genereDeco.bw.write("GridData tfCODE_TIERSLData = new GridData();");
//						field = new DecoratedField(paCorpsFormulaire, SWT.BORDER, new TextControlCreator());
//						tfCODE_TIERS = (Text)field.getControl();
//						FieldDecorationRegistry registry = FieldDecorationRegistry.getDefault();
//						//tfCODE_TIERSLData.widthHint = /*100 +*/ registry.getMaximumDecorationWidth();
//						field.getLayoutControl().setLayoutData(
//								tfCODE_TIERSLData
//						);
//					}
//				}
//				
//			}
			
			
			
			
			do {
				tmpBr = genereDeco.br.readLine();
			} while (!tmpBr.equals("//FINDECO"));
			
			do {
				tmpBr = genereDeco.br.readLine();
				genereDeco.bw.write(tmpBr);
			} while (tmpBr!=null);

		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			try {
				genereDeco.bw.close();
				genereDeco.br.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
	}

}
