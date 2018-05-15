package es.ucm.fdi.tp.view.gui.window;

import java.awt.Dimension;

import javax.swing.JFrame;

import es.ucm.fdi.tp.base.model.GameAction;
import es.ucm.fdi.tp.base.model.GameState;

@SuppressWarnings("serial")
public class GameWindow<S extends GameState<S,A>, A extends GameAction<S,A>> extends JFrame {
	public GameWindow(String title){
		this.setTitle(title);
		this.setMinimumSize(new Dimension(950, 600));
		this.setLocationRelativeTo(null);
	
	}

	public void setWindowEnable(){
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
	}
	
	public void setWindowDisable(){
		this.dispose();
	}
	
	
}
