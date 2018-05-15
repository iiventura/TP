package practica1;

/**
 * La Clase CPU simula a un CPU que maneja instrucciones
 * para una pila y una memoria
 * @author Irene Ventura Farias
 * @version 12/11/16
 */

public class CPU {
	private Memory memory=new Memory();
	private OperandStack stack=new OperandStack();
	private boolean halt=false;
	
	/**
	 * Metodo que  si una maquina esta detenida
	 * @return un boolean 
	 */
	public boolean isHalted(){
		return this.halt;
	}
	
	/**
	 * Metodo que muestra el estado actual de la CPU
	 * @return string que muestra el estado de la pila y memoria
	 * 
	 */
	public String toString(){
		String s = System.getProperty("line.separator") +
				"Estado de la CPU: " + System.getProperty("line.separator") +
				" Memoria: " + this.memory + System.getProperty("line.separator") +
				" Pila: " + this.stack + System.getProperty("line.separator");
		
		return s;
	}
	
	/**
	 * Metodo que reinicia el estado de la pila , la memoria 
	 * y la reactiva
	 * @return boolean que verifica que la memoria y la pila 
	 * se reiniciaron correctamente
	 */
	public boolean reset(){
		this.halt=false;
		return this.stack.reset() &&this.memory.reset();
	}
	
	/**
	 * Metodo que emplea la ejecucio de funciones basicas
	 * @param bc un ByteCode que define cuale funciones hay que
	 * llamar de la pila y la memoria
	 * @return si se ejecuto o no el bytecode
	 */
	public boolean execute(ByteCode bc){
		
		if(isHalted())
			return true;
		else if(bc.getOpCode()==ENUM_BYTECODE.PUSH)
			return this.stack.push(bc.getParam());
		else if(bc.getOpCode()==ENUM_BYTECODE.LOAD){
			int pos=bc.getParam();
			return this.stack.push(memory.read(pos));
		}else if(bc.getOpCode()==ENUM_BYTECODE.STORE){
			int pos=bc.getParam();
			int value=this.stack.pop();
			return this.memory.write(pos,value);
		}else if(bc.getOpCode()==ENUM_BYTECODE.ADD)
			return this.stack.add();
		else if(bc.getOpCode()==ENUM_BYTECODE.SUB)
			return this.stack.sub();
		else if(bc.getOpCode()==ENUM_BYTECODE.MUL)
			return this.stack.mul();
		else if(bc.getOpCode()==ENUM_BYTECODE.DIV)
			return this.stack.div();
		else if(bc.getOpCode()==ENUM_BYTECODE.OUT){
			/*
			 * Lo desapila y apila para no perder el valor
			 *  en la pila
			 */
			int top=this.stack.pop();
			this.stack.push(top);
			System.out.println(top);
			
			return true;
				
		}else if(bc.getOpCode()==ENUM_BYTECODE.HALT){
			return this.halt=true;
		}	
		else return false;
		
		
	}
	
}
