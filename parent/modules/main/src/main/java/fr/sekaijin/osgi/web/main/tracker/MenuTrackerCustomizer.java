package fr.sekaijin.osgi.web.main.tracker;

import org.osgi.framework.ServiceReference;
import org.osgi.util.tracker.ServiceTrackerCustomizer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fr.sekaijin.osgi.web.main.api.IMenu;
import fr.sekaijin.osgi.web.main.registrar.MenuItemRegistrar;

public class MenuTrackerCustomizer
implements ServiceTrackerCustomizer 
{
	private Logger log;

	public MenuTrackerCustomizer() {
		super();
		log = LoggerFactory.getLogger(getClass());
	}

	@Override
	public void modifiedService(ServiceReference reference, Object service) {
		removedService(reference, service);
		addingService(reference);		
	}

	@Override
	public Object addingService(ServiceReference reference) {
		IMenu service = IMenu.class.cast(reference.getBundle().getBundleContext().getService(reference));
		log.info("ADDING MENU    : " + service.getModuleName());
		MenuItemRegistrar.addMenuItem(service.getModuleName(), service.getMenuItems());
		return service;
	}

	@Override
	public void removedService(ServiceReference reference, Object ser) {
		IMenu service = IMenu.class.cast(reference.getBundle().getBundleContext().getService(reference));
		log.info("REMOVING MENU  : " + service.getModuleName());
		MenuItemRegistrar.removeMenuItem(service.getModuleName());
	}

}