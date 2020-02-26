package modele;

import java.util.ArrayList;
import javax.swing.AbstractListModel;

public class GroupListModel extends AbstractListModel<String> {
	private static final long serialVersionUID = 1L;
	private ArrayList<String> groups = new ArrayList<String>();

	public String getElementAt(int index) {
		return (String) this.groups.get(index);
	}

	public int getSize() {
		return this.groups.size();
	}

	public void addElement(String x) {
		this.groups.add(x);
		this.fireContentsChanged(this, 0, this.groups.size());
	}

	public void deleteElement(Object o) {
		this.groups.remove(o);
		this.fireContentsChanged(this, 0, this.groups.size());
	}

	public int getindex(Object o) {
		return this.groups.indexOf(o);
	}

	public ArrayList<String> getList() {
		return this.groups;
	}

	public ArrayList<String> clearList() {
		this.groups.clear();
		return this.groups;
	}
}