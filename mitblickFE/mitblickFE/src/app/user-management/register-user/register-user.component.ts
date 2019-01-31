import {Component, Inject, OnInit} from '@angular/core';
import {MAT_DIALOG_DATA, MatDialogRef} from "@angular/material";
import {ManageAllUsersComponent} from "../manage-all-users/manage-all-users.component";
import {UserData} from "../../user/authentication.service";
import {UsermanagementService} from "../usermanagement.service";
import {MyErrorStateMatcher} from "../update-user/update-user.component";
import {FormControl, Validators} from "@angular/forms";

@Component({
  selector: 'app-register-user',
  templateUrl: './register-user.component.html',
  styleUrls: ['./register-user.component.scss']
})
export class RegisterUserComponent implements OnInit {


  constructor(

    public dialogRef: MatDialogRef<ManageAllUsersComponent>,
    @Inject(MAT_DIALOG_DATA) public data: UserData,
    public usermgmt: UsermanagementService) {
  }

  matcher = new MyErrorStateMatcher();
  passwordFormControl = new FormControl('', [
    Validators.required,
    this.validatePassword
  ]);

  emailFormControl = new FormControl('', [
    Validators.email,
    Validators.required,
    this.validateEmail
  ]);

  validatePassword(control: FormControl) {
    const regex = new RegExp('^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#?!@$%^&*.-]).{6,}$');

    if (regex.test(control.value)) {
      return null;
    }
    return {
      passwordInvalid: {
        password: control.value
      }
    }
  }

  validateEmail(control: FormControl) {
    const regex = new RegExp('^[A-Za-z0-9._%+-]+@[a-zA-z]+.com$');
    if (regex.test(control.value)) {
      return null;
    }
    return {
      emaildomainerror: {
        email: control.value
      }
    }
  }

  submitRegister() {
    this.usermgmt.registerUser(this.data).subscribe(
      data => {

        this.dialogRef.close();
      }, error => {

      }
    );

  }

  passwordErrorMessage: string;
  emailErrorMessage: string;

  getPasswordErrorMessage() {
    this.passwordFormControl.hasError('required') ? (this.passwordErrorMessage = "Password is required.") :
      this.passwordFormControl.hasError('passwordInvalid') ? (this.passwordErrorMessage = "Password is not strong enough") :
        '';

    return this.passwordErrorMessage;
  }

  getEmailErrorMessages(){

    this.emailFormControl.hasError('required') ? (this.emailErrorMessage = "Email is required") :
      this.emailFormControl.hasError('email') ? (this.emailErrorMessage = "Email is not valid") :
          '';
    return this.emailErrorMessage;

  }


  onNoClick(): void {

    this.dialogRef.close();
  }

  ngOnInit() {
  }

}
