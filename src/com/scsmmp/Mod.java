package com.scsmmp;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Mod
{
    private String name;
    private String inputPath;
    private String outputPath;
    private boolean overwrite;
    private File mbdFile;
    private List<File> secFiles;

    public Mod(String sName, String sInputPath, String sOutputPath)
    {
        name = sName.trim();
        inputPath = sInputPath.trim();
        outputPath = sOutputPath.trim();
        overwrite = false;
    }

    public boolean isOverwrite()
    {
        return overwrite;
    }

    public void setOverwrite(boolean bOverwrite)
    {
        overwrite = bOverwrite;
    }

    public String getSecScsDir()
    {
        return "map" + File.separator + name + File.separator;
    }

    public String getMapScsDir()
    {
        return "map" + File.separator;
    }

    public String getOutputPath()
    {
        return outputPath + File.separator + name + ".scs";
    }

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
