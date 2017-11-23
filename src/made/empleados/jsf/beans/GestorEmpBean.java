package made.empleados.jsf.beans;

import java.util.ArrayList;
import java.util.Collection;
import java.util.SortedSet;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.event.ActionEvent;
import javax.inject.Inject;

import made.empleados.daos.EmpDAO;
import made.empleados.daos.qualifiers.DAOType;
import made.empleados.daos.qualifiers.DAOTypeQ;
import made.empleados.ejbs.EmpleadosNegocioLocal;
import made.empleados.model.Empleado;

@ManagedBean
@RequestScoped
public class GestorEmpBean {

	private enum WorkingWith {
		DAO,
		SES_BEAN
	}
	
	private WorkingWith workingWith = WorkingWith.SES_BEAN;

	@Inject @DAOTypeQ(DAOType.DAO_JPA_LOCAL)
	private EmpDAO dao;

	/*
	 * A la hora de inyectar los ejbs, los mappedName se utilizarían si
	 * se trata de ejb's desplegados en otro espacio de memoria (por ejemplo,
	 * en un .jar aparte) y los name si está todo en un .ear (como es el caso)
	 */

//	@EJB(mappedName = "java:global/ejb_empleados/EmpleadosNegocioBean!made.empleados.ejbs.EmpleadosNegocioRemote")
	@EJB(name="empleadosNegocioEJB")
	private EmpleadosNegocioLocal negocio;
	
	private Empleado emp = new Empleado();

	public void setDao(EmpDAO dao) {
		this.dao = dao;
	}

	public void setNegocio(EmpleadosNegocioLocal negocio) {
		this.negocio = negocio;
	}

	public String getCif() {
		return emp.getCif();
	}

	public void setCif(String cif) {
		emp.setCif(cif);
	}

	public String getNombre() {
		return emp.getNombre();
	}

	public void setNombre(String nombre) {
		emp.setNombre(nombre);
	}

	public String getApellidos() {
		return emp.getApellidos();
	}

	public void setApellidos(String apellidos) {
		emp.setApellidos(apellidos);
	}

	public int getEdad() {
		return emp.getEdad();
	}

	public void setEdad(int edad) {
		emp.setEdad(edad);
	}
	
	public Collection<Empleado> getEmpleados() {
		SortedSet<Empleado> empleados;
		if (workingWith == WorkingWith.DAO) {
			empleados = dao.getAllEmpleados();
		} else {
			empleados = negocio.getAllEmpleados();
		}
		return new ArrayList<Empleado>(empleados);
	}
	
	public void insertarEmpleado(ActionEvent event) {
		if (workingWith == WorkingWith.DAO) {
			dao.insertaEmpleado(emp);
		} else {
			negocio.insertaEmpleado(emp);
		}
	}

	public void eliminarEmpleado(ActionEvent event) {
		if (workingWith == WorkingWith.DAO) {
			dao.eliminaEmpleado(this.getCif());
		} else {
			negocio.eliminaEmpleado(this.getCif());
		}
	}

	public void modificarEmpleado(ActionEvent event) {
		if (workingWith == WorkingWith.DAO) {
			dao.modificaEmpleado(emp);
		} else {
			negocio.modificaEmpleado(emp);
		}
	}
	
	public void getEmpleado(ActionEvent event) {
		if (workingWith == WorkingWith.DAO) {
			emp = dao.getEmpleado(this.getCif());
		} else {
			emp = negocio.getEmpleado(this.getCif());
		}
	}
	
}
