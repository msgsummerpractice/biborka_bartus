import { Component, inject } from '@angular/core';
import { AuthService } from '../auth';
import { UsernamePipe } from '../username-pipe';

@Component({
  selector: 'app-ownprofile',
  imports: [UsernamePipe],
  templateUrl: './ownprofile.html',
})
export class Ownprofile {
  public authService = inject(AuthService);
  public randomUsername = 'random_username';
}
