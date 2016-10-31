package fr.sekaijin.osgi.web.module.main.servlet;

import java.io.IOException;
import java.util.Set;

import org.eclipse.jetty.websocket.WebSocket;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EventSocket implements WebSocket.OnTextMessage {
	private Connection connection;
	private Set<EventSocket> members;
	private Logger log;

	public EventSocket(Set<EventSocket> members) {
		this.members = members;
		log = LoggerFactory.getLogger(getClass());
	}

	@Override
	public void onClose(int closeCode, String message) {
		try {
			this.connection.sendMessage("disconnected");
		} catch (IOException e) {
			log.warn(e.getMessage());;
		}
		members.remove(this);
	}

	public void sendMessage(String data) throws IOException {
		connection.sendMessage(data);
	}

	@Override
	public void onMessage(String data) {
		log.info("Received: " + data);
	}

	public boolean isOpen() {
		return connection.isOpen();
	}

	@Override
	public void onOpen(Connection cnx) {
		members.add(this);
		this.connection = cnx;
		try {
			this.connection.sendMessage("Connected");
		} catch (IOException e) {
			log.warn(e.getMessage());;
		}
	}
}
