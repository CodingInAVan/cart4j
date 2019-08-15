import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ClientListComponent } from './client-list.component';
import {RouterModule} from '@angular/router';
import {PaginatorModule} from '../../../shared/ui/paginator/paginator.module';
import {TableModule} from '../../../shared/ui/table/table.module';
import {MaterialModule} from '../../../shared/ui/material/material.module';

@NgModule({
  declarations: [ClientListComponent],
  imports: [
    CommonModule,
    RouterModule,
    PaginatorModule,
    TableModule,
    MaterialModule
  ],
  exports: [
    ClientListComponent
  ]
})
export class ClientListModule { }
