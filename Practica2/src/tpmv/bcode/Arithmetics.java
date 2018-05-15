package tpmv.bcode;

import tpmv.program.CPU;
/**
 * Clase Abstracta que hereda de Bytecode todas las instrucciones aritmeticas
 * @author Irene Ventura Farias
 *
 */
public abstract class Arithmetics extends ByteCode {
	/**
	 * Constructora por defecto
	 */
	public Arithmetics(){}
	/**
	 * Verifica que es un bytecode aritmetico
	 */
	@Override   
	public ByteCode parse(String[] words) {  
		if (words.length!=1) return null;
		else return this.parseAux(words[0]);
	}
	abstract protected ByteCode parseAux(String word);
	
	/**
	 * Envia el tipo de ByteCode Arithmetico que es
	 */
	 public String toString(){
		 return this.toStringAux();
	 }   
	 abstract protected String toStringAux();
	 
	 /**
	  * Verifica que existan dos elementos almacenados en la pila 
	  * luego realiza su operacion correspondiente 
	  */
	 @Override
	 public boolean execute(CPU cpu){
		 if (cpu.getSizeStack()>=2){
			 
			 int b=cpu.pop();//CIMA
			 int a=cpu.pop();//SUBCIMA
			 	if( operation(cpu,a,b)){
			 		cpu.increaseProgramCounter();
			 		return true;
			 	}else return false;

		 }else return false;
	 }
	 abstract protected boolean operation(CPU cpu, int a, int b);
}
