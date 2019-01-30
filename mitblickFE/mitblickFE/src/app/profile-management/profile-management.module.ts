import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {ProfileManagementComponent} from './profile-management/profile-management.component';
import {
  MatButtonModule,
  MatCardModule,
  MatCheckboxModule,
  MatExpansionModule,
  MatFormFieldModule,
  MatInputModule,
  MatListModule,
  MatPaginatorModule
} from "@angular/material";
import {FormsModule, ReactiveFormsModule} from "@angular/forms";

@NgModule({
  declarations: [ProfileManagementComponent],
  imports: [
    CommonModule,
    MatPaginatorModule,
    MatCardModule,
    FormsModule,
    MatFormFieldModule,
    MatInputModule,
    MatButtonModule,
    MatFormFieldModule,
    ReactiveFormsModule,
    MatExpansionModule,
    MatListModule,
    MatCheckboxModule
  ]
})
export class ProfileManagementModule {
}
