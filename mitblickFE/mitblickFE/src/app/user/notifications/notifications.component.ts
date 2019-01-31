import {Component, OnInit} from '@angular/core';
import {NotificationsService} from "../notifications.service";
import {NavbarComponent} from "../../navigation/navbar/navbar.component";
import {ProfileDialogComponent} from "../../supervisor-management/profile-dialog/profile-dialog.component";
import {MatDialog} from "@angular/material";
import {ProfileService} from "../profile.service";
import {SupervisorViewService} from "../../supervisor-management/supervisor-view.service";
import {TranslateService} from "@ngx-translate/core";

@Component({
  selector: 'app-notifications',
  templateUrl: './notifications.component.html',
  styleUrls: ['./notifications.component.scss']
})
export class NotificationsComponent implements OnInit {

  unread;
  notificationData = [{
    profileName: "Example Exmaple",
    profileEmail: "example@example.com",
    notificationDate: (new Date(Date.now())).toLocaleDateString(),
    notificationTitle: "Title Example",
    read: false

  }, {
    profileName: "Example Exmaple",
    profileEmail: "example@example.com",
    notificationDate: (new Date(Date.now())).toLocaleDateString(),
    notificationTitle: "Title Example",
    read: true

  }];
  userProfiles = [{}];

  constructor(private translate: TranslateService,
              private notifService: NotificationsService, public dialog: MatDialog,
              private profileService: ProfileService, private supervisorService: SupervisorViewService) {
  }

  amountOfUnread() {
    // @ts-ignore
    this.notifService.getAmountOfNotifs(localStorage.getItem("email")).subscribe(res => NavbarComponent.unread = res);
  }

  getNotificationsByUser() {
    // @ts-ignore
    this.notifService.getNotifications(localStorage.getItem("email")).subscribe(res => this.notificationData = res);
  }

  ngOnInit() {
    this.amountOfUnread();
    this.getNotificationsByUser();
  }

  markNotificationAsRead(notification) {
    this.notifService.markNotificationAsRead(notification.id).subscribe(_ => {
      this.amountOfUnread();
      this.getNotificationsByUser();
    });
  }

  markNotification(notification) {
    if (!notification.isRead) {
      this.notifService.markNotificationAsRead(notification.id).subscribe(_ => {
        this.amountOfUnread();
        this.getNotificationsByUser();
      });
    } else {
      this.notifService.markNotificationAsUnread(notification.id).subscribe(_ => {
        this.amountOfUnread();
        this.getNotificationsByUser();
      });
    }

  }

  showProfile(notification: any) {
    this.markNotificationAsRead(notification);
    var email = notification.userMail;
    this.profileService.getProfileByEmail(email).subscribe(profile => {
      const dialogRef = this.dialog.open(ProfileDialogComponent, {
        width: '750px',
        maxHeight: '950px',
        data: profile,
        panelClass: 'actions-hide'
      });
    });

  }

  accept(notification) {
    this.markNotificationAsRead(notification);
    this.supervisorService.acceptProfile(localStorage.getItem("email"), notification.userMail).subscribe();
    this.notifService.delete(notification.id).subscribe(_ => {
      this.amountOfUnread();
      this.getNotificationsByUser();
    });
  }

  decline(notification) {
    this.markNotificationAsRead(notification);

    this.supervisorService.declineProfile(localStorage.getItem("email"), notification.userMail).subscribe();
    this.notifService.delete(notification.id).subscribe(_ => {
      this.amountOfUnread();
      this.getNotificationsByUser();
    });
  }
}
