import {Component, Input, OnInit} from '@angular/core';
import {TableModel} from './table-model';

@Component({
  selector: 'app-table',
  templateUrl: './table.component.html',
  styleUrls: ['./table.component.css']
})
export class TableComponent implements OnInit {
  @Input() data = [];
  @Input() columnHeaders: TableModel[];
  constructor() { }

  ngOnInit() {
  }

}
