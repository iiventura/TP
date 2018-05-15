package tpmv.cm;

import tpmv.exceptions.ArrayException;
import tpmv.exceptions.BadFormatByteCodeException;
import tpmv.mv.Engine;

public class Replace implements Command {
	private int n;
	/**
	 * Constructora sin parametro
	 */
	public Replace (){}
	
	/**
	 * Constructora con entero
	 * @param n entero que indic una posicion del bcProgram
	 */
	public Replace(int n){
		this.n=n;
	}
	
	/**
	 * Ejecuta el reemplazo de Bytecode
	 */
	@Override
	public void execute(Engine engine) 
			throws ArrayException, BadFormatByteCodeException {
		
		engine.executeReplace(n);
		
	}

	/**
	 * Verifica que es REPLACE
	 */
	@Override
	public Command parse(String[] s) {
		if (s.length==2 && s[0].equalsIgnoreCase("REPLACE")){

			int val=Integer.parseInt(s[1]);
			return new Replace(val); 
				
		}else return null;
	}

	/**
	 * Envia la funcion de REPLACE
	 */
	@Override
	public String textHelp() {
		return "  REPLACE N: Reemplaza la instruccion N por la solicitada al usuario"+
				System.getProperty("line.separator");
	}

	public String toString(){
		return "REPLACE "+this.n;
	}
}
