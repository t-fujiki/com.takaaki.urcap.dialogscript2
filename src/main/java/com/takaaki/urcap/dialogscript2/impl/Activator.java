package com.takaaki.urcap.dialogscript2.impl;

import com.ur.urcap.api.contribution.InstallationNodeService;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

public class Activator implements BundleActivator {

	@Override
	public void start(BundleContext context) throws Exception {
		DialogScriptInstallationNodeService installationNodeService = new DialogScriptInstallationNodeService();

		context.registerService(InstallationNodeService.class, installationNodeService, null);

	}

	@Override
	public void stop(BundleContext bundleContext) throws Exception {

	}
}
