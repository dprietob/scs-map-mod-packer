/*
 * Copyright (C) 2020 The SCSMMP Author
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.scsmmp.gui;

import javax.swing.*;
import java.awt.*;

/**
 * Manages the about dialog.
 *
 * @author Daniel Prieto
 */
public class AboutDialog
{
    /**
     * Shows the about dialog.
     *
     * @param parent
     * @param name
     * @param version
     */
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

    /**
     * Obtains the main info pane.
     *
     * @param name
     * @param version
     * @return
     */
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
                "How to use: <font color=\"blue\">https://github.com/dprietob/SCS-Map-Mod-Packer/blob/master/README.md</font>."
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
