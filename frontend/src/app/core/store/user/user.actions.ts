import { createAction, props } from "@ngrx/store";
import { User } from "../../models/user";



export const checkToken = createAction(
    "[User] validate jwt token"
);

export const loginUser = createAction(
    "[User] login user", props<{username: string, password: string}>()
);

export const setUser = createAction(
    "[User] set user", props<{payload: User}>()
);


export const resetUser = createAction(
    "[User] reset user"
);