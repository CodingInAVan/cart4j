import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ClientAddComponent } from './client-add.component';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {AddModule} from '../../../shared/ui/form/add/add.module';

@NgModule({
  declarations: [ClientAddComponent],
  imports: [
    CommonModule,
    FormsModule,
    ReactiveFormsModule,
    AddModule
  ]
})
export class ClientAddModule { }
