package es.ucm.fdi.tp.base.model;

public class GameError extends RuntimeException {
	private static final long serialVersionUID = 4703354133717328836L;
	
	public GameError(String msg) {
		super(msg);
	}

}
