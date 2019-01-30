package user.boundary;

import com.google.gson.Gson;
import exception.BusinessException;
import profile.service.ProfileManagementService;
import user.dto.UserDTO;
import user.service.UserManagementService;

import javax.ejb.EJB;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.util.List;

@Path("/manage-users")
public class UserManagementBoundary {

    @EJB
    private UserManagementService userManagementService;

    @EJB
    private ProfileManagementService profileManagementService;

    /**
     * Returns all users.
     *
     * @param securityContext
     * @return
     */
    @GET
    //@Secured("USER_MANAGEMENT")
    @Path("/get-all-users")
    public Response getAllUsers(@Context SecurityContext securityContext) {
        try {
            List<UserDTO> allUsers = userManagementService.getAllUsers();
            String allUsersJson = new Gson().toJson(allUsers);
            return Response.ok(allUsersJson).build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }

    /**
     * Returns all users with supervisor role.
     *
     * @param securityContext
     * @return
     */
    @GET
    //@Secured("USER_MANAGEMENT")
    @Path("/get-all-supervisors")
    public Response getAllSupervisors(@Context SecurityContext securityContext) {
        try {
            List<UserDTO> allUsers = userManagementService.getAllSupervisors();
            String allUsersJson = new Gson().toJson(allUsers);
            return Response.ok(allUsersJson).build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }

    /**
     * Activates a user.
     *
     * @param email
     * @return
     */
    @POST
    @Produces("application/json")
    @Path("/activate-user")
    public Response activateUser(@FormParam("email") String email) {
        try {
            userManagementService.activateUser(email);
            return Response.ok().build();
        } catch (BusinessException e) {
            return Response.status(Response.Status.NOT_ACCEPTABLE).entity(e.getExceptionCode()).build();
        }
    }

    /**
     * Deactivates a User
     *
     * @param email
     * @return
     */
    @POST
    @Produces("application/json")
    @Path("/deactivate-user")
    public Response deactivateUser(@FormParam("email") String email) {
        try {
            userManagementService.deactivateUser(email);
            return Response.ok().build();
        } catch (BusinessException e) {
            return Response.status(Response.Status.UNAUTHORIZED).entity(e.getExceptionCode()).build();
        }
    }

    /**
     * Updates a user.
     *
     * @param userDTO
     * @param headers
     * @return
     */
    @POST
    @Consumes("application/json")
    @Produces("application/json")
    @Path("/update-user")
    public Response updateUser(UserDTO userDTO, @Context HttpHeaders headers) {
        try {

            userManagementService.updateUser(userDTO);
            return Response.ok().build();
        } catch (BusinessException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getExceptionCode()).build();
        }
    }

    /**
     * Registers a user with the given JSON
     *
     * @param userDTO
     * @return
     */
    @POST
    @Path("/register-user")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response registerUser(final UserDTO userDTO) {

        try {

            userManagementService.createUser(userDTO);
            return Response.status(Response.Status.CREATED).build();
        } catch (BusinessException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getExceptionCode()).build();
        }
    }

    /**
     * Returns the roles of a user.
     *
     * @param email
     * @return
     */
    @GET
    @Path("/get-roles-of-user")
    @Produces("application/json")
    @Consumes("application/json")
    public Response getRolesOfUser(@QueryParam("email") String email) {
        try {
            return Response
                    .ok(
                            new Gson().toJson(userManagementService
                                    .getUserForEmail(email)
                                    .getRoles())
                    )
                    .build();
        } catch (BusinessException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getExceptionCode()).build();
        }
    }


}

