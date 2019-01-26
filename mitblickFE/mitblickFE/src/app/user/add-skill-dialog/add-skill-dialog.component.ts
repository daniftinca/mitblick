import {Component, Inject, OnInit} from '@angular/core';
import {MAT_DIALOG_DATA, MatDialogRef} from "@angular/material";
import {ProfileComponent} from "../profile/profile.component";
import {SkillService} from "../../skill-management/skill.service";

export interface SkillDialogData {
  skillid: number;
  skillAreaName: string;
  skillRating: number;
  email: string;
  skillName: string;
}

@Component({
  selector: 'app-add-skill-dialog',
  templateUrl: './add-skill-dialog.component.html',
  styleUrls: ['./add-skill-dialog.component.scss']
})
export class AddSkillDialogComponent implements OnInit {
  private skillEntries: any;
  private numbers: any;

  constructor(
    public dialogRef: MatDialogRef<ProfileComponent>,
    @Inject(MAT_DIALOG_DATA) public data: SkillDialogData,
    private skillService: SkillService) {
  }

  onNoClick(): void {
    this.dialogRef.close();
  }

  ngOnInit(): void {
    // @ts-ignore
    this.numbers = Array(5).fill().map((x, i) => i + 1);
    this.skillService.getAllSkillAreas().subscribe(res => this.skillEntries = res);
  }

  getSkills(area) {
    for (var id in this.skillEntries) {
      // @ts-ignore
      if (this.skillEntries[id].name == area) {
        // @ts-ignore
        return this.skillEntries[id].skills;
      }
    }
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
