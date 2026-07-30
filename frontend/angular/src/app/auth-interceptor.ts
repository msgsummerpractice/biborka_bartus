import { HttpInterceptorFn } from '@angular/common/http';
import { AuthService } from './auth';

export const authInterceptor: HttpInterceptorFn = (req, next) => {
  const clone = req.clone({
    setHeaders: {
      Authorization: `Bearer ${new AuthService().isAuthenticated()}`,
    },
  });
  return next(clone);
};
