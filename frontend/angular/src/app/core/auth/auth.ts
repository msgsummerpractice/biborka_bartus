import { Injectable, signal } from '@angular/core';
import { WritableSignal } from '@angular/core';

@Injectable({
  providedIn: 'root',
})
export class AuthService {
  #isAuthenticatedSignal: WritableSignal<boolean> = signal(false);
  isAuthenticated = this.#isAuthenticatedSignal.asReadonly();

  getIsAuthenticated() {
    return this.#isAuthenticatedSignal();
  }
  login(): void {
    this.#isAuthenticatedSignal.set(true);
  }
  logout(): void {
    this.#isAuthenticatedSignal.set(false);
  }
}
