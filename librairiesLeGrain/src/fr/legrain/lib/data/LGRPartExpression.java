package fr.legrain.lib.data;

import java.util.List;

import org.eclipse.core.expressions.EvaluationResult;
import org.eclipse.core.expressions.Expression;
import org.eclipse.core.expressions.IEvaluationContext;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.IWorkbenchPart;

public class LGRPartExpression extends Expression {



	private final List<Control> listeComposantFocusable;
	private Control focusCourant = null;
	

	public LGRPartExpression(final IWorkbenchPart activePart,
			List<Control> listeComposantFocusable) {

		if(listeComposantFocusable==null)
			throw new NullPointerException("The listeComposantFocusable must not be null"); //$NON-NLS-1$
		this.listeComposantFocusable = listeComposantFocusable;
	}
	
	
	@Override
	public EvaluationResult evaluate(IEvaluationContext context)
			throws CoreException {
		for (Control element : listeComposantFocusable) {
			if(element.isFocusControl())
				return EvaluationResult.TRUE;
		}
		return EvaluationResult.FALSE;
	}

}
