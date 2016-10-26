package fr.sekaijin.osgi.web.main.tracker;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.util.tracker.ServiceTrackerCustomizer;

import fr.sekaijin.osgi.web.main.api.IModule;
import fr.sekaijin.osgi.web.main.registrar.MenuItemRegistrar;
import fr.sekaijin.osgi.web.module.tracker.AbstractModuleTrackerCustomizer;

public class ModuleTrackerCustomizer extends AbstractModuleTrackerCustomizer implements ServiceTrackerCustomizer {
	public ModuleTrackerCustomizer(BundleContext context) {
		super(context);
	}

	@Override
	public void modifiedService(ServiceReference reference, Object service) {
		removedService(reference, service);
		addingService(reference);		
	}

	@Override
	public Object addingService(ServiceReference reference) {
		IModule service = IModule.class.cast(context.getService(reference));
		log.info("ADDING MODULE    : " + service.getModuleName());
		MenuItemRegistrar.addMenuItem(service.getModuleName(), service.getMenuItems());
		return service;
	}

	@Override
	public void removedService(ServiceReference reference, Object ser) {
		IModule service = IModule.class.cast(context.getService(reference));
		log.info("REMOVING MODULE  : " + service.getModuleName());
		MenuItemRegistrar.removeMenuItem(service.getModuleName());
	}

}