package com.takaaki.urcap.dialogscript2.impl.xmlrpc;

import org.apache.xmlrpc.server.*;
import org.apache.xmlrpc.webserver.WebServer;
import java.io.IOException;

import com.takaaki.urcap.dialogscript2.impl.DialogScriptInstallationNodeContribution;

public class DialogScriptXmlRpcServer extends XmlRpcServer {

    private WebServer webServer;

    public DialogScriptXmlRpcServer(DialogScriptInstallationNodeContribution contribution, int port) throws Exception {

        DialogScriptHandler.initialize(contribution);

        webServer = new WebServer(port);
        XmlRpcServer xmlRpcServer = webServer.getXmlRpcServer();

        PropertyHandlerMapping phm = new PropertyHandlerMapping();
        phm.addHandler("dialog", DialogScriptHandler.class);
        xmlRpcServer.setHandlerMapping(phm);

        XmlRpcServerConfigImpl config = (XmlRpcServerConfigImpl) xmlRpcServer.getConfig();
        config.setEnabledForExtensions(true);
        config.setContentLengthOptional(false);
    }

    public void start() throws IOException {
        webServer.start();
    }

    public void stop() {
        webServer.shutdown();
    }
}