import {Component, Inject, OnInit} from '@angular/core';
import {MAT_DIALOG_DATA, MatDialogRef} from "@angular/material";
import {SkillService} from "../skill.service";
import {SkillManagementViewComponent} from "../skill-management-view/skill-management-view.component";
import {TranslateService} from "@ngx-translate/core";

@Component({
  selector: 'app-add-skill',
  templateUrl: './add-skill.component.html',
  styleUrls: ['./add-skill.component.scss']
})
export class AddSkillComponent implements OnInit {

  private formData = {
    name: ""
  };

  constructor(private translate: TranslateService,
              private skillManagement: SkillService,
              public dialogRef: MatDialogRef<SkillManagementViewComponent>,
              @Inject(MAT_DIALOG_DATA) public data) {
  }

  submitAddSkill() {
    // let skillData = {
    //   "name": skillName,
    // };
    this.skillManagement.addSkill(this.formData, this.data.skillAreaName)
      .subscribe(data => {
        this.dialogRef.close();
      })
  }

  ngOnInit() {
    console.log(this.dialogRef)
  }

}
