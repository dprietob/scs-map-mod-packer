package com.scsmmp.gui;

import javax.swing.*;
import java.awt.*;

public class AboutDialog
{
    public static void show(JFrame parent, String name, String version)
    {
        JDialog aboutDialog = new JDialog();
        aboutDialog.getContentPane().add(getMainPane(name, version));
        aboutDialog.pack();
        aboutDialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        aboutDialog.setLocationRelativeTo(parent);
        aboutDialog.setTitle("About " + name + " v" + version);
        aboutDialog.setResizable(false);
        aboutDialog.setModal(true);
        aboutDialog.setVisible(true);
    }

    private static JPanel getMainPane(String name, String version)
    {
        JEditorPane epHelp = new JEditorPane();
        epHelp.setContentType("text/html");
        epHelp.putClientProperty(JEditorPane.HONOR_DISPLAY_PROPERTIES, Boolean.TRUE);
        epHelp.setFont(new JLabel().getFont());
        epHelp.setEditable(false);
        epHelp.setText(
                "<h2>" + name + " v" + version + "</h2>" +
                "This tiny tool allows automate the process of packaging files for a mod created through ETS2 map<br>" +
                "editor and probably ATS (not tested) creating a single .scs file ready to be activated in the game.<br><br>" +
                "<b>Author: Daniel Prieto</b><br><br>" +
                "Start, abort, help and package icons by <b>FatCow Farm-fresh</b>: " +
                "<font color=\"blue\">http://www.fatcow.com/free-icons)</font>.<br>" +
                "User manual: <font color=\"blue\">https://github.com/dprietob/SCS-Map-Mod-Packer/wiki</font>."
        );

        JPanel mainPane = new JPanel();
        mainPane.setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.gridheight = 1;
        gbc.weightx = 0;
        gbc.gridx = 0;
        gbc.gridy = 0;

        mainPane.add(epHelp, gbc);

        return mainPane;
    }
}
