package fr.legrain.gestCom.Appli;

import org.eclipse.core.runtime.preferences.IScopeContext;

import fr.legrain.services.IServicePreferenceDossier;

public class ServicePreferenceDossier implements IServicePreferenceDossier {

	@Override
	public IScopeContext getProjectScopeContext() {
		return Const.getProjectScopeContext();
	}

}
