import {Component, Inject, OnInit} from '@angular/core';
import {ErrorStateMatcher, MAT_DIALOG_DATA, MatDialogRef} from "@angular/material";
import {ManageAllUsersComponent} from "../manage-all-users/manage-all-users.component";
import {UsermanagementService} from "../usermanagement.service";
import {FormControl, FormGroup, FormGroupDirective, NgForm, Validators} from "@angular/forms";
import {PermissionmanagementService} from "../permissionmanagement.service";


export class MyErrorStateMatcher implements ErrorStateMatcher {

  constructor() {
  }

  isErrorState(control: FormControl | null, form: FormGroupDirective | NgForm | null) {
    const isSubmitted = form && form.submitted;
    return !!(control && control.invalid && (control.dirty || control.touched || isSubmitted));
  }
}

@Component({
  selector: 'app-update-user',
  templateUrl: './update-user.component.html',
  styleUrls: ['./update-user.component.scss']
})
export class UpdateUserComponent implements OnInit {

  matcher = new MyErrorStateMatcher();

  supervisors;
  private supervisorControl = new FormControl();

  constructor(

    public dialogRef: MatDialogRef<ManageAllUsersComponent>,
    @Inject(MAT_DIALOG_DATA) public data,
    public usermgmt: UsermanagementService,
    public permissionmngmt: PermissionmanagementService
  ) {
  }
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

  private form = new FormGroup({supervisor: this.supervisorControl});


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

  setSupervisor() {
    this.usermgmt.addEmployeeToSupervisor(this.data.email, this.data.supervisor).subscribe(_ => this.dialogRef.close());
  }

  getUserRoles() {
    console.log(this.data);
    this.usermgmt.getRolesOfUser(this.data.email)
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

  getSupervisors() {
    this.usermgmt.getAllSupervisors().subscribe(res => this.supervisors = res)
  }

  addRoleToUser() {
    this.usermgmt.addRoleToUser(this.data.email, this.selectedrole)
      .subscribe(() => {
        this.getUserRoles();
      });
  }

  removeRoleFromUser() {

    this.usermgmt.revokeRoleFromUser(this.data.email, this.selectedrole)
      .subscribe(() => {
        this.getUserRoles();
      });
  }

  ngOnInit() {
     this.getUserRoles();
     this.getAllRoles();
    this.getSupervisors();
  }

}
