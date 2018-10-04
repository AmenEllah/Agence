import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IVoyage } from 'app/shared/model/voyage.model';

type EntityResponseType = HttpResponse<IVoyage>;
type EntityArrayResponseType = HttpResponse<IVoyage[]>;

@Injectable({ providedIn: 'root' })
export class VoyageService {
    private resourceUrl = SERVER_API_URL + 'api/voyages';

    constructor(private http: HttpClient) {}

    create(voyage: IVoyage): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(voyage);
        return this.http
            .post<IVoyage>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    update(voyage: IVoyage): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(voyage);
        return this.http
            .put<IVoyage>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http
            .get<IVoyage>(`${this.resourceUrl}/${id}`, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<IVoyage[]>(this.resourceUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    private convertDateFromClient(voyage: IVoyage): IVoyage {
        const copy: IVoyage = Object.assign({}, voyage, {
            dateDepart: voyage.dateDepart != null && voyage.dateDepart.isValid() ? voyage.dateDepart.format(DATE_FORMAT) : null
        });
        return copy;
    }

    private convertDateFromServer(res: EntityResponseType): EntityResponseType {
        res.body.dateDepart = res.body.dateDepart != null ? moment(res.body.dateDepart) : null;
        return res;
    }

    private convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
        res.body.forEach((voyage: IVoyage) => {
            voyage.dateDepart = voyage.dateDepart != null ? moment(voyage.dateDepart) : null;
        });
        return res;
    }
}
