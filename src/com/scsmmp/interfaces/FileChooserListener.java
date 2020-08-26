package com.scsmmp.interfaces;

/**
 * Interface that defines the listener when a directory is selected
 * through the FileChooserDialog class.
 *
 * @author Daniel Prieto
 * @version 1.0.0
 * @since 2020-08-26
 */
public interface FileChooserListener
{
    void onDirectoryChoosed(String directoryPath);
}
