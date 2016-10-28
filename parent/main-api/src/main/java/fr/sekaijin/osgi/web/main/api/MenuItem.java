package fr.sekaijin.osgi.web.main.api;

public class MenuItem {
	private String label;
	private String path;
	private String icon;

	public MenuItem() {
		super();
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}
}
