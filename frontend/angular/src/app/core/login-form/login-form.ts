import { Component, inject } from '@angular/core';
import {
  ReactiveFormsModule,
  NonNullableFormBuilder,
  Validators,
  FormControl,
} from '@angular/forms';

type LoginForm = {
  email: FormControl<string>;
  password: FormControl<string>;
};

@Component({
  selector: 'app-login-form',
  imports: [ReactiveFormsModule],
  templateUrl: './login-form.html',
})
export class LoginFormComponent {
  private readonly _formBuilder = inject(NonNullableFormBuilder);
  protected readonly loginFormGroup = this._formBuilder.group<LoginForm>({
    email: this._formBuilder.control('', [Validators.required, Validators.email]),
    password: this._formBuilder.control('', [Validators.required]),
  });

  onFormSubmit(): void {
    if (this.loginFormGroup.valid) {
      const formValue = this.loginFormGroup.value;
      console.log('Form submitted with values:', formValue);
    } else {
      console.log('Form is invalid');
    }
  }
}
