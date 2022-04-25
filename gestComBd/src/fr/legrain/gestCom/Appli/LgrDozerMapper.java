package fr.legrain.gestCom.Appli;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javassist.ClassMap;

import net.sf.dozer.util.mapping.CustomFieldMapperIF;
import net.sf.dozer.util.mapping.DozerBeanMapper;
import net.sf.dozer.util.mapping.MappingException;
import net.sf.dozer.util.mapping.fieldmap.FieldMap;

import org.apache.log4j.Logger;
import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.Path;

import fr.legrain.gestCom.gestComBd.gestComBdPlugin;

public class LgrDozerMapper<Source,Destination extends Object> {
	
	static Logger logger = Logger.getLogger(LgrDozerMapper.class.getName());
	
	private static DozerBeanMapper mapper = null;

	private String mappingFile = "/dozerBeanMapping.xml";
	private List<String> myMappingFiles = new ArrayList<String>();
	
	public LgrDozerMapper() {
		if(mapper == null) {
			mapper = new DozerBeanMapper();
			mapper.setMappingFiles(getMappingFiles());
		}
	}
	
	public List<String> getMappingFiles() {
		myMappingFiles.clear();
		URL mappingURL = FileLocator.find(gestComBdPlugin.getDefault().getBundle(),new Path(mappingFile),null);
		//myMappingFiles.add("file:/donnees/Projet/Java/Eclipse/GestionCommerciale/gestComBd/dozerBeanMapping.xml");
		try {
			logger.debug("DozerMappingFile : "+FileLocator.toFileURL(mappingURL).toString());
			myMappingFiles.add(FileLocator.toFileURL(mappingURL).toString());
		} catch (IOException e) {
			logger.error("",e);
		}
		return myMappingFiles;
	}
	
	public DozerBeanMapper getMapper() {
		if(mapper == null) {
			mapper = new DozerBeanMapper();
			mapper.setMappingFiles(myMappingFiles);
		}
		return mapper;
	}
	
	public Destination map(Source o, Class dest) {
		try {
			return (Destination) getMapper().map(o, dest);
		} catch (Exception e) {
			logger.error("",e);
		}
		return null;
	}
	
	public void map(Source o, Destination d) {
		getMapper().map(o, d);
	}

	public List<String> getMyMappingFiles() {
		return myMappingFiles;
	}

	public String getMappingFile() {
		return mappingFile;
	}







}
