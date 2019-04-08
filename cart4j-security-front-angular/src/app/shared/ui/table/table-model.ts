export interface TableModel {
  readonly label: string;
  readonly column: string;
  readonly columnType?: ColumnType;
}
enum ColumnType {
  TEXT,
  SELECT
}

export interface TableSetting {
  readonly width: any;
}
