import { Component, inject } from '@angular/core';
import { AuthService } from '../../core/auth/auth';
import { UsernamePipe } from '../../shared/pipes/username-pipe';

@Component({
  selector: 'app-ownprofile',
  imports: [UsernamePipe],
  templateUrl: './own-profile.html',
})
export class Ownprofile {
  public randomUsername = 'random_username';
  protected getIsAuthenticated = inject(AuthService).getIsAuthenticated;
}
