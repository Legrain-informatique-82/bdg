package fr.legrain.liasseFiscale.wizards;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;

import org.apache.log4j.Logger;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.ScrollBar;

import com.lowagie.text.pdf.PRAcroForm;
import com.lowagie.text.pdf.PRIndirectReference;
import com.lowagie.text.pdf.PdfArray;
import com.lowagie.text.pdf.PdfDictionary;
import com.lowagie.text.pdf.PdfName;
import com.lowagie.text.pdf.PdfObject;
import com.lowagie.text.pdf.PdfReader;
import com.lowagie.text.pdf.PdfString;

public class UtilImage {
	
	static Logger logger = Logger.getLogger(UtilImage.class.getName());
	static PdfReader reader = null;
	static String pdfFile = null;
	private int DEFAULT_SCALE = 1;
	
	public Canvas imageBackground(Composite comp, Display disp, InputStream imageFile, int hauteurImage, int largeurImage) {
		return imageBackground(comp,disp,imageFile,hauteurImage,largeurImage,DEFAULT_SCALE);
	}
	
	/**
	 * Création d'un Canvas avec une image en background
	 * @param comp
	 * @param disp
	 * @param imageFile
	 * @param imageHeight
	 * @param imageWidth
	 * @param scale
	 * @return
	 */
	public Canvas imageBackground(Composite comp, Display disp, InputStream imageFile, int imageHeight, int imageWidth, int scale) {
		Image originalImage = null;
		if (imageFile != null) {
			originalImage = new Image (disp, imageFile);
			
			originalImage = new Image(disp,
					originalImage.getImageData().scaledTo(
							(int)(originalImage.getBounds().width*scale),
							(int)(originalImage.getBounds().height*scale)));
			
		}
		if (originalImage == null) {
			int width = 150, height = 200;
			originalImage = new Image (disp, width, height);
			GC gc = new GC (originalImage);
			gc.fillRectangle (0, 0, width, height);
			gc.drawLine (0, 0, width, height);
			gc.drawLine (0, height, width, 0);
			gc.drawText ("Default Image", 10, 10);
			gc.dispose ();
		}
		final Image image = originalImage;
		final Point origin = new Point (0, 0);
		final Canvas canvas = new Canvas (comp, SWT.NO_BACKGROUND |
				SWT.NO_REDRAW_RESIZE | SWT.V_SCROLL | SWT.H_SCROLL);
		final ScrollBar hBar = canvas.getHorizontalBar ();
		hBar.addListener (SWT.Selection, new Listener () {
			public void handleEvent (Event e) {
				int hSelection = hBar.getSelection ();
				int destX = -hSelection - origin.x;
				Rectangle rect = image.getBounds ();
				canvas.scroll (destX, 0, 0, 0, rect.width, rect.height, true);
				origin.x = -hSelection;
			}
		});
		final ScrollBar vBar = canvas.getVerticalBar ();
		vBar.addListener (SWT.Selection, new Listener () {
			public void handleEvent (Event e) {
				int vSelection = vBar.getSelection ();
				int destY = -vSelection - origin.y;
				Rectangle rect = image.getBounds ();
				canvas.scroll (0, destY, 0, 0, rect.width, rect.height, true);
				origin.y = -vSelection;
			}
		});
		canvas.addListener (SWT.Resize,  new Listener () {
			public void handleEvent (Event e) {
				Rectangle rect = image.getBounds ();
				Rectangle client = canvas.getClientArea ();
				hBar.setMaximum (rect.width);
				vBar.setMaximum (rect.height);
				hBar.setThumb (Math.min (rect.width, client.width));
				vBar.setThumb (Math.min (rect.height, client.height));
				int hPage = rect.width - client.width;
				int vPage = rect.height - client.height;
				int hSelection = hBar.getSelection ();
				int vSelection = vBar.getSelection ();
				if (hSelection >= hPage) {
					if (hPage <= 0) hSelection = 0;
					origin.x = -hSelection;
				}
				if (vSelection >= vPage) {
					if (vPage <= 0) vSelection = 0;
					origin.y = -vSelection;
				}
				
				canvas.redraw ();
			}
		});
		canvas.addListener (SWT.Paint, new Listener () {
			public void handleEvent (Event e) {
				GC gc = e.gc;
				gc.drawImage (image, origin.x, origin.y);
				Rectangle rect = image.getBounds ();
				Rectangle client = canvas.getClientArea ();
				int marginWidth = client.width - rect.width;
				if (marginWidth > 0) {
					gc.fillRectangle (rect.width, 0, marginWidth, client.height);
				}
				int marginHeight = client.height - rect.height;
				if (marginHeight > 0) {
					gc.fillRectangle (0, rect.height, client.width, marginHeight);
				}
				
				for (int i = 0; i < canvas.getChildren().length; i++) {
					canvas.getChildren()[i].redraw();
				}
			}
		});
		
		return canvas;
	}
	
