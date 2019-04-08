import { Component, OnInit } from '@angular/core';
import { Location } from '@angular/common';
import {Client} from '../../../model';
import {ClientService} from '../../../services/client.service';

@Component({
  selector: 'app-client-add',
  templateUrl: './client-add.component.html',
  styleUrls: ['./client-add.component.css']
})
export class ClientAddComponent implements OnInit {
  client: Client;

  constructor(private clientService: ClientService, private _location: Location) {

  }

  ngOnInit() {
  }

  submit() {
    this.clientService.addClient(this.client).subscribe( data => {
        // success ...
      }
      , err => {
        // handling the error
      });
  }

  cancel() {
    this._location.back();
  }


}
