package vicar.controller.seguridades;

import java.io.Serializable;  
import java.util.List;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.context.SessionScoped;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

import vicar.model.core.entities.SegAsignacion;
import vicar.model.core.entities.SegModulo;
import vicar.model.core.entities.SegUsuario;
import vicar.model.seguridades.managers.ManagerSeguridades;
import vicar.controller.JSFUtil;

@Named
@SessionScoped
public class BeanSegAsignaciones implements Serializable {
	private static final long serialVersionUID = 1L;

	@EJB
	private ManagerSeguridades managerSeguridades;
	
	private List<SegUsuario> listaUsuarios;
	private List<SegModulo> listaModulos;
	private int idSegUsuarioSeleccionado;
	private List<SegAsignacion> listaAsignaciones;
	
	
	public BeanSegAsignaciones() {
		
	}
	
	public String actionMenuAsignaciones() {
		listaUsuarios=managerSeguridades.findAllUsuarios();
		listaModulos=managerSeguridades.findAllModulos();
		return "asignaciones";
	}
	
	public void actionListenerSeleccionarUsuario() {
		listaAsignaciones=managerSeguridades.findAsignacionByUsuario(idSegUsuarioSeleccionado);
	}
	
	public void actionListenerAsignarPerfil(int idSegPerfil) {
		try {
			managerSeguridades.asignarPerfil(idSegUsuarioSeleccionado, idSegPerfil);
			listaAsignaciones=managerSeguridades.findAsignacionByUsuario(idSegUsuarioSeleccionado);
			JSFUtil.crearMensajeINFO("Perfil asignado.");
		} catch (Exception e) {
			JSFUtil.crearMensajeERROR(e.getMessage());
			e.printStackTrace();
		}
	}
	
	public void actionListenerEliminarAsignacionModulo(int idSegAsignacion) {
		try {
			managerSeguridades.eliminarAsignacion(idSegAsignacion);
			listaAsignaciones=managerSeguridades.findAsignacionByUsuario(idSegUsuarioSeleccionado);
			JSFUtil.crearMensajeINFO("Asignación eliminada.");
		} catch (Exception e) {
			JSFUtil.crearMensajeERROR(e.getMessage());
			e.printStackTrace();
		}
	}

	public List<SegUsuario> getListaUsuarios() {
		return listaUsuarios;
	}

	public void setListaUsuarios(List<SegUsuario> listaUsuarios) {
		this.listaUsuarios = listaUsuarios;
	}

	public List<SegModulo> getListaModulos() {
		return listaModulos;
	}

	public void setListaModulos(List<SegModulo> listaModulos) {
		this.listaModulos = listaModulos;
	}

	public int getIdSegUsuarioSeleccionado() {
		return idSegUsuarioSeleccionado;
	}

	public void setIdSegUsuarioSeleccionado(int idSegUsuarioSeleccionado) {
		this.idSegUsuarioSeleccionado = idSegUsuarioSeleccionado;
	}

	public List<SegAsignacion> getListaAsignaciones() {
		return listaAsignaciones;
	}

	public void setListaAsignaciones(List<SegAsignacion> listaAsignaciones) {
		this.listaAsignaciones = listaAsignaciones;
	}
	
	

}
