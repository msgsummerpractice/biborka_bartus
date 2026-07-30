import { Injectable, signal } from '@angular/core';

@Injectable({
  providedIn: 'root',
})
export class AuthService {
  public isAuthenticated = signal(true);

  constructor() {}

  login(): void {
    this.isAuthenticated.set(true);
  }
  logout(): void {
    this.isAuthenticated.set(false);
  }
}
