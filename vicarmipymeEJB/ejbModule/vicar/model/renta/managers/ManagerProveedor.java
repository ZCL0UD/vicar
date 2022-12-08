package vicar.model.renta.managers;
   
import java.util.ArrayList; 
import java.util.List;

import javax.ejb.EJB; 
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import vicar.model.core.entities.Producto;
import vicar.model.core.entities.Proveedore;
import vicar.model.core.managers.ManagerDAO;
import vicar.model.core.utils.ModelUtil;


/**
 * Session Bean implementation class ManagerRenta
 */
@Stateless
@LocalBean
public class ManagerProveedor {

	@EJB
	private ManagerDAO mDAO;

	/**
	 * Default constructor.
	 */
	public ManagerProveedor() {
		// TODO Auto-generated constructor stub
	}

	public List<Producto> findAllProducto() {
		return mDAO.findWhere(Producto.class, "o.proDisponible=" + "true", null);
	}

	// Proveedor
	public void insertarRegistroProveedor(Proveedore proveedores) throws Exception {
		proveedores.setProvLog(true);
		mDAO.insertar(proveedores);
	}

	public List<Proveedore> findAllProveedore() {
		return mDAO.findWhere(Proveedore.class, "o.provLog=true", null);
	}

	public List<Proveedore> findByRuc(String ruc) {
		return mDAO.findWhere(Proveedore.class, "o.rucProveedor=" + "'" + ruc + "'", null);
	}


	public void updateProveedor(Proveedore proveedores) throws Exception {
		mDAO.actualizar(proveedores);
	}

	public void deleteProveedor(Proveedore proveedores) throws Exception {
		proveedores.setProvLog(false);
		mDAO.actualizar(proveedores);
	}
    
}
