import {Injectable} from '@angular/core';
import {AuthService} from '../../services/auth.service';
import {Router} from '@angular/router';
import {
  HttpErrorResponse,
  HttpEvent,
  HttpHandler,
  HttpInterceptor,
  HttpRequest,
  HttpResponse
} from '@angular/common/http';
import {Observable} from 'rxjs';
import {environment} from '../../../environments/environment';
import {tap} from 'rxjs/internal/operators/tap';

@Injectable({
  providedIn: 'root'
})

@Injectable()
export class AppHttpInterceptorService implements HttpInterceptor {
  constructor(public auth: AuthService, private router: Router) {}
  intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    if (!environment.production) {
      console.log('HttpInterceptor is running');
    }
    if (this.auth.isLoggedIn.getValue() && req.url.search(/oauth\/token/gi) === -1) {
      const authReq = req.clone({
        headers: req.headers.set('Authorization', `Bearer ${this.auth.getToken()}`)});
      if (!environment.production) {
        console.log('interceptor running with new headers');
      }

      return next.handle(authReq).pipe(
        tap((event: HttpEvent<any>) => {
          if (event instanceof HttpResponse) {
            if (!environment.production) {
              console.log('TAP function', event);
            }
          }
        }, (err: any) => {
          if (err instanceof HttpErrorResponse) {
            if (err.status === 401) {
              localStorage.removeItem('token');
              this.router.navigate(['/login']).then();
            }
          }
        })
      );
    } else {
      return next.handle(req);
    }
  }

}
