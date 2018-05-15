package tpmv.inst;

import tpmv.bc.Load;
import tpmv.bc.Out;
import tpmv.elements.Compiler;
import tpmv.elements.LexicalParser;
import tpmv.exceptions.ArrayException;

public class Write implements Instruction {
	private String varName;
	
	public Write(){}
	public Write(String varName){
		this.varName=varName;
	}
	
	@Override
	public void compile(Compiler compiler) throws ArrayException {
		int index = compiler.indexOf(this.varName);
		compiler.addByteCode(new Load(index));
		compiler.addByteCode(new Out());
		
	}

	@Override
	public Instruction lexParse(String[] words, LexicalParser lexParser) {
		if (words.length != 2 || !"write".equals(words[0])) 
			return null;
		else {
			lexParser.increaseProgramCounter();
			return new Write(words[1]);
		}
	}
	
	public String toString(){
		return "Write ("+this.varName+")";
	}

	

}
