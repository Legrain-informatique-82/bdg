package fr.legrain.lib.validation;

import java.util.Date;

import org.eclipse.core.databinding.conversion.IConverter;

import fr.legrain.lib.data.LibDate;

public class ConvertDate2String implements IConverter {
	
	public Object convert(Object fromObject) {
		Date sourceInteger = (Date) fromObject;
		String s = null;
		if(sourceInteger!=null)
			s = LibDate.dateToString(sourceInteger);
		return s;
	}

	public Object getFromType() {
		return Date.class;
	}

	public Object getToType() {
		return String.class;
	}
}
