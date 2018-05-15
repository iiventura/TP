package practica1;

/**
 * La Clase Memory realiza la implementacion de la memoria 
 * @author Irene Ventura Farias
 *@version 12/11/16
 */
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
	 * Metodo que aumenta el tamano de la memoria
	 * @param pos entero con el que se duplicara el tamano del arreglo
	 */
	
	/*private void resize(int pos){
		//redimensiona el arreglo el doble de la posicion
		//pos>=this.size
	
	}*/
	
	/**
	 * Metodo que borra todos los valores en el arreglo
	 * y la variable la inicializa a modo de reiniciar
	 * @return no hay elementos en memoria 
	 */
	public boolean reset(){
		//Anula los valores
		 this.empty=true;
		for(int i=0;i<this.size;i++)
			this.memory[i]=null;
		
		return this.empty;
		
	}
	/**
	 * Metodo que escribe en el arreglo si la posicion es menor que 
	 * el tamano del arreglo
	 * @param pos posicion en el arreglo 
	 * @param value valor que contendra dicha posicion
	 * @return si se escribio o no
	 */
	public boolean write(int pos, int value){
		//Indicar que se ha escrito en la memoria
		//if (pos>this.size) resize(pos);
		
		this.empty=false;
		//Ignora posiciones mayores al MAX
		if(pos<this.size){
			this.memory[pos]=value;
			return true;
		}else return false;
		
		
	}
	
	/**
	 * Metodo que lee del arreglo el valor en una posicion
	 * @param pos posicion en el arreglo
	 * @return valor que se encuentra en dicha pos
	 */
	public int read(int pos){
		
		if(pos<this.size&& this.memory[pos]!=null )
			return (this.memory[pos]);
		else
			return 0;
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
