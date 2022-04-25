package fr.legrain.libLgrBirt.chart.test.data.test;
import java.util.Map;

import org.eclipse.birt.chart.computation.DataPointHints;
import org.eclipse.birt.chart.event.StructureSource;
import org.eclipse.birt.chart.event.StructureType;
import org.eclipse.birt.chart.exception.ChartException;
import org.eclipse.birt.chart.factory.Generator;
import org.eclipse.birt.chart.factory.RunTimeContext;
import org.eclipse.birt.chart.model.Chart;
import org.eclipse.birt.chart.model.attribute.ActionType;
import org.eclipse.birt.chart.model.attribute.TooltipValue;
import org.eclipse.birt.chart.model.data.Action;
import org.eclipse.birt.chart.render.ActionRendererAdapter;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Text;

import com.ibm.icu.util.ULocale;
/**
 * Simple implementation for IActionRenderer
 */
public class MyActionRenderer extends ActionRendererAdapter{

	private Chart chart;
	private Map cacheScriptEvaluator;
	private Composite compositeComment;
	private Text textComment;

	public MyActionRenderer(Chart chart) {
		
		this.chart = chart;
		try {
			RunTimeContext context = Generator.instance().prepare(chart, null, null, 
					 ULocale.getDefault());
			context.setActionRenderer(this);

		} catch (ChartException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public MyActionRenderer(){
		
	}
	public MyActionRenderer(Composite compositeComment,Text textComment){
		this.compositeComment = compositeComment;
		this.textComment = textComment;
	}

	public void processAction(Action action, StructureSource source ){
		
		String value = "";
		if ( ActionType.SHOW_TOOLTIP_LITERAL.equals(action.getType())){
			String MyToolTip = "";
			
			
//			if (StructureType.SERIES_DATA_POINT.equals(source.getType())){
//				final DataPointHints dph = (DataPointHints) source.getSource();
//				MyToolTip = "My Value is " + dph.getDisplayValue() + "--" +dph.getBaseDisplayValue();
//				tv.setText( MyToolTip+" test 99999" );
//				System.out.println("qsdqsd");
//				textComment.setText(MyToolTip);
//				compositeComment.layout();
//			}
			if (StructureType.SERIES_DATA_POINT.equals(source.getType())){
				final DataPointHints dph = (DataPointHints) source.getSource();
				TooltipValue tv = (TooltipValue) action.getValue();
				
				String idtentifiant = dph.getSeriesDisplayValue();
				String baseDisplay = dph.getBaseDisplayValue();
				String display = dph.getDisplayValue();
				
				MyToolTip = "My Value is " +display+"--"+baseDisplay+"--"+idtentifiant;
				tv.setText( MyToolTip );
				textComment.setText(MyToolTip);
//				if(idtentifiant.equals("Devis")){
//					if(baseDisplay.equals("Février")){
//						textComment.setText("chiffre d'affaire est 5646546");
//						compositeComment.layout();
//						System.out.println("qdqsd");
//					}
//					
//				}
				
			}
			
		}
		if (ActionType.HIGHLIGHT_LITERAL.equals(action.getType())){
			DataPointHints dph = (DataPointHints) source.getSource();
			
			//if (StructureType.SERIES_DATA_POINT.equals(source.getType())){
//			System.out.println(source.getType());
			
			if (StructureType.SERIES_DATA_POINT.equals(source.getType())){
				String idtentifiant = dph.getSeriesDisplayValue();
				String baseDisplay = dph.getBaseDisplayValue();
				String display = dph.getDisplayValue();
				String orthogonalDisplayValue = dph.getOrthogonalDisplayValue();
				int index = dph.getIndex();
				Double orthogonalDisplay = (Double)dph.getOrthogonalValue();
//				System.out.println(idtentifiant+"--"+baseDisplay + "--" +display+"--"+orthogonalDisplayValue+"--"+
//						index+"--"+orthogonalDisplay);
			}
		}
		
	}
	public Composite getCompositeComment() {
		return compositeComment;
	}
	public void setCompositeComment(Composite compositeComment) {
		this.compositeComment = compositeComment;
	}
	public Text getTextComment() {
		return textComment;
	}
	public void setTextComment(Text textComment) {
		this.textComment = textComment;
	}



}

