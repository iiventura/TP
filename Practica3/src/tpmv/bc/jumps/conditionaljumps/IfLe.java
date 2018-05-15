package tpmv.bc.jumps.conditionaljumps;

import tpmv.bc.ByteCode;
import tpmv.bc.jumps.ConditionalJump;


public class IfLe extends ConditionalJump {
	/** 
	 * Constructora sin parametro
	 */
	public IfLe(){
		super();
	}
	
	/** 
	 * Constructora con parametro
	 */
	public IfLe(int n){
		super(n);
	}

	/**
	 * Compara si un elemento es menor que otro
	 */
	@Override
	protected boolean compares(int a, int b) {
		return a<b;
	}

	/**
	 * Verifica que es el bytecode IFLE
	 */
	@Override
	protected ByteCode parseJump(String s, int param) {
		if(s.equalsIgnoreCase("IFLE")){
			return new IfLe(param);
			
		}else return null;
	}

	public String toString(){
		return"IFLE "+this.param;
	}
}
