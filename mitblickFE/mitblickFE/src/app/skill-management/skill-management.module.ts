import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {SkillManagementViewComponent} from './skill-management-view/skill-management-view.component';
import {MatExpansionModule, MatListModule} from "@angular/material";
import {AddSkillAreaComponent} from './add-skill-area/add-skill-area.component';
import {AddSkillComponent} from './add-skill/add-skill.component';

@NgModule({
  declarations: [SkillManagementViewComponent, AddSkillAreaComponent, AddSkillComponent],
  imports: [
    CommonModule,
    MatExpansionModule,
    MatListModule

  ],
  exports: [
    SkillManagementViewComponent
  ]
})
export class SkillManagementModule {
}
