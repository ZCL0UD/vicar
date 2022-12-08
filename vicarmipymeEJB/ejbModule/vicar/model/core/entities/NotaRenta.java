package vicar.model.core.entities;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the nota_renta database table.
 * 
 */
@Entity
@Table(name="nota_renta")
@NamedQuery(name="NotaRenta.findAll", query="SELECT n FROM NotaRenta n")
public class NotaRenta implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_ntr", unique=true, nullable=false)
	private Integer idNtr;

	@Column(name="ntr_descripcion", length=2147483647)
	private String ntrDescripcion;

	@Temporal(TemporalType.DATE)
	@Column(name="ntr_fecha_actual")
	private Date ntrFechaActual;

	@Temporal(TemporalType.DATE)
	@Column(name="ntr_fecha_fin")
	private Date ntrFechaFin;

	@Temporal(TemporalType.DATE)
	@Column(name="ntr_fecha_inicio")
	private Date ntrFechaInicio;

	@Column(name="ntr_total", precision=7, scale=2)
	private BigDecimal ntrTotal;

	//bi-directional many-to-one association to DetalleRenta
	@OneToMany(mappedBy="notaRenta", cascade = CascadeType.ALL,fetch = FetchType.EAGER)
	private List<DetalleRenta> detalleRentas;

	//bi-directional many-to-one association to Cliente
	@ManyToOne
	@JoinColumn(name="id_cli", nullable=false)
	private Cliente cliente;

	//bi-directional many-to-one association to Almacenaje
	@OneToMany(mappedBy="notaRenta")
	private List<Almacenaje> almacenajes;

	public NotaRenta() {
	}

	public Integer getIdNtr() {
		return this.idNtr;
	}

	public void setIdNtr(Integer idNtr) {
		this.idNtr = idNtr;
	}

	public String getNtrDescripcion() {
		return this.ntrDescripcion;
	}

	public void setNtrDescripcion(String ntrDescripcion) {
		this.ntrDescripcion = ntrDescripcion;
	}

	public Date getNtrFechaActual() {
		return this.ntrFechaActual;
	}

	public void setNtrFechaActual(Date ntrFechaActual) {
		this.ntrFechaActual = ntrFechaActual;
	}

	public Date getNtrFechaFin() {
		return this.ntrFechaFin;
	}

	public void setNtrFechaFin(Date ntrFechaFin) {
		this.ntrFechaFin = ntrFechaFin;
	}

	public Date getNtrFechaInicio() {
		return this.ntrFechaInicio;
	}

	public void setNtrFechaInicio(Date ntrFechaInicio) {
		this.ntrFechaInicio = ntrFechaInicio;
	}

	public BigDecimal getNtrTotal() {
		return this.ntrTotal;
	}

	public void setNtrTotal(BigDecimal ntrTotal) {
		this.ntrTotal = ntrTotal;
	}

	public List<DetalleRenta> getDetalleRentas() {
		return this.detalleRentas;
	}

	public void setDetalleRentas(List<DetalleRenta> detalleRentas) {
		this.detalleRentas = detalleRentas;
	}

	public DetalleRenta addDetalleRenta(DetalleRenta detalleRenta) {
		getDetalleRentas().add(detalleRenta);
		detalleRenta.setNotaRenta(this);

		return detalleRenta;
	}

	public DetalleRenta removeDetalleRenta(DetalleRenta detalleRenta) {
		getDetalleRentas().remove(detalleRenta);
		detalleRenta.setNotaRenta(null);

		return detalleRenta;
	}

	public Cliente getCliente() {
		return this.cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public List<Almacenaje> getAlmacenajes() {
		return this.almacenajes;
	}

	public void setAlmacenajes(List<Almacenaje> almacenajes) {
		this.almacenajes = almacenajes;
	}

	public Almacenaje addAlmacenaje(Almacenaje almacenaje) {
		getAlmacenajes().add(almacenaje);
		almacenaje.setNotaRenta(this);

		return almacenaje;
	}

	public Almacenaje removeAlmacenaje(Almacenaje almacenaje) {
		getAlmacenajes().remove(almacenaje);
		almacenaje.setNotaRenta(null);

		return almacenaje;
	}

}