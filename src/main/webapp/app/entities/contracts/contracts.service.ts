import {Injectable} from '@angular/core';
import {Http, Response} from '@angular/http';
import {Observable} from 'rxjs/Rx';

import {Contracts} from './contracts.model';

@Injectable()
export class ContractsService {

    private resourceUrl = 'api/contracts';

    constructor(private http: Http) { }

    loadBuybackContracts(): Observable<Contracts[]> {
        return this.http.get(`${this.resourceUrl}/buyback/pending`).map((res: Response) => {
            return res.json();
        });
    }
}
