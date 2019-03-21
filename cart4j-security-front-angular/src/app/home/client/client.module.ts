import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ClientComponent } from './client.component';
import {ClientService} from "../../services/client.service";
import {MaterialModule} from "../../shared/material/material.module";

@NgModule({
  declarations: [ClientComponent],
  imports: [
    CommonModule,
    MaterialModule
  ],
  providers: [ClientService]
})
export class ClientModule { }
