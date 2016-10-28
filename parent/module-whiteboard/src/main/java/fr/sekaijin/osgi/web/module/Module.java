package fr.sekaijin.osgi.web.module;

import java.util.Map;

import javax.servlet.Servlet;

public class Module implements IModule{

	private String moduleName;
	private String contextId;
	private String contextPath;
	private Map<String, Servlet> servlets;
	private String[] welcomeFiles;
	private Boolean welcomeRedirect = false;
	private String[] jspPatterns;
	private Map<String, String> errors;
	private Map<String, String> resources;

	public Module() {
	}

	@Override
	public String getModuleName() {
		return this.moduleName;
	}

	public void setModuleName(String moduleName) {
		this.moduleName = moduleName;
	}

	@Override
	public String getContextId() {
		return contextId;
	}

	public void setContextId(String contextId) {
		this.contextId = contextId;
	}

	@Override
	public String getContextPath() {
		return this.contextPath;
	}

	public void setContextPath(String contextPath) {
		this.contextPath = contextPath;
	}

	@Override
	public Map<String, Servlet> getServlets() {
		return this.servlets;
	}

	public void setServlets(Map<String, Servlet> servlets) {
		this.servlets = servlets;
	}

	@Override
	public String[] getWelcomeFiles() {
		return this.welcomeFiles;
	}

	public void setWelcomeFiles(String[] welcomeFiles) {
		this.welcomeFiles = welcomeFiles;
	}

	@Override
	public Boolean isWelcomeRedirect() {
		return welcomeRedirect;
	}

	public void setWelcomeRedirect(Boolean welcomeRedirect) {
		this.welcomeRedirect = welcomeRedirect;
	}

	@Override
	public String[] getJspPatterns() {
		return jspPatterns;
	}

	public void setJspPatterns(String[] jspPatterns) {
		this.jspPatterns = jspPatterns;
	}

	@Override
	public Map<String, String> getErrors() {
		return errors;
	}

	public void setErrors(Map<String, String> errors) {
		this.errors = errors;
	}

	@Override
	public Map<String, String> getResources() {
		return resources;
	}

	public void setResources(Map<String, String> resources) {
		this.resources = resources;
	}

}
