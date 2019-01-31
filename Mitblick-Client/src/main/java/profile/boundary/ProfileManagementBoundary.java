package profile.boundary;

import com.google.gson.Gson;
import exception.BusinessException;
import profile.dto.FilterDTO;
import profile.dto.ProfileDTO;
import profile.entities.JobTitle;
import profile.entities.Region;
import profile.service.ProfileManagementService;

import javax.ejb.EJB;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.util.List;

@Path("/manage-profiles")
public class ProfileManagementBoundary {

    @EJB
    private ProfileManagementService profileManagementService;

    /**
     * Get all profiles.
     *
     * @param securityContext
     * @return List of profiles as Json | BAD_REQUEST
     */
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


    /**
     * Get a profile identified by an id.
     *
     * @param id              Profile id.
     * @param securityContext
     * @return Profile as Json | BAD_REQUEST
     */
    @GET
    @Path("/get-by-id/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getById(@PathParam("id") long id, @Context SecurityContext securityContext) {

        try {
            ProfileDTO profile = profileManagementService.getById(id);
            String profileStr = new Gson().toJson(profile);
            return Response.ok(profileStr).build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }

    /**
     * Get a profile identified by an email.
     *
     * @param email Profile email.
     * @param securityContext
     * @return Profile as Json | BAD_REQUEST
     */
    @GET
    @Path("/get-by-email/{email}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getByEmail(@PathParam("email") String email, @Context SecurityContext securityContext) {

        try {
            ProfileDTO profile = profileManagementService.getByEmail(email);
            String profileStr = new Gson().toJson(profile);
            return Response.ok(profileStr).build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }


    /**
     * Update a profile.
     *
     * @param profileDTO A profileDTO with which the profile will be updated by their id.
     * @param headers
     * @return Updated profile as Json | BAD_REQUEST
     */
    @POST
    @Consumes("application/json")
    @Produces("application/json")
    @Path("/update")
    public Response update(ProfileDTO profileDTO, @Context HttpHeaders headers) {
        try {

            ProfileDTO profile = profileManagementService.update(profileDTO);
            String profileStr = new Gson().toJson(profile);
            return Response.ok(profileStr).build();
        } catch (BusinessException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getExceptionCode()).build();
        }
    }

    /**
     * Create a profile.
     *
     * @param profileDTO ProfileDTO containing the profile that has to be created.
     * @return CREATED | BAD_REQUEST
     */
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

    /**
     * Delete a profile.
     *
     * @param profileDTO ProfileDTO containing the profile that has to be deleted.
     * @return O | BAD_REQUEST
     */
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

    /**
     * Add a skill to a profile. Profile has a skill entry a skill id, a skill area name and a skill rating are required.
     *
     * @param skillId Id of the skill that has to be added.
     * @param skillAreaName Name of the skill area.
     * @param skillRating Rating of the skill.
     * @param email Email of the profile.
     * @param headers
     * @return OK | BAD_REQUEST
     */
    @POST
    @Path("/add-skill")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addSkill(@FormParam("skillId") Long skillId,
                             @FormParam("skillAreaName") String skillAreaName,
                             @FormParam("skillRating") Integer skillRating,
                             @FormParam("email") String email,
                             @Context HttpHeaders headers) {
        try {
            ProfileDTO profile = profileManagementService.addSkill(skillId, skillAreaName, skillRating, email);
            String profileStr = new Gson().toJson(profile);
            return Response.ok(profileStr).build();
        } catch (BusinessException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getExceptionCode()).build();
        }
    }

