import {Component, OnInit} from '@angular/core';
import {ProfileService} from "../profile.service";
import {MatDialog} from "@angular/material";
import {AddProjectDialogComponent, ProjectDialogData} from "../add-project-dialog/add-project-dialog.component";
import {EditProfileComponent, ProfileDialogData} from "../edit-profile/edit-profile.component";

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.scss']
})
export class ProfileComponent implements OnInit {

  private profile;

  private new_project: ProjectDialogData;
  private new_profile: ProfileDialogData;


  constructor(private profileService: ProfileService, public dialog: MatDialog) {
  }

  ngOnInit() {
    var email = localStorage.getItem("email");
    this.profileService.getProfileByEmail(email).subscribe(res => {
      this.profile = res;
      if (this.profile.photo == "" || this.profile.photo == undefined) {
        this.profile.photo = "https://material.angular.io/assets/img/examples/shiba1.jpg";
      }
    });
  }

  alertUser() {
    alert("First name: " + this.profile.firstName);
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

}


