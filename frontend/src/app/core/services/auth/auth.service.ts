import { Injectable, PLATFORM_ID, inject } from '@angular/core';
import { isPlatformBrowser } from '@angular/common';
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

  private platformId = inject(PLATFORM_ID);

  constructor(private http: HttpClient) { }

  isAdmin(): boolean {
    if (!isPlatformBrowser(this.platformId)) return false;
    return localStorage.getItem('teachhub_role') === 'administrador';
  }

  private salvarSessao(res: any) {
    if (res.data && res.data.token) {
      localStorage.setItem('teachhub_token', res.data.token);
      localStorage.setItem('teachhub_role', res.data.role);
    }
  }

  login(loginData: any): Observable<any> {
    return this.http.post(`${this.API_URL}/login`, loginData).pipe(
      tap((res: any) => this.salvarSessao(res))
    );
  }

  logout(): void {
    localStorage.removeItem('teachhub_token');
    localStorage.removeItem('teachhub_role');
  }

  loginGoogle(credential: string): Observable<any> {
  return this.http.post(`${this.API_URL}/google`, { credential }).pipe(
    tap((res: any) => this.salvarSessao(res))
  );
}

}