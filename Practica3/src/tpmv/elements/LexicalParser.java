package tpmv.elements;


import tpmv.exceptions.ArrayException;
import tpmv.exceptions.LexicalAnalisysException;
import tpmv.inst.Instruction;
import tpmv.inst.InstructionParser;
import tpmv.mv.ParsedProgram;
import tpmv.mv.SourceProgram;

public class LexicalParser {
	private SourceProgram sProgram;
	private int programCounter;
	
	public LexicalParser(){
		this.sProgram=new SourceProgram();
		this.programCounter=0;
	}
	
	public void increaseProgramCounter(){
		this.programCounter++;
	}
	
	public void initialize(SourceProgram sProgram){
		this.sProgram=sProgram;
	}
	
	public void lexicalParser(ParsedProgram pProgram, String stopKey) 
				throws LexicalAnalisysException, ArrayException{
		 boolean stop = false;
			
			while( this.programCounter < sProgram.getSize() && !stop) { //
				String ins = sProgram.getInstruction(this.programCounter);
				String instr=ins.trim();
					if (instr.equalsIgnoreCase(stopKey)) 
						stop = true;
					else{
						Instruction instruction = InstructionParser.parse(instr, this);
						if (instruction == null) 
							throw new LexicalAnalisysException("Lexical Analisys Exception with: "+ instr+ " at line "+this.programCounter);
					else{
						pProgram.addInstruction(instruction); 
						
					}
					
				}
			}
	}
	
	public int getProgramCounter(){
		return this.programCounter;
	}
}


