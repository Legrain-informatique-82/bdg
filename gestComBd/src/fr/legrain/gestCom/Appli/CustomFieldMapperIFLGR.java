package fr.legrain.gestCom.Appli;

import net.sf.dozer.util.mapping.CustomFieldMapperIF;
import net.sf.dozer.util.mapping.classmap.ClassMap;
import net.sf.dozer.util.mapping.fieldmap.FieldMap;
import org.hibernate.Hibernate;
public class CustomFieldMapperIFLGR implements CustomFieldMapperIF {
   private FieldMap fieldMapRetour = null;
   private String fieldEntree = null;
	@Override
	
	
	public boolean mapField(Object source, Object destination, Object sourceFieldValue, ClassMap classMap,
			FieldMap fieldMapping) {
		fieldMapping.getSrcFieldType(source.getClass());
		if(fieldEntree.equals(fieldMapping.getDestFieldName()))
			fieldMapRetour=fieldMapping;
				//
		if(sourceFieldValue != null && Hibernate.isInitialized(sourceFieldValue)) {
		  return false;
		} else {
			fieldMapping.writeDestValue(destination, null);
			return true;
		}
		
	}
	public CustomFieldMapperIFLGR( String fieldEntree) {
		super();
		this.fieldMapRetour = null;
		this.fieldEntree = fieldEntree;
	}

	public String getFieldEntree() {
		return fieldEntree;
	}
	public void setFieldEntree(String fieldEntree) {
		this.fieldEntree = fieldEntree;
	}
	public FieldMap getFieldMapRetour() {
		return fieldMapRetour;
	}
	public void setFieldMapRetour(FieldMap fieldMapRetour) {
		this.fieldMapRetour = fieldMapRetour;
	}
	public String getSrcFieldType() {
		return fieldMapRetour.getSrcFieldType();
	}
	public Class getSrcFieldType(Class runtimeSrcClass) {
		return fieldMapRetour.getSrcFieldType(runtimeSrcClass);
	}

}
