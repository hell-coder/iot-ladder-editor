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
package com.github.leofds.iotladdereditor.ladder.symbol.instruction.count.view;

import java.awt.Dimension;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import com.github.leofds.iotladdereditor.application.Mediator;
import com.github.leofds.iotladdereditor.device.DeviceMemory;
import com.github.leofds.iotladdereditor.i18n.Strings;
import com.github.leofds.iotladdereditor.ladder.rung.Rung;
import com.github.leofds.iotladdereditor.ladder.symbol.instruction.LadderInstruction;
import com.github.leofds.iotladdereditor.ladder.view.DialogScreen;
import com.github.leofds.iotladdereditor.ladder.view.factory.FieldFactory;
import com.github.leofds.iotladdereditor.ladder.view.listener.ComboCustomActionListener;
import com.github.leofds.iotladdereditor.ladder.view.listener.FloatKeyListener;

public class CountPropertyScreen extends DialogScreen{

	private static final long serialVersionUID = 1L;
	private DeviceMemory memory;
	private JFormattedTextField ftName;
	private JTextField textPreset;
	private JComboBox comboPreset;
	private DefaultComboBoxModel modelPreset = new DefaultComboBoxModel();
	private JFormattedTextField ftAccum;
	
	public CountPropertyScreen(String title,DeviceMemory memory) {
		super(title);
		this.memory = memory;

		JPanel panel = new JPanel(null);
		panel.setPreferredSize(new Dimension(200, 160));
		panel.setBorder(new EmptyBorder(5, 5, 5, 5));

		JLabel lbName = new JLabel("Number:",SwingConstants.RIGHT);
		lbName.setBounds(10, 10, 56, 14);
		panel.add(lbName);

		JLabel lbPreset = new JLabel("Preset:",SwingConstants.RIGHT);
		lbPreset.setBounds(10, 35, 56, 14);
		panel.add(lbPreset);

		JLabel lbAccum = new JLabel("Accum:",SwingConstants.RIGHT);
		lbAccum.setBounds(10, 60, 56, 14);
		panel.add(lbAccum);

		ftName = FieldFactory.createLongField(1, 999);
		ftName.setHorizontalAlignment(SwingConstants.RIGHT);
		ftName.setBounds(76, 8, 86, 20);
		panel.add(ftName);

/*		
		ftPreset = FieldFactory.createLongField(-999999999, 999999999);
		ftPreset.setHorizontalAlignment(SwingConstants.RIGHT);
		ftPreset.setBounds(76, 33, 86, 20);
		panel.add(ftPreset);
*/
		comboPreset = new JComboBox(modelPreset);
		comboPreset.setBounds(76, 33, 86, 20);
		comboPreset.addActionListener(new ComboCustomActionListener(comboPreset));
		textPreset = (JTextField)comboPreset.getEditor().getEditorComponent();
		comboPreset.getEditor().getEditorComponent().addKeyListener( new FloatKeyListener( textPreset ) );
		modelPreset.addElement(Strings.constantValue());
		comboPreset.setSelectedIndex(0);
		panel.add(comboPreset);

		ftAccum = FieldFactory.createLongField(-999999999, 999999999);
		ftAccum.setHorizontalAlignment(SwingConstants.RIGHT);
		ftAccum.setBounds(76, 58, 86, 20);
		panel.add(ftAccum);

		JSeparator separator = new JSeparator();
		separator.setBounds(5, 110, 190, 2);
		panel.add(separator);

		JButton btnSaveButton = new JButton(Strings.save());
		btnSaveButton.setBounds(10, 120, 89, 23);
		btnSaveButton.addActionListener(getSaveAction());
		panel.add(btnSaveButton);

		JButton btnCancelButton = new JButton(Strings.cancel());
		btnCancelButton.setBounds(100, 120, 89, 23);
		btnCancelButton.addActionListener(getCancelAction());
		panel.add(btnCancelButton);
		
		setContentPane(panel);
	}
	
	@Override
	public boolean validateFilds() {
		String name = "C"+ftName.getText();

		if(name.equals("C")){
			JOptionPane.showMessageDialog(null, Strings.invalidInstructionName());
			ftName.setText("");
			return false;
		}
		Mediator me = Mediator.getInstance();
		for(Rung rung: me.getProject().getLadderProgram().getRungs()){
			for(LadderInstruction instruction: rung){
				DeviceMemory memory = instruction.getMemory();
				if(memory != null){
					if(!memory.equals(this.memory)){
						if(memory.getName().equals(name)){
							JOptionPane.showMessageDialog(null, Strings.instructionAlreadyExists());
							return false;
						}
					}
				}
			}
		}
		if(ftName.getText().isEmpty() || ftAccum.getText().isEmpty()){
			JOptionPane.showMessageDialog(null, Strings.invalidInstructionAttribute());
			return false;
		}
		return true;
	}
	
	public void addPreset(DeviceMemory memory){
		modelPreset.addElement(memory);
	}

	public DeviceMemory getSelectedPreset(){
		int index = comboPreset.getSelectedIndex();
		if(index==0||index==-1){
			String str = textPreset.getText();
			if(str.contains(".")) {
				return new DeviceMemory(""+Float.parseFloat(textPreset.getText()), Float.class);
			}
			return new DeviceMemory(""+Integer.parseInt(textPreset.getText()), Integer.class);
		}
		return (DeviceMemory) comboPreset.getSelectedItem();
	}

	public void setPreset(DeviceMemory memory){
		for(int i=0;i<modelPreset.getSize();i++){
			if(modelPreset.getElementAt(i) instanceof DeviceMemory){
				DeviceMemory mem = (DeviceMemory) modelPreset.getElementAt(i);
				if(mem.equals(memory)){
					comboPreset.setSelectedIndex(i);
					return;
				}
			}
		}
		comboPreset.setSelectedItem(memory);
	}

	public String getName(){
		return "C"+ftName.getText();
	}
	
	public void setName(String name){
		ftName.setText(name.isEmpty() ? "" : name.substring(1));
	}
	
	public int getAccum() {
		return Integer.parseInt(ftAccum.getText());
	}

	public void setAccum(int accum) {
		this.ftAccum.setText(""+accum);
	}
}
