
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;

/**
 * 
 * @author Aditya Kasturi, Ketki Trimukhe, Parth Sawant
 * 
 */
public class RRQPacket extends Packet {

    private File localStorageFile;
    private String remoteFileName;
    private TftpIOEnum mode;

    public RRQPacket(){
        super(TftpOpCodesEnum.RRQ);
    }

    public RRQPacket(File local, String remoteFileName, TftpIOEnum mode) {
        super(TftpOpCodesEnum.RRQ);

        this.localStorageFile = local;
        this.remoteFileName = remoteFileName;
        this.mode = mode;
    }

    public String getRemoteFileName() {
        return remoteFileName;
    }

    public File getLocalStorageFile() {
        return localStorageFile;
    }

    public TftpIOEnum getMode() {
        return mode;
    }

    @Override
    public Packet deserializePacket(byte[] data) throws IOException, TftpFormatException {
        //Cannot be sent by server
        return this;
    }

    @Override
    protected void fillRawPacket(DataOutputStream out) throws IOException {
        out.writeShort(opcode);
        out.writeBytes(remoteFileName);
        out.writeByte(0);
        out.writeBytes(mode.getValue());
        out.writeByte(0);
    }

    @Override
    public String toString() {
        return "RRQPacket{" +
                "remoteFile='" + localStorageFile.getAbsolutePath() + '\'' +
                ", mode=" + mode.name() +
                '}';
    }
}
