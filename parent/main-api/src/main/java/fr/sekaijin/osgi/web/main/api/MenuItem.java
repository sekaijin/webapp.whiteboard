package fr.sekaijin.osgi.web.main.api;

public class MenuItem {
	private String key;
	private String path;
	private String moduleName;
	private String icon;

	public MenuItem(String moduleName, String icon, String key, String path) {
		super();
		this.moduleName= moduleName;
		this.icon = icon;
		this.key = key;
		this.path = path;
	}

	public String getModuleName() {
		return moduleName;
	}

	public void setModuleName(String moduleName) {
		this.moduleName = moduleName;
	}


	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public MenuItem(String key) {
		super();
		this.key = key;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}
}
