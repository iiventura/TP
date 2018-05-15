package tpmv.cm;

import tpmv.mv.Engine;

public class Quit implements Command{
	/**
	 * Constructora
	 */
	public Quit(){}

	/**
	 * Finaliza la ejecucion del programa
	 */
	@Override
	public void execute(Engine engine) {
		engine.executeQuit();
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
	 * Muestra la funcionalidad de QUIT
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
