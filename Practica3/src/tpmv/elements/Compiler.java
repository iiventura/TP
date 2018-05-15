package tpmv.elements;

import tpmv.bc.ByteCode;
import tpmv.exceptions.ArrayException;
import tpmv.inst.Instruction;
import tpmv.mv.ByteCodeProgram;
import tpmv.mv.ParsedProgram;

public class Compiler {
	private ByteCodeProgram bcProgram;
	private String[] varTable;
	private int numVariables;
	private final int MAX_VARS=100;
	
	/**
	 * Constructora
	 */
	public Compiler(){
		this.numVariables=0;
		this.varTable=new String[MAX_VARS];
	}
	
	/**
	 * Agrega un bytecode a la lista
	 * @param bc bytecode a agregar
	 * @throws ArrayException si el array esta completamente lleno
	 */
	public void addByteCode(ByteCode bc)
			throws ArrayException{
		this.bcProgram.addByteCode(bc);
	}
	/**
	 * Reemplaza un bytecode de la lista
	 * @param pos posuicion del bc a sustituir
	 * @param bc byecode nuevo
	 * @throws ArrayException si la posicion no existe en el arreglo
	 */
	
	public void replaceByteCode(int pos, ByteCode bc)
			throws ArrayException{
		this.bcProgram.replace(pos, bc);
	}
	
	/**
	 * Agregar una variable o modificarla
	 * @param varName variable
	 * @return posicion donde fue almacenada
	 */
	public int addVariable(String varName){
		this.varTable[this.numVariables]=varName;
		this.numVariables++;
		
		return this.numVariables-1;
	}
	
	/**
	 * Convierte las instrucciones de pProgram a ByteCode
	 * @param pProgram programa parseado
	 * @throws ArrayException si el bcProgram esta lleno
	 */
	public void compile(ParsedProgram pProgram)
			throws ArrayException{
		int i=0;
		
			while(i<pProgram.getSize()){
				Instruction inst=pProgram.getInstruction(i);
				inst.compile(this);
				i++;
			}
		
		
	}
	
	/**
	 * tamano del bcprogram
	 * @return nro de bbytecode almacenados
	 */
	public int getSizeBCProgram(){
		return this.bcProgram.size();
	}
	
	/**
	 * Busca una variable
	 * @param varName variable
	 * @return posicion en la que se encuentra
	 */
	public int indexOf(String varName){
		int i=0;
		boolean found=false;
		
		while(i<this.numVariables&&!found){
			if(varTable[i].equals(varName))
				found=true;
			else 
				i++;
		}
		
		if(!found)
			i=addVariable(varName);
		
		return i;
	}
	
	/**
	 * Igualar el puntero de engine al del compiler
	 * @param bcProgram lista de bytecode
	 */
	public void initializate(ByteCodeProgram bcProgram){
		this.bcProgram=bcProgram;
	}
	
	public String toString(){
		return this.bcProgram.toString();
	}
}
