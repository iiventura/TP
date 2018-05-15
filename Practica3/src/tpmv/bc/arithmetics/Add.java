package tpmv.bc.arithmetics;

import tpmv.bc.ByteCode;
import tpmv.elements.CPU;
import tpmv.exceptions.StackException;

public class Add extends Arithmetics{
	public Add(){}

	/**
	 * Ejecuta la suma y la almacena en la pila
	 */
	@Override
	protected void operates(CPU cpu, int sc, int c)
			throws StackException {
		cpu.push(sc+c);
	}
	
	/**
	 * Verifica que es el Add y lo crea
	 */
	@Override
	protected ByteCode parseOperation(String word) {
		if (word.equalsIgnoreCase("ADD"))
			return new Add(); 
		else return null;
	}
	
	public String toString(){
		return "ADD";
	}

}
