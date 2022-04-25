package fr.legrain.libLgrBirt.test;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;

import org.eclipse.birt.report.engine.api.EngineConfig;
import org.eclipse.birt.report.engine.api.EngineException;
import org.eclipse.birt.report.engine.api.HTMLCompleteImageHandler;
import org.eclipse.birt.report.engine.api.HTMLEmitterConfig;
import org.eclipse.birt.report.engine.api.HTMLRenderOption;
import org.eclipse.birt.report.engine.api.IGetParameterDefinitionTask;
import org.eclipse.birt.report.engine.api.IParameterDefnBase;
import org.eclipse.birt.report.engine.api.IParameterGroupDefn;
import org.eclipse.birt.report.engine.api.IReportRunnable;
import org.eclipse.birt.report.engine.api.IRunAndRenderTask;
import org.eclipse.birt.report.engine.api.IScalarParameterDefn;
import org.eclipse.birt.report.engine.api.ReportEngine;

/**
 * Runs a BIRT report and writes the output to a directory.
 * RunReport report-name -[h|p] [-i] param=value
 * -h HTML (default)
 * -p PDF
 * -i Display report info.
 * param=value Set parameter "param" to the value "value".
 */

public class RunReport
{
	// Command-line main to parse arguments described above.
	public static void main(String[] args)
	{
		int i = 0;
		if ( i >= args.length )
		{
			System.out.println( "No report specified." );
			return;
		}
		String reportName = args[i++];
		String format = null;
		boolean showInfo = false;

		// Stoare parameter values into a hash map to later pass to the
		// BIRT report engine.

		HashMap params = new HashMap( );
		for ( ;  i < args.length;  i++ )
		{
			if ( args[i].charAt(0) == '-' )
			{
				char flag = Character.toLowerCase( args[i].charAt( 1 ) );
				if ( flag == 'p' )
				{
					format = HTMLRenderOption.OUTPUT_FORMAT_PDF;
				}
				else if ( flag == 'h' )
				{
					format = HTMLRenderOption.OUTPUT_FORMAT_HTML;
				}
				else if ( flag == 'i' )
				{
					showInfo = true;
				}
				else
				{
					usage( );
					return;
				}
			}
			else
			{
				// Get a parameter value. Note, this example code only works
				// for string parameters. If you have date or number parameters,
				// your application should parse them, then put the corresponding
				// objects into the hash map.

				String arg = args[i];
				int posn = arg.indexOf( '=' );
				if ( posn == -1 )
				{
					usage( );
					return;
				}
				String param = arg.substring( 0, posn );
				String value = arg.substring( posn + 1 );
				params.put( param, value );
			}
		}
		run( reportName, params, format, showInfo );
	}

	public static void usage( )
	{
		System.out.println( "java RunReport rptName [-h|p]\n" );
		System.out.println( "               param=value ..." );
	}

	// Example function to run a report given the report name, parameters and
	// output type. The output is put into a file in the same directory as the
	// report itself.

	public static void run( String name, HashMap params,
							String format, boolean showInfo )
	{
		// The EngineConfig is used to set up engine-wide configuration
		// information.

		EngineConfig config = new EngineConfig( );

		// The Engine home is the location of the BIRT engine runtime.
		// M3 Note: Use the report engine installed as part of the BIRT web app
		// used to preview reports. Alter this path to the location of your
		// Eclipse installation.

		config.setEngineHome( "c:\\eclipse3.1\\eclipse\\plugins\\org.eclipse.birt.report.viewer_1.0.1\\birt" );

		// HTML reports can have embedded images or charts. Set up the configuration
		// required to write these into a directory in the output directory.
		// The HTMLCompleteImageHandler is one provided by BIRT for this purpose.
		// Your application can create a customized version to provide alternative
		// ways to handle images.

		HTMLEmitterConfig hc = new HTMLEmitterConfig( );
		HTMLCompleteImageHandler imageHandler = new HTMLCompleteImageHandler( );
		hc.setImageHandler( imageHandler );
		config.setEmitterConfiguration( HTMLRenderOption.OUTPUT_FORMAT_HTML, hc );

		// Create the report engine itself. This engine can be used to run
		// multiple reports.

		ReportEngine engine = new ReportEngine( config );

		// Open the report design.

		IReportRunnable report = null;
		try
		{
			report = engine.openReportDesign( name );
		}
		catch ( EngineException e )
		{
			System.err.println( "Report " + name + " not found!\n" );
			engine.destroy( );
			return;
		}

		if ( showInfo )
		{
			showReportInfo( engine, report );
		}

		// Create a task to run the report and convert the output to HTML
		// or PDF.

		if ( format != null )
		{

			parseParams( engine, report, params );

			IRunAndRenderTask task = engine.createRunAndRenderTask( report );
			HTMLRenderOption options = new HTMLRenderOption( );
			options.setOutputFormat( format );
			String output = name.replaceFirst( ".rptdesign", "." + format );
			options.setOutputFileName( output );
			task.setRenderOption( options );
			task.setParameterValues( params );

			// Run the report.

			try
			{
				task.run( );
			}
			catch ( EngineException e1 )
			{
				System.err.println( "Report " + name + " run failed.\n" );
				System.err.println( e1.toString( ) );
			}
		}

		engine.destroy( );
	}

