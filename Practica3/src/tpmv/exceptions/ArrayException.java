package tpmv.exceptions;

@SuppressWarnings("serial")
public class ArrayException extends Exception{
	/**
	 * Envia el mensaje de excepcion
	 * @param instr contiene el mensaje a mostrar
	 */
	public ArrayException(String instr){
		super(instr);
	}
	
	public String toString(){
		return "Array Exception";
	}

}
