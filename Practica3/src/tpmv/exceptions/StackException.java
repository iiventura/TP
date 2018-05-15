package tpmv.exceptions;

@SuppressWarnings("serial")
public class StackException extends ExecutionErrorException  {
	/**
	 * Envia el mensaje de excepcion
	 * @param instr contiene el mensaje a mostrar
	 */
	public StackException(String instr){
		super(instr);
	}
	
	public String toString(){
		return "Stack Exception";
	}

}
