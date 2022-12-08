package vicar.model.core.entities;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the detalle_renta database table.
 * 
 */
@Entity
@Table(name="detalle_renta")
@NamedQuery(name="DetalleRenta.findAll", query="SELECT d FROM DetalleRenta d")
public class DetalleRenta implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_det_rent", unique=true, nullable=false)
	private Integer idDetRent;

	@Column(name="det_renta_cantidad")
	private Integer detRentaCantidad;

	//bi-directional many-to-one association to NotaRenta
	@ManyToOne
	@JoinColumn(name="id_ntr", nullable=false)
	private NotaRenta notaRenta;

	//bi-directional many-to-one association to Producto
	@ManyToOne
	@JoinColumn(name="id_producto", nullable=false)
	private Producto producto;

	public DetalleRenta() {
	}

	public Integer getIdDetRent() {
		return this.idDetRent;
	}

	public void setIdDetRent(Integer idDetRent) {
		this.idDetRent = idDetRent;
	}

	public Integer getDetRentaCantidad() {
		return this.detRentaCantidad;
	}

	public void setDetRentaCantidad(Integer detRentaCantidad) {
		this.detRentaCantidad = detRentaCantidad;
	}

	public NotaRenta getNotaRenta() {
		return this.notaRenta;
	}

	public void setNotaRenta(NotaRenta notaRenta) {
		this.notaRenta = notaRenta;
	}

	public Producto getProducto() {
		return this.producto;
	}

	public void setProducto(Producto producto) {
		this.producto = producto;
	}

}