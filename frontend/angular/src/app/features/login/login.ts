import { Component } from '@angular/core';
import { LoginFormComponent } from '../../core/login-form/login-form';

@Component({
  selector: 'app-login',
  templateUrl: './login.html',
  standalone: true,
  imports: [LoginFormComponent],
})
export class Login {}
