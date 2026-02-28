import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, tap } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class AuthService {
 
  register(userData: any): Observable<any>{
    return this.http.post(`${this.API_URL}/register`, userData);
  }

  private readonly API_URL = 'http://localhost:8080/auth';

  constructor(private http: HttpClient) { }

  login(loginData: any): Observable<any> {
    return this.http.post(`${this.API_URL}/login`, loginData).pipe(
      tap((res: any) => {
        if (res.data && res.data.token) {
          localStorage.setItem('teachhub_token', res.data.token);
        }
      })
    );
  }
}