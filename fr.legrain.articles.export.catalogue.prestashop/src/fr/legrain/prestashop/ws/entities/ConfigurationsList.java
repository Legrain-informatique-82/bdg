package fr.legrain.prestashop.ws.entities;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

import org.eclipse.persistence.oxm.annotations.XmlPath;

@XmlRootElement(name="prestashop")
public class ConfigurationsList {

	@XmlElementWrapper(name="configurations")
	@XmlElement(name="configuration")
	private List<ConfigurationsListValue> configurations = null;
	
	public ConfigurationsList() {
		
	}

	public List<ConfigurationsListValue> getConfigurations() {
		return configurations;
	}

	public void setConfigurations(List<ConfigurationsListValue> configurations) {
		this.configurations = configurations;
	}
}