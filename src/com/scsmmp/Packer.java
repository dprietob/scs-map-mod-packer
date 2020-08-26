package com.scsmmp;

import com.scsmmp.interfaces.ProcessUpdaterListener;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.zip.Deflater;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class Packer
{
    private ProcessUpdaterListener listener;
    private PackerThread packerThread;

    public Packer(ProcessUpdaterListener pulListener)
    {
        listener = pulListener;
    }

    public void wrap(Mod mod)
    {
        packerThread = new PackerThread(mod);
        packerThread.start();
    }

    public void abort()
    {
        if (packerThread != null) {
            packerThread.abort();
        }
    }

    private class PackerThread extends Thread implements Runnable
    {
        private Mod mod;
        private ZipOutputStream zos;
        private int totalFiles;
        private int filesProcessed;
        private boolean isExecuting;

        private PackerThread(Mod mMod)
        {
            mod = mMod;
            totalFiles = mMod.getSecFileList().size() + 1;
            filesProcessed = 0;
            isExecuting = false;
        }

        public void run()
        {
            try {
                isExecuting = true;

                if (mod.isCreateBackup() && !createOldFileBackup()) {
                    listener.onNotifyError("An error occurred while trying to backup the old .scs file.");
                    isExecuting = false;
                }

                if (isExecuting) {
                    zos = new ZipOutputStream(new FileOutputStream(mod.getOutputPath()));
                    zos.setLevel(Deflater.NO_COMPRESSION);
                    zos.setMethod(Deflater.DEFLATED);

                    for (File secFile : mod.getSecFileList()) {
                        if (!isExecuting) {
                            break;
                        }
                        processFile(secFile, mod.getSecScsDir());
                    }
                    processFile(mod.getMbdFile(), mod.getMapScsDir());
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

        private boolean createOldFileBackup()
        {
            File oldScsFile = new File(mod.getOutputPath());

            if (oldScsFile.exists()) {
                return oldScsFile.renameTo(new File(oldScsFile.getAbsolutePath() + getBackupExt()));
            }

            return true;
        }

        private String getBackupExt()
        {
            return "_" + new Timestamp(System.currentTimeMillis()).toInstant().toEpochMilli() + ".old";
        }

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

        private int getPercentageProgress()
        {
            return (filesProcessed * 100) / totalFiles;
        }

        private void abort()
        {
            isExecuting = false;
        }
    }
}
