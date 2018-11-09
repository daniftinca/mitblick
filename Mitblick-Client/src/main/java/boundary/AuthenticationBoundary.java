package boundary;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.google.gson.Gson;
import exception.BusinessException;
import user.entities.User;
import user.service.UserManagementService;

import javax.ejb.EJB;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Date;


@Path("/authenticate")
public class AuthenticationBoundary {

    @EJB
    private UserManagementService userManagement;

    // static Logger log = LogManager.getLogger(AuthenticationManagementController.class.getName());

    /**
     * Authenticates a user and creates a JWT token for him and returns it.
     *
     * @param email
     * @param password
     * @param securityContext
     * @return
     */
    @POST
    @Produces("application/json")
    @Consumes("application/x-www-form-urlencoded")
    public Response authenticateUser(@FormParam("email") String email,
                                     @FormParam("password") String password, @Context SecurityContext securityContext) {
        try {

            userManagement.login(email, password);
            User user = userManagement.getUserForEmail(email);
            String token = issueToken(user);
            return Response.ok("{\"token\": \"" + token + "\"}").build();
        } catch (BusinessException e) {
            return Response.status(Response.Status.UNAUTHORIZED).entity(e.getExceptionCode()).build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
    }

    private String issueToken(User user) {
        LocalTime midnight = LocalTime.MIDNIGHT;
        LocalDate today = LocalDate.now(ZoneId.of("Europe/Bucharest"));
        LocalDateTime todayMidnight = LocalDateTime.of(today, midnight);
        LocalDateTime tomorrowMidnight = todayMidnight.plusDays(1);
        Date out = Date.from(tomorrowMidnight.atZone(ZoneId.systemDefault()).toInstant());

        String rolesJson = new Gson().toJson(user.getRoles());


        try {
            Algorithm algorithm = Algorithm.HMAC256("secret");
            return JWT.create()
                    .withExpiresAt(out)
                    .withClaim("email", user.getEmail())
                    .withClaim("role", rolesJson)
                    .sign(algorithm);

        } catch (JWTCreationException exception) {
            //Invalid Signing configuration / Couldn't convert Claims.
            //log.catching(exception);
            return "";
        }

    }


}

