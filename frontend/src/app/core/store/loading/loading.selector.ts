import { createFeatureSelector, createSelector } from "@ngrx/store";
import { LoadingState } from "./loading.reducer";


export const loadingState = createFeatureSelector<LoadingState>("loading");

export const loadingStatus = createSelector(loadingState, (state:LoadingState) => state.status);
export const loadingMsg = createSelector(loadingState, (state:LoadingState) => state.message);