package fr.sekaijin.osgi.web.module.tracker;

import org.osgi.framework.BundleContext;
import org.osgi.util.tracker.ServiceTracker;
import org.osgi.util.tracker.ServiceTrackerCustomizer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ModuleTracker extends Thread {
	private ServiceTracker tracker;
	private ServiceTrackerCustomizer ModuleTrackerCustomizer;
	private String className;
	private Logger log;
	private BundleContext context;

	public ModuleTracker(BundleContext context, String className, ServiceTrackerCustomizer ModuleTrackerCustomizer) {
		this.ModuleTrackerCustomizer = ModuleTrackerCustomizer;
		this.className = className;
		this.log = LoggerFactory.getLogger(getClass());
		this.context = context;
	}

	public void run() {
		try {
			this.tracker = new ServiceTracker(context, className, ModuleTrackerCustomizer);
			this.tracker.open();
		} catch (Throwable e) {
			log.error(e.getMessage(), e);
		}
	}

	public synchronized void close() {
		if (tracker != null) {
			tracker.close();
		}
	}
}
