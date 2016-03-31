


import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.nio.charset.Charset;


/**
 * 
 * @author Aditya Kasturi, Ketki Trimukhe, Parth Sawant
 * 
 */
public class ErrorPacket extends Packet {

    private short errno;
    private String errorMsg;

    public ErrorPacket() {
        super(TftpOpCodesEnum.ERROR);
    }

    public ErrorPacket(short errno, String errorMsg){
        super(TftpOpCodesEnum.ERROR);

        this.errno = errno;
        this.errorMsg = errorMsg;
    }

    public short getErrno() {
        return errno;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    @Override
    protected void fillRawPacket(DataOutputStream out) throws IOException {
        out.writeShort(opcode);
        out.writeShort(errno);
        out.write(errorMsg.getBytes(Charset.forName("ASCII")));
        out.write(0);
    }

    @Override
    public Packet deserializePacket(byte[] data) throws TftpFormatException, IOException {
        DataInputStream in = new DataInputStream(new ByteArrayInputStream(data));

        if(in.readShort() != TftpOpCodesEnum.ERROR) throw new TftpFormatException("Wrong opcode");

        errno = in.readShort();

        byte[] errorMsgRaw = new byte[data.length - 5]; // [Op, errno, errorMsg, 0]

        System.arraycopy(data, 4, errorMsgRaw, 0, errorMsgRaw.length);
        errorMsg = new String(errorMsgRaw, Charset.forName("ASCII"));

        in.close();

        return this;
    }

    @Override
    public String toString() {
        return "ErrorPacket{" +
                "errno=" + errno +
                ", errorMsg='" + errorMsg + '\'' +
                '}';
    }
}
