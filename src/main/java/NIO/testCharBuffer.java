package NIO;

import java.nio.CharBuffer;

public class testCharBuffer {

	public static void main(String[] args)
	{
		CharBuffer aCharBuffer=CharBuffer.allocate(1024);
		
		/*
		 * position 当前读写位置
		 * limit  position超过 limit报错
		 * remaining=length = limit-position
		 * 
		 * capacity
		 * 
		 */
		//flip limit=position   position=0 
		
		//aCharBuffer.append("helloWord!");
		System.out.println("*********        original          **************");
		System.out.println("capacity="+aCharBuffer.capacity());
		System.out.println("length="+aCharBuffer.length());
		System.out.println("remaining="+aCharBuffer.remaining());
		System.out.println("limit="+aCharBuffer.limit());
		System.out.println("hasRemaining="+aCharBuffer.hasRemaining());
		System.out.println("position="+aCharBuffer.position());
		
		System.out.println("*********        flip          **************");
		aCharBuffer.flip();
		System.out.println("capacity="+aCharBuffer.capacity());
		System.out.println("length="+aCharBuffer.length());
		System.out.println("remaining="+aCharBuffer.remaining());
		System.out.println("limit="+aCharBuffer.limit());
		System.out.println("hasRemaining="+aCharBuffer.hasRemaining());
		System.out.println("position="+aCharBuffer.position());
		//System.out.println("get()="+aCharBuffer.get());
		
		aCharBuffer.clear();
		System.out.println("*********        clear          **************");
		System.out.println("capacity="+aCharBuffer.capacity());
		System.out.println("length="+aCharBuffer.length());
		System.out.println("remaining="+aCharBuffer.remaining());
		System.out.println("limit="+aCharBuffer.limit());
		System.out.println("hasRemaining="+aCharBuffer.hasRemaining());
		System.out.println("position="+aCharBuffer.position());
		
		
		
		System.out.println("*********       put(helloWord!)         **************");
		aCharBuffer.put("helloWord!");
		System.out.println("capacity="+aCharBuffer.capacity());
		System.out.println("length="+aCharBuffer.length());
		System.out.println("remaining="+aCharBuffer.remaining());
		System.out.println("limit="+aCharBuffer.limit());
		System.out.println("hasRemaining="+aCharBuffer.hasRemaining());
		System.out.println("position="+aCharBuffer.position());
	
		
		System.out.println("*********        flip          **************");
		aCharBuffer.flip();
		System.out.println("capacity="+aCharBuffer.capacity());
		System.out.println("length="+aCharBuffer.length());
		System.out.println("remaining="+aCharBuffer.remaining());
		System.out.println("limit="+aCharBuffer.limit());
		System.out.println("hasRemaining="+aCharBuffer.hasRemaining());
		System.out.println("position="+aCharBuffer.position());
		//System.out.println("get()="+aCharBuffer.get());
		
		
	
		System.out.println("*********        get()          **************");
		System.out.println("get():"+aCharBuffer.get());
		System.out.println("capacity="+aCharBuffer.capacity());
		System.out.println("length="+aCharBuffer.length());
		System.out.println("remaining="+aCharBuffer.remaining());
		System.out.println("limit="+aCharBuffer.limit());
		System.out.println("hasRemaining="+aCharBuffer.hasRemaining());
		System.out.println("position="+aCharBuffer.position());
		
		System.out.println("*********       put(a)         **************");
		aCharBuffer.put("a");
		System.out.println("capacity="+aCharBuffer.capacity());
		System.out.println("length="+aCharBuffer.length());
		System.out.println("remaining="+aCharBuffer.remaining());
		System.out.println("limit="+aCharBuffer.limit());
		System.out.println("hasRemaining="+aCharBuffer.hasRemaining());
		System.out.println("position="+aCharBuffer.position());
		
		System.out.println("*********        rewind()          **************");
		aCharBuffer.rewind();
	
		System.out.println("capacity="+aCharBuffer.capacity());
		System.out.println("length="+aCharBuffer.length());
		System.out.println("remaining="+aCharBuffer.remaining());
		System.out.println("limit="+aCharBuffer.limit());
		System.out.println("hasRemaining="+aCharBuffer.hasRemaining());
		System.out.println("position="+aCharBuffer.position());
		
		
		/*
		aCharBuffer.clear();
		System.out.println("*********        clear          **************");
		System.out.println("capacity="+aCharBuffer.capacity());
		System.out.println("length="+aCharBuffer.length());
		System.out.println("remaining="+aCharBuffer.remaining());
		System.out.println("limit="+aCharBuffer.limit());
		System.out.println("hasRemaining="+aCharBuffer.hasRemaining());
		System.out.println("position="+aCharBuffer.position());
		
		
		System.out.println("*********        flip          **************");
		aCharBuffer.flip();
		System.out.println("capacity="+aCharBuffer.capacity());
		System.out.println("length="+aCharBuffer.length());
		System.out.println("remaining="+aCharBuffer.remaining());
		System.out.println("limit="+aCharBuffer.limit());
		System.out.println("hasRemaining="+aCharBuffer.hasRemaining());
		System.out.println("position="+aCharBuffer.position());
		//System.out.println("get(110)="+aCharBuffer.get(110));
		
		
		*/
		aCharBuffer.limit(20);
	    for(int i=0;i<20;i++)
	    {
	    
	    	System.out.println(i+":"+(int)aCharBuffer.get());
	    }
	}
}
