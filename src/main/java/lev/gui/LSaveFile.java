package lev.gui;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

@SuppressWarnings("unused")
public abstract class LSaveFile {
    protected File location;
    protected ArrayList<Map<Enum<?>, Setting<?>>> maps;
    protected Map<Enum<?>, Setting<?>> defaultSettings;
    protected Map<Enum<?>, Setting<?>> saveSettings;
    protected Map<Enum<?>, Setting<?>> curSettings;
    protected Map<Enum<?>, Setting<?>> cancelSave;
    protected Map<Enum<?>, Setting<?>> peekSave;
    protected Map<Enum<?>, String> helpInfo;
    protected boolean initialized;

    private LSaveFile() {
        this.maps = new ArrayList<>();
        this.defaultSettings = new TreeMap<>();
        this.saveSettings = new TreeMap<>();
        this.curSettings = new TreeMap<>();
        this.cancelSave = new TreeMap<>();
        this.peekSave = new TreeMap<>();
        this.helpInfo = new TreeMap<>();
        this.initialized = false;
        this.maps.add(this.defaultSettings);
        this.maps.add(this.saveSettings);
        this.maps.add(this.curSettings);
        this.maps.add(this.peekSave);
    }

    public LSaveFile(File location) {
        this();
        this.location = location;
    }

    public LSaveFile(String location) {
        this(new File(location));
    }

    public static void copyTo(Map<Enum<?>, Setting<?>> from, Map<Enum<?>, Setting<?>> to) {
        to.clear();

        for (Enum<?> s : from.keySet()) {
            to.put(s, from.get(s).copyOf());
        }
    }

    public void tie(Enum<?> s, LUserSetting<?> c) {
        for (Map<Enum<?>, Setting<?>> e : this.maps) {
            if (e.containsKey(s)) {
                e.get(s).tie(c);
            }
        }
    }

    public void init() {
        if (!this.initialized) {
            this.initSettings();
            this.initHelp();
            this.readInSettings();
            this.initialized = true;
        }

    }

    protected abstract void initSettings();

    protected abstract void initHelp();

    void readInSettings() {
        File f = new File(this.location.getPath());
        if (f.exists()) {
            try {
                BufferedReader input = new BufferedReader(new FileReader(f));
                String version = input.readLine();
                if (!version.contains("Version")) {
                    this.readInSettingsV1(input);
                } else {
                    //noinspection LoopStatementThatDoesntLoop
                    while (true) {
                        while (true) {
                            StringBuilder inStr;
                            do {
                                if (!input.ready()) {
                                    return;
                                }

                                inStr = new StringBuilder(input.readLine().trim());
                            } while (inStr.toString().equals(""));

                            String settingTitle = inStr.substring(0, inStr.indexOf(": "));
                            inStr = new StringBuilder(inStr.substring(inStr.indexOf(": ") + 2));

                            for (Enum<?> anEnum : this.saveSettings.keySet()) {
                                if (this.saveSettings.containsKey(anEnum) && this.saveSettings.get(anEnum).getTitle().equals(settingTitle)) {
                                    if (this.saveSettings.get(anEnum).getClass() == SaveStringList.class) {
                                        int num = Integer.parseInt(inStr.toString().trim());
                                        inStr = new StringBuilder();

                                        for (int i = 0; i < num; ++i) {
                                            inStr.append(input.readLine());
                                        }
                                    }

                                    this.saveSettings.get(anEnum).readSetting(inStr.toString());
                                    this.curSettings.get(anEnum).readSetting(inStr.toString());
                                    break;
                                }
                            }
                        }
                    }
                }
            } catch (Exception var10) {
                JOptionPane.showMessageDialog(null, "Error in reading in save file. Reverting to default settings.");
                this.initSettings();
                this.initHelp();
                this.initialized = true;
            }
        }

    }

