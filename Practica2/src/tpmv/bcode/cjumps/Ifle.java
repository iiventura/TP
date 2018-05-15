package tpmv.bcode.cjumps;

import tpmv.bcode.ByteCode;
import tpmv.bcode.ConditionalJumps;

public class Ifle extends ConditionalJumps {
	/**
	 * Constructora por defecto
	 */
	public Ifle(){}
	/**
	 * Constructora con parametro
	 * @param y parametro
	 */
	public Ifle(int y){super(y);}

	/**
	 * Compara si un elemento es menor que otro
	 */
	@Override
	protected boolean compare(int a, int b) {
		return a<b;
	}
	
	/**
	 * Verifica que es el bytecode IFLE
	 */
	@Override
	protected ByteCode parseAux(String string1, String string2) {
		if(string1.equalsIgnoreCase("IFLE")){
			try{
				int p=Integer.parseInt(string2);
				return new Ifle(p);
			}catch(Exception e){
				return null;
			}
		}else return null;
	}

	/**
	 * Envia el bytecode IFLE
	 */
	@Override
	protected String toStringAux() {
		return "IFLE";
	}

}