	/**
	 * Création d'un ScrolledComposite avec une image en background
	 * @param comp - Composite parent
	 * @param disp
	 * @param imageFile - fichier image
	 * @param imageHeight - hauteur de l'image
	 * @param imageWidth - largeur de l'image
	 * @return ScrolledComposite
	 */
	public ScrolledComposite imageBackgroundSC(Composite comp, Display disp, String imageFile, int hauteurImage, int largeurImage) {
		try {
			return imageBackgroundSC(comp,disp,new FileInputStream(imageFile),hauteurImage,largeurImage,DEFAULT_SCALE);
		} catch (FileNotFoundException e) {
			logger.error("",e);
		}
		return null;
	}
	
	/**
	 * Création d'un ScrolledComposite avec une image en background
	 * @param comp - Composite parent
	 * @param disp
	 * @param imageFile - fichier image
	 * @param imageHeight - hauteur de l'image
	 * @param imageWidth - largeur de l'image
	 * @return ScrolledComposite
	 */
	public ScrolledComposite imageBackgroundSC(Composite comp, Display disp, InputStream imageFile, int hauteurImage, int largeurImage) {
		return imageBackgroundSC(comp,disp,imageFile,hauteurImage,largeurImage,DEFAULT_SCALE);
	}
	
	/**
	 * Création d'un ScrolledComposite avec une image en background
	 * @param comp - Composite parent
	 * @param disp
	 * @param imageFile - fichier image
	 * @param imageHeight - hauteur de l'image
	 * @param imageWidth - largeur de l'image
	 * @param scale - "zoom"
	 * @return ScrolledComposite
	 */
	public ScrolledComposite imageBackgroundSC(Composite comp, Display disp, InputStream imageFile, int imageHeight, int imageWidth, int scale) {
		
		Image originalImage = null;
		if (imageFile != null) {
			originalImage = new Image (disp, imageFile);
//			originalImage = new Image(disp,
//					originalImage.getImageData().scaledTo(
//							(int)(originalImage.getBounds().width*scale),
//							(int)(originalImage.getBounds().height*scale)));
			
		}
		final Image image = originalImage;
		final Point origin = new Point (0, 0);
		final ScrolledComposite sc = new ScrolledComposite(comp, 
				SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL 
				| SWT.NO_BACKGROUND 
				| SWT.NO_REDRAW_RESIZE
				);
		final Canvas canvas = new Canvas (sc,
				SWT.NO_BACKGROUND
				| SWT.NO_REDRAW_RESIZE
				);
		//canvas.setBackgroundImage(image);
		//canvas.setBackgroundMode(SWT.INHERIT_DEFAULT);
		sc.setContent(canvas);
		sc.setMinSize(imageWidth, imageHeight);
		
		// Expand both horizontally and vertically
		sc.setExpandHorizontal(true);
		sc.setExpandVertical(true);
		
		canvas.addListener (SWT.Paint, new Listener () {
			public void handleEvent (Event e) {
				GC gc = e.gc;
				gc.drawImage (image, origin.x, origin.y);
				Rectangle rect = image.getBounds ();
				Rectangle client = canvas.getClientArea();
				int marginWidth = client.width - rect.width;
				if (marginWidth > 0) {
					gc.fillRectangle (rect.width, 0, marginWidth, client.height);
				}
				int marginHeight = client.height - rect.height;
				if (marginHeight > 0) {
					gc.fillRectangle (0, rect.height, client.width, marginHeight);
				}
				
			}
		});
		
		return sc;
	}
	
	/**
	 * Liste le champs d'une page de document PDF avec les informations concernant leur emplacement
	 * dans la page.
	 * @param file - fichier PDF
	 * @param page - page dans le fichier PDF
	 * @return HashMap<String,ArrayList>
	 * <li> cle : nom du champ</li>
	 * <li> valeurs : coordonnées d'un rectangle PDF <br>
	 * Dans l'ordre dans l'arrayList: llx (lower left x), lly (lower left y), urx (upper right x), ury (upper right y)</li>
	 */
	public HashMap<String,ArrayList> listeChamps(String file, String nomFichier, int page) {
		try {
			listeChamps(new FileInputStream(file),nomFichier,page);
		} catch (FileNotFoundException e) {
			logger.error("",e);
		}
		return null;
	}
	
