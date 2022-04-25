package fr.legrain.lib.gui;

import java.awt.BorderLayout;

import javax.swing.JPanel;

public class PaMessageBox extends JPanel {
	private JPanel paGauche = new JPanel();
	private JPanel paDroite = new JPanel();
	private JPanel paHaut = new JPanel();
	private JPanel paBas = new JPanel();
	private JPanel paMessage = new JPanel();
	
	PaMessageBox() {
		this.setLayout(new BorderLayout());
		this.add(paGauche,BorderLayout.WEST);
		this.add(paDroite,BorderLayout.EAST);
		this.add(paHaut,BorderLayout.NORTH);
		this.add(paBas,BorderLayout.SOUTH);
		this.add(paMessage,BorderLayout.CENTER);
	}

	public JPanel getPaBas() {
		return paBas;
	}

	public void setPaBas(JPanel paBas) {
		this.paBas = paBas;
	}

	public JPanel getPaDroite() {
		return paDroite;
	}

	public void setPaDroite(JPanel paDroite) {
		this.paDroite = paDroite;
	}

	public JPanel getPaGauche() {
		return paGauche;
	}

	public void setPaGauche(JPanel paGauche) {
		this.paGauche = paGauche;
	}

	public JPanel getPaHaut() {
		return paHaut;
	}

	public void setPaHaut(JPanel paHaut) {
		this.paHaut = paHaut;
	}

	public JPanel getPaMessage() {
		return paMessage;
	}

	public void setPaMessage(JPanel paMessage) {
		this.paMessage = paMessage;
	}
}
