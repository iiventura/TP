package tpmv.program;

/**
 * La Clase Engine funciona como Controlador, obtiene los comandos 
 * que el usuario le solicita y ejecuta la funcion de cada comando
 * @author Irene Ventura Farias
 *@version 6/12/2016
 */
import java.util.Scanner;

import tpmv.bcode.ByteCode;
import tpmv.bcode.ByteCodeParser;
import tpmv.command.*;
import tpmv.machine.ByteCodeProgram;

public class Engine {
	private boolean end;
	private ByteCodeProgram bcProgram;
	private CPU cpu;
	private static Scanner in=new Scanner(System.in);
	
	/**
	 * Constructora que inicializa e end de la ejecucion
	 * y el bcProgram
	 */
	public Engine(){
		this.end=false;
		this.bcProgram=new ByteCodeProgram();
	}

	
	/**
	 * Metodo que se encarga de iniciar la maquina virtual
	 * y espera los comando que el usuario solicita
	 * para asignarselos a exceute en Command
	 * 
	 */
	public void start(){
		
		String line="";
				
		while(!this.end){
			//Muestra el prompt
			System.out.print("> ");
			Command command=null;
			//lee una linea
			line=in.nextLine();
			command=CommandParser.parse(line);
			
			if(command==null){
				System.out.println("Error: Comando incorrecto");
				
			}else{
				System.out.println("Comienza la ejecución de "+ command.toString());
				
				if(!command.execute(this))
					System.out.println("Error: Ejecucion incorrecta del comando");
				if(this.bcProgram.getNumberOfByteCodes()>0)
					System.out.println(this.bcProgram.toString());
				
			}
			System.out.println();
		}
		
		System.out.println("Fin de la ejecucion...");
		in.close();
	}
	
	/**
	 * Crea un CPU con los ByteCodes actuales
	 * y los intenta ejecutas
	 * @return si se ejecutaron o hubo un error
	 */
	public boolean executeCommandRun(){
		this.cpu=new CPU(this.bcProgram);
		
		if(cpu.run()){
			System.out.print(cpu.toString());
			return true;
		}else return false;
	} 
	
	/**
	 * Muestra al usuario todos los comandos posibles
	 * de la maquina virtual
	 * @return si mostro el menu de ayuda
	 */
	public boolean showHelp(){
		CommandParser.showHelp();
		return true;
	}
	
	/**
	 * Finaliza la ejecucion de la maquina
	 * @return true 
	 */
	public boolean endExecution(){
		this.end=true;
		return this.end;
	}

	/**
	 * Elimina los bytecode que  se encuentran 
	 * registrados en el array
	 * @return si se formateo el bcProgram
	 */
	public boolean resetProgram(){
		if(this.bcProgram.getNumberOfByteCodes()>0)
			this.bcProgram.reset();
		return this.bcProgram.getNumberOfByteCodes()==0;
	}
	
	/**
	 * Reemplaza un bytecode por otro
	 * @param replace posicion del ByteCode a reemplazar
	 * @return si se pudo reemplazar
	 * 
	 */
	public boolean replaceByteCode(int replace){
		
		if(replace<this.bcProgram.getNumberOfByteCodes()){
			System.out.print("Nueva instruccion: ");
			String line=in.nextLine();
			ByteCode bc=ByteCodeParser.parse(line);
			
			if(bc!=null){
				this.bcProgram.replace(replace, bc);
				return true;
			}else {	
				
				return false;
			}
			
				
		}else return false; 
		
	}
	
	/**
	 * Agrega los ByteCodes en el bcProgram
	 * @param bc bytecode a almacenar
	 * @return boolean que indica si se agrego o no al bcProgram
	 */
	public boolean addByteCodeInstruction (ByteCode bc){
		if(bc!=null){
			return this.bcProgram.addBCInctruction(bc);
		}else
			return false;
	}
	
	/**
	 * escribe los bytecodes del programa verificando que 
	 * sea correcto, verifica que el ultimo sea halt
	 * y termina de guardar cuando el bcProgram este lleno
	 * o el usuario indique end
	 * @return si leyo todas los bytecode y finalizo con halt
	 */

	public boolean readByteCodeProgram() {
		String line="";
		boolean end=false; //Verifica que el usuario finalice el almacenamiento de bytecodes
		boolean ok=true; //Verifica que el programa almacenado termine en HALT
		
		resetProgram();
		System.out.println("Introduce el bytecode. Una instruccion por línea:");
		while(!end ){
			line=in.nextLine();
			
			if(line.equalsIgnoreCase("END"))
				end=true;
			else{
				ByteCode bc=ByteCodeParser.parse(line);
				if(!addByteCodeInstruction(bc))
					System.out.println("Error: Error de instruccion ByteCode");
			}
			
		}
		
		//Verifica que el ultimo HALT
		ByteCode ultimo=this.bcProgram.getByteCode(this.bcProgram.getNumberOfByteCodes()-1);
		
		if(!ultimo.toString().equals("HALT"))
			ok=false;
		
		
		return ok;
	}
	
}
