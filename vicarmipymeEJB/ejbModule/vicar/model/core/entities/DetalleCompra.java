package vicar.model.core.entities;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the detalle_compra database table.
 * 
 */
@Entity
@Table(name="detalle_compra")
@NamedQuery(name="DetalleCompra.findAll", query="SELECT d FROM DetalleCompra d")
public class DetalleCompra implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_det_comp", unique=true, nullable=false)
	private Integer idDetComp;

	@Column(name="det_comp_cantidad")
	private Integer detCompCantidad;

	//bi-directional many-to-one association to Compra
	@ManyToOne
	@JoinColumn(name="id_compra", nullable=false)
	private Compra compra;

	//bi-directional many-to-one association to Producto
	@ManyToOne
	@JoinColumn(name="id_producto", nullable=false)
	private Producto producto;

	public DetalleCompra() {
	}

	public Integer getIdDetComp() {
		return this.idDetComp;
	}

	public void setIdDetComp(Integer idDetComp) {
		this.idDetComp = idDetComp;
	}

	public Integer getDetCompCantidad() {
		return this.detCompCantidad;
	}

	public void setDetCompCantidad(Integer detCompCantidad) {
		this.detCompCantidad = detCompCantidad;
	}

	public Compra getCompra() {
		return this.compra;
	}

	public void setCompra(Compra compra) {
		this.compra = compra;
	}

	public Producto getProducto() {
		return this.producto;
	}

	public void setProducto(Producto producto) {
		this.producto = producto;
	}

}