package RandomAccessFile;

import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

public class TestFileMap {
	
	public static void main(String[] args) throws Exception {  
        // 为了以可读可写的方式打开文件，这里使用RandomAccessFile来创建文件。  
        FileChannel fc = new RandomAccessFile("test.dat", "rw").getChannel();  
        //注意，文件通道的可读可写要建立在文件流本身可读写的基础之上  
        int lengthInt =1024; // 128 Mb  
      
        int intByteSize=Integer.SIZE/Byte.SIZE;
        int lengthByte =lengthInt*intByteSize; // 128 Mb  
        MappedByteBuffer out = fc.map(FileChannel.MapMode.READ_WRITE, 0, lengthByte);  
        //写128M的内容  
        for (int i = 0; i < lengthInt; i++) {  
          
            out.putInt(i);
        }  
        System.out.println("Finished writing");  
        //读取文件中间6个字节内容  
        
        for (int i = lengthInt / 2; i < lengthInt / 2 + 6; i++) {  
        	
       
            System.out.println("["+i+"]="+ out.getInt(i*intByteSize));  
        }  
        fc.close();  
    }  
}
