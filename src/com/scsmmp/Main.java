package com.scsmmp;

import com.scsmmp.gui.MainFrame;

import javax.swing.*;

public class Main
{
    private static final String NAME = "SCS Map Mod Packer";
    private static final String VERSION = "1.0.0";

    public static void main(String[] args)
    {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            MainFrame gui = new MainFrame(NAME, VERSION);
            gui.build();
        } catch (Exception e) {
            // handle exception
        }
    }
}
