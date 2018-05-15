package es.ucm.fdi.tp.launcher;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import es.ucm.fdi.tp.base.console.ConsolePlayer;
import es.ucm.fdi.tp.base.model.GameAction;
import es.ucm.fdi.tp.base.model.GamePlayer;
import es.ucm.fdi.tp.base.model.GameState;
import es.ucm.fdi.tp.base.player.RandomPlayer;
import es.ucm.fdi.tp.base.player.SmartPlayer;
import es.ucm.fdi.tp.ttt.TttState;
import es.ucm.fdi.tp.was.WolfAndSheepState;
/**
 * Clase Main del juego ampliado para el juego was 
 */
public class MainP4 {
	private static Scanner in= new Scanner(System.in);
	/**
	 * Se encarga de crear el Estado inicial del juego solicitado por el 
	 * @param gameName nombre del juego
	 * @return el juego creado o null si el nombre no coincide con ninguno de los juegos
	 */
	public static GameState<?,?> createInitialState(String gameName){
		if(gameName.equalsIgnoreCase("ttt"))
			return new TttState(3);
		else if(gameName.equalsIgnoreCase("was"))
			return new WolfAndSheepState();
		else
			return null;
	}
	
	/**
	 * Se enarga de crear el tipo de jugador de la partida
	 * @param gameName nombre del juego
	 * @param playerType tipo de jugador
	 * @param playerName nombre del jugador
	 * @return
	 */
	public static GamePlayer createPlayer(String gameName, String playerType, String playerName){
		
		if (playerType.equalsIgnoreCase("smart"))
			return new SmartPlayer(playerName, 5);
		else  if (playerType.equalsIgnoreCase("rand"))
			return new RandomPlayer(playerName);
		else if  (playerType.equalsIgnoreCase("console")){						
			return  new ConsolePlayer(playerName,in);
			
		}else
			return null;
	}
	
	/**
	 * Comienza la ejecucios del juego
	 * @param initialState un juego inicializado
	 * @param players una lista de jugadores
	 * @return el estado general del juego
	 */
	public static <S extends GameState<S, A>, A extends GameAction<S, A>> int playGame(GameState<S, A> initialState,
			List<GamePlayer> players) {
		int playerCount = 0;
		for (GamePlayer p : players) {
			p.join(playerCount++); // welcome each player, and assign
									// playerNumber
		}
		@SuppressWarnings("unchecked")
		S currentState = (S) initialState;

		while (!currentState.isFinished()) {
			// request move
			A action = players.get(currentState.getTurn()).requestAction(currentState);
			// apply move
			currentState = action.applyTo(currentState);
			System.out.println("After action:\n" + currentState);

			if (currentState.isFinished()) {
				// game over
				String endText = "The game ended: ";
				int winner = currentState.getWinner();
				if (winner == -1) {
					endText += "draw!";
				} else {
					endText += "player " + (winner + 1) + " (" + players.get(winner).getName() + ") won!";
				}
				System.out.println(endText);
			}
		}
		return currentState.getWinner();
	}


	/**
	 * Metodo main que evalua la prueba para jugar WAS
	 * 
	 * @param args argumentos recibidos 
	 */
	public static void main(String[] args) {
		String entrada="";
		String name;
		boolean exit=false;
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		List<GamePlayer> players=new ArrayList<GamePlayer>();
	
		try{
			while(!exit){
				System.out.println("Introduzca el juego que desea ( ttt / was ) y los 2 jugadores que desea ( console/ rand / smart)");
				entrada = br.readLine();
				
				if(entrada.equalsIgnoreCase("exit")){
					exit=true;
				}else{
					
					String[] argumentos = entrada.split(" ");
					
					if(argumentos.length==3){
						GameState<?, ?> game = createInitialState(argumentos[0]);
						
						if(game!=null){
							
							System.out.println("Introduzca nombre del primer jugador:");
							 name = in.nextLine();
							GamePlayer firstPlayer=createPlayer(argumentos[0],argumentos[1],name);
							
							if(firstPlayer!=null){
								
								System.out.println("Introduzca nombre del segundo jugador:");
								 name = in.nextLine();
								GamePlayer secondPlayer=createPlayer(argumentos[0],argumentos[2],name);
								
								if(secondPlayer!=null){
								
									players.add(firstPlayer);
									players.add(secondPlayer);
									playGame(game, players);
									
								
									
								}else 
									throw new Exception("El tipo del segundo jugador es incorrecto");
								
							}else 
								throw new Exception("El tipo del primer jugador es incorrecto");
						}else
							throw new Exception("El juego indicado no existe en esta aplicacion");
					}else 
						throw new Exception("El numero de parametros de entrada es incorrecto.");
				}
				
			}
			
			
			
	
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}finally{
			in.close();
			System.out.println("Fin del Programa...");
		}
	 
		

	}

}
