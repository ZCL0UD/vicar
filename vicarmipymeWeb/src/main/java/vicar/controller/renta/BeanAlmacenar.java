package vicar.controller.renta;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

import vicar.controller.JSFUtil;
import vicar.model.core.entities.Almacenaje;
import vicar.model.core.entities.DetalleRenta;
import vicar.model.core.entities.NotaRenta;
import vicar.model.renta.managers.ManagerRecepcion;

@Named
@SessionScoped
public class BeanAlmacenar implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@EJB
	ManagerRecepcion mRecepcion;

	private Almacenaje recepcionNueva;
	private NotaRenta renta;
	private List<DetalleRenta> detalleRenta;
	private List<Almacenaje> listaAlmacenajes;
	private int codigo;
	private String rendered;

	public BeanAlmacenar() {
		// TODO Auto-generated constructor stub
	}

	@PostConstruct
	public void inicializar() {
		recepcionNueva = new Almacenaje();
		codigo = 1;
		recepcionNueva.setAlmMulta(new BigDecimal(0));
	}

	public void actionConsultarRenta() {
		try {
			// AQUI DEBEMOS HACER LA PRIMERA PARTE
			if (mRecepcion.findWhereAlmcenaje(codigo).size()>=1) {
				rendered = "false";
				detalleRenta = new ArrayList<>();
				renta = new NotaRenta();
				JSFUtil.crearMensajeINFO("Renta ya almacenada");
			} else {
				rendered = "true";
				detalleRenta = mRecepcion.findDetalleRenta(codigo);
				renta = mRecepcion.findByIdNotaRenta(codigo);
				JSFUtil.crearMensajeINFO("Consulta realizada");
			}
		} catch (Exception e) {
			JSFUtil.crearMensajeERROR(e.getMessage());
			e.printStackTrace();
		}
	}

	public String actionRegistrarRecepcion() {
		try {
			// AQUI DEBEMOS HACER LA PRIMERA PARTE
			mRecepcion.registrarAlmacenaje(renta, recepcionNueva);
			JSFUtil.crearMensajeINFO("Recepción realizada");
			listaAlmacenajes = mRecepcion.findAllAlmacenaje();
			renta = new NotaRenta();
			codigo = 0;
			recepcionNueva = new Almacenaje();
			detalleRenta = new ArrayList<>();
			return "";
		} catch (Exception e) {
			JSFUtil.crearMensajeERROR(e.getMessage());
			e.printStackTrace();
			return "";
		}
	}

	public Almacenaje getRecepcionNueva() {
		return recepcionNueva;
	}

	public void setRecepcionNueva(Almacenaje recepcionNueva) {
		this.recepcionNueva = recepcionNueva;
	}

	public NotaRenta getRenta() {
		return renta;
	}

	public void setRenta(NotaRenta renta) {
		this.renta = renta;
	}

	public List<DetalleRenta> getDetalleRenta() {
		return detalleRenta;
	}

	public void setDetalleRenta(List<DetalleRenta> detalleRenta) {
		this.detalleRenta = detalleRenta;
	}

	public List<Almacenaje> getListaAlmacenajes() {
		return listaAlmacenajes;
	}

	public void setListaAlmacenajes(List<Almacenaje> listaAlmacenajes) {
		this.listaAlmacenajes = listaAlmacenajes;
	}

	public int getCodigo() {
		return codigo;
	}

	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}

	public String getRendered() {
		return rendered;
	}

	public void setRendered(String rendered) {
		this.rendered = rendered;
	}

}
