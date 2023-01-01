import { BooksState } from "./books/books.reducer";
import { LoadingState } from "./loading/loading.reducer";
import { UserState } from "./user/user.reducer";


export interface AppState{
    books: BooksState,
    loading: LoadingState,
    user: UserState
}