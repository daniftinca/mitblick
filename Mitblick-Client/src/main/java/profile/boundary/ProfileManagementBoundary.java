package profile.boundary;

import com.google.gson.Gson;
import exception.BusinessException;
import profile.dto.ProfileDTO;
import profile.service.ProfileManagementService;
import utils.Secured;

import javax.ejb.EJB;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.util.List;

@Path("/manage-profiles")
public class ProfileManagementBoundary {

    @EJB
    private ProfileManagementService profileManagementService;

    @GET
    @Secured("PROFILE_MANAGEMENT")
    @Path("/get-all-profiles")
    public Response getAll(@Context SecurityContext securityContext) {

        try {
            List<ProfileDTO> allProfiles = profileManagementService.getAll();
            String allProfilesJson = new Gson().toJson(allProfiles);
            return Response.ok(allProfilesJson).build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }

    @POST
    @Consumes("application/json")
    @Produces("application/json")
    @Path("/update-user")
    public Response update(ProfileDTO profileDTO, @Context HttpHeaders headers) {

        profileManagementService.update(profileDTO);
        return Response.ok().build();
    }

    @POST
    @Path("/register-user")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response register(final ProfileDTO profileDTO) {

        try {
            profileManagementService.create(profileDTO);
            return Response.status(Response.Status.CREATED).build();
        } catch (BusinessException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getExceptionCode()).build();
        }
    }

}
