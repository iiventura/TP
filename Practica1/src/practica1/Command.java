package practica1;


/**
 * La Clase Command esta encargada de definir las posibles estructuras 
 * que puede tener un Command y ejecutarlo segun lo que  solicite el usuario
 * @author Irene Ventura Farias
 *@version 12/11/2016
 */

public class Command {
	private ENUM_COMANDO command;
	private ByteCode instruction;
	private int replace;
	
	/**
	 * Constructora que define los comandos
	 * que no nesecitan otros parametros
	 * HELP QUIT RUN RESET
	 * @param command tipo ENUM_COMANDO  que define el tipo
	 */
	public Command(ENUM_COMANDO command){
		this.command=command;
		this.instruction=null;
		this.replace=-1;
	}
	
	/**
	 * Constructora que define el comando 
	 * REPLACE N
	 * @param command tipo ENUM_COMANDO  que define el tipo
	 * @param replace entero que ubica el ByteCode a reemplazar
	 */
	public Command(ENUM_COMANDO command, int replace){
		this.command=command;
		this.instruction=null;
		this.replace=replace;
	}
	
	/**
	 * Constructora para definir el comando 
	 * NEWINST BYTECODE
	 * @param command tipo ENUM_COMANDO que define el tipo
	 * @param bc ByteCode que el usuario indico
	 */
	
	public Command(ENUM_COMANDO command, ByteCode bc){
		this.command=command;
		this.instruction=bc;
		this.replace=-1;
	}
	
	/**
	 * Metodo que se encarga de ejecutar las funciones de la maquina virtual
	 * @param engine el que define el tipo de comando que el usuario solicito
	 * @return si se pudo ejecutar sin problemas las funciones de engine
	 * 
	 */
	public boolean execute(Engine engine){
		if(this.command==ENUM_COMANDO.HELP){
			Engine.showHelp();
			return true;
		}else if(this.command==ENUM_COMANDO.QUIT){
			return engine.endExecution();
			
		}else if (this.command==ENUM_COMANDO.RUN){
			return engine.executeCommandRun();
			
		}else if (this.command==ENUM_COMANDO.NEWINST){
			ByteCode bc=this.instruction;
			return engine.addByteCodeInstruction(bc);
			
		}else if (this.command==ENUM_COMANDO.RESET){	
			return engine.resetProgram();
			
		}else if(this.command==ENUM_COMANDO.REPLACE){
			int r=this.replace;
			return engine.replaceByteCode(r);
		}else{
			return false;
		}
		
	}
	
	/**
	 * Metodo que obtiene los valores de un Comando 
	 * @return string los datos del comando
	 */
	public String toString(){
		String s="";
		
		s=this.command+" ";
		if(this.instruction!=null)
			s=s+this.instruction.toString();
		if(this.replace!=-1)
			s=s+this.replace;
		
		return s;
	}
}
