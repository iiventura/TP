package tpmv.bc;

import tpmv.elements.CPU;
import tpmv.exceptions.StackException;

public class Store implements ByteCode {
	private int param;
	
	/**
	 * Constructora
	 * @param param
	 */
	public Store(int param){
		this.param=param;
	}

	/**
	 * Ejecuta el store 
	 */
	@Override
	public void execute(CPU cpu) 
			throws StackException {
		int value=cpu.pop();
		cpu.write(this.param,value);
		cpu.next();	
	}

	/**
	 * Verifica si es un STORE
	 */
	@Override
	public ByteCode parse(String[] words) {
		if (words.length==2 && words[0].equalsIgnoreCase("STORE")) {
			int n=Integer.parseInt(words[1]);
			return new Store(n);
			
		}else return null;
	}
	
	public String toString(){
		return "STORE "+this.param;
	}

}
