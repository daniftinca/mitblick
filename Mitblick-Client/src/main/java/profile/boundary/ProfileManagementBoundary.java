package profile.boundary;

import com.google.gson.Gson;
import exception.BusinessException;
import profile.dto.ProfileDTO;
import profile.service.ProfileManagementService;

import javax.ejb.EJB;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.util.List;

@Path("/manage-profiles")
public class ProfileManagementBoundary {

    @EJB
    private ProfileManagementService profileManagementService;

    @GET
    @Path("/get-all")
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
    @Path("/update")
    public Response update(ProfileDTO profileDTO, @Context HttpHeaders headers) {
        try {

            profileManagementService.update(profileDTO);
            return Response.ok().build();
        } catch (BusinessException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getExceptionCode()).build();
        }
    }

    @POST
    @Path("/create")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response create(final ProfileDTO profileDTO) {

        try {
            profileManagementService.create(profileDTO);
            return Response.status(Response.Status.CREATED).build();
        } catch (BusinessException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getExceptionCode()).build();
        }
    }

}
