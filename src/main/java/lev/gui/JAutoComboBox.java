package lev.gui;

import javax.swing.*;
import javax.swing.plaf.basic.BasicComboBoxEditor;
import java.awt.event.ItemEvent;
import java.util.List;

@SuppressWarnings("unused")
public class JAutoComboBox extends JComboBox<Object> {
    private final AutoTextFieldEditor autoTextFieldEditor;
    private boolean isFired = false;

    public JAutoComboBox(List<?> list) {
        this.autoTextFieldEditor = new AutoTextFieldEditor(list);
        this.setEditable(true);
        this.setModel(new DefaultComboBoxModel(list.toArray()) { // fix w java 9
            protected void fireContentsChanged(Object obj, int i, int j) {
                if (!JAutoComboBox.this.isFired) {
                    super.fireContentsChanged(obj, i, j);
                }

            }
        });
        this.setEditor(this.autoTextFieldEditor);
    }

    public boolean isCaseSensitive() {
        return this.autoTextFieldEditor.getAutoTextFieldEditor().isCaseSensitive();
    }

    public void setCaseSensitive(boolean flag) {
        this.autoTextFieldEditor.getAutoTextFieldEditor().setCaseSensitive(flag);
    }

    public boolean isStrict() {
        return this.autoTextFieldEditor.getAutoTextFieldEditor().isStrict();
    }

    public void setStrict(boolean flag) {
        this.autoTextFieldEditor.getAutoTextFieldEditor().setStrict(flag);
    }

    public List<?> getDataList() {
        return this.autoTextFieldEditor.getAutoTextFieldEditor().getDataList();
    }

    public void setDataList(List<?> list) {
        this.autoTextFieldEditor.getAutoTextFieldEditor().setDataList(list);
        this.setModel(new DefaultComboBoxModel<>(list.toArray()));
    }

    public void setSelectedValue(Object obj) {
        if (!this.isFired) {
            this.isFired = true;
            this.setSelectedItem(obj);
            this.fireItemStateChanged(new ItemEvent(this, 701, this.selectedItemReminder, ItemEvent.SELECTED));
            this.isFired = false;
        }
    }

    protected void fireActionEvent() {
        if (!this.isFired) {
            super.fireActionEvent();
        }

    }

    private class AutoTextFieldEditor extends BasicComboBoxEditor {
        AutoTextFieldEditor(List<?> list) {
            this.editor = new JAutoTextField(list, JAutoComboBox.this);
        }

        private JAutoTextField getAutoTextFieldEditor() {
            return (JAutoTextField) this.editor;
        }
    }
}
