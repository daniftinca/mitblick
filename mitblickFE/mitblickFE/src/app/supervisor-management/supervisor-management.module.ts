import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {SupervisorViewComponent} from './supervisor-view/supervisor-view.component';
import {MatButtonModule, MatIconModule, MatListModule, MatTableModule, MatTabsModule} from "@angular/material";
import {ReactiveFormsModule} from "@angular/forms";
import {ProfileDialogComponent} from './profile-dialog/profile-dialog.component';

@NgModule({
  declarations: [SupervisorViewComponent, ProfileDialogComponent],
  imports: [
    CommonModule,
    MatTableModule,
    ReactiveFormsModule,
    MatIconModule,
    MatTabsModule,
    MatListModule,
    MatButtonModule
  ]
})
export class SupervisorManagementModule {
}
