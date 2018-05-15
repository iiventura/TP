package tpmv.mv;

import tpmv.bc.ByteCode;
import tpmv.exceptions.ArrayException;

public class ByteCodeProgram {
	private static final int MAX_INSTR=200;
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
	 * Agrega un nuevo ByteCode a la lista 
	 */
	public boolean addByteCode(ByteCode instr)
			throws ArrayException{
		
		
		if(this.numBC<MAX_INSTR){
			//if(instr!=null)
			this.bcprogram[this.numBC]=instr;
			this.numBC++;
			return (this.bcprogram[this.numBC-1]==instr);
		
		}else{
			throw new ArrayException("Array Exception: No Space on the ByteCode Program");
		}
			
	}
	
	/**
	 * Metodo que reemplaza un ByteCode por otro
	 * @param replace numero de la posicion en la que esta el ByteCode
	 * @param bc el ByteCode nuevo
	 */
	public void replace(int replace, ByteCode bc)
		throws ArrayException{
		
		if(replace<0 || replace>=size())
			throw new ArrayException("Array Exception: Access Error on the ByteCode Program ");
		else
			this.bcprogram[replace]=bc;
	}
	
	/**
	 * Obtener ByteCode del Array
	 */
	public ByteCode getInstruction(int pos)
			throws ArrayException{
		if(pos>=0 && pos<size()){
			ByteCode bc=this.bcprogram[pos];
			return bc;
		}else throw new ArrayException("Array Exception: Access Error on the ByteCode Program");
	}
	
	/**
	 * Metodo que reinicia los BC a 0
	 */
	public void reset(){
		this.numBC=0;
	}
	
	/**
	 * Metodo que devuelve el numero de ByteCode almacenados
	 *  @return numBC
	 */
	public int size(){
		return this.numBC;
	}
	
	
	
	/**
	 Metodo que se encarga de mostrar los ByteCode almacenados
	 * @return String que muestra el programa
			 */
	public String toString(){
		String	s="Programa bytecode almacenado:"+System.getProperty("line.separator");
		
		for(int i=0;i<this.numBC;i++){
			s=s+"["+Integer.toString(i)+"]: "+this.bcprogram[i].toString()+System.getProperty("line.separator");
		}
		
		return s;
	}
}
