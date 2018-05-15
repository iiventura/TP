package tpmv.bc;

import tpmv.elements.CPU;


public class Halt implements ByteCode{
	public Halt(){}

	/**
	 * Activa el Halt de la CPU
	 */
	@Override
	public void execute(CPU cpu){
		cpu.finish();
	}

	/**
	 * Verifica si es un  HALT
	 */
	@Override
	public ByteCode parse(String[] words) {
		if (words.length==1 && words[0].equalsIgnoreCase("HALT"))
			return new Halt(); 
		else return null;
	}

	
	public String toString(){
		return "HALT";
	}
}
