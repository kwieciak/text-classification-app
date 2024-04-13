package org.example.project1.gui;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class ConfusionMatrixRow {
    private final SimpleStringProperty actualClass;
    private final SimpleIntegerProperty japan;
    private final SimpleIntegerProperty germany;
    private final SimpleIntegerProperty uk;
    private final SimpleIntegerProperty usa;
    private final SimpleIntegerProperty canada;
    private final SimpleIntegerProperty france;

    public ConfusionMatrixRow(String actualClass, int japan, int germany, int uk, int usa, int canada, int france) {
        this.actualClass = new SimpleStringProperty(actualClass);
        this.japan = new SimpleIntegerProperty(japan);
        this.germany = new SimpleIntegerProperty(germany);
        this.uk = new SimpleIntegerProperty(uk);
        this.usa = new SimpleIntegerProperty(usa);
        this.canada = new SimpleIntegerProperty(canada);
        this.france = new SimpleIntegerProperty(france);
    }

    // getters and setters
}
