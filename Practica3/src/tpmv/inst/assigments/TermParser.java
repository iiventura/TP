package tpmv.inst.assigments;

public class TermParser {

	private static final Term[] terms={ new Variable(""),new Number(0)};

	
	public static Term parse(String st){
		Term tm;
		
		for ( int i =0 ; i<terms.length; i++) {
			tm = terms[i].parse(st);
			if ( tm != null) return tm;
			
		}
		return null;
	}
}
