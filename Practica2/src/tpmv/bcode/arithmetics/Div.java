package tpmv.bcode.arithmetics;

import tpmv.bcode.Arithmetics;
import tpmv.bcode.ByteCode;
import tpmv.program.CPU;

public class Div extends Arithmetics {

	/**
	 * Verifica que es DIV
	 */
	@Override
	protected ByteCode parseAux(String word) {
		if (word.equalsIgnoreCase("DIV"))
			return new Div(); 
		else return null;
	}

	/**
	 *Envia el bytecode DIV
	 */
	@Override
	protected String toStringAux() {
		return "DIV";
	}
	

	/**
	 *Realiza la operacion dividir
	 */
	@Override
	protected boolean operation(CPU cpu, int a, int b) {
		if(b!=0){
			return cpu.push(a/b);
		}else return false;
	}

	

}
