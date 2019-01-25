import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";

@Injectable({
  providedIn: 'root'
})
export class SkillService {

  baseURL = 'http://localhost:8080/mitblick/rest'

  constructor(private http: HttpClient) {
  }

  getAllSkillAreas() {
    return this.http.get(this.baseURL + 'manage-skill-areas')
  }

}
