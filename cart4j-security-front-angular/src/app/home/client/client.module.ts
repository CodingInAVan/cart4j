import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ClientComponent } from './client.component';
import {ClientService} from '../../services/client.service';
import {ClientAddModule} from './client-add/client-add.module';
import {ClientEditModule} from './client-edit/client-edit.module';
import {RouterModule} from '@angular/router';
import {ClientListModule} from './client-list/client-list.module';
import {ClientViewModule} from './client-view/client-view.module';

@NgModule({
  declarations: [ClientComponent],
  imports: [
    CommonModule,
    ClientAddModule,
    ClientEditModule,
    ClientListModule,
    ClientViewModule,
    RouterModule
  ],
  providers: [ClientService]
})
export class ClientModule { }
