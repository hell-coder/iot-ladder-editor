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
package com.github.leofds.iotladdereditor.device;

import java.io.Serializable;

public class PeripheralIO extends DeviceMemory implements Serializable{

	private static final long serialVersionUID = 3470318238269022501L;
	private String ioSymbol;
	private String path;
	private String description;
	private IO io;
	
	public PeripheralIO(String addr, Class<?> type, String symbol, IO io, String descr) {
		super(addr, type);
		this.ioSymbol = symbol;
		this.path = null;
		this.io = io;
		this.description = descr;
	}
	
//	public PeripheralIO(String addr, Class<?> type, String id, String path, IO io) {
//		super(addr, type);
//		this.ioSymbol = id;
//		this.path = path;
//		this.io = io;
//	}

	public String getSymbol() {
		return ioSymbol;
	}

	public void setSymbol(String pin) {
		this.ioSymbol = pin;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public IO getIo() {
		return io;
	}

	public void setIo(IO io) {
		this.io = io;
	}
}
