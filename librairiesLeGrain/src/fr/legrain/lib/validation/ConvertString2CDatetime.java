package fr.legrain.lib.validation;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import org.eclipse.core.databinding.conversion.IConverter;

import fr.legrain.lib.data.LibDate;

public class ConvertString2CDatetime implements IConverter {

	public Object convert(Object fromObject) {
		String sourceString = (String) fromObject;
		return LibDate.stringToDateNew(sourceString);
		//return LibConversion.stringToBigDecimal((String)fromObject);
	}

	public Object getFromType() {
		return String.class; 
	}

	public Object getToType() {
		return Date.class;
	}
	
}