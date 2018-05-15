package tpmv.bc.jumps.conditionaljumps;

import tpmv.bc.ByteCode;
import tpmv.bc.jumps.ConditionalJump;


public class IfEq extends ConditionalJump{
	/** 
	 * Constructora sin parametro
	 */
	public IfEq(){
		super();
	}
	
	/** 
	 * Constructora con parametro
	 */
	public IfEq(int n){
		super(n);
	}

	/**
	 * Compara si los elementos son iguales
	 */
	@Override
	protected boolean compares(int a, int b) {
		return a==b;
	}

	/**
	 * Verifica si es IFEQ
	 */
	@Override
	protected ByteCode parseJump(String s, int param) {
		if(s.equalsIgnoreCase("IFEQ")){
			return new IfEq(param);
		
		}else return null;
	}
	
	public String toString(){
		return "IFEQ "+this.param;
	}
}
