import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {SkillManagementViewComponent} from './skill-management-view/skill-management-view.component';

@NgModule({
  declarations: [SkillManagementViewComponent],
  imports: [
    CommonModule
  ],
  exports: [
    SkillManagementViewComponent
  ]
})
export class SkillManagementModule {
}
