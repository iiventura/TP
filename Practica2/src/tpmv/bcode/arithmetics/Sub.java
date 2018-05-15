package tpmv.bcode.arithmetics;

import tpmv.bcode.Arithmetics;
import tpmv.bcode.ByteCode;
import tpmv.program.CPU;

public class Sub extends Arithmetics {
	/**
	 * Verifica que es un SUB
	 */
	@Override
	protected ByteCode parseAux(String word) {
		if (word.equalsIgnoreCase("SUB"))
			return new Sub(); 
		else return null;
	}
	/**
 	* Envia el bytecode SUB
 	*/
	@Override
	protected String toStringAux() {
		return "SUB";
	}
	/**
	 * Ejecuta la operacion resta
 	*/
	@Override
	protected boolean operation(CPU cpu, int a, int b) {
		// TODO Auto-generated method stub
		return cpu.push(a-b);
	}

	
}
