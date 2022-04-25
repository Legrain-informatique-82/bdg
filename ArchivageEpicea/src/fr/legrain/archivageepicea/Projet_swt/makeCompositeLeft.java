package fr.legrain.archivageepicea.Projet_swt;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.nebula.widgets.cdatetime.CDT;
import org.eclipse.swt.nebula.widgets.cdatetime.CDateTime;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

public class makeCompositeLeft {

//	public void makeLeft_libelle(Composite objetComposite,String nameLibelle1)
//	{
//		GridData gridData=new GridData();
//		gridData.horizontalSpan = 1;    //
//		gridData.verticalSpan=1;       
//		gridData.horizontalAlignment = GridData.HORIZONTAL_ALIGN_FILL; 
//		gridData.verticalAlignment = GridData.FILL; 
//        Label libelle_1=new Label(objetComposite,SWT.WRAP);
//        libelle_1.setText(nameLibelle1);
//        libelle_1.setLayoutData(gridData);
//	}
//	
//	public Text makeLeft_text(Composite objetComposite,int nombreCol)
//	{
//		GridData gridData3=new GridData(GridData.FILL_HORIZONTAL);
//        gridData3.horizontalSpan = nombreCol;    
//        gridData3.verticalSpan=1;       
//        gridData3.horizontalAlignment = GridData.FILL;
//        //gridData3.verticalAlignment = GridData.FILL; 
//        gridData3.grabExcessHorizontalSpace = true;
//        Text text1 =new Text(objetComposite,SWT.NONE|SWT.BORDER);
//        text1.setLayoutData(gridData3);
//        
//        return text1;
//	}
//	public Button makeLeft_button(Composite objetComposite)
//	{
//		GridData gridData3=new GridData();
//        gridData3.horizontalSpan = nombreCol;    
//        gridData3.verticalSpan=1;        
//        
//        Button bt1=new Button(objetComposite,SWT.NULL);
//		bt1.setText("Rechercher");
//		bt1.setLayoutData(gridData3);
//		return bt1;
//	}
//	public CDateTime makeLeft_date(Composite objetComposite,int nombreCol)
//	{
//		
//		GridData gridData3=new GridData();
//		gridData3.horizontalSpan = nombreCol;    
//		gridData3.verticalSpan=1;        
//		//gridData3.horizontalAlignment = GridData.CENTER; 
//		gridData3.horizontalAlignment = GridData.FILL;
//		gridData3.verticalAlignment = GridData.FILL; 
//		//gridData3.grabExcessHorizontalSpace = GridData.FILL
//		//gridData3.grabExcessHorizontalSpace = true;        
//		
//		CDateTime date=new CDateTime(objetComposite,CDT.BORDER|CDT.DROP_DOWN);
//		date.setSelection(null);
//		date.setLayoutData(gridData3);
//		return date;
//	}
//	/*
//	 * pour seulement utiliser dans ongletPieces de compositeDownGroup
//	 */
//	public void makeLable (Composite objetComposite,int nombreCol){
//		GridData gridData=new GridData();
//		gridData.horizontalSpan = 1;    
//		gridData.verticalSpan=1;        
//		gridData.horizontalAlignment = GridData.END; 
//		gridData.verticalAlignment = GridData.FILL; 
//        Label libelle_1=new Label(objetComposite,SWT.WRAP);
//        //libelle_1.setBackground(new Color(PlatformUI.getWorkbench().getDisplay());
//        libelle_1.setText(nameLibelle1);
//        libelle_1.setLayoutData(gridData);
//		
//	}
//	
//	public GridData objetGridData;
//	public Composite objetComposite;
//	public String nameLibelle1;
//	public String nameLibelle;
//	public Label objetTitel;
//	public Text objettext;
//	public int nombreCol;
}
