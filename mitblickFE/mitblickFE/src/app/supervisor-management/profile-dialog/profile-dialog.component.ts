import {Component, Inject, OnInit} from '@angular/core';
import {AddProjectDialogComponent, ProjectDialogData} from "../../user/add-project-dialog/add-project-dialog.component";
import {EditProfileComponent, ProfileDialogData} from "../../user/edit-profile/edit-profile.component";
import {ProfileService} from "../../user/profile.service";
import {MAT_DIALOG_DATA, MatDialog, MatDialogRef} from "@angular/material";
import {SkillService} from "../../skill-management/skill.service";
import {AddSkillDialogComponent} from "../../user/add-skill-dialog/add-skill-dialog.component";
import {ProfileData} from "../../user/profile/profile.component";
import {SupervisorViewComponent} from "../supervisor-view/supervisor-view.component";

@Component({
  selector: 'app-profile-dialog',
  templateUrl: './profile-dialog.component.html',
  styleUrls: ['./profile-dialog.component.scss']
})
export class ProfileDialogComponent implements OnInit {

  private profile: ProfileData;
  private skillEntries: {};



  private new_project: ProjectDialogData;
  private new_profile: ProfileDialogData;


  constructor(private profileService: ProfileService, public dialog: MatDialog,
              private skillManagement: SkillService,
              @Inject(MAT_DIALOG_DATA) public data: ProfileData,
              public dialogRef: MatDialogRef<SupervisorViewComponent>) {
    this.profile = {
      firstName: "",
      lastName: "",
      photo: "",
      email: localStorage.getItem("email"),
      projekts: [],
      skills: [],
      jobTitle: {name: ""},
      region: {name: ""},
      accepted: 0
    };
  }

  ngOnInit() {
    this.profile = this.data;
    if (this.profile.photo == "" || this.profile.photo == undefined) {
      this.profile.photo = "https://material.angular.io/assets/img/examples/shiba1.jpg";
    }
    this.getSkillEntries();
  }

  activate() {
    this.data.accepted = 1;
    this.dialogRef.close(this.data);
  }

  deactivate(): void {
    this.data.accepted = 0;
    this.dialogRef.close(this.data);
  }

  addSkillDialog(): void {
    const dialogRef = this.dialog.open(AddSkillDialogComponent, {
      width: '350px',
      data: {skillEntries: this.skillEntries}
    });

    dialogRef.afterClosed().subscribe(result => {
      console.log('The dialog was closed');
      var skillid = result.skillid;
      var skillAreaName = result.skillAreaName;
      var skillRating = result.skillRating;
      var email = this.profile.email;
      this.profile.skills.push({
        // @ts-ignore
        skillAreaName: skillAreaName,
        // @ts-ignore
        rating: skillRating,
        // @ts-ignore
        skill: {name: result.skillName, id: result.skillid}
      });
      this.getSkillEntries();
      this.profileService.addSkillToProfile(skillid, skillAreaName, skillRating, email).subscribe();
    });
  }

  addProjectDialog(): void {
    const dialogRef = this.dialog.open(AddProjectDialogComponent, {
      width: '350px',
      data: {}
    });

    dialogRef.afterClosed().subscribe(result => {
      console.log('The dialog was closed');
      this.new_project = result;
      if (this.new_project != undefined) {
        this.profileService.addProjectToProfile(this.profile.email, this.new_project);
        // @ts-ignore
        this.profile.projekts.push(this.new_project);
      }
    });
  }

  editProfileDialog(): void {
    const dialogRef = this.dialog.open(EditProfileComponent, {
      width: '550px',
      data: {}
    });

    dialogRef.afterClosed().subscribe(result => {
      console.log('The dialog was closed');
      this.new_profile = result;
      if (this.new_profile != undefined) {
        this.new_profile.email = this.profile.email;
        if (this.new_profile.firstName == "" || this.new_profile.firstName == undefined) {
          this.new_profile.firstName = this.profile.firstName;
        }
        if (this.new_profile.lastName == "" || this.new_profile.lastName == undefined) {
          this.new_profile.lastName = this.profile.lastName;
        }

        if (this.new_profile.photo == "" || this.new_profile.photo == undefined) {
          if (this.profile.photo == "https://material.angular.io/assets/img/examples/shiba1.jpg") {
            this.new_profile.photo = "";
          } else {
            this.new_profile.photo = this.profile.photo;
          }
        }

        if (this.new_profile.jobTitle == undefined || this.new_profile.jobTitle.name == undefined) {
          this.new_profile.jobTitle = this.profile.jobTitle;
        }

        if (this.new_profile.region == undefined || this.new_profile.region.name == undefined) {
          this.new_profile.region = this.profile.region;
        }
        // @ts-ignore
        this.profileService.updateProfile(this.new_profile).subscribe(res => this.profile = res);
      }
    });
  }

  removeSkill(skillid: number, skillarea: string) {
    this.profileService.removeSkillFromProfile(skillid, this.profile.email).subscribe(res => {
      // @ts-ignore
      this.profile = res;
      this.getSkillEntries();
    });
    // for(var idx in this.skillEntries[skillarea]){
    //   // @ts-ignore
    //   if(this.skillEntries[idx].id == skillid){
    //     this.skillEntries[skillarea].pop(this.skillEntries[idx]);
    //     if(this.skillEntries[skillarea].length == 0){
    //       delete this.skillEntries[skillarea];
    //     }
    //   }
    // }
  }


  removeProject(project): void {
    project.startDate = new Date(project.startDate);
    project.endDate = new Date(project.endDate);

    this.profileService.removeProject(project, this.profile.email).subscribe();
    // @ts-ignore
    this.profile.projekts.pop(project);
  }

  getSkillEntries() {
    this.skillEntries = {};
    for (var id in this.profile.skills) {
      var skillentry = this.profile.skills[id];
      // @ts-ignore
      var sa_name = skillentry.skillAreaName;
      if (this.skillEntries == undefined || !(sa_name in this.skillEntries)) {
        this.skillEntries[sa_name] = [];
      }
      // @ts-ignore
      var skill = {name: skillentry.skill.name, rating: skillentry.rating, id: skillentry.skill.id};
      this.skillEntries[sa_name].push(skill);
    }
  }

  keys() {
    if (this.skillEntries != undefined) {
      return Object.keys(this.skillEntries);
    }
  }

  projekts() {
    if (this.profile != undefined) {
      return this.profile.projekts;
    }
  }
}
