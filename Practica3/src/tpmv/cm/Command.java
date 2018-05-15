package tpmv.cm;

import java.io.FileNotFoundException;
import tpmv.exceptions.*;
import tpmv.mv.Engine;

public interface Command {
	
	void execute (Engine engine)
			throws FileNotFoundException, LexicalAnalisysException,
			 ArrayException, BadFormatByteCodeException, StackException,
             DivByZeroException, ExecutionErrorException;
	
	Command parse(String[ ] s);
	String textHelp();
}
