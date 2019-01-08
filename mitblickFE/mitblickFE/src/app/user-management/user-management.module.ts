import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {ManageAllUsersComponent} from './manage-all-users/manage-all-users.component';
import {UserModule} from "../user/user.module";
import {HttpModule} from "@angular/http";
import {HttpClientModule} from "@angular/common/http";
import {MatButtonModule, MatDialogModule, MatTableModule} from "@angular/material";
import {RegisterUserComponent} from './register-user/register-user.component';
import {UpdateUserComponent} from './update-user/update-user.component';
import {DeactivationPopupComponent} from './deactivation-popup/deactivation-popup.component';

@NgModule({
  declarations: [ManageAllUsersComponent, RegisterUserComponent, UpdateUserComponent, DeactivationPopupComponent],
  imports: [
    CommonModule,
    UserModule,
    HttpModule,
    HttpClientModule,
    MatTableModule,
    MatButtonModule,
    MatDialogModule
  ],
  exports:[ManageAllUsersComponent]
})
export class UserManagementModule { }
