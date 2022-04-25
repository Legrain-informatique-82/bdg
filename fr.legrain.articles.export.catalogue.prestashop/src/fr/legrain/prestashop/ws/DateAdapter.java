package fr.legrain.prestashop.ws;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.xml.bind.annotation.adapters.XmlAdapter;

public class DateAdapter extends XmlAdapter<String, Date> {
	
	private String dateNullePresta = "0000-00-00";
	private String formatDatePresta = "yyyy-MM-dd HH:mm:ss";
	private String formatDatePrestaSimple = "yyyy-MM-dd";
   
	@Override
    public Date unmarshal(String s) {
		Date retour = null;
		//Calendar c = Calendar.getInstance();
	    //c.set(2010, 01, 01, 15, 30, 0);
	    SimpleDateFormat sdf = new SimpleDateFormat(formatDatePresta);
	    SimpleDateFormat sdfSimple = new SimpleDateFormat(formatDatePrestaSimple);
	    //String TimeStop_Str = sdf.format(c.getTime());
	    
    	if(s.equals(dateNullePresta)) {
    		return null;
    	}else if(s!=null) {
    		try {
    			retour = sdf.parse(s);
			} catch (ParseException e) {
				try {
					sdfSimple.parse(s);
				} catch (ParseException ex) {
					ex.printStackTrace();
				}
			}
        	return retour;
        } else {
        	return null;
        }
    }

    @Override
    public String marshal(Date c) {
    	//Calendar cal = Calendar.getInstance();
	    SimpleDateFormat sdf = new SimpleDateFormat(formatDatePresta);
    	if(c==null) {
    		return dateNullePresta;
    	} else {
    		return sdf.format(c);
    		//return c.toString();
    	}
    }
}
