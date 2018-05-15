package es.ucm.fdi.tp.view.console;



import es.ucm.fdi.tp.base.model.GameAction;
import es.ucm.fdi.tp.base.model.GameState;
import es.ucm.fdi.tp.mvc.GameEvent;
import es.ucm.fdi.tp.mvc.GameObservable;
import es.ucm.fdi.tp.mvc.GameObserver;


public class ConsoleView<S extends GameState<S, A> , A extends GameAction<S,A>> 
implements GameObserver<S,A>{

	public ConsoleView(GameObservable<S, A> gameTable) {
		gameTable.addObserver(this);
	}


	public  void notifyEvent(GameEvent<S,A> e){
		switch (e.getType()) {
		case Start:
			System.out.println(e.toString());
			System.out.println(e.getState().toString());
			break;
			
		case Change:
			System.out.println("After action:\n" + e.getState().toString());
		
			break;
		case Error:
			System.out.println(e.getError().getMessage());
			break;
		case Stop:
			String endText = "The game ended ";
			
			if(e.getState().isFinished()){
				int winner = e.getState().getWinner();
				if (winner == -1) {
					endText += ": draw!";
				} else {
					endText += ": player " + (winner + 1) + " won!";
				}
			}else{
				endText +="!!";
			}
			
				System.out.println(endText);
			
			break;
		case Info:
			e.toString();
			break;

		default:
			break;
		}

	}

}
