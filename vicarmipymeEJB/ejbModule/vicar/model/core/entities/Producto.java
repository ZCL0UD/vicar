package vicar.model.core.entities;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;


/**
 * The persistent class for the productos database table.
 * 
 */
@Entity
@Table(name="productos")
@NamedQuery(name="Producto.findAll", query="SELECT p FROM Producto p")
public class Producto implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_producto", unique=true, nullable=false)
	private Integer idProducto;

	@Column(name="pro_cantidad")
	private Integer proCantidad;

	@Column(name="pro_disponible")
	private Boolean proDisponible;

	@Column(name="pro_nombre", nullable=false, length=60)
	private String proNombre;

	@Column(name="pro_precio_alquiler", precision=7, scale=2)
	private BigDecimal proPrecioAlquiler;

	@Column(name="pro_precio_equipo", precision=7, scale=2)
	private BigDecimal proPrecioEquipo;

	//bi-directional many-to-one association to DetalleCompra
	@OneToMany(mappedBy="producto")
	private List<DetalleCompra> detalleCompras;

	//bi-directional many-to-one association to DetalleRenta
	@OneToMany(mappedBy="producto")
	private List<DetalleRenta> detalleRentas;

	public Producto() {
	}

	public Integer getIdProducto() {
		return this.idProducto;
	}

	public void setIdProducto(Integer idProducto) {
		this.idProducto = idProducto;
	}

	public Integer getProCantidad() {
		return this.proCantidad;
	}

	public void setProCantidad(Integer proCantidad) {
		this.proCantidad = proCantidad;
	}

	public Boolean getProDisponible() {
		return this.proDisponible;
	}

	public void setProDisponible(Boolean proDisponible) {
		this.proDisponible = proDisponible;
	}

	public String getProNombre() {
		return this.proNombre;
	}

	public void setProNombre(String proNombre) {
		this.proNombre = proNombre;
	}

	public BigDecimal getProPrecioAlquiler() {
		return this.proPrecioAlquiler;
	}

	public void setProPrecioAlquiler(BigDecimal proPrecioAlquiler) {
		this.proPrecioAlquiler = proPrecioAlquiler;
	}

	public BigDecimal getProPrecioEquipo() {
		return this.proPrecioEquipo;
	}

	public void setProPrecioEquipo(BigDecimal proPrecioEquipo) {
		this.proPrecioEquipo = proPrecioEquipo;
	}

	public List<DetalleCompra> getDetalleCompras() {
		return this.detalleCompras;
	}

	public void setDetalleCompras(List<DetalleCompra> detalleCompras) {
		this.detalleCompras = detalleCompras;
	}

	public DetalleCompra addDetalleCompra(DetalleCompra detalleCompra) {
		getDetalleCompras().add(detalleCompra);
		detalleCompra.setProducto(this);

		return detalleCompra;
	}

	public DetalleCompra removeDetalleCompra(DetalleCompra detalleCompra) {
		getDetalleCompras().remove(detalleCompra);
		detalleCompra.setProducto(null);

		return detalleCompra;
	}

	public List<DetalleRenta> getDetalleRentas() {
		return this.detalleRentas;
	}

	public void setDetalleRentas(List<DetalleRenta> detalleRentas) {
		this.detalleRentas = detalleRentas;
	}

	public DetalleRenta addDetalleRenta(DetalleRenta detalleRenta) {
		getDetalleRentas().add(detalleRenta);
		detalleRenta.setProducto(this);

		return detalleRenta;
	}

	public DetalleRenta removeDetalleRenta(DetalleRenta detalleRenta) {
		getDetalleRentas().remove(detalleRenta);
		detalleRenta.setProducto(null);

		return detalleRenta;
	}

}