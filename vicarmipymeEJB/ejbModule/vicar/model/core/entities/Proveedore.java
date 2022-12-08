package vicar.model.core.entities;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the proveedores database table.
 * 
 */
@Entity
@Table(name="proveedores")
@NamedQuery(name="Proveedore.findAll", query="SELECT p FROM Proveedore p")
public class Proveedore implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_prov", unique=true, nullable=false)
	private Integer idProv;

	@Column(name="prov_correo", length=50)
	private String provCorreo;

	@Column(name="prov_direccion", length=50)
	private String provDireccion;

	@Column(name="prov_log")
	private Boolean provLog;

	@Column(name="prov_nombre", length=60)
	private String provNombre;

	@Column(name="prov_telefono", length=10)
	private String provTelefono;

	@Column(name="ruc_proveedor", nullable=false, length=13)
	private String rucProveedor;

	//bi-directional many-to-one association to Compra
	@OneToMany(mappedBy="proveedore")
	private List<Compra> compras;

	public Proveedore() {
	}

	public Integer getIdProv() {
		return this.idProv;
	}

	public void setIdProv(Integer idProv) {
		this.idProv = idProv;
	}

	public String getProvCorreo() {
		return this.provCorreo;
	}

	public void setProvCorreo(String provCorreo) {
		this.provCorreo = provCorreo;
	}

	public String getProvDireccion() {
		return this.provDireccion;
	}

	public void setProvDireccion(String provDireccion) {
		this.provDireccion = provDireccion;
	}

	public Boolean getProvLog() {
		return this.provLog;
	}

	public void setProvLog(Boolean provLog) {
		this.provLog = provLog;
	}

	public String getProvNombre() {
		return this.provNombre;
	}

	public void setProvNombre(String provNombre) {
		this.provNombre = provNombre;
	}

	public String getProvTelefono() {
		return this.provTelefono;
	}

	public void setProvTelefono(String provTelefono) {
		this.provTelefono = provTelefono;
	}

	public String getRucProveedor() {
		return this.rucProveedor;
	}

	public void setRucProveedor(String rucProveedor) {
		this.rucProveedor = rucProveedor;
	}

	public List<Compra> getCompras() {
		return this.compras;
	}

	public void setCompras(List<Compra> compras) {
		this.compras = compras;
	}

	public Compra addCompra(Compra compra) {
		getCompras().add(compra);
		compra.setProveedore(this);

		return compra;
	}

	public Compra removeCompra(Compra compra) {
		getCompras().remove(compra);
		compra.setProveedore(null);

		return compra;
	}

}