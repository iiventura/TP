package tpmv.command;

import tpmv.program.Engine;

public class Run extends Command {

	/**
	 * Ejecuta los bytecode en la CPU
	 */
	@Override
	
	public boolean execute(Engine engine) {
		return engine.executeCommandRun();
	}

	/**
	 * Verifica que el comando es RUN
	 */
	@Override
	public Command parse(String[] s) {
		if (s.length==1 && s[0].equalsIgnoreCase("RUN"))
			return new Run(); 
		else return null;
	}

	/**
	 * Muestra la funcion de Run
	 */
	@Override
	public String textHelp() {
		return "  RUN: Ejecuta este programa "+
				System.getProperty("line.separator");
	}

	/**
	 * Envia el comando Run
	 */
	public String toString(){
		return"RUN";
	}
}
