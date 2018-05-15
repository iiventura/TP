package tpmv.exceptions;


@SuppressWarnings("serial")
public class ExecutionErrorException extends Exception{
	/**
	 * Envia el mensaje de excepcion
	 * @param instr contiene el mensaje a mostrar
	 */
	public ExecutionErrorException(String instr) {
		super(instr);
	}
	
	public String toString(){
		return "Error Execution Exception";
	}

}
