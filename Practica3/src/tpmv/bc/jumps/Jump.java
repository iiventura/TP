package tpmv.bc.jumps;

import tpmv.bc.ByteCode;

public abstract class Jump implements ByteCode{
	protected int param;
	
	/**
	 * Constructora sin parametro
	 */
	public Jump(){}
	
	/**
	 * Constructora con parametro
	 * @param param entero
	 */
	public Jump(int param){
		this.param=param;
	}

	/**
	 * Verifica que  existan dos componentes
	 */
	@Override
	public ByteCode parse(String[] words) {
		if (words.length!=2) return null;
		else {
			int n=Integer.parseInt(words[1]);
			return this.parseJump(words[0],n);
		}
	
	}protected abstract ByteCode parseJump(String s, int param);
	
	

}