	/**
	 * Liste le champs d'une page de document PDF avec les informations concernant leur emplacement
	 * dans la page.
	 * @param file - fichier PDF
	 * @param nomFichier - nom du fichier PDF (pour detecter les changements)
	 * @param page - page dans le fichier PDF
	 * @return HashMap<String,ArrayList>
	 * <li> cle : nom du champ</li>
	 * <li> valeurs : coordonnées d'un rectangle PDF <br>
	 * Dans l'ordre dans l'arrayList: llx (lower left x), lly (lower left y), urx (upper right x), ury (upper right y)</li>
	 */
	public HashMap<String,ArrayList> listeChamps(InputStream file, String nomFichier, int page) {
		HashMap<String,ArrayList> result = new HashMap<String,ArrayList>();
		try {
//			if(reader==null)
//				reader = new PdfReader(file);
			
			if(pdfFile==null || !pdfFile.equals(nomFichier)) {
				reader = new PdfReader(file);
				pdfFile = nomFichier;
			}
			//     logger.debug("reader.getPageSize(page) : "+reader.getPageSize(page).height() +" "+reader.getPageSize(page).width());
//			PRAcroForm form = reader.getAcroForm();
//			if (form == null) {
//			logger.debug("This document has no fields.");
//			return null;
//			}
			
//			PdfLister list = new PdfLister(System.err);
//			HashMap refToField = new HashMap();
//			ArrayList fields = form.getFields();
//			for (int k = 0; k < fields.size(); ++k) {
//			PRAcroForm.FieldInformation field = (PRAcroForm.FieldInformation)fields.get(k);
//			refToField.put(new Integer(field.getRef().getNumber()), field);
//			}
			// for (int page = 1; page <= reader.getNumberOfPages(); ++page) {
			PdfDictionary dPage = reader.getPageN(page);
			PdfArray annots = (PdfArray)PdfReader.getPdfObject(dPage.get(PdfName.ANNOTS));
			if (annots == null)
				return null;//continue;
			ArrayList ali = annots.getArrayList();
			for (int annot = 0; annot < ali.size(); ++annot) {
				PdfObject refObj = (PdfObject)ali.get(annot);
				PRIndirectReference ref = null;
				PdfDictionary an = (PdfDictionary)PdfReader.getPdfObject(refObj);
				PdfName name = (PdfName)an.get(PdfName.SUBTYPE);
				if (name == null || !name.equals(PdfName.WIDGET))
					continue;
				
//				PdfName type = (PdfName)an.get(PdfName.FT);
//				if (type == null || !type.equals(PdfName.TEXT))
//				continue;
				
				PdfArray rect = (PdfArray)an.get(PdfName.RECT);
				String fName = "";
				PRAcroForm.FieldInformation field = null;
				while (an != null) {
					PdfString tName = (PdfString)an.get(PdfName.T);
					if (tName != null)
						fName = tName.toString() + "." + fName;
//					if (refObj.type() == PdfObject.INDIRECT && field == null) {
//					ref = (PRIndirectReference)refObj;
//					field = (PRAcroForm.FieldInformation)refToField.get(new Integer(ref.getNumber()));
//					}
					refObj = an.get(PdfName.PARENT);
					an = (PdfDictionary)PdfReader.getPdfObject(refObj);
				}
				if (fName.endsWith("."))
					fName = fName.substring(0, fName.length() - 1);
				//stream.println("page " + page + ", name - " + fName);
				//for (Object o : rect.getArrayList()) {
				//	System.out.println(o);
				result.put(fName,rect.getArrayList());
				//} 
				
				//list.listAnyObject(rect);
//				if (field != null) {
//				System.out.println("Merged attributes of " + field.getName());
//				list.listAnyObject(field.getInfo());
//				System.out.println("Dictionary of " + field.getName());
//				list.listAnyObject(PdfReader.getPdfObject(field.getRef()));
//				}
			}
			//}
			//}
			return result;
		}
		catch(IOException ioe) {
			logger.error(ioe.getMessage());
		}
		return result;
	}
	
	//public void listeChampsPDFBox() {}
	
}
