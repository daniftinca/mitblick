import {BrowserModule, DomSanitizer} from '@angular/platform-browser';
import {NgModule} from '@angular/core';

import {AppRoutingModule} from './app-routing.module';
import {AppComponent} from './app.component';
import {NavigationModule} from "./navigation/navigation.module";
import {BrowserAnimationsModule} from "@angular/platform-browser/animations";
import {MatButtonModule, MatCheckboxModule, MatIconRegistry} from "@angular/material";
import {RouterModule, Routes} from "@angular/router";
import {UserModule} from "./user/user.module";
import {HttpClient} from "@angular/common/http";
import {TranslateHttpLoader} from "@ngx-translate/http-loader";
import {TranslateLoader, TranslateModule} from "@ngx-translate/core";

const appRoutes: Routes = [
  {
    path: '', pathMatch: 'full', redirectTo: '/login'
  },

];

export function HttpLoaderFactory(http: HttpClient) {

  return new TranslateHttpLoader(http, './assets/i18n/', '.json');

}

@NgModule({
  declarations: [
    AppComponent
  ],
  imports: [
    TranslateModule.forRoot({

      loader: {

        provide: TranslateLoader,

        useFactory: HttpLoaderFactory,

        deps: [HttpClient]

      }

    }),
    BrowserModule,
    BrowserAnimationsModule,
    MatButtonModule,
    MatCheckboxModule,
    AppRoutingModule,
    NavigationModule,
    UserModule,
    RouterModule.forRoot(appRoutes),

  ],
  providers: [],

  bootstrap: [AppComponent]
})
export class AppModule {
  constructor(private iconStuff: MatIconRegistry, private domSanitizer: DomSanitizer){
    this.iconStuff.addSvgIcon('email',
      domSanitizer.bypassSecurityTrustResourceUrl('/assets/baseline-email-24px.svg'));
    this.iconStuff.addSvgIcon('shield',
      domSanitizer.bypassSecurityTrustResourceUrl('/assets/baseline-security-24px.svg'))

  }
}
