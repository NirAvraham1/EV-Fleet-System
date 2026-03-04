import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { BehaviorSubject, tap } from 'rxjs';
import { jwtDecode } from 'jwt-decode';
import { environment } from '../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private tokenKey = 'auth_token';
  private userSubject = new BehaviorSubject<any>(null);
  user$ = this.userSubject.asObservable();

  constructor(private http: HttpClient) {
    this.loadUserFromStorage();
  }

  private loadUserFromStorage() {
    const token = localStorage.getItem(this.tokenKey);
    if (token) {
      try {
        const decoded = jwtDecode(token);
        this.userSubject.next(decoded);
      } catch (e) {
        this.logout();
      }
    }
  }

  login(credentials: any) {
    return this.http.post<{ token: string }>(`${environment.authUrl}/login`, credentials)
      .pipe(tap(response => {
        localStorage.setItem(this.tokenKey, response.token);
        const decoded = jwtDecode(response.token);
        this.userSubject.next(decoded);
      }));
  }

  register(userData: any) {
    return this.http.post<{ token: string }>(`${environment.authUrl}/register`, userData)
      .pipe(tap(response => {
        localStorage.setItem(this.tokenKey, response.token);
        const decoded = jwtDecode(response.token);
        this.userSubject.next(decoded);
      }));
  }

  logout() {
    localStorage.removeItem(this.tokenKey);
    this.userSubject.next(null);
  }

  getToken() {
    return localStorage.getItem(this.tokenKey);
  }

  getRole(): string | null {
    const user = this.userSubject.value;
    // JWT usually stores role in 'role' or authorities. 
    // In our backend we put it in 'role' claim.
    return user ? user.role : null;
  }
  
  isAdmin(): boolean {
    return this.getRole() === 'ADMIN';
  }
}