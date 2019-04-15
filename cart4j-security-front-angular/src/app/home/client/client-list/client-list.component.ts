import { Component, OnInit } from '@angular/core';
import {ClientService} from '../../../services/client.service';
import {Client} from '../../../model';
import {ColumnModel} from '../../../shared/ui/model/ui-model';
import {Router} from '@angular/router';

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
  columnHeaders: ColumnModel[] = [
    {
      columnName: 'id',
      label: 'ID'
    },
    {
      columnName: 'clientUniqueId',
      label: 'Client Unique Id'
    },
    {
      columnName: 'grantTypes',
      label: 'Grant Types'
    }
  ];
  constructor(private clientService: ClientService, private router: Router) {
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
  clickRow(item) {
    this.router.navigate(['/clients/view/' + item.id]).then();
  }
}
