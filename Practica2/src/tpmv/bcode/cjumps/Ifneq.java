package tpmv.bcode.cjumps;

import tpmv.bcode.ByteCode;
import tpmv.bcode.ConditionalJumps;

public class Ifneq extends ConditionalJumps{
	
	/**
	 * Constructora por defecto 
	 */
	public Ifneq(){}

	/**
	 * Cosntructora con parametro
	 * @param y parametro
	 */
	public Ifneq(int y){super(y);}

	/**
	 * Compara si dos elementos son diferentes
	 */
	@Override
	protected boolean compare(int a, int b) {
		return a!=b;
	}

	/**
	 * Verifica que es el bytecode IFNEQ
	 */
	@Override
	protected ByteCode parseAux(String string1, String string2) {
		if(string1.equalsIgnoreCase("IFNEQ")){
			try{
				int p=Integer.parseInt(string2);
				return new Ifneq(p);
			}catch(Exception e){
				return null;
			}
		}else return null;
	}

	/**
	 * Envia el bytecode IFNEQ
	 */
	@Override
	protected String toStringAux() {
		return "IFNEQ";
	}

}
