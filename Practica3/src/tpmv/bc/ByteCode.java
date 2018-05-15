package tpmv.bc;

import tpmv.elements.CPU;
import tpmv.exceptions.StackException;
import tpmv.exceptions.DivByZeroException;

public interface ByteCode {
	void execute(CPU cpu) throws StackException,DivByZeroException ;   
	ByteCode parse(String[] words);
}
