package practica1;

/**
 * Esta Clase se encarga de definir un Bytecode
 * @author Irene Ventura Farias
 *@version 12/11/16
 */
public class ByteCodeParser {
	/**
	 * Metodo que dado un string define los datos de un ByteCode
	 * @param line string que contiene los campos para el bytecode
	 * @return un ByteCode para ejecutar en la CPU 
	 * 
	 */
	public static ByteCode parse(String line){
		line=line.trim();
		String []instruccion=line.split(" +");
		
		if(instruccion.length==0)
			return null;
		else{
			instruccion[0]=instruccion[0].toLowerCase();
			
			if(instruccion.length==1)
				if(instruccion[0].equalsIgnoreCase("ADD"))
					return new ByteCode(ENUM_BYTECODE.ADD);
				else if(instruccion[0].equalsIgnoreCase("SUB"))
					return new ByteCode(ENUM_BYTECODE.SUB);
				else if(instruccion[0].equalsIgnoreCase("MUL"))
					return new ByteCode(ENUM_BYTECODE.MUL);
				else if(instruccion[0].equalsIgnoreCase("DIV"))
					return new ByteCode(ENUM_BYTECODE.DIV);
				else if(instruccion[0].equalsIgnoreCase("OUT"))
					return new ByteCode(ENUM_BYTECODE.OUT);
				else if(instruccion[0].equalsIgnoreCase("HALT"))
					return new ByteCode(ENUM_BYTECODE.HALT);
				else return null;
			else if(instruccion.length==2){
				if(instruccion[0].equalsIgnoreCase("PUSH"))
					return new ByteCode(ENUM_BYTECODE.PUSH,Integer.valueOf(instruccion[1]));
				else if(instruccion[0].equalsIgnoreCase("LOAD"))
					return new ByteCode(ENUM_BYTECODE.LOAD,Integer.valueOf(instruccion[1]));
				else if(instruccion[0].equalsIgnoreCase("STORE"))
					return new ByteCode(ENUM_BYTECODE.STORE,Integer.valueOf(instruccion[1]));
				else return null; 
				
			}
			
		}
		return null;
		
	}
	
}

		
		
		
		
			
			
						
				
				
			
		
		
			
	


