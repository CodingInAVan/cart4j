import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import {AuthService} from "./services/auth.service";
import {LoginModule} from "./auth/login/login.module";
import {BrowserAnimationsModule} from "@angular/platform-browser/animations";
import {HomeModule} from "./home/home/home.module";
import {HttpClientModule} from "@angular/common/http";
import {CookieService} from "ngx-cookie-service";

@NgModule({
  declarations: [
    AppComponent
  ],
  imports: [
    HttpClientModule,
    BrowserModule,
    BrowserAnimationsModule,
    AppRoutingModule,
    LoginModule,
    HomeModule,
  ],
  providers: [AuthService, CookieService],
  bootstrap: [AppComponent]
})
export class AppModule { }
