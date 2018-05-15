package tpmv.bc;

import tpmv.elements.CPU;
import tpmv.exceptions.StackException;

public class Push implements ByteCode{
	private int param;
	
	/**
	 * Contructora
	 * @param param entero a cargar 
	 */
	public Push(int param){
		this.param=param;
	}

	/**
	 * Invoca el apilar de la CPU
	 */
	
	@Override
	public void execute(CPU cpu) 
			throws StackException {
		cpu.push(this.param);
		cpu.next();
		
	}

	/**
	 * Verifica si es un bytecode PUSH
	 */
	@Override
	public ByteCode parse(String[] words) {
		if (words.length==2 && words[0].equalsIgnoreCase("PUSH")) {
			int n=Integer.parseInt(words[1]);
			return new Push(n);
			
		}else return null;
	}
	
	public String toString(){
		return "PUSH "+this.param;
	}

}
