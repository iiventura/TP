package tpmv.mv;


import tpmv.exceptions.ArrayException;
import tpmv.inst.Instruction;

public class ParsedProgram {
	private final static int MAX_PARSES=200;
	private Instruction[] pProgram;
	private int nParsers;
	
	public ParsedProgram(){
		this.pProgram=new Instruction[MAX_PARSES];
		this.nParsers=0;
	}

	public void addInstruction(Instruction instr)
		throws ArrayException{
		if(this.nParsers<MAX_PARSES){
			pProgram[this.nParsers]=instr;
			this.nParsers++;
		}else{
			throw new ArrayException("No Space on the Parsed Program");
		}
	}
	
	public Instruction getInstruction(int n){
		return this.pProgram[n];
	}
	
	public int getSize(){
		return this.nParsers;
	}
	
	public void reset(){
		this.nParsers=0;
	}
	
	public String toString(){
		String line="";
		for(int i=0;i<this.nParsers;i++){
			line=line+"[ "+Integer.toString(i)+" ]: "+this.pProgram[i].toString()+System.getProperty("line.separator");
		}
		
		return line;
	}
}
