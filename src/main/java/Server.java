import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Set;

/**
 * 
 * @author hadeslee
 */
public class Server {

	public static void main(String[] args) throws Exception {
		// boolean b = true;
		ByteBuffer buffer = ByteBuffer.allocate(1);

		ServerSocketChannel ss = ServerSocketChannel.open();
		ss.socket().bind(new InetSocketAddress(8891));
		ss.configureBlocking(false);
		Selector se = Selector.open();
		ss.register(se, SelectionKey.OP_ACCEPT);
	
		int i = 0;

		while (se.select(5000) >= 0) {
			i++;
			System.out.println("loop :" + i);
			System.out.println("begin se.keys() ="+ se.keys().size());
			
			

			Set<SelectionKey> set = se.selectedKeys();

			System.out.println("selected  :" + set.size() + " Keys !");

			for (SelectionKey key : set) {
				int ops = key.readyOps();
				// print ops
				System.out.print("ops=");

				if ((ops & SelectionKey.OP_ACCEPT) > 0)
					System.out.print("&OP_ACCEPT");
				if ((ops & SelectionKey.OP_CONNECT) > 0)
					System.out.print("&OP_CONNECT");
				if ((ops & SelectionKey.OP_READ) > 0)
					System.out.print("&OP_READ");
				if ((ops & SelectionKey.OP_WRITE) > 0)
					System.out.print("&OP_WRITE");

				System.out.println();
				if ((ops & SelectionKey.OP_ACCEPT) > 0) {

					ServerSocketChannel ssc = (ServerSocketChannel) key
							.channel();
					SocketChannel sc = ssc.accept();

					System.out.println("µØÖ·ÊÇ:" + sc.socket());

					sc.configureBlocking(false);

					SelectionKey readKey=sc.register(se, SelectionKey.OP_READ);
					readKey.attach("1");

				}

				else if ((ops & SelectionKey.OP_READ) > 0) {

					SocketChannel sc = (SocketChannel) key.channel();

					System.out.flush();
					buffer.clear();

					try {
						int n = sc.read(buffer);

						if (n == -1) {
							System.out.println("n=-1 client closed");
							//key.cancel();
							//System.out.println("do key.cancel();");
							sc.close();
							System.out.println("do sc.close();");
							
						}

						if (n > 0) {
							buffer.flip();
							System.out.print("ReadFromClient : n " + n
									+ " bytes :" );
							for(int i1=0;i1<n;i1++)
							{
								System.out.print(buffer.get()+" ");
							}
							System.out.println();
						}
						

					} catch (java.io.IOException e) {
						System.out
								.println("catch  java.io.IOException ,client closed ");

						key.cancel();
						sc.close();
					}

					/*
					 * Thread.sleep(5000); if (b) { b = false; sc.write(buffer);
					 * }
					 */

				}
			}
			set.clear();
			System.out.flush();
			
			Set<SelectionKey> allKeys = se.keys();
			System.out.println("end se.keys() ="+ allKeys.size());
			System.out.println("se.selectNow()="+se.selectNow());
			System.out.println("end se.keys() ="+ allKeys.size());
			System.out.println("end loop");
		}

	}
}