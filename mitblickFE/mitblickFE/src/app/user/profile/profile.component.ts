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

  private profile = {
    skills : []
};

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
      if (this.new_project != undefined) {
        this.profileService.addProject(this.new_project).subscribe(_ => location.reload());

      }
    });
  }

  addProject(project): void {

  }

  removeProject(project): void {
    project.date = new Date(project.date);
    this.profileService.removeProject(project).subscribe(_ => location.reload());
  }

}


