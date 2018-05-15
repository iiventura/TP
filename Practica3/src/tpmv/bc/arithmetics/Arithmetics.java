package tpmv.bc.arithmetics;

import tpmv.bc.ByteCode;
import tpmv.elements.CPU;
import tpmv.exceptions.DivByZeroException;
import tpmv.exceptions.StackException;

public abstract class Arithmetics implements ByteCode {
	/**
	 * Constructora por defecto
	 */
	public Arithmetics(){}
	
	 /**
	  * Verifica que existan dos elementos almacenados en la pila 
	  * y ejecuta segun la funcion aritmetica 
	  */
	 @Override
	 public void execute(CPU cpu)
			 throws StackException,DivByZeroException{
		 
		 if (cpu.getSizeStack()>=2){
			 int c=cpu.pop();//CIMA
			 int sc=cpu.pop();//SUBCIMA
			 	operates(cpu,sc,c);
			 	cpu.next();
		}else{
			throw new StackException("Not Enough Elements Stack Exception");
		}
		 
	 }abstract protected void operates(CPU cpu, int sc, int c)
			 throws StackException, DivByZeroException;
	 
	 /**
	  * Verifica que es un bytecode aritmetico
	 */
	@Override   
	public ByteCode parse(String[] words) {  
		if (words.length!=1) return null;
		else return this.parseOperation(words[0]);
	
	}abstract protected ByteCode parseOperation(String word);
}
