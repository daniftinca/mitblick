import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {TranslateContentComponent} from './translate-content/translate-content.component';
import {MatButtonModule, MatMenuModule} from "@angular/material";
import {TranslationService} from "./translation.service";

@NgModule({
  declarations: [TranslateContentComponent],
  imports: [
    CommonModule,
    MatMenuModule,
    MatButtonModule,
  ],
  exports: [TranslateContentComponent],
  providers: [TranslationService]
})

export class TranslationModule {
}
