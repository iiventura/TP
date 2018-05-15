package tpmv.bcode.storage;

import tpmv.bcode.ByteCode;
import tpmv.program.CPU;

public class Out extends ByteCode {

	/**
	 * Extrae el tope de la pila y lo imprime
	 * volviendolo a colocar en el tope
	 */
	@Override
	public boolean execute(CPU cpu) {
		int top=cpu.pop();
		cpu.push(top);
		System.out.println(top);
		cpu.increaseProgramCounter();
		return true;
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
	
	/**
	 * Envia el bytecode OUT
	 */
	public String toString(){
		return "OUT";
	}

	
}
