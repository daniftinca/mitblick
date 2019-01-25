package skills.boundary;


import exception.BusinessException;
import skills.dto.SkillDTO;
import skills.service.SkillManagementService;

import javax.ejb.EJB;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/manage-skills")
public class SkillManagementBoundary {

    @EJB
    private SkillManagementService skillManagementService;

    /**
     * Updates a skill.
     *
     * @param oldName
     * @param newName
     * @param headers
     * @return
     */
//    @POST
//    @Consumes("application/x-www-form-urlencoded")
//    @Produces("application/json")
//    @Path("/update-skill")
//    public Response updateSkill(@FormParam("oldName") String oldName,@FormParam("newName") String newName, @Context HttpHeaders headers) {
//        try {
//
//            skillManagementService.updateSkill(oldName,newName);
//            return Response.ok().build();
//        } catch (BusinessException e) {
//            return Response.status(Response.Status.BAD_REQUEST).entity(e.getExceptionCode()).build();
//        }
//    }

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
     * Registers a skill with the given JSON
     *
     * @param skillDTO
     * @return
     */
    @POST
    @Path("/delete-skill")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteSkill(final SkillDTO skillDTO) {

        try {

            skillManagementService.deleteSkill(skillDTO);

            return Response.status(Response.Status.OK).build();
        } catch (BusinessException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getExceptionCode()).build();
        }
    }
}
