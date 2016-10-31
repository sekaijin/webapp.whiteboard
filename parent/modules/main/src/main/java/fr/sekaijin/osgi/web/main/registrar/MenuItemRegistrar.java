package fr.sekaijin.osgi.web.main.registrar;

import java.util.HashMap;
import java.util.Map;

import fr.sekaijin.osgi.web.main.api.IMenu;

public class MenuItemRegistrar {
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
}