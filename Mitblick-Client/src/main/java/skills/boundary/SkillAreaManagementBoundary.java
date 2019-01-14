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

    @GET
    @Path("/somthing")
    public Response doSomthign(){
        return Response.status(Response.Status.CREATED).build();
    }


    /**
     * Updates a skill area.
     *
     * @param skillAreaDTO
     * @param headers
     * @return
     */
    @POST
    @Consumes("application/json")
    @Produces("application/json")
    @Path("/update-skill-area")
    public Response updateSkillArea(SkillAreaDTO skillAreaDTO, @Context HttpHeaders headers) {
        try {

            skillAreaManagementService.updateSkillArea(skillAreaDTO);
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
            return Response.status(Response.Status.CREATED).build();
        } catch (BusinessException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getExceptionCode()).build();
        }
    }

}
