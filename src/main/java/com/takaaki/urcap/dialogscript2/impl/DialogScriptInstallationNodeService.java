package com.takaaki.urcap.dialogscript2.impl;

import com.ur.urcap.api.contribution.InstallationNodeContribution;
import com.ur.urcap.api.contribution.InstallationNodeService;
import com.ur.urcap.api.domain.URCapAPI;

import java.io.InputStream;

import com.ur.urcap.api.domain.data.DataModel;

public class DialogScriptInstallationNodeService implements InstallationNodeService {

	private DialogScriptInstallationNodeContribution contribution;

	public DialogScriptInstallationNodeService() {

	}

	@Override
	public InstallationNodeContribution createInstallationNode(URCapAPI api, DataModel model) {

		contribution = new DialogScriptInstallationNodeContribution(api, model);

		return contribution;
	}

	@Override
	public String getTitle() {
		return "Dialog Script";
	}

	@Override
	public InputStream getHTML() {
		return this.getClass().getResourceAsStream("/com/takaaki/urcap/dialogscript2/impl/installation_en.html");

	}
}
