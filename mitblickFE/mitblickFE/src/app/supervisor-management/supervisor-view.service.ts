import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders} from "@angular/common/http";

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
    let body = new URLSearchParams();
    // @ts-ignore
    body.set('supervisorEmail', supervisorEmail);
    body.set('userEmail', email);

    return this.http.post(this.baseURL + '/manage-profiles/unaccept',
      body.toString(),
      {
        headers: new HttpHeaders(
          {'Content-Type': 'application/x-www-form-urlencoded'}
        )
      });
  }

  acceptProfile(supervisorEmail: string, email: string) {
    let body = new URLSearchParams();
    // @ts-ignore
    body.set('supervisorEmail', supervisorEmail);
    body.set('userEmail', email);

    return this.http.post(this.baseURL + '/manage-profiles/accept',
      body.toString(),
      {
        headers: new HttpHeaders(
          {'Content-Type': 'application/x-www-form-urlencoded'}
        )
      });
  }

  getPdfByEmail(email) {
    return this.http.get(this.baseURL + '/pdf/profile-by-email/' + email, {
      observe: 'response',
      responseType: 'blob' as 'json'
    });
  }
}
