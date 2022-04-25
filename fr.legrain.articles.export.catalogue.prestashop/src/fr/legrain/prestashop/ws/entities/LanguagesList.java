package fr.legrain.prestashop.ws.entities;

import java.util.List;

import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

import org.eclipse.persistence.oxm.annotations.XmlPath;

/**
 * Shop languages
 * @author nicolas
 *
 */
@XmlRootElement(name="prestashop")
public class LanguagesList {
	
	@XmlPath("languages/language")
	@XmlElementWrapper(name="languages")
	private List<Languages> languages = null;

	public List<Languages> getLanguages() {
		return languages;
	}

	public void setLanguages(List<Languages> languages) {
		this.languages = languages;
	}
	
}