    void readInSettingsV1(BufferedReader input) throws IOException {
        label43:
        while (input.ready()) {
            StringBuilder inStr = new StringBuilder(input.readLine().trim());
            if (!inStr.toString().equals("")) {
                String settingTitle = inStr.substring(4, inStr.indexOf(" to "));
                inStr = new StringBuilder(inStr.substring(inStr.indexOf(" to ") + 4));
                Iterator<?> i$ = this.saveSettings.keySet().iterator();

                while (true) {
                    Enum<?> s;
                    do {
                        do {
                            if (!i$.hasNext()) {
                                continue label43;
                            }

                            s = (Enum<?>) i$.next();
                        } while (!this.saveSettings.containsKey(s));
                    } while (!this.saveSettings.get(s).getTitle().equals(settingTitle));

                    if (this.saveSettings.get(s).getClass() == SaveStringList.class) {
                        int num = Integer.parseInt(inStr.toString().trim());
                        inStr = new StringBuilder();

                        for (int i = 0; i < num; ++i) {
                            inStr.append(input.readLine());
                        }
                    }

                    this.saveSettings.get(s).readSetting(inStr.toString());
                    this.curSettings.get(s).readSetting(inStr.toString());
                }
            }
        }

    }

    public void saveToFile() {
        File f = this.location;
        File dir = f.getParentFile();
        if (!dir.isDirectory()) {
            //noinspection ResultOfMethodCallIgnored
            dir.mkdirs();
        }

        if (f.isFile()) {
            //noinspection ResultOfMethodCallIgnored
            f.delete();
        }

        try {
            BufferedWriter output = new BufferedWriter(new FileWriter(f));
            output.write("### Savefile used for the application.  Version: 2\n");

            for (Enum<?> s : this.curSettings.keySet()) {
                if (!this.curSettings.get(s).get().equals("")) {
                    this.curSettings.get(s).write(output);
                } else {
                    this.defaultSettings.get(s).write(output);
                }
            }

            output.close();
        } catch (IOException var6) {
            JOptionPane.showMessageDialog(null, "The application couldn't open the save file output stream.  Your settings were not saved.");
        }

    }

    void Add(Enum<?> type, Setting<?> s) {

        for (Map<Enum<?>, Setting<?>> m : this.maps) {
            m.put(type, s.copyOf());
        }
    }

    public void Add(Enum<?> type, Boolean b, Boolean... extraFlags) {
        this.Add(type, new SaveBool(type.toString(), b, extraFlags));
    }

    public void Add(Enum<?> type, String s, Boolean... extraFlags) {
        this.Add(type, new SaveString(type.toString(), s, extraFlags));
    }

    public void Add(Enum<?> type, Integer i, Boolean... extraFlags) {
        this.Add(type, new SaveInt(type.toString(), i, extraFlags));
    }

    public void Add(Enum<?> type, Enum<?> e, Boolean... extraFlags) {
        this.Add(type, new SaveEnum(type.toString(), e, extraFlags));
    }

    public void Add(Enum<?> type, ArrayList<String> strs, Boolean... extraFlags) {
        this.Add(type, new SaveStringList(type.toString(), strs, extraFlags));
    }

    public void Add(Enum<?> type, Float f, Boolean... extraFlags) {
        this.Add(type, new SaveFloat(type.toString(), f, extraFlags));
    }

    public void Add(Enum<?> type, Color c, Boolean... extraFlags) {
        this.Add(type, new SaveColor(type.toString(), c, extraFlags));
    }

    public void Add(Enum<?> type, Double d, Boolean... extraFlags) {
        this.Add(type, new SaveDouble(type.toString(), d, extraFlags));
    }

    public void updateCurToGUI() {
        for (Enum<?> s : this.curSettings.keySet()) {
            this.updateCurToGUI(s);
        }
    }

    public void updateCurToGUI(Enum<?> s) {
        this.curSettings.get(s).set();
    }

    public void updateGUItoCur() {
        this.revertTo(this.curSettings);
    }

    void set(Enum<?> setting, Object in) {
        this.curSettings.get(setting).setTo(in);
    }

    public void peekSaved() {
        this.peek(this.saveSettings);
    }

    public void peekDefaults() {
        this.peek(this.defaultSettings);
    }

    public void clearPeek() {
        for (Setting<?> s : this.curSettings.values()) {
            if (s.tie != null) {
                s.tie.revertTo(this.peekSave);
                s.tie.clearHighlight();
            }
        }

        this.updateCurToGUI();
    }

    void peek(Map<Enum<?>, Setting<?>> in) {
        copyTo(this.curSettings, this.peekSave);

        for (Setting<?> s : this.curSettings.values()) {
            if (s.tie != null && !s.tie.revertTo(in)) {
                s.tie.highlightChanged();
            }
        }

        this.updateCurToGUI();
    }

