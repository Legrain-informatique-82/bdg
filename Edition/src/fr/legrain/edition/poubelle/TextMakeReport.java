package fr.legrain.edition.poubelle;

import java.util.Iterator;

import org.eclipse.birt.report.model.api.CellHandle;
import org.eclipse.birt.report.model.api.DesignConfig;
import org.eclipse.birt.report.model.api.DesignElementHandle;
import org.eclipse.birt.report.model.api.DesignEngine;
import org.eclipse.birt.report.model.api.ElementFactory;
import org.eclipse.birt.report.model.api.GridHandle;
import org.eclipse.birt.report.model.api.ImageHandle;
import org.eclipse.birt.report.model.api.LabelHandle;
import org.eclipse.birt.report.model.api.ReportDesignHandle;
import org.eclipse.birt.report.model.api.RowHandle;
import org.eclipse.birt.report.model.api.SessionHandle;
import org.eclipse.birt.report.model.api.SlotHandle;
import org.eclipse.birt.report.model.api.elements.DesignChoiceConstants;

public class TextMakeReport {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		TextReport();
	}
		/**
		 * Building a reporting application
		 */
//		EngineConfig engineConfig = new EngineConfig();
//		engineConfig.setEngineHome("C:/TestReport/birt-runtime-2_2_2/ReportEngine");
//		ReportEngine reportEngine =  new ReportEngine( engineConfig);
		/**
		 * About programming with a report design
		 */
		
		public static void TextReport() {
		
			// Create a design engine configuration object.
			DesignConfig dConfig = new DesignConfig( );
			DesignEngine dEngine = new DesignEngine( dConfig );
			// Create a session handle, using the system locale.
			SessionHandle session = dEngine.newSessionHandle( null );
			// Create a handle for an existing report design.
			String name = "c:/tmp/sample.rptdesign";
			ReportDesignHandle design = null;
			try {
				design = session.openDesign( name );
				LabelHandle headerLabel = ( LabelHandle ) design.findElement( "Header Label" );
				String valueText = ((LabelHandle)headerLabel).getText();
				System.out.println(valueText);
				/**
				 * How to add a grid item and label item to a report design
				 */
				ElementFactory factory = design.getElementFactory( );
				try {
					// Create a grid element with 2 columns and 1 row.
					GridHandle grid = factory.newGridItem( "New grid", 2, 1 );
					// Set a simple property on the grid, the width.
					grid.setWidth( "50%" );
					// Create a new label and set its properties.
					
					LabelHandle label = factory.newLabel( "Hello Label" );
					label.setText( "Hello, world!" );
					// Get the first row of the grid.
					RowHandle row = ( RowHandle ) grid.getRows( ).get( 0 );
					// Add the label to the second cell in the row.
					CellHandle cell = ( CellHandle ) row.getCells( ).get( 1 );
					cell.getContent( ).add( label );
					// Get the Body slot. Add the grid to the end of the slot.
					design.getBody( ).add( grid );
				} catch ( Exception e ) {
					// Handle any exception
					}
				
				/**
				 * update property of a item
				 */
//				try {
//					// To prepare to change a style property, get a StyleHandle.
//					StyleHandle labelStyle = headerLabel.getPrivateStyle();
//					// Update the background color.
//					ColorHandle bgColor = labelStyle.getBackgroundColor();
//					bgColor.setRGB( 0xFF8888 );
//				} catch ( Exception e ) {
//				// Handle any exception
//				}
				/**
				 * update content of item in a Report
				 */
//				try {
//					headerLabel.setText( "Updated " + headerLabel.getText( ) );
//				} catch ( Exception e ) {
//				// Handle the exception
//				}
				
				// Instantiate a slot handle and iterator for the body slot.
				SlotHandle shBody = design.getBody();
				Iterator slotIterator = shBody.iterator();
				// To retrieve top-level report items, iterate over the body.
				while (slotIterator.hasNext()) {
					Object shContents = slotIterator.next();
					// To get the contents of the top-level report items,
					// instantiate slot handles.
					if (shContents instanceof GridHandle) {
						GridHandle grid = ( GridHandle ) shContents;
						SlotHandle grRows = grid.getRows( );
						Iterator rowIterator = grRows.iterator( );
						System.out.println("There is a GIRD!");
						while (rowIterator.hasNext()) {
							// Get RowHandle objects.
							Object rowSlotContents = rowIterator.next();
							// To find the image element, iterate over the grid.
							SlotHandle cellSlot = ( ( RowHandle ) rowSlotContents ).getCells( );
							CellHandle cell = (CellHandle) cellSlot.get( 0 );
							Iterator cellIterator = cellSlot.iterator( );
							while ( cellIterator.hasNext( ) ) {
								Object cellSlotContents = cellIterator.next( ); 
								SlotHandle cellContentSlot = ((CellHandle) cellSlotContents).getContent( );
								
								Iterator cellContentIterator = cellContentSlot.iterator( );
								while (cellContentIterator.hasNext( )) {
									// Get a DesignElementHandle object.									
									Object cellContents = cellContentIterator.next( );
									if (cellContents instanceof ImageHandle) {
										String imageSource = ( ( ImageHandle ) cellContents ).getSource( ); 
												// Check that the image has a URI.
										
										if ((imageSource.equals( DesignChoiceConstants.IMAGE_REF_TYPE_URL )) 
										|| (imageSource.equals(DesignChoiceConstants.IMAGE_REF_TYPE_FILE))){
											String imageURI = ( ( ImageHandle )cellContents ).getURI( );
											System.out.println("I am wun--"+imageURI);
										}
									}
								}
							}
						}
					}
				}

				// Access the label by name.
				
				DesignElementHandle logoImage = design.findElement( "Company Logo" );
				// Check for the existence of the report item.
				if ( logoImage == null) { 
					System.out.println("logoImage is null"); 
				}
				// Check that the report item has the expected class.
				if ( !( logoImage instanceof ImageHandle ) ) { 
					System.out.println("logoImage isn't a type of ImageHandle"); 
				}
				// Retrieve the URI of the image.
				String imageURI = ( (ImageHandle ) logoImage ).getURI( );
				System.out.println(imageURI);
//				DesignElementHandle contentText = design.findElement( "ValueText1" );
//				String valueText = ((LabelHandle)contentText).getText();
//				System.out.println(valueText);
				design.saveAs(name);
			} catch (Exception e) {
				System.err.println( "Report " + name + " not opened!\nReason is " + e.toString( ) );
				//return null;
			}
		
	}

}
