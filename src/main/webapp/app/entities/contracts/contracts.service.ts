import {Injectable} from '@angular/core';
import {Observable} from 'rxjs/Rx';

import {Contracts} from './contracts.model';
import {HttpClient} from "@angular/common/http";

@Injectable()
export class ContractsService {

    private resourceUrl = 'api/contracts';

    constructor(private http: HttpClient) { }

    loadBuybackContracts(): Observable<Contracts[]> {
        return this.http.get<Contracts[]>(`${this.resourceUrl}/buyback/pending`);
    }
}
