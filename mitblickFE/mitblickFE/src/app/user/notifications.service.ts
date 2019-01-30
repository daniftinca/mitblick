import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders} from "@angular/common/http";


@Injectable({
  providedIn: 'root'
})
export class NotificationsService {

  baseURL = 'http://localhost:8080/mitblick/rest';

  constructor(private http: HttpClient) {
  }

  getAmountOfNotifs(email) {
    return this.http.get(this.baseURL + "/manage-notifications/get-amount-of-notifications?email=" + email);
  }

  getNotifications(email: string) {
    return this.http.get(this.baseURL + "/manage-notifications/get-notifications-by-user?email=" + email);
  }

  markNotificationAsRead(id) {
    let body = new URLSearchParams();
    body.set("notificationId", id);
    return this.http.post(this.baseURL + "/manage-notifications/mark-notification-as-read",
      body.toString(),
      {
        headers: new HttpHeaders(
          {'Content-Type': 'application/x-www-form-urlencoded'}
        )
      });
  }

  markNotificationAsUnread(id) {
    let body = new URLSearchParams();
    body.set("notificationId", id);
    return this.http.post(this.baseURL + "/manage-notifications/mark-notification-as-unread",
      body.toString(),
      {
        headers: new HttpHeaders(
          {'Content-Type': 'application/x-www-form-urlencoded'}
        )
      });
  }

  delete(id) {
    let body = new URLSearchParams();
    body.set("notificationId", id);
    return this.http.post(this.baseURL + "/manage-notifications/delete-notification",
      body.toString(),
      {
        headers: new HttpHeaders(
          {'Content-Type': 'application/x-www-form-urlencoded'}
        )
      });
  }
}
