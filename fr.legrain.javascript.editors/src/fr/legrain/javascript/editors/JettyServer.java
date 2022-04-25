package fr.legrain.javascript.editors;


import org.mortbay.jetty.Server;
import org.mortbay.jetty.handler.ContextHandlerCollection;

public class JettyServer {
	/*
	 * http://www.codeproject.com/Articles/128145/Run-Jetty-Web-Server-Within-Your-Application
	 */

	private Server server;
	
	public JettyServer() {
		this(8585);
	}
	public JettyServer(Integer runningPort) {
		server = new Server(runningPort);
	}
	
	public void setHandler(ContextHandlerCollection contexts) {
		server.setHandler(contexts);
	}
	
	public void start() throws Exception {
		server.start();
	}
	
	public void stop() throws Exception {
		server.stop();
		server.join();
	}
	
	public boolean isStarted() {
		return server.isStarted();
	}
	
	public boolean isStopped() {
		return server.isStopped();
	}
}
