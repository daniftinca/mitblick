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

@Path("/manage-notifications")
public class NotificationManagementBoundary {

    @EJB
    private NotificationManagementService notificationManagementService;


    /**
     * Add a notification.
     *
     * @param notification The notification that has to be added.
     * @return CREATED
     */
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/add-notification")
    public Response createNotification(final Notification notification) {
        notificationManagementService.createNotification(notification);
        return Response.status(Response.Status.CREATED).build();
    }

    /**
     * Delete a notification by id.
     *
     * @param notificationId Id of the notification that has to be deleted.
     * @return OK | BAD_REQUEST
     */
    @POST
    @Path("/delete-notification")
    @Consumes({"application/x-www-form-urlencoded"})
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteNotification(@FormParam("notificationId") Long notificationId) {

        try {

            notificationManagementService.deleteNotification(notificationId);
            return Response.status(Response.Status.OK).build();
        } catch (BusinessException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getExceptionCode()).build();
        }
    }

    /**
     * Mark a notification as read.
     *
     * @param notificationId The id of the notification.
     * @return OK | BAD_REQUEST
     */
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

    /**
     * Mark a notification as unread.
     *
     * @param notificationId The id of the notification.
     * @return OK | BAD_REQUEST
     */
    @POST
    @Path("/mark-notification-as-unread")
    @Consumes({"application/x-www-form-urlencoded"})
    @Produces(MediaType.APPLICATION_JSON)
    public Response markNotificationAsUnread(@FormParam("notificationId") Long notificationId) {

        try {
            notificationManagementService.markNotificationAsUnread(notificationId);
            return Response.status(Response.Status.OK).build();
        } catch (BusinessException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getExceptionCode()).build();
        }
    }

    /**
     * Mark all notifications as read.
     *
     * @param userMail Email of the user whose notifications should be marked.
     * @return OK | BAD_REQUEST
     */
    @POST
    @Path("/mark-all-as-read")
    @Consumes({"application/x-www-form-urlencoded"})
    @Produces(MediaType.APPLICATION_JSON)
    public Response markAllAsRead(@FormParam("userMail") String userMail) {

        try {

            notificationManagementService.markAllNotificationsAsRead(userMail);
            return Response.status(Response.Status.OK).build();
        } catch (BusinessException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getExceptionCode()).build();
        }
    }

    /**
     * Get all notification for a specific user.
     *
     * @param mail Email of the user.
     * @return Notifications as Json | BAD_REQUEST
     */
    @GET
    @Path("/get-notifications-by-user")
    @Consumes({"application/x-www-form-urlencoded"})
    @Produces(MediaType.APPLICATION_JSON)
    public Response getNotificationsForUser(@QueryParam("email") String mail) {
        try {
            List<Notification> notifications = notificationManagementService.getByUserMail(mail);
            String allNotificationsJson = new Gson().toJson(notifications);
            return Response.ok(allNotificationsJson).build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }

    /**
     * Get the amount of unread notifications for a user.
     *
     * @param mail Email of the user.
     * @return Amount | BAD_REQUEST
     */
    @GET
    @Path("/get-amount-of-notifications")
    @Consumes({"application/x-www-form-urlencoded"})
    public Response getAmountOfUnreadByUser(@QueryParam("email") String mail) {
        try {
            int amount = notificationManagementService.getAmountOfUnreadNotificationsByMail(mail);
            return Response.ok(amount).build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }




}
