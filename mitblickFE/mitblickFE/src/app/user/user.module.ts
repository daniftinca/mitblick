import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {LoginComponent} from './login/login.component';
import {RouterModule} from "@angular/router";
import {
  MatButtonModule,
  MatCardModule,
  MatFormFieldModule,
  MatIconModule,
  MatInputModule,
  MatSnackBarModule
} from "@angular/material";
import {ProfileComponent} from './profile/profile.component';
import {HTTP_INTERCEPTORS, HttpClientModule} from "@angular/common/http";
import {TokenInterceptorService} from "./token-interceptor.service";
import {HttpModule} from "@angular/http";
import {FormsModule} from "@angular/forms";


@NgModule({
  declarations: [LoginComponent, ProfileComponent],
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
    MatCardModule

  ],
  exports: [LoginComponent, RouterModule],
  providers: [{
    provide: HTTP_INTERCEPTORS,
    useClass: TokenInterceptorService,
    multi: true,
  }]
})
export class UserModule {
}