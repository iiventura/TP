package tpmv.command;

public class CommandParser {
	/**
	 * Array de los Comandos de la Aplicacion
	 */
	private final static Command[] commands={new Help(), new Quit(), new Reset(),
			new Replace(), new Run(), new AddByteCodeProgram()};
	
	/**
	 * Metodo que define los datos de un comando
	 * @param line string que contiene la instruccion que el
	 * @return el comando solicitado o null si no existe
	 */
	public static Command parse(String line){
		//eliminar blancos inecesarios
		line.trim();
		//descomponer en palabras
		String words[]=line.split(" +");
		
		boolean found=false;
		int i=0;
		Command c=null;
		while(i<commands.length && !found){
			c=commands[i].parse(words);
			
			if(c!=null) found=true;
			else i++;
			
		}
		
		return c;
	}
	
	/**
	 * Muestra el menu de comandos de la app
	 * cadena de ayuda del comando
	 */
	public static void showHelp(){
		for(int i=0; i<CommandParser.commands.length;i++)
				System.out.print(CommandParser.commands[i].textHelp());
	}

}
