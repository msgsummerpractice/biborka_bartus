import { Routes } from '@angular/router';
import { authGuard } from './auth-guard';
import { Notfound } from './notfound/notfound';
import { Homepage } from './homepage/homepage';
import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';
import { Ownprofile } from './ownprofile/ownprofile';

export const routes: Routes = [
  { path: '', redirectTo: 'homepage', pathMatch: 'full' },
  { path: 'homepage', component: Homepage },
  { path: 'profile', component: Ownprofile, canActivate: [authGuard] },
  { path: 'login', loadComponent: () => import('./login/login').then((m) => m.Login) },
  { path: '**', component: Notfound },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
})
export class AppRoutingModule {}
