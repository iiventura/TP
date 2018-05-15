package tpmv.bc.jumps;


import tpmv.elements.CPU;
import tpmv.exceptions.StackException;

public abstract class ConditionalJump extends Jump {
	/**
	 * Constructora sin entero
	 */
	public ConditionalJump(){
		super();
	}
	
	/**
	 * Constructora con entero
	 * @param n
	 */
	public ConditionalJump(int n){
		super(n);
	}
	
	/**
	 * Extrae la cima y la subcima de la pila 
	 * dependiendo de la compraracion modifica el contador
	 * del programa
	 */
	@Override
	public void execute(CPU cpu)
		throws StackException{
		if (cpu.getSizeStack()>=2){
			int b=cpu.pop();
			int a=cpu.pop();
			
			if (compares(a,b))
				cpu.next();
				
			else  
				cpu.jump(this.param);
		}
		
	}abstract protected boolean compares(int a, int b);

	/**
	 * Asigna un entero
	 * @param n es un entero diferente de 0
	 */
	public void  setN(int n){
		this.param=n;
	}
	
}
