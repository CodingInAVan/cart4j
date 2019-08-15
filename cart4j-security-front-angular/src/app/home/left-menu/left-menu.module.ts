import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import {LeftMenuComponent} from "./left-menu.component";
import {RouterModule} from "@angular/router";

@NgModule({
  declarations: [LeftMenuComponent],
  imports: [
    CommonModule,
    RouterModule
  ],
  exports: [LeftMenuComponent]
})
export class LeftMenuModule { }
