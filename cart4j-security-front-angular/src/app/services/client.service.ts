import { Injectable } from '@angular/core';
import {Observable} from 'rxjs';
import {Client, Page} from '../model';
import {CookieService} from 'ngx-cookie-service';
import {HttpClient, HttpHeaders, HttpParams} from '@angular/common/http';
import {environment} from '../../environments/environment';
import {HandleError, HttpErrorHandler} from '../shared/services/http-handle-error.service';

const CLIENT_API_ENDPOINT_LIST = environment.apiEndPoint + '/api/auth/clients';
const CLIENT_API_ENDPOINT_ID = environment.apiEndPoint + '/api/auth/clients/';
const CLIENT_API_ENDPOINT_POST = environment.apiEndPoint + '/api/auth/clients';

@Injectable()
export class ClientService {
  private  handleError: HandleError;

  constructor(private http: HttpClient, httpErrorHandler: HttpErrorHandler, private cookieService: CookieService) {
    this.handleError  =
      httpErrorHandler.createHandleError('ClientService');
  }
  private getHeader() {
    return new HttpHeaders({
      'Content-type': 'application/json',
      'Authorization': 'Bearer ' + this.cookieService.get('access_token')
    });
  }
  getList(searchKey: string, pageIndex = 0, pageSize = 5): Observable<Page<Client>> {
    return this.http.get<Page<Client>>(CLIENT_API_ENDPOINT_LIST, {
      headers: this.getHeader(),
      params: new HttpParams()
        .set('page', pageIndex.toString())
        .set('size', pageSize.toString())
        .set('searchKey', searchKey)
    }).pipe();
  }
  get(id: string): Observable<Client> {
    return this.http.get<Client>(CLIENT_API_ENDPOINT_ID + id, {headers: this.getHeader()}).pipe();
  }

  addClient(client: Client): Observable<Client> {
    return this.http.post<Client>(CLIENT_API_ENDPOINT_POST, client, {headers: this.getHeader()}).pipe();
  }

  editClient(id: number, client: Client): Observable<Client> {
    return this.http.put<Client>(CLIENT_API_ENDPOINT_ID + id, client, {headers: this.getHeader()}).pipe();
  }

  deleteClient(id: number): Observable<Client> {
    return this.http.delete<Client>(CLIENT_API_ENDPOINT_ID + id, {headers: this.getHeader()}).pipe();
  }
}
