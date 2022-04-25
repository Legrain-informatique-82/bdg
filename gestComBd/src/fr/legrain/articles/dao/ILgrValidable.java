package fr.legrain.articles.dao;

public interface ILgrValidable {

	public abstract void validate();

	public abstract void validate(String field);

}