import {Component, OnInit} from '@angular/core';
import {ProfileService} from "../profile.service";
import {MatDialog} from "@angular/material";
import {AddProjectDialogComponent, ProjectDialogData} from "../add-project-dialog/add-project-dialog.component";

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.scss']
})
export class ProfileComponent implements OnInit {

  private profile: any;

  private new_project: ProjectDialogData;


  constructor(private profileService: ProfileService, public dialog: MatDialog) {
  }

  ngOnInit() {
    var email = localStorage.getItem("email");
    this.profileService.getProfileByEmail(email).subscribe(res => this.profile = res);
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
      this.profileService.addProjectToProfile(localStorage.getItem("email"), this.new_project);
    });
  }

  addProject(project): void {

  }

}


