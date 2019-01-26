import {Component, OnInit} from '@angular/core';
import {ProfileService} from "../profile.service";
import {MatDialog} from "@angular/material";
import {AddProjectDialogComponent, ProjectDialogData} from "../add-project-dialog/add-project-dialog.component";
import {EditProfileComponent, ProfileDialogData} from "../edit-profile/edit-profile.component";
import {SkillService} from "../../skill-management/skill.service";
import {AddSkillDialogComponent} from "../add-skill-dialog/add-skill-dialog.component";

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.scss']
})

export class ProfileComponent implements OnInit {

  private profile: any;
  private skillEntries: {};



  private new_project: ProjectDialogData;
  private new_profile: ProfileDialogData;


  constructor(private profileService: ProfileService, public dialog: MatDialog, private skillManagement: SkillService) {
  }

  ngOnInit() {
    var email = localStorage.getItem("email");
    this.profileService.getProfileByEmail(email).subscribe(res => {
      this.profile = res;
      if (this.profile.photo == "" || this.profile.photo == undefined) {
        this.profile.photo = "https://material.angular.io/assets/img/examples/shiba1.jpg";
      }
      this.getSkillEntries();
    });
  }

  alertUser() {
    alert("First name: " + this.profile.firstName);
  }

  addSkillDialog(): void {
    const dialogRef = this.dialog.open(AddSkillDialogComponent, {
      width: '350px',
      data: {}
    });

    dialogRef.afterClosed().subscribe(result => {
      console.log('The dialog was closed');
      var skillid = result.skillid;
      var skillAreaName = result.skillAreaName;
      var skillRating = result.skillRating;
      var email = this.profile.email;
      this.profile.skills.push({skillAreaName: skillAreaName, rating: skillRating, skill: {name: result.skillName}});
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
        this.profileService.addProject(this.new_project).subscribe();
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
        if (this.new_profile.firstname == "" || this.new_profile.firstname == undefined) {
          this.new_profile.firstname = this.profile.firstname;
        }
        if (this.new_profile.lastname == "" || this.new_profile.lastname == undefined) {
          this.new_profile.lastname = this.profile.lastname;
        }

        if (this.new_profile.photo == "" || this.new_profile.photo == undefined) {
          this.new_profile.photo = this.profile.photo;
        }

        this.profileService.updateProfile(this.new_profile).subscribe(res => this.profile = res);
      }
    });
  }


  removeProject(project): void {
    project.date = new Date(project.date);
    this.profileService.removeProject(project, this.profile.email).subscribe();
    this.profile.projekts.pop(project);
    // this.profileService.getProfileByEmail(this.profile.email).subscribe(res => this.profile = res);
  }

  getSkillEntries() {
    this.skillEntries = {};
    for (var id in this.profile.skills) {
      var skillentry = this.profile.skills[id];
      var sa_name = skillentry.skillAreaName;
      if (this.skillEntries == undefined || !(sa_name in this.skillEntries)) {
        this.skillEntries[sa_name] = [];
      }
      var skill = {name: skillentry.skill.name, rating: skillentry.rating};
      this.skillEntries[sa_name].push(skill);
    }
  }

  keys() {
    return Object.keys(this.skillEntries);
  }

}


