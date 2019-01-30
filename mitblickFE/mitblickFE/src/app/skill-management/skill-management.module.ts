import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {SkillManagementViewComponent} from './skill-management-view/skill-management-view.component';
import {
  MatButtonModule,
  MatDialogModule,
  MatExpansionModule,
  MatFormFieldModule,
  MatInputModule,
  MatListModule
} from "@angular/material";
import {AddSkillAreaComponent} from './add-skill-area/add-skill-area.component';
import {AddSkillComponent} from './add-skill/add-skill.component';
import {FormsModule} from "@angular/forms";
import {HttpLoaderFactory} from "../app.module";
import {TranslateLoader, TranslateModule} from "@ngx-translate/core";
import {HttpClient} from "@angular/common/http";

@NgModule({
  declarations: [SkillManagementViewComponent, AddSkillAreaComponent, AddSkillComponent],
  imports: [
    CommonModule,
    MatExpansionModule,
    MatListModule,
    MatDialogModule,
    MatButtonModule,
    FormsModule,
    MatFormFieldModule,
    MatInputModule,
    TranslateModule.forChild({

      loader: {

        provide: TranslateLoader,

        useFactory: HttpLoaderFactory,

        deps: [HttpClient]

      }
    })
  ],
  exports: [
    SkillManagementViewComponent
  ],
  entryComponents: [
    AddSkillAreaComponent,
    AddSkillComponent
  ]
})
export class SkillManagementModule {
}
