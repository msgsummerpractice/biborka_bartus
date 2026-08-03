import { Component } from '@angular/core';
import { DogImage } from '../../shared/components/dog-image/dog-image';
@Component({
  selector: 'app-homepage',
  imports: [DogImage],
  templateUrl: './homepage.html',
})
export class Homepage {
  dogCount = 3;
  dogArray = Array.from({ length: this.dogCount }, (_, index) => index);
}
