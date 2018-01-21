import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';

import { CapConfig } from './cap-config.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class CapConfigService {

    private resourceUrl = 'api/cap-configs';

    constructor(private http: Http) { }

    create(capConfig: CapConfig): Observable<CapConfig> {
        const copy = this.convert(capConfig);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    update(capConfig: CapConfig): Observable<CapConfig> {
        const copy = this.convert(capConfig);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    find(id: string): Observable<CapConfig> {
        return this.http.get(`${this.resourceUrl}/${id}`).map((res: Response) => {
            return res.json();
        });
    }

    query(req?: any): Observable<ResponseWrapper> {
        const options = createRequestOption(req);
        return this.http.get(this.resourceUrl, options)
            .map((res: Response) => this.convertResponse(res));
    }

    delete(id: string): Observable<Response> {
        return this.http.delete(`${this.resourceUrl}/${id}`);
    }

    private convertResponse(res: Response): ResponseWrapper {
        const jsonResponse = res.json();
        return new ResponseWrapper(res.headers, jsonResponse, res.status);
    }

    private convert(capConfig: CapConfig): CapConfig {
        const copy: CapConfig = Object.assign({}, capConfig);
        return copy;
    }
}
