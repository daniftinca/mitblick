import {Injectable} from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class ProfileManagementService {

  constructor() {
  }

  getAllProfiles() {

  }

  getProfiles(pageIndex, filterCriterias, sortCriterias, amount) {
    let resultIndex = pageIndex * amount + 1;
    //TODO add backend filter function with amount,startindex and sortCriterias

    //TODO add backend filter which returns a list of profile ids
    //TODO add be sort function
  }

  getAmountOfProfiles(filterCriterias) {

    //TODO add backend filter count function
  }


  getUserProfile(email) {

  }
}
