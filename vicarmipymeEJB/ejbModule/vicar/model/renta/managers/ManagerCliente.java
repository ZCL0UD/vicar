package vicar.model.renta.managers;
   
import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB; 
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import vicar.model.core.entities.Cliente;
import vicar.model.core.managers.ManagerDAO;
import vicar.model.core.utils.ModelUtil;


/**
 * Session Bean implementation class ManagerRenta
 */
@Stateless
@LocalBean
public class ManagerCliente {

	@EJB
	private ManagerDAO mDAO;
    /**
     * Default constructor. 
     */
    public ManagerCliente() {
        // TODO Auto-generated constructor stub
    }
    
    //Metodos de Vendedor
    
    public void insertarRegistro(Cliente clientes) throws Exception {
    	clientes.setCliLog(true);
    	mDAO.insertar(clientes);
    }
    
    
    public List<Cliente> findAllCliente(){
    	return mDAO.findWhere(Cliente.class, "o.cliLog="+"true",null);
    }
    
    public List<Cliente> findByCedula(String cedula){
    	return mDAO.findWhere(Cliente.class, "o.cliCedula="+"'"+cedula+"'", null);
    }
    
    public void updateRegistroCliente(Cliente clientes) throws Exception {
    	mDAO.actualizar(clientes);
    }
    
    public void deleteRegistroClientelogico(Cliente clientes) throws Exception {
    	clientes.setCliLog(false);
    	mDAO.actualizar(clientes);
    }
    
	/*public List<Cliente> findAllRegistro() {
		return em.createNamedQuery("Cliente.findAll", Cliente.class).getResultList();

	}*/
    
}
