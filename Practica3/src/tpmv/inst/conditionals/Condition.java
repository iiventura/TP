package tpmv.inst.conditionals;

import tpmv.bc.jumps.ConditionalJump;
import tpmv.elements.LexicalParser;
import tpmv.elements.Compiler;
import tpmv.exceptions.ArrayException;
import tpmv.inst.assigments.Term;
import tpmv.inst.assigments.TermParser;

public abstract class Condition {
	protected ConditionalJump cjump;
	private Term t1;
	private Term t2;
	
	
	public Condition(){}
	
	public Condition(Term t1, Term t2){
		this.t1=t1;
		this.t2=t2;
	}
	
	public void compile(Compiler compiler)
			throws ArrayException{
		compiler.addByteCode(this.t1.compile(compiler));
		compiler.addByteCode(this.t2.compile(compiler));
		this.cjump=compileOp();
		compiler.addByteCode(cjump);
		
	}
	protected abstract ConditionalJump compileOp();
	
	public Condition parse(String t1, String op, String t2, LexicalParser parser){
		this.t1 =TermParser.parse(t1);
		this.t2 =TermParser.parse(t2);
		
		if( t1 == null || t2 ==null)
			return null;
		
		else return parseOp(this.t1,op, this.t2, parser);
	}
	protected abstract Condition parseOp(Term t1, String op, Term t2, LexicalParser lexParser);


	public String toString(){
		
		return toStringConditional()+"( '"+this.t1.toString()+"' ,'"+this.t2.toString()+"' )";
		
	}
	protected abstract String toStringConditional();
	
}
