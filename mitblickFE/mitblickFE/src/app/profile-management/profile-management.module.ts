import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {ProfileManagementComponent} from './profile-management/profile-management.component';
import {MatCardModule, MatFormFieldModule, MatPaginatorModule} from "@angular/material";

@NgModule({
  declarations: [ProfileManagementComponent],
  imports: [
    CommonModule,
    MatPaginatorModule,
    MatCardModule,
    MatFormFieldModule
  ]
})
export class ProfileManagementModule {
}
