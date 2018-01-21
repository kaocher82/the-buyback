import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { CapConfig } from './cap-config.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class CapConfigService {

    private resourceUrl =  SERVER_API_URL + 'api/cap-configs';

    constructor(private http: Http) { }

    create(capConfig: CapConfig): Observable<CapConfig> {
        const copy = this.convert(capConfig);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    update(capConfig: CapConfig): Observable<CapConfig> {
        const copy = this.convert(capConfig);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    find(id: string): Observable<CapConfig> {
        return this.http.get(`${this.resourceUrl}/${id}`).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
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
        const result = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            result.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return new ResponseWrapper(res.headers, result, res.status);
    }

    /**
     * Convert a returned JSON object to CapConfig.
     */
    private convertItemFromServer(json: any): CapConfig {
        const entity: CapConfig = Object.assign(new CapConfig(), json);
        return entity;
    }

    /**
     * Convert a CapConfig to a JSON which can be sent to the server.
     */
    private convert(capConfig: CapConfig): CapConfig {
        const copy: CapConfig = Object.assign({}, capConfig);
        return copy;
    }
}
