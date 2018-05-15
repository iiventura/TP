package tpmv.bc.arithmetics;

import tpmv.bc.ByteCode;
import tpmv.elements.CPU;
import tpmv.exceptions.StackException;

public class Mul extends Arithmetics{
	
	/**
	 * Realiza la multiplicacion y apila 
	 */
	@Override
	protected void operates(CPU cpu, int sc, int c)
			throws StackException {
		cpu.push(sc*c);
	
	}

	/**
	 * Verifica que es MUL
	 */
	@Override
	protected ByteCode parseOperation(String word) {
		if (word.equalsIgnoreCase("MUL"))
			return new Mul(); 
		else return null;
	}
	
	public String toString(){
		return "MUL";
	}

}
