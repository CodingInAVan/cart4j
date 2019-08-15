import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ClientViewComponent } from './client-view.component';
import {MaterialModule} from '../../../shared/ui/material/material.module';

@NgModule({
  declarations: [ClientViewComponent],
  imports: [
    CommonModule,
    MaterialModule
  ],
  exports: [ClientViewComponent]
})
export class ClientViewModule { }
