import { HttpClient, HttpClientModule } from '@angular/common/http';
import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { Router } from '@angular/router';
import { Login } from './models/Login';
import { LoginService } from './service/LoginService';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [FormsModule, HttpClientModule],
  templateUrl: './login.component.html',
  styleUrl: './login.component.css',
})
export class LoginComponent {
  loginObj: Login;
  constructor(
    private http: HttpClient,
    private router: Router,
    private loginService: LoginService
  ) {
    this.loginObj = new Login();
  }

  async onLogin() {
    this.loginService.signIn(this.loginObj).subscribe(
      (response) => {
        this.router.navigateByUrl('/dashboard');
      },
      (error) => {
        console.error(error);
      }
    );
  }

  redirectToSignUp() {
    this.router.navigateByUrl('/signup');
  }
}
