import { Component, OnInit } from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';
import {StateStorageService} from "../../shared/auth/state-storage.service";

@Component({
    selector: 'jhi-error',
    templateUrl: './error.component.html'
})
export class ErrorComponent implements OnInit {
    errorMessage: string;
    error403: boolean;
    url: string;

    constructor(
        private route: ActivatedRoute,
        private router: Router,
        private stateStorageService: StateStorageService
    ) {
    }

    ngOnInit() {
        this.route.data.subscribe((routeData) => {
            if (routeData.error403) {
                this.error403 = routeData.error403;
            }
            if (routeData.errorMessage) {
                this.errorMessage = routeData.errorMessage;
            }
        });
        this.url = this.stateStorageService.getUrl();
    }


    login() {
        this.router.navigate(['/login/' + this.url]);
    }
}
