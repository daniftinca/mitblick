import {Component, OnInit} from '@angular/core';
import {ProfileManagementService} from "../profile-management.service";
import {MatDialog, PageEvent} from "@angular/material";
import {ProfileDialogComponent} from "../../supervisor-management/profile-dialog/profile-dialog.component";
import {SupervisorViewService} from "../../supervisor-management/supervisor-view.service";
import * as FileSaver from "file-saver";
import {FormControl, FormGroup} from "@angular/forms";
import {SkillService} from "../../skill-management/skill.service";


@Component({
  selector: 'app-profile-management',
  templateUrl: './profile-management.component.html',
  styleUrls: ['./profile-management.component.scss']
})
export class ProfileManagementComponent implements OnInit {
  firstNameControl = new FormControl();
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
  lastNameControl = new FormControl();
  emailControl = new FormControl();
  jobTitleControl = new FormControl();
  form = new FormGroup({
    firstName: this.firstNameControl, lastName: this.lastNameControl, email: this.emailControl,
    jobTitle: this.jobTitleControl
  });
  private skillEntries: any;

  constructor(private profileManagementService: ProfileManagementService, public dialog: MatDialog,
              private supervisorService: SupervisorViewService, private skillService: SkillService) {
  }

  setPageSizeOptions(setPageSizeOptionsInput: string) {
    this.pageSizeOptions = setPageSizeOptionsInput.split(',').map(str => +str);
  }

  filterChanged(name, value) {
    if (name != undefined && value != undefined) {
      var found = false;
      for (var key in this.filterCriteriaNames) {
        if (this.filterCriteriaNames[key] == name) {
          this.filterCriteriaValues[key] = value;
          found = true;
        }
      }
      if (!found) {
        this.filterCriteriaNames.push(name);
        this.filterCriteriaValues.push(value);
      }
      this.getProfiles(
        0,
        4,
        this.filterCriteriaNames,
        this.filterCriteriaValues,
        this.skillIds);
    }
  }

  skillFilterChanged(id: any) {
    if (this.skillIds.indexOf(id) >= 0) {
      // @ts-ignore
      this.skillIds.pop(id);
    } else {
      this.skillIds.push(id);
    }
    this.getProfiles(
      0,
      4,
      this.filterCriteriaNames,
      this.filterCriteriaValues,
      this.skillIds);

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
    this.skillService.getAllSkillAreas().subscribe(res => this.getSkillEntries(res));

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

  getSkillEntries(skillareas) {
    this.skillEntries = {};
    for (var id in skillareas) {
      var skillentry = skillareas[id];
      // @ts-ignore
      var sa_name = skillentry.name;
      if (this.skillEntries == undefined || !(sa_name in this.skillEntries)) {
        this.skillEntries[sa_name] = [];
      }
      for (var j in skillentry.skills) {
        var skill = {name: skillentry.skills[j].name, id: skillentry.skills[j].id};
        this.skillEntries[sa_name].push(skill);
      }
      // @ts-ignore

    }
  }

  keys() {
    if (this.skillEntries != undefined) {
      return Object.keys(this.skillEntries);
    }
  }


}
