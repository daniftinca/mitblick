package user.boundary;

import com.google.gson.Gson;
import exception.BusinessException;
import user.entities.User;
import user.service.EmployeeManagementService;

import javax.ejb.EJB;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

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

    @GET
    @Path("/get-employees-from-supervisor/{supervisorEmail}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getEmployeesFromSupervisor(@PathParam("supervisorEmail") String supervisorEmail) {
        try {
            List<User> employees = employeeManagementService.getAllEmployeesFromSupervisor(supervisorEmail);
            String allEmployeesJson = new Gson().toJson(employees);
            return Response.ok(allEmployeesJson).build();
        } catch (BusinessException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getExceptionCode()).build();
        }

    }

}
