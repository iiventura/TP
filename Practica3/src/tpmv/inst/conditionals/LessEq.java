package tpmv.inst.conditionals;

import tpmv.bc.jumps.ConditionalJump;
import tpmv.bc.jumps.conditionaljumps.IfLeq;
import tpmv.elements.LexicalParser;
import tpmv.inst.assigments.Term;

public class LessEq extends Condition{
	public LessEq(){}
	public LessEq(Term t1, Term t2){
		super(t1, t2);
	}

	/**
	 * Crear el ByteCode correspondiente
	 */
	@Override
	protected ConditionalJump compileOp() {
		
		return new IfLeq();
	}

	/**
	 * Verifica si es LessEqual
	 */
	@Override
	protected Condition parseOp(Term t1, String op, Term t2, LexicalParser lexParser) {
		if ( !"<=".equals(op))
			return null;
		else
			return new LessEq(t1, t2);
	}
	
	/**
	 * Devuelve el tipo de Condicional
	 */
	@Override
	protected String toStringConditional() {
	
		return "LessEq ";
	}

}
