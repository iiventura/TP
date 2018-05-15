package es.ucm.fdi.tp.view.gui.cpanel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import es.ucm.fdi.tp.base.model.GameAction;
import es.ucm.fdi.tp.base.model.GameState;
import es.ucm.fdi.tp.base.model.GamePlayer.PlayerMode;
import es.ucm.fdi.tp.view.gui.GUIComponents;
import es.ucm.fdi.tp.view.gui.GameController;
import es.ucm.fdi.tp.view.gui.colors.GUIPlayersInfo;
import es.ucm.fdi.tp.view.gui.message.GUIMessage;

@SuppressWarnings("serial")
public class ControlPanel<S extends GameState<S,A>, A extends GameAction<S,A>> extends GUIComponents<S,A>{
	private JButton rmove, smove, reset, stop,set;
	private JLabel tmode;
	private JComboBox<String> playersMode;
	private GameController<S,A> gameCtrl;
	
	public ControlPanel(){
		super();
		initGUI();
	}
	
	private void initGUI(){
		//Rand Button
		rmove=new JButton();
		rmove.setIcon(new ImageIcon("src/main/resources/dice.png"));
		rmove.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				gameCtrl.makeRandomMove();
			}});
		
		
		//Smart Button
		smove=new JButton();
		smove.setIcon(new ImageIcon("src/main/resources/nerd.png"));
		smove.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				gameCtrl.makeSmartMove();
				
			}});
		
		
		
		//Reset Button
		reset=new JButton();
		reset.setIcon(new ImageIcon("src/main/resources/restart.png"));
		reset.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				gameCtrl.restartGame();
				
			}});
		
		//Stop Button
		stop=new JButton();
		stop.setIcon(new ImageIcon("src/main/resources/exit.png"));
		stop.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				int n=JOptionPane.showOptionDialog(new JFrame(), "Seguro que desea salir del juego?","Salir del Juego",
						JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null,null, null);
				
				if(n==0){
					gameCtrl.stopGame();
					System.exit(0);
				}
			}});
		 
		tmode=new JLabel();
		tmode.setText("    Player Mode:");
		
		playersMode=new JComboBox<String>(new String[] { "Manual", "Random", "Smart" });
		//playersMode.setModel(new javax.swing.DefaultComboBoxModel<String>(new String[] { "Manual", "Random", "Smart" }));
		
	
		
		set=new JButton("Set");
		set.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
            	Object s= playersMode.getSelectedItem();
            	if(s.equals("Random") ){
            		gameCtrl.changePlayerMode(PlayerMode.RANDOM);
    				
    			}else if (s.equals("Smart") ){
    				gameCtrl.changePlayerMode(PlayerMode.SMART);
    				
    			}else if (s.equals("Manual")){
    				gameCtrl.changePlayerMode(PlayerMode.MANUAL);
    	
    			}
    			
            }
        });
		
		
		this.add(rmove);
		this.add(smove);
		this.add(reset);
		this.add(stop);
		this.add(tmode);
		this.add(playersMode);
		this.add(set);
	}
	
	
	@Override
	public void enable() {
		rmove.setEnabled(true);
		smove.setEnabled(true);
		reset.setEnabled(true);
		stop.setEnabled(true);
		playersMode.setEnabled(true);
		set.setEnabled(true);
	}

	@Override
	public void disable() {
		rmove.setEnabled(false);
		smove.setEnabled(false);
		reset.setEnabled(false);
		stop.setEnabled(false);
		playersMode.setEnabled(false);
		set.setEnabled(false);
	}

	@Override
	public void setGameController(GameController<S, A> gameCtrl) {
		this.gameCtrl=gameCtrl;	
	}
	

	
	
	
	//Metodos que no son indispensables en este componente
	@Override
	public void update(S state) {}
	public void setPlayersInfoViewer(GUIPlayersInfo<S, A> playersInfoViewer) {}
	public void setMessageViewer(GUIMessage<S, A> infoViewer) {	}
	

}
