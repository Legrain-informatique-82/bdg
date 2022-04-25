package fr.legrain.gestCom.librairiesEcran.notifier;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.graphics.Region;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;

import fr.legrain.gestCom.librairiesEcran.notifier.cache.ColorCache;
import fr.legrain.gestCom.librairiesEcran.notifier.cache.FontCache;
import fr.legrain.lib.data.LibConversion;


/**
 * http://hexapixel.com/2009/06/30/creating-a-notification-popup-widget
 */
public class NotifierComposite extends Composite {

	// how long the the tray popup is displayed after fading in (in milliseconds)
    //private static final int   DISPLAY_TIME  = 4500;
	private static final int   DISPLAY_TIME  = 2000;
    // how long each tick is when fading in (in ms)
    private static final int   FADE_TIMER    = 50;
    // how long each tick is when fading out (in ms)
    private static final int   FADE_IN_STEP  = 30;
    // how many tick steps we use when fading out 
    private static final int   FADE_OUT_STEP = 8;

    // how high the alpha value is when we have finished fading in 
    private static final int   FINAL_ALPHA   = 225;

    // title foreground color
    private static Color       _titleFgColor = ColorCache.getColor(40, 73, 97);
    // text foreground color
    private static Color       _fgColor      = _titleFgColor;

    // shell gradient background color - top
    private static Color       _bgFgGradient = ColorCache.getColor(226, 239, 249);
    // shell gradient background color - bottom    
    private static Color       _bgBgGradient = ColorCache.getColor(177, 211, 243);
    // shell border color
    private static Color       _borderColor  = ColorCache.getColor(40, 73, 97);

    // contains list of all active popup shells
    private static List<Shell> _activeShells = new ArrayList<Shell>();

    // image used when drawing
    private static Image       _oldImage;

   // private static Shell       _shell;
    
	public NotifierComposite(Composite parent, int style) {
		super(parent, style);
		// TODO Auto-generated constructor stub
	}
	
	public NotifierComposite(Composite parent, int style, String title, String message, NotificationType type, ImageDescriptor icone, String couleur) {
		super(parent, style);
		notify(title, message, type, icone, couleur, null,null);
	}
	
	public NotifierComposite(Composite parent, int style, String title, String message, NotificationType type, ImageDescriptor icone) {
		super(parent, style);
		notify(title, message, type, icone,null, null,null);
	}
    
//    public static void notify(String title, String message, NotificationType type) {
//    	notify(title, message, type, null,null);
//    }
	
	/**
	 * 5 couleurs sous la forme : (r,v,b);(r,v,b);(r,v,b);(r,v,b);(r,v,b)<br>
	 * Exemple : (40,73,97);(40,73,97);(226,239,249);(177,211,243);(40,73,97) <br>
	 * Ordre des couleurs : <br>
	 * 1 - title foreground color<br>
	 * 2 - text foreground color<br>
	 * 3 - shell gradient background color - top<br>
	 * 4 - shell gradient background color - bottom   <br> 
	 * 5 - shell border color<br>
	 */
	public void changeCouleur(String couleur) {
		//valeur par defaut : (40,73,97);(40,73,97);(226,239,249);(177,211,243);(40,73,97)
		
		String[] listeCouleur = couleur.split(";");
		Color[] listeCouleurRVB = new Color[listeCouleur.length];
		
		for(int i = 0; i<listeCouleur.length && i<5; i++) {
			String uneCouleur = listeCouleur[i].substring(1).substring(0,listeCouleur[i].substring(1).length()-1);
			String[] uneCouleurRVB = uneCouleur.split(",");
			listeCouleurRVB[i] = ColorCache.getColor(
					LibConversion.stringToInteger(uneCouleurRVB[0]), 
					LibConversion.stringToInteger(uneCouleurRVB[1]), 
					LibConversion.stringToInteger(uneCouleurRVB[2])
					);
		}
	    _titleFgColor = listeCouleurRVB[0];
	    //_fgColor      = _titleFgColor;
	    _fgColor      = listeCouleurRVB[1];
	    _bgFgGradient = listeCouleurRVB[2];
	    _bgBgGradient = listeCouleurRVB[3];
	    _borderColor  = listeCouleurRVB[4];
	}

