package fr.legrain.lib.validation;

import java.util.Date;

import org.eclipse.core.databinding.conversion.IConverter;

import fr.legrain.lib.data.LibDate;

public class ConvertCDatetime2String implements IConverter {

	public Object convert(Object fromObject) {
		String sourceInteger = (String) fromObject;
		String s = null;
		if(sourceInteger!=null)
			s = sourceInteger;
			//s = sourceInteger.toString();
		return s;
	}

	public Object getFromType() {
		return String.class;
	}

	public Object getToType() {
		return String.class;
	}
	
}