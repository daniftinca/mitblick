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

    /**
     * Adds an employee to a supervisor.
     *
     * @param supervisorEmail Email of the supervisor.
     * @param employeeEmail   Email of the user.
     * @return OK | BAD_REQUEST
     */
    @POST
    @Path("/add-employee-to-supervisor")
    @Consumes({"application/x-www-form-urlencoded"})
    public Response addEmployeeToSupervisor(@FormParam("supervisorEmail") String supervisorEmail, @FormParam("employeeEmail") String employeeEmail) {
        try {
            employeeManagementService.addEmployeeToSupervisor(supervisorEmail, employeeEmail);
            return Response.status(Response.Status.OK).build();
        } catch (BusinessException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getExceptionCode()).build();
        }
    }

    /**
     * Removes an employee from a supervisor.
     *
     * @param supervisorEmail Email of the supervisor.
     * @param employeeEmail Email of the employee that has to be removed.
     * @return OK | BAD_REQUEST
     */
    @POST
    @Path("/remove-employee-from-supervisor")
    @Consumes({"application/x-www-form-urlencoded"})
    public Response removeEmployeeFromSupervisor(@FormParam("supervisorEmail") String supervisorEmail, @FormParam("employeeEmail") String employeeEmail) {
        try {
            employeeManagementService.removeEmployeeFromSupervisor(supervisorEmail, employeeEmail);
            return Response.status(Response.Status.OK).build();
        } catch (BusinessException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getExceptionCode()).build();
        }
    }

    /**
     * Get all employees that have the supervisor with the given email.
     *
     * @param supervisorEmail Email of the supervisor
     * @return Employees as Json | BAD_REQUEST
     */
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

    /**
     *Get all supervisors from the database.
     *
     * @return Supervisors as Json.
     */
    @GET
    @Path("/get-all-supervisors")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllSupervisors() {
        List<User> supervisors = employeeManagementService.getAllSupervisors();
        String allEmployeesJson = new Gson().toJson(supervisors);
        return Response.ok(allEmployeesJson).build();
    }

}
