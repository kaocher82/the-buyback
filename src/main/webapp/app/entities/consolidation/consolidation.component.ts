import {AfterViewInit, Component, OnInit} from '@angular/core';

import {Http} from "@angular/http";

@Component({
    selector: 'jhi-consolidation',
    templateUrl: './consolidation.component.html'
})
export class ConsolidationComponent {

    hub442: any;
    region442: any;
    detailsRegion442: any;

    hub68: any;
    region68: any;
    detailsRegion68: any;

    hubTM: any;
    regionTM: any;
    detailsRegionTM: any;

    hubGE: any;
    regionGE: any;
    detailsRegionGE: any;

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
        http.get('/api/assets/GE-8JV/region/details').subscribe((data) => {
            this.detailsRegionGE = data.json();
        });
        http.get('/api/assets/TM-0P2/region/details').subscribe((data) => {
            this.detailsRegionTM = data.json();
        });
        http.get('/api/assets/68FT-6/region/details').subscribe((data) => {
            this.detailsRegion68 = data.json();
        });
        http.get('/api/assets/442-CS/region/details').subscribe((data) => {
            this.detailsRegion442 = data.json();
        });
    }

}
