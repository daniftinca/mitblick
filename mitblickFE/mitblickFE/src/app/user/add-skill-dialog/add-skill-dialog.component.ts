import {Component, Inject, OnInit} from '@angular/core';
import {MAT_DIALOG_DATA, MatDialogRef} from "@angular/material";
import {ProfileComponent} from "../profile/profile.component";
import {SkillService} from "../../skill-management/skill.service";
import {FormControl, FormGroup, Validators} from "@angular/forms";
import {TranslateService} from "@ngx-translate/core";

export interface SkillDialogData {
  skillid: number;
  skillAreaName: string;
  skillRating: number;
  email: string;
  skillName: string;
  skillEntries: {};
}

@Component({
  selector: 'app-add-skill-dialog',
  templateUrl: './add-skill-dialog.component.html',
  styleUrls: ['./add-skill-dialog.component.scss']
})
export class AddSkillDialogComponent implements OnInit {
  private skillEntries: any;
  private numbers: any;

  private skillarea = new FormControl(Validators.requiredTrue);
  private skill = new FormControl(Validators.required);
  private rating = new FormControl(Validators.required);
  private form = new FormGroup({skillarea: this.skillarea, skill: this.skill, rating: this.rating});


  constructor(
    private translate: TranslateService,
    public dialogRef: MatDialogRef<ProfileComponent>,
    @Inject(MAT_DIALOG_DATA) public data: SkillDialogData,
    private skillService: SkillService) {
  }

  sendResponse() {
    this.dialogRef.close(this.data);
  }

  onNoClick(): void {
    this.data = undefined;
    this.dialogRef.close();
  }

  ngOnInit(): void {
    // @ts-ignore
    this.numbers = Array(5).fill().map((x, i) => i + 1);
    this.skillService.getAllSkillAreas().subscribe(res => this.skillEntries = res);
  }

  getSkills(area) {
    var skills = [];
    for (var id in this.skillEntries) {
      // @ts-ignore
      if (this.skillEntries[id].name == area) {

        for (var j in this.skillEntries[id].skills) {
          if (this.filterSkills(this.skillEntries[id].skills[j])) {
            skills.push(this.skillEntries[id].skills[j]);
          }
        }

      }
    }
    return skills;
  }

  filterSkills(skill) {
    for (var i in this.data.skillEntries) {
      for (var j in this.data.skillEntries[i]) {
        if (this.data.skillEntries[i][j].id == skill.id) {
          return false;
        }
      }
    }
    return true;
  }

  setSkillName(id) {
    for (var i in this.skillEntries) {
      // @ts-ignore
      for (var j in this.skillEntries[i].skills) {
        if (this.skillEntries[i].skills[j].id == id) {
          this.data.skillName = this.skillEntries[i].skills[j].name;
        }
      }
    }
  }

}
