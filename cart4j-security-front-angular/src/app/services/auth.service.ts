import { Injectable } from '@angular/core';
import {HttpClient, HttpErrorResponse, HttpHeaders} from '@angular/common/http';
import {LoginRequest, TokenReponse} from '../model';
import {environment} from '../../environments/environment';
import {catchError, map} from 'rxjs/operators';
import {BehaviorSubject, Observable, Observer, throwError} from 'rxjs';
import {CookieService} from 'ngx-cookie-service';


const LOGIN_URL = environment.apiEndPoint + '/oauth/token';
const CLIENT_ID = environment.clientId;
const CLIENT_SECRET = environment.clientSecret;

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  constructor(private http: HttpClient, private cookieService: CookieService) {
    if (this.getToken()) {
      this.isLoggedIn = new BehaviorSubject<boolean>(true);
    } else {
      this.isLoggedIn = new BehaviorSubject<boolean>(false);
    }
  }
  isLoggedIn: BehaviorSubject<boolean>;

  redirectUrl: string;

  private handleError(error: HttpErrorResponse) {
    if (error.error instanceof ErrorEvent) {
      console.error('An error occurred:', error.error.message);
    } else {
      return throwError(error);
    }
    return throwError('Oops something wrong happen here; please try it again later.');
  }

  login(login: LoginRequest) {
    const params = new URLSearchParams();
    params.append('username', login.username);
    params.append('password', login.password);
    params.append('grant_type', 'password');
    const headers = new HttpHeaders(
      {'Content-type': 'application/x-www-form-urlencoded; ' + 'charset=utf-8',
        'Authorization': 'Basic ' + btoa(CLIENT_ID + ':' + CLIENT_SECRET)
      });

    return this.http.post<TokenReponse>(LOGIN_URL, params.toString(), {headers: headers}).pipe(map(res => {
      this.saveToken(res);
    }),
      catchError(error => this.handleError(error))
      );
  }

  private saveToken(token: TokenReponse): void {
    this.cookieService.deleteAll();
    const expireDate = new Date().getTime() * (1000 * token.expires_in);
    this.cookieService.set('access_token', token.access_token, expireDate);
    this.cookieService.set('scope', token.scope);
    this.changeLoginStatus(true);
  }

  public getToken(): string {
    return this.cookieService.get('access_token');
  }
  logout() {
    this.cookieService.set('access_token', '');
    this.cookieService.set('scope', '');
    this.cookieService.delete('access_token');
    this.cookieService.delete('scope');
    this.changeLoginStatus(false);
  }

  changeLoginStatus(status: boolean) {
    this.isLoggedIn.next(status);
  }
}
