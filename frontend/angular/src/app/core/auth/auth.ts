import { Injectable, signal } from '@angular/core';

@Injectable({
  providedIn: 'root',
})
export class AuthService {
  private readonly isAuthenticated = signal<boolean>(false);

  getIsAuthenticated() {
    return this.isAuthenticated.asReadonly();
  }
  login(): void {
    this.isAuthenticated.set(true);
  }
  logout(): void {
    this.isAuthenticated.set(false);
  }
}
