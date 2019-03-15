import { Component, OnInit } from '@angular/core';
import {LoginRequest} from "../../model";

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {
  errorMessage = '';
  loginRequest: LoginRequest = {
    username: '',
    password: ''
  };

  constructor() { }

  ngOnInit() {
  }

  login (loginForm) {

  }
}
