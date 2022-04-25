package fr.legrain.gestCom.Appli;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.mapping.Property;
import org.hibernate.validator.PropertyConstraint;
import org.hibernate.validator.Validator;

import fr.legrain.articles.dao.TaUniteDAO;
import fr.legrain.lib.data.ExceptLgr;
import fr.legrain.lib.data.MessCtrlLgr;
import fr.legrain.lib.data.ModeObjet;

public class LgrHibernateValidator implements Validator<LgrHibernateValidated>, PropertyConstraint {
	
	private static final Log logger = LogFactory.getLog(TaUniteDAO.class);

	private String champ;
	private String table;
	private Class<?> clazz;
	private JPACtrl_Application ctrl = new JPACtrl_Application();

//	part of the Validator<Annotation> contract,
//	allows to get and use the annotation values
	public void initialize(LgrHibernateValidated parameters) {
		champ = parameters.champ();
		table = parameters.table();
		clazz = parameters.clazz();
	}

//	part of the property constraint contract
	public boolean isValid(Object value) {
		boolean valid = true;
		
		MessCtrlLgr mess = new MessCtrlLgr();
		mess.setNomChamp(champ);
		mess.setNomTable(table);
		mess.setEntityClass(clazz);
		ModeObjet m = new ModeObjet();
		m.setMode(ModeObjet.EnumModeObjet.C_MO_EDITION);
		mess.setModeObjet(m);
		mess.setValeur(value.toString());
		try {
			ctrl.ctrlSaisie(mess);
		} catch (ExceptLgr e) {
			logger.error("",e);
			valid = false;
		}
		
//		if (value==null) return true;
//		if ( !(value instanceof String) ) return false;
//		String string = (String) value;
//		if (type == CapitalizeType.ALL) {
//		return string.equals( string.toUpperCase() );
//		}
//		else {
//		String first = string.substring(0,1);
//		return first.equals( first.toUpperCase();
//		}
		return valid;
	}

	public void apply(Property arg0) {
		// TODO Auto-generated method stub
	}
}        