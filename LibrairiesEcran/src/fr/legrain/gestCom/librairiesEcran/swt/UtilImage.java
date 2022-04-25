package fr.legrain.gestCom.librairiesEcran.swt;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import org.apache.log4j.Logger;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.graphics.ImageLoader;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.ScrollBar;


public class UtilImage {
	
	static Logger logger = Logger.getLogger(UtilImage.class.getName());
	private int DEFAULT_SCALE = 1;
	
	public static final String[] FILTRE_EXTENSION_IMAGE = new String[]{"*.png;*.jpg;*.jpeg;*.gif","*.*"};
	public static final String[] FILTRE_LIBELLE_IMAGE = new String[]{"Images (*.png; *.jpg; *.jpeg; *.gif)","Tous les fichiers"};
	
	/**
	 * Redimensionnement d'une image et enregistrement dans un nouveau fichier
	 * @param originalFile - fichier image original
	 * @param newFilePath - nouveau fichier image à générer
	 * @param disp
	 * @param imageHeight - hauteur de la nouvelle image
	 * @param imageWidth - largeur de la nouvelle image
	 */
	public void scaleImage(String originalFile, String newFilePath, Display disp, int imageHeight, int imageWidth) {
		scaleImage(originalFile, newFilePath, disp, imageHeight, imageWidth, 0);
	}
	
	/**
	 * Redimensionnement d'une image et enregistrement dans un nouveau fichier
	 * @param originalFile - fichier image original
	 * @param newFilePath - nouveau fichier image à générer
	 * @param disp
	 * @param scale - ratio de la nouvelle image par rapport à l'original
	 */
	public void scaleImage(String originalFile, String newFilePath, Display disp, double scale) {
		scaleImage(originalFile, newFilePath, disp, 0, 0, scale);
	}
	
	/**
	 * Redimensionnement d'une image et enregistrement dans un nouveau fichier
	 * @param originalFile - fichier image original
	 * @param newFilePath - nouveau fichier image à générer
	 * @param disp - periphérique gérant l'affichage
	 * @param imageHeight - hauteur de la nouvelle image, si 0, calculer automatiqument à partir de la largeur
	 * @param imageWidth - largeur de la nouvelle image, si 0, calculer automatiqument à partir de la hauteur
	 * @param scale - ratio de la nouvelle image par rapport à l'original, si 0, ce sont les paramètres précédent (hauteur et largeur) qui sont utilisés.
	 */
	private void scaleImage(String originalFile, String newFilePath, Display disp, int imageHeight, int imageWidth, double scale) {
		if(originalFile!=null) {
			File original = new File(originalFile);
			
			if(original.exists()) {
				Image originalImage = new Image(disp, originalFile);
				int originalType = -1;
				
				if( original.getName().endsWith(".jpg") || original.getName().endsWith(".jpeg") ) {
					originalType = SWT.IMAGE_JPEG;
				} else if( original.getName().endsWith(".png") ) {
					originalType = SWT.IMAGE_PNG;
				} else if( original.getName().endsWith(".gif") ) {
					originalType = SWT.IMAGE_GIF;
				}
				
				if(originalType != -1) {

					if(scale!=0) {
						originalImage = new Image(disp,
								originalImage.getImageData().scaledTo(
										(int)(originalImage.getBounds().width*scale),
										(int)(originalImage.getBounds().height*scale)));
					} else {
						if(imageWidth==0) {
							//imageWidth = (int)(originalImage.getBounds().width) * (int)(originalImage.getBounds().height/imageHeight);
							imageWidth = (int)(originalImage.getBounds().width * imageHeight/originalImage.getBounds().height);
						} else if(imageHeight==0) {
							//imageHeight = (int)(originalImage.getBounds().height) * (int)(originalImage.getBounds().width/imageWidth);
							imageHeight = (int)(originalImage.getBounds().height * imageWidth/originalImage.getBounds().width);
						}
						originalImage = new Image(disp,
								originalImage.getImageData().scaledTo(imageWidth,imageHeight));
					}

					ImageLoader loader = new ImageLoader();
					loader.data = new ImageData[] {originalImage.getImageData()};
					loader.save(newFilePath, originalType);
				} else {
					logger.debug("Type d'image non gere");
				}
				if(originalImage!=null)
					originalImage.dispose();
			} else {
				logger.debug("Fichier image original inexistant");
			}
			
		}
	}
	
