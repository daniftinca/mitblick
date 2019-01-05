import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {LoginComponent} from './login/login.component';
import {RouterModule} from "@angular/router";
import {MatButtonModule, MatFormFieldModule, MatInputModule} from "@angular/material";


@NgModule({
  declarations: [LoginComponent],
  imports: [
    CommonModule,
    MatFormFieldModule,
    MatInputModule,
    MatButtonModule

  ],
  exports: [LoginComponent, RouterModule]
})
export class UserModule {
}
