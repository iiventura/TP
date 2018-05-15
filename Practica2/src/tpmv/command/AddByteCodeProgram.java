package tpmv.command;

import tpmv.program.Engine;

public class AddByteCodeProgram extends Command {
	/**
	 * Ejecuta la lectura y almacenaje de ByteCodes
	 * en el ByteCodeProgram
	 */
	@Override
	public boolean execute(Engine engine) {
		return engine.readByteCodeProgram();

	}

	/**
	 * Verifica que es BYTECODE 
	 */
	@Override
	public Command parse(String[] s) {
		if(s.length==1||s[0].equalsIgnoreCase("BYTECODE"))
			return new AddByteCodeProgram();			
		else return null;
	}

	/**
	 * Envia la funcion de BYTECODE
	 */
	@Override
	public String textHelp() {
		
		return "  BYTECODE: Permite introducir un programa"+
				System.getProperty("line.separator");
	}
	
	/**
	 * Envia el comando BYTECODE
	 */
	public String toString(){
		return "BYTECODE";
	}

}
