package tpmv.inst.conditionals;

import tpmv.elements.LexicalParser;

public class ConditionParser {
	private static final Condition[] conditions={new Equal(), new Less(), new LessEq(), new NotEqual() };
	
	public static Condition parse(String st, LexicalParser parser){
		
		String[]  words = st.split(" +");
		if (words.length !=3) return null;
		Condition cd;
		
		for ( int i =0 ; i<conditions.length; i++) {
			cd = conditions[i].parse(words[0], words[1], words[2], parser);
			if ( cd != null) return cd;
			
		}
		return null;
	}
}
