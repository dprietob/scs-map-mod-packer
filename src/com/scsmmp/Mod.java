package com.scsmmp;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Class that defines the Mod data, like name, folder location, output and backup.
 *
 * @author Daniel Prieto
 * @version 1.0.0
 * @since 2020-08-26
 */
public class Mod
{
    private String name;
    private String inputPath;
    private String outputPath;
    private boolean createBackup;
    private File mbdFile;
    private List<File> secFiles;

    /**
     * Instantiates a new Mod object with the receiving params.
     *
     * @param sName
     * @param sInputPath
     * @param sOutputPath
     */
    public Mod(String sName, String sInputPath, String sOutputPath)
    {
        name = sName.trim();
        inputPath = sInputPath.trim();
        outputPath = sOutputPath.trim();
        createBackup = true;
    }

    /**
     * Instantiates a new Mod object with the receiving params.
     *
     * @param sName
     * @param sInputPath
     * @param sOutputPath
     * @param bCreateBackup
     */
    public Mod(String sName, String sInputPath, String sOutputPath, boolean bCreateBackup)
    {
        name = sName.trim();
        inputPath = sInputPath.trim();
        outputPath = sOutputPath.trim();
        createBackup = bCreateBackup;
    }

    /**
     * Returns true o false whether to make a backup of old .scs files.
     *
     * @return
     */
    public boolean isCreateBackup()
    {
        return createBackup;
    }

    /**
     * Sets whether to make a backup of old .scs files.
     *
     * @param bCreateBackup
     */
    public void setCreateBackup(boolean bCreateBackup)
    {
        createBackup = bCreateBackup;
    }

    /**
     * Returns main mod directory inside mod map directory.
     * It contains all "sec" files.
     *
     * @return
     */
    public String getSecScsDir()
    {
        return "map" + File.separator + name + File.separator;
    }

    /**
     * Returns mod map directory.
     *
     * @return
     */
    public String getMapScsDir()
    {
        return "map" + File.separator;
    }

    /**
     * Returns .scs file output directory.
     *
     * @return
     */
    public String getOutputPath()
    {
        return outputPath + File.separator + name + ".scs";
    }

    /**
     * Returns .mbd mod map file.
     *
     * @return
     */
    public File getMbdFile()
    {
        if (inputPath != null && mbdFile == null) {
            File file = new File(inputPath + File.separator + name + ".mbd");

            if (file.exists()) {
                mbdFile = file;
            }
        }
        return mbdFile;
    }

    /**
     * Returns all "sec" files of mod map directory.
     *
     * @return
     */
    public List<File> getSecFileList()
    {
        if (inputPath != null && secFiles == null) {
            secFiles = new ArrayList<>();
            File secDir = new File(inputPath + File.separator + name);

            if (secDir.isDirectory()) {
                String[] list = secDir.list();

                if (list != null) {
                    for (String filename : list) {
                        secFiles.add(new File(inputPath + File.separator + name + File.separator + filename));
                    }
                }
            }
        }
        return secFiles;
    }
}
