package fr.sekaijin.osgi.web.main.registrar;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import fr.sekaijin.osgi.web.main.api.MenuItem;

public class MenuItemRegistrar {
	private static Map<String, List<MenuItem>> MENU_ITEMS = new HashMap<>();

	public static synchronized Map<String, List<MenuItem>> getMenuItems() {
		return MENU_ITEMS;
	}

	public static synchronized void setMenuItems(Map<String, List<MenuItem>> menuItems) {
		MENU_ITEMS = menuItems;
	}

	public static synchronized void addMenuItem(String module, List<MenuItem> menuItems) {
		MENU_ITEMS.put(module, menuItems);
	}

	public static synchronized void addMenuItem(String module, MenuItem menuItem) {
		if (MENU_ITEMS.containsKey(module)) {
			MENU_ITEMS.get(MENU_ITEMS).add(menuItem);
		} else {
			List<MenuItem> tempMenuItemList = new ArrayList<>();
			tempMenuItemList.add(menuItem);
			MENU_ITEMS.put(module, tempMenuItemList);
		}
	}

	public static synchronized void removeMenuItem(String module) {
		MENU_ITEMS.remove(module);
	}
}