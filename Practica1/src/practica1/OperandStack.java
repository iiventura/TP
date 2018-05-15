package practica1;

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
		while(this.numElems>0){
			pop();
		}
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
	
	//Operaciones Aritmeticas
	
	/**
	 * Metodo que se encarga de sumar dos elementos;
	 * verifica que hay dos o mas elementos, los suma
	 * y el resultado lo apila
	 * @return si se pudo o no apilar la suma
	 */
	
	public boolean add(){
		if(getNumElems()>=2){
			int b=pop();
			int a=pop();
			
			return push(a+b);
			
		}else return false;
		
	}
	
	/**
	 * Metodo que se encarga de restar dos elementos;
	 * verifica que  hay dos o mas elementos, los resta
	 * y el resultado lo apila
	 * @return si se pudo o no apilar la resta
	 */
	public boolean sub(){
		if(getNumElems()>=2){
			int b=pop();
			int a=pop();
			return push(a-b);
		}else 
			return false;
	}
	
	/**
	 * Metodo que se encarga de multiplicar dos elementos;
	 * verifica que dos o mas elementos, los multiplica
	 * y el resultado lo apila
	 * @return si se pudo o no apilar la multiplicacion
	 */
	public boolean mul(){
		if(getNumElems()>=2){
			int b=pop();
			int a=pop();
			return push(a*b);
		}else
			return false;
	}
	
	/**
	 * Metodo que se encarga de dividir dos elementos;
	 * verifica que dos o mas elementos, verifica que no 
	 * exista una divicion entre 0,luego los divide
	 * y el resultado lo apila
	 * @return si se pudo o no apilar la divicion
	 */
	public boolean div(){
		if(getNumElems()>=2){
			int b=pop();
			int a=pop();
			
			if(b!=0)
				return push(a/b);
			else
				return false;
			
		}else return false;
		
		
	}


}
