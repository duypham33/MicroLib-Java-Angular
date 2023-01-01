import { createAction, props } from "@ngrx/store";


export const startLoading = createAction(
    "[Loading] start loading"
);

export const getResult = createAction(
    "[Loading] get result", 
    props<{status:string, message:string}>()
);

export const reset = createAction(
    "[Loading] reset"
)