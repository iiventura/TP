package tpmv.inst.assigments;

import tpmv.bc.ByteCode;
import tpmv.bc.Push;
import tpmv.elements.Compiler;

public class Number implements Term{
	private Integer n;
	
	public Number(int n){
		this.n=n;
	}

	@Override
	public ByteCode compile(Compiler compile) {
		return new Push(this.n);
	}

	@Override
	public Term parse(String term) {
		try{
			
			this.n=Integer.parseInt(term);
			
			if(this.n!=null)
				return new Number(this.n);
			else 
				return null;
		
			
		}catch (NumberFormatException e) {
			System.err.println("Number Format Exception at this term: "+ term);
			return null;
		}
	}

	public String toString(){
		return  String.valueOf(this.n) ;
	}
	
	

}
