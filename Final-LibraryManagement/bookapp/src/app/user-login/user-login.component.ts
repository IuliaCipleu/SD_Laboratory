import { Component, OnInit } from '@angular/core';
import { UserService } from './user.service';
import { HttpErrorResponse } from '@angular/common/http';
import { Router } from '@angular/router';
import { LoginDTO } from './login.dto';
import { AuthorService } from '../author.service';
import { User } from './user';
import { v4 as uuidv4 } from 'uuid';
import { Role } from './role';

@Component({
  selector: 'app-user-login',
  templateUrl: './user-login.component.html',
  styleUrls: ['./user-login.component.css']
})
export class UserLoginComponent implements OnInit {
  usernameOrEmail: string = '';
  password: string = '';
  registerRole: string = ''; 
  id: string = '';
  registerUsername: string = '';
  registerName: string = '';
  registerSurname : string = '';
  registerEmail: string = '';
  registerPassword = '';
  registerDateOfBirth: Date = new Date();

  constructor(private userService: UserService, private router: Router, private authorService: AuthorService) {}

  ngOnInit() {
    this.id = uuidv4();
  }

  login(): void {
    if (!this.usernameOrEmail || !this.password) {
      alert('Please provide both username/email and password.');
      return;
    }

    const loginDTO: LoginDTO = {
      usernameOrEmail: this.usernameOrEmail, 
      password: this.password
    };

    console.log('Sending login request...');

    this.userService.login(loginDTO).subscribe(
      (response: string) => {
        console.log('Login successful:', response);
        this.userService.setToken(response);
        //this.authorService.setToken(response);
        this.router.navigate(['/books']);
      },
      (error: HttpErrorResponse) => {
        alert(`Login failed for user ${this.usernameOrEmail}. Please check your credentials and try again.`);
        console.error('Login failed:', error);
      }
    );
    
  }

  logout(): void {
    this.userService.logout();
    alert('Logged out successfully!');
    this.router.navigate(['/login']);
  }

  register(): void {
    if (!this.registerName || !this.registerSurname || !this.registerDateOfBirth || !this.registerRole ||
        !this.registerUsername || !this.registerEmail || !this.registerPassword) {
      alert('Please fill all required fields.');
      return;
    }

    const user: User = {
      id: this.id,
      name: this.registerName,
      surname: this.registerSurname,
      dateOfBirth: this.registerDateOfBirth,
      username: this.registerUsername,
      email: this.registerEmail,
      password: this.registerPassword,
      roles: []
    };

    if (this.registerRole === 'admin') {
      this.registerAdmin(user);
    } else if (this.registerRole === 'simpleUser') {
      this.registerSimpleUser(user);
    } else {
      alert('Please select a role.');
    }
  }

  registerAdmin(user: User): void {
    this.userService.registerAdmin(user).subscribe(
      (response: any) => {
        console.log('Admin registered successfully:', response);
        alert('Admin registered successfully!');
        // Optionally, you can automatically log in the newly registered admin
        // this.loginAfterRegistration(signUpDTO.username, signUpDTO.password);
      },
      (error: HttpErrorResponse) => {
        alert('Admin registration failed. Please try again.');
        console.error('Admin registration failed:', error);
      }
    );
  }

  registerSimpleUser(user: User): void {
    this.userService.registerSimpleUser(user).subscribe(
      (response: any) => {
        console.log('Simple user registered successfully:', response);
        alert('Simple user registered successfully!');
        // Optionally, you can automatically log in the newly registered user
        // this.loginAfterRegistration(signUpDTO.username, signUpDTO.password);
      },
      (error: HttpErrorResponse) => {
        alert('User registration failed. Please try again.');
        console.error('User registration failed:', error);
      }
    );
  }

  clearRegisterFields(): void {
    this.registerName = '';
    this.registerSurname = '';
    this.registerDateOfBirth = new Date();
    this.registerRole = '';
    this.registerUsername = '';
    this.registerEmail = '';
    this.registerPassword = '';
  }

  loginAfterRegistration(username: string, password: string): void {
    const loginDTO: LoginDTO = {
      usernameOrEmail: username,
      password: password
    };

    this.userService.login(loginDTO).subscribe(
      (response: string) => {
        console.log('Login successful:', response);
        this.userService.setToken(response);
        this.router.navigate(['/books']);
      },
      (error: HttpErrorResponse) => {
        alert('Login failed after registration. Please log in manually.');
        console.error('Login failed after registration:', error);
      }
    );
  }

}

