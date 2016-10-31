package fr.sekaijin.osgi.web.main.registrar;

import java.util.HashMap;
import java.util.Map;

import fr.sekaijin.osgi.web.main.api.IMenu;
import fr.sekaijin.osgi.web.module.main.servlet.EventServlet;

public class MenuItemRegistrar {
	private static EventServlet EVENT_SERVLET = null;
	private static Map<String, IMenu> MENUS = new HashMap<>();

	public static synchronized Map<String, IMenu> getMenus() {
		return MENUS;
	}

	public static synchronized void addMenu(String module, IMenu menu) {
		MENUS.put(module, menu);
	}

	public static synchronized void removeMenu(String module) {
		MENUS.remove(module);
	}

	public static EventServlet getSocket() {
		return EVENT_SERVLET;
	}

	public static void setSocket(EventServlet eventServlet) {
		EVENT_SERVLET  = eventServlet;
	}
}