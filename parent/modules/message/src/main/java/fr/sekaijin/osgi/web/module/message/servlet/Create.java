package fr.sekaijin.osgi.web.module.message.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Create extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private Logger log;

	public Create() {
		log = LoggerFactory.getLogger(getClass());
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.getWriter().println("Message - Create - Servlet");
		log.info("Message - Create - Servlet");
	}
}
