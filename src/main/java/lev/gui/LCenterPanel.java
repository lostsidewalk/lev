package lev.gui;

import java.awt.*;
import java.util.ArrayList;
import java.util.Iterator;

public class LCenterPanel extends LPanel {
    protected ArrayList<Component> components = new ArrayList<>();

    public LCenterPanel() {
    }

    public void Add(Component input) {
        super.Add(input);
        this.components.add(input);
    }

    public void setSize(int x, int y) {
        super.setSize(x, y);
        int height = 0;
        this.align(Align.Center);

        Iterator<Component> i$;
        Component c;
        for (i$ = this.components.iterator(); i$.hasNext(); height += c.getHeight() + this.spacing) {
            c = i$.next();
        }

        this.last.y = y / 2 - height / 2;
        this.last.x = x / 2;
        i$ = this.components.iterator();

        while (i$.hasNext()) {
            c = i$.next();
            this.setPlacement(c);
        }
    }
}
