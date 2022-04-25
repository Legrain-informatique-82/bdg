package fr.legrain.articles.divers;

import org.eclipse.core.databinding.conversion.IConverter;

import fr.legrain.gestCom.librairiesEcran.swt.LgrUpdateValueStrategy;

public class LgrUpdateValueStrategyComboSensRapportUnite extends LgrUpdateValueStrategy {
	
	public static final String sensMultiplierFalse = "multiplié par";
	public static final String sensDiviserTrue = "divisé par";

	public LgrUpdateValueStrategyComboSensRapportUnite() {
		super(new IConverter() {
			@Override
			public Object getToType() {
				return Boolean.class;
			}

			@Override
			public Object getFromType() {
				return String.class;
			}

			@Override
			public Object convert(Object fromObject) {
				if(fromObject!=null &&  fromObject.toString().equals(sensDiviserTrue)) {
					return true;
				} else {
					return false;
				}
			}
		},
		new IConverter() {
			@Override
			public Object getToType() {
				return String.class;
			}

			@Override
			public Object getFromType() {
				return Boolean.class;
			}

			@Override
			public Object convert(Object fromObject) {
				if(fromObject!=null && (Boolean)fromObject) {
					return sensDiviserTrue;
				} else {
					return sensMultiplierFalse;
				}
			}
		});
	}
	
	static public boolean sens(String sens) {
		if(sens!=null) {
			if(sens.equals(sensDiviserTrue)) {
				return true;
			} else if(sens.equals(sensMultiplierFalse)) {
				return false;
			}
		}
		return false;
	}

}
