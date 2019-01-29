import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {LoginComponent} from './login/login.component';
import {RouterModule} from "@angular/router";
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
  MatNativeDateModule,
  MatSelectModule,
  MatSnackBarModule
} from "@angular/material";
import {MatListModule} from '@angular/material/list';
import {ProfileComponent} from './profile/profile.component';
import {HTTP_INTERCEPTORS, HttpClient, HttpClientModule} from "@angular/common/http";
import {TokenInterceptorService} from "./token-interceptor.service";
import {HttpModule} from "@angular/http";
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import {AddProjectDialogComponent} from './add-project-dialog/add-project-dialog.component';
import {EditProfileComponent} from './edit-profile/edit-profile.component';
import {NotificationsComponent} from './notifications/notifications.component';
import {AddSkillDialogComponent} from './add-skill-dialog/add-skill-dialog.component';
import {TranslateLoader, TranslateModule} from "@ngx-translate/core";
import {HttpLoaderFactory} from "../app.module";


@NgModule({
  declarations: [LoginComponent, ProfileComponent, AddProjectDialogComponent, EditProfileComponent, NotificationsComponent, AddSkillDialogComponent],
  imports: [
    CommonModule,
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
  exports: [LoginComponent, RouterModule],
  providers: [{
    provide: HTTP_INTERCEPTORS,
    useClass: TokenInterceptorService,
    multi: true,
  }],
  entryComponents: [AddProjectDialogComponent, EditProfileComponent, AddSkillDialogComponent]
})
export class UserModule {
}
