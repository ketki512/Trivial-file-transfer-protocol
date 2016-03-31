import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

/**
 * 
 * @author Aditya Kasturi, Ketki Trimukhe, Parth Sawant
 * 
 */
public class DataPacket extends Packet implements PacketInterface {

	private short blockNumber;
	private byte[] data;
	private int offset;
	private int length;

	public DataPacket() {
		super(TftpOpCodesEnum.DATA);
	}

	public DataPacket(short blockNumber, byte[] data, int offset, int length) {
		super(TftpOpCodesEnum.DATA);
		this.blockNumber = blockNumber;
		this.data = data;
		this.offset = offset;
		this.length = length;
	}

	public int getLength() {
		return length;
	}

	public void setLength(int length) {
		this.length = length;
	}

	public byte[] getData() {
		return data;
	}

	@Override
	public short getBlockNumber() {
		return blockNumber;
	}

	@Override

	public Packet deserializePacket(byte[] data) throws TftpFormatException, IOException {
		DataInputStream in = new DataInputStream(new ByteArrayInputStream(data));

		if (in.readShort() != TftpOpCodesEnum.DATA)
			throw new TftpFormatException("Wrong opcode");

		blockNumber = in.readShort();
		this.data = new byte[data.length - 4]; // 4 first bytes (opcode /
												// blockNumber / data)

		System.arraycopy(data, 4, this.data, 0, length);

		in.close();

		return this;
	}

	@Override
	protected void fillRawPacket(DataOutputStream out) throws IOException {
		out.writeShort(opcode);
		out.writeShort(blockNumber);
		out.write(data, offset, length);
	}

	@Override
	public String toString() {
		return "DataPacket{" + "blockNumber=" + blockNumber + ", length=" + length + '}';
	}
}
