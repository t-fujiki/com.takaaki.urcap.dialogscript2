package com.takaaki.urcap.dialogscript2.impl.dialog;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.border.LineBorder;
import java.awt.Window;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import com.takaaki.urcap.dialogscript2.impl.DialogScriptInstallationNodeContribution;
import com.ur.urcap.api.domain.userinteraction.keyboard.KeyboardInputCallback;
import com.ur.urcap.api.domain.userinteraction.keyboard.KeyboardInputFactory;
import com.ur.urcap.api.domain.userinteraction.keyboard.KeyboardNumberInput;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;

public class SampleDialogFrame extends JFrame {

    private JPanel panel;

    private JLabel label_title;
    private JLabel label_qty;

    private JTextField txt_qty;
    private JButton btn_ok;
    private JButton btn_cancel;

    private Box box_title;
    private Box box_qty;
    private Box box_okcancel;

    private DialogScriptInstallationNodeContribution contribution;
    private KeyboardInputFactory keyboardfactory;
    private KeyboardNumberInput<Integer> numberkeyboard;

    public int inputQTY;

    private String OK = "OK";
    private String CANCEL = "Cancel";
    private String QTY = "QTY";
    private String TITLE = "Please input number of QTY.";

    public int response;

    public static int RESPONSE_OK = 1;
    public static int RESPONSE_CANCEL = 0;

    private final String DEFAULT_FONT = "IPAexGothic";
    private Color default_backcolor = new Color(204, 255, 204);

    private Font title_font = new Font(DEFAULT_FONT, Font.BOLD, 15);
    private Font default_font = new Font(DEFAULT_FONT, Font.PLAIN, 15);

    public SampleDialogFrame(DialogScriptInstallationNodeContribution contribution) {
        this.contribution = contribution;

        createForm();

        setEventHandlers();
    }

    /**
     * Open Dialog
     * 
     * @return
     */
    public int showDialog() {
        this.setVisible(true);

        while (true) {
            Thread.yield();
            if (!this.isVisible()) {
                System.out.println("frame closed");
                System.out.println(response);
                return response;
            }
        }

    }

    private void createForm() {
        panel = new JPanel();

        label_title = new JLabel(TITLE);
        label_qty = new JLabel(QTY);

        txt_qty = new JTextField();

        btn_ok = new JButton(OK);
        btn_cancel = new JButton(CANCEL);

        box_title = Box.createHorizontalBox();
        box_qty = Box.createHorizontalBox();
        box_okcancel = Box.createHorizontalBox();

        this.setAlwaysOnTop(true);
        this.setSize(450, 120);
        this.setLocationRelativeTo(null);
        this.setUndecorated(true);
        this.setFont(default_font);

        panel.setBackground(default_backcolor);
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(new LineBorder(Color.BLACK, 2));

        label_title.setMaximumSize(new Dimension(350, 30));
        label_title.setFont(title_font);

        label_qty.setMaximumSize(new Dimension(100, 30));
        label_qty.setFont(default_font);

        txt_qty.setMaximumSize(new Dimension(300, 30));
        txt_qty.setFont(default_font);

        btn_ok.setMaximumSize(new Dimension(120, 40));
        btn_ok.setFont(default_font);
        btn_cancel.setMaximumSize(new Dimension(120, 40));
        btn_cancel.setFont(default_font);

        box_title.setAlignmentX(Component.LEFT_ALIGNMENT);
        box_title.add(setHorizonalSpace(10));
        box_title.add(label_title);

        box_qty.setAlignmentX(Component.LEFT_ALIGNMENT);
        box_qty.add(setHorizonalSpace(10));
        box_qty.add(label_qty);
        box_qty.add(setHorizonalSpace(10));
        box_qty.add(txt_qty);

        box_okcancel.setAlignmentX(Component.LEFT_ALIGNMENT);
        box_okcancel.add(setHorizonalSpace(10));
        box_okcancel.add(btn_ok);
        box_okcancel.add(setHorizonalSpace(10));
        box_okcancel.add(btn_cancel);
        box_okcancel.add(setHorizonalSpace(40));

        panel.add(setVerticalSpace(5));
        panel.add(box_title);
        panel.add(setVerticalSpace(5));
        panel.add(box_qty);
        panel.add(setVerticalSpace(5));
        panel.add(box_okcancel);

        this.add(panel);

    }

    private void setEventHandlers() {
        keyboardfactory = contribution.api.getInstallationAPIProvider().getUserInterfaceAPI().getUserInteraction()
                .getKeyboardInputFactory();
        numberkeyboard = keyboardfactory.createIntegerKeypadInput();

        txt_qty.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                numberkeyboard.show(txt_qty, onInputKeyboard_txt_qty());
            }
        });

        btn_ok.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (!((JButton) e.getSource()).isEnabled())
                    return;

                response = RESPONSE_OK;

                getWindow(e).setVisible(false);
            }
        });

        btn_cancel.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (!((JButton) e.getSource()).isEnabled())
                    return;

                response = RESPONSE_CANCEL;

                getWindow(e).setVisible(false);

            }
        });

    }

    private KeyboardInputCallback<Integer> onInputKeyboard_txt_qty() {
        return new KeyboardInputCallback<Integer>() {
            @Override
            public void onOk(Integer value) {
                txt_qty.setText(String.valueOf(value));
                inputQTY = value;
            }
        };
    }

    private Window getWindow(MouseEvent mouseEvent) {
        Component comp = (Component) mouseEvent.getComponent();
        return SwingUtilities.getWindowAncestor(comp);
    }

    private Component setVerticalSpace(int height) {
        return Box.createRigidArea(new Dimension(0, height));

    }

    private Component setHorizonalSpace(int width) {
        return Box.createRigidArea(new Dimension(width, 0));

    }
}
