package tpmv.elements;



public class Memory {
	private Integer[] memory;
	private int size;
	private final static int MAX_MEM=200;
	private boolean empty;
	
	/**
	 * El Constructor, no recibe parametros, 
	 * inicializa todas los atributos de la Clase
	 */
	public Memory(){
		this.empty=true;
		this.memory=new Integer[Memory.MAX_MEM];
		this.size=Memory.MAX_MEM;
		
		for(int i=0;i<this.size;i++)
			this.memory[i]=null;
	}
	
	/**
	 * Vacia los elementos en la memoria
	 */
	public void reset(){
		 this.empty=true;
			for(int i=0;i<this.size;i++)
				this.memory[i]=null;
			this.size=MAX_MEM;
	}
	
	/**
	 * Metodo que aumenta el tamano de la memoria
	 * @param pos entero con el que se duplicara el tamano del arreglo
	 */
	
	private void resize(int pos){
		//Crea un nuevo arreglo con una dimension doble a la posicion
		Integer[] nuevo=new Integer [pos*2];
		for(int i=0;i<(pos*2);i++){
			if(i<this.size)
				nuevo[i]=this.memory[i];
			else
				nuevo[i]=null;
			
		}
		
		//Modifica el tamano actual y el arreglo memoria ahora apunta
		//al nuevo arreglo
		
		this.size=pos*2;
		this.memory=nuevo;
	
	}
	
	/**
	 * Metodo que lee del arreglo el valor en una posicion
	 * @param pos posicion en el arreglo
	 * @return valor que se encuentra en dicha pos
	 */
	public Integer read(int pos){
		
		if(pos<this.size&& this.memory[pos]!=null )
			return (this.memory[pos]);
		else
			return null;
	}
	
	/**
	 * Metodo que escribe en el arreglo si la posicion es menor que 
	 * el tamano del arreglo
	 * @param pos posicion en el arreglo 
	 * @param value valor que contendra dicha posicion
	 * @return si se escribio o no
	 */
	public boolean write(int pos, int value){
			
		if(pos>this.size) 
			resize(pos);
		//Indica que ya escribio al menos una vez
		this.empty=false;
		//Almacena y devuelve true
			this.memory[pos]=value;
			return true;
		
	}
	
	/**
	 * Metodo que se encarga de leer todos los elementos 
	 * que fueron insertados
	 */
	public String toString(){
		if(this.empty)
			return "<vacia>";
		else{
			String s="";
			for(int i=0;i<this.size;i++)
				if(this.memory[i]!=null)
					s=s+"["+i+"]:"+this.memory[i]+" ";
			
			return s;
		}
		
	}
}
