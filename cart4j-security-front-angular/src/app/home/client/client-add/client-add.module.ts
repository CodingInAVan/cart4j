import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ClientAddComponent } from './client-add.component';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';

@NgModule({
  declarations: [ClientAddComponent],
  imports: [
    CommonModule,
    FormsModule,
    ReactiveFormsModule
  ]
})
export class ClientAddModule { }
