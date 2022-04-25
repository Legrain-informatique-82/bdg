package fr.legrain.articles.dao;


import org.hibernate.validator.ClassValidator;
import org.hibernate.validator.InvalidValue;

import fr.legrain.gestCom.Appli.LgrDozerMapper;


public class LgrValidator<DTO,Entity> implements java.io.Serializable {
	
	public InvalidValue[] validate(DTO a,Entity b){
		Class<Entity> c = null;
		
		LgrDozerMapper<DTO,Entity> mapper = new LgrDozerMapper<DTO,Entity>();
		mapper.map((DTO) a,b);
		
		ClassValidator<Entity> uniteValidator = new ClassValidator<Entity>(c );
		InvalidValue[] iv = uniteValidator.getInvalidValues(b);
//		System.err.println(iv);
		return iv;
	}

}
