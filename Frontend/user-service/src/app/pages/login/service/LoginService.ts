import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { ApiErrorResponse } from '../models/ApiErrorResponse';
import { catchError, throwError } from 'rxjs';

@Injectable({
  providedIn: 'any',
})
export class LoginService {
  constructor(private httpClient: HttpClient) {}

  signIn(payload: any) {
    return this.httpClient.post<any>('http://localhost:8080/user/signIn', payload).pipe(catchError(this.handleError));
  }

  handleError(error: HttpErrorResponse) {
    debugger;
    if (error.error instanceof ErrorEvent) {
      alert('Unknown error Occurred!!');
    } else {
      const apiError: ApiErrorResponse = {
        status: error.error.status,
        timestamp: error.error.timestamp,
        message: error.error.message,
        reason: error.error.reason,
        errors: error.error.errors,
      };
      alert(apiError.status);
    }
    return throwError('Error Occurred from Server!!');
  }
}
