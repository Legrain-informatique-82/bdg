package fr.legrain.lib.gui.aide;

import javax.swing.JComponent;

import com.borland.dx.sql.dataset.QueryDataSet;

import fr.legrain.lib.gui.BaseController;
import fr.legrain.lib.gui.ParamAffiche;

public class ParamAfficheAideRecherche extends ParamAffiche {
	private QueryDataSet query = null;
	private String champsRecherche = null;
	//private Class refCreation = null;
	private JComponent focusDefaut = null;
	
	private ParamAffiche paramEcranCreation = null;
	private BaseController refCreation = null;

	public BaseController getRefCreation() {
		return refCreation;
	}

	public void setRefCreation(BaseController refCreation) {
		this.refCreation = refCreation;
	}
	
	public JComponent getFocusDefaut() {
		return focusDefaut;
	}
	public void setFocusDefaut(JComponent focusDefaut) {
		this.focusDefaut = focusDefaut;
	}	
//	public Class getRefCreation() {
//		return refCreation;
//	}
//
//	public void setRefCreation(Class refCreation) {
//		this.refCreation = refCreation;
//	}

	public QueryDataSet getQuery() {
		return query;
	}

	public void setQuery(QueryDataSet query) {
		this.query = query;
	}

	public String getChampsRecherche() {
		return champsRecherche;
	}

	public void setChampsRecherche(String champsRecherche) {
		this.champsRecherche = champsRecherche;
	}

	public ParamAffiche getParamEcranCreation() {
		return paramEcranCreation;
	}

	public void setParamEcranCreation(ParamAffiche paramEcranCreation) {
		this.paramEcranCreation = paramEcranCreation;
	}

}
