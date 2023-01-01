import { createFeatureSelector, createSelector } from "@ngrx/store";
import { UserState } from "./user.reducer";


export const userState = createFeatureSelector<UserState>("user");

export const userSelector = createSelector(userState, (state:UserState) => state.user);