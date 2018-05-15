package tpmv.cm;

import tpmv.exceptions.ArrayException;
import tpmv.exceptions.LexicalAnalisysException;
import tpmv.mv.Engine;

public class Compile implements Command {
	/**
	 * Constructora
	 */
	public Compile(){}

	/**
	 * Ejecuta la funcion para el analisis lexico y el
	 * programa parseado
	 */
	@Override
	public void execute(Engine engine) 
			throws LexicalAnalisysException, ArrayException {
		
		engine.executeCompile();
	}

	/**
	 * Verifica si es COMPILE
	 */
	@Override
	public Command parse(String[] s) {
		if (s.length==1 && s[0].equalsIgnoreCase("COMPILE"))
			return new Compile(); 
		else return null;
	}

	/**
	 * Envia la funcion de COMPILE
	 */
	@Override
	public String textHelp() {
		return "  COMPILE: procesa el programa fuente a programa parseado"+
				System.getProperty("line.separator")+"          de programa parseado a programa bytecode."+
				System.getProperty("line.separator");
	}
	
	/**
	 * Envia a COMPILE
	 */
	public String toString(){
		return "COMPILE";
	}

}
