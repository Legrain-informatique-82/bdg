package fr.legrain.edition.actions;

import org.apache.log4j.Logger;
import org.eclipse.birt.report.model.api.DesignConfig;
import org.eclipse.birt.report.model.api.ElementFactory;
import org.eclipse.birt.report.model.api.IDesignEngine;
import org.eclipse.birt.report.model.api.IDesignEngineFactory;
import org.eclipse.birt.report.model.api.ReportDesignHandle;
import org.eclipse.birt.report.model.api.SessionHandle;
import org.eclipse.birt.report.model.api.StructureFactory;

public class MakeReportDynamic {

	static Logger logger = Logger.getLogger(MakeDynamiqueReport.class.getName());
	
	private ReportDesignHandle designHandle = null;
	private ElementFactory designFactory = null;//for understand look P242
	private StructureFactory structFactory = null;
	//Configure the Engine and start the Platform
	private DesignConfig config = new DesignConfig( );
	private IDesignEngine engine = null;
	private SessionHandle session = null;
	private IDesignEngineFactory factory = null;
}
