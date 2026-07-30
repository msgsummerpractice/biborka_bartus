import { Component } from '@angular/core';
import { DogImageComponent } from '../components/dog-image';
@Component({
  selector: 'app-homepage',
  imports: [DogImageComponent],
  templateUrl: './homepage.html',
})
export class Homepage {}
