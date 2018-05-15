package es.ucm.fdi.tp.view.gui.colors;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

class MyTableModel extends AbstractTableModel {

	/**
	* 
	*/
	private static final long serialVersionUID = 1L;

	private String[] colNames;
	List<String> players;

	MyTableModel(int n) {
		this.colNames = new String[] { "#Player", "Color" };
		
		players=new ArrayList<String>();
		for(int i=0;i<n;i++)
			players.add("");
		
	}

	@Override
	public String getColumnName(int col) {
		return colNames[col];
	}

	@Override
	public int getColumnCount() {
		return colNames.length;
	}

	@Override
	public int getRowCount() {
		return players != null ? players.size() : 0;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		if (columnIndex == 0) {
			return rowIndex;
		} else {
			return players.get(rowIndex);
		}
	}


	public void refresh() {
		fireTableDataChanged();
	}

};