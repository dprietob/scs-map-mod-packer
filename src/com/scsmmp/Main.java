package com.scsmmp;

import com.scsmmp.gui.MainFrame;

import javax.swing.*;

public class Main
{
    public static void main(String[] args)
    {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            MainFrame gui = new MainFrame();
            gui.build();
        } catch (Exception e) {
            // handle exception
        }
    }
}
