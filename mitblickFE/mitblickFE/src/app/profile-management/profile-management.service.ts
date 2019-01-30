import {Injectable} from '@angular/core';
import {HttpClient, HttpParams} from "@angular/common/http";

@Injectable({
  providedIn: 'root'
})
export class ProfileManagementService {

  baseURL = 'http://localhost:8080/mitblick/rest';

  constructor(private http: HttpClient) {
  }

  getAllProfiles() {

  }

  getProfiles(pageIndex, amount, filterCriteriaNames, filterCriteriaValues, skillIds) {
    let resultIndex = pageIndex * amount;

    let headers = new Headers();
    headers.append('Content-Type', 'application/json');
    let params = new HttpParams().set("index", resultIndex.toString()).set("amount", amount);


    for (let i = 0; i < filterCriteriaNames.length; i++) {
      params = params.append('criteriaName', filterCriteriaNames[i]);
      params = params.append('criteriaValue', filterCriteriaValues[i]);
    }
    for (let i = 0; i < skillIds.length; i++) {
      params = params.append('skillId', skillIds[i]);
    }
    console.log(params);
    return this.http.get(this.baseURL + '/manage-profiles/filter', {params: params});

  }

  exportPdfProfiles(emailList) {
    let params = new HttpParams();
    for (let i = 0; i < emailList.length; i++) {
      params = params.append('emailList', emailList[i]);
    }

    return this.http.get(this.baseURL + 'pdf/profiles-by-email', {params: params});

  }


  getUserProfile(email) {

  }
}
