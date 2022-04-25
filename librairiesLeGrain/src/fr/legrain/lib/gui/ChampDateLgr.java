package fr.legrain.lib.gui;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.swing.InputVerifier;
import javax.swing.JComponent;
import javax.swing.JFormattedTextField;
import javax.swing.JSpinner;
import javax.swing.SpinnerDateModel;
import javax.swing.JFormattedTextField.AbstractFormatter;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.DateFormatter;
import javax.swing.text.DefaultFormatterFactory;
import javax.swing.text.MaskFormatter;

import fr.legrain.lib.data.LgrConstantes;
import fr.legrain.lib.data.LibDate;
import fr.legrain.lib.data.VerrouInterface;

//public class ChampDateLgr extends JXDatePicker{
public class ChampDateLgr extends JFormattedTextField {
	
    	
//    public ChampDateLgr(AbstractFormatter format) {
//    	super(format);
//    } 
    
    public ChampDateLgr() {
	try{ 
		MaskFormatter mf2 =new MaskFormatter(LgrConstantes.C_DATE_MASK_FORMATTER);
		mf2.setPlaceholderCharacter(LgrConstantes.C_DATE_MASK_PLACE_HOLDER);
		this.setFormatterFactory(new DefaultFormatterFactory(mf2));
		this.setFocusLostBehavior(this.PERSIST);

        setInputVerifier(new InputVerifier() {
            public boolean verify(JComponent input) {    
              try {
            	  return true;
              }
              catch (Exception e) {
                return false;
              }
            }
          });        
    } catch (Exception e) {
		e.printStackTrace();
	}
       
    }
    
}
