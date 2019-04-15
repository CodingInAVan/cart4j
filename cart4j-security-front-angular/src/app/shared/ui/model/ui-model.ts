export interface ColumnModel {
  readonly label: string;
  readonly columnName: string;
  readonly columnType?: string;
}
export interface InputFormSetting {
  required: boolean;
  columnName: string;
  label: string;
  columnType: string;
  className: string;
  valueType?: string;
  minLength?: number;
  maxLength?: number;
  autoComplete?: boolean;
  autoCompleteOptions?: string[];
}
export interface TableSetting {
  readonly width: any;
}
