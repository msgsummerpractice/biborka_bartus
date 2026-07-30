import { Component, signal } from '@angular/core';
import { MatButtonModule } from '@angular/material/button';
import { MatToolbarModule } from '@angular/material/toolbar';
import { MatIconModule } from '@angular/material/icon';
import { RouterOutlet } from '@angular/router';
import { RouterModule } from '@angular/router';

@Component({
  selector: 'app-root',
  imports: [RouterModule, RouterOutlet, MatButtonModule, MatToolbarModule, MatIconModule],
  templateUrl: './app.html',
})
export class App {
  protected readonly title = signal('angular');
}