    public void saveToCancelSave() {
        copyTo(this.curSettings, this.cancelSave);
    }

    public void revertToCancel() {
        copyTo(this.cancelSave, this.curSettings);
        this.updateGUItoCur();
    }

    public void revertTo(Map<Enum<?>, Setting<?>> in) {

        for (Setting<?> setting : this.curSettings.values()) {
            if (setting.tie != null) {
                setting.tie.revertTo(in);
            }
        }
    }

    public void revertToSaved(LUserSetting<?> s) {
        this.revertTo(this.saveSettings, s);
    }

    public void revertToDefault(LUserSetting<?> s) {
        this.revertTo(this.defaultSettings, s);
    }

    public void revertToSaved(Enum<?> setting) {
        this.revertTo(this.saveSettings, this.curSettings.get(setting).tie);
    }

    public void revertToDefault(Enum<?> setting) {
        this.revertTo(this.defaultSettings, this.curSettings.get(setting).tie);
    }

    void revertTo(Map<Enum<?>, Setting<?>> in, LUserSetting<?> s) {
        s.revertTo(in);
        copyTo(in, this.peekSave);
    }

    public boolean checkFlagAnd(int index) {
        ArrayList<Setting<?>> modified = this.getModifiedSettings();
        Iterator<Setting<?>> i$ = modified.iterator();

        Setting<?> s;
        do {
            if (!i$.hasNext()) {
                return true;
            }

            s = i$.next();
        } while (s.extraFlags[index]);

        return false;
    }

    public boolean checkFlagOr(int index) {
        ArrayList<Setting<?>> modified = this.getModifiedSettings();
        Iterator<Setting<?>> i$ = modified.iterator();

        Setting<?> s;
        do {
            if (!i$.hasNext()) {
                return false;
            }

            s = i$.next();
        } while (!s.extraFlags[index]);

        return true;
    }

    public boolean checkFlag(Enum<?> s, int index) {
        return this.curSettings.get(s).extraFlags[index];
    }

    public ArrayList<Setting<?>> getModifiedSettings() {
        return this.getDiff(this.saveSettings, this.curSettings);
    }

    public ArrayList<Setting<?>> getDiff(Map<Enum<?>, Setting<?>> lhs, Map<Enum<?>, Setting<?>> rhs) {
        ArrayList<Setting<?>> out = new ArrayList<>();

        for (Enum<?> e : lhs.keySet()) {
            if (!lhs.get(e).equals(rhs.get(e))) {
                out.add(rhs.get(e));
            }
        }

        return out;
    }

    public String getStr(Enum<?> s) {
        return this.curSettings.get(s).getStr();
    }

    public Integer getInt(Enum<?> s) {
        return this.curSettings.get(s).getInt();
    }

    public Boolean getBool(Enum<?> s) {
        return this.curSettings.get(s).getBool();
    }

    public Color getColor(Enum<?> s) {
        return this.curSettings.get(s).getColor();
    }

    public Enum<?> getEnum(Enum<?> s) {
        return this.curSettings.get(s).getEnum();
    }

    public float getFloat(Enum<?> s) {
        return this.curSettings.get(s).getFloat();
    }

    public ArrayList<String> getStrings(Enum<?> s) {
        return new ArrayList<>(this.curSettings.get(s).getStrings());
    }

    public void setStr(Enum<?> e, String s) {
        this.curSettings.get(e).setTo(s);
    }

    public void setInt(Enum<?> e, int i) {
        this.curSettings.get(e).setTo(i);
    }

    public void setColor(Enum<?> e, Color c) {
        this.curSettings.get(e).setTo(c);
    }

    public void setBool(Enum<?> e, boolean b) {
        this.curSettings.get(e).setTo(b);
    }

    public void setStrings(Enum<?> e, ArrayList<String> strs) {
        this.curSettings.get(e).setTo(strs);
    }

    public void addString(Enum<?> e, String s) {
        this.curSettings.get(e).getStrings().add(s);
    }

    public void removeString(Enum<?> e, String s) {
        this.curSettings.get(e).getStrings().remove(s);
    }
}
