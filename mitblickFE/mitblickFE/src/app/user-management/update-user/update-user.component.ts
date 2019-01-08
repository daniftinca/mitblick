import {Component, Inject, OnInit} from '@angular/core';
import {MAT_DIALOG_DATA, MatDialogRef} from "@angular/material";
import {ManageAllUsersComponent} from "../manage-all-users/manage-all-users.component";
import {UsermanagementService} from "../usermanagement.service";

@Component({
  selector: 'app-update-user',
  templateUrl: './update-user.component.html',
  styleUrls: ['./update-user.component.scss']
})
export class UpdateUserComponent implements OnInit {

  constructor(

    public dialogRef: MatDialogRef<ManageAllUsersComponent>,
    @Inject(MAT_DIALOG_DATA) public data,
    public usermgmt: UsermanagementService,
    //public permissionmngmt: PermissionManagementService
  ) {
  }

 /* matcher = {}; //new MyErrorStateMatcher();
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

    if (control.value === null ||
      control.value === undefined ||
      control.value === '') {
      return null;
    }
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
    const regex = new RegExp('^[A-Za-z0-9._%+-]+@msggroup.com$');
    if (regex.test(control.value)) {
      return null;
    }
    return {
      emaildomainerror: {
        emai: control.value
      }
    }
  }

  submitUpdate() {
    if (this.data.password === null ||
      this.data.password === undefined ||
      this.data.password === '') {
      delete this.data.password;
    }
    this.usermgmt.updateUser(this.data).subscribe(
      data => {
        this.dialogRef.close();
      }
    );

  }


  passwordErrorMessage: string;
  emailErrorMessage: string;

  getPasswordErrorMessage() {
    this.passwordFormControl.hasError('required') ? (this.passwordErrorMessage="Password is required.") :
      this.passwordFormControl.hasError('passwordInvalid') ? (this.passwordErrorMessage="Password is invalid.") :
        '';

    return this.passwordErrorMessage;
  }

  getEmailErrorMessages() {
    this.emailFormControl.hasError('email') ? (this.emailErrorMessage="Invalid email") :
      this.emailFormControl.hasError('emaildomainerror') ? (this.emailErrorMessage="Invalid domain") :
        '';
    return this.emailErrorMessage;

  }


  onNoClick(): void {
    this.dialogRef.close();
  }


  userRoles;
  allRoles;
  selectedrole;

  getUserRoles() {
    this.usermgmt.getRolesOfUser(this.data.username)
      .subscribe(roles => {
        this.userRoles = roles;
      })
  }

  getAllRoles() {
    this.permissionmngmt.getAllRoles()
      .subscribe(roles => {
        this.allRoles = roles;

      });
  }

  addRoleToUser() {
    this.usermgmt.addRoleToUser(this.data.username, this.selectedrole)
      .subscribe(() => {
        this.getUserRoles();
      });
  }

  removeRoleFromUser() {
    this.usermgmt.revokeRoleFromUser(this.data.username, this.selectedrole)
      .subscribe(() => {
        this.getUserRoles();
      });
  }

*/
  ngOnInit() {
    // this.getUserRoles();
    // this.getAllRoles();
  }

}
