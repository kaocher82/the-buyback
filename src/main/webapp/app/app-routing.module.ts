import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import {navbarRoute} from './app.route';
import {errorRoute, loginRoutes, ssoRoute} from "./layouts";
import { DEBUG_INFO_ENABLED } from './app.constants';

const LAYOUT_ROUTES = [
    navbarRoute,
    ssoRoute,
    ...loginRoutes,
    ...errorRoute
];

@NgModule({
    imports: [
        RouterModule.forRoot(LAYOUT_ROUTES, { useHash: true , enableTracing: DEBUG_INFO_ENABLED })
    ],
    exports: [
        RouterModule
    ]
})
export class LayoutRoutingModule {}
