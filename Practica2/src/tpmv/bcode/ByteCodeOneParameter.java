package tpmv.bcode;

/**
 * Clase Abstracta que hereda de ByteCode define las instrucciones que 
 * tienen un Parametro
 * @author Irene Ventura Farias
 * version: 06/12/16
 */

public abstract class ByteCodeOneParameter extends ByteCode{
	protected int param;
	/**
	 * Constructora por defecto
	 */
	   public ByteCodeOneParameter(){}; 
	  /**
	   * Constructora con una Parametro 
	   * @param p entero 
	   */
	   public ByteCodeOneParameter(int p){ 
		   this.param = p; 
	   }

	/**
	 * Verifica que es un Bytecode con Parametro 
	 */
	@Override
	public ByteCode parse(String[] words) {
		if(words.length!=2) return null;
		else return this.parseAux(words[0],words[1]);
	}
	abstract protected ByteCode parseAux(String string1, String string2);

	/**
	 * Muestra el ByteCode y su parametro
	 */
	 public String toString(){
		 return this.toStringAux() + " " + this.param;
	}   
	 abstract protected String toStringAux(); 
}
