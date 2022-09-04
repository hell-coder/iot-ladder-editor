
package com.github.leofds.iotladdereditor.device;

public class DeviceFactory {

	public static Device createEsp32(){
		Device device  = new Device("PLCUA");
		Peripheral output = new Peripheral("Output", "Q");
		Peripheral input = new Peripheral("Input", "I");
		
		
		for (int i = 1; i < 9; i++) {
			output.addPeripheralItem(new PeripheralIO("Q" + i, Boolean.class, "", IO.OUTPUT,""));
			}
		
		for (int i = 1; i < 13; i++) {
			input.addPeripheralItem(new PeripheralIO("I"+i, Boolean.class, "", IO.INPUT,""));
			}
		
		device.addPeripheral(output);
		device.addPeripheral(input);
		return device;
	}
}
