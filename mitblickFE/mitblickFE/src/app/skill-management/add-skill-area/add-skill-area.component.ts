import {Component, OnInit} from '@angular/core';
import {SkillService} from "../skill.service";
import {MatDialogRef} from "@angular/material";
import {SkillManagementViewComponent} from "../skill-management-view/skill-management-view.component";

@Component({
  selector: 'app-add-skill-area',
  templateUrl: './add-skill-area.component.html',
  styleUrls: ['./add-skill-area.component.scss']
})
export class AddSkillAreaComponent implements OnInit {

  data = {
    "name": "",
    "description": ""
  };

  constructor(private skillManagement: SkillService,
              public dialogRef: MatDialogRef<SkillManagementViewComponent>) {
  }


  submitAddSkillArea() {
    this.skillManagement.addSkillArea(this.data)
      .subscribe(
        data => {
          this.dialogRef.close();
        },
        error => {

        }
      );

  }

  ngOnInit() {
    console.log(this.dialogRef)
  }

}
