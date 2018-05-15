package tpmv.bcode.arithmetics;

import tpmv.bcode.Arithmetics;
import tpmv.bcode.ByteCode;
import tpmv.program.CPU;


public class Add extends Arithmetics {
	/**
	 * Verifica que es un ADD
	 */
	@Override
	protected ByteCode parseAux(String word) {
		if (word.equalsIgnoreCase("ADD"))
			return new Add(); 
		else return null;
	}

	/**
	 * Envia el bytecode ADD
	 */
	@Override
	protected String toStringAux() {
		return "ADD";
	}
	
	/**
	 * Ejecuta la suma 
	 */
	@Override
	protected boolean operation(CPU cpu, int a, int b) {
		return cpu.push(a+b);
	}

	
	

}
