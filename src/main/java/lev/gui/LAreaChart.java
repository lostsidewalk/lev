package lev.gui;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYItemRenderer;
import org.jfree.data.Range;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import java.awt.*;
import java.util.ArrayList;

@SuppressWarnings("unused")
public class LAreaChart extends LChart {
    public final XYPlot plot;
    final ArrayList<XYSeries> series = new ArrayList<>();
    final XYSeriesCollection collection = new XYSeriesCollection();

    public LAreaChart(String title_, Dimension size_, Color titleColor, Color seriesColor, String XLabel, String YLabel) {
        super(title_, size_);
        this.chart = ChartFactory.createXYAreaChart(null, XLabel, YLabel, this.collection, PlotOrientation.VERTICAL, false, true, false);
        this.plot = this.chart.getXYPlot();
        this.plot.setBackgroundPaint(Color.lightGray);
        this.plot.setBackgroundAlpha(0.0F);
        this.plot.setForegroundAlpha(0.7F);
        this.plot.setDomainGridlinePaint(Color.white);
        this.plot.setRangeGridlinePaint(Color.white);
        this.plot.getDomainAxis().setLabelPaint(Color.lightGray);
        this.plot.getRangeAxis().setLabelPaint(Color.lightGray);
        this.plot.getRangeAxis().setTickLabelsVisible(false);
        this.plot.getDomainAxis().setTickLabelPaint(Color.lightGray);
        this.plot.getDomainAxis().setAutoRange(false);
        this.plot.getDomainAxis().setRange(-15.0, 15.0);
        this.plot.setDomainGridlinesVisible(false);
        this.plot.setRangeGridlinesVisible(false);
        this.plot.setOutlineVisible(false);
        this.plot.getRangeAxis().setAxisLineVisible(false);
        this.plot.getRangeAxis().setTickMarksVisible(false);
        this.addSeries(seriesColor);
        this.init(title_, size_, titleColor);
        this.cPanel.setSize(this.cPanel.getWidth() - 12, this.cPanel.getHeight());
    }

    public void paint(Graphics g) {
        super.paint(g);
        g.setColor(new Color(50, 180, 180));
        g.drawLine(this.getSize().width / 2 + 4, 30, this.getSize().width / 2 + 4, this.getSize().height - 36);
    }

    public void clear() {

        for (XYSeries s : this.series) {
            s.clear();
        }
    }

    public void putPoint(int nthSeries, double x, double y) {
        this.series.get(nthSeries).add(x, y);
    }

    public final void addSeries(Color seriesColor) {
        XYSeries seriesNew = new XYSeries("Series");
        this.series.add(seriesNew);
        this.collection.addSeries(seriesNew);
        XYItemRenderer renderer = this.plot.getRenderer();
        renderer.setSeriesPaint(this.series.size() - 1, seriesColor);
    }

    public void resetDomain() {
        Range domain = this.plot.getDataRange(this.plot.getDomainAxis());
        double set = domain.getLowerBound();
        if (domain.getUpperBound() > Math.abs(set)) {
            set = domain.getUpperBound();
        }

        if (Math.abs(set) < 15.0) {
            set = 15.0;
        }

        this.plot.getDomainAxis().setRange(-Math.abs(set), Math.abs(set));
    }
}
