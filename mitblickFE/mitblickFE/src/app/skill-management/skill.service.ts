import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders} from "@angular/common/http";

@Injectable({
  providedIn: 'root'
})
export class SkillService {

  baseURL = 'http://localhost:8080/mitblick/rest'

  constructor(private http: HttpClient) {
  }

  getAllSkillAreas() {
    return this.http.get(this.baseURL + '/manage-skill-areas/get-all-skillareas');
  }

  addSkillArea(skillAreaData) {
    return this.http.post(this.baseURL + '/manage-skill-areas/add-skill-area', skillAreaData,
      {
        headers: new HttpHeaders(
          {'Content-Type': 'application/json'}
        )
      })
  }

  removeSkillArea() {

  }

  addSkill() {

  }

  removeSkill() {

  }



}
