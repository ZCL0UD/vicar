package vicar.controller.renta;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.DriverManager;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;

import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import vicar.controller.JSFUtil;
import vicar.controller.seguridades.BeanSegLogin;
import vicar.model.core.entities.Compra;
import vicar.model.core.entities.DetalleCompra;
import vicar.model.core.entities.Producto;
import vicar.model.core.entities.Proveedore;
import vicar.model.renta.managers.ManagerCompras;

@Named
@SessionScoped
public class BeanCompra implements Serializable {

	@EJB
	private ManagerCompras mCompra;

	private String ruc;
	private List<Compra> listaCompras;
	private List<DetalleCompra> listaDetalles;
	private int idCompra;
	private Date fecha1;
	private Date fecha2;
	private List<Proveedore> listaProveedores;
	private Compra cabecera;
	private int idProductoSeleccionado;
	private int cantidadSeleccionada;
	private List<Producto> listaProductos;
	@Inject
	private BeanSegLogin beanSegLogin;

	public BeanCompra() {

	}

	@PostConstruct
	public void inicializar() {
		listaCompras = mCompra.findAllCompra();
		listaProductos = mCompra.findAllProducto();
		listaProveedores = mCompra.findAllProveedores();
		cantidadSeleccionada = 1;
		ruc = "";
		idProductoSeleccionado = 1;
	}

	// KARELIS
	public void actionConsultarCompra() {
		try {
			// AQUI DEBEMOS HACER LA PRIMERA PARTE
			listaCompras = mCompra.findByNotaCompra(idCompra);
			JSFUtil.crearMensajeINFO("Nota de renta consultada");
		} catch (Exception e) {
			JSFUtil.crearMensajeERROR(e.getMessage());
			e.printStackTrace();
		}
	}

	public void actionConsultarTodaCompra() {
		try {
			// AQUI DEBEMOS HACER LA PRIMERA PARTE
			listaCompras = mCompra.findAllCompra();
			JSFUtil.crearMensajeINFO("Consultar todo");
		} catch (Exception e) {
			JSFUtil.crearMensajeERROR(e.getMessage());
			e.printStackTrace();
		}
	}

	public String actionMostrarNotaCompra(int idCompra) {
		try {
			// AQUI DEBEMOS HACER LA PRIMERA PARTE
			listaCompras = mCompra.findByNotaCompra(idCompra);
			listaDetalles = mCompra.findDetalleCompra(idCompra);
			JSFUtil.crearMensajeINFO("Go");
			return "compra";
		} catch (Exception e) {
			JSFUtil.crearMensajeERROR(e.getMessage());
			e.printStackTrace();
			return "";
		}
	}
    //------------------------------------------------------------------------------------------------------
	// CHARLIE

	// Insertar Compra
	public void actionListenerAdicionarProducto() throws Exception {
		String add = "ADD";
		if (cabecera == null) {
			cabecera = mCompra.adicionarProducto(cabecera, ruc, idProductoSeleccionado, cantidadSeleccionada,
					beanSegLogin.getIdSegUsuario());
		} else if (cabecera != null) {
			for (int i = 0; i < cabecera.getDetalleCompras().size(); i++) {
				if ((int) cabecera.getDetalleCompras().get(i).getProducto().getIdProducto() == idProductoSeleccionado) {
					JSFUtil.crearMensajeWARN("Producto ya seleccionado");
					add = "no";
					break;
				}
			}
			if (add.equals("ADD")) {
				cabecera = mCompra.adicionarProducto(cabecera, ruc, idProductoSeleccionado, cantidadSeleccionada,
						beanSegLogin.getIdSegUsuario());
			}
		}
	}

	public void actionListenerQuitarProducto(int idProducto) throws Exception {
		int eliminar = 0;
		if (cabecera == null) {
			JSFUtil.crearMensajeWARN("No hay equipos que eliminar");
		} else if (cabecera != null) {
			for (int i = 0; i < cabecera.getDetalleCompras().size(); i++) {
				if ((int) cabecera.getDetalleCompras().get(i).getProducto().getIdProducto() == idProducto) {
					cabecera = mCompra.eliminarProducto(cabecera, i);
					JSFUtil.crearMensajeWARN("Producto quitado de la lista");
					break;
				}
			}
		}
	}

