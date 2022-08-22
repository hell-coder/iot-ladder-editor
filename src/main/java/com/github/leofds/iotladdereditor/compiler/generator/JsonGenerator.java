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
package com.github.leofds.iotladdereditor.compiler.generator;

import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;

import com.github.leofds.iotladdereditor.compiler.domain.GenContext;
import com.github.leofds.iotladdereditor.compiler.domain.ProgramFunc;
import com.github.leofds.iotladdereditor.compiler.domain.Quadruple;
import com.github.leofds.iotladdereditor.compiler.domain.Symbol;
import com.github.leofds.iotladdereditor.compiler.domain.SymbolTable;
import com.github.leofds.iotladdereditor.compiler.generator.factory.QuadrupleFactory;
import com.github.leofds.iotladdereditor.device.DeviceMemory;
import com.github.leofds.iotladdereditor.ladder.LadderProgram;
import com.github.leofds.iotladdereditor.ladder.rung.Rung;
import com.github.leofds.iotladdereditor.ladder.rung.Rungs;
import com.github.leofds.iotladdereditor.ladder.symbol.instruction.LadderInstruction;

import com.github.leofds.iotladdereditor.util.FileUtils;
import org.json.*;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.github.leofds.iotladdereditor.application.Mediator;
import com.github.leofds.iotladdereditor.application.ProjectContainer;
import com.github.leofds.iotladdereditor.compiler.analizer.SemanticAnalyzer;
import com.github.leofds.iotladdereditor.compiler.domain.IR;
import com.github.leofds.iotladdereditor.compiler.generator.CodeGenerator;
import com.github.leofds.iotladdereditor.compiler.generator.IRGenerator;
import com.github.leofds.iotladdereditor.compiler.generator.JsonGenerator;
import com.github.leofds.iotladdereditor.compiler.generator.factory.CodeGeneratorFactory;
import com.github.leofds.iotladdereditor.i18n.Strings;
import com.github.leofds.iotladdereditor.util.FileUtils;


public class JsonGenerator {
	public static String generate(LadderProgram ladderProgram){
		JSONObject jo = new JSONObject();

		JSONObject jo_vars = new JSONObject();

		/* Boolean */
		JSONArray ja_mem = new JSONArray();
		for (DeviceMemory m: ladderProgram.getBooleanMemory()) {
			JSONObject jo_mem = new JSONObject();

			jo_mem.put("name", "");
			jo_mem.put("retain", false);
			jo_mem.put("val", false);
			
			ja_mem.put(jo_mem);
		}
		jo_vars.put("bool", ja_mem);

		/* Integer */
		ja_mem = new JSONArray();
		for (DeviceMemory m: ladderProgram.getIntegerMemory()) {
			JSONObject jo_mem = new JSONObject();

			jo_mem.put("name", "");
			jo_mem.put("retain", false);
			jo_mem.put("val", 0);
			
			ja_mem.put(jo_mem);
		}
		jo_vars.put("dint", ja_mem);

		/* Float */
		ja_mem = new JSONArray();
		for (DeviceMemory m: ladderProgram.getFloatMemory()) {
			JSONObject jo_mem = new JSONObject();

			jo_mem.put("name", "");
			jo_mem.put("retain", false);
			jo_mem.put("val", 0.0);
			
			ja_mem.put(jo_mem);
		}
		jo_vars.put("lreal", ja_mem);		

		jo.put("variables", jo_vars);

		JSONArray ja_rungs = new JSONArray();
		for (Rung rung: ladderProgram.getRungs()) {
			JSONArray ja_els = new JSONArray();
			int id = 0;

			JSONObject jo_el = new JSONObject();
			jo_el.put("id", id++);
			jo_el.put("name", "Left Rail");
			jo_el.put("type", "RAIL");
			jo_el.put("data", "{}");
			jo_el.put("conn", new JSONArray().put(id));					
			ja_els.put(jo_el);

			for (LadderInstruction Ir = rung.getFirst(); Ir != null; Ir = Ir.getNext()) {
				if (Ir.getLabel().isEmpty()) {
					continue;
				}

				jo_el = new JSONObject();
				jo_el.put("id", id++);
				jo_el.put("name", "");
				jo_el.put("type", Ir.getTypeStr());
				jo_el.put("data", new JSONObject(Ir.getData()));
				jo_el.put("conn", new JSONArray().put(id));		
				
				ja_els.put(jo_el);
			}
			ja_rungs.put(new JSONObject().put("elements", ja_els));

			
			jo_el = new JSONObject();
			jo_el.put("id", id++);
			jo_el.put("name", "Right Rail");
			jo_el.put("type", "RAIL");
			jo_el.put("data", "{}");
			jo_el.put("conn", new JSONArray());					
			ja_els.put(jo_el);

		}
		jo.put("rungs", ja_rungs);

		try {
			FileUtils.createFile("out/ladder_program.json", jo.toString());
		} catch (IOException e) {
			Mediator.getInstance().outputConsoleMessage(Strings.failToCreateFile());
		}

		return jo.toString();

	}
}