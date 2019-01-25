import {Component, OnInit} from '@angular/core';
import {SkillService} from "../skill.service";

@Component({
  selector: 'app-skill-management-view',
  templateUrl: './skill-management-view.component.html',
  styleUrls: ['./skill-management-view.component.scss']
})
export class SkillManagementViewComponent implements OnInit {

  private skillAreas: any;

  constructor(private skillManagement: SkillService) {


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

  ngOnInit() {
    this.getSkillAreas();
  }
}
