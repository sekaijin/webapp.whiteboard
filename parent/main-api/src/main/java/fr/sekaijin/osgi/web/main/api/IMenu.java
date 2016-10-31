package fr.sekaijin.osgi.web.main.api;

import java.util.List;

public interface IMenu {
	
	public String getModuleName();
	
	public String getModuleIcon();

	public List<MenuItem> getMenuItems();
}
