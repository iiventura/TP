package es.ucm.fdi.tp.view.gui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import es.ucm.fdi.tp.base.model.GameAction;
import es.ucm.fdi.tp.base.model.GameState;
import es.ucm.fdi.tp.mvc.GameEvent;
import es.ucm.fdi.tp.mvc.GameObservable;
import es.ucm.fdi.tp.mvc.GameObserver;
import es.ucm.fdi.tp.view.gui.colors.GUIPlayersInfo;
import es.ucm.fdi.tp.view.gui.colors.PlayersComponent;
import es.ucm.fdi.tp.view.gui.cpanel.ControlPanel;
import es.ucm.fdi.tp.view.gui.message.GUIMessage;
import es.ucm.fdi.tp.view.gui.message.MessageComponent;



@SuppressWarnings("serial")
public class GameContainer<S extends GameState<S,A>,A extends GameAction<S,A>> extends GUIComponents<S,A>implements GameObserver<S,A> {
	private GUIComponents<S,A> gameView;
	private GUIMessage<S, A> messageViewer;
	private GUIPlayersInfo<S, A> playersInfoViewer;
	private ControlPanel<S, A> controlPanel;
	

	private GameController<S, A> gameCtrl;
	
	public GameContainer(String title,GameController<S, A> gameCtrl,
			GameObservable<S, A> game,GUIComponents<S, A> gameView){
		super(title);
		this.gameView = gameView;
		this.gameCtrl = gameCtrl;
		
		initGUI();
		game.addObserver(this);
	}
	

	
	private void initGUI(){
		messageViewer = new MessageComponent<S, A>(); 
		playersInfoViewer = new PlayersComponent<S, A>();
		controlPanel = new ControlPanel<S, A>();
		
		gameView.setMessageViewer(messageViewer);
		gameView.setPlayersInfoViewer(playersInfoViewer);
		
		controlPanel.setMessageViewer(messageViewer);
		
		controlPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		
		JPanel panelDerecho = new JPanel();
		
		panelDerecho.setLayout(new BorderLayout());
		panelDerecho.add(messageViewer,BorderLayout.NORTH);
		panelDerecho.add(playersInfoViewer);
		
		this.window.getContentPane().setLayout(new BorderLayout());
		this.window.getContentPane().add(gameView, BorderLayout.CENTER);
		this.window.getContentPane().add(controlPanel, BorderLayout.NORTH);
		this.window.getContentPane().add(panelDerecho, BorderLayout.EAST);
		
		this.window.setWindowEnable();
	}
	@Override
	public void notifyEvent(final GameEvent<S, A> e) {
		SwingUtilities.invokeLater(new Runnable(){

			@Override
			public void run() {
				handleEvent(e);}
			
		});
		
	}

	 private void handleEvent(final GameEvent<S,A> e){
		 switch ( e.getType() ) {
	       case Start:
	    	   messageViewer.setContent(e.toString()+"\n");
	    	   gameView.update(e.getState());
	    	   
	    	   if(gameCtrl.getPlayerId()!=e.getState().getTurn())
					controlPanel.disable();
				else
					controlPanel.enable();
				break;
			case Change:
				
				gameView.update(e.getState());
				
				 if(gameCtrl.getPlayerId()!=e.getState().getTurn())
						controlPanel.disable();
					else
						controlPanel.enable();
				
				
				break;
			case Error:
				messageViewer.addContent(e.getError().getMessage());
				controlPanel.disable();
				break;
			case Stop:
				controlPanel.disable();
				
				break;
			case Info:
			
				break;

			default:
				break;
	       }
	    SwingUtilities.invokeLater( new Runnable() {
	   public void run() { gameCtrl.handleEvent(e); }
	  });
	 }
	 
	@Override
	public void enable() {
		messageViewer.enable();
		playersInfoViewer.enable();
		controlPanel.enable();
		gameView.disable();
	}

	@Override
	public void disable() {
		messageViewer.disable();
		playersInfoViewer.disable();
		controlPanel.disable();
		gameView.disable();
		
	}

	@Override
	public void update(S state) {
		gameView.update(state);
	}

	

	@Override
	public void setGameController(GameController<S, A> gameCtrl) {
		gameView.setGameController(gameCtrl);
		messageViewer.setGameController(gameCtrl);
		controlPanel.setGameController(gameCtrl);
		
	}
	
	@Override
	public void setMessageViewer(GUIMessage<S, A> infoViewer) {	}

	@Override
	public void setPlayersInfoViewer(GUIPlayersInfo<S, A> playersInfoViewer) {}

}
