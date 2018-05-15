package tpmv.mv;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import tpmv.bc.ByteCode;
import tpmv.bc.ByteCodeParser;
import tpmv.cm.Command;
import tpmv.cm.CommandParser;
import tpmv.elements.CPU;
import tpmv.elements.LexicalParser;
import tpmv.elements.Compiler;
import tpmv.exceptions.*;
import tpmv.inst.Instruction;
import tpmv.inst.InstructionParser;


public class Engine {
	private static Scanner in=new Scanner(System.in);
	private SourceProgram sProgram;
	private ParsedProgram pProgram;
	private ByteCodeProgram bcProgram;
	private Compiler compiler;
	private CPU cpu;
	private boolean end;

	/**
	 * Constructora 
	 */
	public Engine(){
		this.end=false;
		this.sProgram= new SourceProgram();
		this.pProgram=new ParsedProgram();
		this.bcProgram=new ByteCodeProgram();
		this.compiler=new Compiler();
	}

	
	/**
	 * Realiza el proceso de compilacion
	 * @throws LexicalAnalisysException si esta mal escrita la instruccion
	 * @throws ArrayException si el ParsedProgram o el ByteCode no tiene capacidad
	 */
	public void executeCompile()
			throws LexicalAnalisysException, ArrayException{
		try{
			//Programa Fuente a Programa Parseado
			this.lexicalAnalysis();

			//Programa Parseado a Programa ByteCode
			this.generateByteCode();
			
		}catch(LexicalAnalisysException l){
			System.err.println("Lexical Analysis Exception:" );
			throw new LexicalAnalisysException (l.getMessage());
		
		}catch(ArrayException a){
			System.err.println("Array Exception:");
			throw new ArrayException(a.getMessage());
		}

	}

	/**
	 * Se encarga de traducir el sProgram a el parseado
	 * @throws LexicalAnalisysException error de instruccion
	 * @throws ArrayException capacidad de arreglos
	 */
	private void lexicalAnalysis()
			throws LexicalAnalisysException, ArrayException{
		LexicalParser lp=new LexicalParser();
		lp.initialize(this.sProgram);
		this.pProgram=new ParsedProgram();
		
		Instruction inst=null;
		while (lp.getProgramCounter() < this.sProgram.getSize() &&
				!"end".equals(this.sProgram.getInstruction(lp.getProgramCounter()))  ) {
			inst= InstructionParser.parse(this.sProgram.getInstruction(lp.getProgramCounter()), lp);
			
			if (inst!=null)
				this.pProgram.addInstruction(inst);
			else 
				throw new LexicalAnalisysException("   "+this.sProgram.getInstruction(lp.getProgramCounter()) + " at line "+lp.getProgramCounter());

		}
		//System.out.println(this.pProgram.toString());

	}

	/**
	 * Genera las funciones ByteCode basandose en las instrucciones parseadas
	 * @throws LexicalAnalisysException
	 * @throws ArrayException
	 */
	private void generateByteCode()
			throws LexicalAnalisysException, ArrayException{
		this.compiler.initializate(this.bcProgram);
		this.compiler.compile(this.pProgram);
		
	}

	/**
	 * Muestra que hace cada comando mediante textHelp()
	 */
	public void executeHelp(){
		CommandParser.showHelp();
	}

	/**
	 * Finaliza la ejecucion de la Aplicacion
	 */
	public void executeQuit(){
		this.end=true;
	}

	/**
	 * Reemplaza un ByteCode 
	 * @param n posicion del ByteCode a reemplazar
	 * @throws BadFormatByteCodeException mal formato del ByteCode
	 * @throws ArrayException problemas en el bcProgram
	 */
	public void executeReplace(int n)
			throws BadFormatByteCodeException, ArrayException{

		System.out.print("Nueva instruccion: ");
		String line=in.nextLine();
		ByteCode bc=ByteCodeParser.parse(line);

		if(bc!=null){
			this.bcProgram.replace(n, bc);
		}else {	
			throw new BadFormatByteCodeException("Bad Format ByteCode Exception");
		}

	}

	/**
	 * Resetea SourceProgram, ParsedProgram yByteCodeProgram
	 */
	public void executeReset(){
		this.sProgram.reset();
		this.pProgram.reset();
		this.bcProgram.reset();
	}

	/**
	 * Ejecuta las instrucciones ByteCode en la CPU
	 * @throws ExecutionErrorException pila o divicion * cero
	 * @throws ArrayException si el array se llena
	 */
	public void executeRun()
			throws ExecutionErrorException, ArrayException{
		this.cpu=new CPU(this.bcProgram);
		this.cpu.run();
	}

	/**
	 * Carga los String de instrucciones en el sProgram
	 * @param fName
	 * @throws FileNotFoundException si el archivo no existe
	 * @throws ArrayException si no hay capacidad de almacenamiento en sProgram
	 */
	@SuppressWarnings("resource")
	public void load(String fName)
			throws FileNotFoundException,ArrayException, LexicalAnalisysException{
		String linea="";
		executeReset();
		
		try{
			Scanner entrada=new Scanner(new File(fName));

			while(entrada.hasNextLine()){
				linea = entrada.nextLine();
				this.sProgram.addInst(linea);
			}
			if (!"end".equals(linea)) {
				throw new LexicalAnalisysException("El Source Program no finaliza con end");
			}
			
			
			entrada.close();
		}catch(FileNotFoundException e){
			System.err.println("File Error "+fName+" Not Found");
			throw new FileNotFoundException("File Not Found Exception");

		}

	}
	
	/**
	 * Ejecuta los comandos necesarios para el programa
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

				try {
					command.execute(this);
					if(this.sProgram.getSize()>0)
						System.out.println(this.sProgram.toString());

					if(this.bcProgram.size()>0)
						System.out.println(this.bcProgram.toString());


				} catch (Exception e) {
					System.err.println(e.getMessage());
					//e.printStackTrace();
				}

			}
		
		}

		System.out.println("Fin de la ejecucion...");
		in.close();

	}
}
