package fr.legrain.lib.data;

import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Shell;

public class MessageDialogLGR extends org.eclipse.jface.dialogs.MessageDialog {

	public MessageDialogLGR(Shell parentShell, String dialogTitle,
			Image dialogTitleImage, String dialogMessage, int dialogImageType,
			String[] dialogButtonLabels, int defaultIndex) {
		super(parentShell, dialogTitle, dialogTitleImage, dialogMessage,
				dialogImageType, dialogButtonLabels, defaultIndex);
		// TODO Auto-generated constructor stub
	}

	
    /**
     * Convenience method to open a simple confirm (OK/Cancel) dialog.
     * 
     * @param parent
     *            the parent shell of the dialog, or <code>null</code> if none
     * @param title
     *            the dialog's title, or <code>null</code> if none
     * @param message
     *            the message
     * @return <code>true</code> if the user presses the OK button,
     *         <code>false</code> otherwise
     */
    public static boolean openConfirm(Shell parent, String title, String message,int defaultIndex) {
        MessageDialog dialog = new MessageDialog(parent, title, null, // accept
                // the
                // default
                // window
                // icon
                message, QUESTION, new String[] { IDialogConstants.OK_LABEL,
                        IDialogConstants.CANCEL_LABEL }, defaultIndex); 
        return dialog.open() == 0;
    }

    /**
     * Convenience method to open a standard error dialog.
     * 
     * @param parent
     *            the parent shell of the dialog, or <code>null</code> if none
     * @param title
     *            the dialog's title, or <code>null</code> if none
     * @param message
     *            the message
     */
    public static void openError(Shell parent, String title, String message,int defaultIndex) {
        MessageDialog dialog = new MessageDialog(parent, title, null, // accept
                // the
                // default
                // window
                // icon
                message, ERROR, new String[] { IDialogConstants.OK_LABEL }, defaultIndex); 
        dialog.open();
        return;
    }

    /**
     * Convenience method to open a standard information dialog.
     * 
     * @param parent
     *            the parent shell of the dialog, or <code>null</code> if none
     * @param title
     *            the dialog's title, or <code>null</code> if none
     * @param message
     *            the message
     */
    public static void openInformation(Shell parent, String title,
            String message,int defaultIndex) {
        MessageDialog dialog = new MessageDialog(parent, title, null, // accept
                // the
                // default
                // window
                // icon
                message, INFORMATION,
                new String[] { IDialogConstants.OK_LABEL }, defaultIndex);
        dialog.open();
        return;
    }

    /**
     * Convenience method to open a simple Yes/No question dialog.
     * 
     * @param parent
     *            the parent shell of the dialog, or <code>null</code> if none
     * @param title
     *            the dialog's title, or <code>null</code> if none
     * @param message
     *            the message
     * @return <code>true</code> if the user presses the OK button,
     *         <code>false</code> otherwise
     */
    public static boolean openQuestion(Shell parent, String title,
            String message,int defaultIndex) {
        MessageDialog dialog = new MessageDialog(parent, title, null, // accept
                // the
                // default
                // window
                // icon
                message, QUESTION, new String[] { IDialogConstants.YES_LABEL,
                        IDialogConstants.NO_LABEL }, defaultIndex); 
        return dialog.open() == 0;
    }
    
    /**
     * Convenience method to open a standard warning dialog.
     * 
     * @param parent
     *            the parent shell of the dialog, or <code>null</code> if none
     * @param title
     *            the dialog's title, or <code>null</code> if none
     * @param message
     *            the message
     */
    public static void openWarning(Shell parent, String title, String message,int defaultIndex) {
        MessageDialog dialog = new MessageDialog(parent, title, null, // accept
                // the
                // default
                // window
                // icon
                message, WARNING, new String[] { IDialogConstants.OK_LABEL }, defaultIndex); 
        dialog.open();
        return;
    }

}
