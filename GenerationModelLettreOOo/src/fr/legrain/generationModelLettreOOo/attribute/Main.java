package fr.legrain.generationModelLettreOOo.attribute;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.Shell;

import fr.legrain.generationModelLettreOOo.divers.FonctionOpenOffice;



public class Main {
  // Strings to use as list items
  public static String port = "8100";
  public static String pathOffice = "/usr/lib/openoffice/program";
  public static String pathAttributeLettre = "/usr/lib/openoffice/program.properties";
  public static String fileProps = "/home/lee/Bureau/aaaaaa.properties";
  public static String patter = ".properties";

  public static void main(String[] args) {
//    FonctionOpenOffice fonctionOpenOffice = new FonctionOpenOffice(pathOffice, port);
//    fonctionOpenOffice.startServerOpenOffice(pathOffice, port);
//    fonctionOpenOffice.prepareOpenOffice(port);
//    fonctionOpenOffice.findAllMotKeyModelLetter();
//	  if(pathAttributeLettre.endsWith(patter)){
//		  System.out.println("here");
//	  }else{
//		  System.out.println("sdqsd");
//	  }
	  Properties props = new Properties();
		InputStream propsFile;
	    
		try {
			propsFile = new FileInputStream(fileProps);
			props.load(propsFile);
			for(String key : props.stringPropertyNames()) { 
				String value = props.getProperty(key); 
				System.out.println(key + " => " + value); 
			} 

			propsFile.close();
		} catch (Exception ioe) {
			ioe.printStackTrace();
		}

	  
  }
}