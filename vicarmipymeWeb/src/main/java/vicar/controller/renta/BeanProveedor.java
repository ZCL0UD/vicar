package vicar.controller.renta;

import java.io.Serializable;     
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

import vicar.controller.JSFUtil;
import vicar.model.core.entities.Proveedore;
import vicar.model.renta.managers.ManagerProveedor;

@Named
@SessionScoped
public class BeanProveedor implements Serializable {

	@EJB
	private ManagerProveedor mProveedor;

	private List<Proveedore> listaProveedores;
	private Proveedore proveedorNuevo;
	private Proveedore edicionProveedor;
	private String ruc;


	public BeanProveedor() {

	}

	@PostConstruct
	public void inicializar() {
		proveedorNuevo = new Proveedore();
		listaProveedores = mProveedor.findAllProveedore();
		ruc="";
	}

	// Insertar Proveedor

	public String actionListenerInsertarProveedor() {
		try {
			// AQUI DEBEMOS HACER LA PRIMERA PARTE
			mProveedor.insertarRegistroProveedor(proveedorNuevo);
			listaProveedores = mProveedor.findAllProveedore();
			JSFUtil.crearMensajeINFO("Registrado");
			proveedorNuevo = new Proveedore();
			return "menu";
		} catch (Exception e) {
			JSFUtil.crearMensajeERROR(e.getMessage());
			e.printStackTrace();
			return "";
		}
	}


	public void actionSeleccionarProveedor(Proveedore proveedor) {
		edicionProveedor = proveedor;
	}

	public void actionFindAllProveedores() {
		listaProveedores = mProveedor.findAllProveedore();
	}

	public void actionFindByRuc() {
		listaProveedores = mProveedor.findByRuc(ruc);
		if (listaProveedores.isEmpty()) {
			JSFUtil.crearMensajeINFO("Cliente sin registrar");
		}
	}

	public void actionUpdateProveedor() {
		try {
			mProveedor.updateProveedor(edicionProveedor);
			JSFUtil.crearMensajeINFO("Proveedor actualizado.");
		} catch (Exception e) {
			JSFUtil.crearMensajeERROR(e.getMessage());
			e.printStackTrace();
		}
	}

	public void eliminarProveedor(Proveedore proveedor) throws Exception {
		try {
			mProveedor.deleteProveedor(proveedor);
			listaProveedores = mProveedor.findAllProveedore();
			JSFUtil.crearMensajeINFO("Proveedor Eliminado.");
		} catch (Exception e) {
			JSFUtil.crearMensajeERROR(e.getMessage());
			e.printStackTrace();
		}
	}
	
	public List<Proveedore> getListaProveedores() {
		return listaProveedores;
	}

	public void setListaProveedores(List<Proveedore> listaProveedores) {
		this.listaProveedores = listaProveedores;
	}

	public Proveedore getProveedorNuevo() {
		return proveedorNuevo;
	}

	public void setProveedorNuevo(Proveedore proveedorNuevo) {
		this.proveedorNuevo = proveedorNuevo;
	}

	public Proveedore getEdicionProveedor() {
		return edicionProveedor;
	}

	public void setEdicionProveedor(Proveedore edicionProveedor) {
		this.edicionProveedor = edicionProveedor;
	}

	public String getRuc() {
		return ruc;
	}

	public void setRuc(String ruc) {
		this.ruc = ruc;
	}
	
	
}
