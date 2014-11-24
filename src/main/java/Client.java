
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Set;

/**
 *
 * @author hadeslee
 */
public class Client {

    public static void main(String[] args) throws Exception {
    	
        SocketChannel sc = SocketChannel.open();
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        Selector se = Selector.open();
        buffer.put("我是中国人,我爱我的祖国,hadeslee".getBytes());
        buffer.flip();
        
        sc.configureBlocking(false);
        sc.register(se, SelectionKey.OP_CONNECT | SelectionKey.OP_READ | SelectionKey.OP_WRITE);
        sc.connect(new InetSocketAddress("192.168.161.222", 8890));
        while(!sc.finishConnect());
        sc.write(buffer);
        System.out.println("进入循环");
        Thread.sleep(10000);
      
        while (se.select() > 0) {
            Thread.sleep(100);
               
                System.out.println("终于大于0了");
                Set<SelectionKey> set = se.selectedKeys();
                System.out.println("大小是:"+set.size());
                for (SelectionKey key : set) {
                    int ops = key.readyOps();
                    if ((ops & SelectionKey.OP_CONNECT) == SelectionKey.OP_CONNECT) {
                        sc.write(buffer);
                        System.out.println("连接成功");
                    }
                    if ((ops & SelectionKey.OP_READ) == SelectionKey.OP_READ) {
                        System.out.println(" 收到东西");
                        sc.read(buffer);
                        buffer.flip();
                        System.out.println("收到的是:" + new String(buffer.array(),0,buffer.limit()));
                        sc.write(buffer);
                    }
                }
               se.selectedKeys().clear();
        }
    }

 
}