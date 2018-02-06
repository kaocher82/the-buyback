import {AfterViewInit, Component, OnInit} from '@angular/core';

import {Http} from "@angular/http";

@Component({
    selector: 'jhi-consolidation',
    templateUrl: './consolidation.component.html'
})
export class ConsolidationComponent {

    hub442: any;
    region442: any;

    hub68: any;
    region68: any;

    hubTM: any;
    regionTM: any;

    hubGE: any;
    regionGE: any;

    constructor(private http: Http) {
        http.get('/api/assets/442-CS/hub').subscribe((data) => {
            this.hub442 = data.json();
        });
        http.get('/api/assets/442-CS/region').subscribe((data) => {
            this.region442 = data.json();
        });
        http.get('/api/assets/68FT-6/hub').subscribe((data) => {
            this.hub68 = data.json();
        });
        http.get('/api/assets/68FT-6/region').subscribe((data) => {
            this.region68 = data.json();
        });
        http.get('/api/assets/TM-0P2/hub').subscribe((data) => {
            this.hubTM = data.json();
        });
        http.get('/api/assets/TM-0P2/region').subscribe((data) => {
            this.regionTM = data.json();
        });
        http.get('/api/assets/GE-8JV/hub').subscribe((data) => {
            this.hubGE = data.json();
        });
        http.get('/api/assets/GE-8JV/region').subscribe((data) => {
            this.regionGE = data.json();
        });
    }

}
