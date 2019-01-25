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
