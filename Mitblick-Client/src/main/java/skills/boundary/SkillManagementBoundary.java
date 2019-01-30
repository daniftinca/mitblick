package skills.boundary;


import exception.BusinessException;
import skills.dto.SkillDTO;
import skills.service.SkillManagementService;

import javax.ejb.EJB;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/manage-skills")
public class SkillManagementBoundary {

    @EJB
    private SkillManagementService skillManagementService;

    /**
     * Registers a skill with the given JSON
     *
     * @param skillDTO
     * @return
     */
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/add-skill/{skillareaname}")
    public Response createSkill(final SkillDTO skillDTO, @PathParam("skillareaname") String skillAreaName) {

        try {
            skillManagementService.createSkill(skillDTO, skillAreaName);
            return Response.status(Response.Status.CREATED).build();
        } catch (BusinessException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getExceptionCode()).build();
        }
    }


    /**
     * Delete a skill.
     *
     * @param skillId Id of the skill that has to be deleted.
     * @return OK | BAD_REQUEST
     */
    @POST
    @Path("/delete-skill")
    @Consumes({"application/x-www-form-urlencoded"})
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteSkill(@FormParam("skillId") Long skillId) {

        try {

            skillManagementService.deleteSkill(skillId);

            return Response.status(Response.Status.OK).build();
        } catch (BusinessException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getExceptionCode()).build();
        }
    }
}
