package com.explodingpixels.macwidgets;

import java.awt.BorderLayout;
import java.util.Arrays;
import java.util.List;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFrame;

import com.explodingpixels.widgets.PopupButton;

public class DComponentBottomBar {

	public static void main(String[] args) {

		List<String> list = Arrays.asList("Some Item One", "Some Item Two",
				"Some Item Three", "Some Item Four");
		PopupButton<String> popupButton = new PopupButton<String>(list.get(0),
				list);

		List<String> list2 = Arrays.asList("Custom...", "400%", "200%", "100%",
				"75%", "50%", "25%");
		PopupButton<String> placard2 = new PopupButton<String>(list2.get(3),
				list2);

		JComboBox comboBox = new JComboBox(list2.toArray());

		JButton gradientButton = new JButton("Add");
		gradientButton.putClientProperty("JButton.buttonType", "gradient");

		// JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
		ComponentBottomBar componentBottomBar = MacWidgetFactory
				.createComponentStatusBar();

		componentBottomBar.addComponentToLeftWithBorder(popupButton
				.getComponent());
		componentBottomBar
				.addComponentToLeftWithBorder(placard2.getComponent());
		componentBottomBar.addComponentToLeftWithBorder(gradientButton);
		componentBottomBar.addComponentToRight((JComponent) Box
				.createHorizontalStrut(14));
		componentBottomBar.addComponentToCenterWithBorder(comboBox);

		JFrame frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.add(componentBottomBar.getComponent(), BorderLayout.SOUTH);
		frame.setSize(640, 480);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);

	}

}
