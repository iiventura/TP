package tpmv.bcode;


import tpmv.bcode.arithmetics.*;
import tpmv.bcode.cjumps.*;
import tpmv.bcode.storage.*;
import tpmv.bcode.ujumps.*;

public class ByteCodeParser {
	/**
	 * Array de los ByteCodes de la Aplicacion
	 */
	private final static ByteCode[] bytecodes={new Add(),new Sub(), new Mul(), new Div(),
											  new Ifeq(), new Ifle(), new Ifleq(),new Ifneq(),
											  new Halt(),new Load(),new Out(), new Push(),
											  new Store(), new GoTo()};

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
