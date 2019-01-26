package notification.boundary;


import exception.BusinessException;
import notification.service.NotificationManagementService;
import notifications.entities.Notification;

import javax.ejb.EJB;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/manage-notifications")
public class NotificationManagementBoundary {

    @EJB
    private NotificationManagementService notificationManagementService;


    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/add-notification")
    public Response createSkill(final Notification notification) {
        notificationManagementService.createNotification(notification);
        return Response.status(Response.Status.CREATED).build();
    }

    @POST
    @Path("/delete-notification")
    @Consumes({"application/x-www-form-urlencoded"})
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteSkill(@FormParam("notificationId") Long notificationId) {

        try {

            notificationManagementService.deleteNotification(notificationId);
            return Response.status(Response.Status.OK).build();
        } catch (BusinessException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getExceptionCode()).build();
        }
    }

}
