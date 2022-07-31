package lev.gui;

import javax.swing.*;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.FocusListener;
import java.awt.event.MouseListener;
import java.util.Map;

@SuppressWarnings("unused")
public class LDoubleSpinner extends LUserSetting<Double> {
    protected final JSpinner spinner;

    public LDoubleSpinner(String title, double init, double min, double max, double step, int width) {
        super(title);
        SpinnerModel model = new SpinnerNumberModel(init, min, max, step);
        this.spinner = new JSpinner(model);
        JFormattedTextField textField = ((JSpinner.DefaultEditor) this.spinner.getEditor()).getTextField();
        textField.setAlignmentX(4.0F);
        textField.setColumns(width);
        this.spinner.setSize(this.spinner.getPreferredSize());
        this.add(this.spinner);
        this.setSize(this.spinner.getPreferredSize());
    }

    protected void addUpdateHandlers() {
        this.spinner.addChangeListener(new LUserSetting.UpdateChangeHandler());
    }

    public boolean revertTo(Map<Enum<?>, Setting<?>> m) {
        if (this.isTied()) {
            double cur = this.getValue();
            this.setValue(m.get(this.saveTie).getDouble());
            return cur == this.getValue();
        }

        return true;
    }

    public Double getValue() {
        return (Double) this.spinner.getValue();
    }

    public void setValue(int in) {
        this.spinner.setValue(in);
    }

    public void setValue(double in) {
        this.spinner.setValue(in);
    }

    protected void addHelpHandler(boolean hoverListener) {
        JSpinner.DefaultEditor editor = (JSpinner.DefaultEditor) this.spinner.getEditor();
        editor.getTextField().addFocusListener(new LHelpComponent.HelpFocusHandler());
        if (hoverListener) {
            MouseListener m = new LHelpComponent.HelpMouseHandler();
            this.titleLabel.addMouseListener(m);
            editor.getTextField().addMouseListener(m);
            Component[] arr$ = this.spinner.getComponents();
            int len$ = arr$.length;

            for (Component c : arr$) {
                c.addMouseListener(m);
            }
        }

    }

    public synchronized void addFocusListener(FocusListener arg0) {
        JSpinner.DefaultEditor editor = (JSpinner.DefaultEditor) this.spinner.getEditor();
        editor.getTextField().addFocusListener(arg0);
    }

    public void addChangeListener(ChangeListener c) {
        this.spinner.addChangeListener(c);
    }

    public void highlightChanged() {
        ((JSpinner.DefaultEditor) this.spinner.getEditor()).getTextField().setBackground(new Color(224, 121, 147));
    }

    public void clearHighlight() {
        ((JSpinner.DefaultEditor) this.spinner.getEditor()).getTextField().setBackground(Color.WHITE);
    }
}
