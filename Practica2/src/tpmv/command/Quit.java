package tpmv.command;

import tpmv.program.Engine;

public class Quit extends Command{

	/**
	 * Finaliza la ejecucion del programa
	 */
	@Override
	public boolean execute(Engine engine) {
		return engine.endExecution();
	}

	/**
	 * Verifica que es QUIT
	 */
	@Override
	public Command parse(String[] s) {
		if (s.length==1 && s[0].equalsIgnoreCase("QUIT")){
			return new Quit(); 
		}else return null;
	}

	/**
	 * Muestra la funcion de QUIT
	 */
	@Override
	public String textHelp() {
	
		return "  QUIT: Finaliza la ejecucion del programa"+
				System.getProperty("line.separator");
	}

	/**
	 * envia el comando QUIT
	 */
	public String toString(){
		return "QUIT";
	}
}
