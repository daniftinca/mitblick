package skills.boundary;


import exception.BusinessException;
import skills.dto.SkillAreaDTO;
import skills.service.SkillAreaManagementService;

import javax.ejb.EJB;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/manage-skill-areas")
public class SkillAreaManagementBoundary {

    @EJB
    private SkillAreaManagementService skillAreaManagementService;

    /**
     * Registers a skill area with the given JSON
     *
     * @param skillAreaDTO
     * @return
     */
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/add-skill-area")
    public Response createSkillArea(final SkillAreaDTO skillAreaDTO) {

        try {
            skillAreaManagementService.createSkillArea(skillAreaDTO);
            return Response.status(Response.Status.CREATED).build();
        } catch (BusinessException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getExceptionCode()).build();
        }
    }


    /**
     * Updates a skill area.
     *
     * @param oldName
     * @param newName
     * @param newDescription
     * @param headers
     * @return
     */
    @POST
    @Consumes({"application/x-www-form-urlencoded"})
    @Produces("application/json")
    @Path("/update-skill-area")
    public Response updateSkillArea(@FormParam("oldName") String oldName,@FormParam("newName") String newName,@FormParam("newDescription") String newDescription, @Context HttpHeaders headers) {
        try {
            SkillAreaDTO skillAreaDTO = new SkillAreaDTO();
            skillAreaDTO.setName(newName);
            skillAreaDTO.setDescription(newDescription);
            skillAreaManagementService.updateSkillArea(oldName, skillAreaDTO);
            return Response.ok().build();
        } catch (BusinessException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getExceptionCode()).build();
        }
    }


    /**
     * Registers a skill area with the given JSON
     *
     * @param skillAreaDTO
     * @return
     */
    @POST
    @Path("/delete-skill-area")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteSkillArea(final SkillAreaDTO skillAreaDTO) {

        try {

            skillAreaManagementService.deleteSkillArea(skillAreaDTO);
            return Response.status(Response.Status.OK).build();
        } catch (BusinessException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getExceptionCode()).build();
        }
    }

}
