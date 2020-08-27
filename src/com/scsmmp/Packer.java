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

package com.scsmmp;

import com.scsmmp.interfaces.ProcessUpdaterListener;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.List;
import java.util.zip.Deflater;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * Class that defines the Packer entity, in charge to wrapping the mod.
 *
 * @author Daniel Prieto
 */
public class Packer
{
    private ProcessUpdaterListener listener;
    private PackerThread packerThread;

    /**
     * Instantiates a new Packer object with the receiving params.
     *
     * @param pulListener
     */
    public Packer(ProcessUpdaterListener pulListener)
    {
        listener = pulListener;
    }

    /**
     * Starts a new wrapping process in a new thread.
     *
     * @param mod
     */
    public void wrap(Mod mod)
    {
        packerThread = new PackerThread(mod);
        packerThread.start();
    }

    /**
     * Aborts the current wrapping process.
     */
    public void abort()
    {
        if (packerThread != null) {
            packerThread.abort();
        }
    }

    /**
     * Inner class that defines the PackerThread entity, in charge to handle
     * wrapping process.
     *
     * @author Daniel Prieto
     */
    private class PackerThread extends Thread implements Runnable
    {
        private Mod mod;
        private ZipOutputStream zos;
        private int totalFiles;
        private int filesProcessed;
        private boolean isExecuting;

        /**
         * Instantiates a new PackerThread object with the receiving params.
         *
         * @param mMod
         */
        private PackerThread(Mod mMod)
        {
            mod = mMod;
            totalFiles = mMod.getSecFileList().size() + 1;
            filesProcessed = 0;
            isExecuting = false;
        }

        /**
         * Executes the wrapping process.
         */
        public void run()
        {
            try {
                isExecuting = true;

                if (mod.isCreateBackup() && !createOldFileBackup()) {
                    listener.onNotifyError("An error occurred while trying to backup the old .scs file.");
                    isExecuting = false;
                }

                List<File> secFileList = mod.getSecFileList();
                File mbdFile = mod.getMbdFile();

                if (isExecuting) {
                    if (secFileList.size() > 0) {
                        zos = new ZipOutputStream(new FileOutputStream(mod.getOutputPath()));
                        zos.setLevel(Deflater.NO_COMPRESSION);
                        zos.setMethod(Deflater.DEFLATED);

                        for (File secFile : secFileList) {
                            if (!isExecuting) {
                                break;
                            }
                            processFile(secFile, mod.getSecScsDir());
                        }

                        if (mbdFile != null) {
                            processFile(mod.getMbdFile(), mod.getMapScsDir());
                        } else {
                            listener.onNotifyError("\".mbd\" file is missing.");
                        }
                    } else {
                        listener.onNotifyError("\"Sec\" files directory is empty.");
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();

            } finally {
                if (zos != null) {
                    try {
                        zos.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }

        /**
         * Creates a backup file of the old .scs file at the same
         * output directory.
         *
         * @return
         */
        private boolean createOldFileBackup()
        {
            File oldScsFile = new File(mod.getOutputPath());

            if (oldScsFile.exists()) {
                return oldScsFile.renameTo(new File(oldScsFile.getAbsolutePath() + getBackupExt()));
            }

            return true;
        }

        /**
         * Returns the backup file extension with current time millis.
         *
         * @return
         */
        private String getBackupExt()
        {
            return "_" + new Timestamp(System.currentTimeMillis()).toInstant().toEpochMilli() + ".old";
        }

        /**
         * Processes a mod file, adding it to a .scs final file.
         *
         * @param file
         * @param dir
         */
        private void processFile(File file, String dir)
        {
            if (addFile(file, dir)) {
                filesProcessed++;

                if (listener != null) {
                    listener.onUpdateProgress(getPercentageProgress());
                }
            } else {
                if (listener != null) {
                    listener.onNotifyError("An error occurred while trying to package the file '" + file.getAbsolutePath() + "'");
                }
            }
        }

        /**
         * Adds the current mod file processed to a .scs final file.
         *
         * @param file
         * @param dir
         * @return
         */
        private boolean addFile(File file, String dir)
        {
            try {
                ZipEntry entry = new ZipEntry(dir + file.getName());
                zos.putNextEntry(entry);

                FileInputStream fis = new FileInputStream(file.getPath());
                byte[] buffer = new byte[1024];
                int readed;

                while (0 < (readed = fis.read(buffer))) {
                    zos.write(buffer, 0, readed);
                }

                fis.close();
            } catch (IOException e) {
                e.printStackTrace();
                return false;
            }
            return true;
        }

        /**
         * Returns the current processing percentage.
         *
         * @return
         */
        private int getPercentageProgress()
        {
            return (filesProcessed * 100) / totalFiles;
        }

        /**
         * Aborts the current wrapping process thread.
         */
        private void abort()
        {
            isExecuting = false;
        }
    }
}
