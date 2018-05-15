package tpmv.exceptions;

@SuppressWarnings("serial")
public class DivByZeroException extends ExecutionErrorException {
	/**
	 * Envia el mensaje de excepcion
	 * @param instr contiene el mensaje a mostrar
	 */
	public DivByZeroException(String instr){
		super(instr);
	}
	
	public String toString(){
		return "Division by Zero";
	}

}
