import {Component, OnInit} from '@angular/core';
import {HttpClient} from "@angular/common/http";

@Component({
    selector: 'jhi-consolidation',
    templateUrl: './consolidation.component.html'
})
export class ConsolidationComponent implements OnInit {

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

    constructor(private http: HttpClient) {
    }

    ngOnInit(): void {

        this.http.get<any>('/api/assets/442-CS/hub').subscribe((data) => {
            this.hub442 = data;
        });
        this.http.get<any>('/api/assets/442-CS/region').subscribe((data) => {
            this.region442 = data;
        });
        this.http.get<any>('/api/assets/68FT-6/hub').subscribe((data) => {
            this.hub68 = data;
        });
        this.http.get<any>('/api/assets/68FT-6/region').subscribe((data) => {
            this.region68 = data;
        });
        this.http.get<any>('/api/assets/TM-0P2/hub').subscribe((data) => {
            this.hubTM = data;
        });
        this.http.get<any>('/api/assets/TM-0P2/region').subscribe((data) => {
            this.regionTM = data;
        });
        this.http.get<any>('/api/assets/GE-8JV/hub').subscribe((data) => {
            this.hubGE = data;
        });
        this.http.get<any>('/api/assets/GE-8JV/region').subscribe((data) => {
            this.regionGE = data;
        });
        this.http.get<any>('/api/assets/GE-8JV/region/details').subscribe((data) => {
            this.detailsRegionGE = data;
        });
        this.http.get<any>('/api/assets/TM-0P2/region/details').subscribe((data) => {
            this.detailsRegionTM = data;
        });
        this.http.get<any>('/api/assets/68FT-6/region/details').subscribe((data) => {
            this.detailsRegion68 = data;
        });
        this.http.get<any>('/api/assets/442-CS/region/details').subscribe((data) => {
            this.detailsRegion442 = data;
        });
    }

}
