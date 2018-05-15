package tpmv.inst;

import tpmv.elements.LexicalParser;
import tpmv.inst.assigments.CompoundAssignment;
import tpmv.inst.assigments.SimpleAssignment;
import tpmv.inst.conditionals.IfThen;
import tpmv.inst.conditionals.While;

public class InstructionParser {
	/**
	 * Las Instrucciones evaluadas en el Lexical Parser
	 */
	private final static Instruction[] instructions={new SimpleAssignment(), new CompoundAssignment(), new Write(),
													new Return(), new While(), new IfThen()};
	
	public static Instruction parse(String line, LexicalParser parser){
		//elimina blancos extras
		line=line.trim();
		//descomponer en palabras
		String words[]=line.split(" +");

		boolean found=false;
		int i=0;
		Instruction instr=null;

		while(i<instructions.length && !found){
			instr=instructions[i].lexParse(words, parser);

			if(instr!=null)
				found=true;

					i++;
		}

		return instr;
	}
}
