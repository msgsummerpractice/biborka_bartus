import { Component, inject, input, signal } from '@angular/core';
import { OnInit } from '@angular/core';
import { DogImageService } from '../../services/dog-image';

@Component({
  selector: 'app-dog-image ',
  templateUrl: './dog-image.html',
})
export class DogImage implements OnInit {
  dogIndex = input<number>(0);
  dogImage = signal<string | null>(null);
  private dogImageService = inject(DogImageService);
  ngOnInit(): void {
    this.dogImageService.getImageUrl().subscribe((response) => {
      this.dogImage.set(response.message);
    });
  }
}
