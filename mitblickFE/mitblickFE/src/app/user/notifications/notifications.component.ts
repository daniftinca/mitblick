import {Component, OnInit} from '@angular/core';
import {NotificationsService} from "../notifications.service";

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

  constructor(private notifService: NotificationsService) {
  }

  amountOfUnread() {
    this.unread = this.notifService.getAmountOfNotifs();
  }

  ngOnInit() {
  }

}
