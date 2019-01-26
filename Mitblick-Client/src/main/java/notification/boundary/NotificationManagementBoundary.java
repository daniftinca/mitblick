package notification.boundary;


import com.google.gson.Gson;
import exception.BusinessException;
import notification.service.NotificationManagementService;
import notifications.entities.Notification;

import javax.ejb.EJB;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.Optional;

@Path("/manage-notifications")
public class NotificationManagementBoundary {

    @EJB
    private NotificationManagementService notificationManagementService;


    @POST
    @Path("/mark-notification-as-read")
    @Consumes({"application/x-www-form-urlencoded"})
    @Produces(MediaType.APPLICATION_JSON)
    public Response markNotificationAsRead(@FormParam("notificationId") Long notificationId) {

        try {

            notificationManagementService.markNotificationAsRead(notificationId);
            return Response.status(Response.Status.OK).build();
        } catch (BusinessException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getExceptionCode()).build();
        }
    }

    @GET
    @Path("/get-notifications-by-user")
    @Consumes({"application/x-www-form-urlencoded"})
    @Produces(MediaType.APPLICATION_JSON)
    public Response getNotificationsForUser(@FormParam("mail") String mail) {
        try {
            List<Notification> notifications = notificationManagementService.getByUserMail(mail);
            String allNotificationsJson = new Gson().toJson(notifications);
            return Response.ok(allNotificationsJson).build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }

    @GET
    @Path("/get-amount-of-notifications")
    @Consumes({"application/x-www-form-urlencoded"})
    public Response getAmountOfUnreadByUser(@FormParam("mail") String mail) {
        try {
            int amount = notificationManagementService.getAmountOfUnreadNotificationsByMail(mail);
            return Response.ok(amount).build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }



}
