package tpmv.mv;

import tpmv.exceptions.ArrayException;

public class SourceProgram {
	private final static int MAX_SOURCE=200;
	private String[] sProgram;
	private int nSC;
	
	
	public SourceProgram(){
		this.sProgram=new String[MAX_SOURCE];
		this.nSC=0;
	}
	
	public void addInst(String ins)
		throws ArrayException{
		if(this.nSC<MAX_SOURCE){
			//if(instr!=null)
			this.sProgram[this.nSC]=ins;
			this.nSC++;
		
		}else{
			throw new ArrayException("No Space on the ByteCode Program");
		}
		
	}
	
	public String getInstruction(int n){
		return this.sProgram[n];
	}
	
	
	public int getSize(){
		return this.nSC;
	}
	
	public void reset(){
		this.nSC=0;
	}
	
	public String toString(){
		String line="Programa Fuente Almacenado:"+System.getProperty("line.separator");
		
		for (int i=0; i<this.nSC;i++){
			line=line+"["+Integer.toString(i)+"]: "+this.sProgram[i]+System.getProperty("line.separator");
		}
		
		return line;
	}
	

}
