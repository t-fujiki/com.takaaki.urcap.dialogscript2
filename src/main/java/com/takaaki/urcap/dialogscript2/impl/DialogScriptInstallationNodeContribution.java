package com.takaaki.urcap.dialogscript2.impl;

import com.takaaki.urcap.dialogscript2.impl.xmlrpc.DialogScriptXmlRpcServer;
import com.ur.urcap.api.contribution.InstallationNodeContribution;
import com.ur.urcap.api.domain.URCapAPI;
import com.ur.urcap.api.domain.data.DataModel;
import com.ur.urcap.api.domain.script.ScriptWriter;
import com.ur.urcap.api.ui.annotation.Input;
import com.ur.urcap.api.ui.component.InputEvent;
import com.ur.urcap.api.ui.component.InputTextField;
import com.ur.urcap.api.ui.component.InputEvent.EventType;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class DialogScriptInstallationNodeContribution implements InstallationNodeContribution {

	public URCapAPI api;
	public DataModel model;

	private final int DEFAULT_PORT = 61200;
	private final String ARTIFACTID = "dialogscript2";

	@Input(id = "inputPort")
	private InputTextField inputPort;

	@Input(id = "inputPort")
	private void onChange_inputPort(InputEvent event) {
		if (event.getEventType() == EventType.ON_CHANGE) {
			setModelonEvent("inputPort", event);
		}
	}

	private void setModelonEvent(String name, InputEvent event) {
		if (event.getComponent() instanceof InputTextField) {
			InputTextField text = (InputTextField) event.getComponent();

			if (!text.getText().isEmpty())
				model.set(name, text.getText());
			else
				model.remove(name);

		}
	}

	private void updateForm() {

		inputPort.setText(model.get("inputPort", String.valueOf(DEFAULT_PORT)));

	}

	public boolean isEmptyOfString(String string) {
		if (string == null)
			return true;

		if (string == "")
			return true;

		return false;

	}

	public String[] readScriptFile(String filename) {
		try {

			BufferedReader br = new BufferedReader(
					new InputStreamReader(this.getClass().getResourceAsStream(filename)));

			ArrayList<String> list = new ArrayList<String>();

			String addstr;
			while ((addstr = br.readLine()) != null) {
				list.add(addstr);
			}

			br.close();
			String[] res = list.toArray(new String[0]);
			return res;

		} catch (IOException e) {
			return null;
		}

	}

	public DialogScriptInstallationNodeContribution(URCapAPI api, DataModel model) {
		this.api = api;
		this.model = model;

		int port = model.get("inputPort", DEFAULT_PORT);

		try {
			DialogScriptXmlRpcServer xmlRpcServer = new DialogScriptXmlRpcServer(this, port);
			xmlRpcServer.start();
			System.out.println("DialogScriptXmlRpcServer started...");

		} catch (Exception e) {
		}

	}

	@Override
	public void openView() {
		updateForm();
	}

	@Override
	public void closeView() {

	}

	public boolean isDefined() {
		return true;
	}

	@Override
	public void generateScript(ScriptWriter writer) {

		String[] scripts = readScriptFile("/com/takaaki/urcap/" + ARTIFACTID + "/impl/dialogscript.script");

		for (String str : scripts) {
			str = str.replace("{port}", String.valueOf(model.get("inputPort", DEFAULT_PORT)));

			writer.appendLine(str);
		}

	}
}
