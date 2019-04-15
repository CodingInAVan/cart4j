import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ClientViewComponent } from './client-view.component';

@NgModule({
  declarations: [ClientViewComponent],
  imports: [
    CommonModule
  ],
  exports: [ClientViewComponent]
})
export class ClientViewModule { }
