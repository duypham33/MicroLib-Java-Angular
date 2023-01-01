import { createReducer, on } from "@ngrx/store"
import { getResult, reset, startLoading } from "./loading.actions";

export interface LoadingState {
    status:string,
    message:string
}

const initialState:LoadingState = {
    status: "",
    message: ""
}

export const loadingReducer = createReducer(
    initialState,
    on(startLoading, (state, payload) => {
        return {status: "loading", message: ""}
    }),
    on(getResult, (state, payload) => {
        return payload;
    }),
    on(reset, (state, payload) => {
        return {status: "", message: ""};
    })
);