package fr.legrain.lib.validation;

import org.eclipse.core.databinding.conversion.IConverter;

public class ConvertInteger2String implements IConverter {

	public Object convert(Object fromObject) {
		Integer sourceInteger = (Integer) fromObject;
		String s = null;
		if(sourceInteger!=null)
			s = sourceInteger.toString();
		return s;
	}

	public Object getFromType() {
		return Integer.class;
	}

	public Object getToType() {
		return String.class;
	}
	
}