    /**
     * Creates and shows a notification dialog with a specific title, message and a
     * 
     * @param title
     * @param message
     * @param type
     */
    public void notify(String title, String message, NotificationType type, ImageDescriptor icone, String couleur,Integer x,Integer y) {
    	//_shell = new Shell(Display.getDefault().getActiveShell(), SWT.NO_FOCUS | SWT.NO_TRIM | SWT.ON_TOP);
        ////_shell = new Shell(Display.getDefault().getActiveShell(), SWT.NO_FOCUS | SWT.NO_TRIM);
    	
    	if(couleur!=null) {
    		changeCouleur(couleur);
    	}
    	
        setLayout(new FillLayout());
        setForeground(_fgColor);
        setBackgroundMode(SWT.INHERIT_DEFAULT);
//        addListener(SWT.Dispose, new Listener() {
//            @Override
//            public void handleEvent(Event event) {
//                _activeShells.remove(_shell);
//            }
//        });

        final Composite inner = new Composite(this, SWT.NONE);

        GridLayout gl = new GridLayout(2, false);
        gl.marginLeft = 5;
        gl.marginTop = 0;
        gl.marginRight = 5;
        gl.marginBottom = 5;

        inner.setLayout(gl);
        addListener(SWT.Resize, new Listener() {

            @Override
            public void handleEvent(Event e) {
                try {
                    // get the size of the drawing area
                    Rectangle rect = getClientArea();
                    // create a new image with that size
                    Image newImage = new Image(Display.getDefault(), Math.max(1, rect.width), rect.height);
                    // create a GC object we can use to draw with
                    GC gc = new GC(newImage);

                    // fill background
                    gc.setForeground(_bgFgGradient);
                    gc.setBackground(_bgBgGradient);
                    gc.fillGradientRectangle(rect.x, rect.y, rect.width, rect.height, true);

                    // draw shell edge
                    gc.setLineWidth(2);
                    gc.setForeground(_borderColor);
                    gc.drawRectangle(rect.x + 1, rect.y + 1, rect.width - 2, rect.height - 2);
                    // remember to dipose the GC object!
                    gc.dispose();

                    // now set the background image on the shell
                    setBackgroundImage(newImage);

                    // remember/dispose old used iamge
                    if (_oldImage != null) {
                        _oldImage.dispose();
                    }
                    _oldImage = newImage;
                } catch (Exception err) {
                    err.printStackTrace();
                }
            }
        });

        GC gc = new GC(this);

        String lines[] = message.split("\n");
        Point longest = null;
        int typicalHeight = gc.stringExtent("X").y;

        for (String line : lines) {
            Point extent = gc.stringExtent(line);
            if (longest == null) {
                longest = extent;
                continue;
            }

            if (extent.x > longest.x) {
                longest = extent;
            }
        }
        gc.dispose();

        int minHeight = typicalHeight * lines.length;

        CLabel imgLabel = new CLabel(inner, SWT.NONE);
        imgLabel.setLayoutData(new GridData(GridData.VERTICAL_ALIGN_BEGINNING | GridData.HORIZONTAL_ALIGN_BEGINNING));
        
        if(icone!=null)
        	imgLabel.setImage(icone.createImage());
        else
        	imgLabel.setImage(type.getImage());

        CLabel titleLabel = new CLabel(inner, SWT.NONE);
        titleLabel.setLayoutData(new GridData(GridData.FILL_HORIZONTAL | GridData.VERTICAL_ALIGN_CENTER));
        titleLabel.setText(title);
        titleLabel.setForeground(_titleFgColor);
        Font f = titleLabel.getFont();
        FontData fd = f.getFontData()[0];
        fd.setStyle(SWT.BOLD);
        fd.height = 11;
        titleLabel.setFont(FontCache.getFont(fd));

        Label text = new Label(inner, SWT.WRAP);
        Font tf = text.getFont();
        FontData tfd = tf.getFontData()[0];
        tfd.setStyle(SWT.BOLD);
        tfd.height = 8;
        text.setFont(FontCache.getFont(tfd));
        GridData gd = new GridData(GridData.FILL_BOTH);
        gd.horizontalSpan = 2;
        text.setLayoutData(gd);
        text.setForeground(_fgColor);
        text.setText(message);

        minHeight = 100;
        
        //setSize(350, minHeight);

//        if (Display.getDefault().getActiveShell() == null || Display.getDefault().getActiveShell().getMonitor() == null) { return; }
//
//        int startX = x;
//    	int startY = y;
//    	
//        if(x==null && y==null) {
//        	Rectangle clientArea = Display.getDefault().getActiveShell().getMonitor().getClientArea();
//        
////        	int startX = clientArea.x + clientArea.width - 352;
////        	int startY = clientArea.y + clientArea.height - 102;
//        	
//        	startX = clientArea.x + clientArea.width - 352;
//        	startY = clientArea.y + clientArea.height - 102;
//        
//
//        	// move other shells up
////        	if (!_activeShells.isEmpty()) {
////        		List<Shell> modifiable = new ArrayList<Shell>(_activeShells);
////        		Collections.reverse(modifiable);
////        		for (Shell shell : modifiable) {
////        			if(!shell.isDisposed()) {
////        				Point curLoc = shell.getLocation();
////        				shell.setLocation(curLoc.x, curLoc.y - 100);
////        				if (curLoc.y - 100 < 0) {
////        					_activeShells.remove(shell);
////        					shell.dispose();
////        				}
////        			}
////        		}
////        	}
//        	
//        }

//        setLocation(startX, startY);
//        setAlpha(0);
//        setVisible(true);
        
//        //round
//        addRoundedBoxOnResize(_shell, 15, EDGES_ALL);

        //_activeShells.add(this);

        //fadeIn(this);
    }

