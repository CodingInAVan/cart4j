import { Component, OnInit } from '@angular/core';
import { Location } from '@angular/common';
import {Client, ClientInput, GRANT_TYPES} from '../../../model';
import {ClientService} from '../../../services/client.service';
import {InputFormSetting} from '../../../shared/ui/model/ui-model';
import {Router} from '@angular/router';

@Component({
  selector: 'app-client-add',
  templateUrl: './client-add.component.html',
  styleUrls: ['./client-add.component.css']
})
export class ClientAddComponent implements OnInit {
  inputModel: InputFormSetting[] = [
    {
      required: true,
      columnName: 'clientUniqueId',
      label: 'Client Unique Id',
      className: 'full-width',
      columnType: 'text',
      valueType: 'string'
    },
    {
      required: true,
      columnName: 'clientSecret',
      label: 'Client Secret',
      className: 'full-width',
      columnType: 'text',
      valueType: 'password'
    },
    {
      required: true,
      columnName: 'grantTypes',
      className: 'full-width',
      label: 'Grant Types',
      columnType: 'chip',
      autoComplete: true,
      autoCompleteOptions: GRANT_TYPES
    }
  ];
  modelInput: ClientInput = {
    clientUniqueId: '',
    clientSecret: '',
    grantTypes: []
  };
  constructor(private clientService: ClientService, private _location: Location, private router: Router) {

  }

  ngOnInit() {
  }

  submit() {
    console.log(this.modelInput);
    const client = {
      clientUniqueId: this.modelInput.clientUniqueId,
      grantTypes: this.modelInput.grantTypes.join(','),
      clientSecret: this.modelInput.clientSecret
    };
    this.clientService.addClient(client).subscribe( data => {
        this.router.navigate(['/clients']).then();
      }
      , err => {
        // handling the error
      });
  }

  cancel() {
    this._location.back();
  }


}
