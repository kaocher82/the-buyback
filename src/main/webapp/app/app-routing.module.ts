import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import {navbarRoute} from './app.route';
import {errorRoute, loginRoutes, ssoRoute} from "./layouts";

const LAYOUT_ROUTES = [
    navbarRoute,
    ssoRoute,
    ...loginRoutes,
    ...errorRoute
];

@NgModule({
    imports: [
        RouterModule.forRoot(LAYOUT_ROUTES, { useHash: true })
    ],
    exports: [
        RouterModule
    ]
})
export class LayoutRoutingModule {}
