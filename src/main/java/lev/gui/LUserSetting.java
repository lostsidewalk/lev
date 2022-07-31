package lev.gui;

import javax.swing.*;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Map;

public abstract class LUserSetting<T> extends LHelpComponent {
    protected LLabel titleLabel;

    public LUserSetting(String text) {
        super(text);
    }

    public LUserSetting(String text, Font label, Color shade) {
        this(text);
        this.titleLabel = new LLabel(text, label, shade);
        this.add(this.titleLabel);
    }

    public void tie(Enum<?> setting, LSaveFile saveFile, LHelpPanel help_, boolean hoverListener) {
        this.tie(setting, saveFile);
        this.linkTo(setting, saveFile, help_, hoverListener);
    }

    public void tie(Enum<?> setting, LSaveFile saveFile) {
        this.saveTie = setting;
        this.save = saveFile;
        this.save.tie(setting, this);
        this.revertTo(this.save.saveSettings);
        this.addUpdateHandlers();
    }

    protected abstract void addUpdateHandlers();

    public abstract boolean revertTo(Map<Enum<?>, Setting<?>> var1);

    public Boolean isTied() {
        return this.saveTie != null;
    }

    public abstract T getValue();

    public void update() {
        if (this.isTied()) {
            this.save.set(this.saveTie, this.getValue());
        }

    }

    public abstract void highlightChanged();

    public abstract void clearHighlight();

    public String getName() {
        return this.title;
    }

    public class UpdateCaretHandler implements CaretListener {
        public UpdateCaretHandler() {
        }

        public void caretUpdate(CaretEvent event) {
            SwingUtilities.invokeLater(new Runnable() {
                public void run() {
                    LUserSetting.this.update();
                }
            });
        }
    }

    public class UpdateChangeHandler implements ChangeListener {
        public UpdateChangeHandler() {
        }

        public void stateChanged(ChangeEvent event) {
            LUserSetting.this.update();
        }
    }

    public class UpdateHandler implements ActionListener {
        public UpdateHandler() {
        }

        public void actionPerformed(ActionEvent event) {
            LUserSetting.this.update();
        }
    }
}
