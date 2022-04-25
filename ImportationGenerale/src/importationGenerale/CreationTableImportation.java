package importationGenerale;




import java.io.*;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.borland.dx.sql.dataset.Database;

import fr.legrain.gestCom.Appli.IB_APPLICATION;
import fr.legrain.lib.data.LibChaine;
import fr.legrain.lib.data.LibChaineSQL;


public class CreationTableImportation {

    
    /**
     * Prend en argument le nom d'un fichier et écrit le ce qui est
     * saisi au clavier dans ce fichier. La saisie se termine
     * lorsqu'une ligne blanche est lue.
     */


    
    static public void creeTableSurFichier(String nomTable,Database base)
    {
    	try
    	{      
    		IB_APPLICATION ib = new IB_APPLICATION();
    		
    		String adressedufichier = "C:/Lgrdoss/ADHERENT_cogere.csv";
    		FileReader fr = new FileReader(adressedufichier);
    		BufferedReader br = new BufferedReader(fr);
    		StringBuffer requete = new StringBuffer("");
    		
  		requete.append("create table  ")
  		.append(nomTable).append(" (numcli,numag,initcpt,sigle,nom,adresse,cp,ville,tel1,tel2,moisclotcpt  ) ");
    		
    		String ligne = br.readLine();
    		String[] retour = null;
    		int i =1;
    		while (ligne!=null){
    			retour = ligne.split(";");
    			requete.append("insert into ")
    			.append(nomTable)
    			.append(" values (")
    			.append(i);
    			for(int j=0; j<retour.length; j++) {
    				requete.append(",");
    				if (LibChaine.empty(retour[j]))requete.append("null");
    				else
    					if (j==12 || j==13)
        					requete.append("'"+LibChaineSQL.formatStringSQL(retour[j].trim())+"'");
    					else	
    					requete.append("'"+LibChaineSQL.formatStringSQL(retour[j])+"'");
    			}        			
    			requete.append(")");
  			PreparedStatement rqt;
			try {
				rqt = ib.getFIBBase().getJdbcConnection().prepareStatement(requete.toString());
	  			rqt.execute();
	  			requete=new StringBuffer("");
	  			i++;
			} catch (SQLException e) {
				requete=new StringBuffer("");
				System.out.println("erreur : " + e);
			}
    			
    			ligne = br.readLine();
    		}
    		br.close();
    	}
    	catch(IOException ioe){System.out.println("erreur : " + ioe);
    	}
    }

}

