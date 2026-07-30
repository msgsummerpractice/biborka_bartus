import { Routes } from '@angular/router';
import { Notfound } from './notfound/notfound';
import { Homepage } from './homepage/homepage';
import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';
import { Ownprofile } from './ownprofile/ownprofile';
import { Profile } from './profile';

export const routes: Routes = [
  { path: '', component: Homepage },
  { path: 'homepage', component: Homepage },
  { path: 'profile', component: Ownprofile },
  { path: '**', component: Notfound },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
})
export class AppRoutingModule {}
