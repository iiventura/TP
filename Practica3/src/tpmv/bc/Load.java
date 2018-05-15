package tpmv.bc;

import tpmv.elements.CPU;
import tpmv.exceptions.StackException;

public class Load implements ByteCode{
	private int param;
	/**
	 * constructora
	 * @param param entero posicion
	 */
	public Load(int param){
		this.param=param;
	}

	/**
	 * ejecuta la funcion en la cpu para loas
	 */
	@Override
	public void execute(CPU cpu) 
			throws StackException{
		
		cpu.push(cpu.read(this.param));
		cpu.next();
		
	}

	/**
	 * verifica si es load
	 */
	@Override
	public ByteCode parse(String[] words) {
		if (words.length==2 && words[0].equalsIgnoreCase("LOAD")) {
			int n=Integer.parseInt(words[1]);
			return new Load(n);
		}else return null;
			
	}
	
	public String toString(){
		return "LOAD "+this.param;
	}
}
