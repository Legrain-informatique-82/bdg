package fr.legrain.lib.validation;


import org.eclipse.core.databinding.conversion.IConverter;


public class ConvertString2Integer implements IConverter {

	public Object convert(Object fromObject) {
		String sourceString = (String) fromObject;
		if ("".equals(sourceString.trim())) { //$NON-NLS-1$
			return null;
		}
		Integer d = new Integer(sourceString);
		return d;
	}

	public Object getFromType() {
		return String.class; 
	}

	public Object getToType() {
		return Integer.class;
	}

}
