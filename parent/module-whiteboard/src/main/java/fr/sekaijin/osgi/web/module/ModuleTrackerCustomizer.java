package fr.sekaijin.osgi.web.module;

import java.util.ArrayList;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import javax.servlet.Servlet;

import org.ops4j.pax.web.extender.whiteboard.ErrorPageMapping;
import org.ops4j.pax.web.extender.whiteboard.HttpContextMapping;
import org.ops4j.pax.web.extender.whiteboard.JspMapping;
import org.ops4j.pax.web.extender.whiteboard.ResourceMapping;
import org.ops4j.pax.web.extender.whiteboard.WelcomeFileMapping;
import org.ops4j.pax.web.extender.whiteboard.runtime.DefaultErrorPageMapping;
import org.ops4j.pax.web.extender.whiteboard.runtime.DefaultHttpContextMapping;
import org.ops4j.pax.web.extender.whiteboard.runtime.DefaultJspMapping;
import org.ops4j.pax.web.extender.whiteboard.runtime.DefaultResourceMapping;
import org.ops4j.pax.web.extender.whiteboard.runtime.DefaultWelcomeFileMapping;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.framework.ServiceRegistration;
import org.osgi.util.tracker.ServiceTrackerCustomizer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ModuleTrackerCustomizer implements ServiceTrackerCustomizer {
	private Logger log;
	private final Map<String, List<ServiceRegistration>> serviceRegistrations = new HashMap<>();

	public ModuleTrackerCustomizer() {
		super();
		log = LoggerFactory.getLogger(getClass());
	}

	@Override
	public void modifiedService(ServiceReference reference, Object service) {
		removedService(reference, service);
		addingService(reference);
	}

	@Override
	public void removedService(ServiceReference reference, Object ser) {
		IModule module = IModule.class.cast(reference.getBundle().getBundleContext().getService(reference));
		String key = module.getModuleName() + module.getContextId();
		log.info("REMOVING MODULE  : " + module.getModuleName());
		if (null != serviceRegistrations && serviceRegistrations.containsKey(key)) {
			for (ServiceRegistration registration : serviceRegistrations.get(key)) {
				log.info("UnRegistering : " + registration);
				try {
					registration.unregister();
				} catch (Throwable e) {
					log.warn("UnRegistering", e);
				}
			}
			serviceRegistrations.remove(key);
		}
	}

	@Override
	public Object addingService(ServiceReference reference) {
		List<ServiceRegistration> registrations = new ArrayList<>();
		IModule module = IModule.class.cast(reference.getBundle().getBundleContext().getService(reference));
		log.info("ADDING MODULE    : " + module.getModuleName());

		contextRegistration(registrations, module, reference.getBundle().getBundleContext());
		ressourceRegistration(registrations, module, reference.getBundle().getBundleContext());

		if (null != module.getWelcomeFiles()) {
			welcomeRegistration(registrations, module, reference.getBundle().getBundleContext(),
					module.getWelcomeFiles());
		}

		if (null != module.getServlets()) {
			servletsRegistration(registrations, module, reference.getBundle().getBundleContext(), module.getServlets());
		}

		if (null != module.getJspPatterns()) {
			jspRegistration(registrations, module, reference.getBundle().getBundleContext(), module.getJspPatterns());
		}

		if (null != module.getErrors()) {
			errorRegistration(registrations, module, reference.getBundle().getBundleContext(), module.getErrors());
		}

		if (null != module.getResources()) {
			resourcesRegistration(registrations, module, reference.getBundle().getBundleContext(),
					module.getResources());
		}

		serviceRegistrations.put(module.getModuleName() + module.getContextId(), registrations);
		return module;
	}

	private void jspRegistration(List<ServiceRegistration> registrations, IModule module, BundleContext bundleContext,
			String[] jspPatterns) {
		DefaultJspMapping jspMapping = new DefaultJspMapping();
		jspMapping.setHttpContextId(module.getContextId());
		jspMapping.setUrlPatterns(jspPatterns);
		ServiceRegistration registration = bundleContext.registerService(JspMapping.class.getName(), jspMapping, null);
		registrations.add(registration);
	}

	private void welcomeRegistration(List<ServiceRegistration> registrations, IModule module,
			BundleContext bundleContext, String[] welcomeFiles) {
		DefaultWelcomeFileMapping welcomeFilesMap = new DefaultWelcomeFileMapping();
		welcomeFilesMap.setHttpContextId(module.getContextId());
		welcomeFilesMap.setRedirect(module.isWelcomeRedirect());
		welcomeFilesMap.setWelcomeFiles(welcomeFiles);
		ServiceRegistration registration = bundleContext.registerService(WelcomeFileMapping.class.getName(),
				welcomeFilesMap, null);
		registrations.add(registration);
	}

	private void contextRegistration(List<ServiceRegistration> registrations, IModule module,
			BundleContext bundleContext) {
		DefaultHttpContextMapping httpContext = new DefaultHttpContextMapping();
		httpContext.setHttpContextId(module.getContextId());
		httpContext.setPath(module.getContextPath());
		ServiceRegistration registration = bundleContext.registerService(HttpContextMapping.class.getName(),
				httpContext, null);
		registrations.add(registration);
	}

	private void ressourceRegistration(List<ServiceRegistration> registrations, IModule module,
			BundleContext bundleContext) {
		DefaultResourceMapping resources = new DefaultResourceMapping();
		resources.setHttpContextId(module.getContextId());
		resources.setAlias("/" + module.getModuleName() + "/static");
		resources.setPath("/local");
		ServiceRegistration registration = bundleContext.registerService(ResourceMapping.class.getName(), resources,
				null);
		registrations.add(registration);
	}

	private void servletsRegistration(List<ServiceRegistration> registrations, IModule module,
			BundleContext bundleContext, Map<String, Servlet> servlets) {
		for (String path : servlets.keySet()) {
			String adjustedPath = "/" + module.getModuleName() + "/" + path;
			log.info("Registering : " + adjustedPath + " / " + module.getServlets().get(path));

			Dictionary<String, String> props = new Hashtable<>();
			props.put("alias", adjustedPath);
			props.put("httpContext.id", module.getContextId());

			ServiceRegistration registration = bundleContext.registerService(Servlet.class.getName(),
					module.getServlets().get(path), props);
			registrations.add(registration);
		}
	}

	private void errorRegistration(List<ServiceRegistration> registrations, IModule module, BundleContext bundleContext,
			Map<String, String> errors) {
		for (String error : errors.keySet()) {
			DefaultErrorPageMapping errorPageMapping = new DefaultErrorPageMapping();
			errorPageMapping.setHttpContextId(module.getContextId());
			errorPageMapping.setError(error);
			errorPageMapping.setLocation(errors.get(error));

			ServiceRegistration registration = bundleContext.registerService(ErrorPageMapping.class.getName(),
					errorPageMapping, null);
			registrations.add(registration);
		}
	}

	private void resourcesRegistration(List<ServiceRegistration> registrations, IModule module,
			BundleContext bundleContext, Map<String, String> resources) {
		for (String resource : resources.keySet()) {
			DefaultResourceMapping resourceMap = new DefaultResourceMapping();
			resourceMap.setHttpContextId(module.getContextId());
			resourceMap.setAlias(resource);
			resourceMap.setPath(resources.get(resource));

			ServiceRegistration registration = bundleContext.registerService(ResourceMapping.class.getName(),
					resourceMap, null);
			registrations.add(registration);
		}
	}
}