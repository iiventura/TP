package tpmv.bcode.arithmetics;

import tpmv.bcode.Arithmetics;
import tpmv.bcode.ByteCode;
import tpmv.program.CPU;

public class Mul extends Arithmetics {

	/**
	 * Verifica que es MUL
	 */
	@Override
	protected ByteCode parseAux(String word) {
		if (word.equalsIgnoreCase("MUL"))
			return new Mul(); 
		else return null;
	}

	/**
	 *Envia el bytecote MUL
	 */
	@Override
	protected String toStringAux() {
		return "MUL";
	}

	/**
	 * Ejecuta la operacion multiplicar 
	 */
	@Override
	protected boolean operation(CPU cpu, int a, int b) {
		return cpu.push(a*b);
	}

	

}
