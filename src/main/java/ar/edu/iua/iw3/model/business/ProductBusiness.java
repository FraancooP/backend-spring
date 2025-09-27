package ar.edu.iua.iw3.model.business;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ar.edu.iua.iw3.model.Product;
import ar.edu.iua.iw3.model.persistence.ProductRepository;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ProductBusiness implements IProductBusiness {
	
	
	
	@Autowired
	private ProductRepository productDAO;

	@Override
	public List<Product> list() throws BusinessException {
		
		try {
			return productDAO.findAll();
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw BusinessException.builder().ex(e).message(e.getMessage()).build();
		}
		
	}

	@Override
	public Product load(long id) throws NotFoundException, BusinessException {
		//Si el id que pasamos como parametro no existe, informamos con 404 y mensajke de que no se encontro
		Optional<Product> r; ///Definimos r
		try {
			r = productDAO.findById(id);//Asigna optional product en r, tratamos de cargar el ID de la BD
		}catch(Exception e) {
			log.error(e.getMessage(), e);
			throw BusinessException.builder().ex(e).build();//Si hay algun problema
		}
		if(r.isEmpty()) {//Si esta vacio, mandamos mensaje por rest
			throw NotFoundException.builder().message("No se encontro el producto = " + id).build();
		}
		
		return r.get();
	}

	@Override
	public Product load(String product) throws NotFoundException, BusinessException {
		//Igual que el anterior pero buscamos por producto
		Optional<Product> r; ///Definimos r
		try {
			r = productDAO.findByProduct(product);//Asigna optional product en r, tratamos de cargar el ID de la BD
		}catch(Exception e) {
			log.error(e.getMessage(), e);
			throw BusinessException.builder().ex(e).build();//Si hay algun problema
		}
		if(r.isEmpty()) {//Si esta vacio, mandamos mensaje por rest
			throw NotFoundException.builder().message("No se encontro el producto = '" + product +"'").build();
		}
		
		return r.get();
	}

	@Override
	public Product add(Product product) throws FoundException, BusinessException {
		try {
			load(product.getId());//Intenga pasar el ID, si no lo encuentra genera exepcion
			throw FoundException.builder().message("Se encontro el producto id = " + product.getId()).build();//Si pudimos encontrarlo tambien mandam,os el probl;ema(puede ser que este duplicado)!!!
		} catch(NotFoundException e) {
			
		}
		try {
			load(product.getProduct());
			throw FoundException.builder().message("Se encontro el producto  = '" + product.getProduct() + "'").build();
		} catch(NotFoundException e){
			
		}
		try {
			return productDAO.save(product);//Este metodo save devuelve el producto Completo
		}catch(Exception e) {
			log.error(e.getMessage(), e);
			throw BusinessException.builder().ex(e).build();
		}
	}

	@Override
	public Product update(Product product) throws FoundException, NotFoundException, BusinessException {
		load(product.getId());
		Optional<Product> nombreExistente = null;
		try {
			nombreExistente = productDAO.findByProductAndIdNot(product.getProduct(), product.getId());
		}catch (Exception e) {
			log.error(e.getMessage(),e);
			throw BusinessException.builder().ex(e).build();
		}
		if(nombreExistente.isPresent()) {
			throw FoundException.builder().message("Se encontro un producto nombre = " + product.getProduct()).build();
		}
		
		try {
			return productDAO.save(product);
		}catch(Exception e) {
			log.error(e.getMessage(),e);
			throw BusinessException.builder().ex(e).build();
		}
	}

	@Override
	public void delete(long id) throws NotFoundException, BusinessException {
		//Borramos por ID sino error de que id no existe...
		load(id);
		try {
			productDAO.deleteById(id);
		}catch(Exception e) {
			log.error(e.getMessage(),e);
			throw BusinessException.builder().ex(e).build();
		}
		

	}

}
