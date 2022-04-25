package fr.legrain.lib.validation;

import org.eclipse.core.databinding.conversion.IConverter;

public class ConvertDouble2String implements IConverter {

	public Object convert(Object fromObject) {
		Double sourceDouble = (Double) fromObject;
		String s = null;
		if(sourceDouble!=null)
			s = sourceDouble.toString();
		return s;
	}

	public Object getFromType() {
		return Double.class;
	}

	public Object getToType() {
		return String.class;
	}
	
}