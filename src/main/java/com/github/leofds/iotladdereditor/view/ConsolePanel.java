/*******************************************************************************
 * Copyright (C) 2021 Leonardo Fernandes
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 ******************************************************************************/
package com.github.leofds.iotladdereditor.view;

import java.awt.BorderLayout;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;
import javax.swing.text.DefaultCaret;
import java.awt.Font;
import javax.swing.JPopupMenu;
import java.awt.Component;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JMenuItem;
import javax.swing.AbstractAction;
import java.awt.event.ActionEvent;
import javax.swing.Action;
import java.awt.event.ActionListener;

public class ConsolePanel extends JPanel{

	private static final long serialVersionUID = 1L;
	private JScrollPane scroll;
	private JTextArea text;
	private DefaultCaret caret;
	private JPopupMenu popupMenu;
	private JMenuItem mntmNewMenuItem;
	private JMenuItem mntmNewMenuItem_1;

	public ConsolePanel() {
		super(new BorderLayout(0,0));

		setBorder(new EmptyBorder(0, 0, 0, 0));

		JPanel textPanel = new JPanel();
		add(textPanel, BorderLayout.CENTER);
		textPanel.setLayout(new BorderLayout(0, 0));

		text = new JTextArea();
		text.setLineWrap(true);
		text.setFont(new Font("Verdana", Font.PLAIN, 12));
		text.setEditable(false);
		caret = (DefaultCaret) text.getCaret();
		caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
		scroll = new JScrollPane(text,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		
		popupMenu = new JPopupMenu();
		popupMenu.setLabel("");
		addPopup(text, popupMenu);
		
		mntmNewMenuItem = new JMenuItem("CLEAR");
		mntmNewMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				clear();
			}
		});
		popupMenu.add(mntmNewMenuItem);
		
		mntmNewMenuItem_1 = new JMenuItem("Copy to clipboard");
		mntmNewMenuItem_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				text.selectAll();
				text.copy();
			}
		});
		popupMenu.add(mntmNewMenuItem_1);

		textPanel.add(scroll, BorderLayout.CENTER);
	}

	private JScrollPane getScroll(){
		return scroll;
	}

	public void append(String msg){
		text.append(msg);
	}

	public void clear(){
		text.setText("");
	}
	private static void addPopup(Component component, final JPopupMenu popup) {
		component.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				if (e.isPopupTrigger()) {
					showMenu(e);
				}
			}
			public void mouseReleased(MouseEvent e) {
				if (e.isPopupTrigger()) {
					showMenu(e);
				}
			}
			private void showMenu(MouseEvent e) {
				popup.show(e.getComponent(), e.getX(), e.getY());
			}
		});
	}
}
