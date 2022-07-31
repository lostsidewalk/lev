package lev.gui;

import javax.swing.*;
import java.util.HashMap;
import java.util.Map;

public abstract class LSwingWorker<T, E> extends SwingWorker<T, E> {
    static final Map<Class<?>, LSwingWorker<?, ?>> workers = new HashMap<>();

    public LSwingWorker(boolean singleton) {
        if (singleton && workers.containsKey(this.getClass())) {
            LSwingWorker<?, ?> current = workers.get(this.getClass());
            if (!current.isCancelled() && current.isDone()) {
                current.cancel(true);
            }
        }

        workers.put(this.getClass(), this);
    }
}
