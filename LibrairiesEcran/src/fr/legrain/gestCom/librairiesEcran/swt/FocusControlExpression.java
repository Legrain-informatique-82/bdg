package fr.legrain.gestCom.librairiesEcran.swt;

import org.eclipse.core.expressions.Expression;

public class FocusControlExpression  extends Expression{
	
	private String focusId;
	
	public FocusControlExpression(String focusId) {
		this.focusId = focusId;
	}
	
	public void collectExpressionInfo(org.eclipse.core.expressions.ExpressionInfo info) {
		info.addVariableNameAccess(org.eclipse.ui.ISources.ACTIVE_FOCUS_CONTROL_ID_NAME);
		//info.addVariableNameAccess(org.eclipse.ui.ISources.ACTIVE_FOCUS_CONTROL_NAME);
		info.addVariableNameAccess(org.eclipse.ui.ISources.ACTIVE_PART_ID_NAME);
	}
	public org.eclipse.core.expressions.EvaluationResult evaluate(org.eclipse.core.expressions.IEvaluationContext context) {
		final Object variable = context.getVariable(org.eclipse.ui.ISources.ACTIVE_FOCUS_CONTROL_ID_NAME);
		final Object variable2 = context.getVariable(org.eclipse.ui.ISources.ACTIVE_PART_ID_NAME);
//		System.out.println("ACTIVE_FOCUS_CONTROL_ID_NAME : "+variable);
//		System.out.println("ACTIVE_PART_ID_NAME : "+variable2);
		if (variable instanceof String) {
			if (((String) variable).equals(focusId)) {
//		if (variable2 instanceof String) {
//			if (((String) variable2).equals("AAAATestOnglets.editor.article")) { 
//		if (variable instanceof java.util.Collection) {
//			if (((java.util.Collection) variable).contains(focusId)) { 
				return org.eclipse.core.expressions.EvaluationResult.TRUE;
			}
		}
		return org.eclipse.core.expressions.EvaluationResult.FALSE;
	}

	public String getFocusId() {
		return focusId;
	}

	public void setFocusId(String focusId) {
		this.focusId = focusId;
	}
}
