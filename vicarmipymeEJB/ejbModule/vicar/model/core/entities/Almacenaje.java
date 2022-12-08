package vicar.model.core.entities;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;


/**
 * The persistent class for the almacenaje database table.
 * 
 */
@Entity
@Table(name="almacenaje")
@NamedQuery(name="Almacenaje.findAll", query="SELECT a FROM Almacenaje a")
public class Almacenaje implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_almacenaje", unique=true, nullable=false)
	private Integer idAlmacenaje;

	@Temporal(TemporalType.DATE)
	@Column(name="alm_fecha")
	private Date almFecha;

	@Column(name="alm_multa", precision=7, scale=2)
	private BigDecimal almMulta;

	@Column(name="alm_obervacion", length=2147483647)
	private String almObervacion;

	@Column(name="id_cli", nullable=false)
	private Integer idCli;

	//bi-directional many-to-one association to NotaRenta
	@ManyToOne
	@JoinColumn(name="id_ntr", nullable=false)
	private NotaRenta notaRenta;

	public Almacenaje() {
	}

	public Integer getIdAlmacenaje() {
		return this.idAlmacenaje;
	}

	public void setIdAlmacenaje(Integer idAlmacenaje) {
		this.idAlmacenaje = idAlmacenaje;
	}

	public Date getAlmFecha() {
		return this.almFecha;
	}

	public void setAlmFecha(Date almFecha) {
		this.almFecha = almFecha;
	}

	public BigDecimal getAlmMulta() {
		return this.almMulta;
	}

	public void setAlmMulta(BigDecimal almMulta) {
		this.almMulta = almMulta;
	}

	public String getAlmObervacion() {
		return this.almObervacion;
	}

	public void setAlmObervacion(String almObervacion) {
		this.almObervacion = almObervacion;
	}

	public Integer getIdCli() {
		return this.idCli;
	}

	public void setIdCli(Integer idCli) {
		this.idCli = idCli;
	}

	public NotaRenta getNotaRenta() {
		return this.notaRenta;
	}

	public void setNotaRenta(NotaRenta notaRenta) {
		this.notaRenta = notaRenta;
	}

}