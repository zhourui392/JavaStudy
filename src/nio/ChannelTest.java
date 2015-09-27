package nio;

import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * channel
 * @author zhourui <zhourui0125@gmail.com>
 *
 */
public class ChannelTest {
	public static void main(String[] args) throws Exception {
		String basePath = ChannelTest.class.getClassLoader().getResource(".").getPath();
		RandomAccessFile aFile = new RandomAccessFile(basePath + "/data/nio-data.txt", "rw");
		FileChannel inchannel = aFile.getChannel();
		
		ByteBuffer buf = ByteBuffer.allocate(48);
		
		int byteRead = inchannel.read(buf);
		while( byteRead != -1){
			System.out.println("read "+byteRead);
			buf.flip();
			
			while(buf.hasRemaining()){
				System.out.print( (char) buf.get() );
			}
			buf.clear();
			System.out.println();
			byteRead = inchannel.read(buf);
		}
		aFile.close();
	}
}
