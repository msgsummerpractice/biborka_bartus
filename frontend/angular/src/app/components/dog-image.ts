import { Component, signal } from '@angular/core';
import { of, map, catchError, Observable, throwError } from 'rxjs';
import { HttpClient, HttpErrorResponse } from '@angular/common/http';

@Component({
  selector: 'app-dog-image',
  templateUrl: './dog-image.html',
})
export class DogImageComponent {
  private apiUrl = 'https://dog.ceo/api/breeds/image/random';
  readonly dogImage = signal<Observable<string>>(of('https://dog.ceo/api/breeds/image/random'));
  constructor(private http: HttpClient) {
    this.dogImage.set(this.fetchImage(this.apiUrl));
  }

  fetchImage(url: string): Observable<string> {
    this.dogImage.set(
      this.http.get<{ message: string }>(url).pipe(
        map((response) => response.message),
        catchError((error: HttpErrorResponse) => this.handleError(error))
      )
    );
    return this.dogImage();
  }

  private handleError(error: HttpErrorResponse) {
    return throwError(() => new Error('An error occurred while fetching the image.'));
  }
}
