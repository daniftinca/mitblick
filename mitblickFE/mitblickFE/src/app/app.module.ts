import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';

import {AppRoutingModule} from './app-routing.module';
import {AppComponent} from './app.component';
import {NavigationModule} from "./navigation/navigation.module";
import {BrowserAnimationsModule} from "@angular/platform-browser/animations";
import {MatButtonModule, MatCheckboxModule} from "@angular/material";
import {Routes} from "@angular/router";
import {UserModule} from "./user/user.module";

const appRoutes: Routes = [
  {
    path: '', pathMatch: 'full', redirectTo: '/login'
  },

];
@NgModule({
  declarations: [
    AppComponent
  ],
  imports: [
    BrowserModule,
    BrowserAnimationsModule,
    MatButtonModule,
    MatCheckboxModule,
    AppRoutingModule,
    NavigationModule,
    UserModule

  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule {
}
