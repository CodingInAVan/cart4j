import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { AddComponent } from './add.component';
import {TableRowModule} from '../../table/table-row/table-row.module';
import {MaterialModule} from '../../material/material.module';
import {ReactiveFormsModule} from '@angular/forms';

@NgModule({
  declarations: [AddComponent],
  exports: [
    AddComponent
  ],
  imports: [
    CommonModule,
    MaterialModule,
    ReactiveFormsModule
  ]
})
export class AddModule { }
