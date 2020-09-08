package com.takaaki.urcap.dialogscript2.impl.xmlrpc;

import org.apache.xmlrpc.server.*;
import org.apache.xmlrpc.webserver.WebServer;
import java.io.IOException;

import com.takaaki.urcap.dialogscript2.impl.DialogScriptInstallationNodeContribution;

public class DialogScriptXmlRpcServer extends XmlRpcServer {

    private WebServer webServer;

    public DialogScriptXmlRpcServer(DialogScriptInstallationNodeContribution contribution, int port) throws Exception {

        // ハンドラクラスの初期化-------
        DialogScriptHandler.initialize(contribution);

        // XMLRPCサーバ初期化
        webServer = new WebServer(port);
        XmlRpcServer xmlRpcServer = webServer.getXmlRpcServer();

        // ハンドラ登録
        PropertyHandlerMapping phm = new PropertyHandlerMapping();
        phm.addHandler("dialog", DialogScriptHandler.class);
        xmlRpcServer.setHandlerMapping(phm);

        // XMLRPCサーバ準備
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