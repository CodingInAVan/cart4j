import { Component, OnInit } from '@angular/core';
import {Client} from '../../../model';
import {ActivatedRoute, ParamMap, Router} from '@angular/router';
import {ClientService} from '../../../services/client.service';
import {switchMap} from 'rxjs/internal/operators/switchMap';
import {Observable} from 'rxjs';
import {Location} from '@angular/common';

@Component({
  selector: 'app-client-view',
  templateUrl: './client-view.component.html',
  styleUrls: ['./client-view.component.css']
})
export class ClientViewComponent implements OnInit {
  client: Client;
  constructor(private route: ActivatedRoute, private router: Router, private clientService: ClientService) { }
  goList() {
    this.router.navigate(['/clients/']).then();
  }
  ngOnInit() {
    this.route.paramMap.pipe(
      switchMap((params: ParamMap) => this.clientService.get(params.get('id')))
    ).subscribe(client => {
      this.client = client;
    });
  }

}
