package fr.legrain.changelog.ws.client;

import java.util.Collections;
import java.util.Comparator;
import java.util.Date;

import org.eclipse.core.databinding.beans.BeanProperties;
import org.eclipse.core.databinding.observable.list.WritableList;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.window.IShellProvider;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.ui.forms.FormDialog;
import org.eclipse.ui.forms.IManagedForm;
import org.eclipse.ui.forms.widgets.FormText;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.ScrolledForm;
import org.eclipse.ui.forms.widgets.Section;

import fr.legrain.changelog.ws.Article;
import fr.legrain.changelog.ws.Category;
import fr.legrain.lib.data.LibDate;
import fr.legrain.lib.gui.grille.LgrTableViewer;

public class ChangeLogDialog extends FormDialog{
//public class ChangeLogDialog extends Dialog{

	private Category cat = null;
	private ScrolledForm form = null;
	private FormToolkit toolkit = null;
	private boolean parseTags = false;
	
	private Table table;
	
	public ChangeLogDialog(Shell parentShellProvider, Category cat) {
		super(parentShellProvider);
		this.cat = cat;
	}
	
	protected void configureShell(Shell shell) {
	   super.configureShell(shell);
	   shell.setText("Changelog");
	}
	
	@Override
	protected Control createDialogArea(Composite parent) {
		// TODO Auto-generated method stub
		return super.createDialogArea(parent);
	}

	@Override
	protected Control createButtonBar(Composite parent) {
		// TODO Auto-generated method stub
		return super.createButtonBar(parent);
	}

//	@Override
//	protected Control createContents(Composite parent) {
//		// TODO Auto-generated method stub
//
//		
//		toolkit = new FormToolkit(parent.getDisplay());
//		form = toolkit.createScrolledForm(parent);
//		form.setText("Changelog");
////		toolkit.decorateFormHeading(form);
//		GridData gd = new GridData(SWT.FILL, SWT.FILL, true, true);
//		form.setLayoutData(gd);
//		
//		// Titre des colonnes
//		String[] titreColonnes = { "Date", "Nom", "Description", "Version", "Type"};
//	
//		// Taille des colonnes
//		String[] tailleColonnes = { "100", "200", "400", "50", "100" };
//	
//		// Nom des champs dans la classe
//		String[] nomChampColonnes = { "date", "name", "content","version", "type"};
//
//		// Alignement des informations dans les cellules du tableau
//		int[] alignement = { SWT.NONE, SWT.NONE, SWT.NONE, SWT.NONE, SWT.NONE };
//
//		table = toolkit.createTable(form.getBody(), SWT.SINGLE | SWT.BORDER | SWT.FULL_SELECTION);
//		GridData gridDataTab = new GridData(SWT.FILL, SWT.FILL, true, true, 2, 1);
//		gridDataTab.minimumHeight = 150;
//		gridDataTab.heightHint = 150;
//		table.setLayoutData(gridDataTab);
//		
//		// Création de l'élément graphique du tableau et affichage à l'écran
//		LgrTableViewer changeLogViewer = new LgrTableViewer(table);
//		changeLogViewer.createTableCol(table, titreColonnes, tailleColonnes, 1, alignement);
//		changeLogViewer.setListeChamp(nomChampColonnes);
//
//		ChangelogLabelProvider.bind(changeLogViewer, new WritableList(
//				cat.getArticles().getItem(), Article.class), BeanProperties
//				.values(nomChampColonnes));
//				
//		form.layout();
//		form.reflow(true);
//
//		return super.createContents(parent);
//	}