    /**
     * Add a project to a profile.
     *
     * @param projektName Name of the project that has to be added.
     * @param email Email of the profile.
     * @param headers
     * @return OK | BAD_REQUEST
     */
    @POST
    @Path("/add-projekt")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addProjekt(@FormParam("projektName") String projektName,
                               @FormParam("email") String email,
                               @Context HttpHeaders headers) {
        try {
            ProfileDTO profile = profileManagementService.addProjekt(projektName, email);
            String profileStr = new Gson().toJson(profile);
            return Response.ok(profileStr).build();
        } catch (BusinessException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getExceptionCode()).build();
        }
    }

    /**
     * Remove a skill from a profile.
     *
     * @param skillId Id of the skill that has to be removed.
     * @param email Email of the profile.
     * @param headers
     * @return Profile with the skil removed as Json | BAD_REQUEST
     */
    @POST
    @Path("/remove-skill")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    public Response removeSkill(@FormParam("skillId") Long skillId,
                                @FormParam("email") String email,
                                @Context HttpHeaders headers) {
        try {
            ProfileDTO profile = profileManagementService.removeSkill(skillId, email);
            String profileStr = new Gson().toJson(profile);
            return Response.ok(profileStr).build();
        } catch (BusinessException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getExceptionCode()).build();
        }
    }

    /**
     * Remove a project from a profile.
     *
     * @param projektName Name of the project that has to be removed.
     * @param email Email of the profile.
     * @param headers
     * @return OK | BAD_REQUEST
     */
    @POST
    @Path("/remove-projekt")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    public Response removeProjekt(@FormParam("projektName") String projektName,
                                  @FormParam("email") String email,
                                  @Context HttpHeaders headers) {
        try {
            ProfileDTO profile = profileManagementService.removeProjekt(projektName, email);
            String profileStr = new Gson().toJson(profile);
            return Response.ok(profileStr).build();
        } catch (BusinessException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getExceptionCode()).build();
        }
    }


    /**
     * Accept Profile after reviewing the changes.
     *
     * @param supervisorEmail The supervisor who reviews and accepts changes.
     * @param userEmail The user whose profile is reviewed.
     * @param headers
     * @return OK | BAD_REQUEST
     */
    @POST
    @Path("/accept")
    @Consumes({"application/x-www-form-urlencoded"})
    public Response acceptProfile(@FormParam("supervisorEmail") String supervisorEmail, @FormParam("userEmail") String userEmail, @Context HttpHeaders headers) {
        try {
            profileManagementService.acceptProfile(supervisorEmail,userEmail);
            return Response.ok().build();
        } catch (BusinessException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getExceptionCode()).build();
        }
    }

    @POST
    @Path("/unaccept")
    @Consumes({"application/x-www-form-urlencoded"})
    public Response unacceptProfile(@FormParam("supervisorEmail") String supervisorEmail, @FormParam("userEmail") String userEmail, @Context HttpHeaders headers) {
        try {
            profileManagementService.unacceptProfile(supervisorEmail,userEmail);
            return Response.ok().build();
        } catch (BusinessException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getExceptionCode()).build();
        }
    }

    @GET
    @Path("/filter")
    @Produces(MediaType.APPLICATION_JSON)
    public String filter(@QueryParam("index") int index, @QueryParam("amount") int amount, @QueryParam("criteriaName") List<String> criteriaNames, @QueryParam("criteriaValue") List<String> criteriaValues, @QueryParam("skillId") List<Long> skillIDs) {


        try {
            FilterDTO filterDTO = profileManagementService.filter(index, amount, criteriaNames, criteriaValues, skillIDs);
            return new Gson().toJson(filterDTO);
        } catch (BusinessException e) {
            return "not working because business exception";
        }

    }

    /**
     * Get all job titles from the database.
     *
     * @param securityContext
     * @return Job titles as Json | BAD_REQUEST
     */
    @GET
    @Path("/get-all-jobTitles")
    public Response getAllJobTitles(@Context SecurityContext securityContext) {
        try {
            List<JobTitle> jobTitles = profileManagementService.getAllJobTitles();
            String allJobTitles = new Gson().toJson(jobTitles);
            return Response.ok(allJobTitles).build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }

    /**
     * Get all regions from the database.
     *
     * @param securityContext
     * @return Regions as Json | BAD_REQUEST
     */
    @GET
    @Path("/get-all-regions")
    public Response getAllRegions(@Context SecurityContext securityContext) {
        try {
            List<Region> regions = profileManagementService.getAllRegions();
            String allRegions = new Gson().toJson(regions);
            return Response.ok(allRegions).build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }


//    /**
//     * Sends email. Needs recieverEmail, subject and message as parameters.
//     * @return
//     */
//    @POST
//    @Path("/send-email")
//    public Response sendEmail(){
//        try{
//            profileManagementService.sendMailUsingSSL("noreply.mitlick@gmail.com", "Test", "Test the test");
//            return Response.ok().build();
//        }
//        catch (Exception e) {
//            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
//        }
//    }

    @GET
    @Path("/filter-by-supervisor")
    @Produces(MediaType.APPLICATION_JSON)
    public String filterSupervisor(@QueryParam("email") String email) {
        try {
            FilterDTO filterDTO = profileManagementService.filterSupervisor(email);
            return new Gson().toJson(filterDTO);
        } catch (Exception e) {
            return "not working because json exception";
        }
    }


}
