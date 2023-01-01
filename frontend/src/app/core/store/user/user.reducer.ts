import { createReducer, on } from "@ngrx/store";
import { User } from "../../models/user"
import { resetUser, setUser } from "./user.actions";

export interface UserState {
    user: User | null
}

const initialState : UserState = {user: null};

export const userReducer = createReducer(
    initialState,
    on(setUser, (state, {payload}) => {
        return {...state, user: payload};
    }),
    on(resetUser, (state, payload) => {
        return {...state, user: null};
    })
)