package lev.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Map;

@SuppressWarnings("unused")
public class LCheckBoxConfig extends LUserSetting<Boolean> {
    public static String buttonText = "Open";
    public static int spacing = 18;
    protected LSpecialCheckBox cbox;
    protected LButton button;

    protected LCheckBoxConfig(String title_) {
        super(title_);
    }

    public LCheckBoxConfig(String title_, int size, int style, Color shade, LHelpPanel help_, LSaveFile save_, Enum<?> setting) {
        super(title_);
        this.help = help_;
        this.save = save_;
        this.saveTie = setting;
        this.button = new LButton(buttonText);
        this.button.addActionListener(new UpdateHelpActionHandler());
        this.cbox = new LSpecialCheckBox(title_, new Font("Serif", size, style), shade, this);
        this.cbox.tie(setting, save_, this.help, false);
        this.button.setLocation(this.cbox.getWidth() + spacing, 0);
        this.add(this.button);
        this.add(this.cbox);
        this.setSize(this.cbox.getWidth() + this.button.getWidth() + spacing, this.cbox.getHeight());
    }

    public void updateHelp() {
        this.requestFocus();
        SwingUtilities.invokeLater(() -> SwingUtilities.invokeLater(() -> SwingUtilities.invokeLater(LCheckBoxConfig.this::updateHelpHelper)));
    }

    private void updateHelpHelper() {
        super.updateHelp();
        if (this.help != null) {
            this.help.setDefaultPos();
            this.help.hideArrow();
        }

    }

    protected void addUpdateHandlers() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public boolean revertTo(Map<Enum<?>, Setting<?>> m) {
        return this.cbox.revertTo(m);
    }

    public Boolean getValue() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    protected void addHelpHandler(boolean hoverHandler) {
    }

    public void setButtonOffset(int in) {
        this.button.setLocation(this.button.getX(), this.cbox.getY() + in);
        this.setSize(this.getWidth(), this.button.getHeight() + in);
    }

    public void setOffset(int in) {
        this.cbox.setOffset(in);
    }

    public void addShadow() {
        this.cbox.addShadow();
    }

    public JComponent getSource() {
        return this.button.getSource();
    }

    public void addActionListener(ActionListener a) {
        this.button.addActionListener(a);
    }

    public void highlightChanged() {
        this.cbox.highlightChanged();
    }

    public void clearHighlight() {
        this.cbox.clearHighlight();
    }

    protected static class LSpecialCheckBox extends LCheckBox {
        LHelpComponent forwardTo;

        public LSpecialCheckBox(String text, Font font, Color shade, LHelpComponent forwardTo_) {
            super(text, font, shade);
            this.forwardTo = forwardTo_;
        }

        public void updateHelp() {
            this.forwardTo.updateHelp();
        }
    }

    public class UpdateHelpActionHandler implements ActionListener {
        public UpdateHelpActionHandler() {
        }

        public void actionPerformed(ActionEvent event) {
            SwingUtilities.invokeLater(LCheckBoxConfig.this::updateHelp);
        }
    }
}
