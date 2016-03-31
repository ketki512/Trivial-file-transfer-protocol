


/**
 * 
 * @author Aditya Kasturi, Ketki Trimukhe, Parth Sawant
 * 
 */
public class TftpPacketFactory {

    public static Packet fromOPCode(short opcode){
        switch(opcode){
            case 1 : return new RRQPacket();
            case 2 : return new WRQPacket();
            case 3 : return new DataPacket();
            case 4 : return new AckPacket();
            case 5 : return new ErrorPacket();
        }

        return null;
    }

    public static Packet fromRawData(byte[] data){
        assert(data.length > 2): "Not enough data";

        byte lower = data[0];
        byte upper = data[1];

        short opcode = (short) (lower + upper); // opcode start from 1 to 5, so one of the two first byte is equal to 0, the other, equals the opcode value

        return fromOPCode(opcode);
    }
}
