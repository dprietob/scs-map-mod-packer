package com.scsmmp;

import com.scsmmp.gui.MainFrame;

import javax.swing.*;

/**
 * Main class that runs the application.
 *
 * @author Daniel Prieto
 * @version 1.0.0
 * @since 2020-08-26
 */
public class Main
{
    private static final String NAME = "SCS Map Mod Packer";
    private static final String VERSION = "1.0.0";

    /**
     * Runs the application.
     *
     * @param args
     */
    public static void main(String[] args)
    {
        if (args.length > 0) {
            Terminal terminal = new Terminal(args, NAME, VERSION);
            terminal.start();
        } else {
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
}
