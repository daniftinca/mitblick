package projekt.boundary;

import com.auth0.jwt.JWT;
import com.google.gson.Gson;
import exception.BusinessException;
import projekt.dto.ProjektDTO;
import projekt.service.ProjektManagementService;

import javax.ejb.EJB;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.util.List;

@Path("/manage-projekts")
public class ProjektManagementBoundary {

    @EJB
    private ProjektManagementService projektManagementService;

    @GET
    @Path("/get-all")
    public Response getAll(@Context SecurityContext securityContext) {

        try {
            List<ProjektDTO> allProjekts = projektManagementService.getAll();
            String allProjektsJson = new Gson().toJson(allProjekts);
            return Response.ok(allProjektsJson).build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }

    @POST
    @Consumes("application/json")
    @Produces("application/json")
    @Path("/update")
    public Response update(ProjektDTO projektDTO, @Context HttpHeaders headers) {
        try {

            projektManagementService.update(projektDTO);
            return Response.ok().build();
        } catch (BusinessException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getExceptionCode()).build();
        }
    }

    @POST
    @Path("/create")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response create(final ProjektDTO projektDTO, @Context HttpHeaders headers) {

        try {
            projektManagementService.create(projektDTO, getRequester(headers));
            return Response.status(Response.Status.CREATED).build();
        } catch (BusinessException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getExceptionCode()).build();
        }
    }

    private String getRequester(@Context HttpHeaders headers) {
        String authorizationHeader = headers.getRequestHeader("authorization").get(0);
        String token = authorizationHeader.substring("Bearer".length()).trim();
        return JWT.decode(token).getClaim("email").asString();

    }

    @POST
    @Path("/delete/{email}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response delete(final ProjektDTO projektDTO, @PathParam("email") String profileEmail, @Context HttpHeaders headers) {

        try {
            projektManagementService.delete(projektDTO, profileEmail);
            return Response.ok().build();
        } catch (BusinessException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getExceptionCode()).build();
        }
    }
}
