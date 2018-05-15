package tpmv.bcode;
/**
 * Clase ByteCode que engloba todos los comandos de la maquina
 * que se pueden ejecutar en el programa
 * author: Irene Ventura Farias
 * version:06/12/16
 */
import tpmv.program.CPU;

public abstract class ByteCode {
	abstract public boolean execute(CPU cpu);   
	abstract public ByteCode parse(String[] words);

}
