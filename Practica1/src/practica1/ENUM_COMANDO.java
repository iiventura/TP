package practica1;

public enum ENUM_COMANDO {
	HELP, QUIT, NEWINST(2),
	RUN,RESET,REPLACE(1);
	
	private int numArgs;
	
	ENUM_COMANDO (){
		this(0);
	}
	
	ENUM_COMANDO(int n){
		numArgs=n;
	}
	
	public int getNumArgs(){
		return numArgs;
	}
}
