import {Component, OnInit} from '@angular/core';
import {AuthenticationService} from "../../user/authentication.service";
import {Router} from "@angular/router";

@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.scss']
})
export class NavbarComponent implements OnInit {

  constructor(private authService: AuthenticationService, private router: Router) {
  }

  isLoggedIn(){
    return this.authService.isLoggedIn();
  }

  public hasPermission(perm) {
    return this.authService.userHasPermission(perm);
  }

  ngOnInit() {
  }

  logout() {
    localStorage.clear();
    this.router.navigate(['/login']);
  }

}
