import { Component } from '@angular/core';
import { LoginFormComponent } from '../login-form/login-form';

@Component({
  selector: 'app-login',
  imports: [LoginFormComponent],
  templateUrl: './login.html',
})
export class Login {}
