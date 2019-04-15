import {Component, EventEmitter, Input, OnInit, Output, ViewChild} from '@angular/core';
import {InputFormSetting} from '../../model/ui-model';
import {FormControl, FormGroup, Validators} from '@angular/forms';
import {COMMA, ENTER} from '@angular/cdk/keycodes';
import {MatAutocomplete} from '@angular/material';
@Component({
  selector: 'app-add-form',
  templateUrl: './add.component.html',
  styleUrls: ['./add.component.css']
})
export class AddComponent implements OnInit {
  selectable = true;
  removable = true;
  @Input() inputFields: InputFormSetting[];
  form: FormGroup;
  @Input() model;
  addOnBlur = true;
  @Output() onSubmit: EventEmitter<any> = new EventEmitter();

  readonly separatorKeysCodes: number[] = [ENTER, COMMA];

  constructor() { }

  ngOnInit() {
    const formGroup = {};
    console.log(this.inputFields);
    this.inputFields.forEach(field => {
      let validateFn;
      const validateOptions = [];
      if (field.required) {
        validateOptions.push(Validators.required);
      }
      validateFn = Validators.compose(validateOptions);
      formGroup[field.columnName] = new FormControl('', validateFn);
    });
    this.form = new FormGroup(formGroup);
  }

  submitForm() {
    this.onSubmit.emit();
  }
  addChip(event, columnName) {
    if (this.model.hasOwnProperty(columnName)) {
      if (this.model[columnName] == null) {
        this.model[columnName] = [];
      }

      const input = event.input;
      const value = event.value;
      // Add our fruit
      if ((value || '').trim()) {
        this.model[columnName].push(value.trim());
      }
      // Reset the input value
      if (input) {
        input.value = '';
      }
    }
  }
  selectedChip(event, columnName) {
    if (this.model.hasOwnProperty(columnName)) {
      if (this.model[columnName] == null) {
        this.model[columnName] = [];
      }
      const value = event.option.value;
      // Add our fruit
      if ((value || '').trim()) {
        this.model[columnName].push(value.trim());
      }
    }
  }
  removeChip(event, columnName) {
    if (this.model.hasOwnProperty(columnName)) {
      for (let i = 0; i < this.model[columnName].length; i++) {
        if (this.model[columnName][i] === event) {
          this.model[columnName].splice(i, 1);
        }
      }
    }
  }
}
