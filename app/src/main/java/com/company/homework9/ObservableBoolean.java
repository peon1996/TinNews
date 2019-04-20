package com.company.homework9;

import java.io.Serializable;
import java.util.Observable;

public class ObservableBoolean extends Observable implements Serializable {

    private boolean flag;

    public boolean getValue() {
        return flag;
    }

    public void setValue(boolean value) {
        this.flag = value;
        this.setChanged();
        this.notifyObservers(value);
    }
}
