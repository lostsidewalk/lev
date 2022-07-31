package lev.gui;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.event.*;

@SuppressWarnings("unused")
public abstract class LHelpComponent extends LComponent {
    protected LHelpPanel help = null;
    protected String helpPrefix = "";
    protected boolean followPos = true;
    protected Enum<?> saveTie;
    protected LSaveFile save;
    protected String title;
    protected int helpYoffset = 0;

    public LHelpComponent(String title) {
        this.title = title;
    }

    protected abstract void addHelpHandler(boolean var1);

    public void updateHelp() {
        if (this.help != null) {
            this.help.setTitle(this.helpPrefix + this.title);
            this.help.setContent(this.getHelp());
            if (this.followPos) {
                this.help.focusOn(this, this.helpYoffset);
            } else {
                this.help.setDefaultPos();
                this.help.hideArrow();
            }
        }

    }

    public void addHelpPrefix(String input) {
        this.helpPrefix = input + " ";
    }

    public void linkTo(Enum<?> setting, LSaveFile save, LHelpPanel help_, boolean hoverListener) {
        this.help = help_;
        this.save = save;
        this.saveTie = setting;
        if (this.hasHelp()) {
            this.addHelpHandler(hoverListener);
        }

    }

    public boolean hasHelp() {
        return this.getHelp() != null;
    }

    public String getHelp() {
        return this.save.helpInfo.get(this.saveTie);
    }

    public void setFollowPosition(boolean on) {
        this.followPos = on;
    }

    public boolean isFollowingPosition() {
        return this.followPos;
    }

    class HelpChangeHandler implements ChangeListener {
        HelpChangeHandler() {
        }

        public void stateChanged(ChangeEvent e) {
            SwingUtilities.invokeLater(LHelpComponent.this::updateHelp);
        }
    }

    class HelpListHandler implements ListSelectionListener {
        HelpListHandler() {
        }

        public void valueChanged(ListSelectionEvent e) {
            SwingUtilities.invokeLater(LHelpComponent.this::updateHelp);
        }
    }

    public class HelpMouseHandler implements MouseListener {
        public HelpMouseHandler() {
        }

        public void mouseClicked(MouseEvent arg0) {
        }

        public void mousePressed(MouseEvent arg0) {
        }

        public void mouseReleased(MouseEvent arg0) {
        }

        public void mouseEntered(MouseEvent arg0) {
            LHelpComponent.this.updateHelp();
        }

        public void mouseExited(MouseEvent arg0) {
            LHelpComponent.this.updateHelp();
        }
    }

    public class HelpFocusHandler implements FocusListener {
        public HelpFocusHandler() {
        }

        public void focusGained(FocusEvent event) {
            SwingUtilities.invokeLater(LHelpComponent.this::updateHelp);
        }

        public void focusLost(FocusEvent event) {
        }
    }

    public class HelpActionHandler implements ActionListener {
        public HelpActionHandler() {
        }

        public void actionPerformed(ActionEvent event) {
            SwingUtilities.invokeLater(LHelpComponent.this::updateHelp);
        }
    }
}
