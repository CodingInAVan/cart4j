import {Component, EventEmitter, Input, OnChanges, OnInit, Output, SimpleChanges} from '@angular/core';

@Component({
  selector: 'app-paginator',
  templateUrl: './paginator.component.html',
  styleUrls: ['./paginator.component.css']
})
export class PaginatorComponent implements OnInit, OnChanges {

  constructor() { }
  @Input() totalRecords;
  @Input() pageSize;
  @Input() page;

  @Output() pageSizeChange: EventEmitter<any> = new EventEmitter();
  @Output() pageChange: EventEmitter<any> = new EventEmitter();
  totalPages = [];

  ngOnInit() {
  }

  ngOnChanges(changes: SimpleChanges): void {
    this.update();
  }

  update() {
    const totPages = (this.totalRecords + (this.pageSize - 1)) / this.pageSize;
    this.totalPages = [];
    for (let i = 1; i <= totPages; i++) {
      this.totalPages.push(i);
    }
  }

  changePage(page) {
    this.pageChange.emit(page);
  }

  changePageSize(event) {
    this.pageSize = event.target.value;
    this.update();
    this.pageSizeChange.emit(this.pageSize);
  }
}
