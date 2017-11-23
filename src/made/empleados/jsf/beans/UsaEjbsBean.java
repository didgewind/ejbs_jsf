package made.empleados.jsf.beans;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import javax.ejb.EJB;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import made.empleados.ejbs.CalculatorNegocioLocal;
import made.empleados.ejbs.KeepTrackNegocioLocal;

/**
 * Managed Bean para probar ejbs
 * 
 * @author made
 *
 */
@ManagedBean
@ApplicationScoped
public class UsaEjbsBean {

	/*
	 * A la hora de inyectar los ejbs, los mappedName se utilizarían si
	 * se trata de ejb's desplegados en otro espacio de memoria (por ejemplo,
	 * en un .jar aparte) y los name si está todo en un .ear (como es el caso)
	 */
//	@EJB(mappedName = "java:global/ejb_empleados/calculator!made.empleados.ejbs.CalculatorNegocioRemoto")
	@EJB(name="calculatorEJB")
	private CalculatorNegocioLocal calculatorEjb;
	
//	@EJB(mappedName = "java:global/ejb_empleados/keepTrack!made.empleados.ejbs.KeepTrackNegocioRemoto")
	@EJB(name="keepTrackEJB")
	private KeepTrackNegocioLocal sumadorEjb;
	
	private String cifrasASumar;
	private int add2Sumador = 0;
	
	public String getCifrasASumar() {
		return cifrasASumar;
	}

	public void setCifrasASumar(String cifrasASumar) {
		this.cifrasASumar = cifrasASumar;
	}

	public int getAdd2Sumador() {
		return add2Sumador;
	}

	public void setAdd2Sumador(int add2Sumador) {
		this.add2Sumador = add2Sumador;
	}

	public void setCalculatorBean(CalculatorNegocioLocal calculatorBean) {
		this.calculatorEjb = calculatorBean;
	}

	public void setSumadorEjb(KeepTrackNegocioLocal sumadorEjb) {
		this.sumadorEjb = sumadorEjb;
	}

	public int getCalculatorValue() {
		if (this.cifrasASumar != null) {
			return calculatorEjb.add(convierteCifrasASumar());
		} else {
			return 0;
		}
	}

	public int getSumadorValue() {
		return sumadorEjb.getValue();
	}
	
	public void calcula(ActionEvent event) {
		sumadorEjb.add(add2Sumador);
	}
	
	private List<Integer> convierteCifrasASumar() {
		List<Integer> lInt = new ArrayList<Integer>();
		StringTokenizer cifrasString = new StringTokenizer(this.cifrasASumar);
		while (cifrasString.hasMoreTokens()) {
			lInt.add(Integer.parseInt(cifrasString.nextToken()));
		}
		return lInt;
	}
	
	/**
	 * Hago un invalidate de la sesión
	 * @return
	 */
    public String logout() {
        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
        try {
			((HttpServletRequest) (FacesContext.getCurrentInstance().getExternalContext().getRequest())).logout();
		} catch (ServletException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        return "/index.xhtml?faces-redirect=true";
    }
}
