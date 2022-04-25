package fr.legrain.lib.data;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.Map;

import org.apache.log4j.Logger;


public class Update extends BdLgr {
	
	
	static Logger logger = Logger.getLogger(Update.class.getName());
	
	protected ModeObjet mode;
	protected String nomTable;
	protected String idValeur; 
	protected String idChamps;
	protected PreparedStatement rqt;
	
	public Update() {
		super();
	}
	
	/**
	 * 
	 * @param nomTable 
	 * @param idChamps nom de l'identifiant de nomTable
	 */
	public Update(String nomTable, String idChamps) {
		this.nomTable = nomTable;
		this.idChamps = idChamps;
	}
	
	/**
	 * 
	 * @param m liste champs/valeur
	 * @return
	 */
	public String createQuery(Map m) {
		String conteneur = "";
		StringBuffer query = new StringBuffer();
		String key = null;
		Iterator i =null;
		int j=0;
		switch(mode.getMode()){
			case C_MO_INSERTION :
				try {
					ResultSet result = null;
					//result=this.getFIBBase().jdbcConnection().prepareStatement(" select max("+idChamps+")+1 from  "+nomTable).executeQuery();
					result=this.getFIBBase().jdbcConnection().prepareStatement("SELECT gen_id(NUM_"+idChamps+", 1) FROM RDB$DATABASE").executeQuery();
					if (result.next())
						idValeur=String.valueOf(result.getInt(1)) ;
				}catch(SQLException e){
					logger.error(e);
				};
				j=0;
				query.append("insert into ").append(nomTable).append(" (").append(idChamps).append(" ,");
				for (i = m.keySet().iterator();i.hasNext(); ) {
					key = (String) i.next();
				    if(j>0) {
				    	query.append(",").append(key);
				    } else {
				    	query.append(key);
				    }
				    j++;
				}
				query.append(") values('").append(idValeur).append("',");
				j=0;
				for (i = m.keySet().iterator(); i.hasNext(); ) {
					key = (String) i.next();
					conteneur="null";
					if (m.get(key)!=null)
					  conteneur="'"+LibChaineSQL.formatStringSQL(m.get(key).toString())+"'";
					if(j>0) {				    	
				    	query.append(",").append(conteneur);
				    } else {
				    	query.append(conteneur);
				    }
				    j++;
				}
				query.append(");");
				break;
			case C_MO_EDITION :
				j=0;
				query.append("update ").append(nomTable).append(" set ");
				for (i = m.keySet().iterator(); i.hasNext(); ) {
					key = (String) i.next();
					conteneur="null";
					if (m.get(key)!=null)
					  conteneur="'"+LibChaineSQL.formatStringSQL(m.get(key).toString())+"'";
					if(j>0) {
				    	query.append(",").append(key).append(" = ").append(conteneur);
				    } else {
				    	query.append(key).append(" = ").append(conteneur);
				    }
				    j++;
				}
				query.append(" where ").append(idChamps).append(" = '").append(idValeur).append("' ;");
				break;
			case C_MO_SUPPRESSION :
				query.append("delete from ").append(nomTable).append(" where ").append(idChamps).append(" = '").append(idValeur).append("' ;");
				break;
			default:
				logger.debug(mode.getMode());
				break;
		}
		if(logger.isDebugEnabled())
			logger.debug(query.toString());
		return query.toString();
	}
	
	public void execution(String rqt) throws SQLException{
		try {			
			this.getFIBBase().getJdbcConnection().prepareStatement(rqt).executeUpdate();

		} catch (Exception e) {
			logger.error("Erreur : execution", e);
			throw new SQLException(e.getMessage());
		}
	}

	public String getIdChamps() {
		return idChamps;
	}

	public void setIdChamps(String idChamps) {
		this.idChamps = idChamps;
	}

	public String getIdValeur() {
		return idValeur;
	}

	public void setIdValeur(String idValeur) {
		this.idValeur = idValeur;
	}

	public ModeObjet getMode() {
		return mode;
	}

	public void setMode(ModeObjet mode) {
		this.mode = mode;
	}

	public String getNomTable() {
		return nomTable;
	}

	public void setNomTable(String nomTable) {
		this.nomTable = nomTable;
	}

	public PreparedStatement getRqt() {
		return rqt;
	}

	public void setRqt(PreparedStatement rqt) {
		this.rqt = rqt;
	}
	
}
