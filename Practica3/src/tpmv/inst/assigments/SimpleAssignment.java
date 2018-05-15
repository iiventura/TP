package tpmv.inst.assigments;



import tpmv.bc.Store;
import tpmv.elements.Compiler;
import tpmv.elements.LexicalParser;
import tpmv.exceptions.ArrayException;
import tpmv.inst.Instruction;

public class SimpleAssignment implements Instruction {
	private String varName;
	private Term rhs;
	
	
	public SimpleAssignment(){}

	public SimpleAssignment(String varName, Term rhs){
		this.varName=varName;
		this.rhs=rhs;
		
	
	}
	
	@Override
	public void compile(Compiler compiler) throws ArrayException {
		//var = value
		
		compiler.addByteCode(this.rhs.compile(compiler));
		int var=compiler.indexOf(this.varName);
		compiler.addByteCode(new Store(var));
		
	}

	@Override
	public Instruction lexParse(String[] words, LexicalParser lexParser) {
		if (words.length != 3 ||  !"=".equals(words[1]) ) return null; 
		else {
			String name = words[0];
			Term termino = TermParser.parse(words[2]);
			if (termino != null) {

				lexParser.increaseProgramCounter();
				return new SimpleAssignment(name, termino);
			}
		}
		return null;
	}
	


public String toString(){
	return "New SimpleAssignment('"+this.varName+"' ,'"+this.rhs.toString()+"')";
}

}







