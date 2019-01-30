import {Component, OnInit} from '@angular/core';
import {SupervisorViewService} from "../supervisor-view.service";
import {MatDialog} from "@angular/material";
import {ProfileDialogComponent} from "../profile-dialog/profile-dialog.component";
import * as FileSaver from "file-saver";

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

  constructor(private supervisorService: SupervisorViewService, public dialog: MatDialog) {
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
    if (profile.accepted) {
      this.decline(profile);
    } else {
      this.accept(profile);
    }
  }

  accept(profile) {
    this.supervisorService.acceptProfile(localStorage.getItem("email"), profile.email).subscribe(_ => {
      profile.accepted = 1;
      this.getProflies();
    });

  }

  decline(profile) {
    this.supervisorService.declineProfile(localStorage.getItem("email"), profile.email).subscribe(_ => {
      profile.accepted = 0;
      this.getProflies();
    });

  }

  getActivationButtonText(isActive) {
    if (isActive) {

      return "Decline";

    } else {

      return "Accept"

    }
  }

  showProfile(profile: any) {
    const dialogRef = this.dialog.open(ProfileDialogComponent, {
      width: '750px',
      maxHeight: '950px',
      data: profile
    });

    dialogRef.afterClosed().subscribe(result => {
      if (!result.accepted) {
        this.decline(result);
      } else {
        this.accept(result);
      }
    });
  }

  exportPdf(email) {
    var file: any;
    this.supervisorService.getPdfByEmail(email).subscribe(res => {
      console.log(res.headers.get('content-disposition'));
      file = res.body;
      FileSaver.saveAs(res.body, "profile_" + email + ".pdf");
    });
  }
}
