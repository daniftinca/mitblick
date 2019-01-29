import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {TranslateContentComponent} from './translate-content/translate-content.component';
import {MatButtonModule, MatMenuModule} from "@angular/material";
import {TranslationService} from "./translation.service";
import {TranslateModule} from "@ngx-translate/core";

@NgModule({
  declarations: [TranslateContentComponent],
  imports: [
    // TranslateModule.forChild({
    //
    //   loader: {
    //
    //     provide: TranslateLoader,
    //
    //     useFactory: HttpLoaderFactory,
    //
    //     deps: [HttpClient]
    //
    //   }
    //
    // }),
    CommonModule,
    MatMenuModule,
    MatButtonModule,
    TranslateModule,
  ],
  exports: [TranslateContentComponent, TranslateModule],
  providers: [TranslationService]
})

export class TranslationModule {
}
