import { Component, OnInit } from '@angular/core';
import {LoginRequest, LoginStatus} from "../../model";
import {AuthService} from "../../services/auth.service";
import {ActivatedRoute, Router} from "@angular/router";

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {
  returnUrl: string;
  errorMessage = '';
  loginRequest: LoginRequest = {
    username: '',
    password: ''
  };
  loginStatus: LoginStatus = {
    code: '',
    message: ''
  };

  constructor(private authService: AuthService, private router: Router,
              private route: ActivatedRoute) { }

  ngOnInit() {
    this.returnUrl = this.route.snapshot.queryParams['returnUrl'] || '/';
  }

  login (loginForm) {
    console.log(this.returnUrl);
    this.authService.login(this.loginRequest).subscribe(() => {
      this.router.navigate([this.returnUrl]).then();
    }, error => {
      if (error.status === 401) {
        this.errorMessage = 'invalid username and password';
      } else {
        this.errorMessage = 'Oops sorry there is an issue to login. Please try it again later'
      }
    });
    loginForm.reset();
  }
}
