package tpmv.bcode.storage;

import tpmv.bcode.ByteCode;
import tpmv.program.CPU;

public class Halt extends ByteCode{
	
	/**
	 * Activa el Halt de la CPU
	 */
	@Override
	public boolean execute(CPU cpu) {
		cpu.halt();
		return true;
	}

	/**
	 * Verifica es que es un bytecode HALT
	 */
	@Override
	public ByteCode parse(String[] words) {
		if (words.length==1 && words[0].equalsIgnoreCase("HALT"))
			return new Halt(); 
		else return null;
	}
	
	
	/**
	 * Envia el bytecode HALT
	 */
	public String toString(){
		return "HALT";
	}

}
