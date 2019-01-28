import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {ManageAllUsersComponent} from './manage-all-users/manage-all-users.component';
import {UserModule} from "../user/user.module";
import {HttpModule} from "@angular/http";
import {HttpClientModule} from "@angular/common/http";
import {
  MatButtonModule,
  MatChipsModule,
  MatDialogModule,
  MatFormFieldModule,
  MatIconModule,
  MatInputModule,
  MatListModule,
  MatSelectModule,
  MatTableModule,
  MatTabsModule
} from "@angular/material";
import {RegisterUserComponent} from './register-user/register-user.component';
import {UpdateUserComponent} from './update-user/update-user.component';
import {DeactivationPopupComponent} from './deactivation-popup/deactivation-popup.component';
import {FormsModule, ReactiveFormsModule} from "@angular/forms";

@NgModule({
  declarations: [ManageAllUsersComponent, RegisterUserComponent, UpdateUserComponent, DeactivationPopupComponent],
  imports: [
    CommonModule,
    UserModule,
    HttpModule,
    HttpClientModule,
    MatTableModule,
    MatButtonModule,
    MatDialogModule,
    MatChipsModule,
    FormsModule,
    MatFormFieldModule,
    MatInputModule,
    ReactiveFormsModule,
    MatIconModule,
    MatTabsModule,
    MatListModule,
    MatSelectModule
  ],
  exports: [ManageAllUsersComponent],
  entryComponents: [
    RegisterUserComponent,
    UpdateUserComponent,
    DeactivationPopupComponent
  ]
})
export class UserManagementModule { }
