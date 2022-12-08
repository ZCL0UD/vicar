package vicar.controller.renta;

import java.io.Serializable;      
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

import vicar.controller.JSFUtil;
import vicar.model.core.entities.Producto;
import vicar.model.renta.managers.ManagerProductos;

@Named
@SessionScoped
public class BeanProductos implements Serializable {

	@EJB
	private ManagerProductos mProductos;
	private List<Producto> listaProducto;
	private Producto productoNuevo;
	private Producto edicionProducto;

	
	public BeanProductos() {
		
	}
	
	@PostConstruct
	public void inicializar() {
		listaProducto =mProductos.findAllProducto();
		JSFUtil.crearMensajeINFO("Cliente Registrado");
		productoNuevo=new Producto();
		edicionProducto=new Producto();
	}
	
	
	public void actionSeleccionarEdicionProducto(Producto productos) {
		edicionProducto=productos;
	}
	

	public String actionListenerInsertarProducto() {
		try {
			// AQUI DEBEMOS HACER LA PRIMERA PARTE
			mProductos.insertarProducto(productoNuevo);
			listaProducto =mProductos.findAllProducto();
			JSFUtil.crearMensajeINFO("Registrado");
			productoNuevo=new Producto();			
			return "menu";
		} catch (Exception e) {
			JSFUtil.crearMensajeERROR(e.getMessage());
			e.printStackTrace();
			return "";
		}
	}
	
	public void actualizarProducto() {
		try {
			mProductos.updateProducto(edicionProducto);
			JSFUtil.crearMensajeINFO("Producto actualizado.");
			listaProducto =mProductos.findAllProducto();
			edicionProducto= new Producto();
		} catch (Exception e) {
			JSFUtil.crearMensajeERROR(e.getMessage());
			e.printStackTrace();
		}
	}
	
	
	public void eliminarProductologico(Producto productos) throws Exception {
		try {
			mProductos.deleteProductologico(productos);
			JSFUtil.crearMensajeINFO("Producto Eliminado.");
			listaProducto =mProductos.findAllProducto();
		} catch (Exception e) {
			JSFUtil.crearMensajeERROR(e.getMessage());
			e.printStackTrace();
		}
	}

	public List<Producto> getListaProducto() {
		return listaProducto;
	}

	public void setListaProducto(List<Producto> listaProducto) {
		this.listaProducto = listaProducto;
	}

	public Producto getProductoNuevo() {
		return productoNuevo;
	}

	public void setProductoNuevo(Producto productoNuevo) {
		this.productoNuevo = productoNuevo;
	}

	public Producto getEdicionProducto() {
		return edicionProducto;
	}

	public void setEdicionProducto(Producto edicionProducto) {
		this.edicionProducto = edicionProducto;
	}
	
	
}
