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
    @Path("/get-by-id")
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.APPLICATION_JSON)
    public Response getById(long id, @Context SecurityContext securityContext) {

        try {
            ProfileDTO profile = profileManagementService.getById(id);
            String profileStr = new Gson().toJson(profile);
            return Response.ok(profileStr).build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }

    @POST
    @Path("/get-by-email")
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.APPLICATION_JSON)
    public Response getByEmail(String email, @Context SecurityContext securityContext) {

        try {
            ProfileDTO profile = profileManagementService.getByEmail(email);
            String profileStr = new Gson().toJson(profile);
            return Response.ok(profileStr).build();
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

    @POST
    @Path("/delete")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response delete(final ProfileDTO profileDTO) {

        try {
            profileManagementService.delete(profileDTO);
            return Response.ok().build();
        } catch (BusinessException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getExceptionCode()).build();
        }
    }

    @POST
    @Path("/add-skill")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addSkill(@FormParam("skillName") String skillName,
                             @FormParam("email") String email,
                             @Context HttpHeaders headers) {
        try {
            profileManagementService.addSkill(skillName, email);
            return Response.ok().build();
        } catch (BusinessException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getExceptionCode()).build();
        }
    }

    @POST
    @Path("/add-projekt")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addProjekt(@FormParam("projektName") String projektName,
                               @FormParam("email") String email,
                               @Context HttpHeaders headers) {
        try {
            profileManagementService.addProjekt(projektName, email);
            return Response.ok().build();
        } catch (BusinessException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getExceptionCode()).build();
        }
    }

    @POST
    @Path("/remove-skill")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    public Response removeSkill(@FormParam("skillName") String skillName,
                                @FormParam("email") String email,
                                @Context HttpHeaders headers) {
        try {
            profileManagementService.removeSkill(skillName, email);
            return Response.ok().build();
        } catch (BusinessException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getExceptionCode()).build();
        }
    }

    @POST
    @Path("/remove-projekt")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    public Response removeProjekt(@FormParam("projektName") String projektName,
                                  @FormParam("email") String email,
                                  @Context HttpHeaders headers) {
        try {
            profileManagementService.removeProjekt(projektName, email);
            return Response.ok().build();
        } catch (BusinessException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getExceptionCode()).build();
        }
    }
    

}
