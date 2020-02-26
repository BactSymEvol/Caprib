package modele;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

import javax.swing.table.AbstractTableModel;

public class ResultsetListModel extends AbstractTableModel {
	
	private static final long serialVersionUID = 1L;
	private ResultSet rs;
	private ResultSetMetaData rmd;
	
	public ResultsetListModel(ResultSet rs){
		this.rs=rs;
		
		try {
			rmd=rs.getMetaData();
			
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
	}
	@Override
	public int getColumnCount() {
		
		try {
			return rmd.getColumnCount();
		} catch (SQLException e) {
			
			e.printStackTrace();
			return 0;
		}
	}

	@Override
	public int getRowCount() {
		try {
			rs.last();
			return rs.getRow();
		} catch (SQLException e) {			
			e.printStackTrace();
			return 0;
		}
		
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		try {
			rs.absolute(rowIndex+1);
			return rs.getObject(columnIndex +1);
			
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
		
		
	}
	public String getColumnName(int i){
		
		try {
			return rmd.getColumnName(i+1);
		} catch (SQLException e) {
			
			e.printStackTrace();
			return null;
		}
	}
	
	public void clear(){
		
	}

}
