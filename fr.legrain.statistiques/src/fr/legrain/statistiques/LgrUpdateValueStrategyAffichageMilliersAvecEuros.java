/**
 * 
 */
package fr.legrain.statistiques;

import java.math.BigDecimal;
import java.text.NumberFormat;

import org.eclipse.core.databinding.conversion.IConverter;

import fr.legrain.gestCom.librairiesEcran.swt.LgrUpdateValueStrategy;
import fr.legrain.lib.data.LibConversion;

/**
 * @author posttest
 *
 */
public class LgrUpdateValueStrategyAffichageMilliersAvecEuros extends
		LgrUpdateValueStrategy {
	
	private static NumberFormat formatter = LibConversion.formatEuro;

	public LgrUpdateValueStrategyAffichageMilliersAvecEuros() {
		super(new IConverter(){

			@Override
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
			
		}, new IConverter(){

			public Object convert(Object fromObject) {
				BigDecimal sourceBigDecimal = (BigDecimal) fromObject;
				String s = null;
				if(sourceBigDecimal!=null)
					s = formatter.format(sourceBigDecimal);
//					s = sourceBigDecimal.toString();
				return s;
			}

			public Object getFromType() {
				return BigDecimal.class;
			}

			public Object getToType() {
				return String.class;
			}
			
		});
		
	}

}
