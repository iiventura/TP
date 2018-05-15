package tpmv.bc.jumps.conditionaljumps;

import tpmv.bc.ByteCode;
import tpmv.bc.jumps.ConditionalJump;


public class IfLeq extends ConditionalJump{
	/** 
	 * Constructora sin parametro
	 */
	public IfLeq(){
		super();
	}
	/** 
	 * Constructora con parametro
	 */
	public IfLeq(int n){
		super(n);
	}
	
	/**
	 * Compara si es menor o igual
	 */
	@Override
	protected boolean compares(int a, int b) {
		return a<=b;
	}

	/**
	 * Verifica si es IFLEQ
	 */
	@Override
	protected ByteCode parseJump(String s, int param) {
		if(s.equalsIgnoreCase("IFLEQ")){
			return new IfLeq(param);
			
		}else return null;
	}
	
	public String toString(){
		return "IFLEQ "+this.param;
	}

}
