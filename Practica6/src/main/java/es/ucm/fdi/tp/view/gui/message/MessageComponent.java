package es.ucm.fdi.tp.view.gui.message;



import java.awt.Dimension;

import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import es.ucm.fdi.tp.base.model.GameAction;
import es.ucm.fdi.tp.base.model.GameState;
import es.ucm.fdi.tp.view.gui.GameController;


@SuppressWarnings("serial")
public class MessageComponent<S extends GameState<S,A>, A extends GameAction<S,A>> extends GUIMessage<S,A> {
	private JTextArea texto;
	private GameController<S,A> gameCtrl;
	
	public MessageComponent(){
		super();
		initGUI();
	}
	
	private void initGUI(){

		this.setTitle("Status Message");
		this.setPreferredSize(new Dimension(400, 200));
		texto=new JTextArea(10,40);
		texto.setEditable(false);
		
		JScrollPane scrollPane = new JScrollPane(texto);
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		this.add(scrollPane);
	}
	
	/**
	 * Mantiene el mensaje actual y agrega uno nuevo
	 * @param msg texto
	 */
	@Override
	public void addContent(String msg) {
		texto.append("\n"+msg);
		
	}

	/**
	 * Elimina el mensaje actual y asigna el nuevo
	 * @param msg
	 */
	@Override
	public void setContent(String msg) {
		texto.replaceSelection(msg);
		
	}

	
	@Override
	public void update(S state){
		if(gameCtrl.getPlayerId()==state.getWinner())
			setContent("You win!!");
		else if((gameCtrl.getPlayerId()!=state.getWinner())&& (state.getWinner()!=-1))
			setContent("You have lost!");
	}
	
	@Override
	public void setGameController(GameController<S, A> gameCtrl) {
		this.gameCtrl=gameCtrl;
	}

	
	
	

}
