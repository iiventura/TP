package tpmv.inst;

import tpmv.elements.LexicalParser;
import tpmv.elements.Compiler;
import tpmv.exceptions.ArrayException;

public interface Instruction {
	public abstract void compile(Compiler compiler)
		throws ArrayException;
	public abstract Instruction lexParse(String[] words, LexicalParser lexParser);
	

}
