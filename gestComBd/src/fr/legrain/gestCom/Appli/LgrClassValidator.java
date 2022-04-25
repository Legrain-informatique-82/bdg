package fr.legrain.gestCom.Appli;

import java.util.Map;
import java.util.ResourceBundle;

import org.hibernate.annotations.common.reflection.ReflectionManager;
import org.hibernate.annotations.common.reflection.XClass;
import org.hibernate.validator.ClassValidator;
import org.hibernate.validator.MessageInterpolator;

public class LgrClassValidator extends ClassValidator {

	public LgrClassValidator(Class beanClass) {
		super(beanClass);
		// TODO Auto-generated constructor stub
	}

	public LgrClassValidator(Class beanClass, MessageInterpolator interpolator) {
		super(beanClass, interpolator);
		// TODO Auto-generated constructor stub
	}

	public LgrClassValidator(Class beanClass, ResourceBundle resourceBundle,
			MessageInterpolator interpolator, Map childClassValidators,
			ReflectionManager reflectionManager) {
		super(beanClass, resourceBundle, interpolator, childClassValidators,
				reflectionManager);
		// TODO Auto-generated constructor stub
	}

	public LgrClassValidator(Class beanClass, ResourceBundle resourceBundle) {
		super(beanClass, resourceBundle);
		// TODO Auto-generated constructor stub
	}

	public LgrClassValidator(XClass beanXClass, ResourceBundle resourceBundle,
			MessageInterpolator userInterpolator, Map childClassValidators,
			ReflectionManager reflectionManager) {
		super(beanXClass, resourceBundle, userInterpolator, childClassValidators,
				reflectionManager);
		// TODO Auto-generated constructor stub
	}

}
