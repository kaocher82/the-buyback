import {Component, OnInit} from '@angular/core';
import {Http} from "@angular/http";
import {Appraisal} from "./appraisal.model";

@Component({
    selector: 'jhi-appraisal',
    templateUrl: './appraisal.component.html'
})
export class AppraisalComponent implements OnInit {

    isLoadingAppraisal: boolean;
    submitDone: boolean;
    appraisal: Appraisal;

    constructor(
        private http: Http
    ) {
    }

    ngOnInit(): void {
        if (!this.appraisal) {
            this.appraisal = new Appraisal();
        }
    }

    executeAppraisal() {
        this.isLoadingAppraisal = true;
        this.submitDone = false;
        return this.http.post('api/appraisal', this.appraisal).subscribe((data) => {
            this.appraisal = data.json();
            this.isLoadingAppraisal = false;
        });
    }
}
