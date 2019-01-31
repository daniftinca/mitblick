import {Component, Inject, OnInit} from '@angular/core';
import {MAT_DIALOG_DATA, MatDialogRef} from "@angular/material";
import {ProfileComponent} from "../profile/profile.component";
import {TranslateService} from "@ngx-translate/core";
import {ProfileService} from "../profile.service";

export interface ProfileDialogData {
  firstName: string;
  lastName: string;
  photo: any;
  email: string;
  jobTitle: { name: string };
  region: { name: string };
  accepted: 0;
}

@Component({
  selector: 'app-edit-profile',
  templateUrl: './edit-profile.component.html',
  styleUrls: ['./edit-profile.component.scss']
})
export class EditProfileComponent implements OnInit {

  private jobTitles: any;
  private regions: any;
  constructor(
    private translate: TranslateService,
    public dialogRef: MatDialogRef<ProfileComponent>,
    private profileService: ProfileService,
    @Inject(MAT_DIALOG_DATA) public data: ProfileDialogData) {
  }

  onNoClick(): void {
    this.dialogRef.close();
  }

  ngOnInit(): void {
    this.getJobTitles();
    this.getRegions();
  }

  previewImage(event) {
    let reader = new FileReader();

    if (event.target.files && event.target.files.length) {
      const [file] = event.target.files;
      reader.onload = () => {
        var img = new Image();
        img.width = 100;
        // @ts-ignore
        img.src = reader.result;
        document.getElementById("chosen_image").appendChild(img);
        this.data.photo = reader.result;
      };
      reader.readAsDataURL(file);


    }

  }

  getJobTitles() {
    this.profileService.getAllJobTitles().subscribe(res => this.jobTitles = res);
  }

  getRegions() {
    this.profileService.getAllRegions().subscribe(res => this.regions = res);
  }
}
