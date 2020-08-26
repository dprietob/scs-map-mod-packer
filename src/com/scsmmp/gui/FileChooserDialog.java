package com.scsmmp.gui;

import com.scsmmp.interfaces.FileChooserListener;

import javax.swing.*;
import java.io.File;

/**
 * Manages the file chooser dialog.
 *
 * @author Daniel Prieto
 * @version 1.0.0
 * @since 2020-08-26
 */
public class FileChooserDialog
{
    /**
     * Shows the file chooser dialog and notifies when a directory
     * is selected.
     *
     * @param listener
     */
    public static void show(FileChooserListener listener)
    {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        fileChooser.setAcceptAllFileFilterUsed(false);

        if (fileChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
            File directorySelected = fileChooser.getSelectedFile();

            if (directorySelected == null) {
                directorySelected = fileChooser.getCurrentDirectory();
            }

            listener.onDirectoryChoosed(directorySelected.getAbsolutePath());
        }
    }
}
