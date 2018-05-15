package tpmv.bc.arithmetics;

import tpmv.bc.ByteCode;
import tpmv.elements.CPU;
import tpmv.exceptions.DivByZeroException;
import tpmv.exceptions.StackException;

public class Div extends Arithmetics{
	/**
	 * Se intentan dividir los elementos y apilarlos
	 * si la base es 0 entonces lanza una excepcion
	 */
	@Override
	protected void operates(CPU cpu, int sc, int c) 
			throws StackException, DivByZeroException {
		
		if(c!=0){
			cpu.push(sc/c);
		}else throw new DivByZeroException("Division By Zero Exception");
		
	}

	/**
	 * Verifica que es un DIV
	 */
	@Override
	protected ByteCode parseOperation(String word) {
		if (word.equalsIgnoreCase("DIV"))
			return new Div(); 
		else return null;
	}

	public String toString(){
		return "DIV";
	}
}
