import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
/**
 * 
 * @author Aditya Kasturi, Ketki Trimukhe, Parth Sawant
 * 
 */
public class WRQPacket extends Packet{

    private File localStorage;
    private String storageName;
    private TftpIOEnum mode;

    public WRQPacket() {
        super(TftpOpCodesEnum.WRQ);
    }

    public WRQPacket(File localStorage, String storageName, TftpIOEnum mode) {
        super(TftpOpCodesEnum.WRQ);
        this.localStorage = localStorage;
        this.storageName = storageName;
        this.mode = mode;
    }

    public File getLocalStorage(){return localStorage; }

    public String getStorageName() {
        return storageName;
    }

    public TftpIOEnum getMode() {
        return mode;
    }

    @Override
    public Packet deserializePacket(byte[] data) {
        //Cannot be sent by server
        return this;
    }

    @Override
    protected void fillRawPacket(DataOutputStream out) throws IOException {
        out.writeShort(opcode);
        out.writeBytes(storageName);
        out.writeByte(0);
        out.writeBytes(mode.getValue());
        out.writeByte(0);
    }

    @Override
    public String toString() {
        return "WRQPacket{" +
                "localStorage=" + localStorage +
                ", storageName='" + storageName + '\'' +
                ", mode=" + mode +
                '}';
    }
}
