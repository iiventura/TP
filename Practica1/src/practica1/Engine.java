package practica1;
import java.util.*;

/**
 * La Clase Engine funciona como Controlador, obtiene los comandos 
 * que el usuario le solicita y mantiene la funcion de cada comando
 * @author Irene Ventura Farias
 *@version 12/11/2016
 */
public class Engine {
	private boolean end;
	private ByteCodeProgram bcProgram;
	private CPU cpu;
	private static Scanner in=new Scanner(System.in);
	
	/**
	 * Contructora que inicializa los atributos de la clase
	 */
	public Engine(){
		this.bcProgram=new ByteCodeProgram();
		this.cpu=new CPU();
		this.end=false;
	}
	
	/**
	 * Metodo que se encarga de iniciar la maquina virtual
	 * y espera los comando que el usuario solicita
	 * para asignarselos a exceute en Command
	 * 
	 */
	public void start(){
		this.end=false;
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
	 * Metodo que se encarga de ejecutar todos los ByteCode
	 * almacenados o hasta que se produzca un error 
	 * @return si pudo ejecutar todos los ByteCode registrados
	 */
	public boolean executeCommandRun(){
		this.cpu.reset();
		int i=0;
		boolean e=true;
		
		while(i<this.bcProgram.getNumberOfByteCodes()&&e){
			ByteCode actual=this.bcProgram.getByteCode(i);
			
			if(cpu.execute(actual)){
				System.out.println("El estado de la maquina tras ejecutar el bytecode "+ actual.toString()+" es:");
				System.out.println(cpu.toString());
				i++;
			}else e=false;
		}
			
		return e;
	}
	
	/**
	 * Metodo que se encarga de mostrarle al usuario todos los 
	 * comandos posibles
	 * @return true que mostro la informacion
	 */
	public static boolean showHelp(){
		System.out.println("  HELP: Muestra esta ayuda.");
		System.out.println("  QUIT: Cierra la aplicacion.");
		System.out.println("  RUN: Ejecuta este programa ");
		System.out.println("  NEWINST BYTECODE: Introduce una nueva instruccion al programa");
		System.out.println("  RESET: Vacia el programa actual");
		System.out.println("  REPLACE N: Reemplaza la instruccion N por la solicitada al usuario");
	
		return true;
	}

	/**
	 * Meto que se encarga de finalizar la ejecucion de la maquina virtual
	 * @return true para finalizar
	 */
	public boolean endExecution(){
		this.end=true;
		return this.end;
	}

	/**
	 * Metodo que agrega los ByteCodes en el bcProgram
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
	 * Metodo que invoca a reset de bcProgram y el cpu
	 * @return si se pudo reiniciar ambos objetos
	 * 
	 */
	public boolean resetProgram(){
		this.bcProgram.reset();
		
		return this.bcProgram.getNumberOfByteCodes()==0 && this.cpu.reset();
	}

	/**
	 * Metodo que invoca la funcion reemplazar
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

}
