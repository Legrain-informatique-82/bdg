package fr.legrain.lib.data;



import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormatSymbols;

import org.eclipse.swt.events.VerifyEvent;
import org.eclipse.swt.events.VerifyListener;
import org.eclipse.swt.widgets.Text;

public class QueDesChiffresIntervaleDecimale implements VerifyListener{
	private int precision=0;
	private int taille=0;
	private String nomChampBase;
	private String nomTable;
	private DatabaseMetaData metadonnees=null;

	public QueDesChiffresIntervaleDecimale() {
		super();
	}

	public QueDesChiffresIntervaleDecimale(String nomChampBase, String nomTable,
			DatabaseMetaData metadonnees) {
		super();
		this.nomChampBase = nomChampBase;
		this.nomTable = nomTable;
		this.metadonnees = metadonnees;
		try {
			ResultSet rs = this.metadonnees.getColumns(null, null, nomTable, nomChampBase);
			if (rs.next()) {
				taille= rs.getInt(7);
				precision= rs.getInt(9);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}


	public QueDesChiffresIntervaleDecimale(int precision) {
		super();
		this.precision = precision;
	}

	public void verifyText(VerifyEvent e) {
		int nbChiffreAvantVirgule = 0;
		int nbChiffreApresVirgule = 0;
		
		e.doit = Character.isDigit(e.character)
						|| Character.isIdentifierIgnorable(e.character)
						|| LibChaine.formatChiffre(String.valueOf(e.character));
		if(e.doit)
			e.doit=!e.text.equals(",");
		
		if(e.doit && e.widget instanceof Text){
			try {
				String apresValide = ((Text)e.widget).getText().substring(0, e.start)+e.character+((Text)e.widget).getText().substring(e.end);
				if(apresValide.trim().compareTo("")!=0 
						&& apresValide.trim().compareTo(String.valueOf(DecimalFormatSymbols.getInstance().getMinusSign()))!=0){
					//pas vide et pas moins (-)
					if(precision>0){					
						//String valeur = apresValide.trim().substring(apresValide.trim().indexOf(".")+1);
						String valeur = apresValide.trim();
						//if (valeur.compareTo(apresValide.trim())==0)
						//	valeur="";
						//nbChiffreApresVirgule = valeur.length();
						int intValue = LibConversion.stringToInteger(valeur);
						if(intValue>precision)
						//if (nbChiffreApresVirgule >precision) 
							e.doit=false; 
					}
//					if(taille>0) {
//						String valeur = null;
//						if(apresValide.trim().indexOf(".")!=-1)
//							valeur = apresValide.trim().substring(0,apresValide.trim().indexOf("."));
//						else
//							valeur = apresValide.trim();
//							
////						if (valeur.compareTo(apresValide.trim())==0)
////							valeur="";
//						nbChiffreAvantVirgule = valeur.length();
//						if ((nbChiffreAvantVirgule + nbChiffreApresVirgule)>taille) 
//							e.doit=false; 
//					}
				}
			} catch (Exception e2) {
				e.doit=false;
			}
		}

	}


	public int getPrecision() {
		return precision;
	}
	public void setPrecision(int precision) {
		this.precision = precision;
	}

	public String getNomChampBase() {
		return nomChampBase;
	}

	public void setNomChampBase(String nomChampBase) {
		this.nomChampBase = nomChampBase;
	}

	public String getNomTable() {
		return nomTable;
	}

	public void setNomTable(String nomTable) {
		this.nomTable = nomTable;
	}

	public DatabaseMetaData getMetadonnees() {
		return metadonnees;
	}

	public void setMetadonnees(DatabaseMetaData metadonnees) {
		this.metadonnees = metadonnees;
	}

}