    private static void fadeIn(final Shell _shell) {
        Runnable run = new Runnable() {

            @Override
            public void run() {
                try {
                    if (_shell == null || _shell.isDisposed()) { return; }

                    int cur = _shell.getAlpha();
                    cur += FADE_IN_STEP;

                    if (cur > FINAL_ALPHA) {
                        _shell.setAlpha(FINAL_ALPHA);
                        startTimer(_shell);
                        return;
                    }

                    _shell.setAlpha(cur);
                    Display.getDefault().timerExec(FADE_TIMER, this);
                } catch (Exception err) {
                    err.printStackTrace();
                }
            }

        };
        Display.getDefault().timerExec(FADE_TIMER, run);
    }

    private static void startTimer(final Shell _shell) {
        Runnable run = new Runnable() {

            @Override
            public void run() {
                try {
                    if (_shell == null || _shell.isDisposed()) { return; }

                    fadeOut(_shell);
                } catch (Exception err) {
                    err.printStackTrace();
                }
            }

        };
        Display.getDefault().timerExec(DISPLAY_TIME, run);

    }

    private static void fadeOut(final Shell _shell) {
        final Runnable run = new Runnable() {

            @Override
            public void run() {
                try {
                    if (_shell == null || _shell.isDisposed()) { return; }

                    int cur = _shell.getAlpha();
                    cur -= FADE_OUT_STEP;

                    if (cur <= 0) {
                        _shell.setAlpha(0);
                        if (_oldImage != null) {
                            _oldImage.dispose();
                        }
                        _shell.dispose();
                        _activeShells.remove(_shell);
                        return;
                    }

                    _shell.setAlpha(cur);

                    Display.getDefault().timerExec(FADE_TIMER, this);

                } catch (Exception err) {
                    err.printStackTrace();
                }
            }

        };
        Display.getDefault().timerExec(FADE_TIMER, run);

    }
    
    /*****************************************************************************************************************************/

	/** Left top edge*/
	public static final int EDGE_LT = 1 << 1;
	/** Right top edge */
	public static final int EDGE_RT = 1 << 2;
	/** Left bottom edge */
	public static final int EDGE_LB = 1 << 3;
	/** Right bottom edge */
	public static final int EDGE_RB = 1 << 4;
	/** Left side - left top and bottom edges */
	public static final int EDGES_LEFT = EDGE_LT | EDGE_LB;
	/** Right side - right top and bottom edges */
	public static final int EDGES_RIGHT = EDGE_RT | EDGE_RB;
	/** Top side - top left and right edges */
	public static final int EDGES_TOP = EDGE_LT | EDGE_RT;
	/** Bottom side - bottom left and right edges */
	public static final int EDGES_BOTTOM = EDGE_LB | EDGE_RB;
	/** All edges */
	public static final int EDGES_ALL = EDGES_LEFT | EDGES_RIGHT;


/**
* Returns a rounded rectangle region
* * @param bounds The rounded box size: x, y: offsets, width, height: size
* @param radius: edge radius
* @param edges: which edges are rounded. Combination of EDGE_* constants (using "|")
* @return Region to be set using Control.setRegion()
*/
public static Region regionRoundedBox(Rectangle bounds, int radius, int edges) {
Region res = new Region();

		res.add(bounds);
		Rectangle rect = new Rectangle(0, 0, 1, 0);
		for (int i = 0; i < radius; i++) {
			int y = radius - (int)Math.round(Math.sqrt(radius*radius - (radius-i)*(radius-i)));
			
			// top left
			rect.x = bounds.x + i;
			rect.y = bounds.y;
			rect.height = y + 1;
			if ((edges & EDGE_LT) != 0)
				res.subtract(rect);
			
			// top right
			rect.x = bounds.x + bounds.width - i - 1;
			if ((edges & EDGE_RT) != 0)
				res.subtract(rect);
			
			// bottom right
			rect.y = bounds.y + bounds.height - y - 1;
			if ((edges & EDGE_RB) != 0)
				res.subtract(rect);
		
			// bottom left
			rect.x = bounds.x + i;
			if ((edges & EDGE_LB) != 0)
				res.subtract(rect);
		}
		
		return res;
	}


	public static void addRoundedBoxOnResize(final Control c, final int radius, final int edges) {
		// doplnÃme listener
		Listener listener = new Listener() {
			public void handleEvent(Event event) {
				Rectangle r = c.getBounds();
				r.x = 0;
				r.y = 0;
				c.setRegion(regionRoundedBox(r, radius, edges));
			}
		};
		c.addListener(SWT.Resize, listener);
		
		// a hneÄ ho aj vyvolÃme (ak by nedoÅlo k resize...)
		listener.handleEvent(null);
	}
}
