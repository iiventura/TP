package tpmv.machine;

/**
 * La clase OperandStack realiza la implementacion de la Pila 
 *@author Irene Ventura Farias
 *@version 12/11/2016
 */

public class OperandStack {
	public static final int MAX_STACK=100;
	private int []stack;
	private int numElems;
	
	
	/**
	 * Constructor de la Clase
	 * Inicializa atributos de la clase 
	 */
	public OperandStack(){
		this.numElems=0;
		this.stack=new int [OperandStack.MAX_STACK];
	}
	
	/**
	 * Metodo que elimina todos los elementos almacenados
	 * @return si no hay elementos
	 * 
	 */

	public boolean reset(){
		this.numElems=0;
		return isEmpty();
	}
	
	/**
	 * Metodo que comprueba que no hay elementos 
	 * @return numElems no tiene elementos
	 */
	public boolean isEmpty(){
		return numElems==0;
	}

	/**
	 * Metodo que se encarga de apilar enteros 
	 * @param value es el dato a guardar
	 * @return si el valor pudo ser agregado o no
	 */
	public boolean push(int value){
		
		if(numElems<MAX_STACK){
			this.stack[this.numElems]=value;
			this.numElems++;
			return true;
		
		}else return false;
		
	}
		 
	/**
	 * Metodo que busca el ultimo elemento almacenado y
	 * lo guarda en un entero y "desapila" colocando cero
	 * en el lugar donde se guardaba
	 * @return el ultimo almacenado o -1
	 */
	public int pop(){
		int valor=-1;
		
		if(getNumElems()>0){
			valor= this.stack[this.numElems-1];
			this.stack[this.numElems-1]=0;
			this.numElems--;
		}
		return valor;
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
		if(isEmpty())
			return "<vacia>";
		else{
			String s="";
			for(int i=0;i<this.numElems;i++)
				s=s+this.stack[i]+" ";
			return s;
		}
	}
	


}
