package com.scsmmp;

public interface UpdaterListener
{
    void updateProgress(Integer progress);
    void notifyError();
}
