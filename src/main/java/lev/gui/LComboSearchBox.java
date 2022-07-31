package lev.gui;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseListener;
import java.util.Set;
import java.util.TreeSet;

@SuppressWarnings("unused")
public class LComboSearchBox<T> extends LComboBox<T> {
    static String searchText = "Search...";
    protected Set<T> backup = new TreeSet<>();
    protected JTextField search = new JTextField();
    protected LButton enterButton;
    protected LComboSearchBox<T>.FilterWorker worker = new FilterWorker("");

    public LComboSearchBox(String title_, Font font, Color shade) {
        super(title_, font, shade);
        this.search.setLocation(this.titleLabel.getX() + this.titleLabel.getWidth() + 10, 0);
        this.search.setText(searchText);
        this.search.setVisible(true);
        this.search.addFocusListener(new FocusListener() {
            public void focusGained(FocusEvent e) {
                if (LComboSearchBox.this.search.getText().equals(LComboSearchBox.searchText)) {
                    LComboSearchBox.this.search.setText("");
                }

            }

            public void focusLost(FocusEvent e) {
                if (LComboSearchBox.this.search.getText().trim().equals("")) {
                    LComboSearchBox.this.search.setText(LComboSearchBox.searchText);
                }

            }
        });
        this.addDocumentListener(new DocumentListener() {
            public void insertUpdate(DocumentEvent e) {
                LComboSearchBox.this.filterItems();
            }

            public void removeUpdate(DocumentEvent e) {
                LComboSearchBox.this.filterItems();
            }

            public void changedUpdate(DocumentEvent e) {
            }
        });
        this.add(this.search);
    }

    void filterItems() {
        String trimmed = this.search.getText().trim();
        if (trimmed.equals(searchText)) {
            trimmed = "";
        }

        trimmed = trimmed.toUpperCase();
        this.worker.cancel(true);
        this.box.removeAllItems();
        this.worker = new FilterWorker(trimmed);
        this.worker.execute();
    }

    public void setSize(int x, int y) {
        super.setSize(x, y);
        this.search.setSize(x - this.titleLabel.getWidth() - this.titleLabel.getX() - 10, this.titleLabel.getHeight());
        if (this.enterButton != null) {
            this.enterButton.setLocation(x - this.enterButton.getWidth(), this.search.getY() + this.search.getHeight() + 10);
            this.box.setSize(x - this.enterButton.getWidth() - 10, this.box.getHeight());
            this.enterButton.setSize(this.enterButton.getWidth(), this.box.getHeight());
        }

    }

    public void removeAllItems() {
        super.removeAllItems();
        this.backup.clear();
    }

    public int getBackupListSize() {
        return this.backup.size();
    }

    public void addItem(T o) {
        super.addItem(o);
        this.backup.add(o);
    }

    public void addBoxActionListener(ActionListener f) {
        super.addActionListener(f);
    }

    public void addFocusListener(FocusListener f) {
        super.addFocusListener(f);
        if (this.enterButton != null) {
            this.enterButton.addFocusListener(f);
        }

        this.search.addFocusListener(f);
    }

    public void addMouseListener(MouseListener m) {
        super.addMouseListener(m);
        if (this.enterButton != null) {
            this.enterButton.addMouseListener(m);
        }

        this.search.addMouseListener(m);
    }

    public void reset() {
        this.setText(searchText);
        this.filterItems();
        super.reset();
    }

    public String getText() {
        return this.search.getText();
    }

    public void setText(String s) {
        this.search.setText(s);
    }

    public void addEnterButton(String label, ActionListener done) {
        this.enterButton = new LButton(label);
        this.enterButton.addActionListener(done);
        this.add(this.enterButton);
        this.setSize(this.getSize().width, this.getSize().height);
    }

    public final void addDocumentListener(DocumentListener d) {
        this.search.getDocument().addDocumentListener(d);
    }

    class FilterWorker extends SwingWorker<Integer, Integer> {
        String filter;

        FilterWorker(String s) {
            this.filter = s;
        }

        protected Integer doInBackground() {

            for (T item : LComboSearchBox.this.backup) {
                if (item.toString().toUpperCase().contains(this.filter)) {
                    LComboSearchBox.this.box.addItem(item);
                }
            }

            return 1;
        }

        protected void done() {
        }
    }
}
