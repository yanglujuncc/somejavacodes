package NIO;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CharsetEncoder;
import java.util.Iterator;

public class testClient {

public static void handleKey(SelectionKey key) throws IOException {

		
		int interestOps=key.interestOps();
		System.out.println("#interestOps:"+interestOpsStr(interestOps));
		
		ByteBuffer readBuffer = ByteBuffer.allocate(8 * 1024);

		Charset charset = Charset.forName("GB2312");
		CharsetDecoder decoder = charset.newDecoder();
		CharsetEncoder encoder = charset.newEncoder();

		int ReadOps = key.readyOps();
		System.out.println("    ReadOps:"+interestOpsStr(ReadOps));
		
		// 接收请求事件

		if (key.isAcceptable()) {
			System.out.println("Acceptable");

	

			return;
		}
		// 连接事件
		if (key.isConnectable()) {
			
			System.out.println("Connectable");
			SocketChannel client = (SocketChannel) key.channel();
			//1	key.interestOps(SelectionKey.OP_READ);
		
			
			client.register(key.selector(), SelectionKey.OP_READ);
			//key.cancel();
			return ;

		}
		// 可读事件
		if (key.isReadable()) {

			System.out.println("Readable");
			SocketChannel channel = (SocketChannel) key.channel();

	
			int count = channel.read(readBuffer);
			System.out.println("   read:" + count);
			if (count > 0) {

				readBuffer.flip();

				CharBuffer charBuffer = decoder.decode(readBuffer);
				readBuffer.clear();
				System.out.println("   Server:" + charBuffer.toString());

			}
			System.exit(1);
			return;
		}
		// 可写事件
		if (key.isWritable()) {
			
			return;

		}

		

	}
	
	public static  String interestOpsStr(int interestOps) throws IOException {
		String outStr="";
		
	
		if((interestOps&SelectionKey.OP_ACCEPT)!=0){
			outStr+="OP_ACCEPT|";
		}
		if((interestOps&SelectionKey.OP_CONNECT)!=0){
			outStr+="OP_CONNECT|";
		}
		if((interestOps&SelectionKey.OP_READ)!=0){
			outStr+="OP_READ|";
		}
		if((interestOps&SelectionKey.OP_WRITE)!=0){
			outStr+="OP_WRITE|";
		}
		
		return outStr;
	}
	public static void main(String[] args) throws IOException {

		
		// ******** Selector
		Selector aSelector = Selector.open();

		InetSocketAddress hostAdr = new InetSocketAddress(8089);

		// ******** SocketChannel
		SocketChannel client = SocketChannel.open();
		client.configureBlocking(false);
		client.connect(hostAdr);
		client.register(aSelector, SelectionKey.OP_CONNECT, null);

		for (int i = 1; i < 10; i++) {

			System.out.println("Client select loop:" + i);
			aSelector.select(30000);

			System.out.println(" RegistedKey:" +aSelector.keys().size());
			for (SelectionKey registedKey : aSelector.keys()) {

				System.out.println(registedKey.channel().hashCode()+"   "+registedKey.channel() + ":" + interestOpsStr(registedKey.interestOps()));
			}
			
			System.out.println("selectedKeySet:" +  aSelector.selectedKeys().hashCode() + " Size:" + aSelector.selectedKeys().size());
			System.out.println("selectedKeySet:" +  aSelector.selectedKeys().hashCode() + " Size:" + aSelector.selectedKeys().size());
			
			Iterator<SelectionKey> it = aSelector.selectedKeys().iterator();

			while (it.hasNext()) {
				SelectionKey key = it.next();
				handleKey(key);
				it.remove();

		
			}
			System.out.println("###############################################" );
		}

	}
}
