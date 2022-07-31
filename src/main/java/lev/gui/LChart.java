package lev.gui;

import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

public class LChart extends JPanel {
    protected JFreeChart chart;
    protected ChartPanel cPanel;
    protected LLabel title;

    public LChart(@SuppressWarnings("unused") String title_, Dimension size_) {
        this.setLayout(null);
        super.setSize(size_.width, size_.height);
    }

    protected void init(String title_, Dimension size_, Color c) {
        if (title_ != null) {
            this.title = new LLabel(title_, new Font("Serif", Font.BOLD, 14), c);
            this.title.setLocation(this.getWidth() / 2 - this.title.getWidth() / 2, 0);
            this.add(this.title);
        }

        this.chart.setBackgroundPaint(new Color(0, 0, 0, 0));
        this.cPanel = new ChartPanel(this.chart);
        this.cPanel.setVisible(true);
        this.cPanel.setOpaque(false);
        Border border = BorderFactory.createLineBorder(Color.black, 0);
        this.cPanel.setBorder(border);
        if (this.title == null) {
            this.cPanel.setSize(size_);
        } else {
            this.cPanel.setSize(size_.width, size_.height - this.title.getHeight());
            this.cPanel.setLocation(0, this.title.getHeight());
        }

        this.add(this.cPanel);
        this.setOpaque(false);
    }
}
