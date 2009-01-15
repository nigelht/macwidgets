package com.explodingpixels.macwidgets;

import com.explodingpixels.macwidgets.plaf.GradientButtonUI;
import com.jgoodies.forms.builder.PanelBuilder;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import java.awt.Color;

public class DGradientButtonUI {

    private static JPanel createButtonPanel(
            String description, GradientButtonUI.ButtonType buttonType) {
        // definte the FormLayout columns and rows.
        FormLayout layout = new FormLayout("right:p:grow, 3dlu, left:p:grow",
                "3dlu, p, 0dlu, p, 3dlu");
        layout.setColumnGroups(new int[][]{{1, 3}});
        // create the cell constraints to use in the layout.
        CellConstraints cc = new CellConstraints();
        // create the builder with.
        PanelBuilder builder = new PanelBuilder(layout);

        JButton button = new JButton("Button");
        button.setUI(new GradientButtonUI(buttonType));

        builder.addLabel(description, cc.xy(1, 2));
        builder.add(button, cc.xy(3, 2));

        return builder.getPanel();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {

                JPanel panel = new JPanel();
                panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

//                panel.add();
//                panel.add(createButtonPanel("Dark gradient", GradientButtonUI.ButtonType.DARK));

                // definte the FormLayout columns and rows.
                FormLayout layout = new FormLayout("fill:p:grow", "p, 0dlu, p");
                // create the cell constraints to use in the layout.
                CellConstraints cc = new CellConstraints();
                // create the builder with.
                PanelBuilder builder = new PanelBuilder(layout);

                JPanel light = createButtonPanel("Light gradient", GradientButtonUI.ButtonType.LIGHT);
                light.setBackground(Color.WHITE);

                JPanel dark = createButtonPanel("Dark gradient", GradientButtonUI.ButtonType.DARK);
                dark.setOpaque(false);
//                dark.setBackground(Color.BLACK);

                builder.add(light, cc.xy(1, 1));
                builder.add(dark, cc.xy(1, 3));

                JButton buttonOne = new JButton("Button One");
                buttonOne.setUI(new GradientButtonUI(GradientButtonUI.ButtonType.LIGHT));

                JButton buttonTwo = new JButton("Button Two");
                buttonTwo.setUI(new GradientButtonUI(GradientButtonUI.ButtonType.DARK));

                TriAreaComponent unifiedToolBar = MacWidgetFactory.createUnifiedToolBar();
                unifiedToolBar.addComponentToCenter(buttonOne);
                unifiedToolBar.addComponentToCenter(buttonTwo);

                HudWindow hudWindow = new HudWindow("");
                JDialog dialog = hudWindow.getJDialog();
//                unifiedToolBar.installWindowDraggerOnWindow(frame);
//                MacUtils.makeWindowLeopardStyle(frame.getRootPane());
//                WindowUtils.createAndInstallRepaintWindowFocusListener(frame);

//                frame.add(unifiedToolBar.getComponent(), BorderLayout.NORTH);
                builder.getPanel().setOpaque(false);
                hudWindow.setContentPane(builder.getPanel());
//                frame.setContentPane(new JScrollPane(builder.getPanel()));
                dialog.setSize(500, 400);
                dialog.setLocationRelativeTo(null);
                dialog.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                dialog.setVisible(true);
            }
        });


    }

}
