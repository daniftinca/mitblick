import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {ProfileManagementComponent} from './profile-management/profile-management.component';
import {
  MatButtonModule,
  MatCardModule,
  MatFormFieldModule,
  MatInputModule,
  MatPaginatorModule
} from "@angular/material";
import {FormsModule} from "@angular/forms";
import {HttpLoaderFactory} from "../app.module";
import {TranslateLoader, TranslateModule} from "@ngx-translate/core";
import {HttpClient} from "@angular/common/http";

@NgModule({
  declarations: [ProfileManagementComponent],
  imports: [
    CommonModule,
    MatPaginatorModule,
    MatCardModule,
    MatFormFieldModule,
    FormsModule,
    MatInputModule,
    MatButtonModule,
    TranslateModule.forChild({

      loader: {

        provide: TranslateLoader,

        useFactory: HttpLoaderFactory,

        deps: [HttpClient]

      }
    })
  ]
})
export class ProfileManagementModule {
}
