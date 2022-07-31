package lev.gui;

import javax.swing.*;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;
import java.util.List;

@SuppressWarnings("unused")
public class JAutoTextField extends JTextField {
    private List<?> dataList;
    private boolean isCaseSensitive = false;
    private boolean isStrict = true;
    private JAutoComboBox autoComboBox = null;

    public JAutoTextField(List<?> list) {
        if (list == null) {
            throw new IllegalArgumentException("values can not be null");
        } else {
            this.dataList = list;
            this.init();
        }
    }

    JAutoTextField(List<?> list, JAutoComboBox b) {
        if (list == null) {
            throw new IllegalArgumentException("values can not be null");
        } else {
            this.dataList = list;
            this.autoComboBox = b;
            this.init();
        }
    }

    private void init() {
        this.setDocument(new AutoDocument());
        if (this.isStrict && this.dataList.size() > 0) {
            this.setText(this.dataList.get(0).toString());
        }

    }

    private String getMatch(String s) {
        for (Object o : this.dataList) {
            String s1 = o.toString();
            if (s1 != null) {
                if (!this.isCaseSensitive && s1.toLowerCase().startsWith(s.toLowerCase())) {
                    return s1;
                }

                if (this.isCaseSensitive && s1.startsWith(s)) {
                    return s1;
                }
            }
        }

        return null;
    }

    public void replaceSelection(String s) {
        AutoDocument _lb = (AutoDocument) this.getDocument();
        if (_lb != null) {
            try {
                int i = Math.min(this.getCaret().getDot(), this.getCaret().getMark());
                int j = Math.max(this.getCaret().getDot(), this.getCaret().getMark());
                _lb.replace(i, j - i, s, null);
            } catch (Exception var5) {
            }
        }

    }

    public boolean isCaseSensitive() {
        return this.isCaseSensitive;
    }

    public void setCaseSensitive(boolean flag) {
        this.isCaseSensitive = flag;
    }

    public boolean isStrict() {
        return this.isStrict;
    }

    public void setStrict(boolean flag) {
        this.isStrict = flag;
    }

    public List<?> getDataList() {
        return this.dataList;
    }

    public void setDataList(List<?> list) {
        if (list == null) {
            throw new IllegalArgumentException("values can not be null");
        } else {
            this.dataList = list;
        }
    }

    protected class AutoDocument extends PlainDocument {
        protected AutoDocument() {
        }

        public void replace(int i, int j, String s, AttributeSet attributeset) throws BadLocationException {
            super.remove(i, j);
            this.insertString(i, s, attributeset);
        }

        public void insertString(int i, String s, AttributeSet attributeset) throws BadLocationException {
            if (s != null && !"".equals(s)) {
                String s1 = this.getText(0, i);
                String s2 = JAutoTextField.this.getMatch(s1 + s);
                int j = i + s.length() - 1;
                if (JAutoTextField.this.isStrict && s2 == null) {
                    s2 = JAutoTextField.this.getMatch(s1);
                    --j;
                } else if (!JAutoTextField.this.isStrict && s2 == null) {
                    super.insertString(i, s, attributeset);
                    return;
                }

                if (JAutoTextField.this.autoComboBox != null && s2 != null) {
                    JAutoTextField.this.autoComboBox.setSelectedValue(s2);
                }

                super.remove(0, this.getLength());
                super.insertString(0, s2, attributeset);
                JAutoTextField.this.setSelectionStart(j + 1);
                JAutoTextField.this.setSelectionEnd(this.getLength());
            }
        }

        public void remove(int i, int j) throws BadLocationException {
            int k = JAutoTextField.this.getSelectionStart();
            if (k > 0) {
                --k;
            }

            String s = JAutoTextField.this.getMatch(this.getText(0, k));
            if (!JAutoTextField.this.isStrict && s == null) {
                super.remove(i, j);
            } else {
                super.remove(0, this.getLength());
                super.insertString(0, s, null);
            }

            if (JAutoTextField.this.autoComboBox != null && s != null) {
                JAutoTextField.this.autoComboBox.setSelectedValue(s);
            }

            try {
                JAutoTextField.this.setSelectionStart(k);
                JAutoTextField.this.setSelectionEnd(this.getLength());
            } catch (Exception var6) {
            }
        }
    }
}
