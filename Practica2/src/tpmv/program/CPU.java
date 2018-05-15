package tpmv.program;

/**
 * La Clase CPU simula la maquina que maneja instrucciones
 * usando una pila, la memoria y ahora un bcProgram
 * @author Irene Ventura Farias
 * @version 6/12/16
 */

import tpmv.bcode.ByteCode;
import tpmv.machine.*;


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
	 * Metodo que detiene la CPU
	 */
	public void halt() { 
		this.halt = true;
	}
	
	/**
	 * Metodo que muestra el estado actual de la CPU
	 * @return string que muestra el estado de la pila y memoria
	 * 
	 */
	 public String toString(){
		 String s = System.getProperty("line.separator") +
					"Estado de la CPU: " + System.getProperty("line.separator") +
					" Memoria: " + this.memory + System.getProperty("line.separator") +
					" Pila: " + this.stack + System.getProperty("line.separator");
			
		
		 return s;
	 }

	 /**
	  * Consulta el tamano de la pila
	  * invocando la funcion de la pila
	  * @return tamano 
	  */
	 public int getSizeStack() {
		 return this.stack.getNumElems();
	 }
	 
	 /**
	  * Invoca a pop() de OperandStack
	  * @return elemento desapilado
	  */
	 public int pop() { 
		 return this.stack.pop(); 
	 }
	 
	 /**
	  * Invoca a push() de OperandStack()
	  * @param i elemento a apilar
	  * @return si se pudo apilar
	  */
	 public boolean push(int i) {
		 return this.stack.push(i);
	 }
	 
	 /**
	  * Invoca a read de Memory
	  * @param param posicion a leer
	  * @return valor almacenado en la memoria
	  */
	 public Integer read(int param){
		 return this.memory.read(param);
	 }
	 
	 /**
	  * Invoca al write de Memory
	  * @param param posicion en la que guarda el dato
	  * @param value dato 
	  */
	 public void write(int param, int value){
		 this.memory.write(param, value);
	 }
	 
	 /*
	  * Le asigna un valor al contador del programa
	  */
	 public void setProgramCounter(int jump){
		 this.programCounter=jump;
	 }
	 
	 /**
	  * Incrementa el valor del contador del Programa
	  */
	 public void increaseProgramCounter(){
		 this.programCounter++;
	 }
	 
	 /**
	  * Ejecuta las instrucciones de bcProgram
	  * @return si se ejecutaron o hubo un error
	  */
	  public boolean run() { 
		  this.programCounter=0; 
		  boolean ok = true;
		  
		  while (this.programCounter < bcProgram.getNumberOfByteCodes() && ok&&!this.halt) {
			  ByteCode bc = bcProgram.getByteCode(this.programCounter);
			  if (!bc.execute(this)) ok=false;
			  else System.out.println(toString());
		  }
		  
		  return ok;
		
	  }
	  
	 
	  
}
