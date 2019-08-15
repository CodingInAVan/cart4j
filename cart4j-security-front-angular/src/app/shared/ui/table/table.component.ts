import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {ColumnModel} from '../model/ui-model';

@Component({
  selector: 'app-table',
  templateUrl: './table.component.html',
  styleUrls: ['./table.component.css']
})
export class TableComponent implements OnInit {
  @Input() data = [];
  @Input() set columnHeaders(columnHeaders: ColumnModel[]) {
    this.columnModels = columnHeaders;
    for (const columnHeader of this.columnModels) {
      this.displayedColumns.push(columnHeader.label);
    }
  }
  columnModels: ColumnModel[];
  displayedColumns = [];

  @Output() clickRow: EventEmitter<any> = new EventEmitter();
  constructor() { }

  clickItem(event, item) {
    this.clickRow.emit(item);
  }
  ngOnInit() {
  }

}
