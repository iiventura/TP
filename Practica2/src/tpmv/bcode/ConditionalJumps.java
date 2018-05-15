package tpmv.bcode;

import tpmv.program.CPU;
/**
 * Clase abstracta que define Los Saltos Condicionales pertenecientes a ByteCode
 * pero que heredan de ByteCodeOne Parameter por tener 
 * @author Irene Ventura Farias
 * version: 12/06/16
 */
public abstract class ConditionalJumps extends ByteCodeOneParameter{
	/**
	 * Constructora por defecto
	 */
	public ConditionalJumps(){}
	/**
	 * Constructora con parametro
	 * @param j parametro del Bytecode
	 */
	public ConditionalJumps(int j){ super(j); }
	
	/**
	 * Verifica que existan almenos dos elementos en la Pila 
	 * para compararlos, los desapila y luego realiza la operacion
	 * segun el tipo de condicional 
	 */
	@Override
	public boolean execute(CPU cpu) {
		if (cpu.getSizeStack()>=2){
			int b=cpu.pop();
			int a=cpu.pop();
			
			if (compare(a,b))
				cpu.increaseProgramCounter();
				
			else  
				cpu.setProgramCounter(this.param);
			return true;
		}else return false;  
	}
	abstract protected boolean compare(int a, int b);
}
