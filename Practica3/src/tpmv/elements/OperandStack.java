package tpmv.elements;

import tpmv.exceptions.StackException;

public class OperandStack {
	public static final int MAX_STACK=100;
	private int []stack;
	private int numElems;
	
	public OperandStack(){
		this.numElems=0;
		this.stack=new int [OperandStack.MAX_STACK];
	}
	
	
	/**
	 * Metodo que comprueba que no hay elementos 
	 * @return numElems no tiene elementos
	 */
	public boolean emptyStack(){
		return numElems==0;
	}
	
	/**
	 * Metodo que elimina todos los elementos almacenados
	 * 
	 */
	public void reset(){
		this.numElems=0;
	}
	
	/**
	 * Metodo que se encarga de apilar enteros 
	 * @param value es el dato a guardar
	 * @return si el valor pudo ser agregado o no
	 */
	public boolean push(int value)
			throws StackException{
		if(stackOverFlow())
			throw new StackException("Stack Overflow Exception");
			
		else{
			this.stack[this.numElems]=value;
			this.numElems++;
			return true;
		}
	}
	
	/**
	 * Metodo que busca el ultimo elemento almacenado y
	 * lo guarda en un entero y "desapila" colocando cero
	 * en el lugar donde se guardaba
	 * @return el ultimo almacenado o -1
	 */
	public int pop() 
			throws StackException{
		int valor=-1;
		
		if(emptyStack())
			throw new StackException("Empty Stack Exception");
		else{
			valor= this.stack[this.numElems-1];
			this.stack[this.numElems-1]=0;
			this.numElems--;
		}
		
		return valor;
		
	}
	
	/**
	 * Evalua si la pila ya esta llena
	 */
	public boolean stackOverFlow(){
		return numElems>=MAX_STACK;
	}

	/**
	 * Metodo que indica el nro de elmentos que hay almacenados
	 * @return numero de elementos 
	 */
	public int getNumElems(){
		return this.numElems;
	}
	
	/**
	 * Metodo que se encarga de copiar los elementos que hay almacenados
	 * @return string con o sin elementos
	 */
	public String toString(){
		if(emptyStack())
			return "<vacia>";
		else{
			String s="";
			for(int i=0;i<this.numElems;i++){
				s=s+this.stack[i]+" ";
				
			}
			return s;
		}
	}
}
