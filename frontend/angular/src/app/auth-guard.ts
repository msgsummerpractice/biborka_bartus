import { CanActivateFn, RedirectCommand } from '@angular/router';
import { AuthService } from './auth';
import { inject } from '@angular/core';
import { Router } from '@angular/router';

export const authGuard: CanActivateFn = (route, state) => {
  const authService = inject(AuthService);
  const router = inject(Router);
  console.log(`AuthGuard: isAuthenticated = ${authService.isAuthenticated()}`);
  if (authService.isAuthenticated()) {
    return true;
  }
  return new RedirectCommand(router.parseUrl('/login'));
};
