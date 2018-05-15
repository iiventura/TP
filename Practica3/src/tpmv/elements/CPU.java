package tpmv.elements;

import tpmv.bc.ByteCode;
import tpmv.exceptions.ArrayException;
import tpmv.exceptions.DivByZeroException;
import tpmv.exceptions.ExecutionErrorException;
import tpmv.exceptions.StackException;
import tpmv.mv.ByteCodeProgram;

public class CPU {
	private Memory memory=new Memory();
	private OperandStack stack=new OperandStack();
	private boolean halt=false;
	private int programCounter=0;
	private ByteCodeProgram bcProgram=new ByteCodeProgram();
	
	/**
	 * Constructor
	 * @param program arrays de bytecodes
	 */
	public CPU(ByteCodeProgram program){ 
		this.bcProgram = program;
	}
	
	/**
	 * Agregar un bytecode al programa
	 * @param bc bytecode
	 * @throws ArrayException si el array esta lleno
	 */
	public void addByteCode(ByteCode bc)
			throws ArrayException{
		this.bcProgram.addByteCode(bc);
	}
	
	
	/**
	 * Reemplaza un ByteCode de bcProgram
	 * @param pos posicion en el array
	 * @param bc bytecode
	 * @throws ArrayException si la posicion no existe
	 */
	public void replaceByteCode(int pos, ByteCode bc)
		throws ArrayException{
		this.bcProgram.replace(pos, bc);
	}
	/**
	 * Metodo que comprueba que no hay elementos 
	 * en la pila
	 * @return numElems no tiene elementos
	 */
	public boolean emptyStack(){
		return this.stack.emptyStack();
	}
	
	/**
	 * Detiene la ejecucion de la CPU
	 */
	public void finish(){
		this.halt=true;
	}
	
	/**
	 * Reinicia la memoria y la pila
	 */
	public void initialize(){
		this.memory.reset();
		this.stack.reset();
	}
	
	/**
	 * Le asigna un valor al contador del programa
	 * @param n numero de instruccion a saltar
	 */
	public void jump(int n){
		this.programCounter=n;
	}
	
	/**
	  * Incrementa el valor del contador del Programa
	  */
	public void next(){
		this.programCounter++;
	}
	
	/**
	 * Muestra el tope de la pila
	 * @throws StackException si la pila esta vacia
	 */
	public void out()
			throws StackException{
		if(this.stack.emptyStack())
			throw new StackException("Empty Stack Exception");
		else{
			int top=pop();
			System.out.println("Consola: "+top);
			next();
		}	
	}
	
	/**
	 * Invoca a pop() de OperandStack
	 * @return elemento desapilado
	 * @throws StackException si no hay elementos
	 */
	public int pop() 
			throws StackException{
		return this.stack.pop();
	}
	
	/**
	 * Invoca a push() de OperandStack()
	 * @param n elemento a apilar
	 * @throws StackException pila llena
	 */
	public void push(int n)
			throws StackException{
		this.stack.push(n);
	}
	
	/**
	 * Invoca a read de Memory
	 * @param pos posicion a leer
	 * @return valor almacenado en la memoria
	 */
	public Integer read(int pos){
		 return this.memory.read(pos);
	}
	
	 /**
	 * Invoca al write de Memory
	 * @param pos posicion en la que guarda el dato
	 * @param value dato 
	 */
	public void write(int pos,int value){
		this.memory.write(pos, value);
	}
	
	/**
	 * Reinicia el programa y la cpu
	 */
	public void reset(){
		this.programCounter=0;
		initialize();
	}
	
	/**
	 * Ejecuta una a una las instrucciones de bcProgram
	 * @throws ArrayException array lleno 
	 * @throws ExecutionErrorException error de ejecucion
	 */
	public void run()
		throws ArrayException, ExecutionErrorException{
		
		 while (!this.halt &&this.programCounter<this.bcProgram.size()) {
			 try{
				 ByteCode bc = bcProgram.getInstruction(this.programCounter);
				 bc.execute(this);
			 
			  
			 }catch(DivByZeroException d){
				 System.err.println("Execution Error Exception at bytecode "+this.programCounter);
				 throw new ExecutionErrorException(d.getMessage());
				 
			 }catch( StackException s){
				 System.err.println("Execution Error Exception at bytecode "+this.programCounter);
				 throw new ExecutionErrorException(s.getMessage());
				 
			 }
		  }
		 System.out.println(toString());
		
	}
	
	/**
	 * Verifica el numero de elementos que existe 
	 * @return entero  con  elementos de la pila
	 */
	public int getSizeStack(){
		return this.stack.getNumElems();
	}
	
	/**
	 * Verifica si la pila esta llena o no 
	 * @return boolean que indica si esta o no 
	 * completamente llena 
	 */
	public boolean stackOverflow(){
		return this.stack.stackOverFlow();
	}
	
	/**
	 * Muestra el estado de la CPU: Pila y Memoria
	 */
	public String toString(){
		 String s = System.getProperty("line.separator") +
					"Estado de la CPU: " + System.getProperty("line.separator") +
					" Memoria: " + this.memory + System.getProperty("line.separator") +
					" Pila: " + this.stack + System.getProperty("line.separator");
			
		
		 return s;
	}
	
	
	
}
