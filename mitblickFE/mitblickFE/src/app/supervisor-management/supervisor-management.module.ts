import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {SupervisorViewComponent} from './supervisor-view/supervisor-view.component';
import {
  MatButtonModule,
  MatCardModule,
  MatChipsModule,
  MatDatepickerModule,
  MatDialogModule,
  MatExpansionModule,
  MatFormFieldModule,
  MatIconModule,
  MatInputModule,
  MatListModule,
  MatNativeDateModule,
  MatSelectModule,
  MatSnackBarModule,
  MatTableModule,
  MatTabsModule
} from "@angular/material";
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import {ProfileDialogComponent} from './profile-dialog/profile-dialog.component';
import {HttpModule} from "@angular/http";
import {HttpClient, HttpClientModule} from "@angular/common/http";
import {HttpLoaderFactory} from "../app.module";
import {TranslateLoader, TranslateModule} from "@ngx-translate/core";

@NgModule({
  declarations: [SupervisorViewComponent, ProfileDialogComponent],
  imports: [
    CommonModule,
    MatTableModule,
    ReactiveFormsModule,
    MatIconModule,
    MatTabsModule,
    MatListModule,
    MatButtonModule,
    MatDialogModule, CommonModule,
    MatFormFieldModule,
    MatInputModule,
    MatButtonModule,
    MatSnackBarModule,
    HttpModule,
    HttpClientModule,
    MatIconModule,
    FormsModule,
    MatCardModule,
    MatListModule,
    MatChipsModule,
    MatExpansionModule,
    MatDatepickerModule,
    MatNativeDateModule,
    MatButtonModule,
    MatDialogModule,
    MatSelectModule,
    ReactiveFormsModule,
    TranslateModule.forChild({

      loader: {

        provide: TranslateLoader,

        useFactory: HttpLoaderFactory,

        deps: [HttpClient]

      }
    })
  ],
  entryComponents: [ProfileDialogComponent]
})
export class SupervisorManagementModule {
}
