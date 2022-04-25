package fr.legrain.gestCom.Appli;

import fr.legrain.lib.data.ModelObject;

public interface IlgrMapper<DTO extends ModelObject,Entity> {

	public DTO entityToDto(Entity e);
	public Entity dtoToEntity(DTO e);
}
