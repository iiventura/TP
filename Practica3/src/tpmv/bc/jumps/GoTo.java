package tpmv.bc.jumps;

import tpmv.bc.ByteCode;
import tpmv.elements.CPU;


public class GoTo extends Jump {
	
	public GoTo(int n){
		super(n);
	}
	
	/**
	 * Modifica el contador del Programa
	 */
	@Override
	public void execute(CPU cpu) {
		cpu.jump(this.param);
	}

	/**
	 * Verifica si es un GOTO
	 */
	@Override
	protected ByteCode parseJump(String s, int param) {
		if(s.equalsIgnoreCase("GOTO")){
			return new GoTo(param);
		
		}else return null;
	}
	
	public String toString(){
		return "GOTO "+this.param;
	}

}
