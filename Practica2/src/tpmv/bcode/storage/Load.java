package tpmv.bcode.storage;

import tpmv.bcode.ByteCode;
import tpmv.bcode.ByteCodeOneParameter;
import tpmv.program.CPU;

public class Load extends ByteCodeOneParameter {
	/**
	 * Constructora por defecto
	 */
	public Load(){}
	/**
	 * Constructora con parametro
	 * @param n parametro
	 */
	public Load(int n){super(n);}

	/**
	 * Verifica que es el bytecode LOAD
	 */
	@Override
	protected ByteCode parseAux(String string1, String string2) {
		if(string1.equalsIgnoreCase("LOAD")){
			try{
				int p=Integer.parseInt(string2);
				return new Load(p);
			}catch(Exception e){
				return null;
			}
		}else return null;
	}

	/**
	 * Envia el bytecode LOAD
	 */
	@Override
	protected String toStringAux() {
		return "LOAD";
	}

	/**
	 * Dado un elemento en una posicion de la memoria
	 * lo apila en el CPU
	 */
	@Override
	public boolean execute(CPU cpu) {
	
		if(cpu.push(cpu.read(this.param))){
			cpu.increaseProgramCounter();
			return true;
			
		}return false;		
			
		
	}

}
