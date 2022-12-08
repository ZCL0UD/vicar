package vicar.model.renta.managers;
   
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ejb.EJB; 
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import vicar.model.core.entities.Almacenaje;
import vicar.model.core.entities.DetalleRenta;
import vicar.model.core.entities.NotaRenta;
import vicar.model.core.entities.Producto;
import vicar.model.core.entities.Almacenaje;
import vicar.model.core.managers.ManagerDAO;


/**
 * Session Bean implementation class ManagerRenta
 */
@Stateless
@LocalBean
public class ManagerRecepcion {

	@EJB
	private ManagerDAO mDAO;	
    /**
     * Default constructor. 
     */
    public ManagerRecepcion() {
       
    }
    
    
	public NotaRenta findByIdNotaRenta(int idNtr) throws Exception{
		return (NotaRenta) mDAO.findById(NotaRenta.class, idNtr);	
	}
	public List<Almacenaje> findWhereAlmcenaje(int idNtr) throws Exception{
		return mDAO.findWhere(Almacenaje.class, "o.notaRenta.idNtr="+idNtr, null);	
	}
	public List<DetalleRenta> findWhereproducto(int idNtr ) throws Exception  { 
		
		 return mDAO.findWhere(DetalleRenta.class, "o.notaRenta.idNtr="+idNtr, null);
	}
	public List<NotaRenta> findAllNotaRenta() {
		return mDAO.findAll(NotaRenta.class);
	}

	//Buscar Producto 
	public Producto findByIdProducto(int idProducto) throws Exception{
		return (Producto) mDAO.findById(Producto.class,idProducto );
	}
	//Actualizar la cantidad de producto, cuando se recepte
	public void updateCantidadProducto(Producto producto) throws Exception {
		mDAO.actualizar(producto);
	}
	
	public void registrarAlmacenaje(NotaRenta cabecera, Almacenaje almacenaje) throws Exception {
		Producto producto = new Producto();
		int updateStock = 0;
		for (int i = 0; i < cabecera.getDetalleRentas().size(); i++) {
			producto = (Producto) mDAO.findById(Producto.class,
					cabecera.getDetalleRentas().get(i).getProducto().getIdProducto());
			updateStock = producto.getProCantidad() + (cabecera.getDetalleRentas().get(i).getDetRentaCantidad());
			producto.setProCantidad(updateStock);
			mDAO.actualizar(producto);
		}
		almacenaje.setAlmFecha(new Date());
		almacenaje.setNotaRenta(cabecera);
		almacenaje.setIdCli(cabecera.getCliente().getIdCli());
		mDAO.insertar(almacenaje);
	}
	
	public List<Almacenaje> findAllAlmacenaje() {
		return mDAO.findAll(Almacenaje.class);
	}
	
	public List<DetalleRenta> findDetalleRenta(int idRenta) throws Exception {
		return mDAO.findWhere(DetalleRenta.class, "o.notaRenta=" + "'" + idRenta + "'", null);
	}
	
}
