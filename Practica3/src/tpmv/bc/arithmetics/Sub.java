package tpmv.bc.arithmetics;

import tpmv.bc.ByteCode;
import tpmv.elements.CPU;
import tpmv.exceptions.StackException;

public class Sub extends Arithmetics{
	/**
	 * Realiza la resta de los elementos y los apila
	 */
	@Override
	protected void operates(CPU cpu, int sc, int c) 
			throws StackException {
		cpu.push(sc-c);
		
	}

	/**
	 * Verifica que es SUB
	 */
	@Override
	protected ByteCode parseOperation(String word) {
		if (word.equalsIgnoreCase("SUB"))
			return new Sub(); 
		else return null;
	}

	public String toString(){
		return "SUB";
	}
	
}
