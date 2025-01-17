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
package com.github.leofds.iotladdereditor.view.event;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.Observable;
import java.util.Observer;
import java.net.URL;
import java.net.HttpURLConnection;

import com.github.leofds.iotladdereditor.application.Mediator;
import com.github.leofds.iotladdereditor.compiler.Compiler;
import com.github.leofds.iotladdereditor.view.event.Subject.SubMsg;
import com.github.leofds.iotladdereditor.compiler.generator.JsonGenerator;
import com.github.leofds.iotladdereditor.ladder.LadderProgram;
import com.github.leofds.iotladdereditor.ladder.ProgramProperties;


public class FlashEvent implements Observer {

	private Subject subject;
	private LadderProgram ladderProgram;

	public FlashEvent(Subject subject) {
		subject.addObserver(this);
		this.subject = subject;
	}

	private void Flash() {

		Mediator me = Mediator.getInstance();
		
		ladderProgram = Mediator.getInstance().getProject().getLadderProgram();
		ProgramProperties properties = ladderProgram.getProperties();

		try {
			byte[] JsonProgram = JsonGenerator.generate(me.getProject().getLadderProgram()).getBytes(StandardCharsets.UTF_8);;

			URL url = new URL("http://" +properties.getipAddress()+ "/ladder_program");
			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			con.setRequestMethod("POST");
			con.setDoOutput(true);
			con.setFixedLengthStreamingMode(JsonProgram.length);
//			http.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
			OutputStream os = con.getOutputStream();
			os.write(JsonProgram);


			int status = con.getResponseCode();
			me.outputConsoleMessage("Code:" + status);

			BufferedReader in = new BufferedReader(
			new InputStreamReader(con.getInputStream()));
			String inputLine;
			StringBuffer content = new StringBuffer();
			while ((inputLine = in.readLine()) != null) {
				content.append(inputLine);
			}
			in.close();
			con.disconnect();
			me.outputConsoleMessage(content.toString());
		} catch (Exception e) {
			e.printStackTrace();
			me.outputConsoleMessage(e.getMessage());
		}

	}


	@Override
	public void update(Observable o, Object arg) {
		if(subject instanceof Subject && arg instanceof SubMsg){
			switch((SubMsg) arg) {
			case FLASH:
				Flash();
				break;
			}
		}
	}

}
