package com.takaaki.urcap.dialogscript2.impl.xmlrpc;

import com.takaaki.urcap.dialogscript2.impl.DialogScriptInstallationNodeContribution;
import com.takaaki.urcap.dialogscript2.impl.dialog.SampleDialogFrame;

public class DialogScriptHandler {


    private static DialogScriptInstallationNodeContribution _contribution;

    public static void initialize(DialogScriptInstallationNodeContribution contribution) {
        _contribution = contribution;

    }

    public int show() throws DialogScriptHandlerException {

        SampleDialogFrame frame = new SampleDialogFrame(_contribution);

        int res = frame.showDialog();

        if (res == SampleDialogFrame.RESPONSE_OK) {
            int qty = frame.inputQTY;
            frame.dispose();

            return qty;
        } else {
            frame.dispose();
            return 0;
        }

    }
}