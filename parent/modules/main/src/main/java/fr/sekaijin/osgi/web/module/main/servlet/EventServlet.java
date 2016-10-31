package fr.sekaijin.osgi.web.module.main.servlet;

import java.io.IOException;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.eclipse.jetty.websocket.WebSocket;
import org.eclipse.jetty.websocket.WebSocketServlet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fr.sekaijin.osgi.web.main.registrar.MenuItemRegistrar;

public class EventServlet extends WebSocketServlet {

	private static final long serialVersionUID = -7289719281366784056L;
	public static String newLine = System.getProperty("line.separator");

	private final Set<EventSocket> members = new CopyOnWriteArraySet<>();
	private Logger log;

	@Override
	public void init() throws ServletException {
		super.init();
		log = LoggerFactory.getLogger(getClass());
		MenuItemRegistrar.setSocket(this);
	}
	
	public void notifySubscriber() {		
		log.info("Running Server Message Sending");
		for (EventSocket member : members) {
			log.info("Trying to send to Member!");
			if (member.isOpen()) {
				log.info("Sending!");
				try {
					member.sendMessage("menuUpdate");
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		getServletContext().getNamedDispatcher("default").forward(request, response);
	}

	public WebSocket doWebSocketConnect(HttpServletRequest request, String protocol) {
		return new EventSocket(members);
	}

}