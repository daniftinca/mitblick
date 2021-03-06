import {Component, OnInit} from '@angular/core';
import {AuthenticationService} from "../../user/authentication.service";
import {Router} from "@angular/router";
import {NotificationsService} from "../../user/notifications.service";
import {TranslateService} from "@ngx-translate/core";



@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.scss']
})
export class NavbarComponent implements OnInit {

  public static unread = 0;

  constructor(private authService: AuthenticationService,
              private router: Router,
              private notifService: NotificationsService,
              private translate: TranslateService) {
  }


  static update(service) {
    service.getAmountOfNotifs(localStorage.getItem("email")).subscribe(res => NavbarComponent.unread = res);
  }

  getUnread() {
    return NavbarComponent.unread;
  }


  amountOfUnread() {
    // @ts-ignore
    this.notifService.getAmountOfNotifs(localStorage.getItem("email")).subscribe(res => this.unread = res);
  }

  isLoggedIn(){
    return this.authService.isLoggedIn();
  }

  public hasPermission(perm) {
    return this.authService.userHasPermission(perm);
  }

  ngOnInit() {
    if (localStorage.getItem("email") && this.authService.isLoggedIn()) {
      console.log("I am logged in.");
      this.amountOfUnread();
      var service = this.notifService;
      if (this.hasPermission("PROFILE_REVIEW") && this.authService.isLoggedIn()) {
        setInterval(function () {
            NavbarComponent.update(service);
          }
          , 5000);
      }
    }
  }

  logout() {
    localStorage.clear();
    this.router.navigate(['/login']);
  }

}
