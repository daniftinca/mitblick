package user.boundary;

import exception.BusinessException;
import user.service.EmployeeManagementService;

import javax.ejb.EJB;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

@Path("/manage-employees")
public class EmployeeManagementBoundary {

    @EJB
    private EmployeeManagementService employeeManagementService;

    @POST
    @Path("/add-employee-to-supervisor")
    @Consumes({"application/x-www-form-urlencoded"})
    public Response addEmployeeToSupervisor(@FormParam("supervisorEmail") String supervisorEmail,@FormParam("employeeEmail") String employeeEmail){
        try {
            employeeManagementService.addEmployeeToSupervisor(supervisorEmail,employeeEmail);
            return Response.status(Response.Status.OK).build();
        }catch (BusinessException e){
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getExceptionCode()).build();
        }
    }

    @POST
    @Path("/remove-employee-from-supervisor")
    @Consumes({"application/x-www-form-urlencoded"})
    public Response removeEmployeeFromSupervisor(@FormParam("supervisorEmail") String supervisorEmail,@FormParam("employeeEmail") String employeeEmail){
        try {
            employeeManagementService.removeEmployeeFromSupervisor(supervisorEmail,employeeEmail);
            return Response.status(Response.Status.OK).build();
        }catch (BusinessException e){
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getExceptionCode()).build();
        }
    }
}
