package tpmv.bc;

import tpmv.elements.CPU;
import tpmv.exceptions.StackException;

public class Out implements ByteCode{
	public Out(){}
	
	/**
	 * Extrae el tope de la pila y lo imprime
	 * volviendolo a colocar en el tope
	 */
	@Override
	public void execute(CPU cpu) 
			throws StackException {
		
		cpu.out();
	}

	/**
	 * Verifica que es el bytecode OUT
	 */
	@Override
	public ByteCode parse(String[] words) {
		if (words.length==1 && words[0].equalsIgnoreCase("OUT"))
			return new Out(); 
		else return null;
	}
	
	public String toString(){
		return "OUT";
	}
	

}
