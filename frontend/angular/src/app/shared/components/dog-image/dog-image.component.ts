import { Component, inject, signal } from '@angular/core';
import { OnInit } from '@angular/core';
import { DogImageService } from '../../services/dog-image.service';

@Component({
  selector: 'app-dog-image',
  templateUrl: './dog-image.html',
})
export class DogImageComponent implements OnInit {
  dogImage = signal<string | null>(null);
  private dogImageService = inject(DogImageService);
  ngOnInit(): void {
    this.dogImageService.getImageUrl().subscribe((response) => {
      this.dogImage.set(response.message);
    });
  }
}
