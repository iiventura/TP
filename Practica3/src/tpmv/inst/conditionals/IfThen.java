package tpmv.inst.conditionals;

import tpmv.bc.jumps.ConditionalJump;
import tpmv.elements.Compiler;
import tpmv.elements.LexicalParser;
import tpmv.exceptions.ArrayException;
import tpmv.exceptions.LexicalAnalisysException;
import tpmv.inst.Instruction;
import tpmv.mv.ParsedProgram;

public class IfThen implements Instruction {
	private Condition cd;
	private ParsedProgram pP;

	public IfThen(){}

	public IfThen(Condition cd, ParsedProgram pP){
		this.cd=cd;
		this.pP=pP;
	}

	@Override
	public void compile(Compiler compiler) 
			throws ArrayException {
		this.cd.compile(compiler);
		compiler.compile(this.pP);
		ConditionalJump cj=this.cd.cjump;
		int n=compiler.getSizeBCProgram()+1;
		cj.setN(n);
		
		
	}

	@Override
	public Instruction lexParse(String[] words, LexicalParser lexParser) {
		try{
			if (words.length != 4 || !"if".equals(words[0])) return null;
			else {
				Condition c1 =	ConditionParser.parse(words[1]+" "+words[2]+" "+words[3], lexParser);
				ParsedProgram iBody = new ParsedProgram();
				lexParser.increaseProgramCounter();
				lexParser.lexicalParser(iBody, "ENDIF");
				lexParser.increaseProgramCounter();
				return new IfThen(c1, iBody);
			}


		}catch (LexicalAnalisysException e) {
			System.err.println (e.getMessage() +"at instruction IFTHEN");
			return null;
		}catch(ArrayException a){
			return null;
		}
	}


}