	public String actionRegistrarCompra() {
		try {
			// AQUI DEBEMOS HACER LA PRIMERA PARTE
			mCompra.registrarCompra(cabecera);
			cabecera = null;
			ruc = "";
			JSFUtil.crearMensajeINFO("Nota de compra creada");
			return "menu";
		} catch (Exception e) {
			JSFUtil.crearMensajeERROR(e.getMessage());
			e.printStackTrace();
			return "";
		}
	}
	
	public String actionReporte(int id_ntr) {
		Map<String, Object> parametros = new HashMap<String, Object>();
		parametros.put("idCompra", id_ntr);
		/*
		 * parametros.put("p_titulo_principal",p_titulo_principal);
		 * parametros.put("p_titulo",p_titulo);
		 */
		FacesContext context = FacesContext.getCurrentInstance();
		ServletContext servletContext = (ServletContext) context.getExternalContext().getContext();
		String ruta = servletContext.getRealPath("renta/compras/reporteNotasCompras.jasper");
		System.out.println(ruta);
		HttpServletResponse response = (HttpServletResponse) context.getExternalContext().getResponse();
		response.addHeader("Content-disposition", "attachment;filename=reporte.pdf");
		response.setContentType("application/pdf");
		try {
			Class.forName("org.postgresql.Driver");
			Connection connection = null;
			connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/renta", "postgres",
					"1721486007");
			JasperPrint impresion = JasperFillManager.fillReport(ruta, parametros, connection);
			JasperExportManager.exportReportToPdfStream(impresion, response.getOutputStream());
			context.getApplication().getStateManager().saveView(context);
			System.out.println("reporte generado.");
			context.responseComplete();
			System.out.println("reporte generado."+id_ntr);
		} catch (Exception e) {
			JSFUtil.crearMensajeERROR(e.getMessage());
			e.printStackTrace();
		}
		return "";
	}
	
	public void actionConsultarCompraFecha() {
		try {
			// AQUI DEBEMOS HACER LA PRIMERA PARTE
			listaCompras = mCompra.findCompraByFecha(new SimpleDateFormat("yyyy-MM-dd").format(fecha1),
					new SimpleDateFormat("yyyy-MM-dd").format(fecha2));
			JSFUtil.crearMensajeINFO("Nota de renta consultada");
		} catch (Exception e) {
			JSFUtil.crearMensajeERROR(e.getMessage());
			e.printStackTrace();
		}
	}

	public String getRuc() {
		return ruc;
	}

	public void setRuc(String ruc) {
		this.ruc = ruc;
	}

	public List<Compra> getListaCompras() {
		return listaCompras;
	}

	public void setListaCompras(List<Compra> listaCompras) {
		this.listaCompras = listaCompras;
	}

	public List<DetalleCompra> getListaDetalles() {
		return listaDetalles;
	}

	public void setListaDetalles(List<DetalleCompra> listaDetalles) {
		this.listaDetalles = listaDetalles;
	}

	public int getIdCompra() {
		return idCompra;
	}

	public void setIdCompra(int idCompra) {
		this.idCompra = idCompra;
	}

	public Compra getCabecera() {
		return cabecera;
	}

	public void setCabecera(Compra cabecera) {
		this.cabecera = cabecera;
	}

	public int getIdProductoSeleccionado() {
		return idProductoSeleccionado;
	}

	public void setIdProductoSeleccionado(int idProductoSeleccionado) {
		this.idProductoSeleccionado = idProductoSeleccionado;
	}

	public int getCantidadSeleccionada() {
		return cantidadSeleccionada;
	}

	public void setCantidadSeleccionada(int cantidadSeleccionada) {
		this.cantidadSeleccionada = cantidadSeleccionada;
	}

	public List<Producto> getListaProductos() {
		return listaProductos;
	}

	public void setListaProductos(List<Producto> listaProductos) {
		this.listaProductos = listaProductos;
	}

	public BeanSegLogin getBeanSegLogin() {
		return beanSegLogin;
	}

	public void setBeanSegLogin(BeanSegLogin beanSegLogin) {
		this.beanSegLogin = beanSegLogin;
	}

	public Date getFecha1() {
		return fecha1;
	}

	public void setFecha1(Date fecha1) {
		this.fecha1 = fecha1;
	}

	public Date getFecha2() {
		return fecha2;
	}

	public void setFecha2(Date fecha2) {
		this.fecha2 = fecha2;
	}

	public List<Proveedore> getListaProveedores() {
		return listaProveedores;
	}

	public void setListaProveedores(List<Proveedore> listaProveedores) {
		this.listaProveedores = listaProveedores;
	}
	
}
