import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { StoreModule } from '@ngrx/store';
import { booksReducer } from './store/books/books.reducer';
import { EffectsModule } from '@ngrx/effects';
import { BooksEffects } from './store/books/books.effects';
import { loadingReducer } from './store/loading/loading.reducer';
import { userReducer } from './store/user/user.reducer';
import { UserEffects } from './store/user/user.effects';



@NgModule({
  declarations: [],
  imports: [
    CommonModule,
    StoreModule.forFeature("books", booksReducer),
    StoreModule.forFeature("loading", loadingReducer),
    StoreModule.forFeature("user", userReducer),
    EffectsModule.forFeature([BooksEffects, UserEffects])
  ]
})
export class CoreModule { }
