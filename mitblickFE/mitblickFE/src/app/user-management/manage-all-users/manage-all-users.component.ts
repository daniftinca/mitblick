import {Component, OnInit} from '@angular/core';
import {UsermanagementService} from "../usermanagement.service";
import {MatDialog} from "@angular/material";
import {DeactivationPopupComponent} from "../deactivation-popup/deactivation-popup.component";
import {RegisterUserComponent} from "../register-user/register-user.component";
import {UpdateUserComponent} from "../update-user/update-user.component";
import {TranslateService} from "@ngx-translate/core";

@Component({
  selector: 'app-manage-all-users',
  templateUrl: './manage-all-users.component.html',
  styleUrls: ['./manage-all-users.component.scss']
})
export class ManageAllUsersComponent implements OnInit {

  userData;
  button: string;
  dataSource: any;

  constructor(private translate: TranslateService,
              private usrMgmtService: UsermanagementService,
              public dialog: MatDialog) {

  }

  openRegisterDialog(): void {
    const dialogRef = this.dialog.open(RegisterUserComponent, {
      width: '60%',
      data: {
        email: this.userData.email,

      }
    });

    dialogRef.afterClosed().subscribe(result => {

      this.getUsers();
    });
  }

  openUpdateDialog(user): void {
    const dialogRef = this.dialog.open(UpdateUserComponent, {
      width: '60%',
      data: {
        active: user.isActive,
        email: user.email,
        supervisorEmail: user.supervisorEmail,
        password: user.password,

      }
    });

    dialogRef.afterClosed().subscribe(result => {

      this.getUsers();
    });
  }

  getUsers() {
    this.usrMgmtService.getAllUsers()
      .subscribe(
        data => {
          this.dataSource = data;
        }
      )
  }



  getActivationButtonText(isActive) {
    if (isActive) {

      return "Deactivate";

    } else {

      return "Activate"

    }
  }

  activateUser(email) {
    this.usrMgmtService.activateUser(email)
      .subscribe(
        data => {
          this.getUsers();
        }
      );
  }

  openDeactivationErrorDialog(user): void {
    const dialogRef = this.dialog.open(DeactivationPopupComponent, {
      width: '300px',
      data: {
        email: user.email
      }
    });
  }


  deactivateUser(email) {
    this.usrMgmtService.deactivateUser(email)
      .subscribe(
        data => {
          this.getUsers();
        },
        error1 => {
          if (error1.status == 302) {
            this.openDeactivationErrorDialog(email);
          }
        }
      );
  }

  toggleActivation(isActive, email) {
    if (isActive) {
      this.deactivateUser(email);
    } else {
      this.activateUser(email);
    }
  }

  displayedColumns: string[] = [
    'email',
    'activation',
    'edit'
  ];


  ngOnInit() {
    this.getUsers();
    this.userData = {
      email: "",
      password: ""
    }
  }

}
