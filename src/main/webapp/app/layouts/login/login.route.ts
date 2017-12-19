import { Routes} from '@angular/router';
import {LoginComponent} from "./login.component";

export const loginRoutes: Routes = [
    {
        path: 'login/:target',
        component: LoginComponent
    },
    {
        path: 'login',
        component: LoginComponent
    }
];
