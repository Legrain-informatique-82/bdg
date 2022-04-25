package fr.legrain.lib.gui;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.event.EventListenerList;


public class MessageBox extends JDialog {
	protected EventListenerList listenerList = new EventListenerList();
	
	//panel par defaut
	//pouvoir ajouter son propre panel
	//message + titre
	//type d'action close,...
	private PaMessageBox panel = new PaMessageBox();
	
	public final static String YES    = "Oui"; 
	public final static String NO     = "Non";
	public final static String CANCEL = "Annuler";
	
	//type de question
	public final static int BTN_YES           = 1;
	public final static int BTN_YES_NO        = 2;
	public final static int BTN_YES_NO_CANCEL = 3;
	
	//reponse possible
	public final static int REP_YES           = 4;
	public final static int REP_NO            = 5;
	public final static int REP_CANCEL        = 6;
	
	//Action pour laquelle le message a été afficher: Fermer, Annuler,...
	private String command = null;
	
	private Action actYes = null;
	private Action actNo  = null;
	private Action actCancel = null;
	
	private JButton btnYes = new JButton();
	private JButton btnNo  = new JButton();
	private JButton btnCancel = new JButton();
	
	static private MessageBox box = null;
	
	public MessageBox() {
		super();
	}
	
	static public MessageBox getMessageBox(String titre, String message, String command, Action actYes, Action actNo, Action actCancel) {
		box = new MessageBox(titre,message,command,actYes,actNo,actCancel);
		return box;
	}
	
	static public MessageBox getMessageBox(String titre, String message, String command) {
		box = new MessageBox(titre,message,command);
		return box;
	}
	
	public MessageBox(String titre, String message, String command, Action actYes, Action actNo, Action actCancel) {
		this.setSize(300, 200);
		centreDialog(this);
		this.setTitle(titre);
		this.setResizable(false);
		this.setAlwaysOnTop(true);
		panel.getPaMessage().add(new JLabel(message));
		this.command = command;
		this.actYes = actYes;
		this.actNo = actNo;
		this.actCancel = actCancel;
		
		if(actYes!=null) {
			setActYes(actYes);
			panel.getPaBas().add(btnYes);
		}
		if(actNo!=null) {
			setActNo(actNo);
			panel.getPaBas().add(btnNo);
		}
		if(actCancel!=null) {
			setActCancel(actCancel);
			panel.getPaBas().add(btnCancel);
		}
		
		this.add(panel);
	}
	
	public MessageBox(String titre, String message, String command) {
		this.setSize(300, 200);
		centreDialog(this);
		this.setTitle(titre);
		this.setResizable(false);
		this.setAlwaysOnTop(true);
		panel.getPaMessage().add(new JLabel(message));
		this.command = command;
		this.actYes = new DefaultActionYes(MessageBox.YES);
		this.actNo = new DefaultActionNo(MessageBox.NO);
		this.actCancel = new DefaultActionCancel(MessageBox.CANCEL);
		
		if(actYes!=null) {
			setActYes(actYes);
			panel.getPaBas().add(btnYes);
		}
		if(actNo!=null) {
			setActNo(actNo);
			panel.getPaBas().add(btnNo);
		}
		if(actCancel!=null) {
			setActCancel(actCancel);
			panel.getPaBas().add(btnCancel);
		}
		
		this.add(panel);
	}
	
	private MessageBox getThis() {
		return this;
	}
	
	public void centreDialog(JDialog d) {
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		Dimension dialogSize = d.getSize();
		if (dialogSize.height > screenSize.height) {
			dialogSize.height = screenSize.height;
		}
		if (dialogSize.width > screenSize.width) {
			dialogSize.width = screenSize.width;
		}
		d.setLocation( (screenSize.width - dialogSize.width) / 2, (screenSize.height - dialogSize.height) / 2);
	}
	
	class DefaultActionYes extends AbstractAction {
		public DefaultActionYes(String name) {
			super(name);
		}
		
		public void actionPerformed(ActionEvent e) {
			getThis().fireMessageBoxYes(new MessageBoxEvent(getThis(),REP_YES,command));
			getThis().dispose();
		}
	}
	
	class DefaultActionNo extends AbstractAction {
		public DefaultActionNo(String name) {
			super(name);
		}
		
		public void actionPerformed(ActionEvent e) {
			getThis().fireMessageBoxNo(new MessageBoxEvent(getThis(),REP_NO,command));
			getThis().dispose();
		}
	}
	
	class DefaultActionCancel extends AbstractAction {
		public DefaultActionCancel(String name) {
			super(name);
		}
		
		public void actionPerformed(ActionEvent e) {
			getThis().fireMessageBoxCancel(new MessageBoxEvent(getThis(),REP_CANCEL,command));
			getThis().dispose();
		}
	}
	
	public void addMessageBoxListener(MessageBoxListener l) {
		listenerList.add(MessageBoxListener.class, l);
	}
	
	public void removeMessageBoxListener(MessageBoxListener l) {
		listenerList.remove(MessageBoxListener.class, l);
	}
	
	protected void fireReponseMessageBox(MessageBoxEvent e) {
		Object[] listeners = listenerList.getListenerList();
		for (int i = listeners.length - 2; i >= 0; i -= 2) {
			if (listeners[i] == MessageBoxListener.class) {
				if (e == null)
					e = new MessageBoxEvent(this);
				( (MessageBoxListener) listeners[i + 1]).reponseMessageBox(e);
			}
		}
	}
	
	protected void fireMessageBoxYes(MessageBoxEvent e) {
		Object[] listeners = listenerList.getListenerList();
		for (int i = listeners.length - 2; i >= 0; i -= 2) {
			if (listeners[i] == MessageBoxListener.class) {
				if (e == null)
					e = new MessageBoxEvent(this,REP_YES,command);
				( (MessageBoxListener) listeners[i + 1]).messageBoxYes(e);
			}
		}
	}
	
	protected void fireMessageBoxNo(MessageBoxEvent e) {
		Object[] listeners = listenerList.getListenerList();
		for (int i = listeners.length - 2; i >= 0; i -= 2) {
			if (listeners[i] == MessageBoxListener.class) {
				if (e == null)
					e = new MessageBoxEvent(this,REP_NO,command);
				( (MessageBoxListener) listeners[i + 1]).messageBoxNo(e);
			}
		}
	}
	
	protected void fireMessageBoxCancel(MessageBoxEvent e) {
		Object[] listeners = listenerList.getListenerList();
		for (int i = listeners.length - 2; i >= 0; i -= 2) {
			if (listeners[i] == MessageBoxListener.class) {
				if (e == null)
					e = new MessageBoxEvent(this,REP_CANCEL,command);
				( (MessageBoxListener) listeners[i + 1]).messageBoxCancel(e);
			}
		}
	}

	public Action getActCancel() {
		return actCancel;
	}

	public void setActCancel(Action actCancel) {
		this.actCancel = actCancel;
		btnCancel.setAction(actCancel);
		btnCancel.setText(CANCEL);
	}

	public Action getActNo() {
		return actNo;
	}

	public void setActNo(Action actNo) {
		this.actNo = actNo;
		btnNo.setAction(actNo);
		btnNo.setText(NO);
	}

	public Action getActYes() {
		return actYes;
	}

	public void setActYes(Action actYes) {
		this.actYes = actYes;
		btnYes.setAction(actYes);
		btnYes.setText(YES);
	}
}
