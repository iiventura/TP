package tpmv.inst;

import tpmv.bc.Halt;
import tpmv.elements.Compiler;
import tpmv.elements.LexicalParser;
import tpmv.exceptions.ArrayException;

public class Return implements Instruction {

	public Return(){}

	@Override
	public void compile(Compiler compiler) throws ArrayException {
		compiler.addByteCode(new Halt());

	}

	@Override
	public Instruction lexParse(String[] words, LexicalParser lexParser) {
		if (words.length != 1 || !"return".equalsIgnoreCase(words[0])) 
			return null;
		else{
			lexParser.increaseProgramCounter();
			return new Return();
		}

	}

	public String toString(){
		return "Return";
	}

}
