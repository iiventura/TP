package tpmv.cm;

import java.io.FileNotFoundException;
import tpmv.exceptions.ArrayException;
import tpmv.exceptions.LexicalAnalisysException;
import tpmv.mv.Engine;

public class Load implements Command {
	private String fName;
	/**
	 * Constructora sin parametro
	 */
	public Load(){}
	
	/**
	 * Constructora con parametro
	 * @param fName nombre del archivo a cargar
	 */
	public Load(String fName){
		this.fName=fName;
	}

	/**
	 * Ejecuta la carga de fichero 
	 */
	@Override
	public void execute(Engine engine) 
				throws FileNotFoundException, ArrayException, LexicalAnalisysException{
		
		engine.load(fName);
	}

	/**
	 * Verifica si es LOAD
	 */
	@Override
	public Command parse(String[] s) {
		if (s.length==2 && s[0].equalsIgnoreCase("LOAD")){

			return new Load(s[1]); 
				
		}else return null;
	}

	/**
	 * Envia la funcion  de LOAD
	 */
	@Override
	public String textHelp() {
		return "  LOAD FICH: carga el fichero de nombre FICH como programa fuente."+
				System.getProperty("line.separator");
	}
		
	/**
	 * Envia el comando LOAD 
	 */
	public String toString(){
		return "LOAD "+this.fName;
	}
	
	
}
