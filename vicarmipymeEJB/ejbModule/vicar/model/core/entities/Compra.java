package vicar.model.core.entities;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the compra database table.
 * 
 */
@Entity
@Table(name="compra")
@NamedQuery(name="Compra.findAll", query="SELECT c FROM Compra c")
public class Compra implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_compra", unique=true, nullable=false)
	private Integer idCompra;

	@Temporal(TemporalType.DATE)
	@Column(name="comp_fecha")
	private Date compFecha;

	@Column(name="comp_total", precision=7, scale=2)
	private BigDecimal compTotal;

	//bi-directional many-to-one association to Proveedore
	@ManyToOne
	@JoinColumn(name="id_prov", nullable=false)
	private Proveedore proveedore;

	//bi-directional many-to-one association to SegUsuario
	@ManyToOne
	@JoinColumn(name="id_seg_usuario", nullable=false)
	private SegUsuario segUsuario;

	//bi-directional many-to-one association to DetalleCompra
	@OneToMany(mappedBy="compra")
	private List<DetalleCompra> detalleCompras;

	public Compra() {
	}

	public Integer getIdCompra() {
		return this.idCompra;
	}

	public void setIdCompra(Integer idCompra) {
		this.idCompra = idCompra;
	}

	public Date getCompFecha() {
		return this.compFecha;
	}

	public void setCompFecha(Date compFecha) {
		this.compFecha = compFecha;
	}

	public BigDecimal getCompTotal() {
		return this.compTotal;
	}

	public void setCompTotal(BigDecimal compTotal) {
		this.compTotal = compTotal;
	}

	public Proveedore getProveedore() {
		return this.proveedore;
	}

	public void setProveedore(Proveedore proveedore) {
		this.proveedore = proveedore;
	}

	public SegUsuario getSegUsuario() {
		return this.segUsuario;
	}

	public void setSegUsuario(SegUsuario segUsuario) {
		this.segUsuario = segUsuario;
	}

	public List<DetalleCompra> getDetalleCompras() {
		return this.detalleCompras;
	}

	public void setDetalleCompras(List<DetalleCompra> detalleCompras) {
		this.detalleCompras = detalleCompras;
	}

	public DetalleCompra addDetalleCompra(DetalleCompra detalleCompra) {
		getDetalleCompras().add(detalleCompra);
		detalleCompra.setCompra(this);

		return detalleCompra;
	}

	public DetalleCompra removeDetalleCompra(DetalleCompra detalleCompra) {
		getDetalleCompras().remove(detalleCompra);
		detalleCompra.setCompra(null);

		return detalleCompra;
	}

}