import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { TableComponent } from './table.component';
import {MaterialModule} from '../material/material.module';
import {TableRowModule} from './table-row/table-row.module';

@NgModule({
  declarations: [TableComponent],
  exports: [
    TableComponent
  ],
  imports: [
    CommonModule,
    TableRowModule,
    MaterialModule
  ]
})
export class TableModule { }
