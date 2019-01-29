import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";

@Injectable({
  providedIn: 'root'
})
export class SupervisorViewService {

  baseURL = 'http://localhost:8080/mitblick/rest';

  constructor(private http: HttpClient) {
  }

  getProfilesBySupervisor(email) {
    return this.http.get(this.baseURL + '/manage-profiles/filter-by-supervisor?email=' + email);
  }

  declineProfile(supervisorEmail: string, email: any) {

  }

  acceptProfile(supervisorEmail: string, email: string) {

  }
}