	@Override
	protected void createFormContent(IManagedForm mform) {
		super.createFormContent(mform);
		
		toolkit = mform.getToolkit();
		form = mform.getForm();
		form.setText("Liste des changements");
//		form.setText(null);
		
		//Composite body = form.getBody();
		toolkit.decorateFormHeading(form.getForm());
		toolkit.paintBordersFor(form.getBody());
		GridLayout gl = new GridLayout(5, false);
		form.getBody().setLayout(gl);
		
//		FormText laDate = toolkit.createFormText(body, false);
//		laDate.setText(toFormText("<b>Date</b>"), parseTags, false);
//		
//		FormText laNom = toolkit.createFormText(body, false);
//		laNom.setText(toFormText("Nom"), parseTags, false);
//		
//		FormText laDesc = toolkit.createFormText(body, false);
//		laDesc.setText(toFormText("Description"), parseTags, false);
//		
//		FormText laVer = toolkit.createFormText(body, false);
//		laVer.setText(toFormText("Version"), parseTags, false);
//		
//		FormText laType = toolkit.createFormText(body, false);
//		laType.setText(toFormText("Type"), parseTags, false);
//		
////		toolkit.createLabel(body, cat.getName(), SWT.NONE);
//		for (Article a : cat.getArticles().getItem()) {
//			FormText date = toolkit.createFormText(body, false);
////			date.setText("", false, false);
//			date.setText(toFormText(LibDate.dateToString(new Date(a.getDate()*1000l))), parseTags, false);
//			
//			FormText nom = toolkit.createFormText(body, false);
//			nom.setText(toFormText(a.getName()), parseTags, false);
//			
//			FormText desc = toolkit.createFormText(body, false);
//			desc.setText(toFormText(a.getContent()), parseTags, false);
//			
//			FormText ver = toolkit.createFormText(body, false);
//			ver.setText(toFormText(a.getVersion()), parseTags, false);
//			
//			FormText type = toolkit.createFormText(body, false);
//			type.setText(toFormText(a.getType()), parseTags, false);
//
//		}
		
		// Titre des colonnes
		String[] titreColonnes = { "Date", "Nom", "Description", "Version", "Type"};
	
		// Taille des colonnes
		String[] tailleColonnes = { "100", "200", "400", "52", "100" };
	
		// Nom des champs dans la classe
		String[] nomChampColonnes = { "date", "name", "content","version", "type"};

		// Alignement des informations dans les cellules du tableau
		int[] alignement = { SWT.NONE, SWT.NONE, SWT.NONE, SWT.NONE, SWT.NONE };

		table = toolkit.createTable(form.getBody(), SWT.SINGLE | SWT.BORDER | SWT.FULL_SELECTION);
		GridData gridDataTab = new GridData(SWT.FILL, SWT.FILL, true, true, 2, 1);
		gridDataTab.minimumHeight = 150;
		gridDataTab.heightHint = 150;
		table.setLayoutData(gridDataTab);
		
		// Création de l'élément graphique du tableau et affichage à l'écran
		LgrTableViewer changeLogViewer = new LgrTableViewer(table);
		changeLogViewer.createTableCol(table, titreColonnes, tailleColonnes, 1, alignement);
		changeLogViewer.setListeChamp(nomChampColonnes);
		
		//Tri par Date, du plus récent au plus ancien
		Collections.sort(cat.getArticles().getItem(), new Comparator<Article>() {
			  public int compare(Article o1, Article o2) {
			      if (new Date(o1.getDate()*1000l) == null || new Date(o2.getDate()*1000l) == null)
			        return 0;
			      return -(new Date(o1.getDate()*1000l).compareTo(new Date(o2.getDate()*1000l)));
			  }
			});

		ChangelogLabelProvider.bind(changeLogViewer, new WritableList(
				cat.getArticles().getItem(), Article.class), BeanProperties
				.values(nomChampColonnes));
		
		form.layout();
		form.reflow(true);

		
//		for (Article a : cat.getArticles().getItem()) {
//			Label t = new Label(this.dialogArea, SWT.NONE);
//			message += "*--- nom : " + a.getName()+"\n";
//			message += "*--- contenu : " + a.getContent()+"\n";
//		}
//		for (SubCategory sc : cat.getCategories().getItem()) {
//			message += "********** Sous Catégorie : " + sc.getName()+"\n";
//			for (Article a : sc.getArticles().getItem()) {
//				message += "*--- nom : " + a.getName()+"\n";
//				message += "*--- contenu : " + a.getContent()+"\n";
//			}
//		}
		
	}
	
	public String toFormText(String s) {
		if(parseTags) {
			//s = s.replaceAll("&", "&amp;");
			return "<form>"+s+"</form>";
		} else {
			return s;
		}
	}

	public ChangeLogDialog(IShellProvider parentShellProvider) {
		super(parentShellProvider);
		
	}

}
