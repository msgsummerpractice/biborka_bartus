import { Component, signal } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { RouterModule } from '@angular/router';
import { Header } from './features/header/header';

@Component({
  selector: 'app-root',
  imports: [RouterModule, RouterOutlet, Header],
  templateUrl: './app.html',
})
export class App {
  protected readonly title = signal('angular');
}
