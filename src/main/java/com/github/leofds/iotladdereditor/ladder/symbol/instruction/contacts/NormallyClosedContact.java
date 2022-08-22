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
package com.github.leofds.iotladdereditor.ladder.symbol.instruction.contacts;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Line2D;
import java.util.ArrayList;
import java.util.List;

import com.github.leofds.iotladdereditor.compiler.domain.GenContext;
import com.github.leofds.iotladdereditor.compiler.domain.Kind;
import com.github.leofds.iotladdereditor.compiler.domain.Quadruple;
import com.github.leofds.iotladdereditor.compiler.domain.Symbol;
import com.github.leofds.iotladdereditor.compiler.domain.SymbolTable;
import com.github.leofds.iotladdereditor.compiler.generator.factory.QuadrupleFactory;

public class NormallyClosedContact extends ContactInstruction{
	
	private static final long serialVersionUID = 1L;

	public NormallyClosedContact() {
		super();
		setLabel("|/|");
	}

	@Override
	public String getTypeStr(){
		return "NC";
	}

	@Override
	public void paint(Graphics2D g2d){
		super.paint(g2d);
		g2d.setColor(new Color(0, 0, 255));
		g2d.setStroke(new BasicStroke(1));
		g2d.draw(new Line2D.Double(0, blockHeight/2, blockWidth/3, blockHeight/2));
		g2d.draw(new Line2D.Double(blockWidth-blockWidth/3, blockHeight/2, blockWidth, blockHeight/2));
		g2d.draw(new Line2D.Double(blockWidth/3, blockHeight/3, blockWidth/3, blockHeight/1.5f));
		g2d.draw(new Line2D.Double(blockWidth-blockWidth/3, blockHeight/3, blockWidth-blockWidth/3, blockHeight/1.5f));
		g2d.draw(new Line2D.Double(blockWidth/3, blockHeight/1.5f, blockWidth-blockWidth/3, blockHeight/3));
	}

	@Override
	public List<Quadruple> generateIR(GenContext context) {
		SymbolTable symbolTable = context.getSymbolTable();
		Symbol comment 		= symbolTable.addLiteral(getLabel()+" ("+getMemory()+")");
		Symbol value 		= symbolTable.add(new Symbol(getMemory().getName(), Kind.VARIABLE, getMemory().getType(), GenContext.GLOBAL_SCOPE));
		Symbol label 		= symbolTable.addLabel(context.genLabel(), context.getScope());
		Symbol status		= symbolTable.addBoolVar(context.getCurrentStatus(), context.getScope());
		Symbol zero			= symbolTable.addIntConst("0");
		
		List<Quadruple> quadruples = new ArrayList<Quadruple>();
		quadruples.add( QuadrupleFactory.createComment(comment) );
		quadruples.add( QuadrupleFactory.createIfFalse(value, label) );
		quadruples.add( QuadrupleFactory.createAssignment(zero, status) );
		quadruples.add( QuadrupleFactory.createLabel(label) );
		return quadruples;
	}
}
