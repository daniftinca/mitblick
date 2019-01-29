import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {ProfileManagementComponent} from './profile-management/profile-management.component';
import {
  MatButtonModule,
  MatCardModule,
  MatFormFieldModule,
  MatInputModule,
  MatPaginatorModule
} from "@angular/material";
import {FormsModule} from "@angular/forms";

@NgModule({
  declarations: [ProfileManagementComponent],
  imports: [
    CommonModule,
    MatPaginatorModule,
    MatCardModule,
    FormsModule,
    MatFormFieldModule,
    MatInputModule,
    MatButtonModule
  ]
})
export class ProfileManagementModule {
}
