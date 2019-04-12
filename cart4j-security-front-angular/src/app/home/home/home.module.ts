import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { HomeComponent } from './home.component';
import {HomeRoutingModule} from './home-routing.module';
import {AuthGuard} from '../../auth/guard/auth.guard';
import {LeftMenuModule} from '../left-menu/left-menu.module';
import {ClientModule} from '../client/client.module';
import {ResourceModule} from '../resource/resource.module';
import {HTTP_INTERCEPTORS, HttpClientModule} from '@angular/common/http';
import {AppHttpInterceptorService} from '../../shared/services/app-http-interceptor.service';

@NgModule({
  declarations: [HomeComponent],
  imports: [
    CommonModule,
    HomeRoutingModule,
    LeftMenuModule,
    ClientModule,
    ResourceModule
  ],
  providers: [AuthGuard]
})
export class HomeModule { }
