import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { HomeComponent } from './home.component';
import {HomeRoutingModule} from "./home-routing.module";
import {AuthGuard} from "../../auth/guard/auth.guard";
import {ResourcesModule} from "../../resources/resources/resources.module";

@NgModule({
  declarations: [HomeComponent],
  imports: [
    CommonModule,
    HomeRoutingModule,
    ResourcesModule
  ],
  providers: [AuthGuard]
})
export class HomeModule { }
