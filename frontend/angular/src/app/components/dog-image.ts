import { Component, signal } from '@angular/core';
import { of, map, catchError, Observable, throwError } from 'rxjs';
import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { OnInit } from '@angular/core';

@Component({
  selector: 'app-dog-image',
  templateUrl: './dog-image.html',
})
export class DogImageComponent implements OnInit {
  constructor(private http: HttpClient) {}
  private apiUrl = 'https://dog.ceo/api/breeds/image/random';
  dogImage = signal('');

  ngOnInit() {
    this.fetchDogImage().subscribe({
      next: (imageUrl) => this.dogImage.set(imageUrl),
      error: (error) => console.error(error),
    });
  }

  private fetchDogImage(): Observable<string> {
    this.dogImage.set('');
    return this.getDogImage();
  }

  private getDogImage(): Observable<string> {
    return this.http.get<{ message: string }>(this.apiUrl).pipe(
      map((response) => response.message),
      catchError(this.handleError)
    );
  }

  private handleError(error: HttpErrorResponse) {
    return throwError(() => new Error('An error occurred while fetching the image.'));
  }
}
