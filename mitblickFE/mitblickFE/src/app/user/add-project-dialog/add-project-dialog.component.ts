import {Component, Inject, OnInit} from '@angular/core';
import {MAT_DIALOG_DATA, MatDialogRef} from "@angular/material";
import {ProfileComponent} from "../profile/profile.component";
import {FormControl, FormGroup, Validators} from "@angular/forms";

export interface ProjectDialogData {
  name: string;
  client: string;
  branch: string;
  description: string;
  startDate: Date;
  endDate: Date;
}

@Component({
  selector: 'app-add-project-dialog',
  templateUrl: './add-project-dialog.component.html',
  styleUrls: ['./add-project-dialog.component.scss']
})
export class AddProjectDialogComponent implements OnInit {

  private name = new FormControl(Validators.required);
  private client = new FormControl(Validators.required);
  private branch = new FormControl(Validators.required);
  private description = new FormControl(Validators.required);
  private startDate = new FormControl(Validators.required);
  private endDate = new FormControl(Validators.required);
  private form = new FormGroup({
    name: this.name, client: this.client,
    branch: this.branch, description: this.description, startDate: this.startDate, endDate: this.endDate
  });

  constructor(
    public dialogRef: MatDialogRef<ProfileComponent>,
    @Inject(MAT_DIALOG_DATA) public data: ProjectDialogData) {
  }

  sendResponse() {
    this.dialogRef.close(this.data);
  }

  onNoClick(): void {
    this.dialogRef.close();
  }

  ngOnInit(): void {
  }

  datesValidation() {
    return this.data.startDate > this.data.endDate;
  }
}
