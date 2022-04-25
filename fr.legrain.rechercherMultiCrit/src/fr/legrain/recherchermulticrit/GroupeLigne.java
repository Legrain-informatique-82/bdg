/**
 * 
 */
package fr.legrain.recherchermulticrit;

import java.awt.Color;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.forms.widgets.FormToolkit;

/**
 * Classe de Groupe de Ligne contenant les lignes liées par l'utilisateur
 * @author nicolas²
 *
 */
public class GroupeLigne {

	private List<Ligne> groupe;
	private Group groupe_composite;
	private Composite parent;
	private Composite lignespec;
	private Combo andOrGroup;
	private Label lblandOr;
	private int numero;

	/**
	 * Constructeur par défaut du groupe
	 */
	public GroupeLigne(int numero,Composite parent, FormToolkit toolkit){
		this.numero = numero;
		this.parent= parent;
		groupe = new ArrayList<Ligne>();
		groupe_composite = new Group(parent,SWT.NONE);
		groupe_composite.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, 3, 1));
		groupe_composite.setLayout(new GridLayout(1, false));
		groupe_composite.setText("Groupe "+numero);
		if (numero != 1){
			lignespec = toolkit.createComposite(groupe_composite);
			lignespec.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
			lignespec.setLayout(new GridLayout(2, false));
			lblandOr = new Label (lignespec,SWT.NONE);
			lblandOr.setText("Relation avec le groupe précédent :");
			andOrGroup = new Combo(lignespec,SWT.READ_ONLY);
			andOrGroup.add("ET");
			andOrGroup.add("OU");
			andOrGroup.select(1);
			groupe_composite.layout();

			
		}
		
		layout();
	}

	/**
	 * On ajoute une nouvelle ligne au groupe
	 * @param laligne la ligne à ajouter au groupe
	 */
	public void addLigne(Ligne laligne){
		groupe.add(laligne);
	}

	/**
	 * On insère la ligne à la position demandée
	 * @param pos la position de la ligne
	 * @param laligne la ligne à insérer
	 */
	public void insertLigne(int pos, Ligne laligne){
		groupe.add(pos,laligne);
	}

	/**
	 * Méthode permettant de monter une ligne dans le groupe
	 * @param laligne la ligne que l'on souhaite monter
	 */
	public void upLigne(Ligne laligne){
		int posLigne = groupe.lastIndexOf(laligne); // on récupère l'index de la ligne
		if (posLigne !=0 ){ // la ligne n'est pas la première du groupe
			// On procède à l'échange entre les lignes
			Ligne temp = groupe.set(posLigne-1,groupe.get(posLigne));
			groupe.set(posLigne, temp); 
		}
	}

	/**
	 * Méthode permettant de descendre une ligne dans le groupe
	 * @param laligne la ligne que l'on souhaite descendre
	 */
	public void downLigne(Ligne laligne){
		int posLigne = groupe.lastIndexOf(laligne); // on récupère l'index de la ligne
		if (posLigne < groupe.size()){ // la ligne n'est pas la dernière du groupe
			// On procède à l'échange entre les lignes
			Ligne temp = groupe.set(posLigne+1,groupe.get(posLigne));
			groupe.set(posLigne, temp);
		}
	}
	
	
	/** 
	 * Méthode retourant la taille du groupe
	 * @return la taille du groupe
	 */
	public int getSize() {
		return groupe.size();
	}

	/**
	 * Méthode permettant de supprimer une ligne dans le groupe
	 * @param pos
	 */
	public void remove(int pos) {
		groupe.remove(pos);
	}

	/** 
	 * Méthode permettant de récupérer le groupe lui 
	 * même sous forme de List de ligne
	 * @return
	 */
	public List<Ligne> getGroupe() {
		return groupe;
	}

	/**
	 * Renvoi la dernière position connue de la ligne passée en paramètre
	 * @param laligne dont on souhaite connaître la position
	 * @return
	 */
	public int lastPos(Ligne laligne){
		return groupe.lastIndexOf(laligne);
	}

	public Composite getGroupe_composite() {
		return groupe_composite;
	}

	public void layout(){
		groupe_composite.layout();
		parent.layout();
	}
	
	public void refreshNumGroupe(int numero){
		groupe_composite.setText("Groupe "+numero);
	}

	public Composite getLignespec() {
		return lignespec;
	}

	public Combo getAndOrGroup() {
		return andOrGroup;
	}

	public int getNumero() {
		return numero;
	}





}
