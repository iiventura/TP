package tpmv.cm;

import tpmv.mv.Engine;

public class Help implements Command {
	/**
	 * Constructora
	 */
	public Help(){}

	/**
	 * Invoca la Ayuda para el usuario
	 */
	@Override
	public void execute(Engine engine) {
		engine.executeHelp();
	}

	/**
	 * Verifica si es HELP
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
