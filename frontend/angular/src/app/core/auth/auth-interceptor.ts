import { HttpInterceptorFn } from '@angular/common/http';
import { AuthService } from './auth';
import { inject } from '@angular/core';

export const authInterceptor: HttpInterceptorFn = (req, next) => {
  const authService = inject(AuthService);
  const clone = req.clone({
    setHeaders: {
      Authorization: `Bearer ${authService.getIsAuthenticated()}`,
    },
  });
  return next(clone);
};
