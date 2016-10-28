package fr.sekaijin.osgi.web.module;

import java.util.Map;

import javax.servlet.Servlet;

public interface IModule {

	public String getModuleName();
	public String getContextId();
	public String getContextPath();
	public Map<String, Servlet> getServlets();
	public String[] getWelcomeFiles();
	public Boolean isWelcomeRedirect();
	public String[] getJspPatterns();
	public Map<String, String> getErrors();
	public Map<String, String> getResources();
}
