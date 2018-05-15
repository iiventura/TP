package tpmv.exceptions;

@SuppressWarnings("serial")
public class BadFormatByteCodeException extends Exception{
	/**
	 * Envia el mensaje de excepcion
	 * @param instr contiene el mensaje a mostrar
	 */
	public BadFormatByteCodeException(String instr){
		super(instr);
	}
	
	public String toString(){
		return "Bad Format Bytecode Exception";
	}
}
