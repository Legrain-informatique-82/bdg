package fr.legrain.libLgrBirt.chart.test;

import org.eclipse.birt.chart.model.Chart;
import org.eclipse.birt.chart.model.ChartWithAxes;
import org.eclipse.birt.chart.model.attribute.ChartDimension;
import org.eclipse.birt.chart.model.impl.ChartWithAxesImpl;

public class TestCreateChart {

	public static final Chart createBar( ){
		ChartWithAxes cwaBar = ChartWithAxesImpl.create( );
		
		cwaBar.setDimension( ChartDimension.TWO_DIMENSIONAL_LITERAL );
		cwaBar.setType( "Bar Chart" ); //$NON-NLS-1$
		cwaBar.setSubType( "Side-by-side" ); //$NON-NLS-1$
		
		return cwaBar;
	}
	
}
