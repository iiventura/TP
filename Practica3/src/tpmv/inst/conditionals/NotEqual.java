package tpmv.inst.conditionals;

import tpmv.bc.jumps.ConditionalJump;
import tpmv.bc.jumps.conditionaljumps.IfNeq;
import tpmv.elements.LexicalParser;
import tpmv.inst.assigments.Term;

public class NotEqual extends Condition{
	public NotEqual(){}
	
	public NotEqual(Term t1, Term t2){
		super(t1,t2);
	}
	
	/**
	 * Crear el ByteCode correspondiente
	 */
	@Override
	protected ConditionalJump compileOp() {
	
		return new IfNeq();
	}
	
	/**
	 * Verifica si es NotEqual
	 */
	@Override
	protected Condition parseOp(Term t1, String op, Term t2, LexicalParser lexParser) {
		if ( !"!=".equals(op))
			return null;
		else 
			return new NotEqual(t1, t2);
	}
	
	
 
	/**
	 * Devuelve el tipo de Condicional
	 */

	@Override
	protected String toStringConditional() {
		
		return "NotEq ";
	}
}
