package skills.boundary;


import com.google.gson.Gson;
import exception.BusinessException;
import skills.dto.SkillAreaDTO;
import skills.entities.Skill;
import skills.entities.SkillArea;
import skills.service.SkillAreaManagementService;

import javax.ejb.EJB;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
    public Response updateSkillArea(@FormParam("oldName") String oldName, @FormParam("newName") String newName, @FormParam("newDescription") String newDescription, @Context HttpHeaders headers) {
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

    /**
     * Add a skill to a skill area.
     *
     * @param skillId       Id of the skill that has to be added.
     * @param skillAreaName Name of the skill area.
     * @return OK | BAD_REQUEST
     */
    @POST
    @Path("/add-skill")
    @Consumes({"application/x-www-form-urlencoded"})
    @Produces(MediaType.APPLICATION_JSON)
    public Response addSkillToSkillArea(@FormParam("skillId") Long skillId, @FormParam("skillAreaName") String skillAreaName) {

        try {
            skillAreaManagementService.addSkillToSkillArea(skillId, skillAreaName);
            return Response.status(Response.Status.OK).build();
        } catch (BusinessException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getExceptionCode()).build();
        }
    }

    /**
     * Remove skill from skill area.
     *
     * @param skillId Id of the skill that has to be removes.
     * @param skillAreaName Name of the skill area.
     * @return OK | BAD_REQUEST
     */
    @POST
    @Path("/remove-skill")
    @Consumes({"application/x-www-form-urlencoded"})
    @Produces(MediaType.APPLICATION_JSON)
    public Response removeSkillFromSkillArea(@FormParam("skillId") Long skillId, @FormParam("skillAreaName") String skillAreaName) {
        try {
            skillAreaManagementService.removeSkillFromSkillArea(skillId, skillAreaName);
            return Response.status(Response.Status.OK).build();
        } catch (BusinessException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getExceptionCode()).build();
        }
    }

    /**
     * Get all skill areas.
     *
     * @return Skill areas as Json | BAD_REQUEST
     */
    @GET
    @Path("/get-all-skillareas")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllSkillAreas() {
        try {
            Optional<List<SkillArea>> skillAreas = skillAreaManagementService.getAllSkillAreas();
            if (skillAreas.isPresent()) {
                String allSkillAreasJson = new Gson().toJson(skillAreas.get());
                return Response.ok(allSkillAreasJson).build();
            }
            return Response.ok(new ArrayList<SkillArea>()).build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }


    /**
     * Get all skills from a skill area.
     *
     * @param name Name of the skill area.
     * @return Skills as Json | BAD_REQUEST
     */
    @GET
    @Path("/get-all-skills-from-skillarea/{name}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllSkillsFromSkillAreas(@PathParam("name") String name) {
        try {
            List<Skill> skills = skillAreaManagementService.getSkillsFromSkillArea(name);
            String allSkillsJson = new Gson().toJson(skills);
            return Response.ok(allSkillsJson).build();
        } catch (BusinessException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getExceptionCode()).build();
        }

    }


}
