package tpmv.inst.assigments;


import tpmv.bc.Store;
import tpmv.bc.arithmetics.*;
import tpmv.elements.Compiler;
import tpmv.elements.LexicalParser;
import tpmv.exceptions.ArrayException;
import tpmv.inst.Instruction;

public class CompoundAssignment implements Instruction{
	private String varName;
	private String operator;
	private Term t1;
	private Term t2;

	public CompoundAssignment(){}

	public CompoundAssignment(String varName, String operator, Term t1, Term t2){
		this.varName=varName;
		this.operator=operator;
		this.t1=t1;
		this.t2=t2;
	}

	@Override
	public void compile(Compiler compiler) throws ArrayException {
		//term1
		compiler.addByteCode(this.t1.compile(compiler));
		//term2
		compiler.addByteCode(this.t2.compile(compiler));
		
		//operador
		if(this.operator.equals("+"))
			compiler.addByteCode(new Add());
		else if(this.operator.equals("-"))
			compiler.addByteCode(new Sub());
		else if(this.operator.equals("*"))
			compiler.addByteCode(new Mul());
		else if(this.operator.equals("/"))
			compiler.addByteCode(new Div());
		
		//varName
		int v=compiler.indexOf(this.varName);
		compiler.addByteCode(new Store(v));
		
	}

	@Override
	public Instruction lexParse(String[] words, LexicalParser lexParser) {
		if (words.length != 5) return null;
		else {
			varName = words[0];
			t1 = TermParser.parse(words[2]);
			operator = words[3];
			t2 = TermParser.parse(words[4]);
			lexParser.increaseProgramCounter();
			return new CompoundAssignment(varName, operator, t1, t2);
		}
	}

	

	public String toString(){
		return "New CompoundAssignment ('"+this.varName+"' ,'"+this.t1.toString()+"' ,'"+
				this.operator+"' ,'"+this.t2.toString()+"')";
	}
}



