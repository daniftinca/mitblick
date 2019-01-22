import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders} from "@angular/common/http";

@Injectable({
  providedIn: 'root'
})
export class ProfileService {

  baseURL = 'http://localhost:8080/mitblick/rest';

  constructor(private http: HttpClient) {
  }

  getProfileByEmail(email) {
    const body = email;
    return this.http.post(this.baseURL + '/manage-profiles/get-by-email', body,
      {
        headers: new HttpHeaders(
          {'Content-Type': 'text/plain'}
        )
      });
  }
}
