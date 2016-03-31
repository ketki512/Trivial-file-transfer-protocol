import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
/**
 * AckPacket.java
 * 
 * The Class implements the Acknowledgement Packet Structure by extending the 
 * super class Packet.
 * 
 * 
 * 
 * @author Aditya Kasturi, Ketki Trimukhe, Parth Sawant
 * 
 */
public class AckPacket extends Packet {

    private short blockID;

    public AckPacket() {
        super(TftpOpCodesEnum.ACK);
    }

    public AckPacket(short blockID){
        super(TftpOpCodesEnum.ACK);
        this.blockID = blockID;
    }

    @Override
    protected void fillRawPacket(DataOutputStream out) throws IOException {
        out.writeShort(opcode);
        out.writeShort(blockID);
    }
    
    
    /**
     * Deserialize a Packet 
     * 
     * @throws TftpFormatException and IOException
     */
    @Override
    public Packet deserializePacket(byte[] data) throws TftpFormatException, IOException {
        DataInputStream in = new DataInputStream(new ByteArrayInputStream(data));
        //Return if any wrong IPCode
        if(in.readShort() != TftpOpCodesEnum.ACK) throw new TftpFormatException("Wrong opcode");
        blockID = in.readShort();

        in.close();
        return this;
    }

    public short getBlockID() {
        return blockID;
    }

    @Override
    public String toString() {
        return "AckPacket{" +
                "blockID=" +
        		 blockID +
                '}';
    }
}
