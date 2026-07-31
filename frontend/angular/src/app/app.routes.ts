import { Routes } from '@angular/router';
import { authGuard } from './core/auth/auth-guard';
import { Notfound } from './features/not-found/not-found';
import { Homepage } from './features/homepage/homepage';
import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

export const routes: Routes = [
  { path: '', redirectTo: 'homepage', pathMatch: 'full' },
  { path: 'homepage', component: Homepage },
  {
    path: 'profile',
    loadComponent: () => import('./features/own-profile/own-profile').then((m) => m.Ownprofile),
    canActivate: [authGuard],
  },
  { path: 'login', loadComponent: () => import('./features/login/login').then((m) => m.Login) },
  { path: '**', component: Notfound },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
})
export class AppRoutingModule {}
