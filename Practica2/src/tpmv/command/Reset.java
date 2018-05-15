package tpmv.command;

import tpmv.program.Engine;

public class Reset extends Command{
	/**
	 * Formatea el estado de ByteCodeProgram
	 */
	@Override
	public boolean execute(Engine engine) {
		return engine.resetProgram();
	}

	/**
	 * Verifica que sea RESET
	 */
	@Override
	public Command parse(String[] s) {
		if (s.length==1 && s[0].equalsIgnoreCase("RESET"))
				return new Reset(); 
		else return null;
	}

	/**
	 * Muestra la funcion de Help
	 */
	@Override
	public String textHelp() {
		
		return "  RESET: Vacia el programa actual"+
				System.getProperty("line.separator");
	}
	
	/**
	 * Envia el comando Reset
	 */
	public String toString(){
		return "RESET";
	}

	
}
