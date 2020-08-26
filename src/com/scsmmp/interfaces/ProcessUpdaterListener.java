package com.scsmmp.interfaces;

/**
 * Interface that defines the wrapping process events: update and error.
 *
 * @author Daniel Prieto
 * @version 1.0.0
 * @since 2020-08-26
 */
public interface ProcessUpdaterListener
{
    void onUpdateProgress(int progress);
    void onNotifyError(String reason);
}
