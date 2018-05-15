package tpmv.command;

import tpmv.program.Engine;

public class Replace extends Command {
	private int pos;
	/**
	 * Constructora por defecto de la Clase 
	 */
	public Replace(){}
	/**
	 * Contructora con parametro
	 * @param pos en la que esta guardado el bytecode
	 */
	public Replace(int pos){
		this.pos=pos;
	}
	/**
	 * Ejecuta el reemplazo de Bytecode
	 */
	@Override
	public boolean execute(Engine engine) {
		 return engine.replaceByteCode(this.pos);
	}
	/**
 	* Verifica que es REPLACE
 	*/
	@Override
	public Command parse(String[] s) {
		if (s.length==2 && s[0].equalsIgnoreCase("REPLACE")){

			try{
				this.pos=Integer.parseInt(s[1]);
				return new Replace(pos); 
				
			}catch (Exception e){
				return null;
			}
			
			
		}else return null;
		
	}
	
	/**
	 * Envia la funcion de REPLACE
	 */
	@Override
	public String textHelp() {
		
		return "  REPLACE N: Reemplaza la instruccion N por la solicitada al usuario"+
				System.getProperty("line.separator");
	}
	
	/**
	 * Envia el comando REPLACE
	 */
	public String toString(){
		return "REPLACE"+ this.pos;
	}

}
