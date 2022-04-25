package fr.legrain.gestCom.librairiesEcran.notifier;

import org.eclipse.swt.graphics.Image;

import fr.legrain.gestCom.librairiesEcran.notifier.cache.ImageCache;

public enum NotificationType {
    ERROR(ImageCache.getImage("error.png")),
    DELETE(ImageCache.getImage("delete.png")),
    WARN(ImageCache.getImage("warn.png")),
    SUCCESS(ImageCache.getImage("ok.png")),
    INFO(ImageCache.getImage("info.png")),
    LIBRARY(ImageCache.getImage("library.png")),
    HINT(ImageCache.getImage("hint.png")),
    PRINTED(ImageCache.getImage("printer.png")),
    CONNECTION_TERMINATED(ImageCache.getImage("terminated.png")),
    CONNECTION_FAILED(ImageCache.getImage("connecting.png")),
    CONNECTED(ImageCache.getImage("connected.png")),
    DISCONNECTED(ImageCache.getImage("disconnected.png")),
    TRANSACTION_OK(ImageCache.getImage("ok.png")),
    TRANSACTION_FAIL(ImageCache.getImage("error.png"));
    
    public NotificationType getType(String nom) {
		return NotificationType.valueOf(nom);
    }

    private Image _image;

    private NotificationType(Image img) {
        _image = img;
    }

    public Image getImage() {
        return _image;
    }
}
