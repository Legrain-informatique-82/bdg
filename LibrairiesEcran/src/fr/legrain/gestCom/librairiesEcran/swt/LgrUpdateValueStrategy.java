package fr.legrain.gestCom.librairiesEcran.swt;

import org.eclipse.core.databinding.UpdateValueStrategy;
import org.eclipse.core.databinding.conversion.IConverter;

public class LgrUpdateValueStrategy {
	private IConverter uiToModelConverter = null;
	private IConverter modelToUIConverter = null;
	
	
	public LgrUpdateValueStrategy(IConverter uiToModelConverter,
			IConverter modelToUIConverter) {
		super();
		this.uiToModelConverter = uiToModelConverter;
		this.modelToUIConverter = modelToUIConverter;
	}
	
	public IConverter getUiToModelConverter() {
		return uiToModelConverter;
	}
	public void setUiToModelConvertor(IConverter uiToModelConverter) {
		this.uiToModelConverter = uiToModelConverter;
	}
	public IConverter getModelToUIConverter() {
		return modelToUIConverter;
	}
	public void setModelToUIConvertor(IConverter modelToUIConverter) {
		this.modelToUIConverter = modelToUIConverter;
	}
	
}
