import { Component, OnInit } from '@angular/core';
import {ClientService} from '../../../services/client.service';
import {Client} from '../../../model';
import {TableModel} from '../../../shared/ui/table/table-model';

@Component({
  selector: 'app-client-list',
  templateUrl: './client-list.component.html',
  styleUrls: ['./client-list.component.css']
})
export class ClientListComponent implements OnInit {

  searchKey = '';
  totalRecords = 0;
  pageSize = 5;
  page = 0;
  list: Client[] = [];
  columnHeaders: TableModel[] = [
    {
      column: 'id',
      label: 'ID'
    },
    {
      column: 'clientUniqueId',
      label: 'Client Unique Id'
    },
    {
      column: 'grantTypes',
      label: 'Grant Types'
    },
    {
      column: 'options',
      label: ''
    }
  ];
  constructor(private clientService: ClientService) {
  }
  ngOnInit() {
    this.getList();
  }
  changePage(page) {
    this.page = page - 1;
    this.getList();
  }
  changePageSize(pageSize) {
    this.pageSize = pageSize;
    this.getList();
  }
  getList() {
    this.clientService.getList(this.searchKey, this.page, this.pageSize).subscribe(res => {
      this.list = res.list;
      this.totalRecords = res.totalRecords;
      this.page = res.offset;
    });
  }
}
