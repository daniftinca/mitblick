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

  unread = 2;

  constructor(private authService: AuthenticationService,
              private router: Router,
              private notifService: NotificationsService,
              private translate: TranslateService) {
  }


  amountOfUnread() {
    this.unread = this.notifService.getAmountOfNotifs();
  }

  isLoggedIn(){
    return this.authService.isLoggedIn();
  }

  public hasPermission(perm) {
    return this.authService.userHasPermission(perm);
  }

  ngOnInit() {
    this.amountOfUnread();
  }

  logout() {
    localStorage.clear();
    this.router.navigate(['/login']);
  }

}
