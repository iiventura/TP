package practica1;

public enum ENUM_BYTECODE {
	ADD, SUB, MUL,DIV,
	PUSH(1), LOAD(1),STORE(1),
	OUT,HALT;
	
	private int numArgs;
	
	ENUM_BYTECODE(){
		this(0);
	}
	
	ENUM_BYTECODE(int n){
		numArgs=n;
	}

	public int getNumArgs(){
		return numArgs;
	}
}
