import {Component, OnInit} from '@angular/core';
import {ProfileManagementService} from "../profile-management.service";
import {PageEvent} from "@angular/material";

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

  constructor(private profileManagementService: ProfileManagementService) {
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

}
