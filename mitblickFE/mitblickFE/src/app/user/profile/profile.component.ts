import {Component, OnInit} from '@angular/core';
import {ProfileService} from "../profile.service";

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.scss']
})
export class ProfileComponent implements OnInit {

  private profile: any;


  constructor(private profileService: ProfileService) {
  }

  ngOnInit() {
    var email = localStorage.getItem("email");
    this.profileService.getProfileByEmail(email).subscribe(res => this.profile = res);
    console.log("Profile first name: " + this.profile.firstName);
  }

  alertUser() {
    alert("First name: " + this.profile.firstName);
  }

}


