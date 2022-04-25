package fr.legrain.gestCom.librairiesEcran.swt;

import org.eclipse.core.runtime.Assert;
import org.eclipse.jface.preference.StringFieldEditor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.events.FocusAdapter;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Text;

public class StringCommentaireEditor extends StringFieldEditor {
	
    /**
     * The text field, or <code>null</code> if none.
     */
    Text textField;
    
    /**
     * The validation strategy; 
     * <code>VALIDATE_ON_KEY_STROKE</code> by default.
     */
    private int validateStrategy = VALIDATE_ON_KEY_STROKE;
	
    /**
     * Text limit of text field in characters; initially unlimited.
     */
    private int textLimit = UNLIMITED;
    
    public StringCommentaireEditor(String commentaire, String string,
			Composite fieldEditorParent) {
    	super(commentaire,string,fieldEditorParent);
		// TODO Auto-generated constructor stub
	}

	/**
     * Returns this field editor's text control.
     * <p>
     * The control is created if it does not yet exist
     * </p>
     *
     * @param parent the parent
     * @return the text control
     */
    public Text getTextControl(Composite parent) {
//    	tfLIBL_COMMENTAIRELData.horizontalAlignment = GridData.FILL;
//    	tfLIBL_COMMENTAIRELData.horizontalSpan = 3;
//    	tfLIBL_COMMENTAIRELData.grabExcessHorizontalSpace = true;
//    	tfLIBL_COMMENTAIRELData.verticalAlignment = GridData.FILL;
//    	tfLIBL_COMMENTAIRELData.grabExcessVerticalSpace = true;
    	if (textField == null) {
            textField = new Text(parent, SWT.MULTI
					| SWT.WRAP
					| SWT.H_SCROLL
					| SWT.V_SCROLL
					| SWT.BORDER);
            textField.setFont(parent.getFont());
            switch (validateStrategy) {
            case VALIDATE_ON_KEY_STROKE:
                textField.addKeyListener(new KeyAdapter() {

                    /* (non-Javadoc)
                     * @see org.eclipse.swt.events.KeyAdapter#keyReleased(org.eclipse.swt.events.KeyEvent)
                     */
                    public void keyReleased(KeyEvent e) {
                        valueChanged();
                    }
                });

                break;
            case VALIDATE_ON_FOCUS_LOST:
                textField.addKeyListener(new KeyAdapter() {
                    public void keyPressed(KeyEvent e) {
                        clearErrorMessage();
                    }
                });
                textField.addFocusListener(new FocusAdapter() {
                    public void focusGained(FocusEvent e) {
                        refreshValidState();
                    }

                    public void focusLost(FocusEvent e) {
                        valueChanged();
                        clearErrorMessage();
                    }
                });
                break;
            default:
                Assert.isTrue(false, "Unknown validate strategy");//$NON-NLS-1$
            }
            textField.addDisposeListener(new DisposeListener() {
                public void widgetDisposed(DisposeEvent event) {
                    textField = null;
                }
            });
            if (textLimit > 0) {//Only set limits above 0 - see SWT spec
                textField.setTextLimit(textLimit);
            }
        } else {
            checkParent(textField, parent);
        }
        return textField;
    }

    

}
