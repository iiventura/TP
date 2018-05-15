package tpmv.bcode.cjumps;

import tpmv.bcode.ByteCode;
import tpmv.bcode.ConditionalJumps;

public class Ifleq extends ConditionalJumps{
	/**
	 * Constructora por defecto
	 */
	public Ifleq(){}
	
	/**
	 * Constructora con parametro
	 * @param y parametro
	 */
	public Ifleq(int y){super(y);}

	/**
	 * compara si un elemento es menor o igual que otro
	 */
	@Override
	protected boolean compare(int a, int b) {
		return a<=b;
	}

	/**
	 * Verifica que es el comando IFLEQ
	 */
	@Override
	protected ByteCode parseAux(String string1, String string2) {
		if(string1.equalsIgnoreCase("IFLEQ")){
			try{
				int p=Integer.parseInt(string2);
				return new Ifleq(p);
			}catch(Exception e){
				return null;
			}
		}else return null;
	}

	/**
	 * Envia el bytecode IFLEQ
	 */
	@Override
	protected String toStringAux() {
			return "IFLEQ";
	}

}
