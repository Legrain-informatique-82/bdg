package fr.legrain.lib.gui;

import org.eclipse.jface.viewers.ISelection;

/**
 * <p>Title: </p>
 * <p>Description: Retour pour les fonctions d'affichage.</p>
 * <p>Copyright: Copyright (c) 2005</p>
 * <p>Company: Le Grain SA</p>
 * @author nicolas
 * @version 1.0
 */

public class ResultAffiche {
	
	private String result = null;
	private String idResult = null;
	private ISelection selection = null;
	
	public ResultAffiche() {
	}
	
	public ResultAffiche(String result,String idResult) {
		this.result = result;
		this.idResult = idResult;
	}
	
	public ResultAffiche(String result,String idResult,ISelection selection) {
		this.result = result;
		this.idResult = idResult;
		this.selection = selection;
	}
	
	public String getResult() {
		return result;
	}
	
	public void setResult(String result) {
		this.result = result;
	}

	public String getIdResult() {
		return idResult;
	}

	public void setIdResult(String idResult) {
		this.idResult = idResult;
	}

	public ISelection getSelection() {
		return selection;
	}

	public void setSelection(ISelection selection) {
		this.selection = selection;
	}
	
}
