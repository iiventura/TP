package practica1;

/**
 * Esta Clase se encarga de definir un Commando
 * @author Irene Ventura Farias
 *@version 12/11/16
 */

public class CommandParser {
	/**
	 * Metodo que dado un string define los datos de un Comando
	 * @param line string que contiene los campos comando
	 * puede tener o no un ByteCode 
	 * @return un Command para ejecutar en la CPU 
	 * 
	 */
	
	public static Command parse(String line){
		//Elimina blancos iniciales
		line=line.trim();
		
		//Admite varias blancos entre palabras
		String palabras[]=line.split(" +");
		
		//Verifica el length del array
		if(palabras.length==0)
			return null;
		else{
			palabras[0]=palabras[0].toLowerCase();
			if(palabras.length==1){
				if(palabras[0].equalsIgnoreCase("HELP"))
					return new Command (ENUM_COMANDO.HELP);
				
				else if(palabras[0].equalsIgnoreCase("QUIT"))
					return new Command(ENUM_COMANDO.QUIT);
				
				else if(palabras[0].equalsIgnoreCase("RUN"))
					return new Command(ENUM_COMANDO.RUN);
				
				else if(palabras[0].equalsIgnoreCase("RESET"))
					return new Command(ENUM_COMANDO.RESET);
				else
					return null;
			
			}else if(palabras.length==2){
				
				if(palabras[0].equalsIgnoreCase("REPLACE"))
					return new Command (ENUM_COMANDO.REPLACE,Integer.parseInt(palabras[1]));
				
				else if(palabras[0].equalsIgnoreCase("NEWINST")){
					String bytecode=palabras[1];
					ByteCode bc=ByteCodeParser.parse(bytecode);
					return new Command(ENUM_COMANDO.NEWINST,bc);
				}else{
					return null;
				}
				
			}else if(palabras.length==3){
				if(palabras[0].equalsIgnoreCase("NEWINST")){
					String bytecode=palabras[1]+" "+palabras[2];
					ByteCode bc=ByteCodeParser.parse(bytecode);
					return new Command(ENUM_COMANDO.NEWINST,bc);
				}else {
					return null;}
					
			}else {
				return null;
			}
		}
			
		
	}


}
