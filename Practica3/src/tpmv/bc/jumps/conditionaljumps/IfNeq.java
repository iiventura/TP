package tpmv.bc.jumps.conditionaljumps;

import tpmv.bc.ByteCode;
import tpmv.bc.jumps.ConditionalJump;


public class IfNeq extends ConditionalJump {
	/** 
	 * Constructora sin parametro
	 */
	public IfNeq(){
		super();
	}
	
	/** 
	 * Constructora con parametro
	 */
	public IfNeq(int n){
		super(n);
	}

	/**
	 * Compara si dos elementos son diferentes
	 */
	@Override
	protected boolean compares(int a, int b) {
		return a!=b;
	}

	/**
	 * Verifica que es el bytecode IFNEQ
	 */
	@Override
	protected ByteCode parseJump(String s, int param) {
		if(s.equalsIgnoreCase("IFNEQ")){
			return new IfNeq(param);
			
		}else return null;
	}
	
	public String toString(){
		return "IFNEQ "+this.param;
	}

}
