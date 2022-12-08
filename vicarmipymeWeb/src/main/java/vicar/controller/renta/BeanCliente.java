package vicar.controller.renta;

import java.io.Serializable;    
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

import vicar.controller.JSFUtil;
import vicar.model.core.entities.Cliente;
import vicar.model.renta.managers.ManagerCliente;

@Named
@SessionScoped
public class BeanCliente implements Serializable {

	@EJB
	private ManagerCliente mCliente;
	private Cliente clienteNuevo;
	private Cliente edicionCliente;
	private List<Cliente> listaCliente;
	private String cedulaCliente;

	public BeanCliente() {
		
	}
	
	@PostConstruct
	public void inicializar() {
		clienteNuevo=new Cliente();
		listaCliente = mCliente.findAllCliente();
	}
	
	
	public String actionListenerInsertarCliente() {
		try {
			// AQUI DEBEMOS HACER LA PRIMERA PARTE
			mCliente.insertarRegistro(clienteNuevo);
			listaCliente = mCliente.findAllCliente();
			JSFUtil.crearMensajeINFO("Cliente registrado");
			clienteNuevo=new Cliente();
			return "menu";
		} catch (Exception e) {
			JSFUtil.crearMensajeERROR(e.getMessage());
			e.printStackTrace();
			return "";
		}
	}
	
	public void actionFindClienteByCedula() throws Exception {
		listaCliente = mCliente.findByCedula(cedulaCliente);
		clienteNuevo.setCliCedula(cedulaCliente);
	}
	
	public void actionFindAllCliente() throws Exception {
		listaCliente = mCliente.findAllCliente();
	}
	
	public void actionSeleccionarEdicionCliente(Cliente clientes) {
		edicionCliente=clientes;
	}
	
	public void actualizarRegistroCliente() {
		try {
			mCliente.updateRegistroCliente(edicionCliente);
			JSFUtil.crearMensajeINFO("Cliente actualizado.");
			listaCliente = mCliente.findAllCliente();
			edicionCliente=new Cliente();
		} catch (Exception e) {
			JSFUtil.crearMensajeERROR(e.getMessage());
			e.printStackTrace();
		}
	}
	
	
	public void eliminarRegistroClientelogico(Cliente clientes) throws Exception {
		try {		
			mCliente.deleteRegistroClientelogico(clientes);
			JSFUtil.crearMensajeINFO("Cliente Eliminado.");
			listaCliente = mCliente.findAllCliente();
		} catch (Exception e) {
			JSFUtil.crearMensajeERROR(e.getMessage());
			e.printStackTrace();
		}
	}

	public Cliente getClienteNuevo() {
		return clienteNuevo;
	}

	public void setClienteNuevo(Cliente clienteNuevo) {
		this.clienteNuevo = clienteNuevo;
	}

	public Cliente getEdicionCliente() {
		return edicionCliente;
	}

	public void setEdicionCliente(Cliente edicionCliente) {
		this.edicionCliente = edicionCliente;
	}

	public List<Cliente> getListaCliente() {
		return listaCliente;
	}

	public void setListaCliente(List<Cliente> listaCliente) {
		this.listaCliente = listaCliente;
	}

	public String getCedulaCliente() {
		return cedulaCliente;
	}

	public void setCedulaCliente(String cedulaCliente) {
		this.cedulaCliente = cedulaCliente;
	}
	
}
