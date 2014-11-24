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
import java.util.Set;

public class testSelector {

	static byte[] helloBytes;
	static Charset charset = Charset.forName("GB18030");
	static CharsetDecoder decoder = charset.newDecoder();
	static CharsetEncoder encoder = charset.newEncoder();

	public class Client {

		// 0 1 2 3
		int state;
		ByteBuffer readBuffer = ByteBuffer.allocate(8 * 1024);
		ByteBuffer writeBuffer = ByteBuffer.allocate(8 * 1024);

	}

	public void handleKey(SelectionKey key) throws IOException {

		int interestOps = key.interestOps();
		System.out.println("   hashCode:" + key.hashCode());
		System.out.println("interestOps:" + interestOpsStr(interestOps));

		int ReadOps = key.readyOps();
		System.out.println("    ReadOps:" + interestOpsStr(ReadOps));

		// 接收请求事件

		if (key.isAcceptable()) {
			System.out.println("Acceptable");

			ServerSocketChannel server = (ServerSocketChannel) key.channel();
			SocketChannel linkedChannel = server.accept();
			linkedChannel.configureBlocking(false);
			System.out.println("attached:" + key.attachment());

			
			Client newClient = new Client();
			newClient.state = 1;
			
			linkedChannel.register(key.selector(), SelectionKey.OP_WRITE,newClient);
			return;
		}
		// 连接事件
		if (key.isConnectable()) {
			System.out.println("Connectable");
			SocketChannel client = (SocketChannel) key.channel();
			key.interestOps(SelectionKey.OP_READ);
			// key.cancel();
			return;

		}
		// 可读事件
		if (key.isReadable()) {

			System.out.println("Readable");
			SocketChannel channel = (SocketChannel) key.channel();
			Client linkedClient = (Client) key.attachment();
			if (linkedClient.state == 2) {
				try {
					int count = channel.read(linkedClient.readBuffer);
					if (count > 0) {

						linkedClient.readBuffer.flip();

						CharBuffer charBuffer = decoder.decode(linkedClient.readBuffer);
						linkedClient.readBuffer.clear();

						System.out.println("Read  " + charBuffer.toString());

					}
				} catch (Exception e) {
					e.printStackTrace();
					channel.close();
					System.out.println("Client close");
				}
			}

			return;
		}
		// 可写事件
		if (key.isWritable()) {

			System.out.println("Writable");
			SocketChannel linkedChannel = (SocketChannel) key.channel();
			Client linkedClient = (Client) key.attachment();

			System.out.println();
			if (linkedClient.state == 1) {
				ByteBuffer byteBuffer = ByteBuffer.wrap(helloBytes);
				int writed = linkedChannel.write(byteBuffer);
				System.out.println(">>>>>>>>>>>>>> writed:" + writed);

				linkedClient.state = 2;

				linkedChannel.register(key.selector(), SelectionKey.OP_READ,linkedClient);
			

			}

			return;

		}

	}

	public static String interestOpsStr(int interestOps) throws IOException {
		String outStr = "";

		if ((interestOps & SelectionKey.OP_ACCEPT) != 0) {
			outStr += "OP_ACCEPT|";
		}
		if ((interestOps & SelectionKey.OP_CONNECT) != 0) {
			outStr += "OP_CONNECT|";
		}
		if ((interestOps & SelectionKey.OP_READ) != 0) {
			outStr += "OP_READ|";
		}
		if ((interestOps & SelectionKey.OP_WRITE) != 0) {
			outStr += "OP_WRITE|";
		}

		return outStr;
	}

	public static void main(String[] args) throws IOException {

		String hello = "Hello ,I'm server";
		helloBytes = hello.getBytes("gb18030");

		InetSocketAddress hostAdr = new InetSocketAddress(8089);
		// ******** Selector
		Selector aSelector = Selector.open();

		// ******** ServerSocketChannel
		ServerSocketChannel server = ServerSocketChannel.open();
		server.socket().bind(hostAdr);
		server.configureBlocking(false);
		server.register(aSelector, SelectionKey.OP_ACCEPT, null);

		// ******** SocketChannel
		// SocketChannel client = SocketChannel.open();
		// client.configureBlocking(false);
		// client.connect(hostAdr);
		// client.register(aSelector, SelectionKey.OP_CONNECT, null);

		// client.configureBlocking(false);
		testSelector atestSelector = new testSelector();
		for (int i = 1; 0 < 10; i++) {

			System.out.println("#Server select loop:" + i);
			aSelector.select(5000);
			
			System.out.println(" RegistedKey:" +aSelector.keys().size());
			for (SelectionKey registedKey : aSelector.keys()) {

				System.out.println("   "+registedKey.channel() + ":" + interestOpsStr(registedKey.interestOps()));
			}
			
			
			Set<SelectionKey> selectedKeySet = aSelector.selectedKeys();
			System.out.println("selectedKeySet:" + selectedKeySet.hashCode() + " Size:" + selectedKeySet.size());
			
			Iterator<SelectionKey> it = aSelector.selectedKeys().iterator();

			while (it.hasNext()) {

				SelectionKey key = it.next();
				atestSelector.handleKey(key);
				it.remove();

				// key.

			}
		
			System.out.println("###############################################" );
		}

	}
}
