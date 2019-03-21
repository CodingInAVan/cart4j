import { Component, OnInit } from '@angular/core';
import {ClientService} from "../../services/client.service";
import {Client} from "../../model";
import {MatPaginator, MatTableDataSource} from '@angular/material';
import {SelectionModel} from "@angular/cdk/collections";

@Component({
  selector: 'app-client',
  templateUrl: './client.component.html',
  styleUrls: ['./client.component.css']
})
export class ClientComponent implements OnInit {
  searchKey='';
  totalRecords=0;
  pageSize=5;
  page=0;
  dataSource = new MatTableDataSource<Client>();
  selection = new SelectionModel<Client>(true, []);
  displayedColumns = ['select', 'id', 'clientUniqueId', 'grantTypes'];
  constructor(private clientService: ClientService) {
  }
  applyFilter(value) {
    this.searchKey = value;
    this.getList();
  }
  ngOnInit() {
    this.getList();
  }
  getList() {
    this.clientService.getList(this.searchKey, this.page, this.pageSize).subscribe(res => {
      this.dataSource.data = res.list;
      this.totalRecords = res.totalRecords;
      this.page = res.offset;
    })
  }

  isAllSelected() {
    const numSelected = this.selection.selected.length;
    const numRows = this.dataSource.data.length;
    return numSelected === numRows;
  }

  /** Selects all rows if they are not all selected; otherwise clear selection. */
  masterToggle() {
    this.isAllSelected() ?
      this.selection.clear() :
      this.dataSource.data.forEach(row => this.selection.select(row));
  }
  checkboxLabel(row?: Client): string {
    if (!row) {
      return `${this.isAllSelected() ? 'select' : 'deselect'} all`;
    }
    return `${this.selection.isSelected(row) ? 'deselect' : 'select'} row ${row.id + 1}`;
  }
}
