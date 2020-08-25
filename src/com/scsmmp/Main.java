package com.scsmmp;

import com.scsmmp.gui.MainFrame;

import javax.swing.*;

public class Main
{
    private static final String NAME = "SCS Map Mod Packer";
    private static final String VERSION = "1.0.0";

    public static void main(String[] args)
    {
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Something went wrong while trying set system look and feel.",
                        "Error", JOptionPane.ERROR_MESSAGE);
            }

            MainFrame gui = new MainFrame(NAME, VERSION);
            gui.build();
        });
    }
}
