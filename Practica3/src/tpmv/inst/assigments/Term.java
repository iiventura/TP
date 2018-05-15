package tpmv.inst.assigments;

import tpmv.bc.ByteCode;
import tpmv.elements.Compiler;

public interface Term {
	ByteCode compile(Compiler compile);
	Term parse(String term);
	
}
