package vicar.model.renta.managers;

import java.math.BigDecimal;     
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ejb.EJB; 
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import vicar.model.seguridades.managers.ManagerSeguridades;
import vicar.model.core.entities.Compra;
import vicar.model.core.entities.DetalleCompra;
import vicar.model.core.entities.DetalleRenta;
import vicar.model.core.entities.NotaRenta;
import vicar.model.core.entities.Producto;
import vicar.model.core.entities.Proveedore;
import vicar.model.core.entities.SegUsuario;
import vicar.model.core.managers.ManagerDAO;
import vicar.model.core.utils.ModelUtil;


/**
 * Session Bean implementation class ManagerRenta
 */
@Stateless
@LocalBean
public class ManagerCompras {

	@EJB
	private ManagerDAO mDAO;
	@EJB
	private ManagerSeguridades mSeguridades;
    /**
     * Default constructor. 
     */
    public ManagerCompras() {
        // TODO Auto-generated constructor stub
    }
    
    //KARELIS
    
    public List<Compra> findAllCompra() {
		return mDAO.findAll(Compra.class);
	}

	public List<Compra> findByNotaCompra(int idCompra) throws Exception {
		return mDAO.findWhere(Compra.class, "o.idCompra=" + "'" + idCompra + "'", null);
	}

	public List<DetalleCompra> findDetalleCompra(int idCompra) throws Exception {
		return mDAO.findWhere(DetalleCompra.class, "o.compra=" + "'" + idCompra + "'", null);
	}
	
	public List<Producto> findAllProducto() {
		return mDAO.findWhere(Producto.class, "o.proDisponible=" + "true", null);
	}
	//------------------------------------------------------------------------------------------------------
	//CHARLIE
	
	// Insertar Compra
		public Compra adicionarProducto(Compra cabecera, String ruc, int idProducto, int cantidadProducto, int idSegUsuario)
				throws Exception {
			// comprobar si es el primer producto:
			if (cabecera == null) {
				List<Proveedore> pro = mDAO.findWhere(Proveedore.class, "o.rucProveedor=" + "'" + ruc + "'", null);
				SegUsuario gerente = (SegUsuario) mDAO.findById(SegUsuario.class, idSegUsuario);
				cabecera = new Compra();
				cabecera.setDetalleCompras(new ArrayList<DetalleCompra>());
				cabecera.setCompFecha(new Date());
				cabecera.setProveedore(pro.get(0));
				cabecera.setSegUsuario(gerente);
				cabecera.setCompTotal(new BigDecimal(0));
			}
			Producto producto = (Producto) mDAO.findById(Producto.class, idProducto);
			cabecera.setCompTotal(cabecera.getCompTotal()
					.add(producto.getProPrecioEquipo().multiply(BigDecimal.valueOf(cantidadProducto))));
			DetalleCompra detalle = new DetalleCompra();
			detalle.setProducto(producto);
			detalle.setCompra(cabecera);
			detalle.setDetCompCantidad(cantidadProducto);
			cabecera.getDetalleCompras().add(detalle);
			return cabecera;
		}

		public Compra eliminarProducto(Compra cabecera, int index) throws Exception {
			int idProducto = (int) cabecera.getDetalleCompras().get(index).getProducto().getIdProducto();
			int cantidadProducto = (int) cabecera.getDetalleCompras().get(index).getDetCompCantidad();
			Producto producto = (Producto) mDAO.findById(Producto.class, idProducto);
			cabecera.setCompTotal(cabecera.getCompTotal()
					.subtract(producto.getProPrecioEquipo().multiply(BigDecimal.valueOf(cantidadProducto))));
			cabecera.getDetalleCompras().remove(index);
			return cabecera;
		}

		public void registrarCompra(Compra cabecera) throws Exception {
			if (cabecera == null || cabecera.getDetalleCompras() == null || cabecera.getDetalleCompras().size() == 0)
				throw new Exception("Debe seleccionar por lo menos un equipo");
			Producto producto = new Producto();
			int updateStock = 0;
			for (int i = 0; i < cabecera.getDetalleCompras().size(); i++) {
				producto = (Producto) mDAO.findById(Producto.class,
						cabecera.getDetalleCompras().get(i).getProducto().getIdProducto());
				updateStock = producto.getProCantidad() + (cabecera.getDetalleCompras().get(i).getDetCompCantidad());
				producto.setProCantidad(updateStock);
				mDAO.actualizar(producto);
			}
			mDAO.insertar(cabecera);
		}
		
		public List<Compra> findCompraByFecha(String fecha1, String fecha2) {
			return mDAO.findWhere(Compra.class,
					"o.compFecha>=" + "'" + fecha1 + "'" + " AND " + "o.compFecha<=" + "'" + fecha2 + "'", null);
		}
		
		public List<Proveedore> findAllProveedores() {
			return mDAO.findAll(Proveedore.class);
		}
}
