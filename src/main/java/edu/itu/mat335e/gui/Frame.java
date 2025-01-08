package edu.itu.mat335e.gui;

import javax.swing.*;

public abstract class Frame extends JFrame{
    public Frame() {
        setFrameSettings();
        initialize();
        addSettings();
        addListeners();
        setLayout();
        addComponents();
    }

    public abstract void setFrameSettings();
    public abstract void initialize();
    public abstract void addSettings();
    public abstract void addListeners();
    public abstract void setLayout();
    public abstract void addComponents();
}
