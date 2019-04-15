import {Component, HostListener, Input, OnInit} from '@angular/core';
import {ColumnModel} from '../../model/ui-model';

@Component({
  selector: '[app-table-row]',
  templateUrl: './table-row.component.html',
  styleUrls: ['./table-row.component.css']
})
export class TableRowComponent implements OnInit {
  @Input() item = [];
  @Input() columnHeaders: ColumnModel[];
  hover = false;
  constructor() { }

  @HostListener('mouseenter')
  onMounseEnter() {
    this.hover = true;
  }
  @HostListener('mouseleave')
  onMouseLeave() {
    this.hover = false;
  }

  ngOnInit() {
  }

}
