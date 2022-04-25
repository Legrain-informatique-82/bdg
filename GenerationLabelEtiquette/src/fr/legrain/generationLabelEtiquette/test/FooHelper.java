package fr.legrain.generationLabelEtiquette.test;

import java.beans.XMLEncoder;
import java.beans.XMLDecoder;
import java.io.*;

import fr.legrain.generationLabelEtiquette.divers.ParameterEtiquette;

public class FooHelper {
    public static void write(Foo f, String filename) throws Exception{
        XMLEncoder encoder =
           new XMLEncoder(
              new BufferedOutputStream(
                new FileOutputStream(filename)));
        encoder.writeObject(f);
        encoder.close();
    }

    public static ParameterEtiquette read(String filename)  {
        XMLDecoder decoder = null;
		try {
			decoder = new XMLDecoder(new BufferedInputStream(
                new FileInputStream(filename)));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        //Foo o = (Foo)decoder.readObject();
        ParameterEtiquette o = (ParameterEtiquette)decoder.readObject();
        decoder.close();
        return o;
    }
}
