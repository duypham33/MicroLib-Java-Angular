import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { AuthComponent } from './auth/auth.component';

const routes: Routes = [{
  path: '',
  loadChildren: () => import("./books/books.module").then(m => m.BooksModule)
}, {
  path: 'login',
  component: AuthComponent
}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
