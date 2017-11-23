package made.empleados.servlets;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Ahora mismo no se utiliza. Lo probé para hacer logout, pero ahora funciona
 * el método logout() del bean UsaEjbsBean
 * @author made
 *
 */
@WebServlet("/logoutServlet")
public class LogOutServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
 
    protected void doGet(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {
 
        boolean printJaasInfo = true;
        if(printJaasInfo){
            try{
                System.out.println("LogoutServlet*********************************");
                System.out.println("request.getClass().getName():"+request.getClass().getName());
                System.out.println("isAdministrator:"+request.isUserInRole("ROLE_ADMIN"));
                System.out.println("remoteUser:"+request.getRemoteUser());
                System.out.println("userPrincipalName:"+(request.getUserPrincipal()==null?"null":request.getUserPrincipal().getName()));//ybxiang
                System.out.println("LogoutServlet*********************************");       
            }catch(Exception e){
                e.printStackTrace();
            }
        }
 
        response.setHeader("Cache-Control", "no-cache, no-store");
        response.setHeader("Pragma", "no-cache");
        //
        if(request.getSession(false)!=null){
            request.getSession(false).invalidate();//remove session.
        }
        if(request.getSession()!=null){
            request.getSession().invalidate();//remove session.
        }
        request.logout();//JAAS log out! do NOT work? (servlet specification)
        //response.sendRedirect(request.getContextPath() + "/login.jsp");
        response.sendRedirect(request.getContextPath());
    }
}