import {Component, OnInit} from '@angular/core';
import {SkillService} from "../skill.service";
import {MatDialog} from "@angular/material";
import {AddSkillAreaComponent} from "../add-skill-area/add-skill-area.component";
import {AddSkillComponent} from "../add-skill/add-skill.component";

@Component({
  selector: 'app-skill-management-view',
  templateUrl: './skill-management-view.component.html',
  styleUrls: ['./skill-management-view.component.scss']
})
export class SkillManagementViewComponent implements OnInit {

  private skillAreas: any;

  constructor(private skillManagement: SkillService,
              public dialog: MatDialog) {


  }

  getSkillAreas() {
    this.skillManagement.getAllSkillAreas()
      .subscribe(
        data => {
          this.skillAreas = data;
          console.log(data);
          console.log("SkillAreas: " + this.skillAreas);
        }
      );
  }

  addSkillArea(name, description) {
    let skillAreaData = {
      "name": name,
      "description": description,
    };
    this.skillManagement.addSkillArea(skillAreaData)
      .subscribe(
        data => {
          console.log("Works");
        }, error => {
          console.log("Not works");
        }
      );
  }

  openAddSkillAreaDialog(): void {
    const dialogRef = this.dialog.open(AddSkillAreaComponent, {
      width: '60%',
      data: {}
    });

    dialogRef.afterClosed().subscribe(result => {

      this.getSkillAreas();
    });
  }

  openAddSkillDialog(skillAreaName): void {
    const dialogRef = this.dialog.open(AddSkillComponent, {
      width: '60%',
      data: {
        skillAreaName: skillAreaName
      }
    });

    dialogRef.afterClosed().subscribe(result => {

      this.getSkillAreas();
    });
  }

  deleteSkillArea(name, description) {
    let skillAreaData = {
      "name": name,
      "description": description,
    };
    this.skillManagement.removeSkillArea(skillAreaData).subscribe(
      data => {
        this.getSkillAreas();
      }
    );
  }

  ngOnInit() {
    this.getSkillAreas();
  }
}
