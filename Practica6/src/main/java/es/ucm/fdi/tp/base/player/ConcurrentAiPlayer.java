package es.ucm.fdi.tp.base.player;

import es.ucm.fdi.tp.base.model.GameAction;
import es.ucm.fdi.tp.base.model.GameState;

//P6-ini
public class ConcurrentAiPlayer extends AiPlayer {

	public static final int DEFAULT_TIME = 1000;
	public static final int DEFAULT_THREADS = 1;
	
	private int maxThreads = DEFAULT_THREADS;
	private int timeOut = DEFAULT_TIME;
	private double minMaxValue = 0;
	private int evaluationCount = 0;
	
	public ConcurrentAiPlayer(String name) {
		super(name, null);
	}

	public <S extends GameState<S, A>, A extends GameAction<S, A>> A requestAction(S state) {
		ConcurrentDeepeningMinMax algorithm = new ConcurrentDeepeningMinMax(maxThreads);
		MinMax.Node<S,A> node = algorithm.chooseNode(state, playerNumber, timeOut);
		if (node != null) {
			minMaxValue = node.getValue();
			evaluationCount = algorithm.getEvaluationCount();
			return node.getMove();
		} else {
			minMaxValue = 0.0;
			evaluationCount = 0;
			return null;
		}
		
	}
	
	public int getEvaluationCount() {
		return evaluationCount;
	}

	public double getValue() {
		return minMaxValue;
	}
	
	public void setMaxThreads(int nThreads) {
		this.maxThreads = nThreads;
	}
	
	public void setTimeout(int millis) {
		this.timeOut = millis;
	}
}
//P6-fin