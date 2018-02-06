import {Routes} from '@angular/router';

import {UserRouteAccessService} from '../../shared';

import {ConsolidationComponent} from "./consolidation.component";

export const consolidation: Routes = [
    {
        path: 'consolidation',
        component: ConsolidationComponent,
        data: {
            authorities: ['ROLE_MANAGER'],
            pageTitle: 'Consolidation'
        },
        canActivate: [UserRouteAccessService]
    }
];
