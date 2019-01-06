import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {tap} from "rxjs/internal/operators";
import {JwtHelperService} from "@auth0/angular-jwt";
import * as moment from 'moment';
import {now} from 'moment';
import {URLSearchParams} from '@angular/http';

export interface UserLoginData {
  email: string;
  password: string;
}

export interface UserData {
  email: string;
}

@Injectable({
  providedIn: 'root'
})
export class AuthenticationService {

  baseURL = 'http://localhost:8080/mitblick/rest/';
  constructor(private http: HttpClient) { }


  public getToken(): string {
    return localStorage.getItem('token');
  }

  validateUser(username: string, password: string) {

    let body = new URLSearchParams();
    body.set('email', username);
    body.set('password', password);


    return this.http.post<UserData>(this.baseURL + 'authenticate',
      body.toString(),
      {
        headers: new HttpHeaders(
          {'Content-Type': 'application/x-www-form-urlencoded'}
        )
      }).pipe(
      tap(res => this.setSession(res))
    );
  }

  private setSession(authResult) {


    const helper = new JwtHelperService();


    const decodedToken = helper.decodeToken(authResult.token);


    const expiresAt = new Date(decodedToken.exp * 1000);

    localStorage.setItem('token', authResult.token);

    localStorage.setItem('id_token', decodedToken.iss);
    localStorage.setItem('email', decodedToken.email);
    localStorage.setItem('expires_at', decodedToken.exp);
    localStorage.setItem('roles', decodedToken.role);
  }

  public isLoggedIn() {

    console.log("hello");
    if (!localStorage['expires_at']) {
      return false;
    }
    return this.getExpiration().isAfter(now());
  }

  public getRolesOfUser() {
    let roles = localStorage['roles'];
    return JSON.parse(roles);
  }

  public userHasPermission(permissionString) {
    let userRoles = this.getRolesOfUser();
    for (let role of userRoles) {
      for (let permission of role.permissions) {
        if (permission.type === permissionString) {
          return true;
        }
      }
    }
    return false;
  }

  public logout() {
    localStorage.removeItem('id_token');
    localStorage.removeItem('expires_at');
    localStorage.removeItem('email');
    localStorage.removeItem('roles');
  }

  getExpiration() {
    const time = localStorage['expires_at'];

    const correctSec = time * 1000;
    var expiresAt = new Date(correctSec);


    return moment(expiresAt);
  }
}
