import { Injectable } from '@angular/core';
import {Observable} from "rxjs";
import {Client, Page} from "../model";
import {CookieService} from "ngx-cookie-service";
import {HttpClient, HttpHeaders, HttpParams} from "@angular/common/http";
import {environment} from "../../environments/environment";

const CLIENT_API_ENDPOINT_LIST = environment.apiEndPoint + "/api/auth/clients";

@Injectable()
export class ClientService {
  getList(searchKey: string, pageIndex=0, pageSize=5): Observable<Page<Client>> {
    const headers = new HttpHeaders({
      'Content-type': 'application/json',
      'Authorization': 'Bearer ' + this.cookieService.get('access_token')
    });
    return this.http.get<Page<Client>>(CLIENT_API_ENDPOINT_LIST, {
      headers: headers,
      params: new HttpParams()
        .set('page', pageIndex.toString())
        .set('size', pageSize.toString())
        .set('searchKey', searchKey)
    }).pipe();
  }
  constructor(private http: HttpClient, private cookieService:CookieService) { }
}
