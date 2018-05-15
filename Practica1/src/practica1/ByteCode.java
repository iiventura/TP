package practica1;

/**
 * La clase ByteCode define la estructura basica de un ByteCode
 * @author Irene Ventura Farias
 * @version 12/11/16
 *
 */

public class ByteCode {
	private ENUM_BYTECODE name;
	private int param;
	
	/**
	 * Constructor 1 
	 * @param name Enumerado de tipo ByteCode
	 * para los ByteCodes que no necesitan otros parametros
	 * OUT, HALT, ADD, SUB, DIV, MUL
	 */
	public ByteCode(ENUM_BYTECODE name){
		this.name=name;
		this.param=-1;
	}
	
	/**
	 * Constructor 2
	 * @param name Enumerado de tipo ByteCode
	 * @param param de tipo entero
	 * para PUSH N, LOAD N STORE N
	 */
	public ByteCode(ENUM_BYTECODE name, int param){
		this.name=name;
		this.param=param;
	}
	
	/**
	 * Metodo que indica el tipo ENUM_BYTECODE
	 * @return  ENUM_ByteCode 
	 */
	public ENUM_BYTECODE getOpCode(){
		return this.name;
	}
	
	/**
	 * Metodo que indica un entero como parametro
	 * @return parametro entero
	 */
	public int getParam(){
		return this.param;
	}
	
	/**
	 * Metodo que produce un string con los
	 *  datos de un ByteCode
	 */
	public String toString (){
		return this.name+(name.getNumArgs()>0?(" "+param):" ");
	}
}
