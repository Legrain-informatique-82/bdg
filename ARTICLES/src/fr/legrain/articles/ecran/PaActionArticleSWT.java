package fr.legrain.articles.ecran;

import org.eclipse.jface.fieldassist.DecoratedField;
import org.eclipse.jface.fieldassist.TextControlCreator;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import com.cloudgarden.resource.SWTResourceManager;

import fr.legrain.generationLabelEtiquette.ecrans.CompositeEtiquette;
import fr.legrain.libMessageLGR.LgrMess;
import fr.legrain.publipostage.gui.CompositePublipostage;

/**
 * This code was edited or generated using CloudGarden's Jigloo
 * SWT/Swing GUI Builder, which is free for non-commercial
 * use. If Jigloo is being used commercially (ie, by a corporation,
 * company or business for any purpose whatever) then you
 * should purchase a license for each developer using Jigloo.
 * Please visit www.cloudgarden.com for details.
 * Use of Jigloo implies acceptance of these licensing terms.
 * A COMMERCIAL LICENSE HAS NOT BEEN PURCHASED FOR
 * THIS MACHINE, SO JIGLOO OR THIS CODE CANNOT BE USED
 * LEGALLY FOR ANY CORPORATE OR COMMERCIAL PURPOSE.
 */
public class PaActionArticleSWT extends Composite {

	private Composite paCorpsFormulaire;

	private CompositeEtiquette compositeEtiquette;
	private CompositePublipostage compositePublipostage;
	
	private Group groupEtiquette;
	private Group groupPublipostage;

	final private boolean decore = LgrMess.isDECORE();

	public PaActionArticleSWT(org.eclipse.swt.widgets.Composite parent, int style) {
		super(parent, style);
		initGUI();
	}

	private void initGUI() {
		try {
			GridLayout gd = new GridLayout(2,true);
			this.setLayout(gd);
			{
				GridData compositeEtiquetteLData = new GridData();
				//compositeEtiquetteLData.horizontalSpan = 2;
				compositeEtiquetteLData.horizontalAlignment = SWT.FILL;
				compositeEtiquetteLData.verticalAlignment = SWT.TOP;
				compositeEtiquetteLData.grabExcessVerticalSpace = true;
				groupEtiquette = new Group(this, SWT.NONE);
				groupEtiquette.setText("Etiquettes");
				groupEtiquette.setLayoutData(compositeEtiquetteLData);
				groupEtiquette.setLayout(new GridLayout());
				compositeEtiquette = new CompositeEtiquette(groupEtiquette,SWT.NONE);
				compositeEtiquette.setLayoutData(compositeEtiquetteLData);
				compositeEtiquette.setVisible(true);
			}

			{
				GridData compositePublipostageLData = new GridData();
				//compositePublipostageLData.horizontalSpan = 2;
				compositePublipostageLData.horizontalAlignment = SWT.FILL;
				compositePublipostageLData.verticalAlignment = SWT.TOP;
				compositePublipostageLData.grabExcessHorizontalSpace = true;
				compositePublipostageLData.grabExcessVerticalSpace = true;
				groupPublipostage = new Group(this, SWT.NONE);
				groupPublipostage.setText("Publipostage");
				groupPublipostage.setLayoutData(compositePublipostageLData);
				groupPublipostage.setLayout(new GridLayout());
				compositePublipostage = new CompositePublipostage(groupPublipostage,SWT.NONE);
				compositePublipostage.setLayoutData(compositePublipostageLData);
				compositePublipostage.setVisible(true);
			}

			this.layout();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public CompositeEtiquette getCompositeEtiquette() {
		return compositeEtiquette;
	}

	public CompositePublipostage getCompositePublipostage() {
		return compositePublipostage;
	}

}
