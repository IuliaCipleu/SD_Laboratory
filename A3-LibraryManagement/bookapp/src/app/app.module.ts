// // // app.module.ts
// // import { NgModule } from '@angular/core';
// // import { BrowserModule } from '@angular/platform-browser';
// // import { HttpClientModule } from '@angular/common/http';
// // import { FormsModule } from '@angular/forms';
// // import { CommonModule } from '@angular/common'; // Import CommonModule

// // import { AppComponent } from './app.component';
// // import { BookService } from './book.service';

// // @NgModule({
// //   declarations: [
// //     AppComponent
// //   ],
// //   imports: [
// //     BrowserModule,
// //     HttpClientModule, FormsModule
// //   ],
// //   providers: [BookService],
// //   bootstrap: [AppComponent]
// // })
// // export class AppModule { }
// import { NgModule } from '@angular/core';
// import { BrowserModule } from '@angular/platform-browser';
// import { HttpClientModule } from '@angular/common/http';
// import { FormsModule } from '@angular/forms';
// import { RouterModule } from '@angular/router'; // Import RouterModule
// import { CommonModule } from '@angular/common'; 

// import { AppComponent } from './app.component';
// import { BookService } from './book.service';
// import { UserLoginComponent } from './user-login/user-login.component';
// import { routes } from './app.routes'; // Import routes

// @NgModule({
//   declarations: [
//     AppComponent,
//     UserLoginComponent // Include UserLoginComponent here
//   ],
//   imports: [
//     BrowserModule,
//     HttpClientModule,
//     FormsModule,
//     RouterModule.forRoot(routes) // Configure RouterModule with routes
//   ],
//   providers: [BookService],
//   bootstrap: [AppComponent]
// })
// export class AppModule { }
import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { HttpClientModule } from '@angular/common/http';
import { FormsModule } from '@angular/forms';
import { RouterModule } from '@angular/router'; // Import RouterModule
import { CommonModule } from '@angular/common'; 

import { AppComponent } from './app.component';
import { BookService } from './book.service';
import { UserLoginComponent } from './user-login/user-login.component';
import { routes } from './app.routes'; // Import routes
import { BookComponent } from './book.component';
import { AuthorComponent } from './author.component';
import { AuthorService } from './author.service';
import { BorrowService } from './borrow.service';
import { BorrowerService } from './borrower.service';
import { BorrowerComponent } from './borrower.component';
import { BorrowComponent } from './borrow.component';

@NgModule({
  declarations: [
    AppComponent,
    BookComponent,
    AuthorComponent,
    BorrowerComponent,
    BorrowComponent,
    UserLoginComponent // Include UserLoginComponent here
  ],
  imports: [
    BrowserModule,
    HttpClientModule,
    FormsModule,
    RouterModule.forRoot(routes) // Configure RouterModule with routes
  ],
  providers: [BookService, AuthorService, BorrowService, BorrowerService],
  bootstrap: [AppComponent]
})
export class AppModule { }
