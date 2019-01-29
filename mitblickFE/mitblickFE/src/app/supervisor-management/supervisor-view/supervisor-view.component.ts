import {Component, OnInit} from '@angular/core';
import {SupervisorViewService} from "../supervisor-view.service";

@Component({
  selector: 'app-supervisor-view',
  templateUrl: './supervisor-view.component.html',
  styleUrls: ['./supervisor-view.component.scss']
})
export class SupervisorViewComponent implements OnInit {

  displayedColumns: string[] = [
    'name',
    'view',
    'status',
    'action',
    'export'
  ];
  private profiles: any;
  private dataSource: any;

  constructor(private supervisorService: SupervisorViewService) {
  }

  ngOnInit() {
    this.getProflies();
  }

  getProflies() {
    this.supervisorService.getProfilesBySupervisor(localStorage.getItem("email"))
      .subscribe(res => {
        // @ts-ignore
        this.profiles = res.profiles;
      });
  }

  toggleActivation(profile) {
    if (profile.isAccepted) {
      document.getElementById('toggleButton').style.backgroundColor = "#3f51b5";
      this.decline(profile);
    } else {
      document.getElementById('toggleButton').style.backgroundColor = "#f44336";
      this.accept(profile);
    }
  }

  accept(profile) {
    this.supervisorService.acceptProfile(localStorage.getItem("email"), profile.email);
    profile.isAccepted = 1;
  }

  decline(profile) {
    this.supervisorService.declineProfile(localStorage.getItem("email"), profile.email);
    profile.isAccepted = 0;
  }

  getActivationButtonText(isActive) {
    if (isActive) {

      return "Decline";

    } else {

      return "Accept"

    }
  }

  showProfile(profile: any) {

  }
}
