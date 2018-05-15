package tpmv.exceptions;

@SuppressWarnings("serial")
public class LexicalAnalisysException extends Exception {
	/**
	 * Envia el mensaje de excepcion
	 * @param instr contiene el mensaje a mostrar
	 */
	public LexicalAnalisysException(String instr){
		super(instr);
	}
	
	public String toString(){
		return "Lexical Analisys Exception";
	}

}
