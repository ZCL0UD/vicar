package vicar.model.core.entities;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the cliente database table.
 * 
 */
@Entity
@Table(name="cliente")
@NamedQuery(name="Cliente.findAll", query="SELECT c FROM Cliente c")
public class Cliente implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_cli", unique=true, nullable=false)
	private Integer idCli;

	@Column(name="cli_cedula", nullable=false, length=10)
	private String cliCedula;

	@Column(name="cli_correo", length=50)
	private String cliCorreo;

	@Column(name="cli_direccion", length=50)
	private String cliDireccion;

	@Column(name="cli_log")
	private Boolean cliLog;

	@Column(name="cli_nombre", length=60)
	private String cliNombre;

	@Column(name="cli_telefono", length=10)
	private String cliTelefono;

	//bi-directional many-to-one association to NotaRenta
	@OneToMany(mappedBy="cliente")
	private List<NotaRenta> notaRentas;

	public Cliente() {
	}

	public Integer getIdCli() {
		return this.idCli;
	}

	public void setIdCli(Integer idCli) {
		this.idCli = idCli;
	}

	public String getCliCedula() {
		return this.cliCedula;
	}

	public void setCliCedula(String cliCedula) {
		this.cliCedula = cliCedula;
	}

	public String getCliCorreo() {
		return this.cliCorreo;
	}

	public void setCliCorreo(String cliCorreo) {
		this.cliCorreo = cliCorreo;
	}

	public String getCliDireccion() {
		return this.cliDireccion;
	}

	public void setCliDireccion(String cliDireccion) {
		this.cliDireccion = cliDireccion;
	}

	public Boolean getCliLog() {
		return this.cliLog;
	}

	public void setCliLog(Boolean cliLog) {
		this.cliLog = cliLog;
	}

	public String getCliNombre() {
		return this.cliNombre;
	}

	public void setCliNombre(String cliNombre) {
		this.cliNombre = cliNombre;
	}

	public String getCliTelefono() {
		return this.cliTelefono;
	}

	public void setCliTelefono(String cliTelefono) {
		this.cliTelefono = cliTelefono;
	}

	public List<NotaRenta> getNotaRentas() {
		return this.notaRentas;
	}

	public void setNotaRentas(List<NotaRenta> notaRentas) {
		this.notaRentas = notaRentas;
	}

	public NotaRenta addNotaRenta(NotaRenta notaRenta) {
		getNotaRentas().add(notaRenta);
		notaRenta.setCliente(this);

		return notaRenta;
	}

	public NotaRenta removeNotaRenta(NotaRenta notaRenta) {
		getNotaRentas().remove(notaRenta);
		notaRenta.setCliente(null);

		return notaRenta;
	}

}