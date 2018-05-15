package tpmv.bc;

import tpmv.bc.arithmetics.*;
import tpmv.bc.jumps.GoTo;
import tpmv.bc.jumps.conditionaljumps.*;


public class ByteCodeParser {
	/**
	 * Array de los ByteCodes 
	 */
	private final static ByteCode[] bytecodes={new Add(),new Sub(), new Mul(), new Div(),
											  new IfEq(0), new IfLe(0), new IfLeq(0),new IfNeq(0),
											  new Halt(),new Load(0),new Out(), new Push(0),
											  new Store(0), new GoTo(0)};


	/**
	 * Verifica que el String perteneca a uno de los Bytecodes
	 * @param line String que contiene el bytecode
	 * @return el Bytecode obtenido
	 */
	public static ByteCode parse(String line){
		//elimina blancos extras
		line=line.trim();
		//descomponer en palabras
		String words[]=line.split(" +");
		
		boolean found=false;
		int i=0;
		ByteCode bc=null;
		
		while(i<ByteCodeParser.bytecodes.length && !found){
			bc=bytecodes[i].parse(words);
			if(bc!=null)
				found=true;
			else
				i++;
		}
		
		return bc;
		
	}
}
