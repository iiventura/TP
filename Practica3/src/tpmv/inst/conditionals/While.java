package tpmv.inst.conditionals;

import tpmv.bc.jumps.ConditionalJump;
import tpmv.bc.jumps.GoTo;
import tpmv.elements.Compiler;
import tpmv.elements.LexicalParser;
import tpmv.exceptions.ArrayException;
import tpmv.exceptions.LexicalAnalisysException;
import tpmv.inst.Instruction;
import tpmv.mv.ParsedProgram;

public class While implements Instruction {
	private Condition cd;
	private ParsedProgram pP;

	public While(){}
	public While(Condition cd, ParsedProgram pP){
		
		this.cd=cd;
		this.pP=pP;
	}
	
	@Override
	public void compile(Compiler compiler) throws ArrayException {
		int n=compiler.getSizeBCProgram();
		this.cd.compile(compiler);
		compiler.compile(this.pP);
		ConditionalJump cj=this.cd.cjump;
		int m=compiler.getSizeBCProgram()+1;
		cj.setN(m);
		compiler.addByteCode(new GoTo(n));
	}

	@Override
	public Instruction lexParse(String[] words, LexicalParser lexParser) {
		try{
			if (words.length != 4 || !"while".equals(words[0])) return null;
			else {
				Condition c1 =	ConditionParser.parse(words[1]+" "+words[2]+" "+words[3], lexParser);
				ParsedProgram wBody = new ParsedProgram();
				lexParser.increaseProgramCounter();
				lexParser.lexicalParser(wBody, "ENDWHILE");
				lexParser.increaseProgramCounter();
				return new While(c1, wBody);
			}


		}catch (LexicalAnalisysException e) {
			System.err.println (e.getMessage() +" at instruction WHILE");
			return null;
		}catch(ArrayException a){
			return null;
		}
	}

}

