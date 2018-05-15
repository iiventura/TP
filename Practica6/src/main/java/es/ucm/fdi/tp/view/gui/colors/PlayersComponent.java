package es.ucm.fdi.tp.view.gui.colors;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

import es.ucm.fdi.tp.base.model.GameAction;
import es.ucm.fdi.tp.base.model.GameState;
import es.ucm.fdi.tp.extra.jcolor.ColorChooser;
import es.ucm.fdi.tp.view.gui.table.JBoard;


@SuppressWarnings("serial")
public class PlayersComponent<S extends GameState<S,A>,A extends GameAction<S,A>> extends GUIPlayersInfo<S,A> {
	private int n;
	private List<Color> opciones;
	private JBoard board;
	
	private MyTableModel modelo;
	private Map<Integer,Color> colors;
	private ColorChooser colorChooser;
	
	public PlayersComponent(){
		initColores();
		initGUI();
	}
	
	private void initGUI(){
		this.setTitle("Players Information");
		this.setLayout(new BorderLayout());
		
		colors = new HashMap<>();
		colorChooser = new ColorChooser(new JFrame(), "Choose Line Color", Color.BLACK);
		
		//inicializar el color de los jugadores;
		for(int i=0;i<n;i++){
			colors.put(i,opciones.get(i));
		}
		
		// names table
				modelo = new MyTableModel(n);
				modelo.getRowCount();
				 final JTable table = new JTable(modelo) {
					private static final long serialVersionUID = 1L;

					// THIS IS HOW WE CHANGE THE COLOR OF EACH ROW
					@Override
					public Component prepareRenderer(TableCellRenderer renderer, int row, int col) {
						Component comp = super.prepareRenderer(renderer, row, col);

						// the color of row 'row' is taken from the colors table, if
						// 'null' setBackground will use the parent component color.
						if (col == 1)
							comp.setBackground(colors.get(row));
						else
							comp.setBackground(Color.WHITE);
						comp.setForeground(Color.BLACK);
						return comp;
					}
				};

				table.setToolTipText("Click on a row to change the color of a player");
				table.addMouseListener(new java.awt.event.MouseAdapter() {
					@Override
					public void mouseClicked(java.awt.event.MouseEvent evt) {
						int row = table.rowAtPoint(evt.getPoint());
						int col = table.columnAtPoint(evt.getPoint());
						if (row >= 0 && col >= 0) {
							changeColor(row);
						}
					}

				});

			this.add(new JScrollPane(table), BorderLayout.CENTER);
	}
	
	/**
	 * Crea una lista de Colores
	 */
	private void initColores(){
		opciones=new ArrayList<Color>();
		opciones.add(Color.BLUE);
		opciones.add(Color.GREEN);
		opciones.add(Color.MAGENTA);
		opciones.add(Color.ORANGE);
		opciones.add(Color.PINK);
		opciones.add(Color.RED);
		opciones.add(Color.YELLOW);
		opciones.add(Color.CYAN);
	}
	
	private void changeColor(int row) {
		colorChooser.setSelectedColorDialog(colors.get(row));
		colorChooser.openDialog();
		if (colorChooser.getColor() != null) {
			colors.put(row, colorChooser.getColor());
			repaint();
			board.repaint();
			
		}

	}
	/**
	 * Asigna el numero de jugadores que estan representados en el juego
	 */
	@Override
	public void setNumberOfPlayer(int n) {
		this.n=n;
		initGUI();
	}

	/**
	 * Devuelve el color de un jugador
	 */
	@Override
	public Color getPlayerColor(int p) {
		return colors.get(p);
	}

	public void setBoard(JBoard board){
		this.board=board;
	}

	
}
