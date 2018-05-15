package tpmv.bcode.ujumps;

import tpmv.bcode.ByteCode;
import tpmv.bcode.ByteCodeOneParameter;
import tpmv.program.CPU;

public class GoTo extends ByteCodeOneParameter {
	/**
	 * constructora por defecto
	 */
	public GoTo(){}
	/**
	 * constructora con parametros
	 * @param p parametro
	 */
	public GoTo(int p){super(p);}

	/**
	 * Verifica que es el bytecode GOTO
	 */
	@Override
	protected ByteCode parseAux(String string1, String string2) {
		if(string1.equalsIgnoreCase("GOTO")){
			try{
				int p=Integer.parseInt(string2);
				return new GoTo(p);
			}catch(Exception e){
				return null;
			}
		}else return null;
	}

	/**
	 * Envia el bytecode GOTO
	 */
	@Override
	protected String toStringAux() {
		return "GOTO";
	}

	/**
	 * Modifica el contador de la CPU
	 */
	@Override
	public boolean execute(CPU cpu) {
		cpu.setProgramCounter(this.param);
		return true;
	}

}
