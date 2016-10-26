package fr.sekaijin.osgi.web.module.user;

import java.util.ArrayList;
import java.util.List;

import fr.sekaijin.osgi.web.main.api.IModule;
import fr.sekaijin.osgi.web.main.api.MenuItem;

public class UserModule implements IModule{

	protected String moduleName;
	
	public UserModule(String moduleName) {
		super();
		this.moduleName = moduleName;
	}

	public String getModuleName() {
		return moduleName;
	}

	public List<MenuItem> getMenuItems() {
		List<MenuItem> menuItemList = new ArrayList<>();

		menuItemList.add(new MenuItem(moduleName, "user_add.png", "add", "add"));
		menuItemList.add(new MenuItem(moduleName, "users.jpeg", "manage", "manage"));

		return menuItemList;
	}

}
