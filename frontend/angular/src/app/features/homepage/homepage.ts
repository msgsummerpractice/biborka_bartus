import { Component } from '@angular/core';
import { DogImageComponent } from '../../shared/components/dog-image/dog-image.component';
@Component({
  selector: 'app-homepage',
  imports: [DogImageComponent],
  templateUrl: './homepage.html',
})
export class Homepage {
  dogCount = 3;
  dogArray = Array.from({ length: this.dogCount }, (_, index) => index);
}
