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

import com.scsmmp.interfaces.FileChooserListener;

import javax.swing.*;
import java.io.File;

/**
 * Manages the file chooser dialog.
 *
 * @author Daniel Prieto
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
