package com.takaaki.urcap.dialogscript2.impl.xmlrpc;

import com.takaaki.urcap.dialogscript2.impl.DialogScriptInstallationNodeContribution;
import com.takaaki.urcap.dialogscript2.impl.dialog.SampleDialogFrame;

public class DialogScriptHandler {

    // 値のやり取りは静的変数にて行う。
    // 変数名は_****と頭にアンダースコアを付ける。

    private static DialogScriptInstallationNodeContribution _contribution;

    /**
     * ハンドラ初期化
     * 
     * @param contribution 設定機能
     * @param csvFile      製品情報呼び出し用CSVファイル
     */
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