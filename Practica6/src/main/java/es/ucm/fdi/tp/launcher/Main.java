package es.ucm.fdi.tp.launcher;


import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import javax.swing.SwingUtilities;

import es.ucm.fdi.tp.base.console.ConsolePlayer;
import es.ucm.fdi.tp.base.model.GameAction;
import es.ucm.fdi.tp.base.model.GamePlayer;
import es.ucm.fdi.tp.base.model.GameState;
import es.ucm.fdi.tp.base.player.ConcurrentAiPlayer;
import es.ucm.fdi.tp.base.player.RandomPlayer;
import es.ucm.fdi.tp.chess.ChessAction;
import es.ucm.fdi.tp.chess.ChessState;
import es.ucm.fdi.tp.mvc.GameTable;
import es.ucm.fdi.tp.ttt.TttAction;
import es.ucm.fdi.tp.ttt.TttState;

import es.ucm.fdi.tp.view.console.ConsoleController;
import es.ucm.fdi.tp.view.console.ConsoleView;
import es.ucm.fdi.tp.view.gui.GUIComponents;
import es.ucm.fdi.tp.view.gui.GUIController;
import es.ucm.fdi.tp.view.gui.GameContainer;
import es.ucm.fdi.tp.view.gui.GameController;
import es.ucm.fdi.tp.view.gui.cpanel.smart.SmartThread;
import es.ucm.fdi.tp.view.gui.table.ChessView;
import es.ucm.fdi.tp.view.gui.table.TttView;
import es.ucm.fdi.tp.view.gui.table.WasView;
import es.ucm.fdi.tp.was.WolfAndSheepAction;
import es.ucm.fdi.tp.was.WolfAndSheepState;




public class Main {
	private static Scanner in = new Scanner(System.in);

	/**
	 * Crea un juego
	 * @param gType nombre del juego
	 * @return tablero del juego
	 */

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static  GameTable<?, ?> createGame(String gType) {

		if (gType.equalsIgnoreCase("ttt")) {
			return new GameTable(new TttState(3));

		} else if (gType.equalsIgnoreCase("was")){
			return new GameTable(new WolfAndSheepState());
		}else if(gType.equalsIgnoreCase("chess")){
			return new GameTable(new ChessState());
		}else
			return null;

	}

	/**
	 * Se crea un jugador segun el tipo que se indica
	 * @param playerType tipo jugador
	 * @param playerName nombre
	 * @return GamePlayer (jugador)
	 */
	public static GamePlayer createPlayer(String playerType, String playerName) {

		if (playerType.equalsIgnoreCase("smart"))
			return new ConcurrentAiPlayer(playerName);

		else if (playerType.equalsIgnoreCase("rand"))
			return new RandomPlayer(playerName);

		else if (playerType.equalsIgnoreCase("manual")) {
			return new ConsolePlayer(playerName, in);

		} else
			return null;
	}
	
	
	/**
	 *Inicializa el modo consola el juego
	 * @param game juego
	 * @param playerModes tipo de jugadores
	 */
	private static <S extends GameState<S, A>, A extends GameAction<S, A>> void startConsoleMode(GameTable<S, A> game,
			String playerModes[]) {

		List<GamePlayer> players = new ArrayList<GamePlayer>();
		GamePlayer player;
		for (int i = 0; i < playerModes.length; i++) {
			player = createPlayer(playerModes[i], "Player" + (i + 1));

			if (player != null) {
				players.add(player);
			}
		}

		new ConsoleView<S, A>(game);
		new ConsoleController<S, A>(players, game).run();
	}
	
	

	private static <S extends GameState<S, A>, A extends GameAction<S, A>> void startGUIMode(final String gType,
			 final GameTable<S, A> game) {
	
		
		//List<GamePlayer> players = new ArrayList<GamePlayer>();
		//Recordar que los observer se agregar una vez creada la ventana
		
		
		
		try {
			SwingUtilities.invokeAndWait(new Runnable() {

				
				

				@Override
				public void run() {
					// Crear la ventana para cada jugador
					for (int i = (game.getState().getPlayerCount()-1);i>=0 ; i--) {
						GamePlayer p1=new RandomPlayer("Player 1");
						p1.join(i);
						GamePlayer p2=new ConcurrentAiPlayer("Player 2");
						p2.join(i);	
						SmartThread<S,A> thread=new SmartThread<S,A>(p2,game);
						GUIComponents<S, A> gView = createGameView(gType,game);
						GameController<S,A> gmCtrl=new GUIController<S,A>(i,p1,thread,game);
						GUIComponents<S,A> container=new GameContainer<S,A>(gType +" Player "+(i),gmCtrl,game,gView,thread);
						container.setGameController(gmCtrl);
						
					
					}
					
				}
				
				@SuppressWarnings("unchecked")
				private GUIComponents<S, A> createGameView(String gType,GameTable<S, A> game) {
					if(gType.equalsIgnoreCase("was")){
						return (GUIComponents<S, A>) new WasView((GameState<WolfAndSheepState, WolfAndSheepAction>) game.getState());
					}else if(gType.equalsIgnoreCase("ttt")){
						return (GUIComponents<S, A>) new TttView((GameState<TttState, TttAction>) game.getState());
					}else if(gType.equalsIgnoreCase("chess")){
						return (GUIComponents<S, A>) new ChessView((GameState<ChessState,ChessAction>)game.getState());
					}else
						return null;
				}

			
			});
			}catch(InvocationTargetException | InterruptedException e){
				e.printStackTrace();
				System.err.println("Some error ocurred while creating the view");
				System.exit(1);
			}
		
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				game.start();
			}
		});
		

	}
	
	
	/**
	 * Indica los datos que tiene que pasar por los argumentos 
	 * para que el programa  funcione
	 */
	private static void usage() {
		System.out.println("Usage: game mode player1 player2 ...");
	}

	public static void main(String[] args) {

		if (args.length <= 2) {
			usage();
			System.exit(1);
		}

		GameTable<?, ?> game = createGame(args[0]);

		if (game == null) {
			System.err.println("Invalid game");
			usage();
			System.exit(1);
		}

		String[] otherArgs = Arrays.copyOfRange(args, 2, args.length);
		switch (args[1]) {
		case "console":
			startConsoleMode(game, otherArgs);
			break;

		case "gui":
			startGUIMode(args[0], game);
			break;

		default:
			System.err.println("Invalid view mode: " + args[1]);
			usage();
			System.exit(1);
		}
	}

}