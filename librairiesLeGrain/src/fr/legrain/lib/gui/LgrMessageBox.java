package fr.legrain.lib.gui;

import java.awt.Component;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.JButton;
import javax.swing.JDialog;


//Direct Use:
//To create and use an JOptionPane directly, the standard pattern is roughly as follows:
//
//JOptionPane pane = new JOptionPane(arguments);
//pane.set.Xxxx(...); // Configure
//JDialog dialog = pane.createDialog(parentComponent, title);
//dialog.show();
//Object selectedValue = pane.getValue();
//if(selectedValue == null)
//  return CLOSED_OPTION;
////If there is not an array of option buttons:
//if(options == null) {
//if(selectedValue instanceof Integer)
//return ((Integer)selectedValue).intValue();
//return CLOSED_OPTION;
//}
////If there is an array of option buttons:
//for(int counter = 0, maxCounter = options.length;
//counter < maxCounter; counter++) {
//if(options[counter].equals(selectedValue))
//return counter;
//}
//return CLOSED_OPTION;
	 
public class LgrMessageBox extends JDialog implements FocusListener {
	
	private Component source;
	private JButton b = new JButton("ok");
	
	public LgrMessageBox(){
		super();
		setSize(150,100);
		setTitle("le titre");
		this.add(b);
		
		b.addFocusListener(new FocusAdapter() {
			public void focusGained(FocusEvent e) {
				// TODO Auto-generated method stub
				source = e.getOppositeComponent();
				System.out.println("source "+source.getName());
			}
	
			public void focusLost(FocusEvent e) {
				source.requestFocus();
				System.out.println("re source "+source.getName());
			}
		});
	}
	
	public void setVisible(boolean b) {
		// TODO Auto-generated method stub
		super.setVisible(b);
		this.requestFocusInWindow();
	}
	
	public void focusGained(FocusEvent e) {
		// TODO Auto-generated method stub
		this.source = e.getOppositeComponent();
		System.out.println("source "+source.getName());
	}

	public void focusLost(FocusEvent e) {
		source.requestFocus();
		System.out.println("re source "+source.getName());
	} 

	//Creates a non-modal dialog without a title and without a specified Frame owner.
	LgrMessageBox(Component source){
		super();
		this.source = source;
	}
	
//	//Creates a non-modal dialog without a title with the specified Dialog as its owner.
//    LgrMessageBox(Dialog owner, Component source){
//    	super();
//    	this.source = source;
//    }
//    
//    //Creates a modal or non-modal dialog without a title and with the specified owner dialog.
//    LgrMessageBox(Dialog owner, boolean modal, Component source){
//    	super(owner, modal);
//    	this.source = source;
//    }
//    
//    //Creates a non-modal dialog with the specified title and with the specified owner dialog.
//    LgrMessageBox(Dialog owner, String title, JComponent source){
//    	super(owner, title);
//    	this.source = source;
//    }
//    
//    //Creates a modal or non-modal dialog with the specified title and the specified owner frame.
//    LgrMessageBox(Dialog owner, String title, boolean modal, JComponent source){
//    	super(owner, title, modal);
//    	this.source = source;
//    }
//    
//    //Creates a modal or non-modal dialog with the specified title, owner Dialog, and GraphicsConfiguration.
//    LgrMessageBox(Dialog owner, String title, boolean modal, GraphicsConfiguration gc, JComponent source){
//    	super(owner, title, modal, gc);
//    	this.source = source;
//    }
//    
//    //Creates a non-modal dialog without a title with the specified Frame as its owner.
//    LgrMessageBox(Frame owner, JComponent source){
//    	super(owner);
//    	this.source = source;
//    }
//    
//    //Creates a modal or non-modal dialog without a title and with the specified owner Frame.
//    LgrMessageBox(Frame owner, boolean modal, JComponent source){
//    	super(owner, modal);
//    	this.source = source;
//    }
//    
//    //Creates a non-modal dialog with the specified title and with the specified owner frame.
//    LgrMessageBox(Frame owner, String title, JComponent source){
//    	super(owner, title);
//    	this.source = source;
//    }
//    
//    //Creates a modal or non-modal dialog with the specified title and the specified owner Frame.
//    LgrMessageBox(Frame owner, String title, boolean modal, JComponent source){
//    	super(owner, title, modal);
//    	this.source = source;
//    }
//    
//    //Creates a modal or non-modal dialog with the specified title, owner Frame, and GraphicsConfiguration.
//    LgrMessageBox(Frame owner, String title, boolean modal, GraphicsConfiguration gc, JComponent source){
//    	super(owner, title, modal, gc);
//    	this.source = source;
//    }

}
