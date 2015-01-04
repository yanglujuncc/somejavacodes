package jetty;

import org.eclipse.jetty.server.Server;

public class SimplestServer {
	
	    public static void main( String[] args ) throws Exception
	    {
	        Server server = new Server(8081);
	        server.start();
	      //  server.d
	        server.join();
	    }

}
