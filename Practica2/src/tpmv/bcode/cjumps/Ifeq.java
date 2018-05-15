package tpmv.bcode.cjumps;

import tpmv.bcode.ByteCode;
import tpmv.bcode.ConditionalJumps;

public class Ifeq extends ConditionalJumps{
	/**
	 * Constructora por defecto
	 */
	public Ifeq(){}
	/**
	 * Constructora con parametro
	 * @param y entero
	 */
	public Ifeq(int y){super(y);}

	/**
	 * Compara si dos elementos son iguales
	 */
	@Override
	protected boolean compare(int a, int b) {
		return a==b;
	}
	
	/**
	 * Verifica que es un bytecode condicional IFEQ
	 */
	@Override
	protected ByteCode parseAux(String string1, String string2) {
		if(string1.equalsIgnoreCase("IFEQ")){
			try{
				int p=Integer.parseInt(string2);
				return new Ifeq(p);
			}catch(Exception e){
				return null;
			}
		}else return null;
	
	}
	
	/**
	 *Envia el bytecode IFEQ 
	 */
	@Override
	protected String toStringAux() {
		return "IFEQ";
	}

}
