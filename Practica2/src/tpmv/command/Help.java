package tpmv.command;

import tpmv.program.Engine;

public class Help extends Command{
	/**
	 * invoca la auda para el usuario
	 */
	@Override
	public boolean execute(Engine engine) {
		return engine.showHelp();
	}
	/**
	 * verifica si es HELP
	 */
	@Override
	public Command parse(String[] s) {
		if (s.length==1 && s[0].equalsIgnoreCase("HELP"))
			return new Help(); 
		else return null;
	}
	/**
	 * Envia la funcion de HELP
	 */
	@Override
	public String textHelp() {
		return "  HELP: Muestra esta ayuda."+
				System.getProperty("line.separator");
	}

	/**
	 * Envia el comando HELP
	 */
	public String toString(){
		return "HELP";
	}
}
