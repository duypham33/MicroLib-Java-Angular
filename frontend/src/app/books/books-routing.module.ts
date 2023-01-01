import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { AddComponent } from './add/add.component';
import { HomeComponent } from './home/home.component';
import { MyborrowingsComponent } from './myborrowings/myborrowings.component';
import { UpdateComponent } from './update/update.component';

const routes: Routes = [{
  path: '',
  component: HomeComponent
}, {
  path: 'add',
  component: AddComponent
}, {
  path: 'update/:id',
  component: UpdateComponent
}, {
  path: 'borrowings',
  component: MyborrowingsComponent
}];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class BooksRoutingModule { }
