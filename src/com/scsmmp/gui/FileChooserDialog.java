package com.scsmmp.gui;

import com.scsmmp.interfaces.FileChooserListener;

import javax.swing.*;
import java.io.File;

public class FileChooserDialog
{
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
