

import java.io.File;
import java.util.LinkedList;
import java.util.List;

/**
 * 
 * @author Aditya Kasturi, Ketki Trimukhe, Parth Sawant
 * 
 */
public abstract class TftpObservable {
    private List<TftpObserver> observers;

    public TftpObservable(){
        observers = new LinkedList<TftpObserver>();
    }

    public void addObserver(TftpObserver observer){
        observers.add(observer);
    }

    public void removeObserver(TftpObserver observer){
        observers.remove(observer);
    }

    public void removeObservers(){
        observers.clear();
    }

    protected void fireFileReceptionStarted(String remoteFileName){
        for(TftpObserver o : observers){
           o.onFileReceptionStarted(remoteFileName);
        }
    }

    protected void fireFileReceptionEnded(final Client client,final File holder){
        for(TftpObserver o : observers){
            o.onFileReceptionEnded(client, holder);
        }
    }

    protected void fireFileSendingStarted(File sourceFile){
        for(TftpObserver o : observers){
            o.onFileSendingStarted(sourceFile);
        }
    }

    protected void fireFileSendingProgress(float percent){
        for(TftpObserver o : observers){
            o.onFileSendingProgress(percent);
        }
    }

    protected void fireFileSendingEnded(final Client client, final File sourceFile){
        for(TftpObserver o : observers){
            o.onFileSendingEnded(client, sourceFile);
        }
    }

    protected void fireExceptionOccurred(final Client client, final Exception t){
        for(TftpObserver o : observers){
            o.onExceptionOccured(client, t);
        }
    }

    protected void fireProtocolErrorOccurred(final Client client, int errno, String errorMsg){
        for(TftpObserver o : observers){
            o.onProtocolError(client, errno, errorMsg);
        }
    }
}
