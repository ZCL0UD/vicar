package vicar.api.rest.auditoria;

import java.util.Date;   
import java.util.List;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import vicar.model.auditoria.managers.ManagerAuditoria;
import vicar.model.core.entities.AudBitacora;
import vicar.model.core.utils.ModelUtil;

@RequestScoped
@Path("auditoria")
@Produces("application/json")
@Consumes("application/json")
public class ServiceRESTAuditoria {
	@EJB
	private ManagerAuditoria mAuditoria;
	
	//ruta completa es:
	// /apirest/auditoria/bitacora
	@GET
	@Path(value = "bitacora")
	public List<AudBitacora> findBitacoraAyer(){
		return mAuditoria.findBitacoraByFecha(ModelUtil.addDays(new Date(), -1), new Date());
	}
}
