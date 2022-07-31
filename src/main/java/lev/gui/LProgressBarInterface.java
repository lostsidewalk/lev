package lev.gui;

public interface LProgressBarInterface {
    void setMax(int var1, String var2);

    void setStatus(String var1);

    void setStatusNumbered(int var1, int var2, String var3);

    void setStatusNumbered(String var1);

    void incrementBar();

    void reset();

    int getBar();

    void setBar(int var1);

    int getMax();

    void setMax(int var1);

    void pause(boolean var1);

    boolean paused();

    void done();
}
