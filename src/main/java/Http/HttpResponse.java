package Http;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.SocketChannel;
import java.nio.charset.CharacterCodingException;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CharsetEncoder;

public class HttpResponse {
	
	ByteBuffer readTempBuffer=ByteBuffer.allocate(1024);
	ByteBuffer readMessageHeadBuffer=null;
	ByteBuffer readMessageBodyBuffer=null;
	
	int headBeginIndex=-1;
	int headEndIndex=-1;
	
	int bodyBeginIndex=-1;
	int bodyEndIndex=-1;
	
	boolean isHeadCompletion=false;
	boolean isBodyCompletion=false;
	
	
	public boolean isHeadcompletion() throws CharacterCodingException{
		
		
		return isHeadCompletion;
		ByteBuffer tempByteBuffer=readMessageHeadBuffer.duplicate();
		tempByteBuffer.flip();
		Charset charset = Charset.forName("GB2312");
		CharsetDecoder decoder = charset.newDecoder();
		CharBuffer charBuffer = decoder.decode(tempByteBuffer);
		String received=charBuffer.toString();
		if(received.contains("\r\n\r\n"))
		{
			return true;
		}
		else{
			return false;
		}
		
	}
	public boolean readFromSocketChannel(SocketChannel channel) throws IOException{
		
		if(isHeadCompletion)
			channel.read(readMessageBodyBuffer);
		else
		{
			channel.read(readTempBuffer);
		}
		
		return false;
	}
	

}
