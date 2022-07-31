package lev.gui;

import javax.swing.text.html.HTMLDocument;
import javax.swing.text.html.StyleSheet;
import java.awt.*;

@SuppressWarnings("unused")
public class LHTMLPane extends LEditorPane {
    public LHTMLPane() {
        this.pane.setContentType("text/html");
    }

    public HTMLDocument getDocument() {
        return (HTMLDocument) this.pane.getDocument();
    }

    public StyleSheet getStyleSheet() {
        return this.getDocument().getStyleSheet();
    }

    public void transparentBackground() {
        this.pane.setBackground(new Color(255, 255, 255, 0));
    }
}
