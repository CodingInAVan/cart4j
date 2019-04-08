import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { TableComponent } from './table.component';
import {TableRowModule} from './table-row/table-row.module';

@NgModule({
  declarations: [TableComponent],
  exports: [
    TableComponent
  ],
  imports: [
    CommonModule,
    TableRowModule
  ]
})
export class TableModule { }
