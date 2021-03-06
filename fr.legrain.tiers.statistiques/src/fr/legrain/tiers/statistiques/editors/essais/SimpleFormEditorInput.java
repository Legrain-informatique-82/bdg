/*******************************************************************************
 * Copyright (c) 2000, 2004 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials 
 * are made available under the terms of the Common Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/cpl-v10.html
 * 
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package fr.legrain.tiers.statistiques.editors.essais;

import fr.legrain.tiers.statistiques.editors.FormEditorInput;


public class SimpleFormEditorInput extends FormEditorInput {
	private SimpleModel model;
	
	public SimpleFormEditorInput(String name) {
		super(name);
		model = new SimpleModel();
	}
	
	public SimpleModel getModel() {
		return model;
	}
}