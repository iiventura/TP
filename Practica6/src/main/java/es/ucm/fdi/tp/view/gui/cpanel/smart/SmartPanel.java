package es.ucm.fdi.tp.view.gui.cpanel.smart;


import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;

import es.ucm.fdi.tp.base.model.GameAction;
import es.ucm.fdi.tp.base.model.GameState;
import es.ucm.fdi.tp.view.gui.GUIComponents;
import es.ucm.fdi.tp.view.gui.GameController;
import es.ucm.fdi.tp.view.gui.colors.GUIPlayersInfo;
import es.ucm.fdi.tp.view.gui.message.GUIMessage;

@SuppressWarnings("serial")
public class SmartPanel<S extends GameState<S,A>, A extends GameAction<S,A>> extends GUIComponents<S,A> {
	private JLabel ibrain, itimer,term, term1;
	private JSpinner threads, timer;
	private JButton stop;
	private SmartThread<S, A> thread;
	private GUIMessage<S, A> infoViewer;
	
	
	public SmartPanel(){
		initGUI();
	}

	private void initGUI() {
		this.setBorder(BorderFactory.createTitledBorder("Smart Moves"));
		this.setLayout(new FlowLayout(FlowLayout.LEFT));
		
		ibrain=new JLabel();
		ibrain.setPreferredSize(new Dimension(35,35));
		ibrain.setIcon(new ImageIcon("src/main/resources/brain.png"));
		
		
		threads=new JSpinner();
		threads.setModel( new SpinnerNumberModel(1, 1,  Runtime.getRuntime().availableProcessors(), 1));
		
		term=new JLabel("threads   	");
		
		itimer=new JLabel();
		itimer.setPreferredSize(new Dimension(35,35));
		itimer.setIcon(new ImageIcon("src/main/resources/timer.png"));
		
		timer=new JSpinner();
		timer.setModel(new SpinnerNumberModel(500, 500, 5000, 500));
		
		term1=new JLabel("ms. ");
		
		stop=new JButton();
		stop.setIcon(new ImageIcon("src/main/resources/stop.png"));
		stop.setEnabled(false);
		
		stop.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				thread.interrupt();
			}
		});
		
		
		this.add(ibrain);
		this.add(threads);
		this.add(term);
		this.add(itimer);
		this.add(timer);
		this.add(term1);
		this.add(stop);
		
	}

	@Override
	public void enable() {
		ibrain.setOpaque(true);
		ibrain.setBackground(Color.YELLOW);
		stop.setEnabled(true);
		
	}

	@Override
	public void disable() {
		stop.setEnabled(false);
		ibrain.setBackground(Color.LIGHT_GRAY);
	}
	
	public void setMessage(String text){
		this.infoViewer.addContent(text);
	}
	
	public void setThread(SmartThread<S,A> thread){
		this.thread=thread;
		this.thread.setSmartPanel(this);
	}

	@Override
	public void setMessageViewer(GUIMessage<S, A> infoViewer) {
		this.infoViewer=infoViewer;
	}
	
	
	public int getNThreads(){
		return (int)threads.getValue();
	}
	
	public int getTimeout(){
		return (int)timer.getValue();
	}
	
	
	
	@Override
	public void setGameController(GameController<S, A> gameCtrl) {}
	@Override
	public void setPlayersInfoViewer(GUIPlayersInfo<S, A> playersInfoViewer) {}
	@Override
	public void update(S state) {}
	@Override
	public void setSmartPanel(SmartPanel<S, A> sPanel) {}
	
}
