package vicar.model.renta.managers;
   
import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB; 
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import vicar.model.core.entities.Producto;
import vicar.model.core.managers.ManagerDAO;
import vicar.model.core.utils.ModelUtil;


/**
 * Session Bean implementation class ManagerRenta
 */
@Stateless
@LocalBean
public class ManagerProductos {

	@EJB
	private ManagerDAO mDAO;
    /**
     * Default constructor. 
     */
    public ManagerProductos() {
        // TODO Auto-generated constructor stub
    }
    
    
    public List<Producto> findAllProducto(){
    	return mDAO.findWhere(Producto.class, "o.proDisponible="+"true",null);
    }
    
    public void insertarProducto(Producto producto) throws Exception {
    	producto.setProDisponible(true);
    	mDAO.insertar(producto);
    }
    
    
    public void updateProducto(Producto producto) throws Exception {
    	mDAO.actualizar(producto);
    }
    
    public void deleteProductologico(Producto producto) throws Exception {
    	producto.setProDisponible(false);
    	mDAO.actualizar(producto);
    }
   
}
