package fr.legrain.lib.validation;

import java.math.BigDecimal;

import org.eclipse.core.databinding.conversion.IConverter;

public class ConvertBigDecimal2String implements IConverter {

	public Object convert(Object fromObject) {
		BigDecimal sourceBigDecimal = (BigDecimal) fromObject;
		String s = null;
		if(sourceBigDecimal!=null)
			s = sourceBigDecimal.toString();
		return s;
	}

	public Object getFromType() {
		return BigDecimal.class;
	}

	public Object getToType() {
		return String.class;
	}
	
}