	/**
	 * 
	 * @param cheminImage - Chemin du fichier image à afficher
	 * @param imagePreview - canvas ou sera afficher l'image
	 * @param imagePreviewParent - conteneur du canvas
	 * @return
	 */
	public Canvas changementImage(String cheminImage, Canvas imagePreview, Composite imagePreviewParent) {
//		UtilImage util = new UtilImage();
		try {
			Image img = null;
			
			//Supprimer l'ancienne image
			if(imagePreview!=null)
				imagePreview.dispose();
			
			//Récupération des données de la nouvelle image
			if(cheminImage!=null && new File(cheminImage).exists()) {
				img = new Image(imagePreviewParent.getDisplay(), cheminImage);
			} else if(cheminImage!=null) {
				img = imageTexte("Fichier image introuvable.", imagePreviewParent.getDisplay());
			}
			
			if(img!=null) {
				//Il y a une image à afficher
				imagePreview = imageBackground(imagePreviewParent,imagePreviewParent.getDisplay(),
						img, img.getImageData().width, 
						img.getImageData().height,1,false);

				String infosImage = "Dimension : "+img.getImageData().width+"x"+img.getImageData().height;
				imagePreviewParent.setToolTipText(infosImage);
				imagePreview.setToolTipText(infosImage);
			}
			
			imagePreviewParent.layout();
			
			if(img!=null)
				img.dispose();
			
			return imagePreview;
			
		} catch (Exception e) {
			logger.error("",e);
		}
		return imagePreview;
		
	}
	
	public Image imageTexte(String texte, Display disp) {
		return imageTexte(250,70,texte,disp);
	}
	
	public Image imageTexte(int width, int height, String texte, Display disp) {
		Image originalImage = null;
		originalImage = new Image (disp, width, height);
		GC gc = new GC (originalImage);
		gc.fillRectangle (0, 0, width, height);
		gc.drawLine (0, 0, width, height);
		gc.drawLine (0, height, width, 0);
		gc.drawText (texte, 10, 10);
		gc.dispose ();
		return originalImage;
	}
	
	public Canvas imageBackground(Composite comp, Display disp, InputStream imageFile, int hauteurImage, int largeurImage) {
		return imageBackground(comp,disp,imageFile,hauteurImage,largeurImage,DEFAULT_SCALE,true);
	}
	
	public Canvas imageBackground(Composite comp, Display disp, InputStream imageFile, int imageHeight, int imageWidth, int scale, boolean transparent) {
		Image originalImage = null;
		if (imageFile != null) {
			originalImage = new Image (disp, imageFile);
		}
		return imageBackground(comp, disp, originalImage, imageHeight, imageWidth, scale, transparent);
	}
	
	/**
	 * Méthode reprise dans le plugin "LiasseFiscale" dans fr.legrain.liasseFiscale.wizards.UtilImage <br>
	 * Le plugin liasse ayant un fonctionnement ancien (SQL classique) et étant nécessaire pour le plugin "AnalyseEconomique",
	 * il est <b>pour l'instant</b> plus sur de "dupliqué ou de reprendre un grosse partie du code" de ces methodes. <br>
	 * A l'occasion d'un futur nettoyage de l'analyseEconomique/Liasse, il faudra que seules ces methodes (dans LibrairiesEcran) soient utilisées.
	 * <hr>
	 * Création d'un Canvas avec une image en background
	 * @param comp
	 * @param disp
	 * @param imageFile
	 * @param imageHeight
	 * @param imageWidth
	 * @param scale
	 * @return
	 */
	public Canvas imageBackground(Composite comp, Display disp, Image imageFile, int imageHeight, int imageWidth, double scale, boolean transparent) {
		Image originalImage = imageFile;
//		Image originalImage = null;
//		if (imageFile != null) {
//			originalImage = new Image (disp, imageFile);
			
			originalImage = new Image(disp,
					originalImage.getImageData().scaledTo(
							(int)(originalImage.getBounds().width*scale),
							(int)(originalImage.getBounds().height*scale)));
			
//		}
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
		int styleCanvas = SWT.NO_REDRAW_RESIZE | SWT.V_SCROLL | SWT.H_SCROLL;
		if(transparent)
			styleCanvas = styleCanvas | SWT.NO_BACKGROUND;
		final Canvas canvas = new Canvas (comp, styleCanvas);
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
}
