import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";


@Injectable({
  providedIn: 'root'
})
export class NotificationsService {

  baseURL = 'http://localhost:8080/mitblick/rest/';

  constructor(private http: HttpClient) {
  }

  getAmountOfNotifs() {
    let email = localStorage.email;
    return 1;
  }

}
