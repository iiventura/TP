package tpmv.cm;

import tpmv.exceptions.ArrayException;
import tpmv.exceptions.ExecutionErrorException;
import tpmv.mv.Engine;

public class Run implements Command{
	
	/**
	 * Ejecuta el run del engine
	 */
	@Override
	public void execute(Engine engine) 
			throws ArrayException, ExecutionErrorException {
		engine.executeRun();
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
		return "RUN";
	}

}
