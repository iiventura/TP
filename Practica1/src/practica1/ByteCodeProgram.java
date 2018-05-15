package practica1;

/**
 * La Clase ByteCodeProgram se encarga de mantener un registro de los ByteCode
 * que el usuario reemplaza o agrega a la maquina
 *@author Irene Ventura Farias
 *@version 12/11/16 
 */
public class ByteCodeProgram {
	private static final int MAX_INSTR=100;
	private ByteCode[] bcprogram;
	private int numBC;
	
	/**
	 * La Constructora no recibe parametros
	 * pero inicializa los atributos de la clase
	 */
	public ByteCodeProgram(){
		this.bcprogram=new ByteCode[ByteCodeProgram.MAX_INSTR];
		this.numBC=0;
	}
	
	/**
	 * Metodo que agrega un ByteCode a Array
	 * @param instr ByteCode que se registrara
	 * @return si se pudo almacenar el ByteCode
	 */
	public boolean addBCInctruction(ByteCode  instr){
		if(this.numBC<MAX_INSTR&&instr!=null){
			this.bcprogram[this.numBC]=instr;
			this.numBC++;
			return (this.bcprogram[this.numBC-1]==instr);
		}else{
			return false;
		}
		
	}
	
	/**
	 * Metodo que devuelve el numero de ByteCode almacenados
	 *  @return numBC
	 */
	public int getNumberOfByteCodes(){
		return this.numBC;
	}
	
	/**
	 * Metodo que se encarga de obtener un ByteCode 
	 * del arreglo 
	 * @param i es la posicion en la que se encuentra el ByteCode
	 * @return el ByteCode buscado
	 */
	public ByteCode getByteCode(int i){
		if(getNumberOfByteCodes()>0){
			ENUM_BYTECODE bc=this.bcprogram[i].getOpCode();
			int param=this.bcprogram[i].getParam();
			ByteCode nuevo= new ByteCode(bc,param);

			return nuevo;
		}else return null;
	}
	
	/**
	 * Metodo que se encarga de mostrar los ByteCode almacenados
	 * @return String que muestra el programa
	 */
	public String toString(){
		String	s="Programa almacenado:"+System.getProperty("line.separator");
		
		for(int i=0;i<this.numBC;i++){
			s=s+Integer.toString(i)+": "+this.bcprogram[i].toString()+System.getProperty("line.separator");
		}
		
		return s;
	}
	
	/**
	 * Metodo que borra los ByteCodes Almacenados 
	 * y reinicia el numBC
	 */
	public void reset(){
		for(int i=0;i<this.numBC;i++)
			this.bcprogram[i]=null;
		
		this.numBC=0;
	}
	
	/**
	 * Metodo que reemplaza un ByteCode por otro
	 * @param replace numero de la posicion en la que esta el ByteCode
	 * @param bc el ByteCode nuevo
	 */
	public void replace(int replace, ByteCode bc){
		this.bcprogram[replace]=bc;
	}
}
