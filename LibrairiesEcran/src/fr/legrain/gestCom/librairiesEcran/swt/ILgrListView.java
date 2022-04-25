package fr.legrain.gestCom.librairiesEcran.swt;

public interface ILgrListView<T> {
	public void update(T t);
	
	public void refresh(T t);
	
	public void remove(T t);
	
	public void select(T t);
	
	public void select(int index);
}
