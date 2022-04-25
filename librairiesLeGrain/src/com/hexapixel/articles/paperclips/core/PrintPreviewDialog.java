package com.hexapixel.articles.paperclips.core;

import net.sf.paperclips.PaperClips;
import net.sf.paperclips.Print;
import net.sf.paperclips.PrintJob;
import net.sf.paperclips.ui.PrintPreview;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.printing.PrintDialog;
import org.eclipse.swt.printing.PrinterData;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Spinner;

public class PrintPreviewDialog {

    private PrintJob          _printJob;

    private Shell             _shell;
    private Button            _previousPage;
    private Label             _pageNumber;
    private Button            _nextPage;

    private ScrolledComposite _scroll;
    private PrintPreview      _preview;

    private double[]          _scrollingPosition;

    private Print             _print;
    private Shell             _parentShell;

    private String            _printJobName;
    private IPrintHandler     _fetcher;
    private Image             _shellImage;

    public PrintPreviewDialog(Print print, Shell parentShell, IPrintHandler fetcher) {
        _print = print;
        _parentShell = parentShell;
        _printJobName = fetcher.getPrintJobName();
        _fetcher = fetcher;
        _shellImage = fetcher.getPrintPreviewShellIcon();
    }

    public Shell open() throws Exception {
        _printJob = new PrintJob(_printJobName, _print).setMargins(36); // 1.5"

        _shell = new Shell(_parentShell, SWT.SHELL_TRIM | SWT.RESIZE);
        _shell.setImage(_shellImage);
        _shell.setText("Print Preview");
        _shell.setBounds(100, 100, 800, 600);
        _shell.setLayout(new GridLayout(1, false));

        createButtonPanel(_shell).setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));
        createScrollingPreview(_shell).setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));

        _preview.setPrintJob(_printJob);
        updatePreviewSize();
        updatePageNumber();

        _shell.setVisible(true);

        return _shell;
    }

    private Control createButtonPanel(Composite parent) throws Exception {
        Composite composite = new Composite(parent, SWT.NONE);

        GridLayout layout = new GridLayout(16, false);
        layout.marginWidth = layout.marginHeight = 0;
        composite.setLayout(layout);

        _previousPage = createIconButton(composite, "previous_page.gif", "Previous Page", new Listener() {
            public void handleEvent(Event event) {
                setPreviewPageIndex(_preview.getPageIndex() - _preview.getHorizontalPageCount() * _preview.getVerticalPageCount());
            }
        });

        _pageNumber = new Label(composite, SWT.NONE);

        _nextPage = createIconButton(composite, "next_page.gif", "Next Page", new Listener() {
            public void handleEvent(Event event) {
                setPreviewPageIndex(_preview.getPageIndex() + _preview.getHorizontalPageCount() * _preview.getVerticalPageCount());
            }
        });

        createIconButton(composite, "fit_horizontal.png", "Fit Width", new Listener() {
            public void handleEvent(Event event) {
                _preview.setFitHorizontal(true);
                _preview.setFitVertical(false);
                rememberScrollingPosition();
                updatePreviewSize();
                restoreScrollingPosition();
            }
        });

        createIconButton(composite, "fit_vertical.png", "Fit Height", new Listener() {
            public void handleEvent(Event event) {
                _preview.setFitVertical(true);
                _preview.setFitHorizontal(false);
                rememberScrollingPosition();
                updatePreviewSize();
                restoreScrollingPosition();
            }
        });

        createIconButton(composite, "fit_best.png", "Fit Window", new Listener() {
            public void handleEvent(Event event) {
                _preview.setFitVertical(true);
                _preview.setFitHorizontal(true);
                rememberScrollingPosition();
                updatePreviewSize();
                restoreScrollingPosition();
            }
        });

        createIconButton(composite, "zoom_in.gif", "Zoom In", new Listener() {
            public void handleEvent(Event event) {
                setPreviewScale(_preview.getAbsoluteScale() * 1.1f);
            }
        });

        createIconButton(composite, "zoom_out.gif", "Zoom Out", new Listener() {
            public void handleEvent(Event event) {
                setPreviewScale(_preview.getAbsoluteScale() / 1.1f);
            }
        });

        createIconButton(composite, "zoom_scale.gif", "Zoom to Scale", new Listener() {
            public void handleEvent(Event event) {
                setPreviewScale(1);
            }
        });

        createTextButton(composite, "Port", "Portrait Orientation", new Listener() {
            public void handleEvent(Event event) {
                _printJob.setOrientation(PaperClips.ORIENTATION_PORTRAIT);
                _preview.setPrintJob(_printJob);

                forgetScrollingPosition();
                updatePreviewSize();
                updatePageNumber();
            }
        });

        createTextButton(composite, "Land", "Landscape Orientation", new Listener() {
            public void handleEvent(Event event) {
                _printJob.setOrientation(PaperClips.ORIENTATION_LANDSCAPE);
                _preview.setPrintJob(_printJob);

                forgetScrollingPosition();
                updatePreviewSize();
                updatePageNumber();
            }
        });

        createIconButton(composite, "print.gif", "Print", new Listener() {
            public void handleEvent(Event event) {
                PrintDialog dialog = new PrintDialog(_shell, SWT.NONE);
                PrinterData printerData = dialog.open();
                if (printerData != null) {
                    PaperClips.print(_printJob, printerData);
                    _preview.setPrinterData(printerData);
                }
            }
        });

        createLabel(composite, "Horz Pages");
        createPageCountSpinner(composite, new Listener() {
            public void handleEvent(Event event) {
                _preview.setHorizontalPageCount(((Spinner) event.widget).getSelection());
                forgetScrollingPosition();
                updatePreviewSize();
                updatePageNumber();
            }
        });

        createLabel(composite, "Vert Pages");
        createPageCountSpinner(composite, new Listener() {
            public void handleEvent(Event event) {
                _preview.setVerticalPageCount(((Spinner) event.widget).getSelection());
                forgetScrollingPosition();
                updatePreviewSize();
                updatePageNumber();
            }
        });

        return composite;
    }

    private Control createScrollingPreview(Composite parent) throws Exception {
        _scroll = new ScrolledComposite(parent, SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL);
        _scroll.setExpandHorizontal(true);
        _scroll.setExpandVertical(true);

        _preview = new PrintPreview(_scroll, SWT.NONE);

        _scroll.setContent(_preview);

        _scroll.addListener(SWT.Resize, new Listener() {
            public void handleEvent(Event event) {
                Rectangle bounds = _scroll.getClientArea();

                _scroll.getHorizontalBar().setPageIncrement(bounds.width * 2 / 3);
                _scroll.getVerticalBar().setPageIncrement(bounds.height * 2 / 3);

                if (_preview.isFitHorizontal() ^ _preview.isFitVertical()) {
                    rememberScrollingPosition();
                    updatePreviewSize();
                    restoreScrollingPosition();
                }
            }
        });

        _preview.setFitVertical(true);
        _preview.setFitHorizontal(true);

        Listener dragListener = new Listener() {
            private final Point dpi                   = Display.getDefault().getDPI();
            private boolean     scrollable            = false;

            private boolean     dragging              = false;
            private Point       dragStartScrollOrigin = null;
            private Point       dragStartMouseAnchor  = null;

            public void handleEvent(Event event) {
                try {
                    switch (event.type) {
                        case SWT.Resize:
                            forgetScrollingPosition();
                            Rectangle bounds = _scroll.getClientArea();
                            Point size = _preview.getSize();
                            scrollable = size.x > bounds.width || size.y > bounds.height;
                            if (!scrollable && dragging) endDragging();
                            break;
                        case SWT.MouseDown:
                            forgetScrollingPosition();
                            if (scrollable && event.button == 1) beginDragging(event);
                            break;
                        case SWT.MouseMove:
                            if (dragging) {
                                forgetScrollingPosition();
                                Point point = _preview.toDisplay(event.x, event.y);
                                _scroll.setOrigin(dragStartScrollOrigin.x + dragStartMouseAnchor.x - point.x, dragStartScrollOrigin.y + dragStartMouseAnchor.y - point.y);
                            }
                            break;
                        case SWT.MouseUp:
                            forgetScrollingPosition();
                            if (dragging) endDragging();
                            break;
                        case SWT.MouseEnter:
                            Display.getDefault().addFilter(SWT.MouseWheel, this);
                            break;
                        case SWT.MouseWheel:
                            if (event.count != 0) {
                                if (scrollable && !dragging && (event.stateMask == SWT.NONE || event.stateMask == SWT.SHIFT)) {
                                    forgetScrollingPosition();
                                    bounds = _scroll.getClientArea();
                                    size = _preview.getSize();
                                    Point origin = _scroll.getOrigin();
                                    int direction = event.count > 0 ? -1 : 1;
                                    // Prefer vertical scrolling unless user is pressing Shift
                                    if (size.y > bounds.height && event.stateMask == SWT.NONE) origin.y += direction * Math.min(dpi.y, bounds.height / 4);
                                    else if (size.x > bounds.width) origin.x += direction * Math.min(dpi.x, bounds.width / 4);
                                    _scroll.setOrigin(origin);
                                    event.doit = false;
                                } else if (event.stateMask == SWT.CTRL) { // Ctrl+MouseWheel -> zoom
                                    float scale = _preview.getAbsoluteScale();
                                    setPreviewScale(event.count > 0 ? scale * 1.1f : scale / 1.1f);
                                }
                            }
                            break;
                        case SWT.MouseExit:
                            Display.getDefault().removeFilter(SWT.MouseWheel, this);
                            break;
                    }
                } catch (Exception err) {
                    err.printStackTrace();
                } catch (Error err) {
                    err.printStackTrace();
                }
            }

            private void beginDragging(Event event) {
                dragStartScrollOrigin = _scroll.getOrigin();
                dragStartMouseAnchor = _preview.toDisplay(event.x, event.y);
                dragging = true;
            }

            private void endDragging() {
                dragging = false;
                dragStartMouseAnchor = null;
                dragStartScrollOrigin = null;
            }
        };

        _scroll.addListener(SWT.Resize, dragListener);
        _preview.addListener(SWT.MouseDown, dragListener);
        _preview.addListener(SWT.MouseMove, dragListener);
        _preview.addListener(SWT.MouseUp, dragListener);

        // These are for mouse wheel handling
        _preview.addListener(SWT.MouseEnter, dragListener);
        _preview.addListener(SWT.MouseExit, dragListener);

        return _scroll;
    }

    private Button createIconButton(Composite parent, String imageFilename, String toolTipText, Listener selectionListener) {
        Button button = createButton(parent, toolTipText, selectionListener);
        button.setImage(createImage(imageFilename));
        return button;
    }

    private Button createTextButton(Composite parent, String text, String toolTipText, Listener selectionListener) {
        Button button = createButton(parent, toolTipText, selectionListener);
        button.setText(text);
        return button;
    }

    private Button createButton(Composite parent, String toolTipText, Listener selectionListener) {
        Button button = new Button(parent, SWT.PUSH);
        button.setToolTipText(toolTipText);
        button.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, false));
        button.addListener(SWT.Selection, selectionListener);
        return button;
    }

    private Spinner createPageCountSpinner(Composite parent, Listener selectionListener) {
        Spinner spinner = new Spinner(parent, SWT.BORDER);
        spinner.setMinimum(1);
        spinner.setMaximum(99);
        spinner.addListener(SWT.Selection, selectionListener);
        return spinner;
    }

    private void createLabel(Composite parent, String text) {
        new Label(parent, SWT.NONE).setText(text);
    }

    private Image createImage(String filename) {
        return _fetcher.getImage(filename);
    }

    private void updatePageNumber() {
        int pageIndex = _preview.getPageIndex();
        int pageCount = _preview.getPageCount();
        int visiblePageCount = _preview.getHorizontalPageCount() * _preview.getVerticalPageCount();
        String text = (visiblePageCount > 1 ? "Pages " + (pageIndex + 1) + "-" + Math.min(pageCount, pageIndex + visiblePageCount) : "Page " + (pageIndex + 1)) + " of " + pageCount;
        _pageNumber.setText(text);
        _previousPage.setEnabled(pageIndex > 0);
        _nextPage.setEnabled(pageIndex < pageCount - visiblePageCount);
        _shell.layout(new Control[] { _pageNumber });
    }

    private void rememberScrollingPosition() {
        Point size = _preview.getSize();
        if (size.x == 0 || size.y == 0) {
            forgetScrollingPosition();
        } else if (_scrollingPosition == null) {
            Point origin = _scroll.getOrigin();
            _scrollingPosition = new double[] { (double) origin.x / (double) size.x, (double) origin.y / (double) size.y };
        }
    }

    private void forgetScrollingPosition() {
        _scrollingPosition = null;
    }

    private void restoreScrollingPosition() {
        if (_scrollingPosition != null) {
            Point size = _preview.getSize();
            _scroll.setOrigin((int) Math.round(_scrollingPosition[0] * size.x), (int) Math.round(_scrollingPosition[1] * size.y));
        }
    }

    private void updatePreviewSize() {
        Point minSize;
        Rectangle bounds = _scroll.getClientArea();
        if (_preview.isFitHorizontal()) {
            if (_preview.isFitVertical()) minSize = new Point(0, 0); // Best fit
            else minSize = new Point(0, _preview.computeSize(bounds.width, SWT.DEFAULT).y); // Fit to width
        } else {
            if (_preview.isFitVertical()) minSize = new Point(_preview.computeSize(SWT.DEFAULT, bounds.height).x, 0); // Fit
            // to
            // height
            else minSize = _preview.computeSize(SWT.DEFAULT, SWT.DEFAULT); // Custom scale
        }
        _scroll.setMinSize(minSize);
    }

    private void setPreviewScale(float scale) {
        _preview.setFitVertical(false);
        _preview.setFitHorizontal(false);
        _preview.setScale(scale);
        rememberScrollingPosition();
        updatePreviewSize();
        restoreScrollingPosition();
    }

    private void setPreviewPageIndex(int pageIndex) {
        _preview.setPageIndex(Math.max(Math.min(pageIndex, _preview.getPageCount() - 1), 0));
        updatePageNumber();
    }
}
