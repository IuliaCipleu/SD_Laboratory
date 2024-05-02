// // // import { Routes } from '@angular/router';

// // // export const routes: Routes = [];
// // // //{ path: 'login', component: UserLoginComponent },
// // import { NgModule } from '@angular/core';
// // import { RouterModule, Routes } from '@angular/router';
// // import { UserLoginComponent } from './user-login/user-login.component'; // Import UserLoginComponent
// // import { AppComponent } from './app.component'; // Import BooksComponent

// // const routes: Routes = [
// //   { path: '', component: UserLoginComponent }, // Set UserLoginComponent as the default route
// //   { path: 'books', component: AppComponent },
// //   // Add more routes here if needed
// // ];

// // @NgModule({
// //   imports: [RouterModule.forRoot(routes)],
// //   exports: [RouterModule]
// // })
// // export class AppRoutingModule { }
// import { Routes } from '@angular/router';
// import { UserLoginComponent } from './user-login/user-login.component';
// import { AppComponent } from './app.component';

// export const routes: Routes = [
//   { path: '', component: UserLoginComponent }, // Set UserLoginComponent as the default route
//   { path: 'books', component: AppComponent },
//   // Add more routes here if needed
// ];
import { Routes } from '@angular/router';
import { UserLoginComponent } from './user-login/user-login.component';
import { AppComponent } from './app.component';
import { BookComponent } from './book.component';
import { AuthorComponent } from './author.component';
import { BorrowerComponent } from './borrower.component';
import { BorrowComponent } from './borrow.component';

export const routes: Routes = [
  //{ path: '', component: AppComponent }, // Set AppComponent as the default route
  { path: 'books', component: BookComponent },
  { path: 'borrows', component: BorrowComponent },
  { path: 'borrowers', component: BorrowerComponent },
  { path: 'authors', component: AuthorComponent},
  { path: 'login', component: UserLoginComponent }, // New route for login page
];
