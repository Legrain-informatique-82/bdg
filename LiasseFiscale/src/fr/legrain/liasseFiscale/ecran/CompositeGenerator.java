package fr.legrain.liasseFiscale.ecran;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

public class CompositeGenerator {
	private DescriptionDocumentXML descEcran = null;
	
	public void a() {
//		Shell shell = new Shell(window.getShell());
//		test.testGenEcran(shell,SWT.NONE);
//		shell.setSize(800,600);
//		shell.setLayout(new FillLayout());
//		shell.layout();
//		shell.setVisible(true);
	}
	
	public Composite[] genPages(Composite parent) {
		Composite[] res = new Composite[descEcran.getPages().size()+1];
		int i = 0;
		Composite comp = null;
		
		for (Page p : descEcran.getPages()) {
			comp = new Composite(parent,SWT.NULL);
			
			creationPage(p, comp);
			
			comp.setLayout(new GridLayout(2,true));
			comp.layout();
			i++;
		}
		res[i] = comp;
		
		return res;
	}
	
	public Composite genPage(Composite parent, int page) {
		Composite comp = null;
		
		Page p = descEcran.getPages().get(page);
		comp = new Composite(parent,SWT.NULL);
		
		creationPage(p, comp);
		
		comp.setLayout(new GridLayout(2,true));
		comp.layout();

		return comp;
	}
	
	private void creationPage(Page page, Composite parent) {
		for (Champ c : page.getChamps()) {
			Label l = new Label(parent,SWT.NONE);
			l.setText(c.getLabel());
			GridData lData = new GridData(GridData.FILL,GridData.FILL,true,false);
			l.setLayoutData(lData);
			
			Text t = new Text(parent,SWT.BORDER);
			//t.setText(c.getValeur());
			GridData tData = new GridData(GridData.FILL,GridData.FILL,true,false);
			t.setLayoutData(tData);
		}

		parent.setLayout(new GridLayout(2,true));
		parent.layout();
	}

	public DescriptionDocumentXML getDescEcran() {
		return descEcran;
	}

	public void setDescEcran(DescriptionDocumentXML descEcran) {
		this.descEcran = descEcran;
	}
	
}
