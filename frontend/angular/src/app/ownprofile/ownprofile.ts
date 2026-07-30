import { Component, inject } from '@angular/core';
import { Profile } from '../profile';
import { AuthService } from '../auth';

@Component({
  selector: 'app-ownprofile',
  imports: [],
  templateUrl: './ownprofile.html',
})
export class Ownprofile {
  public authService = inject(AuthService);
}
