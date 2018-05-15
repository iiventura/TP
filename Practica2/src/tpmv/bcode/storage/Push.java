package tpmv.bcode.storage;

import tpmv.bcode.ByteCode;
import tpmv.bcode.ByteCodeOneParameter;
import tpmv.program.CPU;

public class Push extends ByteCodeOneParameter{
	/**
	 * Constructora por defecto
	 */
	public Push(){}
	/**
	 * Constructora con parametro
	 * @param p parametro
	 */
	public Push(int p){super(p);}

	/**
	 * Verifica si es un bytecode PUSH
	 */
	@Override
	protected ByteCode parseAux(String string1, String string2) {
		if(string1.equalsIgnoreCase("PUSH")){
			try{
				int p=Integer.parseInt(string2);
				return new Push(p);
			}catch(Exception e){
				return null;
			}
		}else return null;
	}
	
	/**
	 * Envia el bytecode PUSH 
	 */
	@Override
	protected String toStringAux() {
		return "PUSH";
	}

	/**
	 * Invoca el apilar de la CPU
	 */
	@Override
	public boolean execute(CPU cpu) {
		boolean e=cpu.push(this.param);
		cpu.increaseProgramCounter();
		return e;
	}

}
