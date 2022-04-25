package fr.legrain.lib.validation;

import java.math.BigDecimal;

import org.eclipse.core.databinding.conversion.IConverter;

import fr.legrain.lib.data.LibConversion;

public class ConvertString2BigDecimal implements IConverter {

	public Object convert(Object fromObject) {
		String sourceString = (String) fromObject;
		if ("".equals(sourceString.trim())) { //$NON-NLS-1$
			return null;
		}
		BigDecimal d = new BigDecimal(sourceString);
		return d;
		//return LibConversion.stringToBigDecimal((String)fromObject);
	}

	public Object getFromType() {
		return String.class; 
	}

	public Object getToType() {
		return BigDecimal.class;
	}

}
