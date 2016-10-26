package fr.sekaijin.osgi.web.module.message;

import java.util.ArrayList;
import java.util.List;

import fr.sekaijin.osgi.web.main.api.IModule;
import fr.sekaijin.osgi.web.main.api.MenuItem;

public class MessageModule 
implements IModule {

	protected String moduleName;
	
	public MessageModule(String moduleName) {
		super();
		this.moduleName = moduleName;
	}

	public String getModuleName() {
		return moduleName;
	}

	public List<MenuItem> getMenuItems() {
		List<MenuItem> menuItemList = new ArrayList<>();

		menuItemList.add(new MenuItem(moduleName, "add.png", "create", "create"));
		menuItemList.add(new MenuItem(moduleName, "inbox.png", "inbox", "inbox"));
		menuItemList.add(new MenuItem(moduleName, "outbox.png", "outbox", "outbox"));
		menuItemList.add(new MenuItem(moduleName, "sent.png", "sent", "sent"));

		return menuItemList;
	}

}
