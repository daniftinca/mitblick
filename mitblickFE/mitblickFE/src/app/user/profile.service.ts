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
    return this.http.get(this.baseURL + '/manage-profiles/get-by-email/' + email);
  }

  addProjectToProfile(email, project) {
    var response = this.addProject(project).subscribe();

    let body = new URLSearchParams();
    body.set('email', email);
    body.set('projektName', project.name);
    return this.http.post(this.baseURL + '/manage-profiles/add-projekt',
      body.toString(),
      {
        headers: new HttpHeaders(
          {'Content-Type': 'application/x-www-form-urlencoded'}
        )
      });
  }

  addProject(project) {
    return this.http.post(this.baseURL + '/manage-projekts/create',
      project,
      {
        headers: new HttpHeaders(
          {'Content-Type': 'application/json'}
        )
      });
  }

  removeProject(project, email) {
    return this.http.post(this.baseURL + '/manage-projekts/delete/' + email,
      project,
      {
        headers: new HttpHeaders(
          {'Content-Type': 'application/json'}
        )
      });
  }

  updateProfile(profile) {
    return this.http.post(this.baseURL + '/manage-profiles/update',
      profile,
      {
        headers: new HttpHeaders(
          {'Content-Type': 'application/json'}
        )
      });
  }

  addSkillToProfile(skillid, skillAreaName: string, skillRating, email: string) {
    let body = new URLSearchParams();
    // @ts-ignore
    body.set('skillId', skillid);
    body.set('skillAreaName', skillAreaName);
    // @ts-ignore
    body.set('skillRating', skillRating);
    body.set('email', email);

    return this.http.post(this.baseURL + '/manage-profiles/add-skill',
      body.toString(),
      {
        headers: new HttpHeaders(
          {'Content-Type': 'application/x-www-form-urlencoded'}
        )
      });
  }

  removeSkillFromProfile(skillid: number, email: string) {
    let body = new URLSearchParams();
    // @ts-ignore
    body.set('skillId', skillid);
    body.set('email', email);

    return this.http.post(this.baseURL + '/manage-profiles/remove-skill',
      body.toString(),
      {
        headers: new HttpHeaders(
          {'Content-Type': 'application/x-www-form-urlencoded'}
        )
      });
  }
}
