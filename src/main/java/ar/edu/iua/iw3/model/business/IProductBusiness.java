package ar.edu.iua.iw3.model.business;

import java.util.List;

import ar.edu.iua.iw3.model.Product;

public interface IProductBusiness {
	
	public List<Product> list() throws BusinessException;
	
	//Cargar producto por id, espeera un producto en particular
	public Product load(long id) throws NotFoundException, BusinessException;
	
	//Cargar producto por su nombre (Notar que las excepiones son de tan alto nivel que te das cuenta de que pasa si no lo encuentra)
	public Product load(String product) throws NotFoundException, BusinessException;
	
	//Agregando un producto, y agregarla a la BD (Found exception nos diria que ya estaba....)
	public Product add(Product product) throws FoundException, BusinessException;
	
	//Modificar un producto
	public Product update(Product producto) throws FoundException, NotFoundException, BusinessException;
	
	//Borrar un producto por ID!!
	public void delete(long id) throws NotFoundException, BusinessException;

}
