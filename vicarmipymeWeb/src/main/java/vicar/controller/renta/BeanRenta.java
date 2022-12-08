package vicar.controller.renta;

import java.io.Serializable;  
import java.sql.Connection;
import java.sql.DriverManager;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;

import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import vicar.controller.JSFUtil;
import vicar.model.core.entities.Cliente;
import vicar.model.core.entities.DetalleRenta;
import vicar.model.core.entities.NotaRenta;
import vicar.model.core.entities.Producto;
import vicar.model.renta.managers.ManagerRenta;

@Named
@SessionScoped
public class BeanRenta implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@EJB
	ManagerRenta mRenta;
	private Cliente clienteNuevo;
	private List<Cliente> listaClientes;
	private NotaRenta cabecera;
	private String cedula;
	private int idProductoSeleccionado;
	private int cantidadSeleccionada;
	private List<Producto> listaProductos;
	private List<NotaRenta> listaNotaRenta;
	private int diasAlquiler;
	private int idRenta;
	private List<DetalleRenta> listaDetalles;
	private Date fecha1;
	private Date fecha2;
	private String fechaA;
	private int id_ntr;
	private int id_ntr2;

	public BeanRenta() {
		// TODO Auto-generated constructor stub
	}

	@PostConstruct
	public void inicializar() {
		clienteNuevo = new Cliente();
		listaProductos = mRenta.findAllProducto();
		cantidadSeleccionada = 1;
		diasAlquiler = 1;
		idRenta = 1;
		listaNotaRenta = mRenta.findAllRenta();
		listaClientes = mRenta.findAllCliente();
		fechaA = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
	}

	public void actionListenerAdicionarProducto() throws Exception {
		String add = "ADD";
		if (cabecera == null) {
			cabecera = mRenta.adicionarProducto(cabecera, cedula, idProductoSeleccionado, cantidadSeleccionada,
					diasAlquiler);
		} else if (cabecera != null) {
			for (int i = 0; i < cabecera.getDetalleRentas().size(); i++) {
				if ((int) cabecera.getDetalleRentas().get(i).getProducto().getIdProducto() == idProductoSeleccionado) {
					JSFUtil.crearMensajeWARN("Equipo ya seleccionado");
					add = "no";
					break;
				}
			}
			if (add.equals("ADD")) {
				cabecera = mRenta.adicionarProducto(cabecera, cedula, idProductoSeleccionado, cantidadSeleccionada,
						diasAlquiler);
			}
		}
	}

	public void actionListenerQuitarProducto(int idProducto) throws Exception {
		int eliminar = 0;
		if (cabecera == null) {
			JSFUtil.crearMensajeWARN("No hay equipos que eliminar");
		} else if (cabecera != null) {
			for (int i = 0; i < cabecera.getDetalleRentas().size(); i++) {
				if ((int) cabecera.getDetalleRentas().get(i).getProducto().getIdProducto() == idProducto) {
					cabecera = mRenta.eliminarProducto(cabecera, i, diasAlquiler);
					JSFUtil.crearMensajeWARN("Producto quitado de la lista");
					break;
				}
			}
		}
	}

	public String actionRegistrarRenta() {
		try {
			// AQUI DEBEMOS HACER LA PRIMERA PARTE
			mRenta.registrarRenta(cabecera);
			JSFUtil.crearMensajeINFO("Nota de renta creada");
			listaNotaRenta = mRenta.findAllRenta();
			cantidadSeleccionada = 1;
			diasAlquiler = 1;
			cedula = "";
			cabecera = null;
			id_ntr2 = mRenta.obtenerIdNota()-1;
			return "";
		} catch (Exception e) {
			JSFUtil.crearMensajeERROR(e.getMessage());
			e.printStackTrace();
			return "";
		}
	}

	public void actionConsultarRenta() {
		try {
			// AQUI DEBEMOS HACER LA PRIMERA PARTE
			listaNotaRenta = mRenta.findByNotaRenta(idRenta);
			JSFUtil.crearMensajeINFO("Nota de renta consultada");
		} catch (Exception e) {
			JSFUtil.crearMensajeERROR(e.getMessage());
			e.printStackTrace();
		}
	}

	public void actionConsultarTodaRenta() {
		try {
			// AQUI DEBEMOS HACER LA PRIMERA PARTE
			listaNotaRenta = mRenta.findAllRenta();
			JSFUtil.crearMensajeINFO("Consultar todo");
		} catch (Exception e) {
			JSFUtil.crearMensajeERROR(e.getMessage());
			e.printStackTrace();
		}
	}

	public String actionMostrarNotaRenta(int idRenta) {
		try {
			// AQUI DEBEMOS HACER LA PRIMERA PARTE
			listaNotaRenta = mRenta.findByNotaRenta(idRenta);
			listaDetalles = mRenta.findDetalleRenta(idRenta);
			JSFUtil.crearMensajeINFO("Go");
			return "notaRenta";
		} catch (Exception e) {
			JSFUtil.crearMensajeERROR(e.getMessage());
			e.printStackTrace();
			return "";
		}
	}

	public String actionReporte() {
		Map<String, Object> parametros = new HashMap<String, Object>();
		/*
		 * parametros.put("p_titulo_principal",p_titulo_principal);
		 * parametros.put("p_titulo",p_titulo);
		 */
		FacesContext context = FacesContext.getCurrentInstance();
		ServletContext servletContext = (ServletContext) context.getExternalContext().getContext();
		String ruta = servletContext.getRealPath("renta/renta/report.jasper");
		System.out.println(ruta);
		HttpServletResponse response = (HttpServletResponse) context.getExternalContext().getResponse();
		response.addHeader("Content-disposition", "attachment;filename=reporte.pdf");
		response.setContentType("application/pdf");
		try {
			Class.forName("org.postgresql.Driver");
			Connection connection = null;
			connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/respaldo", "postgres",
					"1721486007");
			JasperPrint impresion = JasperFillManager.fillReport(ruta, parametros, connection);
			JasperExportManager.exportReportToPdfStream(impresion, response.getOutputStream());
			context.getApplication().getStateManager().saveView(context);
			System.out.println("reporte generado.");
			context.responseComplete();
		} catch (Exception e) {
			JSFUtil.crearMensajeERROR(e.getMessage());
			e.printStackTrace();
		}
		return "";
	}
	
	public String actionReporte(int id_ntr) {
		Map<String, Object> parametros = new HashMap<String, Object>();
		parametros.put("idNota", id_ntr);
		/*
		 * parametros.put("p_titulo_principal",p_titulo_principal);
		 * parametros.put("p_titulo",p_titulo);
		 */
		FacesContext context = FacesContext.getCurrentInstance();
		ServletContext servletContext = (ServletContext) context.getExternalContext().getContext();
		String ruta = servletContext.getRealPath("renta/renta/reporteNew.jasper");
		System.out.println(ruta);
		HttpServletResponse response = (HttpServletResponse) context.getExternalContext().getResponse();
		response.addHeader("Content-disposition", "attachment;filename=reporte.pdf");
		response.setContentType("application/pdf");
		try {
			Class.forName("org.postgresql.Driver");
			Connection connection = null;
			connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/respaldo", "postgres",
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
	
	public String actionCancelar() {
		cantidadSeleccionada = 1;
		diasAlquiler = 1;
		cedula = "";
		cabecera = null;
		return "menu";
	}
	
	public String actionRegresar() {
		listaNotaRenta = mRenta.findAllRenta();
		JSFUtil.crearMensajeINFO("Consultar todo");
		return "menu";
	}
	
	public void actionConsultarRentaFecha() {
		try {
			// AQUI DEBEMOS HACER LA PRIMERA PARTE
			listaNotaRenta = mRenta.findNotaByFecha(new SimpleDateFormat("yyyy-MM-dd").format(fecha1),
					new SimpleDateFormat("yyyy-MM-dd").format(fecha2));
			JSFUtil.crearMensajeINFO("Nota de renta consultada");
		} catch (Exception e) {
			JSFUtil.crearMensajeERROR(e.getMessage());
			e.printStackTrace();
		}
	}
	
	public String obtenerID() throws Exception {
		id_ntr = mRenta.obtenerIdNota();
		id_ntr2 = mRenta.obtenerIdNota()-1;
		listaProductos = mRenta.findAllProducto();
		return "maestro";
	}
	
	public Cliente getClienteNuevo() {
		return clienteNuevo;
	}

	public void setClienteNuevo(Cliente clienteNuevo) {
		this.clienteNuevo = clienteNuevo;
	}

	public NotaRenta getCabecera() {
		return cabecera;
	}

	public void setCabecera(NotaRenta cabecera) {
		this.cabecera = cabecera;
	}

	public String getCedula() {
		return cedula;
	}

	public void setCedula(String cedula) {
		this.cedula = cedula;
	}

	public int getIdProductoSeleccionado() {
		return idProductoSeleccionado;
	}

	public void setIdProductoSeleccionado(int idProductoSeleccionado) {
		this.idProductoSeleccionado = idProductoSeleccionado;
	}

	public List<Producto> getListaProductos() {
		return listaProductos;
	}

	public void setListaProductos(List<Producto> listaProductos) {
		this.listaProductos = listaProductos;
	}

	public int getCantidadSeleccionada() {
		return cantidadSeleccionada;
	}

	public void setCantidadSeleccionada(int cantidadSeleccionada) {
		this.cantidadSeleccionada = cantidadSeleccionada;
	}

	public int getDiasAlquiler() {
		return diasAlquiler;
	}

	public void setDiasAlquiler(int diasAlquiler) {
		this.diasAlquiler = diasAlquiler;
	}

	public List<NotaRenta> getListaNotaRenta() {
		return listaNotaRenta;
	}

	public void setListaNotaRenta(List<NotaRenta> listaNotaRenta) {
		this.listaNotaRenta = listaNotaRenta;
	}

	public int getIdRenta() {
		return idRenta;
	}

	public void setIdRenta(int idRenta) {
		this.idRenta = idRenta;
	}

	public List<DetalleRenta> getListaDetalles() {
		return listaDetalles;
	}

	public void setListaDetalles(List<DetalleRenta> listaDetalles) {
		this.listaDetalles = listaDetalles;
	}

	public List<Cliente> getListaClientes() {
		return listaClientes;
	}

	public void setListaClientes(List<Cliente> listaClientes) {
		this.listaClientes = listaClientes;
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

	public int getId_ntr() {
		return id_ntr;
	}

	public void setId_ntr(int id_ntr) {
		this.id_ntr = id_ntr;
	}

	public String getFechaA() {
		return fechaA;
	}

	public void setFechaA(String fechaA) {
		this.fechaA = fechaA;
	}

	public int getId_ntr2() {
		return id_ntr2;
	}

	public void setId_ntr2(int id_ntr2) {
		this.id_ntr2 = id_ntr2;
	}	
	
}
