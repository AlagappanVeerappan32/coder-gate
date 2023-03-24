import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Repository } from './repository';


@Injectable({
  providedIn: 'root'
})
export class RepositoryService {
  private apiServerUrl = 'localhost:3000';
  constructor(private: HttpClient ) { }

  public getRepositories(): Observable<Repository[]>{
  return this.http.get<Repository[]>('${this.apiServerUrl}/getRepositories/limysh');
  }
}
