package com.scsmmp.gui;

import javax.swing.*;

// TODO: start, abort and help icons by FatCow Farm-fresh (http://www.fatcow.com/free-icons)
public class AboutDialog
{
    public static void show(JFrame parent)
    {
        JPanel mainPane = new JPanel();

        JDialog aboutDialog = new JDialog();
        aboutDialog.getContentPane().add(mainPane);
        aboutDialog.pack();
        aboutDialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        aboutDialog.setLocationRelativeTo(parent);
        aboutDialog.setTitle("About SCS Map Mod Packer");
        aboutDialog.setResizable(false);
        aboutDialog.setModal(true);
        aboutDialog.setVisible(true);
    }
}
