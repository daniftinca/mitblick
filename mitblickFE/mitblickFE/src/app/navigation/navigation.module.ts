import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {NavbarComponent} from './navbar/navbar.component';
import {MatButtonModule, MatMenuModule, MatToolbarModule} from "@angular/material";
import {UserModule} from "../user/user.module";
import {RouterModule, Routes} from "@angular/router";
import {LoginComponent} from "../user/login/login.component";
import {ProfileComponent} from "../user/profile/profile.component";

const loginRoutes: Routes = [
  {path: 'login', component: LoginComponent},
  {path: 'profile', component: ProfileComponent},
];
@NgModule({
  declarations: [NavbarComponent],
  exports: [
    NavbarComponent,
    MatToolbarModule,
    MatButtonModule,
    MatMenuModule,
    UserModule,
  ],
  imports: [
    CommonModule,
    MatToolbarModule,
    MatButtonModule,
    MatMenuModule,
    RouterModule.forChild(loginRoutes)
  ]
})
export class NavigationModule {
}
