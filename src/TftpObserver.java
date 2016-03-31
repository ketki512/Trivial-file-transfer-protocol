import java.io.File;

/**
 * 
 * @author Aditya Kasturi, Ketki Trimukhe, Parth Sawant
 * 
 */

public interface TftpObserver {

    public void onFileSendingStarted(File sourceFile);
    public void onFileSendingProgress(float percent);
    public void onFileSendingEnded(Client client, File sourceFile);

    public void onFileReceptionStarted(String remoteFileName);
    public void onFileReceptionEnded(Client client, File holder);

    public void onExceptionOccured(Client client, Exception t);
    public void onProtocolError(Client client, int errno, String errorMsg);
}
