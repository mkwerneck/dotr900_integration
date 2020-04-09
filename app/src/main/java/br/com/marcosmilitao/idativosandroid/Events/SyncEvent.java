package br.com.marcosmilitao.idativosandroid.Events;

/**
 * Created by marcoswerneck on 07/10/19.
 */

public class SyncEvent {
    private boolean syncStatus;

    public SyncEvent(boolean syncStatus)
    {
        this.syncStatus = syncStatus;
    }

    public boolean getSyncStatus()
    {
        return syncStatus;
    }
}
