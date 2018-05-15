package tpmv.inst.assigments;

import tpmv.bc.ByteCode;
import tpmv.bc.Load;
import tpmv.elements.Compiler;

public class Variable implements Term {
	private String varName;
	
	
	public Variable(String st){
		this.varName=st;
	}

	@Override
	public ByteCode compile(Compiler compile) {
		int i=compile.indexOf(this.varName);
		
		return new Load(i);
	}

	@Override
	public Term parse(String term) {
		if (term.length()!=1) 
			return null;
		
		else{
			char name = term.charAt(0);
			if ('a' <= name && name <='z')
				return new Variable(term);
			else 
				return null;
		}
	}

	public String toString(){
		return this.varName;
	}
	
	
	
	
}
