import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { TableRowComponent } from './table-row.component';

@NgModule({
  declarations: [TableRowComponent],
  exports: [
    TableRowComponent
  ],
  imports: [
    CommonModule
  ]
})
export class TableRowModule { }
