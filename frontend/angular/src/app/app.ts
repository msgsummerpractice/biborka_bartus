import { Component, signal } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { MatButtonModule } from '@angular/material/button';
import { MatToolbarModule } from '@angular/material/toolbar';
import { MatIconModule } from '@angular/material/icon';

@Component({
  selector: 'app-root',
  imports: [MatButtonModule, MatToolbarModule, MatIconModule],
  templateUrl: './app.html',
})
export class App {
  protected readonly title = signal('angular');
}
