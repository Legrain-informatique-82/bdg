package fr.legrain.articles.champsupp.controller;

import java.io.IOException;

import javassist.CannotCompileException;
import javassist.ClassClassPath;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtField;
import javassist.CtMethod;
import javassist.CtNewMethod;
import javassist.Modifier;
import javassist.NotFoundException;

public class InitClasse {
	public void testJavassist() {
		String nomChamp = null;
		try {
			//le nom de la classe à éditer
			ClassPool cp = ClassPool.getDefault();
			 cp.insertClassPath(new ClassClassPath(this.getClass()));

			CtClass	classToEdit = cp.get("fr.legrain.articles.champsupp.editors.PaChampSuppArticle");

			//type de l'attribut
			CtClass classType = cp.get("org.eclipse.swt.widgets.Label");

			//le nom de l'attribut
			CtField newField = new CtField(classType, "laChamp1", classToEdit);
			newField.setModifiers(Modifier.PRIVATE);
			classToEdit.addField(newField);

			//get"+"<mettre ici le nom de l'attribut
			/** ajout d'un getter dans la nouvelle classe */
			CtMethod getter = CtNewMethod.getter("get"+"LaChamp1", newField);
			classToEdit.addMethod(getter);

			//set"+"<mettre ici le nom de l'attribut
			/** ajout d'un setter dans la nouvelle classe */
			CtMethod setter = CtNewMethod.setter("set"+"LaChamp1", newField);
			classToEdit.addMethod(setter);
			
			classToEdit.writeFile();
			
//			classToEdit.toClass();
			
			//vue.getLaChamp1();
		} catch (NotFoundException e) {
			e.printStackTrace();
		} catch (CannotCompileException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
