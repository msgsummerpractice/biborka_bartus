import { inject, Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

type DogApiResponse = {
  message: string;
  status: string;
};

@Injectable({
  providedIn: 'root',
})
export class DogImageService {
  private http = inject(HttpClient);
  getImageUrl(): Observable<DogApiResponse> {
    return this.http.get<DogApiResponse>('https://dog.ceo/api/breeds/image/random');
  }
}
