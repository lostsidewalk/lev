package lev.gui;

import java.awt.*;
import java.io.BufferedWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

public abstract class Setting<T> {
    protected T data;
    protected String title;
    protected LUserSetting<T> tie;
    protected Boolean[] extraFlags;

    public Setting(String title_, T data_, Boolean[] extraFlags) {
        this(title_, extraFlags);
        this.setTo(data_);
    }

    public Setting(String title_, Boolean[] extraFlags) {
        this.title = title_;
        this.extraFlags = extraFlags;
    }

    public T get() {
        return this.data;
    }

    public Boolean getBool() {
        return (Boolean) this.data;
    }

    public Color getColor() {
        return (Color) this.data;
    }

    public Enum getEnum() {
        return (Enum) this.data;
    }

    public Float getFloat() {
        return (Float) this.data;
    }

    public Double getDouble() {
        return (Double) this.data;
    }

    public Integer getInt() {
        return (Integer) this.data;
    }

    public ArrayList<String> getStrings() {
        return (ArrayList) this.data;
    }

    public String getStr() {
        return this.data.toString();
    }

    public Object getData() {
        return this.data;
    }

    public String getTitle() {
        return this.title;
    }

    void tie(LUserSetting c) {
        this.tie = c;
    }

    public void set() {
        if (this.tie != null) {
            this.setTo(this.tie.getValue());
        }

    }

//    public final void setTo(T input) {
//        this.data = input;
//    }

    public final void setTo(Object input) {
        this.data = (T) input;
    }

    public void write(BufferedWriter b) throws IOException {
        b.write(this.title + ": " + this + "\n");
    }

    public void readSetting(String input) throws IOException {
        this.parse(input.trim());
    }

    public abstract void parse(String var1);

    public Boolean isEmpty() {
        return this.data == null;
    }

    public abstract Setting<T> copyOf();

    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        } else if (this.getClass() != obj.getClass()) {
            return false;
        } else {
            Setting<T> other = (Setting) obj;
            if (!Objects.equals(this.data, other.data)) {
                return false;
            } else if (!Objects.equals(this.title, other.title)) {
                return false;
            } else {
                return Objects.equals(this.extraFlags, other.extraFlags);
            }
        }
    }

    public int hashCode() {
        int hash = 5;
        hash = 73 * hash + Objects.hashCode(this.data);
        hash = 73 * hash + Objects.hashCode(this.title);
        hash = 73 * hash + Objects.hashCode(this.extraFlags);
        return hash;
    }
}
