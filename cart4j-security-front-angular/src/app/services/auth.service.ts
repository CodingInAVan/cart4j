import { Injectable } from '@angular/core';
import {HttpClient, HttpErrorResponse} from "@angular/common/http";
import {LoginRequest, TokenReponse} from "../model";
import {environment} from "../../environments/environment";
import {catchError, map} from "rxjs/operators";
import {throwError} from "rxjs";


const LOGIN_URL = environment.apiEndPoint + "/api/auth/login"

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private token: string;

  constructor(private http: HttpClient) { }

  login(login: LoginRequest) {
    return this.http.post<TokenReponse>(LOGIN_URL, login).pipe(map(res=> {
      this.saveToken(res.accessToken);
    }),
      catchError(error => this.handleError(error))
      );
  }


  private saveToken(token: string): void {
      localStorage.setItem('cart4j-security-server-token', token);
    this.token = token;
  }

  public getToken(): string {
    if (!this.token) {
      this.token = localStorage.getItem('cart4j-security-server-token');
    }
    return this.token;
  }

  public logout(): void {
    this.token = '';
    window.localStorage.removeItem('api-token');
  }

  public isLoggedIn(): boolean {
    const token: string = this.getToken();
    if (token !== undefined && token != null && token !== 'undefined') {
      return true;
    }
    return false;
  }

  private handleError(error: HttpErrorResponse) {
    if (error.error instanceof ErrorEvent) {
      console.error('An error occurred:', error.error.message);
    } else {
      return throwError(error);
    }
    return throwError('Oops something wrong happen here; please try it again later.');
  }
}
