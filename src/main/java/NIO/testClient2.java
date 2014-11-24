package NIO;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CharsetEncoder;
import java.util.Iterator;

public class testClient2 {
	
public static void main(String[] args) throws IOException {
	
	 Charset charset = Charset.forName("GB18030");
	 CharsetDecoder decoder = charset.newDecoder();
	 CharsetEncoder encoder = charset.newEncoder();
		
		// ******** Selector
		ByteBuffer readBuffer = ByteBuffer.allocate(8 * 1024);

		InetSocketAddress hostAdr = new InetSocketAddress(8089);

		// ******** SocketChannel
		SocketChannel client = SocketChannel.open();
		client.configureBlocking(true);
		client.connect(hostAdr);
	
		client.read(readBuffer);
		readBuffer.flip();
		
		CharBuffer charBuffer = decoder.decode(readBuffer);
		//charBuffer.flip();

		System.out.println("Read  " + charBuffer.toString());
	}

}
