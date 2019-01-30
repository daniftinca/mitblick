import {Component, OnInit} from '@angular/core';
import {ProfileManagementService} from "../profile-management.service";
import {MatDialog, PageEvent} from "@angular/material";
import {ProfileDialogComponent} from "../../supervisor-management/profile-dialog/profile-dialog.component";
import {SupervisorViewService} from "../../supervisor-management/supervisor-view.service";
import * as FileSaver from "file-saver";
import {TranslateService} from "@ngx-translate/core";

@Component({
  selector: 'app-profile-management',
  templateUrl: './profile-management.component.html',
  styleUrls: ['./profile-management.component.scss']
})
export class ProfileManagementComponent implements OnInit {

  filter;
  profiles;
  resultAmount;
  //for filter
  filterCriteriaNames = [];
  //pageSize = 10;
  //pageIndex = 0;
  filterCriteriaValues = [];
  skillIds = [];
  pageSizeOptions: number[] = [4, 8, 10];
  // MatPaginator Output
  pageEvent: PageEvent;

  constructor(private profileManagementService: ProfileManagementService,
              public dialog: MatDialog,
              private supervisorService: SupervisorViewService,
              private translate: TranslateService) {
  }

  setPageSizeOptions(setPageSizeOptionsInput: string) {
    this.pageSizeOptions = setPageSizeOptionsInput.split(',').map(str => +str);
  }

  switchPage(event) {
    this.pageEvent = event;
    console.log(event);
    console.log(this.pageEvent);
    this.getProfiles(
      this.pageEvent.pageIndex,
      this.pageEvent.pageSize,
      this.filterCriteriaNames,
      this.filterCriteriaValues,
      this.skillIds);
  }

  getProfiles(pageIndex, amount, filterCriteriaNames, filterCriteriaValues, skillIds) {
    this.profileManagementService.getProfiles(
      pageIndex,
      amount,
      filterCriteriaNames,
      filterCriteriaValues,
      skillIds)
      .subscribe(data => {
        console.log(data);
        this.filter = data;
        this.profiles = this.filter.profiles;
        this.resultAmount = this.filter.amount;
      });
  }

  ngOnInit() {
    console.log(this.pageEvent);
    this.getProfiles(0, 4, [], [], []);
  }

  showProfile(profile: any) {
    const dialogRef = this.dialog.open(ProfileDialogComponent, {
      width: '750px',
      maxHeight: '950px',
      data: profile,
      panelClass: 'edit-hide'

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
