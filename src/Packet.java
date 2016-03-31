/**
 * 
 * @author Aditya Kasturi, Ketki Trimukhe, Parth Sawant
 * 
 */
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
/**
 * Holder for basic information about TFTPPacket. 
 * All derivate packet have to subclass TFTPPacket.
 * 
 */
public abstract class Packet implements SerializablePacket{

    protected short opcode;

    public Packet(short opcode){
        this.opcode = opcode;
    }

    /**
     * Return the opcode flag of this packet
     * @return TFTP Opcode
     */
    public int getOpcode() {
        return opcode;
    }
    
    /**
     * 
     * The method which is used to serialize
     * 
     */
    @Override
    public byte[] serializePacket() throws IOException {
        final ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        final DataOutputStream out = new DataOutputStream(bytes);
        fillRawPacket(out);
        final byte[] raw = bytes.toByteArray();
        out.close();
        return raw;
    }

    /**
     * 
     * Abstract method to define what a packet has to write into the output
     * 
     * @param out Output buffer
     * @throws IOException
     */
    protected abstract void fillRawPacket(DataOutputStream out) throws IOException;

    /**
     *  The TFTP to string which is used to convert 
     *  the code into TFTP Code
     *  
     */
    @Override
    public String toString() {
    	
    	/**
    	 * Modified the toString Method
    	 * 
    	 */
    	return "TFTPPacket{" 
        	   +"opcode="
        	   + opcode
        	   + '}';
    }
    
}
