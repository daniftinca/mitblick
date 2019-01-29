import {Component, OnInit} from '@angular/core';
import {AuthenticationService, UserData, UserLoginData} from "../authentication.service";
import {Router} from "@angular/router";
import {TranslateService} from "@ngx-translate/core";

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent implements OnInit {

  userLoginData: UserLoginData;
  userData: UserData;
  error: boolean;
  errorMessage: string;


  constructor(private authenticationService: AuthenticationService, private router: Router, private translate: TranslateService) {
    this.userLoginData = {
      email: '',
      password: '',
    };
    this.userData = {
      email: '',

    };
    this.error = false;
    this.errorMessage = '';


  }

  displayError() {
    return this.error;
  }

  getMessage(){
    return this.errorMessage;
  }

  submitForm() {
    console.log("Component here...");
    this.authenticationService.validateUser(this.userLoginData.email, this.userLoginData.password)
      .subscribe(
        data => {
          this.error = false;
          this.router.navigate([`/profile`]);
        },
        err => {
          this.error = true;
          if(err.valueOf().error.value=='USER_DEACTIVATED') {
            this.errorMessage = "This user was deactivated";
          } else{
            this.errorMessage = "Username or password are incorrect."
          }
        }
      );
  }

  isLoggedIn() {
    return this.authenticationService.isLoggedIn();
  }

  logout() {
    this.authenticationService.logout();
  }

  ngOnInit() {
  }

}
