import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { JhiDateUtils } from 'ng-jhipster';

import { CapOrder } from './cap-order.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class CapOrderService {

    private resourceUrl =  SERVER_API_URL + 'api/cap-orders';

    constructor(private http: Http, private dateUtils: JhiDateUtils) { }

    create(capOrder: CapOrder): Observable<CapOrder> {
        const copy = this.convert(capOrder);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    update(capOrder: CapOrder): Observable<CapOrder> {
        const copy = this.convert(capOrder);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    find(id: string): Observable<CapOrder> {
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
     * Convert a returned JSON object to CapOrder.
     */
    private convertItemFromServer(json: any): CapOrder {
        const entity: CapOrder = Object.assign(new CapOrder(), json);
        entity.created = this.dateUtils
            .convertDateTimeFromServer(json.created);
        return entity;
    }

    /**
     * Convert a CapOrder to a JSON which can be sent to the server.
     */
    private convert(capOrder: CapOrder): CapOrder {
        const copy: CapOrder = Object.assign({}, capOrder);

        copy.created = this.dateUtils.toDate(capOrder.created);
        return copy;
    }
}
