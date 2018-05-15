package tpmv.bcode.storage;

import tpmv.bcode.ByteCode;
import tpmv.bcode.ByteCodeOneParameter;
import tpmv.program.CPU;

public class Store extends ByteCodeOneParameter{
	/**
	 * Constructura por defecto
	 */
	public Store(){}
	
	/**
	 * Constructora con Parametro
	 * @param p parametro
	 */
	public Store(int p){super(p);}

	/**
	 * Verifica que es un bytecode STORE
	 */
	@Override
	protected ByteCode parseAux(String string1, String string2) {
		if(string1.equalsIgnoreCase("STORE")){
			try{
				int p=Integer.parseInt(string2);
				return new Store(p);
			}catch(Exception e){
				return null;
			}
		}else return null;
	}

	/**
	 * Envia un bytecode STORE
	 */
	@Override
	protected String toStringAux() {
		return "STORE";
	}

	/**
	 * Almacena el tope de la pila en la Memoria
	 */
	@Override
	public boolean execute(CPU cpu) {
		int value=cpu.pop();
		cpu.write(this.param,value);
		cpu.increaseProgramCounter();
		
		return true;
	}

}