	// Display properties of the report and the list of report parameters.

	static void showReportInfo( ReportEngine engine, IReportRunnable report )
	{
		// Display some of the general report properties.
		// See the ROM Design specification for a list of properties.

		System.out.print( "Report Name: " + report.getReportName() +
						  "\nTitle: " + report.getProperty( "title" ) +
						  "\nComments: " + report.getProperty( "comments" ) +
						  "\nDescription: " + report.getProperty( "description" ) +
						  "\nAuthor: " + report.getProperty( "author" ) +
						  "\nCreated By: " + report.getProperty( "createdBy" ) +
						  "\n" );

		// Display the list of report parameters with their groups.
		// Parameters defined outside a group appear inside a default, unnamed
		// group.

		IGetParameterDefinitionTask task = engine.createGetParameterDefinitionTask( report );
		Collection params = task.getParameterDefns( true );
		if ( params.isEmpty() )
		{
			System.out.print( "This report has no parameters.\n" );
			return;
		}

		Iterator iter = params.iterator( );
		while ( iter.hasNext( ) )
		{
			IParameterDefnBase param = (IParameterDefnBase) iter.next( );
			if ( param instanceof IParameterGroupDefn )
			{
				IParameterGroupDefn group = (IParameterGroupDefn) param;
				System.out.println( "Parameter Group: " + group.getDisplayName( ) );
				Iterator i2 = group.getContents( ).iterator( );
				while ( iter.hasNext( ) )
				{
					IScalarParameterDefn member = (IScalarParameterDefn) i2.next( );
					showParam( task, member, "  " );
				}
			}
			else
				showParam( task, param, "" );
		}
	}

	// Show information for one parameter.
	// Release 1 supports only scalar (single-value) parameters.

	static void showParam( IGetParameterDefinitionTask task,
						   IParameterDefnBase defn,
						   String prefix )
	{
		IScalarParameterDefn param = (IScalarParameterDefn) defn;
		System.out.println( prefix + "Parameter: " + param.getName( ) );
		System.out.println( prefix + "  Display Name: " + param.getDisplayName( ) );
		System.out.println( prefix + "  Type: " + param.getDataType( ) );
		System.out.print( prefix + "  Default Value: " );

		// The default value is an expression. Evaluate it.

		Object value = task.getDefaultValue( param );
		System.out.println( value == null ? "none" : value.toString( ) );
	}

	static void parseParams( ReportEngine engine, IReportRunnable report, HashMap values )
	{
		if ( values.isEmpty( ) )
			return;

		IGetParameterDefinitionTask task = engine.createGetParameterDefinitionTask( report );
		Collection params = task.getParameterDefns( false );
		Iterator iter = values.keySet( ).iterator( );
		while ( iter.hasNext( ) )
		{
			String name = (String) iter.next( );

			IScalarParameterDefn found = null;
			Iterator i2 = params.iterator( );
			while ( i2.hasNext( ) )
			{
				IParameterDefnBase param = (IParameterDefnBase) i2.next( );
				if ( param instanceof IParameterGroupDefn )
					continue;
				if ( param.getName( ).equals( name ) )
				{
					found = (IScalarParameterDefn) param;
					break;
				}
			}
			if ( found == null )
			{
				System.err.println( "Parameter " + name + " not found in the report." );
				continue;
			}
			String value = (String) values.get( name );
//			ReportParameterConverter cfgConverter = new ReportParameterConverter( "", Locale.getDefault( ) );
//			Object obj = cfgConverter.parse( value, found.getDataType( ) );
//			values.put( name, obj );
			values.put( name, value );
		}
	}
}