package fr.sekaijin.osgi.web.module.tracker;

import org.osgi.framework.BundleContext;
import org.slf4j.LoggerFactory;

abstract public class AbstractModuleTrackerCustomizer {

	protected final BundleContext context;
	protected org.slf4j.Logger log;

	public AbstractModuleTrackerCustomizer(BundleContext context) {
		super();
		this.context = context;
		log = LoggerFactory.getLogger(getClass());
	}

}