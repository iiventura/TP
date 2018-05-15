package tpmv.command;

/**
 * Clase Abstracta que define la estructura base de los Comandos
 * author: Irene Ventura Farias
 * version: 12/06/16
 */
import tpmv.program.Engine;

public abstract class Command {
	abstract public boolean execute(Engine engine);
	abstract public Command parse(String[ ] s);
	abstract public String textHelp();
	

}
