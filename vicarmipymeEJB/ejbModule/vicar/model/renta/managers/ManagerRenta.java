
package vicar.model.renta.managers;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import vicar.model.core.utils.ModelUtil;
import vicar.model.core.entities.Cliente;
import vicar.model.core.entities.DetalleRenta;
import vicar.model.core.entities.NotaRenta;
import vicar.model.core.entities.Producto;
import vicar.model.core.managers.ManagerDAO;

/**
 * Session Bean implementation class ManagerRenta
 */
@Stateless
@LocalBean
public class ManagerRenta {

	@EJB
	private ManagerDAO mDAO;

	/**
	 * Default constructor.
	 */
	public ManagerRenta() {
		// TODO Auto-generated constructor stub
	}

	public NotaRenta adicionarProducto(NotaRenta cabecera, String cedula, int idProducto, int cantidadProducto,
			int days) throws Exception {
		// comprobar si es el primer producto:
		if (cabecera == null) {
			List<Cliente> cli = mDAO.findWhere(Cliente.class, "o.cliCedula=" + "'" + cedula + "'", null);
			cabecera = new NotaRenta();
			cabecera.setDetalleRentas(new ArrayList<DetalleRenta>());
			cabecera.setNtrFechaActual(new Date());
			cabecera.setNtrFechaInicio(new Date());
			cabecera.setNtrFechaFin(ModelUtil.addDays(new Date(), days));
			cabecera.setNtrDescripcion("Alquiler");
			cabecera.setCliente(cli.get(0));
			cabecera.setNtrTotal(new BigDecimal(0));
		}
		Producto producto = (Producto) mDAO.findById(Producto.class, idProducto);
		cabecera.setNtrTotal(cabecera.getNtrTotal().add(producto.getProPrecioAlquiler()
				.multiply(BigDecimal.valueOf(cantidadProducto).multiply(BigDecimal.valueOf(days)))));
		DetalleRenta detalle = new DetalleRenta();
		detalle.setProducto(producto);
		detalle.setNotaRenta(cabecera);
		detalle.setDetRentaCantidad(cantidadProducto);
		cabecera.getDetalleRentas().add(detalle);
		return cabecera;
	}

	public NotaRenta eliminarProducto(NotaRenta cabecera, int index, int days) throws Exception {
		int idProducto = (int) cabecera.getDetalleRentas().get(index).getProducto().getIdProducto();
		int cantidadProducto = (int) cabecera.getDetalleRentas().get(index).getDetRentaCantidad();
		Producto producto = (Producto) mDAO.findById(Producto.class, idProducto);
		cabecera.setNtrTotal(cabecera.getNtrTotal().subtract(producto.getProPrecioAlquiler()
				.multiply(BigDecimal.valueOf(cantidadProducto).multiply(BigDecimal.valueOf(days)))));
		cabecera.getDetalleRentas().remove(index);
		return cabecera;
	}

	public void registrarRenta(NotaRenta cabecera) throws Exception {
		if (cabecera == null || cabecera.getDetalleRentas() == null || cabecera.getDetalleRentas().size() == 0)
			throw new Exception("Debe seleccionar por lo menos un equipo");
		Producto producto = new Producto();
		int updateStock = 0;
		for (int i = 0; i < cabecera.getDetalleRentas().size(); i++) {
			producto = (Producto) mDAO.findById(Producto.class,
					cabecera.getDetalleRentas().get(i).getProducto().getIdProducto());
			updateStock = producto.getProCantidad() - (cabecera.getDetalleRentas().get(i).getDetRentaCantidad());
			producto.setProCantidad(updateStock);
			mDAO.actualizar(producto);
		}
		mDAO.insertar(cabecera);
	}
	
	public int obtenerIdNota() throws Exception {
		String id_ntr = "idNtr";
		return (mDAO.obtenerValorMax(NotaRenta.class, id_ntr)+1);
	}
	
	public int obtenerIdNotaReporte() throws Exception {
		String id_ntr = "idNtr";
		return (mDAO.obtenerValorMax(NotaRenta.class, id_ntr));
	}

	public List<Producto> findAllProducto() {
		return mDAO.findWhere(Producto.class, "o.proDisponible=" + "true", null);
	}

	public List<NotaRenta> findAllRenta() {
		return mDAO.findAll(NotaRenta.class);
	}

	public List<NotaRenta> findByNotaRenta(int idRenta) throws Exception {
		return mDAO.findWhere(NotaRenta.class, "o.idNtr=" + "'" + idRenta + "'", null);
	}

	public List<DetalleRenta> findDetalleRenta(int idRenta) throws Exception {
		return mDAO.findWhere(DetalleRenta.class, "o.notaRenta=" + "'" + idRenta + "'", null);
	}

	public List<Cliente> findAllCliente() {
		return mDAO.findWhere(Cliente.class, "o.cliLog=" + "true", null);
	}

	public List<NotaRenta> findNotaByFecha(String fecha1, String fecha2) {
		return mDAO.findWhere(NotaRenta.class,
				"o.ntrFechaActual>=" + "'" + fecha1 + "'" + " AND " + "o.ntrFechaActual<=" + "'" + fecha2 + "'", null);
	}
}
