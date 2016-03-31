

import java.io.IOException;


/**
 * 
 * @author Aditya Kasturi, Ketki Trimukhe, Parth Sawant
 * 
 */
public interface SerializablePacket {
    /**
     * Serialize a packet by returning the raw binary representation of the packet
     * @return Byte[] holding data which will be sent over the network
     * @throws IOException
     */
    public byte[] serializePacket() throws IOException;

    /**
     * Deserialize a packet
     * @param data Data from the network
     * @return TFTPPacket instance holding data readed into the raw binary DatagramPacket
     * @throws TftpFormatException If raw data is malformed
     * @throws IOException
     */
    public Packet deserializePacket(byte[] data) throws TftpFormatException, IOException;
}
